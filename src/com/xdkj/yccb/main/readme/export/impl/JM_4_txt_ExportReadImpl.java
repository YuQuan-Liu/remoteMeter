package com.xdkj.yccb.main.readme.export.impl;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		
		response.setContentType("text/plain;");
		response.setCharacterEncoding("gbk");
		response.setHeader("Content-Disposition", "attachment; filename=monthpoint" + name_ + ".txt");
		
		try {
			PrintWriter writer = response.getWriter();
			
			// Write data to txt
			ReadView view = null;
			String dfyf = new SimpleDateFormat("yyyyMM").format(new Date());
			for(int i =0;i<list.size();i++){
				view = list.get(i);
//				String line = String.format("%-20s", view.getM_apid())+
//						String.format("%-50s", view.getCustomerAddr())+
//						String.format("%-50s", view.getCustomerName())+
//						String.format("%-10s", dfyf)+
//						String.format("%-10s", "0")+
//						String.format("%-10s", view.getReaddata())+
//						String.format("%-15s", view.getCustomerMobile())+
//						String.format("%-10s", view.getN_remark());
				String line = appendToLength(view.getM_apid(),20)+
						appendToLength(view.getCustomerAddr(),50)+
						appendToLength(view.getCustomerName(),50)+
						appendToLength(dfyf,10)+
						appendToLength("0",10)+
						appendToLength(view.getReaddata()+"",10)+
						appendToLength(view.getCustomerMobile()+"",15)+
						appendToLength(view.getN_remark()+"",10);
				//数据库中小区的备注表示   txt导出时对应的本号
				
				if(i != list.size()-1){
					line += "\r\n";
				}
				
				writer.write(line);
				
				
			}
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将string按照固定的字节数输出  格式为gbk  汉字为2个字节
	 * @param ori
	 * @param length
	 * @return
	 */
	public String appendToLength(String ori,int length){
		String append = ori;
		try {
			byte[] oribytes = ori.getBytes("gbk");
			
			int appendlen = length - oribytes.length;
			for(int i = 0;i < appendlen;i++){
				append += " ";
			}
		} catch (UnsupportedEncodingException e) {
		}
		
		return append;
	}

}
