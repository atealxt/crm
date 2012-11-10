package com.zhyfoundry.crm.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springmodules.validation.bean.conf.loader.annotation.handler.Length;

/**
 * @hibernate.class table="student"
 */
@Entity
@Table(name = "student")
public class Student implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private Set<Teacher> teachers = new HashSet<Teacher>(0);

    public Student() {
        this.id = UUID.randomUUID().toString();
    }

    /**
     * @hibernate.id generator-class="uuid.hex" column="id" length="32"
     */
    @Id
    @Column(name = "id", length = 36)
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * @hibernate.property column="name" length="32" not-null="true" type="java.lang.String"
     */
    @Column(name = "name")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @hibernate.set table="teacher_student_relation"
     * @hibernate.key column="student_id"
     * @hibernate.many-to-many class="po.Teacher" column="teacher_id"
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "teacher_student_relation", joinColumns = { @JoinColumn(name = "student_id") }, inverseJoinColumns = @JoinColumn(name = "teacher_id"))
    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }
}
