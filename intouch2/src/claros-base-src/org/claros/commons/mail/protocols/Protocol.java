package org.claros.commons.mail.protocols;

import java.util.ArrayList;

import javax.mail.Message;

import org.claros.commons.exception.SystemException;
import org.claros.commons.mail.exception.ConnectionException;
import org.claros.commons.mail.exception.MailboxActionException;
import org.claros.commons.mail.exception.ServerDownException;
import org.claros.commons.mail.models.ConnectionMetaHandler;

/**
 * @author Umut Gokbayrak
 */
public interface Protocol {
	public ConnectionMetaHandler connect(int connectType) throws SystemException, ConnectionException, ServerDownException;
	public void disconnect();
	public ArrayList fetchAllHeaders() throws SystemException, ConnectionException;
	public ArrayList fetchAllHeadersAsMessages() throws SystemException, ConnectionException;
	public ConnectionMetaHandler deleteMessages(int messageIds[]) throws MailboxActionException, SystemException, ConnectionException;
	public Message getMessage(int messageId) throws MailboxActionException, SystemException, ConnectionException, Exception;
	public void emptyFolder() throws Exception;
	public int getTotalMessageCount() throws Exception;
	public int getUnreadMessageCount() throws Exception;
	public void flagAsDeleted(int[] ids) throws Exception;
}
