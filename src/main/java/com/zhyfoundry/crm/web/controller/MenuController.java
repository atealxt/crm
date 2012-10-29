package com.zhyfoundry.crm.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        model.addAttribute("links", makeLink());
    }

    private List<PageLink> makeLink() {
        final List<PageLink> links = new ArrayList<PageLink>();
        PageLink link = null;

        link = new PageLink();
        link.setLink("/admin/enterprise");
        link.setMsgShow("企业名录管理");
        links.add(link);

        // TODO 国家管理
//        link = new PageLink();
//        link.setLink("/###");
//        link.setMsgShow("企业国家管理");
//        links.add(link);

        link = new PageLink();
        link.setLink("/admin/enterprise?status=-1");
        link.setMsgShow("企业名录回收站");
        links.add(link);

        // TODO group by module

        return links;
    }
}
