package com.zhyfoundry.crm.entity;

public interface Recyclable {

	void remove();

	boolean removed();

	void restore();
}
