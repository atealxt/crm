package org.claros.intouch.webmail.services;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.claros.commons.auth.models.AuthProfile;
import org.claros.commons.mail.models.ConnectionMetaHandler;
import org.claros.commons.mail.models.ConnectionProfile;
import org.claros.intouch.common.services.BaseService;
import org.claros.intouch.webmail.models.SendMailInfo;

import com.zhyfoundry.crm.entity.Enterprise;
import com.zhyfoundry.crm.web.controller.EnterpriseController;

public class SendMailWebSocketService extends WebSocketServlet {

    private static final long serialVersionUID = 1179944282465605081L;
    protected Log logger = LogFactory.getLog(getClass());

    @Override
    protected StreamInbound createWebSocketInbound(String subProtocol, HttpServletRequest request) {
        return new SendMailInbound(request);
    }

    class SendMailInbound extends MessageInbound {

        private final AuthProfile authProfile;
        private final ArrayList attachments;
        private final ConnectionProfile connectionProfile;
        private final Enterprise condition;
        private final String conditionOrder;
        private final ConnectionMetaHandler connectionMetaHandler;

        public SendMailInbound(HttpServletRequest request) {
            super();
            authProfile = BaseService.getAuthProfile(request);
            attachments = (ArrayList) request.getSession().getAttribute("attachments");
            connectionProfile = BaseService.getConnectionProfile(request);
            condition = (Enterprise) request.getSession().getAttribute(EnterpriseController.EMAIL_CONTIDION_OBJ);
            conditionOrder = (String) request.getSession().getAttribute(EnterpriseController.EMAIL_CONTIDION_ORDER);
            connectionMetaHandler = BaseService.getConnectionHandler(request);
        }

        @Override
        protected void onBinaryMessage(ByteBuffer message) throws IOException {
            throw new UnsupportedOperationException("Binary message not supported.");
        }

        @Override
        protected void onTextMessage(CharBuffer message) throws IOException {
            String form = message.toString();
            String output = SendMailService.executeSendMail(new SendMailInfo(form, authProfile, attachments, connectionProfile, condition, conditionOrder, connectionMetaHandler));
            // TODO dynamic
            output(output);
        }

        private void output(String message) {
            CharBuffer buffer = CharBuffer.wrap(message);
            try {
                this.getWsOutbound().writeTextMessage(buffer);
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}
