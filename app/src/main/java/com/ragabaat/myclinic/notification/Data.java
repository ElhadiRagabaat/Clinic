package com.ragabaat.myclinic.notification;

public class Data {

    private  String body,title ,sent;
    private Integer icon;

    public Data() {
    }

    public Data(String body, String title, String sent, Integer icon) {
        this.body = body;
        this.title = title;
        this.sent = sent;
        this.icon = icon;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSent() {
        return sent;
    }

    public void setSent(String sent) {
        this.sent = sent;
    }

    public Integer getIcon() {
        return icon;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
    }
}
