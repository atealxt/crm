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
@Table(name = "COUNTRY")
/** 国家 */
public class Country implements java.io.Serializable {

	private static final long serialVersionUID = 7995004583868794600L;
	private static final int MAX_LEN_NAME = 20;
	private Integer id;
	@NotBlank
	@Length(max = MAX_LEN_NAME)
	private String name;// 国家名称

	public Country() {
		super();
	}

	public Country(String name) {
		super();
		this.name = name;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "NAME", length = MAX_LEN_NAME)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Country [id=" + id + ", name=" + name + "]";
	}
}
