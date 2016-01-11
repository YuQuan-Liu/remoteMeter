package com.xdkj.yccb.main.readme.import_.impl;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hexiong.jdbf.DBFReader;
import com.hexiong.jdbf.JDBFException;
import com.xdkj.yccb.main.entity.Meter;
import com.xdkj.yccb.main.entity.Readlog;
import com.xdkj.yccb.main.entity.Readmeterlog;
import com.xdkj.yccb.main.readme.import_.NonRemoteUpload;
import com.xdkj.yccb.main.readme.service.MeterService;
import com.xdkj.yccb.main.readme.service.impl.MeterServiceImpl;

public class JM_NonRemoteReadUpload implements NonRemoteUpload{

	public List<Readmeterlog> read(String excelPath,int readlogid,int n_id,MeterService meterService){
		InputStream input = null;
		DBFReader reader = null;
		try {
			input = new FileInputStream(excelPath);
			reader = new DBFReader(input);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		List<Readmeterlog> list = new ArrayList<>();
		Readlog readlog = new Readlog();
		readlog.setPid(readlogid);
		Readmeterlog readmeterlog = null;
		Object[] rowobj = null;
		
		try {
			while((rowobj = reader.nextRecord()) != null){
				//3 YBBM   4 C_CODE  25 TPOINT
//				System.out.println((String) rowobj[3]);  //YBBM
//				System.out.println((String) rowobj[4]);
//				System.out.println(n_id);
//				System.out.println(rowobj[25]);  //TPOINT
//				
				//get the meter id
				Meter m = meterService.getMeterbyAPID((String) rowobj[4],n_id);
				
				if(m != null){
					readmeterlog = new Readmeterlog();
					readmeterlog.setActionResult((int)(long)rowobj[25]);
					readmeterlog.setActionType((byte)1);
					readmeterlog.setMeter(m);
					readmeterlog.setReadlog(readlog);
					readmeterlog.setRemark("");
					list.add(readmeterlog);
				}
			}
		} catch (JDBFException e) {
			e.printStackTrace();
		}
		return list;
	}
}
