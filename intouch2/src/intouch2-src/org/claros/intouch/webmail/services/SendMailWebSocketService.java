package org.claros.intouch.webmail.services;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.claros.commons.auth.models.AuthProfile;
import org.claros.commons.mail.models.ByteArrayDataSource;
import org.claros.commons.mail.models.ConnectionMetaHandler;
import org.claros.commons.mail.models.ConnectionProfile;
import org.claros.commons.mail.models.Email;
import org.claros.commons.mail.models.EmailHeader;
import org.claros.commons.mail.models.EmailPart;
import org.claros.commons.mail.protocols.Smtp;
import org.claros.commons.mail.utility.Utility;
import org.claros.intouch.common.services.BaseService;
import org.claros.intouch.preferences.controllers.UserPrefsController;
import org.claros.intouch.webmail.models.SendMailInfo;

import com.zhyfoundry.crm.core.DIManager;
import com.zhyfoundry.crm.core.dao.Pager;
import com.zhyfoundry.crm.entity.Enterprise;
import com.zhyfoundry.crm.entity.MailSentInfo;
import com.zhyfoundry.crm.service.EnterpriseService;
import com.zhyfoundry.crm.web.controller.EnterpriseController;

public class SendMailWebSocketService extends WebSocketServlet {

    private static final long serialVersionUID = 1179944282465605081L;
    protected Log log = LogFactory.getLog(getClass());

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

        /** @see SendMailService#executeSendMail(SendMailInfo) */
        @Override
        protected void onTextMessage(CharBuffer message) throws IOException {
            String form = message.toString();
            SendMailInfo sendMailInfo = new SendMailInfo(form, authProfile, attachments, connectionProfile, condition, conditionOrder, connectionMetaHandler);

            try {
                String from = sendMailInfo.getFrom();
                String to = sendMailInfo.getTo();
                String cc = sendMailInfo.getCc();
                String bcc = sendMailInfo.getBcc();
                String subject = sendMailInfo.getSubject();
                String body = sendMailInfo.getBody();
                String requestReceiptNotification = sendMailInfo.getRequestReceiptNotification();
                String priority = sendMailInfo.getPriority();
                String sensitivity = sendMailInfo.getSensitivity();

                // learn the global charset setting.

                // learn user preferences from the DB.
                AuthProfile auth = sendMailInfo.getAuthProfile();

                String saveSentContacts = UserPrefsController.getUserSetting(auth, "saveSentContacts");
                if (saveSentContacts == null) {
                    saveSentContacts = "yes";
                }

                boolean isEnterprise = sendMailInfo.isEnterprise();

                // now create a new email object.
                Email email = new Email();
                EmailHeader header = new EmailHeader();

                Address adrs[] = Utility.stringToAddressArray(from);
                header.setFrom(adrs);

                if (!isEnterprise) {
                    SendMailService.setTo(to, header, saveSentContacts, auth);
                }

                if (!isEnterprise && cc != null && cc.trim() != "") {
                    Address ccs[] = Utility.stringToAddressArray(cc);
                    header.setCc(ccs);
                    if (saveSentContacts != null && saveSentContacts.equals("yes")) {
                        SendMailService.saveContacts(auth, ccs);
                    }
                }
                if (!isEnterprise && bcc != null && bcc.trim() != "") {
                    Address bccs[] = Utility.stringToAddressArray(bcc);
                    header.setBcc(bccs);
                    if (saveSentContacts != null && saveSentContacts.equals("yes")) {
                        SendMailService.saveContacts(auth, bccs);
                    }
                }
                header.setSubject(subject);
                header.setDate(new Date());

                String replyTo = UserPrefsController.getUserSetting(auth, "replyTo");
                if (replyTo != null && replyTo.trim().length() != 0) {
                    header.setReplyTo(new Address[] { new InternetAddress(replyTo) });
                }

                if (requestReceiptNotification != null && requestReceiptNotification.equals("1")) {
                    header.setRequestReceiptNotification(Boolean.valueOf(true));
                }

                if (priority != null) {
                    header.setPriority(Short.valueOf(priority).shortValue());
                }

                if (sensitivity != null) {
                    header.setSensitivity(Short.valueOf(sensitivity).shortValue());
                }

                email.setBaseHeader(header);

                ArrayList parts = new ArrayList();
                EmailPart bodyPart = new EmailPart();
                bodyPart.setContentType("text/html; charset=UTF-8");
                /*
                 * HtmlCleaner cleaner = new HtmlCleaner(body);
                 * cleaner.clean(false,false);
                 */
                bodyPart.setContent(body);
                parts.add(0, bodyPart);

                // attach some files...
                ArrayList attachments = sendMailInfo.getAttachments();
                if (attachments != null) {
                    List newLst = new ArrayList();
                    EmailPart tmp = null;
                    for (int i = 0; i < attachments.size(); i++) {
                        try {
                            tmp = (EmailPart) attachments.get(i);
                            String disp = tmp.getDisposition();
                            File f = new File(disp);
                            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
                            byte data[] = new byte[(int) f.length() + 2];
                            bis.read(data);
                            bis.close();

                            MimeBodyPart bp = new MimeBodyPart();
                            DataSource ds = new ByteArrayDataSource(data, tmp.getContentType(), tmp.getFilename());
                            bp.setDataHandler(new DataHandler(ds));
                            bp.setDisposition("attachment; filename=\"" + tmp.getFilename() + "\"");
                            tmp.setDisposition(bp.getDisposition());
                            bp.setFileName(tmp.getFilename());
                            tmp.setDataSource(ds);
                            tmp.setContent(bp.getContent());
                            newLst.add(tmp);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    parts.addAll(newLst);
                }
                email.setParts(parts);

                // it is time to send the email object message
                Smtp smtp = new Smtp(sendMailInfo.getConnectionProfile(), sendMailInfo.getAuthProfile());

                Enterprise condition = sendMailInfo.getCondition();
                int i = 1, sentCnt = 0;
                int maxSentCnt = sendMailInfo.getMaxSentCnt();
                Pager pager = new Pager(i++, 20, sendMailInfo.getConditionOrder());
                EnterpriseService enterpriseService = DIManager.getBean(EnterpriseService.class);
                List<Enterprise> enterprises = enterpriseService.getEnterprises(condition, pager);
                List<Integer> enterprisesId = new ArrayList<Integer>();
                Queue<Long> sendLimitMinute = new LinkedList<Long>();
                Queue<Long> sendLimitHour = new LinkedList<Long>();
                output("log: 邮件发送 开始");
                while (!enterprises.isEmpty()) {
                    for (Enterprise o : enterprises) {
                        String enterpriseEmail = o.getEmail();
                        if (StringUtils.isBlank(enterpriseEmail)) {
                            continue;
                        }
                        boolean hasEnterpriseSent = false;
                        List<String> enterpriseEmails = SendMailService.splitEmails(enterpriseEmail);
                        for (String eml : enterpriseEmails) {
                            long timeToSleep = checkSendLimit(sendLimitMinute, 60 * 1000, sendMailInfo.getSendLimitMinute());
                            if (timeToSleep != -1) {
                                log.debug("已达每分钟邮件发送最大数量限制，休眠" + timeToSleep + "毫秒");
                                output("log:已达每分钟邮件发送最大数量限制，休眠" + timeToSleep + "毫秒");
                                Thread.sleep(timeToSleep);
                            }
                            timeToSleep = checkSendLimit(sendLimitHour, 60 * 60 * 1000, sendMailInfo.getSendLimitHour());
                            if (timeToSleep != -1) {
                                log.debug("已达每小时邮件发送最大数量限制，休眠" + timeToSleep + "毫秒");
                                output("log:已达每小时邮件发送最大数量限制，休眠" + timeToSleep + "毫秒");
                                Thread.sleep(timeToSleep);
                            }
                            boolean statusOK;
                            String statusMsg;
                            try {
                                SendMailService.setTo(eml, email.getBaseHeader(), saveSentContacts, auth);
                                if (sentCnt >= maxSentCnt) {
                                    break;
                                }
                                email.getBaseHeader().setSubject(o.processDSL(subject));
                                ((EmailPart)email.getParts().get(0)).setContent(o.processDSL(body));
                                statusOK = SendMailService.sendMail(smtp, email, auth, sendMailInfo.getConnectionMetaHandler(), sendMailInfo.getConnectionProfile(), o);
                                sentCnt++;
                                if (statusOK) {
                                    statusMsg = "成功";
                                    hasEnterpriseSent = true;
                                } else {
                                    statusMsg = "失败";
                                }
                            } catch (Exception e) {
                                log.error(e.getMessage(), e);
                                statusOK = false;
                                statusMsg = "失败(" + (e.getMessage() != null ? e.getMessage() : e.toString()) + ")";
                            }
                            output(JSONObject.fromObject(new MailSentInfo(o.getName(), eml,statusOK, statusMsg)).toString());
                            if (!statusOK && sendMailInfo.getSecondsToWaitingForMailSendFail() > 0) {
                                log.debug(eml + " 发送失败，休眠" + sendMailInfo.getSecondsToWaitingForMailSendFail() + "秒");
                                output("log:" + eml + " 发送失败，休眠" + sendMailInfo.getSecondsToWaitingForMailSendFail() + "秒");
                                Thread.sleep(sendMailInfo.getSecondsToWaitingForMailSendFail() * 1000);
                            }
                            if (sendMailInfo.getSecondsToWaitingForMailSend() > 0) {
                                log.debug(eml + " 发送完毕，休眠" + sendMailInfo.getSecondsToWaitingForMailSend() + "秒");
                                output("log:" + eml + " 发送完毕，休眠" + sendMailInfo.getSecondsToWaitingForMailSend() + "秒");
                                Thread.sleep(sendMailInfo.getSecondsToWaitingForMailSend() * 1000);
                            }
                        }
                        if (hasEnterpriseSent) {
                            enterprisesId.add(o.getId());
                        }
                        if (sentCnt >= maxSentCnt) {
                            break;
                        }
                    }
                    if (sentCnt >= maxSentCnt) {
                        break;
                    }
                    pager = new Pager(i++, 20, sendMailInfo.getConditionOrder());
                    enterprises = enterpriseService.getEnterprises(condition, pager);
                }
                for (Integer id : enterprisesId) {
                    enterpriseService.increaseMailSentCount(id);
                }
                output("log: 邮件发送 结束");
                output("success");
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                output("System Error (" + (e.getMessage() != null ? e.getMessage() : e.toString()) + ")");
            }
        }

        private long checkSendLimit(Queue<Long> timestamps, int during, int sendLimit) {
            if (sendLimit == -1) {
                return -1;
            }
            long now = System.currentTimeMillis();
            if (timestamps.size() == 0) {
                timestamps.add(now);
                return -1;
            }
            long first = timestamps.peek();
            if ((now - first) >= during) {
                timestamps.poll();
                timestamps.add(now);
                return -1;
            }
            if (timestamps.size() < sendLimit) {
                timestamps.add(now);
                return -1;
            }
            timestamps.poll();
            timestamps.add(now);
            return during - (now - first);
        }

        private void output(String message) {
            CharBuffer buffer = CharBuffer.wrap(message);
            try {
                this.getWsOutbound().writeTextMessage(buffer);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}
