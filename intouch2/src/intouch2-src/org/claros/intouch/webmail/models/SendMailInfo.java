package org.claros.intouch.webmail.models;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.claros.commons.auth.models.AuthProfile;
import org.claros.commons.mail.models.ConnectionMetaHandler;
import org.claros.commons.mail.models.ConnectionProfile;
import org.claros.intouch.common.services.BaseService;

import com.zhyfoundry.crm.entity.Enterprise;
import com.zhyfoundry.crm.web.controller.EnterpriseController;

public class SendMailInfo {

    protected Log logger = LogFactory.getLog(getClass());

    private final String from;
    private final String to;
    private final String cc;
    private final String bcc;
    private final String subject;
    private String body;
    private final String requestReceiptNotification;
    private final String priority;
    private final String sensitivity;
    private final boolean isEnterprise;
    private final ArrayList attachments;
    private final ConnectionProfile connectionProfile;
    private final AuthProfile authProfile;
    private final Enterprise condition;
    private final String conditionOrder;
    private int maxSentCnt = Integer.MAX_VALUE;
    private int sendLimitMinute = -1;
    private int sendLimitHour = -1;
    private int secondsToWaitingForMailSend = -1;
    private int secondsToWaitingForMailSendFail = -1;
    private final ConnectionMetaHandler connectionMetaHandler;

    public SendMailInfo(String form, AuthProfile authProfile, ArrayList attachments,
            ConnectionProfile connectionProfile, Enterprise condition, String conditionOrder,
            ConnectionMetaHandler connectionMetaHandler) {
        logger.debug(form);
        String[] parameters = form.split("&");
        Map<String, String> map = new HashMap<String, String>();
        for (String s : parameters) {
            int idxEq = s.indexOf("=");
            if (idxEq == -1) {
                continue;
            }
            if (idxEq == s.length() - 1) {
                try {
                    map.put(URLDecoder.decode(s.substring(0, idxEq), "utf-8"), "");
                } catch (UnsupportedEncodingException e) {
                    logger.error(e.getMessage(), e);
                }
            } else {
                try {
                    map.put(URLDecoder.decode(s.substring(0, idxEq), "utf-8"),
                            URLDecoder.decode(s.substring(idxEq + 1), "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        from = map.get("from");
        to = map.get("to");
        cc = map.get("cc");
        bcc = map.get("bcc");
        subject = map.get("subject");
        body = map.get("body");
        requestReceiptNotification = map.get("requestReceiptNotification");
        priority = map.get("priority");
        sensitivity = map.get("sensitivity");
        isEnterprise = map.get("enterprise") != null;
        if (isEnterprise) {
            body = map.get("composeBody");
        }
        if (StringUtils.isNotEmpty(map.get("sendMailCountLimitation"))) {
            try {
                maxSentCnt = Integer.parseInt(map.get("sendMailCountLimitation"));
            } catch (NumberFormatException e) {
                logger.error(e.getMessage(), e);
            }
        }
        if (StringUtils.isNotEmpty(map.get("secondsToWaitingForMailSend"))) {
            try {
                secondsToWaitingForMailSend = Integer.parseInt(map.get("secondsToWaitingForMailSend"));
            } catch (NumberFormatException e) {
                logger.error(e.getMessage(), e);
            }
        }
        if (StringUtils.isNotEmpty(map.get("secondsToWaitingForMailSendFail"))) {
            try {
                secondsToWaitingForMailSendFail = Integer.parseInt(map.get("secondsToWaitingForMailSendFail"));
            } catch (NumberFormatException e) {
                logger.error(e.getMessage(), e);
            }
        }
        if (StringUtils.isNotEmpty(map.get("sendLimitMinute"))) {
            try {
                sendLimitMinute = Integer.parseInt(map.get("sendLimitMinute"));
            } catch (NumberFormatException e) {
                logger.error(e.getMessage(), e);
            }
        }
        if (StringUtils.isNotEmpty(map.get("sendLimitHour"))) {
            try {
                sendLimitHour = Integer.parseInt(map.get("sendLimitHour"));
            } catch (NumberFormatException e) {
                logger.error(e.getMessage(), e);
            }
        }
        this.authProfile = authProfile;
        this.attachments = attachments;
        this.connectionProfile = connectionProfile;
        this.condition = condition;
        this.conditionOrder = conditionOrder;
        this.connectionMetaHandler = connectionMetaHandler;
    }

    public SendMailInfo(HttpServletRequest request) {
        from = request.getParameter("from");
        to = request.getParameter("to");
        cc = request.getParameter("cc");
        bcc = request.getParameter("bcc");
        subject = request.getParameter("subject");
        body = request.getParameter("body");
        requestReceiptNotification = request.getParameter("requestReceiptNotification");
        priority = request.getParameter("priority");
        sensitivity = request.getParameter("sensitivity");
        authProfile = BaseService.getAuthProfile(request);
        isEnterprise = request.getParameter("enterprise") != null;
        if (isEnterprise) {
            body = request.getParameter("composeBody");
        }
        attachments = (ArrayList) request.getSession().getAttribute("attachments");
        connectionProfile = BaseService.getConnectionProfile(request);
        condition = (Enterprise) request.getSession().getAttribute(EnterpriseController.EMAIL_CONTIDION_OBJ);
        conditionOrder = (String) request.getSession().getAttribute(EnterpriseController.EMAIL_CONTIDION_ORDER);
        if (StringUtils.isNotEmpty(request.getParameter("sendMailCountLimitation"))) {
            try {
                maxSentCnt = Integer.parseInt(request.getParameter("sendMailCountLimitation"));
            } catch (NumberFormatException e) {
                logger.error(e.getMessage(), e);
            }
        }
        if (StringUtils.isNotEmpty(request.getParameter("secondsToWaitingForMailSend"))) {
            try {
                secondsToWaitingForMailSend = Integer.parseInt(request.getParameter("secondsToWaitingForMailSend"));
            } catch (NumberFormatException e) {
                logger.error(e.getMessage(), e);
            }
        }
        if (StringUtils.isNotEmpty(request.getParameter("secondsToWaitingForMailSendFail"))) {
            try {
                secondsToWaitingForMailSendFail = Integer.parseInt(request
                        .getParameter("secondsToWaitingForMailSendFail"));
            } catch (NumberFormatException e) {
                logger.error(e.getMessage(), e);
            }
        }
        if (StringUtils.isNotEmpty(request.getParameter("sendLimitMinute"))) {
            try {
                sendLimitMinute = Integer.parseInt(request.getParameter("sendLimitMinute"));
            } catch (NumberFormatException e) {
                logger.error(e.getMessage(), e);
            }
        }
        if (StringUtils.isNotEmpty(request.getParameter("sendLimitHour"))) {
            try {
                sendLimitHour = Integer.parseInt(request.getParameter("sendLimitHour"));
            } catch (NumberFormatException e) {
                logger.error(e.getMessage(), e);
            }
        }
        connectionMetaHandler = BaseService.getConnectionHandler(request);
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getCc() {
        return cc;
    }

    public String getBcc() {
        return bcc;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public String getRequestReceiptNotification() {
        return requestReceiptNotification;
    }

    public String getPriority() {
        return priority;
    }

    public String getSensitivity() {
        return sensitivity;
    }

    public boolean isEnterprise() {
        return isEnterprise;
    }

    public ArrayList getAttachments() {
        return attachments;
    }

    public ConnectionProfile getConnectionProfile() {
        return connectionProfile;
    }

    public AuthProfile getAuthProfile() {
        return authProfile;
    }

    public Enterprise getCondition() {
        return condition;
    }

    public String getConditionOrder() {
        return conditionOrder;
    }

    public int getMaxSentCnt() {
        return maxSentCnt;
    }

    public int getSecondsToWaitingForMailSendFail() {
        return secondsToWaitingForMailSendFail;
    }

    public ConnectionMetaHandler getConnectionMetaHandler() {
        return connectionMetaHandler;
    }

    public int getSendLimitMinute() {
        return sendLimitMinute;
    }

    public int getSendLimitHour() {
        return sendLimitHour;
    }

    public int getSecondsToWaitingForMailSend() {
        return secondsToWaitingForMailSend;
    }
}
