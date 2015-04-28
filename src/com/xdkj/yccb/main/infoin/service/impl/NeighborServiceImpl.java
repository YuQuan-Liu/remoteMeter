package com.xdkj.yccb.main.infoin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.entity.Gprs;
import com.xdkj.yccb.main.entity.Neighbor;
import com.xdkj.yccb.main.entity.Watercompany;
import com.xdkj.yccb.main.infoin.dao.GprsDAO;
import com.xdkj.yccb.main.infoin.dao.NeighborDAO;
import com.xdkj.yccb.main.infoin.dto.GprsView;
import com.xdkj.yccb.main.infoin.dto.NeighborView;
import com.xdkj.yccb.main.infoin.service.NeighborService;
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
		return listView;
	}

	@Override
	public String addNeighbor(Neighbor nbr) {
		//此处暂时默认自来水公司（待定）
		Watercompany wc = new Watercompany();
		wc.setPid(1);
		nbr.setWatercompany(wc);
		nbr.setValid("1");
		int pid = neighborDAO.addNeighbor(nbr);
		if(pid>0){
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
	public String deleteNbrById(int nbrId) {
		//neighborDAO.
		return null;
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
	public String deleteGprsById(int gprsId) {
		gprsDAO.deleteGprs(gprsId);
		return "succ";
	}

	@Override
	public List<GprsView> getGprsByNbrId(int nbrId) {
		List<Gprs> list = gprsDAO.getListByNeighborId(nbrId);
		List<GprsView> listView = new ArrayList<GprsView>();
		for (Gprs g : list) {
			GprsView gv = new GprsView();
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
		return listView;
	}

}
