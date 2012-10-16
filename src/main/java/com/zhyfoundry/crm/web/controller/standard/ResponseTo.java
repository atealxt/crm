package com.zhyfoundry.crm.web.controller.standard;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

public class ResponseTo extends AbstractView {

    @Override
    protected void renderMergedOutputModel(
            final Map<String, Object> arg0,
            final HttpServletRequest arg1,
            final HttpServletResponse response) throws Exception {

        InputStream inputStream = new FileInputStream("D:/Work/Data/Java/Else/REST_cn.pdf");

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            byte[] bufferByte = new byte[2048];
            int tempData = 0;
            while ((tempData = inputStream.read(bufferByte)) != -1) {
                out.write(bufferByte, 0, tempData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        inputStream.close();

        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment; filename=test.pdf");
        response.setContentLength(out.size());

        ServletOutputStream output = response.getOutputStream();
        out.writeTo(output);
        output.flush();
        output.close();
    }

}
