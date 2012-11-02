package com.zhyfoundry.crm.core;

import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DSContextHolder.getDSContext();
    }

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return Logger.getLogger(getClass().getPackage().getName());
	}
}
