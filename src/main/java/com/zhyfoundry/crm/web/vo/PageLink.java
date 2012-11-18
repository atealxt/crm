package com.zhyfoundry.crm.web.vo;

public class PageLink {

    private String title;
    private String link;
    private String msgShow;
    private String target = "main";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getMsgShow() {
        return msgShow;
    }

    public void setMsgShow(String msgShow) {
        this.msgShow = msgShow;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return "PageLink [link=" + link + ", msgShow=" + msgShow + ", title=" + title + "]";
    }
}
