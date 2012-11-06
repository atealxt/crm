# CRM documentation

## System requirement

* Java 1.6 or higher.
* MySQL 5 or higher.
* Web container like Tomcat.
* Maven 3 or higher.
* JUnit 4 or higher.

## Step to install

* Download all project dependencies with Maven command:

```
$ mvn install -Dmaven.test.skip=true
```

* Manual create database:

```
mysql> CREATE DATABASE `ZHYFOUNDRY_CRM`;
```

* Setting database username and password in following files:

  `/src/main/resources/jdbc.properties`

  `/src/test/java/com/zhyfoundry/crm/environment/DBMaker_mysql.xml`

  `intouch2/war/WEB-INF/config/config.xml`
* Run `/src/test/java/com/zhyfoundry/crm/environment/DBMaker.java` by using JUnit.
* Execute script files which in folder `/script`
* Setting mail configuration(edit `/intouch2/war/WEB-INF/config/config.xml`, and create a tmp directory as well. You can see the detail in `/intouch2/INSTALL.txt`).
* Now you can package it to a war file and run it:

```
$ mvn clean install
```

## Step to import history data
* Name the data excel file `ImportData.xlsx`, and put it to `/src/test/java/com/zhyfoundry/crm/environment/` (Cover the old file).
* Run `/src/test/java/com/zhyfoundry/crm/environment/ExcelDataImporter.java` by using JUnit.
* Check if error occurred at `/logs/ERROR.log`.
  (Note that you can change the default error log file path at `/src/main/resources/log4j.xml`.)