package com.xdkj.yccb.main.readme.export.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hexiong.jdbf.DBFWriter;
import com.hexiong.jdbf.JDBField;
import com.xdkj.yccb.main.readme.dao.ReadDao;
import com.xdkj.yccb.main.readme.dao.impl.ReadDaoImpl;
import com.xdkj.yccb.main.readme.dto.ReadView;
import com.xdkj.yccb.main.readme.export.ExportRead;


public class YT2ExportReadImpl implements ExportRead {

	
	@Override
	public void download(HttpServletRequest request,
			HttpServletResponse response, List<Integer> nid_list, String name,ReadDao readDao) {
		
		//产生数据  并将数据放到response
		List<ReadView> list = readDao.getYT2Data(nid_list);
		
		String name_ = new SimpleDateFormat("yyyy_MM_dd").format(new Date());
		try {
			name_ = new SimpleDateFormat("yyyy_MM_dd").format(new Date())+new String(name.getBytes("utf-8"), "ISO8859_1");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		response.setContentType("application/octet-stream;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment; filename=" + name_ + ".dbf");
		
		ServletOutputStream sos = null;
		try {
			sos = response.getOutputStream();
			
			// Defined Style of dbf
			JDBField[] fields = { 
					new JDBField("sbh", 'C', 11, 0),
					new JDBField("ysyf", 'C', 6, 0),
					new JDBField("byzz", 'N', 7, 0),
					new JDBField("cbbz", 'C', 1, 0),
					new JDBField("cbrq", 'D', 8, 0)};

			DBFWriter dbfwriter = new DBFWriter(sos, fields);

			// Write data to DBF
			ReadView view = null;
			String cbbz = "1";
			SimpleDateFormat dfyf = new SimpleDateFormat("yyMMdd");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for(int i =0;i<list.size();i++){
				view = list.get(i);
				if(view.getMeterState() == 5){
					cbbz = "2";
				}else{
					cbbz = "1";
				}
				
				
				Date readtime = df.parse(view.getReadtime());
				Object[] record = { 
						view.getM_apid(), 
						dfyf.format(readtime),
						view.getReaddata(),
						cbbz,
						readtime };
				dbfwriter.addRecord(record);
			}
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
