package com.xdkj.yccb.main.adminor.dao;

import java.util.List;

import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.adminor.dto.PriceKindView;
import com.xdkj.yccb.main.entity.Pricekind;

public interface PriceKindDAO {
	List<Pricekind> getList(PriceKindView pkv,PageBase pb);
	Integer getTotalCount(PriceKindView pkv);
	Integer addPriceKind(Pricekind pk);
	boolean update(Pricekind pk);
}
