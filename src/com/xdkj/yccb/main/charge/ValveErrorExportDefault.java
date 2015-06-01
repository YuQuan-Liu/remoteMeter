package com.xdkj.yccb.main.charge;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.xdkj.yccb.main.charge.dto.ControlErrorView;

public class ValveErrorExportDefault extends AbstractExcelView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {
		
		HSSFSheet sheet;
		HSSFCell cell;
		
		sheet = workbook.createSheet("阀门异常日志");
		sheet.setDefaultColumnWidth(20);
		
		cell = getCell(sheet, 0, 0);
		setText(cell, "用户名");
		cell = getCell(sheet, 0, 1);
		setText(cell, "手机");
		cell = getCell(sheet, 0, 2);
		setText(cell, "楼-单元-户");
		cell = getCell(sheet, 0, 3);
		setText(cell, "集中器地址");
		cell = getCell(sheet, 0, 4);
		setText(cell, "采集器地址");
		cell = getCell(sheet, 0, 5);
		setText(cell, "表地址");
		cell = getCell(sheet, 0, 6);
		setText(cell, "表状态");
		cell = getCell(sheet, 0, 7);
		setText(cell, "阀门状态");
		cell = getCell(sheet, 0, 8);
		setText(cell, "阀门操作");
		cell = getCell(sheet, 0, 9);
		setText(cell, "异常原因");
		cell = getCell(sheet, 0, 10);
		setText(cell, "操作时间");
		
		List<ControlErrorView> list = (List<ControlErrorView>) model.get("list");
		ControlErrorView errorView = null;
		for(int i = 0;i < list.size();i++){
			errorView = list.get(i);
			for(int j = 0;j< 11;j++){
				cell = getCell(sheet, i+1, j);
				switch (j) {
				case 0:
					setText(cell, errorView.getCustomerName()+"");
					break;
				case 1:
					setText(cell, errorView.getCustomerMobile());
					break;
				case 2:
					setText(cell, errorView.getC_num());
					break;
				case 3:
					setText(cell, errorView.getG_addr());
					break;
				case 4:
					setText(cell, errorView.getCollectorAddr()+"");
					break;
				case 5:
					setText(cell, errorView.getMeterAddr());
					break;
				case 6:
					String meterstate = "";
					if(errorView.getMeterState() == 1){
						meterstate = "正常";
					}
					if(errorView.getMeterState() == 2){
						meterstate = "数据错误";
					}
					if(errorView.getMeterState() == 3){
						meterstate = "线路故障";
					}
					if(errorView.getMeterState() == 4){
						meterstate = "超时";
					}
					if(errorView.getMeterState() == 5){
						meterstate = "人工修改";
					}
					setText(cell, meterstate);
					break;
				case 7:
					String valvestate = "";
					if (errorView.getValveState() == 1) {
						valvestate = "开";
					} else {
						if (errorView.getValveState() == 0) {
							valvestate = "关";
						} else {
							valvestate = "异常";
						}
					}
					setText(cell, valvestate);
					break;
				case 8:
					setText(cell, errorView.getSwitch_()+"");
					break;
				case 9:
					setText(cell, errorView.getErrorReason());
					break;
				case 10:
					setText(cell, errorView.getCompleteTime());
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
