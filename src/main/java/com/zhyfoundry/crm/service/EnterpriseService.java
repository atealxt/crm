package com.zhyfoundry.crm.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhyfoundry.crm.core.ServiceException;
import com.zhyfoundry.crm.core.dao.BaseDao;
import com.zhyfoundry.crm.core.dao.Pager;
import com.zhyfoundry.crm.core.service.PaginationServiceImpl;
import com.zhyfoundry.crm.dao.CountryDao;
import com.zhyfoundry.crm.dao.EnterpriseDao;
import com.zhyfoundry.crm.dao.GeneralDao;
import com.zhyfoundry.crm.entity.Country;
import com.zhyfoundry.crm.entity.Enterprise;
import com.zhyfoundry.crm.entity.Memorandum;

@Service
public class EnterpriseService extends PaginationServiceImpl<Enterprise, Integer> {

    @Autowired
    private EnterpriseDao enterpriseDao;
    @Autowired
    private CountryDao countryDao;
    @Autowired
    private GeneralDao generalDao;

    @Override
    protected BaseDao<Enterprise, Integer> getDao() {
        return enterpriseDao;
    }

    public List<Enterprise> getEnterprises(Enterprise condition, final Pager pager) {
        StringBuilder queryBuilder = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        geneQueryString(queryBuilder, params, condition);
        String query = queryBuilder.toString();
        String queryContainsOrder = queryBuilder.append(pager.getOrder()).toString();
        final List<Enterprise> enterprises = findByQuery(query, queryContainsOrder, pager, params.toArray());
        for (final Enterprise f : enterprises) {
            initialize(f.getCountry());
        }
        return enterprises;
    }

    private void geneQueryString(StringBuilder query, List<Object> params, Enterprise condition) {
        query.append("select t from Enterprise t left join t.country where 1=1 ");
        if (condition.getId() != null) {
            query.append(" and t.id = ?");
            params.add(condition.getId());
        }
        if (condition.getStatus() != null) {
            query.append(" and t.status = ?");
            params.add(condition.getStatus());
        }
        if (StringUtils.isNotBlank(condition.getKeyword())) {
            query.append(" and t.keyword like ?");
            params.add("%" + condition.getKeyword() + "%");
        }
        if (condition.getCountry() != null) {
            if (condition.getCountry().getId() != null) {
                query.append(" and t.country.id = ?");
                params.add(condition.getCountry().getId());
            }
            if (StringUtils.isNotBlank(condition.getCountry().getName())) {
                query.append(" and t.country.name like ?");
                params.add("%" + condition.getCountry().getName() + "%");
            }
        }
        if (StringUtils.isNotBlank(condition.getName())) {
            query.append(" and t.name like ?");
            params.add("%" + condition.getName() + "%");
        }
        if (StringUtils.isNotBlank(condition.getContact())) {
            query.append(" and t.contact like ?");
            params.add("%" + condition.getContact() + "%");
        }
        if (StringUtils.isNotBlank(condition.getEmail())) {
            query.append(" and t.email like ?");
            params.add("%" + condition.getEmail() + "%");
        }
        if (StringUtils.isNotBlank(condition.getTel())) {
            query.append(" and t.tel like ?");
            params.add("%" + condition.getTel() + "%");
        }
        if (StringUtils.isNotBlank(condition.getMobileNo())) {
            query.append(" and t.mobileNo like ?");
            params.add("%" + condition.getMobileNo() + "%");
        }
        if (StringUtils.isNotBlank(condition.getFaxNo())) {
            query.append(" and t.faxNo like ?");
            params.add("%" + condition.getFaxNo() + "%");
        }
        if (StringUtils.isNotBlank(condition.getSource())) {
            query.append(" and t.source like ?");
            params.add("%" + condition.getSource() + "%");
        }
        if (StringUtils.isNotBlank(condition.getRemark())) {
            query.append(" and t.remark like ?");
            params.add("%" + condition.getRemark() + "%");
        }
    }

    @Transactional
    public void add(Enterprise enterprise) {
        enterprise.setCountry(initCountry(enterprise));
        add(enterprise);
    }

    @Transactional
    public Enterprise checkAndModify(Enterprise enterprise) {
        Enterprise e = enterpriseDao.findByName(enterprise.getName());
        if (e != null) {
            logger.warn("该企业名已存在。id = " + e.getId() + ", name = " + e.getName());
            throw new ServiceException("Enterprise.name.exist").addReason(e.getId()).addReason(e.getName());
        }
        enterprise.setCountry(initCountry(enterprise));
        return merge(enterprise);
    }

    @Transactional
    public void modify(Enterprise enterprise) {
        enterprise.setCountry(initCountry(enterprise));
        merge(enterprise);
    }

    private Country initCountry(Enterprise enterprise) {
        Country country;
        if (StringUtils.isNotBlank(enterprise.getCountry().getName())) {
            country = countryDao.findByName(enterprise.getCountry().getName());
        } else if (enterprise.getCountry().getId() != null) {
            country = countryDao.findById(enterprise.getCountry().getId());
        } else {
            return null;
        }
        if (country == null) {
            country = new Country(enterprise.getCountry().getName());
            countryDao.save(country);
        }
        return country;
    }

    @Override
    @Transactional
    public void removeById(Integer id) {
        Enterprise enterprise = getDao().findById(id);
        if (enterprise.removed()) {
            getDao().delete(enterprise);
        } else {
            enterprise.remove();
        }
    }

    @Transactional
    public void restore(Integer id) {
        Enterprise enterprise = getDao().findById(id);
        if (enterprise.removed()) {
            enterprise.restore();
            merge(enterprise);
        } else {
            logger.warn("操作异常：" + enterprise);
        }
    }

    public long count(Enterprise condition) {
        StringBuilder query = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        geneQueryString(query, params, condition);
        return getDao().count(query.toString(), params.toArray());
    }

    @Transactional
    public void increaseMailSentCount(Integer id) {
        getDao().execute("update Enterprise set countMailSent = countMailSent + 1 where id = ?", id);
    }

    @Override
    @Transactional(readOnly = true)
    public Enterprise get(Integer id) {
        Enterprise e = super.get(id);
        initialize(e.getMemos());
        return e;
    }

    @Transactional
    public void addMemo(Integer enterpriseId, String content) {
        Memorandum memo = new Memorandum(content);
        memo.setEnterprise(super.get(enterpriseId));
        generalDao.save(memo);
    }

    @Transactional
    public void deleteMemo(Integer memoId) {
        getDao().execute("delete Memorandum where id = ?", memoId);
    }
}
