package com.xdkj.yccb.main.adminor.dao;

import java.util.List;

import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.adminor.dto.PriceKindView;
import com.xdkj.yccb.main.entity.Pricekind;

public interface PriceKindDAO {
	
	/**
	 * 根据自来水公司的pid  获取自来水公司下的全部单价  显示
	 * @param wcid
	 * @return
	 */
	List<Pricekind> getList(int wcid);
	
	Integer getTotalCount(PriceKindView pkv);
	Integer addPriceKind(Pricekind pk);
	boolean update(Pricekind pk);
	Pricekind getById(int pid);

	String checkPKname(String pkname, int wcid);

	String deletePK(int pid);

	String changepk(int old_, int new_);
}
