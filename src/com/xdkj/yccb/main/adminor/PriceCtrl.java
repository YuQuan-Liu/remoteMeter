package com.xdkj.yccb.main.adminor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xdkj.yccb.common.JsonDataUtil;
import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.adminor.dto.PriceKindView;
import com.xdkj.yccb.main.adminor.service.PriceService;
import com.xdkj.yccb.main.entity.Pricekind;

@Controller
public class PriceCtrl {
	@Autowired
	private PriceService priceService;
	
	public static final String priceList = "/adminor/priceList";
	public static final String priceAdd = "";
	@RequestMapping(value="/admin/price/list",method=RequestMethod.GET)
	public String priceList(){
		return priceList;
	}
	
	@RequestMapping(value="/admin/price/listContent",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String priceListContent(PriceKindView pkv, PageBase pb){
		List<PriceKindView> list = priceService.getList(pkv, pb);
		int total = priceService.getTotalCount(pkv);
		return JsonDataUtil.getJsonData(list, total);
	}
	
	@RequestMapping(value="/admin/price/addprice")
	@ResponseBody
	public String addPriceKind(Pricekind pk){
		return priceService.addPriceKind(pk);
	}

}
