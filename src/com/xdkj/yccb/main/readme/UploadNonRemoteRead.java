package com.xdkj.yccb.main.readme;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.xdkj.yccb.main.entity.Meter;
import com.xdkj.yccb.main.entity.Readlog;
import com.xdkj.yccb.main.entity.Readmeterlog;

public class UploadNonRemoteRead {

	public static List<Readmeterlog> readExcel(String excelPath,int readlogid){
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
		List<Readmeterlog> list = new ArrayList<>();
		Row row = null;
		Readlog readlog = new Readlog();
		readlog.setPid(readlogid);
		Readmeterlog readmeterlog = null;
		for(int i = 1;i <= sheet.getLastRowNum();i++){
			row = sheet.getRow(i);
			int mid = Integer.parseInt(getCellString(row, 4));
			int result = Integer.parseInt(getCellString(row, 7));
			
			
			Meter m = new Meter();
			m.setPid(mid);
			
			readmeterlog = new Readmeterlog();
			readmeterlog.setActionResult(result);
			readmeterlog.setActionType((byte)1);
			readmeterlog.setMeter(m);
			readmeterlog.setReadlog(readlog);
			readmeterlog.setRemark("");
			list.add(readmeterlog);
		}
		return list;
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
			}
		}
		return str;
	}
}
