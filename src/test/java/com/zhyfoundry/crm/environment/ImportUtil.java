package com.zhyfoundry.crm.environment;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 * Excel数据导入辅助工具类
 * <p>
 *
 *
 * @author <a href="mailto:lysongfei@gmail.com">songfei</a>
 * @version 3.0, 2012-4-24
 */
public class ImportUtil {

	private static Logger logger = Logger.getLogger(ImportUtil.class);

	public static boolean isRowNull(Row row){
		String cellValue = "";
		String flag = "";
		boolean isNull = false;
		int cellNum = row.getLastCellNum();
		for(int i=0;i<cellNum;i++){
			cellValue = getCellValue(row.getCell(i));
			if(cellValue == null || cellValue.trim().length() == 0 ){
				flag += 1;
			}
		}
		if(flag.length() == cellNum){
			isNull = true;
		}
		return isNull;
	}

	public static String getCellValue(Cell cell) {
		String value = "";
		if (cell != null) {
			if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {// 字符串型
				value = cell.getRichStringCellValue().toString();
			} else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {// 数值型
				if (HSSFDateUtil.isCellDateFormatted(cell)) {//日期型
					 DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					 value = format.format(cell.getDateCellValue());
				} else {//数值型
					value = String.valueOf(cell.getNumericCellValue());
				}
			} else if (cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {// 是否为布尔型
				value = Boolean.toString(cell.getBooleanCellValue());
			} else if(cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA){
				value = cell.getCellFormula();
			}
		}
		return value.trim();
	}

	 /**
     * 整理指定目录中的数据文件
     * HashMap<String,String>  1：文件名称(含扩展名) 2:文件绝对路径
     * @param dir 			数据文件目录
     * @return 数据文件集合
     */
    public static HashMap<String,String> getMatchFiles(File dir) {
    	long start = System.currentTimeMillis();
    	 HashMap<String, String> fileNames = new LinkedHashMap<String, String>();
         File[] dataFiles = null;
         if(dir.exists() && dir.isDirectory()){
         	dataFiles = dir.listFiles();
         	for(int i=0;i<dataFiles.length;i++){
         		if(dataFiles[i].isDirectory()){
         			String[] fnames = dataFiles[i].list();
         			for(int j=0;j<fnames.length;j++){
         				fileNames.put(FilenameUtils.getName(fnames[j]),dataFiles[i].getAbsolutePath()+File.separator+fnames[j]);
         			}
         		}else{
         			String fname = dataFiles[i].getAbsolutePath();
         			fileNames.put(FilenameUtils.getName(fname),fname);
         		}
         	}
         }
        long end = System.currentTimeMillis();
        logger.info("匹配文件耗时：" +(end-start) + "毫秒");
        return fileNames;
    }
}
