package com.zhyfoundry.crm.web.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.DefaultValueProcessor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhyfoundry.crm.core.dao.Pager;
import com.zhyfoundry.crm.entity.Father;
import com.zhyfoundry.crm.service.FatherService;
import com.zhyfoundry.crm.web.PagingController;
import com.zhyfoundry.crm.web.PageInfo;

@Controller
public class TestPaging extends PagingController {

    @Autowired
    @Qualifier("FatherService")
    private FatherService fatherService;

    @RequestMapping("/pagination_back")
    public String test1(final HttpServletRequest req, final ModelMap model) throws IOException {
        final int page = getPage(req);
        final int pageSize = getPageSize(req);
        final int startIndex = calcStartIndex(page, pageSize);
        final int count = (int) fatherService.count();
        final int pageCount = calcPageSum(count, pageSize);
        model.addAttribute("list", fatherService.getFathers(new Pager(startIndex, pageSize)));
        model.addAttribute("count", count);
        model.addAttribute("pageNo", page);
        model.addAttribute("pageCount", pageCount);
        return "pagination_back";
    }

    @RequestMapping("/pagination_static")
    public String test2(final HttpServletRequest req, final ModelMap model) throws IOException {
        model.addAttribute("list", fatherService.getFathers());
        return "pagination_static";
    }

    @RequestMapping("/pagination_front")
    public String test3(final HttpServletRequest req, final ModelMap model) throws IOException {
        return "pagination_front";
    }

    //TODO 抽象此函数
    @RequestMapping("/pagination_front_ajax")
    public void test4(final HttpServletRequest req, final HttpServletResponse resp) throws IOException {
        final int page = getPage(req);
        final int pageSize = getPageSize(req);
        final int startIndex = calcStartIndex(page, pageSize);
        final int count = (int) fatherService.count();
        final int pageCount = calcPageSum(count, pageSize);
        final List<Father> list = fatherService.getFathers(new Pager(startIndex, pageSize));

        final PageInfo pageInfo = new PageInfo(page, pageSize, startIndex, count, pageCount);
        final JsonConfig jconfig = new JsonConfig();
        jconfig.registerPropertyExclusion(Father.class, "children");
        jconfig.registerDefaultValueProcessor(Integer.class, new DefaultValueProcessor() {
            @Override
            public Object getDefaultValue(@SuppressWarnings("rawtypes") final Class arg0) {
                return "";
            }
        });
        pageInfo.setJsonData(JSONArray.fromObject(list, jconfig).toString());
        resp.setContentType("application/json;charset=UTF-8");
        resp.getWriter().write(JSONObject.fromObject(pageInfo).toString());
    }

    @Override
    protected int getDefaultPageSize() {
        return 2;
    }

    @Override
    protected int getCountLimitation() {
        return Integer.MAX_VALUE;
    }
}
