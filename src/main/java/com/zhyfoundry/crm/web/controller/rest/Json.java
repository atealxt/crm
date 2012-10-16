package com.zhyfoundry.crm.web.controller.rest;

import java.util.Date;

public class Json {

    private int id;
    private String name;
    private Date registerTime;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(final Date registerTime) {
        this.registerTime = registerTime;
    }

    @Override
    public String toString() {
        return "Json [id=" + id + ", name=" + name + ", registerTime=" + registerTime + "]";
    }

}
