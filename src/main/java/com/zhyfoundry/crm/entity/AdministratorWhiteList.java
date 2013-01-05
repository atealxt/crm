package com.zhyfoundry.crm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springmodules.validation.bean.conf.loader.annotation.handler.Length;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotBlank;

@Entity
@Table(name = "ADMINISTRATOR_WHITELIST")
/** 管理员白名单 */
public class AdministratorWhiteList implements java.io.Serializable {

	private static final long serialVersionUID = -1994730919767720741L;
	private Integer id;
	@NotBlank
	@Length(max = Administrator.MAX_LEN)
	private String username;

	public AdministratorWhiteList() {
		super();
	}

	public AdministratorWhiteList(String username) {
		super();
		this.username = username;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "USERNAME", length = Administrator.MAX_LEN)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
