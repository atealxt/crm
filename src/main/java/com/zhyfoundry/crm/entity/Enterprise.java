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
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "ENTERPRISE", uniqueConstraints = { @UniqueConstraint(columnNames = { "NAME" }) })
/** 企业总表 */
public class Enterprise implements java.io.Serializable {

	private static final long serialVersionUID = 867088200985240910L;
	private Integer id;
	private Date createTime;
	private Date updateTime;
	private String keyword;// 关键字
	private Country country;// 所属国家
	private String name;// 公司名称
	private String contact;// 联系人
	private String email;// 邮箱
	private String tel;// 电话
	private String mobileNo;// 手机
	private String faxNo;// 传真
	private String source;// 来源网站
	private String remark;// 备注

	public Enterprise() {
		super();
	}

	public Enterprise(Integer id) {
		super();
		this.id = id;
	}

	public Enterprise(Integer id, String keyword, Country country, String name, String contact,
			String email, String tel, String mobileNo, String faxNo, String source, String remark) {
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

	@Column(name = "KEYWORD")
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

	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "CONTACT")
	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "TEL")
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Column(name = "MOBILE_NO")
	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	@Column(name = "FAX_NO")
	public String getFaxNo() {
		return faxNo;
	}

	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}

	@Column(name = "SOURCE")
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Column(name = "REMARK")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "UPDATE_TIME")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "Enterprise [id=" + id + ", name=" + name + "]";
	}
}
