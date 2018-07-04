package com.xdkj.yccb.main.logger;

/**
 * 记录日志实例类
 * @author Rocket
 *
 */
public class ActionLog {

	private int adminid;
	private int type;
	private String actionname;
	private String remark;
	public int getAdminid() {
		return adminid;
	}
	public void setAdminid(int adminid) {
		this.adminid = adminid;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
		String action = "";
		switch(type){
			case 1 : action = "添加管理员"; break;
			case 2 : action = "删除管理员"; break;
			case 3 : action = "更改角色"; break;
			case 4 : action = "更改片区"; break;
			case 5 : action = "片区添加"; break;
			case 6 : action = "片区删除"; break;
			case 7 : action = "单价添加"; break;
			case 8 : action = "单价删除"; break;
			case 9 : action = "调整单价"; break;
			case 10: action = "小区添加"; break;
			case 11: action = "小区删除"; break;
			case 12: action = "集中器添加"; break;
			case 13: action = "集中器删除"; break;
			case 14: action = "用户添加"; break;
			case 15: action = "用户删除"; break;
			case 16: action = "表添加"; break;
			case 17: action = "表删除"; break;
			case 18: action = "用户表批量添加"; break;
			case 19: action = "开阀关阀  -x"; break;
			case 20: action = "人工修改  -x"; break;
			case 21: action = "Excel上传-非远传"; break;
			case 22: action = "预后付费转换"; break;
			case 23: action = "更新单价"; break;
			case 24: action = "水费减免"; break;
			case 25: action = "撤销交费"; break;
			case 26: action = "撤销扣费"; break;
			case 27: action = "换表"; break;
			case 28: action = "login"; break;
			case 29: action = "logout"; break;
			case 30: action = "调整表具"; break;
			case 31: action = "更新扣费读数"; break;
			case 32: action = "记录减免吨数"; break;
			case 33: action = "扣费记录转到虚表吨数"; break;
		}
		this.actionname = action;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public ActionLog(int adminid, int type, String remark) {
		super();
		this.adminid = adminid;
		this.type = type;
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "[adminid=" + adminid + ", actionname=" + actionname + ", remark=" + remark + "]";
	}
	
}
