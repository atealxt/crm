package com.zhyfoundry.crm.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.zhyfoundry.crm.core.ServiceException;
import com.zhyfoundry.crm.core.dao.Pager;
import com.zhyfoundry.crm.entity.Enterprise;
import com.zhyfoundry.crm.service.CountryService;
import com.zhyfoundry.crm.service.EnterpriseService;
import com.zhyfoundry.crm.web.PagingController;

@Controller
public class EnterpriseController extends PagingController {

	@RequestMapping(value = "/admin/enterprise")
	public String list(final HttpServletRequest req, final HttpServletResponse resp, final ModelMap modelMap, @ModelAttribute("condition") final Enterprise condition)
			throws Exception {
		Pager pager = getPager(req);
		modelMap.addAttribute("list", enterpriseService.getEnterprises(condition, pager));
		modelMap.addAttribute("pager", pager);
		modelMap.addAttribute("countries", eountryService.getAllCountries());
		return "admin/enterprise/list";
	}

	@RequestMapping(value = "/admin/enterprise/sendEmail", method = RequestMethod.POST)
	public String sendEmail(final HttpServletRequest req, final HttpServletResponse resp, final ModelMap modelMap, @ModelAttribute("condition") final Enterprise condition)
			throws Exception {
		modelMap.addAttribute("EnterpriseCount", enterpriseService.count(condition));
		req.getSession().setAttribute(EMAIL_CONTIDION_OBJ, condition);
		modelMap.addAttribute("subjectExample", "支持参数：${name} - 企业名称，${contact} - 联系人<br>例如：对于“Dear ${name}, how are you”，企业 MyFoundry 生成的标题为：“Dear MyFoundry, how are you”。<br>支持逻辑或关系，如对于“Dear ${contact|name}, how are you”，企业 MyFoundry 有联系人Tom时生成的标题为：“Dear Tom, how are you”，没有联系人时生成的标题为：“Dear MyFoundry, how are you”。");
		return "admin/enterprise/compose";
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
			return "redirect:/admin/enterprise";
		}
	}

	@RequestMapping(value = "/admin/enterprise/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable final Integer id, final HttpServletRequest req, final HttpServletResponse resp, final ModelMap modelMap) throws Exception {
		enterpriseService.removeById(id);
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
}
