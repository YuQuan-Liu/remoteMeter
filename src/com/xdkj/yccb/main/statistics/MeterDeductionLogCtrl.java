package com.xdkj.yccb.main.statistics;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.xdkj.yccb.main.statistics.service.MeterDeductionLogService;

/**
 * 
 * Description: 结算用水统计controller
 * @author SongWei
 * @date 2015-5-23
 * @version 1.0
 */
@Controller
public class MeterDeductionLogCtrl {
	@Autowired
	private MeterDeductionLogService meterDeductionLogService;
	
	@RequestMapping(value="/statistics/meterDeLog/list")
	public String meterDeLogList(HttpServletRequest request,Model model){
		
		return "/statistics/meterDeLog";
	}
	
	@RequestMapping(value="/statistics/meterDeLog/ListData",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String ListData(){
		
		return JSON.toJSONString("");
	}
	

}
