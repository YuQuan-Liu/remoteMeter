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
import com.xdkj.yccb.main.entity.Watercompany;
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
	public List<DepartmentView> getList(int wcid) {
		List<Department> list = departmentDAO.getList(wcid);
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
	public String add(int wcid,String name,String remark,int[] nbr_ids) {
		Department dep = new Department();
		dep.setDepartmentName(name);
		dep.setRemark(remark);
		dep.setValid("1");
		Watercompany wc = new Watercompany();
		wc.setPid(wcid);
		dep.setWatercompany(wc);
		int depId = departmentDAO.add(dep);
		if(depId>0){
			//保存 片区-小区 关系表
//			if(null!=nbrIds&&nbrIds.length>0){
//				//String [] ids = nbrIds.split(",");
//				for (int i = 0; i < nbrIds.length; i++) {
//					Detaildepart dd = new Detaildepart();
//					Neighbor nbr = new Neighbor();
//					nbr.setPid(Integer.parseInt(nbrIds[i]));
//					dd.setDepartment(dep);
//					dd.setNeighbor(nbr);
//					dd.setValid("1");
//					detaildepartDAO.addDetaildepart(dd);
//				}
//			}
//			
			detaildepartDAO.addDetaildeparts(dep,nbr_ids);
			return "succ";
		}else{
			return "fail";
		}
	}


	@Override
	public Department getById(String depId) {
		return departmentDAO.getById(Integer.parseInt(depId));
	}

	

	@Override
	public String checkdepname(int wcid, String name) {
		
		return departmentDAO.checkdepname(wcid,name);
	}

	@Override
	public String deletedep(int pid) {
		
		return departmentDAO.deletedep(pid);
	}

	@Override
	public String deletedetail(int dep_id, int n_id) {
		
		return detaildepartDAO.deleteDetaildepart(dep_id,n_id);
	}

	@Override
	public String adddetail(int dep_id, int n_id) {
		
		Detaildepart detail = detaildepartDAO.getDetailBy(dep_id,n_id);
		if(detail == null){
			detail = new Detaildepart();
		}
		detail.setValid("1");
		
		Department dep = new Department();
		dep.setPid(dep_id);
		Neighbor n = new Neighbor();
		n.setPid(n_id);
		detail.setDepartment(dep);
		detail.setNeighbor(n);
		return detaildepartDAO.addDetaildepart(detail)+"";
	}


}
