package com.xdkj.yccb.main.infoin;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.xdkj.yccb.main.infoin.dto.CustomerMeter;

public class UploadCustomer {

//	private String excelPath;
//	public UploadCustomer(String excelPath){
//		this.excelPath = excelPath;
//	}
	
	public static Map readExcel(String excelPath){
		InputStream input = null;
		HSSFWorkbook wb = null;
		Sheet sheet = null;
		try {
			input = new FileInputStream(excelPath);
			wb = new HSSFWorkbook(input);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		sheet = wb.getSheetAt(0);
		Row row = sheet.getRow(0);
		String n_name = getCellString(row,0);
		String g_addr = getCellString(row, 1);
		int count = Integer.parseInt(getCellString(row, 2));
		
		List<CustomerMeter> list = new ArrayList<>();
		for(int i = 2;i < count+2;i++){
			row = sheet.getRow(i);
			Map map = new HashMap<>();
			for(int j = 0;j < 32;j++){
//				System.out.println(getCellString(row, j));
				switch (j) {
				case 0:
					map.put("customerName", getCellString(row, j));
					break;
				case 1:
					map.put("c_apid", getCellString(row, j));
					break;
				case 2:
					map.put("customerMobile", getCellString(row, j));
					break;
				case 3:
					map.put("customerEmail", getCellString(row, j));
					break;
				case 4:
					map.put("nationalId", getCellString(row, j));
					break;
				case 5:
					map.put("customerAddr", getCellString(row, j));
					break;
				case 6:
					map.put("louNum", getCellString(row, j));
					break;
				case 7:
					map.put("dyNum", getCellString(row, j));
					break;
				case 8:
					map.put("huNum", getCellString(row, j));
					break;
				case 9:
					map.put("prePaySign", getCellString(row, j));
					break;
				case 10:
					map.put("customerBalance", getCellString(row, j));
					break;
				case 11:
					map.put("warnThre", getCellString(row, j));
					break;
				case 12:
					map.put("warnStyle", getCellString(row, j));
					break;
				case 13:
					map.put("warnSwitch", getCellString(row, j));
					break;
				case 14:
					map.put("hkid", getCellString(row, j));
					break;
				case 15:
					map.put("steelNum", getCellString(row, j));
					break;
				case 16:
					map.put("collectorAddr", getCellString(row, j));
					break;
				case 17:
					map.put("meterAddr", getCellString(row, j));
					break;
				case 18:
					map.put("qfh", getCellString(row, j));
					break;
				case 19:
					map.put("m_apid", getCellString(row, j));
					break;
				case 20:
					map.put("pkid", getCellString(row, j));
					break;
				case 21:
					map.put("mainMeter", getCellString(row, j));
					break;
				case 22:
					map.put("suppleMode", getCellString(row, j));
					break;
				case 23:
					map.put("mkid", getCellString(row, j));
					break;
				case 24:
					map.put("meterSolid", getCellString(row, j));
					break;
				case 25:
					map.put("lihu", getCellString(row, j));
					break;
				case 26:
					map.put("isValve", getCellString(row, j));
					break;
				case 27:
					map.put("deductionStyle", getCellString(row, j));
					break;
				case 28:
					map.put("valveOffthre", getCellString(row, j));
					break;
				case 29:
					map.put("timerSwitch", getCellString(row, j));
					break;
				case 30:
					map.put("timer", getCellString(row, j));
					break;
				case 31:
					map.put("overflow", getCellString(row, j));
					break;
				default:
					break;
				};
			}
			CustomerMeter cm = new CustomerMeter();
			try {
				ConvertUtils.register(new BigDecimalConverter(), BigDecimal.class);
				BeanUtils.populate(cm, map);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			list.add(cm);
		}
		Map data = new HashMap<>();
		data.put("n_name", n_name);
		data.put("g_addr", g_addr);
		data.put("list", list);
		return data;
	}
	public static String getCellString(Row row,int s){
		String str = "";
		Cell cell = row.getCell(s);
		if(cell != null){
			switch (cell.getCellType()){
			case Cell.CELL_TYPE_STRING:
				str = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_NUMERIC:
				str = String.valueOf((long)cell.getNumericCellValue());
				break;
			case Cell.CELL_TYPE_FORMULA:
				str = cell.getStringCellValue();
				break;
				default:
				str = cell.getStringCellValue();
				break;
			}
		}
		return str;
	}
}
