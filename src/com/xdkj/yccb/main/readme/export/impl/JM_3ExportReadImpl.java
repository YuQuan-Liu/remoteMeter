package com.xdkj.yccb.main.readme.export.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hexiong.jdbf.DBFWriter;
import com.hexiong.jdbf.JDBField;
import com.xdkj.yccb.main.readme.dao.ReadDao;
import com.xdkj.yccb.main.readme.dto.ReadView;
import com.xdkj.yccb.main.readme.export.ExportRead;


public class JM_3ExportReadImpl implements ExportRead {

	
	@Override
	public void download(HttpServletRequest request,
			HttpServletResponse response, List<Integer> nid_list, String name,ReadDao readDao) {
		
		//产生数据  并将数据放到response
		List<ReadView> list = readDao.getAllRemoteMeters(nid_list);
		
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
//			JDBField[] fields = { 
//					new JDBField("sbh", 'C', 11, 0),
//					new JDBField("ysyf", 'C', 6, 0),
//					new JDBField("byzz", 'N', 7, 0),
//					new JDBField("cbbz", 'C', 1, 0),
//					new JDBField("cbrq", 'D', 8, 0)};
			
			JDBField[] fields = { 
					new JDBField("DOWN_ORDER", 'N', 7, 0),
					new JDBField("COPY_ORDER", 'N', 7, 0),
					new JDBField("BH", 'C', 20, 0),
					new JDBField("YBBM", 'C', 20, 0),
					new JDBField("C_CODE", 'C', 20, 0),
					new JDBField("UP_CODE", 'C', 20, 0),
					new JDBField("C_NAME", 'C', 80, 0),
					new JDBField("C_ADDR", 'C', 80, 0),
					new JDBField("C_PHONE", 'C', 20, 0),
					new JDBField("COPIER", 'C', 12, 0),
					new JDBField("LINKMAN", 'C', 12, 0),
					new JDBField("KIND1", 'C', 30, 0),
					new JDBField("GANO", 'N', 6, 0),
					new JDBField("MPNO", 'N', 6, 0),
					new JDBField("PULSE", 'N', 6, 0),
					new JDBField("KJ", 'C', 10, 0),
					new JDBField("COPYMONTH", 'C', 10, 0),
					new JDBField("PPOINT", 'N', 9, 0),
					new JDBField("AVERYSL", 'N', 7, 0),
					new JDBField("SBLC", 'N', 9, 0),
					new JDBField("LJSL", 'N', 7, 0),
					new JDBField("METERKIND", 'N', 3, 0),
					new JDBField("ISYHDB", 'N', 3, 0),
					new JDBField("FIXYSL", 'N', 7, 0),
					new JDBField("XBSCALE", 'N', 6, 2),
					new JDBField("TPOINT", 'N', 9, 0),
					new JDBField("YSL", 'N', 7, 0),
					new JDBField("ISCOPY", 'N', 3, 0),
					new JDBField("COPYDATE", 'C', 10, 0),
					new JDBField("LOW", 'N', 7, 0),
					new JDBField("ZTID", 'N', 4, 0),
					new JDBField("ZTMC", 'C', 20, 0),
					new JDBField("SBDZ", 'C', 40, 0),
					new JDBField("FAC", 'C', 20, 0)};

			DBFWriter dbfwriter = new DBFWriter(sos, fields);

			// Write data to DBF
			ReadView view = null;
			String cbbz = "1";
			SimpleDateFormat dfyf = new SimpleDateFormat("yyyyMM");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for(int i =0;i<list.size();i++){
				view = list.get(i);
				Date readtime = df.parse(view.getReadtime());
				Object[] record = { 
						i,//new JDBField("DOWN_ORDER", 'N', 7, 0),
						i,//new JDBField("COPY_ORDER", 'N', 7, 0),
						"",//new JDBField("BH", 'C', 20, 0),
						view.getM_apid(),//new JDBField("YBBM", 'C', 20, 0),
						view.getM_apid(),//new JDBField("C_CODE", 'C', 20, 0),
						"",//new JDBField("UP_CODE", 'C', 20, 0),
						"",//new JDBField("C_NAME", 'C', 80, 0),
						view.getC_num(),//new JDBField("C_ADDR", 'C', 80, 0),
						"",//new JDBField("C_PHONE", 'C', 20, 0),
						"",//new JDBField("COPIER", 'C', 12, 0),
						"",//new JDBField("LINKMAN", 'C', 12, 0),
						"",//new JDBField("KIND1", 'C', 30, 0),
						0,//new JDBField("GANO", 'N', 6, 0),
						0,//new JDBField("MPNO", 'N', 6, 0),
						0,//new JDBField("PULSE", 'N', 6, 0),
						"",//new JDBField("KJ", 'C', 10, 0),
						dfyf.format(readtime),//new JDBField("COPYMONTH", 'C', 10, 0),
						0,//new JDBField("PPOINT", 'N', 9, 0),
						0,//new JDBField("AVERYSL", 'N', 7, 0),
						0,//new JDBField("SBLC", 'N', 9, 0),
						0,//new JDBField("LJSL", 'N', 7, 0),
						0,//new JDBField("METERKIND", 'N', 3, 0),
						0,//new JDBField("ISYHDB", 'N', 3, 0),
						0,//new JDBField("FIXYSL", 'N', 7, 0),
						0,//new JDBField("XBSCALE", 'N', 6, 2),
						view.getReaddata(),//new JDBField("TPOINT", 'N', 9, 0),
						0,//new JDBField("YSL", 'N', 7, 0),
						0,//new JDBField("ISCOPY", 'N', 3, 0),
						"",//new JDBField("COPYDATE", 'C', 10, 0),
						0,//new JDBField("LOW", 'N', 7, 0),
						0,//new JDBField("ZTID", 'N', 4, 0),
						"1",//new JDBField("ZTMC", 'C', 20, 0),
						"",//new JDBField("SBDZ", 'C', 40, 0),
						view.getMeterAddr()};//new JDBField("FAC", 'C', 20, 0)};
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
