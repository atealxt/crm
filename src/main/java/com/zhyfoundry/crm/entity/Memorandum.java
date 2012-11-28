package com.zhyfoundry.crm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springmodules.validation.bean.conf.loader.annotation.handler.Length;

@Entity
@Table(name = "MEMORANDUM")
/** 备注 */
public class Memorandum implements java.io.Serializable {

    private static final long serialVersionUID = 5324170566477507009L;
    private static final int MAX_LEN_CONTENT = 1024;
    private Integer id;
    @Length(max = MAX_LEN_CONTENT)
    private String content;
    private Date createTime;

    public Memorandum() {
        super();
    }

    public Memorandum(String content) {
        super();
        this.content = content;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "CONTENT", length = MAX_LEN_CONTENT)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "CREATE_TIME", insertable = false, updatable = false, columnDefinition = "TIMESTAMP NULL default CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
