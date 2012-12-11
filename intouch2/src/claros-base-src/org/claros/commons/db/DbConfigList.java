package org.claros.commons.db;

import java.util.HashMap;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zhyfoundry.crm.core.DIManager;

public class DbConfigList {
    private static Log log = LogFactory.getLog(DbConfigList.class);

    public static HashMap dbList = new HashMap();

    public void addDbConfig(DbConfig db) {
        dbList.put(db.getId(), db);
    }

    public static HashMap getDbList() {
        return dbList;
    }

    public static DataSource getDataSource() {
        return DIManager.getBean("DSMySQL");
    }

    @Deprecated
    /** itouch database config has been removed. Instead of, using main spring data source. */
    public static DataSource getDataSourceById(String id) {
//	    DbConfig db = (DbConfig)dbList.get(id);
//	    if (db != null) {
//		    return db.getDataSource();
//	    } else {
//	        log.warn("The Id Searched at the DbConfigList does not correspond to a DbConfig");
//	        return null;
//	    }
        return getDataSource();
    }
}
