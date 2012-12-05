package org.claros.intouch.webmail.services;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.claros.commons.utility.MD5;
import org.claros.intouch.common.services.BaseService;
import org.claros.intouch.contacts.controllers.ContactsController;
import org.claros.intouch.preferences.controllers.UserPrefsController;
import org.claros.intouch.webmail.controllers.FolderController;
import org.claros.intouch.webmail.controllers.MailController;
import org.claros.intouch.webmail.factory.FolderControllerFactory;
import org.claros.intouch.webmail.factory.MailControllerFactory;
import org.claros.intouch.webmail.models.FolderDbObject;
import org.claros.intouch.webmail.models.MsgDbObject;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.zhyfoundry.crm.core.DIManager;
import com.zhyfoundry.crm.core.dao.Pager;
import com.zhyfoundry.crm.entity.Enterprise;
import com.zhyfoundry.crm.entity.MailSentInfo;
import com.zhyfoundry.crm.service.EnterpriseService;
import com.zhyfoundry.crm.web.controller.EnterpriseController;

import freemarker.template.Template;

public class SendMailService extends BaseService {

    /**
     *
     */
    private static final long serialVersionUID = -7451365138227115574L;
    private static Log log = LogFactory.getLog(SendMailService.class);

    /**
     * The doPost method of the servlet. <br>
     *
     * This method is called when a form has its tag value method equals to
     * post.
     *
     * @param request
     *            the request send by the client to the server
     * @param response
     *            the response send by the server to the client
     * @throws ServletException
     *             if an error occurred
     * @throws IOException
     *             if an error occurred
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setHeader("Expires", "-1");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-control", "no-cache");
        response.setHeader("Content-Type", "text/html; charset=utf-8");

        PrintWriter out = response.getWriter();

        try {
            /*
             * String charset = Constants.charset;
             *
             * String from = new
             * String(request.getParameter("from").getBytes(charset), "utf-8");
             * String to = new
             * String(request.getParameter("to").getBytes(charset), "utf-8");
             * String cc = new
             * String(request.getParameter("cc").getBytes(charset), "utf-8");
             * String bcc = new
             * String(request.getParameter("bcc").getBytes(charset), "utf-8");
             * String subject = new
             * String(request.getParameter("subject").getBytes(charset),
             * "utf-8"); String body= new
             * String(request.getParameter("body").getBytes(charset), "utf-8");
             */
            String from = request.getParameter("from");
            String to = request.getParameter("to");
            String cc = request.getParameter("cc");
            String bcc = request.getParameter("bcc");
            String subject = request.getParameter("subject");
            String body = request.getParameter("body");
            String requestReceiptNotification = request.getParameter("requestReceiptNotification");
            String priority = request.getParameter("priority");
            String sensitivity = request.getParameter("sensitivity");

            // learn the global charset setting.

            // learn user preferences from the DB.
            AuthProfile auth = getAuthProfile(request);

            String saveSentContacts = UserPrefsController.getUserSetting(auth, "saveSentContacts");
            if (saveSentContacts == null) {
                saveSentContacts = "yes";
            }

            boolean isEnterprise = request.getParameter("enterprise") != null;
            if (isEnterprise) {
                body = request.getParameter("composeBody");
            }

            // now create a new email object.
            Email email = new Email();
            EmailHeader header = new EmailHeader();

            Address adrs[] = Utility.stringToAddressArray(from);
            header.setFrom(adrs);

            if (!isEnterprise) {
                setTo(to, header, saveSentContacts, auth);
            }

            if (!isEnterprise && cc != null && cc.trim() != "") {
                Address ccs[] = Utility.stringToAddressArray(cc);
                header.setCc(ccs);
                if (saveSentContacts != null && saveSentContacts.equals("yes")) {
                    saveContacts(auth, ccs);
                }
            }
            if (!isEnterprise && bcc != null && bcc.trim() != "") {
                Address bccs[] = Utility.stringToAddressArray(bcc);
                header.setBcc(bccs);
                if (saveSentContacts != null && saveSentContacts.equals("yes")) {
                    saveContacts(auth, bccs);
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
            ArrayList attachments = (ArrayList) request.getSession().getAttribute("attachments");
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
            Smtp smtp = new Smtp(getConnectionProfile(request), getAuthProfile(request));

            if (isEnterprise) {
                List<MailSentInfo> mailSentInfos = new ArrayList<MailSentInfo>();
                Enterprise condition = (Enterprise) request.getSession().getAttribute(EnterpriseController.EMAIL_CONTIDION_OBJ);
                int i = 1, sentCnt = 0;
                int maxSentCnt = Integer.MAX_VALUE;
                if (StringUtils.isNotEmpty(request.getParameter("sendMailCountLimitation"))) {
                    try {
                        maxSentCnt = Integer.parseInt(request.getParameter("sendMailCountLimitation"));
                    } catch (NumberFormatException e) {
                        // TODO check input is number by using JS, and remove this catch.
                    }
                }
                Pager pager = new Pager(i++, 20);
                EnterpriseService enterpriseService = DIManager.getBean(EnterpriseService.class);
                List<Enterprise> enterprises = enterpriseService.getEnterprises(condition, pager);
                List<Integer> enterprisesId = new ArrayList<Integer>();
                // TODO send time limit(minute, hour, day)
                // TODO websocket or async
                // TODO basic statistic(total XXX, sucess XXX)
                while (!enterprises.isEmpty()) {
                    for (Enterprise o : enterprises) {
                        String enterpriseEmail = o.getEmail();
                        if (StringUtils.isBlank(enterpriseEmail)) {
                            continue;
                        }
                        boolean hasEnterpriseSent = false;
                        List<String> enterpriseEmails = splitEmails(enterpriseEmail);
                        for (String s : enterpriseEmails) {
                            boolean statusOK;
                            String statusMsg;
                            try {
                                setTo(s, email.getBaseHeader(), saveSentContacts, auth);
                                if (sentCnt >= maxSentCnt) {
                                    break;
                                }
                                email.getBaseHeader().setSubject(o.processDSL(subject));
                                ((EmailPart)email.getParts().get(0)).setContent(o.processDSL(body));
                                statusOK = sendMail(smtp, email, auth, request, o);
                                if (statusOK) {
                                    statusMsg = "成功";
                                    hasEnterpriseSent = true;
                                    sentCnt++;
                                } else {
                                    statusMsg = "失败";
                                }
                            } catch (Exception e) {
                                log.error(e.getMessage(), e);
                                statusOK = false;
                                statusMsg = "失败(" + e.getMessage() + ")";
                            }
                            mailSentInfos.add(new MailSentInfo(o.getName(), s, statusMsg));
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
                    pager = new Pager(i++, 20);
                    enterprises = enterpriseService.getEnterprises(condition, pager);
                }
                for (Integer id : enterprisesId) {
                    enterpriseService.increaseMailSentCount(id);
                }

                outputReport(mailSentInfos, out);
            } else {
                boolean statusOK = sendMail(smtp, email, auth, request, null);
                if (statusOK) {
                    out.print("ok");
                } else {
                    out.print("fail");
                }
            }
        } catch (Exception e) {
            out.print("fail");
            log.error(e.getMessage(), e);
        }
    }

    private void outputReport(List<MailSentInfo> mailSentInfos, PrintWriter out) {
        FreeMarkerConfigurer config = DIManager.getBean(FreeMarkerConfigurer.class);
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("mailSentInfo", mailSentInfos);
        try {
            Template t = new Template("", new StringReader("<#import \"macro/reportMailSent.ftl\" as report><@report.page />"), config.getConfiguration());
            t.process(model, out);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private List<String> splitEmails(String enterpriseEmail) {
        List<String> emails = new ArrayList<String>();
        String[] arr = enterpriseEmail.split(",|;|\\|");
        for (String s : arr) {
            if (StringUtils.isNotBlank(s)) {
                emails.add(s);
            }
        }
        return emails;
    }

    private void setTo(String to, EmailHeader header, Object saveSentContacts, AuthProfile auth) throws Exception {
        Address tos[] = Utility.stringToAddressArray(to);
        header.setTo(tos);
        if (saveSentContacts != null && saveSentContacts.equals("yes")) {
            saveContacts(auth, tos);
        }
    }

    private boolean sendMail(Smtp smtp, Email email, AuthProfile auth, HttpServletRequest request, Enterprise enterprise) throws Exception {
        HashMap sendRes = smtp.send(email, false);
        MimeMessage msg = (MimeMessage) sendRes.get("msg");

        // if we fail to send the message to any of the recepients
        // we should make a report about it to the user.
        Address[] sent = (Address[]) sendRes.get("sent");
        // Address[] fail = (Address[])sendRes.get("fail");
        // Address[] invalid = (Address[])sendRes.get("invalid");

        if (sent == null || sent.length == 0) {
            return false;
        } else {
            // if save to sent items enabled, save the sent mail.
            String saveEnabled = UserPrefsController.getUserSetting(auth, "saveSent");
            if (saveEnabled == null) {
                saveEnabled = "yes";
            }
            if (saveEnabled == null || saveEnabled.equals("yes")) {
                saveSentMail(auth, msg, request, enterprise);
            }
            return true;
        }
    }

    /**
     *
     * @param auth
     * @param adrs
     */
    private void saveContacts(AuthProfile auth, Address[] adrs) {
        try {
            if (adrs != null) {
                InternetAddress adr = null;
                for (int i = 0; i < adrs.length; i++) {
                    adr = (InternetAddress) adrs[i];
                    ContactsController.saveSenderFromAddr(auth, adr);
                }
            }
        } catch (Exception e) {
            log.debug("save contact failed.", e);
        }

    }

    /**
     *
     * @param auth
     * @param msg
     * @param request
     * @param enterprise
     * @throws Exception
     */
    private void saveSentMail(AuthProfile auth, MimeMessage msg, HttpServletRequest request, Enterprise enterprise) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        msg.writeTo(bos);
        byte bMsg[] = bos.toByteArray();

        // serialize the message byte array
        ObjectOutputStream os = new ObjectOutputStream(bos);
        os.writeObject(bMsg);

        // create an email db item
        MsgDbObject item = new MsgDbObject();
        item.setEmail(bMsg);
        String md5Header = new String(MD5.getHashString(bMsg)).toUpperCase(new Locale("en", "US"));

        ConnectionMetaHandler handler = getConnectionHandler(request);
        ConnectionProfile profile = getConnectionProfile(request);

        FolderControllerFactory factory = new FolderControllerFactory(auth, profile, handler);
        FolderController foldCont = factory.getFolderController();
        FolderDbObject fItem = foldCont.getSentItems();

        item.setUniqueId(md5Header);
        item.setMessageId(msg.getHeader("Message-ID", null));
        item.setFolderId(fItem.getId());
        item.setUnread(new Boolean(false));
        item.setUsername(auth.getUsername());
        item.setMsgSize(new Long(bMsg.length));
        if (enterprise != null) {
            item.setEnterpriseId(enterprise.getId());
        }

        // save the email db item.
        MailControllerFactory mailFact = new MailControllerFactory(auth, profile, handler, fItem.getFolderName());
        MailController mailCont = mailFact.getMailController(); // TODO if is IMAP, also save to DB.
        mailCont.appendEmail(item);
    }
}
