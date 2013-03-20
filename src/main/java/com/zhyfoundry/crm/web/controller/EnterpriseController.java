package com.zhyfoundry.crm.web.controller;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.zhyfoundry.crm.core.ServiceException;
import com.zhyfoundry.crm.core.dao.Pager;
import com.zhyfoundry.crm.entity.Enterprise;
import com.zhyfoundry.crm.service.CountryService;
import com.zhyfoundry.crm.service.EnterpriseService;
import com.zhyfoundry.crm.web.PagingController;

import freemarker.template.Template;
import freemarker.template.TemplateException;

@Controller
public class EnterpriseController extends PagingController {

	@RequestMapping(value = "/admin/enterprise")
    public String list(final HttpServletRequest req, final HttpServletResponse resp, final ModelMap modelMap, @ModelAttribute("condition") Enterprise condition)
            throws Exception {
		Pager pager;
    	if (usePreviousList(req)) {
    		Enterprise e = getPreviousListCondition(req);
    		if (e != null) {
    			ConvertUtils.register(new DateConverter(null), java.util.Date.class);
    			ConvertUtils.register(new IntegerConverter(null), Integer.class);
    			BeanUtils.copyProperties(condition, e);
    		}
    		Pager p = getPreviousListPager(req);
    		if (p != null) {
    			pager = p;
    		} else {
    			pager = getPager(req);
    		}
    	}
    	else {
    		pager = getPager(req);
    	}
    	saveListStatus(req, condition, pager);
        modelMap.addAttribute("list", enterpriseService.getEnterprises(condition, pager));
        modelMap.addAttribute("pager", pager);
        modelMap.addAttribute("countries", eountryService.getAllCountries());
        return "admin/enterprise/list";
    }

    private void saveListStatus(HttpServletRequest req, Enterprise condition, Pager pager) {
    	req.getSession().setAttribute(PREVIOUS_LIST_CONDITION, condition);
    	req.getSession().setAttribute(PREVIOUS_LIST_PAGER, pager);
    	req.getSession().setAttribute(EMAIL_CONTIDION_ORDER, getOrder(req));
	}

	private Pager getPreviousListPager(HttpServletRequest req) {
		return (Pager) req.getSession().getAttribute(PREVIOUS_LIST_PAGER);
	}

	private Enterprise getPreviousListCondition(HttpServletRequest req) {
		return (Enterprise) req.getSession().getAttribute(PREVIOUS_LIST_CONDITION);
	}

	private boolean usePreviousList(HttpServletRequest req) {
		return PARAM_USE_PREVIOUS_LIST_TRUE.equals(req.getParameter(PARAM_USE_PREVIOUS_LIST));
	}

	@RequestMapping(value = "/admin/enterprise/sendEmail", method = RequestMethod.POST)
    public String sendEmail(final HttpServletRequest req, final HttpServletResponse resp, final ModelMap modelMap, @ModelAttribute("condition") final Enterprise condition)
            throws Exception {
        modelMap.addAttribute("EnterpriseCount", enterpriseService.count(condition));
        req.getSession().setAttribute(EMAIL_CONTIDION_OBJ, condition);
        req.getSession().setAttribute(EMAIL_CONTIDION_ORDER, getOrder(req));
        modelMap.addAttribute("subjectExample", getSubjectExample());
        return "admin/enterprise/compose";
    }

    private String getSubjectExample() throws TemplateException, IOException {
        StringWriter out = new StringWriter();
        Template t = new Template("", new StringReader("<#import \"macro/sampleOfMailVariable.ftl\" as sample><@sample.page />"), config.getConfiguration());
        t.process(new HashMap<String, Object>(), out);
        return out.toString();
    }

    @RequestMapping(value = "/admin/enterprise/add", method = RequestMethod.GET)
    public String preAdd(final HttpServletRequest req, final HttpServletResponse resp, final ModelMap modelMap) throws Exception {
        modelMap.addAttribute("o", new Enterprise());
        modelMap.addAttribute("countries", eountryService.getAllCountries());
        return "admin/enterprise/add";
    }

    @RequestMapping(value = "/admin/enterprise/add", method = RequestMethod.POST)
    public String add(@ModelAttribute("o") final Enterprise enterprise,
            final BindingResult result,
            final HttpServletRequest req,
            final HttpServletResponse resp,
            final ModelMap modelMap) throws Exception {
        return addOrEdit(enterprise, result, req, null, resp, modelMap);
    }

    @RequestMapping(value = "/admin/enterprise/addMemo", method = RequestMethod.POST)
    public String addMemo(@RequestParam(value = "eId") final Integer enterpriseId,
            @RequestParam(value = "content") final String content,
            final HttpServletRequest req,
            final HttpServletResponse resp,
            final ModelMap modelMap) throws Exception {
        enterpriseService.addMemo(enterpriseId, content);
        return "redirect:/admin/enterprise/" + enterpriseId;
    }

    @RequestMapping(value = "/admin/enterprise/{id}", method = RequestMethod.GET)
    public String view(@PathVariable final Integer id, final HttpServletRequest req, final HttpServletResponse resp, final ModelMap modelMap,
            @RequestParam(value = "edit", required = false) final boolean edit) throws Exception {
        Enterprise o = enterpriseService.get(id);
        modelMap.addAttribute("o", o);
        if (edit) {
            modelMap.addAttribute("countries", eountryService.getAllCountries());
            return "admin/enterprise/edit";
        }
        return "admin/enterprise/view";
    }

    @RequestMapping(value = "/admin/enterprise/{id}", method = RequestMethod.POST)
    public String addOrEdit(@ModelAttribute("o") final Enterprise enterprise,
            final BindingResult result,
            final HttpServletRequest req,
            @PathVariable final Integer id,
            final HttpServletResponse resp,
            final ModelMap modelMap) throws Exception {
        validator.validate(enterprise, result);
        if (enterprise.getCountry() != null && StringUtils.isNotEmpty(enterprise.getCountry().getName())) {
            validator.validate(enterprise.getCountry(), result);
        }
        if (result.hasErrors()) {
            if (id == null) {
                return "admin/enterprise/add";
            }
            return "admin/enterprise/edit";
        }
        if (id == null) {
            Enterprise addedEnterprise;
            try {
                addedEnterprise = enterpriseService.checkAndModify(enterprise);
            } catch (ServiceException e) {
                appendError(req, e);
                return "admin/enterprise/add";
            }
            appendInfo(req, "Enterprise.new.success", addedEnterprise.getId(), addedEnterprise.getName());
            return preAdd(req, resp, modelMap);
        } else {
            enterpriseService.modify(enterprise);
            if (PARAM_USE_PREVIOUS_LIST_TRUE.equals(req.getParameter("returnToList"))) {
            	return "forward:/admin/enterprise" + "?" + PARAM_USE_PREVIOUS_LIST + "=" + PARAM_USE_PREVIOUS_LIST_TRUE + "&"+ getOrderParam(req);
            } else {
            	return "redirect:/admin/enterprise/" + id;
            }
        }
    }

	private String getOrderParam(HttpServletRequest req) {
		String order = (String) req.getSession().getAttribute(EMAIL_CONTIDION_ORDER);
		try {
			return PARAM_ORDER + "=" + URLEncoder.encode(order, "utf-8") ;
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	@RequestMapping(value = "/admin/enterprise/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable final Integer id, final HttpServletRequest req, final HttpServletResponse resp, final ModelMap modelMap) throws Exception {
        enterpriseService.removeById(id);
    }

    @RequestMapping(value = "/admin/enterprise/deleteMemo", method = RequestMethod.POST)
    public String deleteMemo(@RequestParam(value = "eId") final Integer enterpriseId,
            @RequestParam(value = "memoId") final Integer memoId,
            final HttpServletRequest req,
            final HttpServletResponse resp,
            final ModelMap modelMap) throws Exception {
        enterpriseService.deleteMemo(memoId);
        return "redirect:/admin/enterprise/" + enterpriseId;
    }

    @RequestMapping(value = "/admin/enterprise/{id}/restore", method = RequestMethod.POST)
    public String restore(@PathVariable final Integer id, final HttpServletRequest req, final HttpServletResponse resp, final ModelMap modelMap) throws Exception {
        enterpriseService.restore(id);
        return "redirect:/admin/enterprise?status=-1";
    }

    @Override
    protected int getDefaultPageSize() {
        return 50;
    }

    @Override
    protected int getCountLimitation() {
        return 500;
    }

    @Autowired
    private EnterpriseService enterpriseService;
    @Autowired
    private CountryService eountryService;
    @Autowired
    private Validator validator;
    public static final String EMAIL_CONTIDION_OBJ = "EMAIL_CONTIDION_OBJ";
    public static final String EMAIL_CONTIDION_ORDER = "EMAIL_CONTIDION_ORDER";
    @Autowired
    private FreeMarkerConfigurer config;
    private static final String PARAM_USE_PREVIOUS_LIST = "usePreviousList";
    private static final String PARAM_USE_PREVIOUS_LIST_TRUE = "true";
	private static final String PREVIOUS_LIST_PAGER = "PreviousListPager";
	private static final String PREVIOUS_LIST_CONDITION = "PreviousListCondition";
}
