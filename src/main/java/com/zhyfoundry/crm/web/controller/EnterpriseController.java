package com.zhyfoundry.crm.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		// TODO 计算企业个数
		req.getSession().setAttribute("EMAIL_CONTIDION_OBJ", condition);
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
}
