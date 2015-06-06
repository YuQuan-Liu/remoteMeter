package com.xdkj.yccb.main.adminor.service;

import java.util.List;

import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.adminor.dto.BasicpriceValues;
import com.xdkj.yccb.main.adminor.dto.BasicpriceView;
import com.xdkj.yccb.main.adminor.dto.PriceKindView;
import com.xdkj.yccb.main.entity.Basicprice;
import com.xdkj.yccb.main.entity.Pricekind;

public interface PriceService {
	
	/**
	 * 根据自来水公司的pid  获取自来水公司下的全部单价  显示
	 * @param wcid
	 * @return
	 */
	public List<PriceKindView> getList(int wcid) ;
	/**
	 * 单价总条数
	 * @param pkv
	 * @return
	 */
	int getTotalCount(PriceKindView pkv);
	/**
	 * 添加单价
	 * @param pk 单价
	 * @param bpv 基本单价值
	 * @return
	 */
	String addPriceKind(Pricekind pk,BasicpriceValues bpv);
	/**
	 * 更新单价
	 * @param pk
	 * @return
	 */
	String updatePriceKind(Pricekind pk);
	/**
	 * 添加基本单价
	 * @param bp
	 * @return
	 */
	String addBasicPrice(Basicprice bp);
	/**
	 * 删除基本单价
	 * @param bpId
	 * @return
	 */
	String deleteBasicPrice(String bpId);
	/**
	 * 根据单价id查询基本单价列表
	 * @param priceKindId 单价id
	 * @return
	 */
	List<BasicpriceView> getListByPriceKindId(int priceKindId);
	/**
	 * 获取单价信息
	 * @param pid 单价id
	 * @return
	 */
	Pricekind getById(int pid);
	/**
	 * 自来水下是否已近有这个单价名了
	 * @param pkname
	 * @param wcid
	 * @return
	 */
	public String checkPKname(String pkname, int wcid);
	/**
	 * 删除单价
	 * @param pid
	 * @return
	 */
	public String deletePK(int pid);
	/**
	 * 调整单价  
	 * @param old_
	 * @param new_
	 * @return
	 */
	public String changepk(int old_, int new_);

}
