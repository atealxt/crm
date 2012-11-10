package com.zhyfoundry.crm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.springmodules.validation.bean.conf.loader.annotation.handler.Length;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotBlank;

@Entity
@Table(name = "ENTERPRISE", uniqueConstraints = { @UniqueConstraint(columnNames = { "NAME" }) })
/** 企业总表 */
public class Enterprise implements java.io.Serializable, Recyclable {

	private static final long serialVersionUID = 867088200985240910L;
	private static final int MAX_LEN_KEYWORD = 512;
	private static final int MAX_LEN_NAME = 100;
	private static final int MAX_LEN_CONTACT = 200;
	private static final int MAX_LEN_EMAIL = 1024;
	private static final int MAX_LEN_TEL = 200;
	private static final int MAX_LEN_MOBILENO = 200;
	private static final int MAX_LEN_FAXNO = 200;
	private static final int MAX_LEN_SOURCE = 200;
	private static final int MAX_LEN_REMARK = 1024;
	private static final int MAX_LEN_STATUS = 2;

	private Integer id;
	private Date createTime;
	private Date updateTime;
	private Integer status = STATUS_NORMAL;// 状态
	public final static Integer STATUS_NORMAL = 0;// 状态 - 正常
	public final static Integer STATUS_DELETE = -1;// 状态 - 删除

	@Length(max = MAX_LEN_KEYWORD)
	private String keyword;// 关键字
	private Country country;// 所属国家
	@NotBlank
	@Length(max = MAX_LEN_NAME)
	private String name;// 公司名称
	@Length(max = MAX_LEN_CONTACT)
	private String contact;// 联系人
	@Length(max = MAX_LEN_EMAIL)
	private String email;// 邮箱
	@Length(max = MAX_LEN_TEL)
	private String tel;// 电话
	@Length(max = MAX_LEN_MOBILENO)
	private String mobileNo;// 手机
	@Length(max = MAX_LEN_FAXNO)
	private String faxNo;// 传真
	@Length(max = MAX_LEN_SOURCE)
	private String source;// 来源网站
	@Length(max = MAX_LEN_REMARK)
	private String remark;// 备注

	public Enterprise() {
		super();
	}

	public Enterprise(String name) {
		super();
		this.name = name;
	}

	public Enterprise(Integer id, String keyword, Country country, String name, String contact, String email, String tel, String mobileNo, String faxNo, String source,
			String remark) {
		super();
		this.id = id;
		this.keyword = keyword;
		this.country = country;
		this.name = name;
		this.contact = contact;
		this.email = email;
		this.tel = tel;
		this.mobileNo = mobileNo;
		this.faxNo = faxNo;
		this.source = source;
		this.remark = remark;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "KEYWORD", length = MAX_LEN_KEYWORD)
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@ManyToOne(optional = true)
	@JoinColumn(name = "COUNTRY_ID")
	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	@Column(name = "NAME", length = MAX_LEN_NAME)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "CONTACT", length = MAX_LEN_CONTACT)
	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	@Column(name = "EMAIL", length = MAX_LEN_EMAIL)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "TEL", length = MAX_LEN_TEL)
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Column(name = "MOBILE_NO", length = MAX_LEN_MOBILENO)
	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	@Column(name = "FAX_NO", length = MAX_LEN_FAXNO)
	public String getFaxNo() {
		return faxNo;
	}

	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}

	@Column(name = "SOURCE", length = MAX_LEN_SOURCE)
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Column(name = "REMARK", length = MAX_LEN_REMARK)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "CREATE_TIME", insertable = false, updatable = false, columnDefinition = "TIMESTAMP NULL default CURRENT_TIMESTAMP")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "UPDATE_TIME", insertable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "STATUS", length = MAX_LEN_STATUS)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public void remove() {
		this.status = STATUS_DELETE;
	}

	@Override
	public boolean removed() {
		return STATUS_DELETE.equals(this.status);
	}

	@Override
	public void restore() {
		this.status = STATUS_NORMAL;
	}

	@Override
	public String toString() {
		return "Enterprise [id=" + id + ", name=" + name + "]";
	}
}
