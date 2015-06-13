package com.xdkj.yccb.main.readme.export;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xdkj.yccb.main.readme.dao.ReadDao;
/**
 * 抄表导出的接口  
 * 实现类的名称命名   如：烟台市自来水   YT2ExportReadImpl  
 * YT 地区名  
 * 2  自来水公司ID 
 * @author Rocket
 *
 */
public interface ExportRead {
	
	public void download(HttpServletRequest request,HttpServletResponse response,List<Integer> nid_list,String name,ReadDao readDao);
}
