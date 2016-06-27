package com.xdkj.yccb.main.readme.export.impl;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import com.hexiong.jdbf.DBFWriter;
import com.hexiong.jdbf.JDBField;
import com.xdkj.yccb.main.readme.dao.ReadDao;
import com.xdkj.yccb.main.readme.dto.ReadView;
import com.xdkj.yccb.main.readme.export.ExportRead;


public class JM_4_txt_ExportReadImpl implements ExportRead {

	
	@Override
	public void download(HttpServletRequest request,
			HttpServletResponse response, List<Integer> nid_list, String name,ReadDao readDao) {
		
		//产生数据  并将数据放到response
		List<ReadView> list = readDao.getAllRemoteMeters(nid_list);
		
		String name_ = new SimpleDateFormat("yyyyMMdd").format(new Date());
//		try {
//			name_ = new SimpleDateFormat("yyyy_MM_dd").format(new Date())+new String(name.getBytes("utf-8"), "ISO8859_1");
//		} catch (UnsupportedEncodingException e1) {
//			e1.printStackTrace();
//		}
		
		response.setContentType("text/plain");
		response.setHeader("Content-Disposition", "attachment; filename=monthpoint" + name_ + ".txt");
		
		ServletOutputStream sos = null;
		BufferedOutputStream buf = null;
		try {
			sos = response.getOutputStream();
			
			buf = new BufferedOutputStream(sos);

			// Write data to txt
			ReadView view = null;
			String dfyf = new SimpleDateFormat("yyyyMM").format(new Date());
			for(int i =0;i<list.size();i++){
				view = list.get(i);
				String line = String.format("%-20s", view.getM_apid())+
						String.format("%-50s", view.getCustomerName())+
						String.format("%-10s", dfyf)+
						String.format("%-10s", "0")+
						String.format("%-10s", view.getReaddata())+
						String.format("%-15s", view.getCustomerMobile())+
						String.format("%-10s", view.getN_remark())+"\r\n";
				buf.write(line.getBytes());
				
			}
			buf.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(sos != null){
					sos.close();
				}
				if(buf != null){
					buf.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
