package org.claros.commons.mail.protocols;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.claros.commons.auth.models.AuthProfile;
import org.claros.commons.configuration.PropertyFile;
import org.claros.commons.mail.models.ByteArrayDataSource;
import org.claros.commons.mail.models.ConnectionProfile;
import org.claros.commons.mail.models.Email;
import org.claros.commons.mail.models.EmailPart;
import org.claros.commons.mail.models.EmailPriority;
import org.claros.commons.mail.models.EmailSensitivity;
import org.claros.commons.mail.utility.SmtpAuthenticator;

import com.sun.mail.smtp.SMTPMessage;

/**
 * @author Umut Gokbayrak
 *
 */
public class Smtp {

	private Session session = null;

	public Smtp(ConnectionProfile profile, AuthProfile auth) {
		Properties props = new Properties();
		props.put("mail.smtp.host", profile.getSmtpServer());
		props.put("mail.smtp.port", Integer.toString(profile.getISmtpPort()));
		
		if (profile.getSmtpAuthenticated() != null && profile.getSmtpAuthenticated().equals("true")) {
			props.setProperty("mail.smtp.auth", "true");
			SmtpAuthenticator authenticator = new SmtpAuthenticator(auth.getUsername(), auth.getPassword());
			session = Session.getInstance(props, authenticator);
		} else {
			session = Session.getInstance(props, null);
		}
	}

	public HashMap send(Email msg, boolean simulate) throws Exception {
		Address from = msg.getBaseHeader().getFrom()[0];
		Address[] to = msg.getBaseHeader().getTo();
		Address[] cc = msg.getBaseHeader().getCc();
		Address[] bcc = msg.getBaseHeader().getBcc();
		Address[] replyTo = msg.getBaseHeader().getReplyTo();
		Boolean requestReceiptNotification = msg.getBaseHeader().getRequestReceiptNotification();
		short priority = msg.getBaseHeader().getPriority();
		short sensitivity = msg.getBaseHeader().getSensitivity();

		SMTPMessage mimeMsg = new SMTPMessage(session);
		String subject = msg.getBaseHeader().getSubject();
		
		mimeMsg.setFrom(from);
		if (to != null) {
			mimeMsg.setRecipients(Message.RecipientType.TO, to);
		}
		if (cc != null) {
			mimeMsg.setRecipients(Message.RecipientType.CC, cc);
		}
		if (bcc != null) {
			mimeMsg.setRecipients(Message.RecipientType.BCC, bcc);
		}
		if (replyTo != null) {
			mimeMsg.setReplyTo(replyTo);
		}

		mimeMsg.setSentDate(new Date());
		if (subject == null || subject.length() == 0) {
			subject = "No subject";
		}

		if(requestReceiptNotification!=null){
			mimeMsg.addHeader("Disposition-Notification-To", from.toString());
		}
		
		if(priority > 0){
			mimeMsg.addHeader("X-Priority", String.valueOf(priority));
			mimeMsg.addHeader("X-MSMail-Priority", EmailPriority.toStringValue(priority));
		}

		if(sensitivity > 0){
			mimeMsg.addHeader("Sensitivity", EmailSensitivity.toStringValue(sensitivity));
		}

		String charset = PropertyFile.getConfiguration("/config/config.xml").getString("common-params.charset");
		
		mimeMsg.setSubject(MimeUtility.encodeText(subject,charset,null));
		ArrayList parts = msg.getParts();
		EmailPart bodyPart = (EmailPart)parts.get(0);

		boolean isTextBody = (bodyPart.isHTMLText()) ? false : true;

		if (parts.size() == 1 && isTextBody) {
			mimeMsg.setText((String)bodyPart.getContent(), charset);
		} else {
			BodyPart bp = new MimeBodyPart();
			bp.setContent((String)bodyPart.getContent(), bodyPart.getContentType());
			bp.setHeader("Content-Type", bodyPart.getContentType());

			// attachments are added here. 
			MimeMultipart multipart = new MimeMultipart();
			multipart.addBodyPart(bp);

			// text body is added if it is a HTML message.
			/*
			if (!isTextBody) {
				BodyPart bp2 = new MimeBodyPart();
				bp2.setHeader("Content-Type", "text/plain; charset=" + charset);
				String txtBody = (String)bodyPart.getContent();
				txtBody = Utility.replaceAllOccurances(txtBody, "<br />", "\n");
				txtBody = Utility.replaceAllOccurances(txtBody, "<p>", "\n");
				txtBody = Utility.replaceAllOccurances(txtBody, "</p>", "\n");
				txtBody = Utility.replaceAllOccurances(txtBody, "&nbsp;", " ");
				txtBody = Utility.replaceAllOccurances(txtBody, "&uuml;", "�");
				txtBody = Utility.replaceAllOccurances(txtBody, "&Uuml;", "Ü");
				txtBody = Utility.replaceAllOccurances(txtBody, "&ccedil;", "�");
				txtBody = Utility.replaceAllOccurances(txtBody, "&Ccedil;", "Ç");
				txtBody = Utility.replaceAllOccurances(txtBody, "&ouml;", "ö");
				txtBody = Utility.replaceAllOccurances(txtBody, "&Ouml;", "Ö");
				bp2.setContent(org.claros.commons.mail.utility.Utility.stripHTMLTags(txtBody), "text/plain; charset=" + charset);
				multipart.addBodyPart(bp2);
			}
			*/

			// other attachments will follow
			MimeBodyPart attPart = null;
			EmailPart myPart = null;
			DataSource ds = null;
			String tmpContType = null;
			int pos = -1;
			for (int i=1; i < msg.getParts().size(); i++) {
				myPart = (EmailPart)msg.getParts().get(i);
				attPart = new MimeBodyPart();
				
				ds = myPart.getDataSource();
				if (ds == null) {
					if (myPart.getContent() instanceof ByteArrayOutputStream) {
						ByteArrayOutputStream bos = (ByteArrayOutputStream)myPart.getContent();
						ds = new ByteArrayDataSource(bos.toByteArray(), myPart.getContentType(), myPart.getFileName());
						attPart.setDataHandler(new DataHandler(ds));
						bos.close();
					} else if (myPart.getContent() instanceof ByteArrayInputStream) {
						ByteArrayInputStream bis = (ByteArrayInputStream)myPart.getContent();
						ByteArrayOutputStream bos = new ByteArrayOutputStream();
						int j = -1;
						while ((j = bis.read()) != -1) {
							bos.write(j);
						}
						ds = new ByteArrayDataSource(bos.toByteArray(), myPart.getContentType(), myPart.getFileName());
						attPart.setDataHandler(new DataHandler(ds));
						bos.close();
						bis.close();
					} else {
						attPart.setContent(myPart.getContent(), myPart.getContentType());
					}
				} else {
					attPart.setDataHandler(new DataHandler(ds));
				}
				
				attPart.setDisposition(myPart.getDisposition());
				attPart.setFileName(MimeUtility.encodeText(myPart.getFilename(),charset,null));
				
				tmpContType = (myPart.getContentType() == null) ? "application/octet-stream" : myPart.getContentType();
				
				pos = tmpContType.indexOf(";");
				if (pos >= 0) {
					tmpContType = tmpContType.substring(0, pos);
				}
				attPart.setHeader("Content-Type", tmpContType);
				multipart.addBodyPart(attPart);
			}

			// setting the content and finished
			mimeMsg.setContent(multipart);
		}
		mimeMsg.addHeader("X-Mailer", "Claros inTouch (http://www.claros.org)");
		mimeMsg.saveChanges();

		// we are sending the message and generating a sent report on the fly.
		HashMap out = new HashMap();
		out.put("msg", mimeMsg);
		if (!simulate) {
			try {
				mimeMsg.setSendPartial(true);
				mimeMsg.setSentDate(new Date());
				Transport.send(mimeMsg);
				Address[] sent = mimeMsg.getAllRecipients();
				out.put("sent", sent);
			} catch (SendFailedException se) {
				Address[] sent = se.getValidSentAddresses();
				Address[] invalid = se.getInvalidAddresses();
				Address[] fail = se.getValidUnsentAddresses();
				out.put("sent", sent);
				out.put("invalid", invalid);
				out.put("fail", fail);
			}
		}
		return out;
	}
}
