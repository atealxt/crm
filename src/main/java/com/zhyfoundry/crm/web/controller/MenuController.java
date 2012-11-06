package com.zhyfoundry.crm.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhyfoundry.crm.web.BaseController;
import com.zhyfoundry.crm.web.vo.PageLink;

@Controller
@RequestMapping("/admin/menu")
public class MenuController extends BaseController {

	@RequestMapping(method = RequestMethod.GET)
	public String main(final HttpServletRequest req, final HttpServletResponse resp, final ModelMap model) throws IOException {
		makeRootMain(req, model);
		return "admin/menu";
	}

	private void makeRootMain(final HttpServletRequest req, final ModelMap model) {
		model.addAttribute("mapLink", makeLink());
	}

	private Map<String, List<PageLink>> makeLink() {

		Map<String, List<PageLink>> mapLink = new HashMap<String, List<PageLink>>();

		List<PageLink> links = new ArrayList<PageLink>();
		PageLink link = null;

		link = new PageLink();
		link.setLink("/admin/enterprise");
		link.setMsgShow("Enterprise.management");
		links.add(link);

		link = new PageLink();
		link.setLink("/admin/enterprise?status=-1");
		link.setMsgShow("Enterprise.recycle");
		links.add(link);

		mapLink.put("Enterprise", links);

		links = new ArrayList<PageLink>();

		link = new PageLink();
		link.setLink("/admin/country");
		link.setMsgShow("Country.management");
		links.add(link);

		mapLink.put("Country", links);

		return mapLink;
	}
}
