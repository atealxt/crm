package com.zhyfoundry.crm.environment;

import java.io.InputStream;
import java.util.HashMap;

import junit.framework.Assert;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.Validator;

import com.zhyfoundry.crm.TestBase;
import com.zhyfoundry.crm.TestConstants;
import com.zhyfoundry.crm.dao.CountryDao;
import com.zhyfoundry.crm.dao.EnterpriseDao;
import com.zhyfoundry.crm.entity.Country;
import com.zhyfoundry.crm.entity.Enterprise;
import com.zhyfoundry.crm.service.EnterpriseService;
import com.zhyfoundry.crm.web.controller.EnterpriseController;

@TransactionConfiguration(defaultRollback = false)
public class ExcelDataImporter extends TestBase {

	private static final String EXCEL_FILE_PATH = "ImportData.xlsx";
//	private static final String EXCEL_FILE_PATH = "/home/ImportData.xls";

	@Override
	public void execute() throws Exception {
//		InputStream inp = new FileInputStream(new File(EXCEL_FILE_PATH));
		InputStream inp = Thread.currentThread().getContextClassLoader().getResourceAsStream(TestConstants.TEST_RESOURCES_PATH_BASE + "environment/" + EXCEL_FILE_PATH);
		Assert.assertNotNull(inp);
		Workbook wb;
		if (EXCEL_FILE_PATH.endsWith(".xlsx")) {
			wb = new XSSFWorkbook(inp);
		} else {
			wb = new HSSFWorkbook(inp);
		}
		Sheet sheet = wb.getSheetAt(0);
		int rowCount = sheet.getLastRowNum();

		cleanUp();

		for (int i = 1; i <= rowCount; i++) {
			Row row = sheet.getRow(i);
			int j = 2;
			if (ImportUtil.isRowNull(row)) {
				break;
			}
			if (row.getZeroHeight()) {
				continue;
			}
			request.clearAttributes();
			Enterprise o = new Enterprise();
			o.setKeyword(ImportUtil.getCellValue(row.getCell(j++)));
			String countryName = ImportUtil.getCellValue(row.getCell(j++));
			if (StringUtils.isNotBlank(countryName)) {
				o.setCountry(new Country(countryName));
			}
			o.setName(ImportUtil.getCellValue(row.getCell(j++)));
			o.setContact(ImportUtil.getCellValue(row.getCell(j++)));
			o.setEmail(ImportUtil.getCellValue(row.getCell(j++)));
			o.setTel(ImportUtil.getCellValue(row.getCell(j++)));
			o.setMobileNo(ImportUtil.getCellValue(row.getCell(j++)));
			o.setFaxNo(ImportUtil.getCellValue(row.getCell(j++)));
			o.setSource(ImportUtil.getCellValue(row.getCell(j++)));
			o.setRemark(ImportUtil.getCellValue(row.getCell(j++)));
			@SuppressWarnings("rawtypes")
			BindingResult result = new MapBindingResult(new HashMap(), "Enterprise");
			add((i + 1), o, result, request, response, model);
		}
	}

	private void add(int i, Enterprise o, BindingResult result, MockHttpServletRequest request,
			MockHttpServletResponse response, ModelMap model) {
		validator.validate(o, result);
		if (o.getCountry() != null && StringUtils.isNotEmpty(o.getCountry().getName())) {
			validator.validate(o.getCountry(), result);
		}
		if (result.hasErrors()) {
			logger.warn("插入第" + i + "行数据失败：" + o);
			logger.warn("错误信息：" + result.getAllErrors() + "\r\n");
			return;
		}
		Enterprise e = enterpriseDao.findByName(o.getName());
		if (e != null) {
			logger.warn("插入第" + i + "行数据失败：" + o);
			logger.warn("错误信息：该企业名已存在。id = " + e.getId() + ", name = " + e.getName() + "\r\n");
			return;
		}
		if (o.getCountry() != null) {
			Country country = countryDao.findByName(o.getCountry().getName());
			if (country == null) {
				country = new Country(o.getCountry().getName());
				countryDao.save(country);
			}
			o.setCountry(country);
		}
		enterpriseService.merge(o);
	}

	private void cleanUp() {
		enterpriseService.execute("delete from Enterprise");
		enterpriseService.execute("delete from Country");
	}

	@Autowired
	private EnterpriseController enterpriseController;
	@Autowired
	private EnterpriseService enterpriseService;
	@Autowired
	private CountryDao countryDao;
	@Autowired
	private EnterpriseDao enterpriseDao;
	@Autowired
	private Validator validator;
}
