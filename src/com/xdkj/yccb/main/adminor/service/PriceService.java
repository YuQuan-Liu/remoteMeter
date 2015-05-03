package com.xdkj.yccb.main.adminor.service;

import java.util.List;

import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.adminor.dto.PriceKindView;
import com.xdkj.yccb.main.entity.Basicprice;
import com.xdkj.yccb.main.entity.Pricekind;

public interface PriceService {
	/**
	 * 单价列表
	 * @param pkv
	 * @param pb
	 * @return
	 */
	List<PriceKindView> getList(PriceKindView pkv,PageBase pb);
	/**
	 * 单价总条数
	 * @param pkv
	 * @return
	 */
	int getTotalCount(PriceKindView pkv);
	/**
	 * 添加单价
	 * @param pk
	 * @return
	 */
	String addPriceKind(Pricekind pk);
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

}
