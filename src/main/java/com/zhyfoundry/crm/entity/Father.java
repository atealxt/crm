package com.zhyfoundry.crm.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @hibernate.class table="father"
 */
@Entity
@Table(name = "father")
public class Father implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private Integer age;

    private Set<Child> children = new HashSet<Child>(0);

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
     * @hibernate.property column="name" length="32" not-null="true" type="java.lang.String"
     */
    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @hibernate.property column="age" length="32" type="java.lang.Integer"
     */
    public Integer getAge() {
        return age;
    }

    public void setAge(final Integer age) {
        this.age = age;
    }

    /**
     * @hibernate.set table="child"
     * @hibernate.key column="father_id"
     * @hibernate.one-to-many class="po.Child"
     */
    @OneToMany(mappedBy = "father", cascade = CascadeType.ALL)
    public Set<Child> getChildren() {
        return children;
    }

    public void setChildren(final Set<Child> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "Father [age=" + age + ", id=" + id + ", name=" + name + "]";
    }
}
