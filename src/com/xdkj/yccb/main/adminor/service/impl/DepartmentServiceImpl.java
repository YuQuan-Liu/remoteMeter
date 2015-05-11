package com.xdkj.yccb.main.adminor.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.adminor.dao.DepartmentDAO;
import com.xdkj.yccb.main.adminor.dao.DetaildepartDAO;
import com.xdkj.yccb.main.adminor.dto.DepartmentView;
import com.xdkj.yccb.main.adminor.service.DepartmentService;
import com.xdkj.yccb.main.entity.Department;
import com.xdkj.yccb.main.entity.Detaildepart;
import com.xdkj.yccb.main.entity.Neighbor;
import com.xdkj.yccb.main.infoin.dao.NeighborDAO;
import com.xdkj.yccb.security.UserForSession;
@Service
public class DepartmentServiceImpl implements DepartmentService {
	@Autowired
	private DepartmentDAO departmentDAO;
	@Autowired
	private NeighborDAO neighborDAO;
	@Autowired
	private DetaildepartDAO detaildepartDAO;

	@Override
	public List<DepartmentView> getList(DepartmentView depview,
			PageBase pageInfo) {
		List<Department> list = departmentDAO.getList(depview, pageInfo);
		List<DepartmentView> listView = new ArrayList<DepartmentView>();
		for (Department department : list) {
			DepartmentView dv = new DepartmentView();
			dv.setPid(department.getPid());
			dv.setDepartmentName(department.getDepartmentName());
			dv.setWatercompany(department.getWatercompany().getCompanyName());
			dv.setRemark(department.getRemark());
			dv.setValid(department.getValid());
			listView.add(dv);
		}
		list=null;
		return listView;
	}

	@Override
	public int getTotalCount(DepartmentView depview) {
		return departmentDAO.getTotalCount(depview);
	}

	@Override
	public String add(Department dep,String[] nbrIds) {
		int depId = departmentDAO.add(dep);
		if(depId>0){
			//保存 片区-小区 关系表
			if(null!=nbrIds&&nbrIds.length>0){
				//String [] ids = nbrIds.split(",");
				for (int i = 0; i < nbrIds.length; i++) {
					Detaildepart dd = new Detaildepart();
					Neighbor nbr = new Neighbor();
					nbr.setPid(Integer.parseInt(nbrIds[i]));
					dd.setDepartment(dep);
					dd.setNeighbor(nbr);
					dd.setValid("1");
					detaildepartDAO.addDetaildepart(dd);
				}
			}
			return "succ";
		}else{
			return "fail";
		}
	}

	@Override
	public String delete(String ids) {
		
		return null;
	}

	@Override
	public String getNbrByCurrUser(UserForSession u,String depId) {
		JSONArray nbrs = new JSONArray();
		List<Detaildepart> dpts = null;
		if(null!=u){
			if(null!=depId&&!"".equals(depId)){
				//获取指定片区下的小区
				dpts = detaildepartDAO.getListByDepId(Integer.parseInt(depId));
			}
			int watComId = u.getWaterComId();
			List<Neighbor> list = neighborDAO.getNbrByWatcomId(watComId);
			for (Neighbor nbr : list) {
				JSONObject n = new JSONObject();
				n.put("id", nbr.getPid());
				n.put("text", nbr.getNeighborName());
				if(null!=dpts&&dpts.size()>0){
					for (Detaildepart dpt : dpts) {
						if(dpt.getNeighbor().getPid()==nbr.getPid()){
							//回填选中
							n.put("selected", true);
						}
					}
				}
				nbrs.add(n);
			}
		}
		return nbrs.toString();
	}

	@Override
	public Department getById(String depId) {
		return departmentDAO.getById(Integer.parseInt(depId));
	}

	@Override
	public String update(Department dep, String[] nbrIds) {
		Department oldDep = departmentDAO.getById(dep.getPid());
		List<Detaildepart> old = new ArrayList<Detaildepart>(oldDep.getDetaildeparts());
		List<Integer> delNbrsIds = new ArrayList<Integer>();
		if(old.size()>0){
			//对比新旧片区执行删除
			for (Detaildepart oldDD : old) {
				int nId = oldDD.getNeighbor().getPid();
				boolean del = true;
				for (int i = 0; i < nbrIds.length; i++) {
					 if(nId==Integer.parseInt(nbrIds[i])){
						 del=false;
						 break;
					 }
				}
				if(del){
					delNbrsIds.add(nId);
				}
			}
		}
		if(delNbrsIds.size()>0){
			detaildepartDAO.deleteBatch(delNbrsIds);
		}
		//对比新旧片区执行添加
		for (int i = 0; i < nbrIds.length; i++) {
			boolean isNew = true;
			for (Detaildepart oldDD : old) {
				if(Integer.parseInt(nbrIds[i])==oldDD.getNeighbor().getPid()){
					isNew=false;
					 break;
				 }
			}
			if(isNew){
				Detaildepart dd = new Detaildepart();
				dd.setDepartment(dep);
				dd.setNeighbor(new Neighbor(Integer.parseInt(nbrIds[i])));
				dd.setValid("1");
				detaildepartDAO.addDetaildepart(dd);
			}
		}
		return "succ";
	}

}
