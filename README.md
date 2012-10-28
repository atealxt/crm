# CRM documentation

## System requirement

* Java 1.6 or higher.
* Maven 3 or higher.
* JUnit.
* Relational Database which Hibernate supported.
* Web container like Tomcat.

## Step to install

* Create database which name you can find from `/src/main/resources/jdbc.properties` field `jdbc.url`.
* Setting database username and password in `/src/main/resources/jdbc.properties` and `/src/test/java/com/zhyfoundry/crm/DBMaker_mysql.xml`.
* Run `/src/test/java/com/zhyfoundry/crm/DBMaker.java` by using JUnit.
  (Notice that you can change the default Admin username and password which in that file before run it.)
* Execute script file which in folder `/script`
* Now you can package it to a war file and run it:

```
$ mvn install
```

