package com.xdkj.yccb.main.infoin.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.entity.Gprs;
import com.xdkj.yccb.main.entity.Neighbor;
import com.xdkj.yccb.main.entity.Watercompany;
import com.xdkj.yccb.main.infoin.dao.GprsDAO;
import com.xdkj.yccb.main.infoin.dao.NeighborDAO;
import com.xdkj.yccb.main.infoin.dto.GprsView;
import com.xdkj.yccb.main.infoin.dto.NeighborView;
import com.xdkj.yccb.main.infoin.service.NeighborService;
import com.xdkj.yccb.main.statistics.dto.NeighborBalance;
@Service
public class NeighborServiceImpl implements NeighborService {
	@Autowired
	private NeighborDAO neighborDAO;
	@Autowired
	private GprsDAO gprsDAO;

	@Override
	public List<NeighborView> getList(NeighborView nbr, PageBase pb) {
		List<Neighbor> list = neighborDAO.getList(nbr, pb);
		List<NeighborView> listView = new ArrayList<NeighborView>();
		for (Neighbor ngbr : list) {
			NeighborView nv = new NeighborView();
			nv.setPid(ngbr.getPid());
			nv.setIp(ngbr.getIp());
			nv.setMainMeter(ngbr.getMainMeter());
			nv.setNeighborAddr(ngbr.getNeighborAddr());
			nv.setNeighborName(ngbr.getNeighborName());
			nv.setRemark(ngbr.getRemark());
			nv.setTimer(ngbr.getTimer());
			nv.setTimerSwitch(ngbr.getTimerSwitch());
			nv.setValid(ngbr.getValid());
			nv.setWatercompany(ngbr.getWatercompany().getCompanyName());
			listView.add(nv);
		}
		list = null;
		return listView;
	}
	
	public List<NeighborView> getList(int depart_id,int wcid){
		List<Neighbor> list = neighborDAO.getList(depart_id, wcid);
		
		List<NeighborView> listView = new ArrayList<NeighborView>();
		NeighborView nv = null;
		
		for(Neighbor n:list){
			nv = new NeighborView();
			try {
				BeanUtils.copyProperties(nv, n);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			listView.add(nv);
		}
		
		return listView;
//		if(depart_id == 0){
//			return neighborDAO.getList(depart_id, wcid);
//		}else{
//			List<Neighbor> list = new ArrayList<>();
//			for (Object detail : neighborDAO.getList(depart_id, wcid)) {
//				list.add(((Detaildepart)detail).getNeighbor());
//			}
//			return list;
//		}
		
		
	}

	@Override
	public String addNeighbor(Neighbor nbr) {
		//此处暂时默认自来水公司（待定）
		Watercompany wc = new Watercompany();
		wc.setPid(1);
		nbr.setWatercompany(wc);
		nbr.setValid("1");
		int pid = neighborDAO.addNeighbor(nbr);
		if(pid > 0){
			return "succ";
		}
		return "fail";
	}

	@Override
	public String updateNeighbor(Neighbor nbr) {
		neighborDAO.update(nbr);
		return "succ";
	}

	@Override
	public String deleteNbrById(String nbrId) {
		//这边“删除”小区后，集中器级联“删除”，事务能不鞥你回滚有待验证？
		//……
		neighborDAO.delete(nbrId);
		gprsDAO.deleteByNbrId(nbrId);
		return "succ";
	}

	@Override
	public Neighbor getNbrById(int nbrId) {
		return neighborDAO.getById(nbrId);
	}
	
	@Override
	public int getCount(Neighbor nv, PageBase pb) {
		return neighborDAO.getTotalCount(nv, pb);
	}
	
	@Override
	public String addGprs(Gprs gprs) {
		gprs.setValid("1");
		gprs.setInstallTime(new Date());
		//此处还应设置安装人为默认当前用户--待完善
		//……
		int pid = gprsDAO.addGprs(gprs);
		if(pid>0){
			return "succ";
		}
		return "fail";
	}

	@Override
	public String updateGprs(Gprs gprs) {
		gprsDAO.update(gprs);
		return "succ";
	}

	@Override
	public Gprs getGprsById(int gprsId) {
		return gprsDAO.getById(gprsId);
	}

	@Override
	public String deleteGprsById(String gprsId) {
		gprsDAO.deleteGprs(Integer.parseInt(gprsId));
		return "succ";
	}

	@Override
	public List<GprsView> getGprsByNbrId(int nbrId) {
		List<Gprs> list = gprsDAO.getListByNeighborId(nbrId);
		List<GprsView> listView = new ArrayList<GprsView>();
		for (Gprs g : list) {
			GprsView gv = new GprsView();
			gv.setPid(g.getPid());
			gv.setGprsaddr(g.getGprsaddr());
			gv.setGprsprotocol(g.getGprsprotocol());
			gv.setGprstel(g.getGprstel());
			gv.setInstallAddr(g.getInstallAddr());
			gv.setInstallPerson(g.getInstallPerson());
			gv.setInstallTime(g.getInstallTime());
			gv.setIp(g.getIp());
			gv.setPort(g.getPort());
			gv.setRemark(g.getRemark());
			gv.setValid(g.getValid());
			listView.add(gv);
		}
		list = null;
		return listView;
	}

	@Override
	public List<NeighborBalance> getNeighborBalance(int n_id) {
		
		return neighborDAO.getNeighborBalance(n_id);
	}

	@Override
	public List<Map> getLous(int n_id) {
		List<String> list = neighborDAO.getLous(n_id);
		List<Map> result = new ArrayList<>();
		Map map = null;
		for(int i = 0;i < list.size();i++){
			map = new HashMap();
			map.put("lou", list.get(i));
			map.put("id", i+"");
			result.add(map);
		}
		
		return result;
	}

	@Override
	public List<Map> getDys(int n_id, String lou) {
		List<String> list = neighborDAO.getDys(n_id,lou);
		List<Map> result = new ArrayList<>();
		Map map = null;
		for(int i = 0;i < list.size();i++){
			map = new HashMap();
			map.put("dy", list.get(i));
			map.put("id", i+"");
			result.add(map);
		}
		
		return result;
	}

}
