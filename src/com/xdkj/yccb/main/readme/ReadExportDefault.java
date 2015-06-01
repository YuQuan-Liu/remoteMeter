package com.xdkj.yccb.main.readme;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.xdkj.yccb.main.readme.dto.ReadView;

@Component
public class ReadExportDefault extends AbstractExcelView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		HSSFSheet sheet;
		HSSFCell cell;
		
		sheet = workbook.createSheet("水表信息");
		sheet.setDefaultColumnWidth(20);
		
		cell = getCell(sheet, 0, 0);
		setText(cell, "用户ID");
		cell = getCell(sheet, 0, 1);
		setText(cell, "用户名");
		cell = getCell(sheet, 0, 2);
		setText(cell, "手机");
		cell = getCell(sheet, 0, 3);
		setText(cell, "楼-单元-户");
		cell = getCell(sheet, 0, 4);
		setText(cell, "水表ID");
		cell = getCell(sheet, 0, 5);
		setText(cell, "集中器地址");
		cell = getCell(sheet, 0, 6);
		setText(cell, "采集器地址");
		cell = getCell(sheet, 0, 7);
		setText(cell, "表地址");
		cell = getCell(sheet, 0, 8);
		setText(cell, "表读数");
		cell = getCell(sheet, 0, 9);
		setText(cell, "抄表时间");
		
		
		List<ReadView> list = (List<ReadView>) model.get("list");
		ReadView readView = null;
		for(int i = 0;i < list.size();i++){
			readView = list.get(i);
			for(int j = 0;j< 10;j++){
				cell = getCell(sheet, i+1, j);
				switch (j) {
				case 0:
					setText(cell, readView.getC_id()+"");
					break;
				case 1:
					setText(cell, readView.getCustomerName());
					break;
				case 2:
					setText(cell, readView.getCustomerMobile());
					break;
				case 3:
					setText(cell, readView.getC_num());
					break;
				case 4:
					setText(cell, readView.getM_id()+"");
					break;
				case 5:
					setText(cell, readView.getG_addr());
					break;
				case 6:
					setText(cell, readView.getCollectorAddr());
					break;
				case 7:
					setText(cell, readView.getMeterAddr());
					break;
				case 8:
					setText(cell, readView.getReaddata()+"");
					break;
				case 9:
					setText(cell, readView.getReadtime());
					break;
				default:
					break;
				}
			}
		}
		
//		new SimpleDateFormat("yyyy_MM_dd").format(new Date());
		//时间戳+小区名
		String name = new SimpleDateFormat("yyyy_MM_dd").format(new Date())+new String(model.get("n_name").toString().getBytes("utf-8"), "ISO8859_1");
		
		response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + name + ".xls" + "\"");
		
	}

	

}
