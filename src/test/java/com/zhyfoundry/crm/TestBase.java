package com.zhyfoundry.crm;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.ModelMap;

/**
 * 测试基类
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
@TestExecutionListeners( {})
public abstract class TestBase extends AbstractTransactionalJUnit4SpringContextTests {

    protected MockHttpServletRequest request = new MockHttpServletRequest("POST", "/index.do");
    protected MockHttpServletResponse response = new MockHttpServletResponse();
    protected ModelMap model = new ModelMap();
    private final long costTime = System.currentTimeMillis();

    @Override
    @Autowired
    public void setDataSource(@Qualifier("dataSource") final DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    @Before
    public void setUp() {
        logger.debug("test start");
    }

    @After
    public void tearDown() throws Exception {
        logger.debug("test end");
        logger.debug("cost time: " + (System.currentTimeMillis() - costTime) + "ms");
    }

    @Test
    public void test() {
        try {
            execute();
        } catch (final Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    public abstract void execute() throws Exception;
}
