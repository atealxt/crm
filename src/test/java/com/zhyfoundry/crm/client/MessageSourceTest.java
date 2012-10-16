package com.zhyfoundry.crm.client;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;

import com.zhyfoundry.crm.TestBase;

public class MessageSourceTest extends TestBase {

    @Autowired
    protected MessageSource messageSource;

    @Override
    public void execute() throws Exception {
        MessageSourceAccessor text = new MessageSourceAccessor(messageSource, request.getLocale());
        System.out.println(text.getMessage("helloworld"));

        text = new MessageSourceAccessor(messageSource, Locale.SIMPLIFIED_CHINESE);
        System.out.println(text.getMessage("helloworld"));
    }
}
