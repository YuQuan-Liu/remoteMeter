package com.xdkj.yccb.main.infoin.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.adminor.dao.AdministratorDAO;
import com.xdkj.yccb.main.adminor.dao.DetaildepartDAO;
import com.xdkj.yccb.main.entity.Admininfo;
import com.xdkj.yccb.main.entity.Department;
import com.xdkj.yccb.main.entity.Detaildepart;
import com.xdkj.yccb.main.entity.Gprs;
import com.xdkj.yccb.main.entity.Neighbor;
import com.xdkj.yccb.main.entity.Watercompany;
import com.xdkj.yccb.main.infoin.dao.GprsDAO;
import com.xdkj.yccb.main.infoin.dao.NeighborDAO;
import com.xdkj.yccb.main.infoin.dto.GprsView;
import com.xdkj.yccb.main.infoin.dto.NeighborView;
import com.xdkj.yccb.main.infoin.service.NeighborService;
import com.xdkj.yccb.main.readme.quartz.QuartzManager;
import com.xdkj.yccb.main.statistics.dto.ChargeRate;
import com.xdkj.yccb.main.statistics.dto.MonthSettled;
import com.xdkj.yccb.main.statistics.dto.MonthWaste;
import com.xdkj.yccb.main.statistics.dto.NeighborBalance;
import com.xdkj.yccb.main.statistics.dto.SettledWaste;
import com.xdkj.yccb.main.statistics.dto.SettledWater;
import com.xdkj.yccb.main.statistics.dto.SettledWaterN;
@Service
public class NeighborServiceImpl implements NeighborService {
	@Autowired
	private NeighborDAO neighborDAO;
	@Autowired
	private GprsDAO gprsDAO;
	@Autowired
	private DetaildepartDAO detaildepartDAO;
	@Autowired
	private AdministratorDAO administratorDAO;
	
	public List<NeighborView> getList(int depart_id,int wcid){
		List<Neighbor> list = neighborDAO.getList(depart_id, wcid);
		
		List<NeighborView> listView = new ArrayList<NeighborView>();
		NeighborView nv = null;
		
		for(Neighbor n:list){
			nv = new NeighborView();
			try {
				BeanUtils.copyProperties(nv, n);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			
			listView.add(nv);
		}
		
		return listView;
	}

	@Override
	public String addNeighbor(int adid,int wcid,int depart_id,Neighbor nbr) {
		//此处暂时默认自来水公司（待定）
		Watercompany wc = new Watercompany();
		wc.setPid(wcid);
		nbr.setWatercompany(wc);
		nbr.setValid("1");
		int pid = neighborDAO.addNeighbor(nbr);
		
		if(pid > 0){
			if(depart_id > 0){
				//add to the department 
				Detaildepart detail = new Detaildepart();
				Department depart = new Department();
				depart.setPid(depart_id);
				detail.setDepartment(depart);
				
				detail.setNeighbor(nbr);
				detail.setValid("1");
				detaildepartDAO.addDetaildepart(detail);
			}
			if(nbr.getTimerSwitch() == 1){
				QuartzManager.addJobNeighbor(nbr, administratorDAO.getById(adid));
			}
			
			return "succ";
		}
		return "fail";
	}


	@Override
	public String checkn_name(int wcid, String n_name) {
		
		return neighborDAO.checkn_name(wcid,n_name);
	}
	

	@Override
	public String checkGPRSAddr(String gprsaddr) {
		
		return neighborDAO.checkGPRSAddr(gprsaddr);
	}

	
	@Override
	public String updateNeighbor(Admininfo admin,Neighbor nbr) {
		if(nbr.getTimerSwitch() == 0){
			nbr.setTimer("");
		}
		neighborDAO.update(nbr);
		
		if(nbr.getTimerSwitch() == 0){
			QuartzManager.removeNeighbor(nbr);
		}else{
			QuartzManager.modifyNeighborJobTime(nbr, admin);
		}
		
		return "succ";
	}

	@Override
	public String deleteNbrById(int nbrId) {
		//这边“删除”小区后，集中器级联“删除”，事务能不鞥你回滚有待验证？
		//……
		neighborDAO.delete(nbrId);
//		gprsDAO.deleteByNbrId(nbrId);
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

	@Override
	public String getWaste(int n_id, int year) {
		//获取总表的信息
		List<MonthWaste> list = neighborDAO.getWaste(n_id,year);
		JSONObject jo = new JSONObject();
		JSONArray ja_n = new JSONArray();
		JSONArray ja_s = new JSONArray();
		for(int i = 0;i < 12;i++){
			ja_n.add(i, 0);
			ja_s.add(i, 0);
		}
		MonthWaste monthWaste = null;
		for(int i = 0;i < list.size();i++){
			monthWaste = list.get(i);
			ja_n.set(monthWaste.getMonth()-1, monthWaste.getnRead());
			ja_s.set(monthWaste.getMonth()-1, monthWaste.getSlaveSum());
		}
		jo.put("nread", ja_n);
		jo.put("slaveSum", ja_s);
		return jo.toJSONString();
	}

	@Override
	public String getSettledwater(List<NeighborView> neighbor_list, int year) {
		String ids = "";
		
		if(neighbor_list.size() > 0){
			for(int i = 0;i < neighbor_list.size();i++){
				ids += neighbor_list.get(i).getPid();
				if(i != neighbor_list.size()-1){
					ids += ",";
				}
			}
			return JSON.toJSONString(neighborDAO.getSettledWater(ids,year));
		}
		
		return "";
	}

	@Override
	public String getDrawSettledwater(int n_id, int year) {
		
		List<MonthSettled> list = neighborDAO.getMonthSettled(n_id,year);
		JSONObject jo = new JSONObject();
		JSONArray ja_n = new JSONArray();
		JSONArray ja_s = new JSONArray();
		for(int i = 0;i < 12;i++){
			ja_n.add(i, 0);
			ja_s.add(i, 0);
		}
		MonthSettled monthSettled = null;
		for(int i = 0;i < list.size();i++){
			monthSettled = list.get(i);
			ja_n.set(monthSettled.getMonth()-1, monthSettled.getYl());
			ja_s.set(monthSettled.getMonth()-1, monthSettled.getDemoney());
		}
		jo.put("yl", ja_n);
		jo.put("demoney", ja_s);
		return jo.toJSONString();
	}

	@Override
	public List<SettledWaste> getSettledyl(int n_id, int year) {
		return neighborDAO.getSettledyl(n_id,year);
	}

	@Override
	public String getSettledwaterN(List<NeighborView> neighbor_list, int year) {
		String ids = "";
		
		if(neighbor_list.size() > 0){
			for(int i = 0;i < neighbor_list.size();i++){
				ids += neighbor_list.get(i).getPid();
				if(i != neighbor_list.size()-1){
					ids += ",";
				}
			}
			List<SettledWaterN> list = neighborDAO.getSettledWaterN(ids,year);
			SettledWaterN settledWaterN = null;
			BigDecimal demoney = new BigDecimal(0);
			int yl = 0;
			for(int i = 0;i<list.size();i++){
				settledWaterN = list.get(i);
				demoney = demoney.add(settledWaterN.getDemoney());
				yl += settledWaterN.getYl();
			}
			settledWaterN = new SettledWaterN();
			settledWaterN.setDemoney(demoney);
			settledWaterN.setN_id(0);
			settledWaterN.setYl(yl);
			settledWaterN.setN_name("合计");
			list.add(settledWaterN);
			return JSON.toJSONString(list);
		}
		
		return "";
	}

	@Override
	public List<ChargeRate> getChargeRate(List<NeighborView> neighbor_list,
			int year) {
		String ids = "";
		
		if(neighbor_list.size() > 0){
			for(int i = 0;i < neighbor_list.size();i++){
				ids += neighbor_list.get(i).getPid();
				if(i != neighbor_list.size()-1){
					ids += ",";
				}
			}
			return neighborDAO.getChargeRate(ids,year);
		}
		
		return new ArrayList<>();
	}

	@Override
	public String getDrawChargerate(List<NeighborView> neighbor_list,int year) {
		
		List<ChargeRate> list = getChargeRate(neighbor_list,year);
		
		JSONObject jo = new JSONObject();
		JSONArray ja_n = new JSONArray();
		JSONArray ja_count = new JSONArray();
		JSONArray ja_owecount = new JSONArray();
		JSONArray ja_balance = new JSONArray();
		JSONArray ja_owebalance = new JSONArray();
		
		ChargeRate chargeRate = null;
		for(int i = 0;i<list.size();i++){
			chargeRate = list.get(i);
			ja_n.add(chargeRate.getN_name());
			ja_count.add(chargeRate.getAllcount()-chargeRate.getOwecount());
			ja_owecount.add(chargeRate.getOwecount());
			ja_balance.add(chargeRate.getBalance());
			ja_owebalance.add(chargeRate.getOwebalance());
		}
		
		jo.put("n_name", ja_n);
		jo.put("count", ja_count);
		jo.put("owecount", ja_owecount);
		jo.put("balance", ja_balance);
		jo.put("owebalance", ja_owebalance);
		return jo.toJSONString();
		
	}

	@Override
	public List<Neighbor> getallNbrBywcid(int waterComId) {
		List<Neighbor> list = neighborDAO.getNbrByWatcomId(waterComId);
//		JSONArray ja = new JSONArray();
//		Neighbor n = null;
//		for(int i = 0;i < list.size();i++){
//			n = list.get(i);
//			JSONObject jo = new JSONObject();
//			jo.put("nid", n.getPid());
//			jo.put("name", n.getNeighborName());
//			jo.put("addr", n.getNeighborAddr());
//			ja.add(jo);
//		}
//		return ja.toJSONString();
		return list;
	}


}
