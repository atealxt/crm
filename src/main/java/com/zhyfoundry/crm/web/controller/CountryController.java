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
import com.zhyfoundry.crm.entity.Country;
import com.zhyfoundry.crm.service.CountryService;
import com.zhyfoundry.crm.web.PagingController;

@Controller
public class CountryController extends PagingController {

	@RequestMapping(value = "/admin/country")
	public String list(final HttpServletRequest req, final HttpServletResponse resp, final ModelMap modelMap, @ModelAttribute("condition") final Country condition)
			throws Exception {
		Pager pager = getPager(req);
		modelMap.addAttribute("list", countryService.getCountrys(condition, pager));
		modelMap.addAttribute("pager", pager);
		return "admin/country/list";
	}

	@RequestMapping(value = "/admin/country/add", method = RequestMethod.GET)
	public String preAdd(final HttpServletRequest req, final HttpServletResponse resp, final ModelMap modelMap) throws Exception {
		modelMap.addAttribute("o", new Country());
		return "admin/country/add";
	}

	@RequestMapping(value = "/admin/country/add", method = RequestMethod.POST)
	public String add(@ModelAttribute("o") final Country country, final BindingResult result, final HttpServletRequest req, final HttpServletResponse resp, final ModelMap modelMap)
			throws Exception {
		return addOrEdit(country, result, req, null, resp, modelMap);
	}

	@RequestMapping(value = "/admin/country/{id}", method = RequestMethod.GET)
	public String view(@PathVariable final Integer id, final HttpServletRequest req, final HttpServletResponse resp, final ModelMap modelMap,
			@RequestParam(value = "edit", required = false) final boolean edit) throws Exception {
		Country o = countryService.get(id);
		modelMap.addAttribute("o", o);
		if (edit) {
			return "admin/country/edit";
		}
		return "admin/country/view";
	}

	@RequestMapping(value = "/admin/country/{id}", method = RequestMethod.POST)
	public String addOrEdit(@ModelAttribute("o") final Country country, final BindingResult result, final HttpServletRequest req, @PathVariable final Integer id,
			final HttpServletResponse resp, final ModelMap modelMap) throws Exception {
		validator.validate(country, result);
		if (result.hasErrors()) {
			if (id == null) {
				return "admin/country/add";
			}
			return "admin/country/edit";
		}
		if (id == null) {
			Country addedCountry = countryService.checkAndModify(country);
			appendInfo(req, "Country.new.success", addedCountry.getId(), addedCountry.getName());
			return preAdd(req, resp, modelMap);
		} else {
			countryService.checkAndModify(country);
			return "redirect:/admin/country";
		}
	}

	@RequestMapping(value = "/admin/country/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable final Integer id, final HttpServletRequest req, final HttpServletResponse resp, final ModelMap modelMap) throws Exception {
		try {
			countryService.removeById(id);
		} catch (ServiceException e) {
			initResponse(resp);
			resp.getWriter().print(e.getMessage());
		}
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
	private CountryService countryService;
	@Autowired
	private Validator validator;
}
