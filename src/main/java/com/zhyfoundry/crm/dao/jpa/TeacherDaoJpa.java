package com.zhyfoundry.crm.dao.jpa;

import org.springframework.stereotype.Repository;

import com.zhyfoundry.crm.core.dao.BaseDaoJpa;
import com.zhyfoundry.crm.dao.TeacherDao;
import com.zhyfoundry.crm.entity.Teacher;

@Repository("TeacherDao")
public class TeacherDaoJpa extends BaseDaoJpa<Teacher, String> implements TeacherDao {

    public TeacherDaoJpa() {
        super(Teacher.class);
    }
}
