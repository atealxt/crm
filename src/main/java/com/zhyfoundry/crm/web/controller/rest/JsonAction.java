package com.zhyfoundry.crm.web.controller.rest;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/** 建议：Paramter使用spring的机制、return时仍然用json-lib */
@Controller
public class JsonAction {

    private static Log logger = LogFactory.getLog(JsonAction.class);

    @RequestMapping(value = "/rest/json1", method = RequestMethod.GET)
    public @ResponseBody
    Json Json1(@RequestParam final String param) {
        logger.debug("param: " + param);

        final Json json = new Json();
        json.setId(111);
        json.setName("@ResponseBody测试");
        json.setRegisterTime(new Date());
        logger.debug(json);

        return json;
    }

    @RequestMapping(value = "/rest/json2/{id}/{name}", method = RequestMethod.GET)
    public void Json2(final HttpServletResponse resp, @PathVariable final Integer id, @PathVariable final String name)
            throws IOException {
        logger.debug("param id: " + id);
        logger.debug("param name: " + name);

        final Json json = new Json();
        json.setId(id);
        json.setName(name);
        json.setRegisterTime(new Date());
        logger.debug(json);

        resp.setContentType("application/json;charset=UTF-8");
        resp.getWriter().write(JSONObject.fromObject(json).toString());
    }

    /** 前台要用post提交才行，或者借助jquery json插件这样的工具 */
    @RequestMapping(value = "/rest/json3", method = RequestMethod.POST)
    public @ResponseBody
    Json Json3(@RequestBody final Json param) throws IOException {
        logger.debug("param: " + param);

        final Json json = new Json();
        json.setId(param.getId());
        json.setName(param.getName());
        json.setRegisterTime(param.getRegisterTime());
        logger.debug(json);

        return json;
    }
}
