package org.claros.commons.mail.utility;

import javax.mail.Folder;

import org.claros.commons.mail.models.ConnectionProfile;


/**
 * @author Umut Gokbayrak
 *
 */
public class Constants {
    public static final String POP3 = "pop3";
	public static final String IMAP = "imap";
	

	public static final int CONNECTION_READ_ONLY = Folder.READ_ONLY;
	public static final int CONNECTION_READ_WRITE = Folder.READ_WRITE;


    private static final String STR_FOLDER_INBOX = "INBOX";
	private static final String STR_FOLDER_JUNK = "Junk Mail";
	private static final String STR_FOLDER_SENT = "Sent Mail";
	private static final String STR_FOLDER_TRASH = "Trash";
	private static final String STR_FOLDER_DRAFTS = "Drafts";

	/**
	 * 
	 * @param profile
	 * @return
	 */
	public static String FOLDER_INBOX(ConnectionProfile profile) {
		return STR_FOLDER_INBOX;
	}
	
	/**
	 * 
	 * @param profile
	 * @return
	 */
	public static String FOLDER_JUNK(ConnectionProfile profile) {
		if (profile.getProtocol().equals(IMAP)) {
			return profile.getFolderNameSpace() + STR_FOLDER_JUNK;
		}
		return STR_FOLDER_JUNK;
	}

	/**
	 * 
	 * @param profile
	 * @return
	 */
	public static String FOLDER_SENT(ConnectionProfile profile) {
		if (profile.getProtocol().equals(IMAP)) {
			return profile.getFolderNameSpace() + STR_FOLDER_SENT;
		}
		return STR_FOLDER_SENT;
	}

	/**
	 * 
	 * @param profile
	 * @return
	 */
	public static String FOLDER_TRASH(ConnectionProfile profile) {
		if (profile.getProtocol().equals(IMAP)) {
			return profile.getFolderNameSpace() + STR_FOLDER_TRASH;
		}
		return STR_FOLDER_TRASH;
	}

	/**
	 * 
	 * @param profile
	 * @return
	 */
	public static String FOLDER_DRAFTS(ConnectionProfile profile) {
		if (profile.getProtocol().equals(IMAP)) {
			return profile.getFolderNameSpace() + STR_FOLDER_DRAFTS;
		}
		return STR_FOLDER_DRAFTS;
	}
}
