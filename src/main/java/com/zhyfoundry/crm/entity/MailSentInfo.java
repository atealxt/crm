package com.zhyfoundry.crm.entity;

public class MailSentInfo {

    private String name;
    private String mail;
    private boolean success;
    private String status;

    public MailSentInfo(String name, String mail, String status) {
        super();
        this.name = name;
        this.mail = mail;
        this.status = status;
    }

    public MailSentInfo(String name, String mail, boolean success, String status) {
        super();
        this.name = name;
        this.mail = mail;
        this.success = success;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
