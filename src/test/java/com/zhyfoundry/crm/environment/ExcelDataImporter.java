package com.zhyfoundry.crm.environment;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

import com.zhyfoundry.crm.TestBase;
import com.zhyfoundry.crm.TestConstants;
import com.zhyfoundry.crm.entity.Country;
import com.zhyfoundry.crm.entity.Enterprise;
import com.zhyfoundry.crm.service.EnterpriseService;
import com.zhyfoundry.crm.web.BaseController;
import com.zhyfoundry.crm.web.controller.EnterpriseController;

@TransactionConfiguration(defaultRollback = false)
public class ExcelDataImporter extends TestBase {

	private static final String EXCEL_FILE_PATH = "ImportData.xlsx";
//	private static final String EXCEL_FILE_PATH = "D:\\test.xlsx";

	@Autowired
	private EnterpriseController enterpriseController;
	@Autowired
	private EnterpriseService enterpriseService;

	@Override
	public void execute() throws Exception {
//		InputStream inp = new FileInputStream(new File(EXCEL_FILE_PATH));
		InputStream inp = Thread.currentThread().getContextClassLoader().getResourceAsStream(TestConstants.TEST_RESOURCES_PATH_BASE + "environment/" + EXCEL_FILE_PATH);
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
			Enterprise o = new Enterprise();
			o.setKeyword(ImportUtil.getCellValue(row.getCell(j++)));
			o.setCountry(new Country(ImportUtil.getCellValue(row.getCell(j++))));
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
			enterpriseController.add(o, result, request, response, model);
			List<Exception> e = BaseController.getErrors(request);
			if (result.hasErrors() || !e.isEmpty()) {
				logger.warn("插入第" + i + "行数据失败：" + o);
				if (result.hasErrors() ) {
					logger.warn("错误信息：" + result.getAllErrors());
				}
				if (!e.isEmpty()) {
					logger.warn("错误信息：" + e);
				}
			}
		}
	}

	private void cleanUp() {
		enterpriseService.execute("delete from Enterprise");
	}
}
