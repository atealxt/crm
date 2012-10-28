package com.zhyfoundry.crm;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.zhyfoundry.crm.entity.Administrator;
import com.zhyfoundry.crm.entity.Child;
import com.zhyfoundry.crm.entity.Country;
import com.zhyfoundry.crm.entity.Enterprise;
import com.zhyfoundry.crm.entity.Father;
import com.zhyfoundry.crm.entity.Student;
import com.zhyfoundry.crm.entity.Teacher;

/**
 * DB环境初始化类
 */
public class DBMaker {

	public static final String USERNAME_ADMIN = "Admin";
	public static final String PASSWORD_ADMIN = "Admin123456";
	private Configuration conf;
	private SessionFactory sf;

	@Before
	public void setUp() {
		System.out.println("Enviroment build start");
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("Enviroment build end");
	}

	@Test
	public void mysql() {
		try {
			conf = new AnnotationConfiguration()
					.configure(TestConstants.TEST_RESOURCES_PATH_BASE + "DBMaker_mysql.xml");
			sf = conf.buildSessionFactory();
			createTable();
			sf.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createTable() throws Exception {
		SchemaExport export = new SchemaExport(conf);
		// export.setOutputFile("D:/test.sql");
		export.create(true, true);

		createOne2Many();
		createMany2Many();
		createAdmin();
		createEnterprise();
		DataTools.importEnterpriseDataFromAnotherDB();
	}

	private void createEnterprise() {
		Country country = new Country("China");
		sf.getCurrentSession().beginTransaction();
		sf.getCurrentSession().save(country);

		for (int i = 1; i < 60; i++) {
			Enterprise e = new Enterprise(i, "keyword", country, "name" + i, "admin", "admin@admin.com", "123456",
					"234567", "345678", "http://www.123.com", "remark");
			sf.getCurrentSession().save(e);
		}

		sf.getCurrentSession().getTransaction().commit();
		sf.getCurrentSession().close();
	}

	private void createAdmin() {
		Administrator admin = new Administrator();
		admin.setUsername(USERNAME_ADMIN);
		admin.setPassword(PASSWORD_ADMIN);
		sf.getCurrentSession().beginTransaction();
		sf.getCurrentSession().save(admin);
		sf.getCurrentSession().getTransaction().commit();
		sf.getCurrentSession().close();
	}

	private void createOne2Many() {
		Father f = new Father();
		f.setName("tom");
		f.setAge(30);
		sf.getCurrentSession().beginTransaction();
		sf.getCurrentSession().save(f);
		sf.getCurrentSession().getTransaction().commit();
		sf.getCurrentSession().close();

		Child c = new Child();
		c.setName("jerry");
		c.setFather(f);
		sf.getCurrentSession().beginTransaction();
		sf.getCurrentSession().save(c);
		sf.getCurrentSession().getTransaction().commit();
		sf.getCurrentSession().close();
	}

	private void createMany2Many() {
		Teacher t1 = new Teacher();
		t1.setName("Mr. Wang");
		Teacher t2 = new Teacher();
		t2.setName("Mr. Zhang");
		Student s1 = new Student();
		s1.setName("Xiao Zhao");
		Student s2 = new Student();
		s2.setName("Xiao Liu");

		sf.getCurrentSession().beginTransaction();
		sf.getCurrentSession().save(t1);
		sf.getCurrentSession().save(t2);
		sf.getCurrentSession().save(s1);
		sf.getCurrentSession().save(s2);
		sf.getCurrentSession().getTransaction().commit();

		Set<Teacher> teachers = new HashSet<Teacher>();
		teachers.add(t1);
		teachers.add(t2);
		Set<Student> students = new HashSet<Student>();
		students.add(s1);
		students.add(s2);

		t1.setStudents(students);
		t2.setStudents(students);
		s1.setTeachers(teachers);
		s2.setTeachers(teachers);

		sf.getCurrentSession().beginTransaction();
		sf.getCurrentSession().update(t1);
		sf.getCurrentSession().update(t2);
		// sf.getCurrentSession().update(s1);
		// sf.getCurrentSession().update(s2);
		sf.getCurrentSession().getTransaction().commit();
		sf.getCurrentSession().close();
	}

}
