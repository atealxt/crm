package com.zhyfoundry.crm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @hibernate.class table="child"
 */
@Entity
@Table(name = "child")
public class Child implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private Float money;
    private Father father;

    /**
     * @hibernate.id generator-class="uuid.hex" column="id" length="32"
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return this.id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * @hibernate.property column="name" length="32" not-null="true" type="java.lang.String" lazy="true"
     */
    @Column(name = "name")
    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Column(name = "money", precision = 10)
    public Float getMoney() {
        return money;
    }

    public void setMoney(final Float money) {
        this.money = money;
    }

    /**
     * @hibernate.many-to-one column="father_id" not-null="true"
     */
    @ManyToOne(optional = true)
    @JoinColumn(name = "father_id")
    public Father getFather() {
        return this.father;
    }

    public void setFather(final Father father) {
        this.father = father;
    }

    @Override
    public String toString() {
        return "Child [id=" + id + ", name=" + name + "]";
    }
}
