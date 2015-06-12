package com.xdkj.yccb.main.readme.export.impl;

import java.io.IOException;
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
import com.xdkj.yccb.main.readme.dto.ReadView;
import com.xdkj.yccb.main.readme.export.ExportRead;

@Component
public class YT2ExportReadImpl implements ExportRead {

	@Autowired
	private ReadDao readDao;
	@Override
	public void download(HttpServletRequest request,
			HttpServletResponse response, List<Integer> nid_list, String name) {
		
		//产生数据  并将数据放到response
		
		List<ReadView> list = readDao.getYT2Data(nid_list);
		
		response.setContentType("application/octet-stream;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment; filename=" + name + ".dbf");
		
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
			int cbbz = 1;
			SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
			for(int i =0;i<list.size();i++){
				view = list.get(i);
				if(view.getMeterState() == 5){
					cbbz = 2;
				}else{
					cbbz = 1;
				}
				Object[] record = { 
						view.getM_apid(), 
						df.format(new Date(view.getReadtime())),
						view.getReaddata(),
						cbbz,
						new Date(view.getReadtime()) };
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
