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
			dv.setRemark(department.getRemark());
			dv.setValid(department.getValid());
			listView.add(dv);
		}
		return listView;
	}

	@Override
	public int getTotalCount(DepartmentView depview) {
		return departmentDAO.getTotalCount(depview);
	}

	@Override
	public String add(Department dep) {
		if(departmentDAO.add(dep)>0){
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

}
