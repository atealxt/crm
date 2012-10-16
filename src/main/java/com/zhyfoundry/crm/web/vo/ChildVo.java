package com.zhyfoundry.crm.web.vo;

import org.springmodules.validation.bean.conf.loader.annotation.handler.Length;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotBlank;

public class ChildVo {

    @NotBlank
    @Length(max = 80)
    private String name;
    private Float money;
    private FatherVo father;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(final Float money) {
        this.money = money;
    }

    public FatherVo getFather() {
        return father;
    }

    public void setFather(final FatherVo father) {
        this.father = father;
    }

    @Override
    public String toString() {
        return "ChildVo [name=" + name + ", money=" + money + ", father=" + father + "]";
    }
}
