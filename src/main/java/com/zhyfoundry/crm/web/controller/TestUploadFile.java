package com.zhyfoundry.crm.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class TestUploadFile {

    @RequestMapping("/testupload")
    public void test1(@RequestParam(value = "file1") final MultipartFile file, final HttpServletResponse resp)
            throws IOException {
        if (!file.isEmpty()) {
            final byte[] bytes = file.getBytes();
            resp.getOutputStream().println("file: " + bytes);
        }
        resp.getOutputStream().println("name: " + file.getOriginalFilename() + "  size: " + file.getSize());
    }

//    @RequestMapping("/testupload2")
//    public void test2(MultipartHttpServletRequest request, final HttpServletResponse resp) throws IOException {
//
//        List<MultipartFile> files = request.getFiles("file");//可按name获取所有文件
//
//        if (!file.isEmpty()) {
//            final byte[] bytes = file.getBytes();
//            resp.getOutputStream().println("file: " + bytes);
//        }
//        resp.getOutputStream().println("name: " + file.getOriginalFilename() + "  size: " + file.getSize());
//    }
}
