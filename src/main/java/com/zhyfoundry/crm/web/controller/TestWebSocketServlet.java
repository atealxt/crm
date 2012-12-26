package com.zhyfoundry.crm.web.controller;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestWebSocketServlet extends WebSocketServlet {

    private static final long serialVersionUID = 6728679898667731008L;
    protected Log logger = LogFactory.getLog(getClass());

    @Override
    protected StreamInbound createWebSocketInbound(String subProtocol, HttpServletRequest request) {
        return new TestInbound();
    }

    class TestInbound extends MessageInbound {

        @Override
        protected void onBinaryMessage(ByteBuffer message) throws IOException {
            throw new UnsupportedOperationException("Binary message not supported.");
        }

        @Override
        protected void onTextMessage(CharBuffer message) throws IOException {
            String msg = message.toString();
            if (logger.isDebugEnabled()) {
                logger.debug(msg);
            }
            // modify the message by adding a timestamp
            msg = "(" + System.currentTimeMillis() + ") " + msg;
            broadcast(msg);
        }

        private void broadcast(String message) {
            CharBuffer buffer = CharBuffer.wrap(message);
            try {
                this.getWsOutbound().writeTextMessage(buffer);
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }

        @Override
        public int getReadTimeout() {
            return -1;
        }
    }
}
