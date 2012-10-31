package org.claros.commons.mail.protocols;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.FolderNotFoundException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.claros.commons.auth.models.AuthProfile;
import org.claros.commons.exception.SystemException;
import org.claros.commons.mail.exception.ConnectionException;
import org.claros.commons.mail.exception.MailboxActionException;
import org.claros.commons.mail.exception.ServerDownException;
import org.claros.commons.mail.models.ConnectionMetaHandler;
import org.claros.commons.mail.models.ConnectionProfile;
import org.claros.commons.mail.models.EmailHeader;
import org.claros.commons.mail.models.EmailSensitivity;
import org.claros.commons.mail.utility.Constants;
import org.claros.commons.mail.utility.Utility;
import org.claros.commons.utility.Formatter;

/**
 * @author Umut Gokbayrak
 */
public class ImapProtocolImpl implements Protocol {
	private static Log log = LogFactory.getLog(ImapProtocolImpl.class);
	private String folder;
	private ConnectionProfile profile;
	private AuthProfile auth;
	private ConnectionMetaHandler handler;
	private static final Locale loc = new Locale("en", "US");
	private static Map imapFolders = Collections.synchronizedMap(new HashMap());
	
	/**
	 * 
	 * @param profile
	 * @param auth
	 * @param handler
	 */
	ImapProtocolImpl(ConnectionProfile profile, AuthProfile auth, ConnectionMetaHandler handler, String folder) {
		this.profile = profile;
		this.auth = auth;
		this.handler = handler;
		this.folder = folder;
		
		if (imapFolders.get(auth.getUsername()) == null) {
			HashMap imapUserFolders = new HashMap();
			imapFolders.put(auth.getUsername(), imapUserFolders);
		}
		
		if (this.folder == null || this.folder.trim().equals("") || this.folder.toLowerCase(loc).equals(Constants.FOLDER_INBOX(profile).toLowerCase(loc))) {
			this.folder = Constants.FOLDER_INBOX(profile);
		} else {
			if (!this.folder.startsWith(profile.getFolderNameSpace())) {
				this.folder = profile.getFolderNameSpace() + this.folder;
			}
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public Folder getFolder() throws Exception {
		return getImapFolder(true);
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public Folder getImapFolder(boolean useCache) throws Exception {
		Folder myFold = null;
		if (folder == null) {
			folder = Constants.FOLDER_INBOX(profile);
		}

		if (folder != null && handler != null) {
			Store store = handler.getStore();
			if (store == null || !store.isConnected()) {
				log.debug("Connection is closed. Restoring it...");
				handler = connect(Constants.CONNECTION_READ_WRITE);
				log.debug("Connection re-established");
			}
			
			HashMap imapUserFolders = null;
			if (useCache) {
				imapUserFolders = (HashMap)imapFolders.get(auth.getUsername());
				myFold = (Folder)imapUserFolders.get(folder);
			}
			if (myFold == null) {
				myFold = handler.getStore().getFolder(folder);
			}
			if (!myFold.isOpen()) {
				try {
					log.debug("Folder :" + folder + " is closed. Opening.");
					myFold.open(Constants.CONNECTION_READ_WRITE);
					log.debug("Folder is open.");
				} catch (Throwable e) {
					log.debug("nevermind go on");
					// nevermind go on...
				}
			}
			if (useCache) {
				try {
					imapUserFolders.put(folder, myFold);
					imapFolders.put(auth.getUsername(), imapUserFolders);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return myFold;
	}

	/* (non-Javadoc)
	 * @see org.claros.commons.mail.protocols.Protocol#connect(int)
	 */
	public ConnectionMetaHandler connect(int connectType) throws SystemException, ConnectionException, ServerDownException {
		Folder fold = null;
		try {
			if (handler == null || handler.getStore() == null || !handler.getStore().isConnected()) {
				Properties props = new Properties();
//				props.setProperty("mail.imap.separatestoreconnection", "true");
//				props.setProperty("mail.imap.connectionpoolsize", "0");
//				props.setProperty("mail.imap.connectionpooltimeout", "100");
				
//				props.setProperty("mail.debug", "true");
				Session session = Session.getDefaultInstance(props);
				log.debug("session instance initiated");
				handler = new ConnectionMetaHandler();
				handler.setStore(session.getStore(profile.getProtocol()));
				log.debug("session store set. protocol is: " + profile.getProtocol());
				handler.getStore().connect(profile.getFetchServer(), profile.getIFetchPort(), auth.getUsername(), auth.getPassword());
				if (handler.getStore().isConnected()) {
					log.debug("Store has been connected... Successful");
				} else {
					log.warn("Connection unsuccessfull...!!");
				}
			}
			fold = handler.getStore().getFolder(Constants.FOLDER_INBOX(profile));

			HashMap imapUserFolders = (HashMap)imapFolders.get(auth.getUsername());
			imapUserFolders.put("INBOX", fold);
			imapFolders.put(auth.getUsername(), imapUserFolders);
			
			handler.setMbox(fold);
			log.debug("Got mailbox folder. Folder is: " + fold.getFullName());
			handler.setTotalMessagesCount(fold.getMessageCount());
			log.debug("Message Count:" + handler.getTotalMessagesCount());
		} catch (FolderNotFoundException e) {
			log.fatal(profile.getProtocol() + " cannot identify the INBOX folder. Please check your folder-namespace variable at config.xml.");
			throw new SystemException(e);
		} catch (NoSuchProviderException e) {
			log.fatal(profile.getProtocol() + " provider could not be found.");
			throw new SystemException(e);
		} catch (MessagingException e) {
			Exception ne = e.getNextException();
			if (ne != null) {
				if (ne instanceof ConnectException || ne instanceof IOException) {
					throw new ServerDownException("Server is unreachable.");
				}
			}
			log.error("Connection could not be established." + e.getMessage());
//			throw new ConnectionException(e);
		} catch (Exception e) {
			log.error("An unknown exception while connect.", e);
		}
		return handler;
	}

	/* (non-Javadoc)
	 * @see org.claros.commons.mail.protocols.Protocol#deleteMessages(int[])
	 */	
	public ConnectionMetaHandler deleteMessages(int[] messageIds) throws MailboxActionException, SystemException, ConnectionException {
		Folder fold = null;
		try {
			fold = getFolder();
			if (messageIds != null && messageIds.length > 0) {
				for (int i=0;i<messageIds.length;i++) {
					try {
						if (messageIds[i] > 0) {
							Message msg = fold.getMessage(messageIds[i]);
							msg.setFlag(Flags.Flag.DELETED, true);
						}
					} catch (Exception e) {
						log.debug("error while deleting messsage", e);
					}
				}
				fold.expunge();
			}
		} catch (MessagingException e) {
			log.error("Could not delete message ids: " + messageIds, e);
			throw new MailboxActionException(e);
		} catch (IndexOutOfBoundsException e) {
			log.error("Maybe you are double clicking the delete button", e);
		} catch (Exception e) {
			log.error("Could not delete message ids: " + messageIds, e);
			throw new MailboxActionException(e);
		} finally {
			closeFolder(fold);
//			disconnect();
		}
		return handler;
	}


	/**
	 * Fetches all e-mail headers from the server, with appropriate
	 * fields already set.
	 * @param handler
	 * @return ArrayList of MessageHeaders
	 * @throws ConnectionException
	 */
	public ArrayList fetchAllHeaders() throws SystemException, ConnectionException {
		ArrayList headers = new ArrayList();
		Folder fold = null;
		try {
			fold = getFolder();
			EmailHeader header = null;

			Message[] msgs = fold.getMessages();
			FetchProfile fp = new FetchProfile();
			fp.add(FetchProfile.Item.ENVELOPE);
			fp.add(FetchProfile.Item.FLAGS);
			fp.add(FetchProfile.Item.CONTENT_INFO);
			fp.add("Size");
			fp.add("Date");
			fp.add("Disposition-Notification-To");
			fp.add("X-Priority");
			fp.add("X-MSMail-Priority");
			fp.add("Sensitivity");
			fold.fetch(msgs, fp);

			Message msg = null;
			for (int i = 0; i < msgs.length; i++) {
				try {
					header = new EmailHeader();
					msg = msgs[i];

					header.setMultipart((msg.isMimeType("multipart/*")) ? true : false);
					header.setMessageId(i + 1);
					header.setFrom(msg.getFrom());
					header.setTo(msg.getRecipients(Message.RecipientType.TO));
					header.setCc(msg.getRecipients(Message.RecipientType.CC));
					header.setBcc(msg.getRecipients(Message.RecipientType.BCC));
					header.setDate(msg.getSentDate());
					header.setReplyTo(msg.getReplyTo());
					header.setSize(msg.getSize());
					header.setSubject(org.claros.commons.utility.Utility.updateTRChars(msg.getSubject()));
                    
					// now set the human readables.
					header.setDateShown(Formatter.formatDate(header.getDate(), "dd.MM.yyyy HH:mm"));
					header.setFromShown(org.claros.commons.utility.Utility.updateTRChars(Utility.addressArrToStringShort(header.getFrom())));
					header.setToShown(Utility.addressArrToStringShort(header.getTo()));
					header.setCcShown(Utility.addressArrToStringShort(header.getCc()));
					header.setSizeShown(Utility.sizeToHumanReadable(header.getSize()));
					
					org.claros.commons.mail.parser.MessageParser.setHeaders(msg, header);
                    
					boolean deleted = false;
					if (profile.getProtocol().equals(Constants.IMAP)) {
						Flags.Flag flags[] = msg.getFlags().getSystemFlags();
						if (flags != null) {
							Flags.Flag flag = null;
							for (int m=0; m < flags.length; m++) {
								flag = flags[m];
								if (flag.equals(Flags.Flag.SEEN)) {
									header.setUnread(new Boolean(false));
								}
								
								if (flag.equals(Flags.Flag.DELETED)) {
									deleted = true;
								}
							}
						}
					}
					if (header.getUnread() == null) {
						header.setUnread(new Boolean(true));
					}
                    
					// it is time to add it to the arraylist
					if (!deleted) {
						headers.add(header);
					}
				} catch (MessagingException e1) {
					log.error("Could not parse headers of e-mail. Message might be defuncted or illegal formatted.", e1);
				}
			}
		} catch (MessagingException e) {
			log.error("Could not fetch message headers. Is mbox connection still alive???", e);
//			throw new ConnectionException(e);
		} catch (Exception e) {
			log.error("Could not fetch message headers. Is mbox connection still alive???", e);
//			throw new ConnectionException(e);
		}
		return headers;
	}


	/**
	 * Fetches and returns message headers as message objects.
	 * @return
	 * @throws SystemException
	 * @throws ConnectionException
	 */
	public ArrayList fetchAllHeadersAsMessages() throws SystemException, ConnectionException {
		ArrayList headers = null;
		Folder fold = null;
		try {
			headers = new ArrayList();
			fold = getFolder();
			Message[] msgs = fold.getMessages();
			FetchProfile fp = new FetchProfile();
			fp.add(FetchProfile.Item.ENVELOPE);
			fp.add(FetchProfile.Item.FLAGS);
			fp.add(FetchProfile.Item.CONTENT_INFO);
			fp.add("Size");
			fp.add("Date");
			fold.fetch(msgs, fp);

			Message msg = null;
			for (int i = 0; i < msgs.length; i++) {
				try {
					msg = msgs[i];

					boolean deleted = false;
					Flags.Flag flags[] = msg.getFlags().getSystemFlags();
					if (flags != null) {
						Flags.Flag flag = null;
						for (int m=0; m < flags.length; m++) {
							flag = flags[m];
							if (flag.equals(Flags.Flag.DELETED)) {
								deleted = true;
							}
						}
					}
					if (!deleted) {
						headers.add(msg);
					}
				} catch (Exception e) {
					log.debug("probably an error fetching list", e);
				}
			}
		} catch (MessagingException e) {
			log.error("Could not fetch message headers. Is mbox connection still alive???", e);
			throw new ConnectionException(e);
		} catch (Exception e) {
			log.error("Could not fetch message headers. Is mbox connection still alive???", e);
			throw new ConnectionException(e);
		}
		return headers;
	}

	/**
	 * 
	 */
	public Message getMessage(int messageId) throws MailboxActionException, SystemException, ConnectionException, Exception {
		Message msg = null;
		Folder fold = null;
		try {
			try {
				fold = getFolder();
				msg = fold.getMessage(messageId);
			} catch (MessagingException e) {
				log.error("Could not fetch message body from remote server.", e);
				throw new MailboxActionException(e);
			}
		} catch (Exception e) {
			throw e;
		}
		return msg;
	}

	/**
	 * Disconnects the previously opened data connection if
	 * the connection is still alive.
	 * @param handler
	 */
	public void disconnect() {
		try {
			HashMap imapUserFolders = (HashMap)imapFolders.get(auth.getUsername());
			Iterator iter = imapUserFolders.keySet().iterator();
			Folder tmp = null;
			while (iter.hasNext()) {
				try {
					tmp = (Folder)imapUserFolders.get((String)iter.next());
					closeFolder(tmp);
					tmp = null;
				} catch (Throwable e) {
					log.debug("Unable to close folder:" + tmp);
				}
			}
			imapFolders.put(auth.getUsername(), new HashMap());
		} catch (Throwable e1) {
		}
		
		try {
			handler.getStore().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param msgId
	 * @throws Exception
	 */
	public void markAsRead(Long msgId) throws Exception {
		Folder f = getFolder();
		try {
			Message msg = f.getMessage(msgId.intValue());
			msg.setFlag(Flags.Flag.SEEN, true);
		} catch (MessagingException e) {
			log.warn("Marking as Read not worked.", e);
		}
	}

	/**
	 * 
	 * @param buff
	 * @throws Exception
	 */
	public void appendEmail(byte[] buff) throws Exception {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props);
		ByteArrayInputStream bis = new ByteArrayInputStream(buff);
		MimeMessage msg = new MimeMessage(session, bis);
		msg.setFlag(Flags.Flag.SEEN, true);

		ProtocolFactory factory = new ProtocolFactory(profile, auth, handler);
		ImapProtocolImpl imap = (ImapProtocolImpl)factory.getImap(folder);
		Folder f = imap.getFolder();

		try {
			f.appendMessages(new Message[] {msg});
		} catch (MessagingException e) {
			log.warn("appenging msg to folder : " + folder + " failed.", e);
		} finally {
			bis.close();
		}
	}

	/**
	 * 
	 * @param msgId
	 * @param destFolder
	 * @throws Exception
	 */
	public void moveEmail(Long msgId, String destFolder) throws Exception {
		ProtocolFactory factory = new ProtocolFactory(profile, auth, handler);
		ImapProtocolImpl fromProtocol = (ImapProtocolImpl)factory.getImap(folder);
		ImapProtocolImpl destProtocol = (ImapProtocolImpl)factory.getImap(destFolder);
		Folder from = fromProtocol.getFolder();
		Folder dest = null;

		try {
			Message msg = fromProtocol.getMessage(msgId.intValue());
			if (msg != null) {
				// because of the buggy imap servers lost the connection after getMessage
				// we need to check if the folder is open or not. 
				// (Do not use uw-imapd, it sucks!!!)
				from = fromProtocol.getFolder();
				dest = destProtocol.getFolder();
				from.copyMessages(new Message[] {msg}, dest);
				// deleteMessages(new int[] {msg.getMessageNumber()});
				flagAsDeleted(new int[] {msg.getMessageNumber()});
			}
		} catch (IndexOutOfBoundsException e) {
			log.debug("Index kaçtı. Moving message to folder : " + destFolder + " failed.", e);
		} catch (Exception e) {
			log.warn("Moving message to folder : " + destFolder + " failed.", e);
		}
	}

	/**
	 * 
	 * @param messageIds
	 * @param destFolder
	 * @throws Exception
	 */
	public void moveEmails(int messageIds[], String destFolder) throws Exception {
		ProtocolFactory factory = new ProtocolFactory(profile, auth, handler);
		ImapProtocolImpl fromProtocol = (ImapProtocolImpl)factory.getImap(folder);
		ImapProtocolImpl destProtocol = (ImapProtocolImpl)factory.getImap(destFolder);
		Folder from = fromProtocol.getFolder();
		Folder dest = null;

		try {
			Message msg = null;
			
			int counter = 0;
			dest = destProtocol.getFolder();
			Message msgs[] = new MimeMessage[messageIds.length];
			
			// copy messages to destination folder first
			for (int i=0;i<messageIds.length;i++) {
				try {
					msg = fromProtocol.getMessage(messageIds[i]);

					if (msg != null) {
						msgs[counter] = msg;
						counter++;
					}
				} catch (Exception e) {
					log.debug("error while copying messages", e);
				}
			}
			
			from.copyMessages(msgs, dest);
			// now delete the processed messages all at a time.
			// deleteMessages(messageIds);
			flagAsDeleted(messageIds);
		} catch (IndexOutOfBoundsException e) {
			log.debug("Index kaçtı. Moving message to folder : " + destFolder + " failed.", e);
		} catch (Exception e) {
			log.warn("Moving message to folder : " + destFolder + " failed.", e);
		}
	}

	/**
	 * 
	 * @param messageIds
	 * @param destFolders
	 * @throws Exception
	 */
	public void moveEmails(int messageIds[], String destFolders[]) throws Exception {
		ProtocolFactory factory = new ProtocolFactory(profile, auth, handler);
		ImapProtocolImpl fromProtocol = (ImapProtocolImpl)factory.getImap(folder);
		Folder from = fromProtocol.getFolder();
		Folder dest = null;

		try {
			Message msg = null;
			// copy messages to destination folder first
			for (int i=0;i<messageIds.length;i++) {
				try {
					msg = fromProtocol.getMessage(messageIds[i]);
					ImapProtocolImpl destProtocol = (ImapProtocolImpl)factory.getImap(destFolders[i]);
					dest = destProtocol.getFolder();
					from.copyMessages(new Message[] {msg}, dest);
				} catch (Exception e) {
					log.debug("error while copying messages", e);
				}
			}
			
			// now delete the processed messages all at a time.
			// deleteMessages(messageIds);
			flagAsDeleted(messageIds);
			
		} catch (Exception e) {
			log.warn("Moving message failed.", e);
		}
	}

	/**
	 * @return
	 */
	public Folder[] listFolders() throws Exception {
		ProtocolFactory factory = new ProtocolFactory(profile, auth, handler);
		ImapProtocolImpl protocol = (ImapProtocolImpl)factory.getImap(folder);
		Folder f = protocol.getFolder();

		Folder parent = null;
		Folder[] folders = null;
		
		try {
			parent = f.getParent();
			folders = parent.listSubscribed("*");
		} catch (MessagingException e) {
			log.warn("Cannot get folder list.");
		} finally {
			// closeFolder(parent);
		}
		return folders;
	}

	/**
	 * 
	 * @param f
	 */
	public void closeFolder(Folder f) {
		if (f != null) {
			try {
				if (f.isOpen()) {
					f.close(true);
					log.info("Folder: " + f.getName() + " was open and now closed.");

					HashMap imapUserFolders = (HashMap)imapFolders.get(auth.getUsername());
					imapUserFolders.put(folder, null);
					imapFolders.put(auth.getUsername(), imapUserFolders);
				} else {
					log.info("Folder: " + f.getName() + " was already closed.");
				}
			} catch (MessagingException e) {
				log.info("Error while closing folder: " + f.getName(), e);
			}
		}
	}

	/**
	 * 
	 */
	public void createFolder() throws Exception {
		Folder f = getFolder();
		try {
			if (!f.exists()) {
				f.create(Folder.HOLDS_MESSAGES);
				f.setSubscribed(true);
			} else {
				if (!f.isSubscribed()) {
					f.setSubscribed(true);
				}
			}
		} catch (MessagingException e) {
			log.warn("Could not create folder: " + f.getName());
		}
	}

	/**
	 * @return
	 */
	public int getUnreadMessageCount() throws Exception {
		Folder f = getFolder();

		if (f.exists()) {
			return f.getUnreadMessageCount();
		}
		return 0;
	}

	/**
	 * @return
	 */
	public int getTotalMessageCount() throws Exception {
		Folder f = getFolder();

		if (f.exists()) {
			return f.getMessageCount();
		}
		return 0;
	}

	/**
	 * 
	 */
	public void emptyFolder() throws Exception {
		Folder f = getFolder();

		try {
			Message msgs[] = f.getMessages();
			FetchProfile fp = new FetchProfile();
			fp.add(FetchProfile.Item.ENVELOPE);
			f.fetch(msgs, fp);
			
			int ids[] = new int[msgs.length];
			for (int i=0; i<msgs.length; i++) {
				ids[i] = msgs[i].getMessageNumber();
			}
			if (ids.length > 0) {
				flagAsDeleted(ids);
				// deleteMessages(ids);
			}
		} catch (Exception e) {
			log.warn("Could not delete all messages in folder: " + folder);
		}
	}

	/**
	 * 
	 */
	public void flagAsDeleted(int[] messageIds) throws Exception {
		Folder fold = null;
		try {
			fold = getFolder();
			if (messageIds != null && messageIds.length > 0) {
				for (int i=0;i<messageIds.length;i++) {
					try {
						if (messageIds[i] > 0) {
							Message msg = fold.getMessage(messageIds[i]);
							msg.setFlag(Flags.Flag.SEEN, true);
							msg.setFlag(Flags.Flag.DELETED, true);
						}
					} catch (Exception e) {
						log.debug("error while deleting messsage", e);
					}
				}
			}
		} catch (MessagingException e) {
			log.error("Could not delete message ids: " + messageIds, e);
			throw new MailboxActionException(e);
		} catch (IndexOutOfBoundsException e) {
			log.warn("Maybe you are double clicking the delete button. do it a little bit slowly :) ", e);
		} catch (Exception e) {
			log.error("Could not delete message ids: " + messageIds, e);
			throw new MailboxActionException(e);
		}
	}

	/**
	 * 
	 */
	public void deleteFolder() throws Exception {
		Folder f = getFolder();
		f.setSubscribed(false);
		closeFolder(f);
		f.delete(true);
	}

	/**
	 * @param newName
	 */
	public void renameFolder(String newName) throws Exception {
		Folder fOld = getFolder();
		Folder fNew = handler.getStore().getFolder(profile.getFolderNameSpace() + newName);
		closeFolder(fOld);
		fOld.renameTo(fNew);
		fNew.setSubscribed(true);
	}
}
