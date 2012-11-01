# CRM documentation

## System requirement

* Java 1.6 or higher.
* MySQL 5 or higher.
* Web container like Tomcat.
* Maven 3 or higher.
* JUnit 4 or higher.

## Step to install

* Manual create database `ZHYFOUNDRY_CRM`.

```
mysql> CREATE DATABASE `ZHYFOUNDRY_CRM`;
```

* Setting database username and password in `/src/main/resources/jdbc.properties` and `/src/test/java/com/zhyfoundry/crm/environment/DBMaker_mysql.xml`.
* Run `/src/test/java/com/zhyfoundry/crm/environment/DBMaker.java` by using JUnit.
  (Note that you can change the default Admin username and password which in that file before run it.)
* Execute script file which in folder `/script`
* Now you can package it to a war file and run it:

```
$ mvn install
```

## Step to import history data
* Name the data excel file `ImportData.xlsx`, and put it to `/src/test/java/com/zhyfoundry/crm/environment/` (Cover the old file). 
* Run `/src/test/java/com/zhyfoundry/crm/environment/ExcelDataImporter.java` by using JUnit.
* Check if error occurred at `/logs/ERROR.log`.
  (Note that you can change the default error log file path at `/src/main/resources/log4j.xml`.)