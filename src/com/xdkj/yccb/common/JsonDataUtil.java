package com.xdkj.yccb.common;

import java.util.List;

import com.alibaba.fastjson.JSON;

import net.sf.json.JSONArray;
public class JsonDataUtil {
	public static String getJsonData(List<?> list,Integer totalCount){
		if(totalCount == null||totalCount < 0){
			totalCount = 0;
		}
		JSONArray jsonArr = new JSONArray();
		for (Object o : list) {
			jsonArr.add(JSON.toJSONString(o));
		}
		return "{\"total\":"+totalCount+",\"rows\":"+jsonArr.toString()+"}";
	}

}
