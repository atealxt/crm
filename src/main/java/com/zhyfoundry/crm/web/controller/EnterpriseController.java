package com.zhyfoundry.crm.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.zhyfoundry.crm.core.dao.Pager;
import com.zhyfoundry.crm.entity.Enterprise;
import com.zhyfoundry.crm.service.EnterpriseService;
import com.zhyfoundry.crm.web.PagingController;

@Controller
public class EnterpriseController extends PagingController {

	@RequestMapping(value = "/admin/enterprise")
	public ModelAndView list(final HttpServletRequest req, final HttpServletResponse resp, final ModelMap model,
			@ModelAttribute("condition") final Enterprise condition) throws Exception {
		Pager pager = getPager(req);
		model.addAttribute("list", enterpriseService.getEnterprises(condition, pager));
		model.addAttribute("pager", pager);
		ModelMap modelMap = new ModelMap("condition", condition);
		return new ModelAndView("admin/enterprise/list", modelMap);
	}

	@RequestMapping(value = "/admin/enterprise/{id}", method = RequestMethod.GET)
	public String view(@PathVariable final Integer id, final HttpServletRequest req, final HttpServletResponse resp,
			final ModelMap modelMap) throws Exception {
		// TODO
		return null;
	}

	@RequestMapping(value = "/admin/enterprise/{id}", method = RequestMethod.POST)
	public String add(@PathVariable final Integer id, final HttpServletRequest req, final HttpServletResponse resp,
			final ModelMap modelMap) throws Exception {
		// TODO
		return null;
	}

	@RequestMapping(value = "/admin/enterprise/{id}", method = RequestMethod.PUT)
	public String edit(@PathVariable final Integer id, final HttpServletRequest req, final HttpServletResponse resp,
			final ModelMap modelMap) throws Exception {
		// TODO
		return null;
	}

	@RequestMapping(value = "/admin/enterprise/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable final Integer id, final HttpServletRequest req, final HttpServletResponse resp,
			final ModelMap modelMap) throws Exception {
		Enterprise e = enterpriseService.get(id);
		enterpriseService.remove(e);
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
}
