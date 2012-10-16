package com.zhyfoundry.crm.web.vo;

public class FatherVo {

    private String name;

    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(final Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "FatherVo [name=" + name + ", age=" + age + "]";
    }
}
