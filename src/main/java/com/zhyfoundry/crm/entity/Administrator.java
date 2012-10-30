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
@Table(name = "ADMINISTRATOR")
/** 管理员 */
public class Administrator implements java.io.Serializable {

	private static final int MAX_LEN = 32;
	private static final long serialVersionUID = 6160107519679158630L;
	private Integer id;
	@NotBlank
	@Length(max = MAX_LEN)
	private String username;
	@NotBlank
	@Length(max = MAX_LEN)
	private String password;

	public Administrator() {
		super();
	}

	public Administrator(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "USERNAME", length = MAX_LEN)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "PASSWORD", length = MAX_LEN)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
