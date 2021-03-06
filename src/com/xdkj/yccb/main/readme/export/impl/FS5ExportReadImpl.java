package com.xdkj.yccb.main.readme.export.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import com.xdkj.yccb.main.readme.dao.ReadDao;
import com.xdkj.yccb.main.readme.dto.ReadView;
import com.xdkj.yccb.main.readme.export.ExportRead;


public class FS5ExportReadImpl implements ExportRead {

	
	@Override
	public void download(HttpServletRequest request,
			HttpServletResponse response, List<Integer> nid_list, String name,ReadDao readDao) {
		
		//产生数据  并将数据放到response
		String name_ = new SimpleDateFormat("yyyy_MM_dd").format(new Date());
		try {
			name_ = new SimpleDateFormat("yyyy_MM_dd").format(new Date())+new String(name.getBytes("utf-8"), "ISO8859_1");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		response.setContentType("application/octet-stream;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment; filename=" + name_ + ".xls");
		
		ServletOutputStream sos = null;
		try {
			sos = response.getOutputStream();
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("Sheet1");
			HSSFRow row = sheet.createRow(0);
			row.createCell(0).setCellValue("水表号");   //水表apid
			row.createCell(1).setCellValue("本期指针");
			row.createCell(2).setCellValue("抄表月份");
			
			List<ReadView> list =readDao.getAllRemoteMeters(nid_list);
			SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
			SimpleDateFormat df_ = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			ReadView readView = null;
			for(int i = 1;i<= list.size();i++){
				readView = list.get(i-1);
				row = sheet.createRow(i);
				row.createCell(0).setCellValue(readView.getM_apid()==null?"":readView.getM_apid());
				row.createCell(1).setCellValue(readView.getReaddata());
				row.createCell(2).setCellValue(df.format(df_.parse(readView.getReadtime())));
			}
			wb.write(sos);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(sos != null){
					sos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
