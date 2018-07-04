package com.xdkj.yccb.main.logger;

/**
 * 记录日志实例类
 * @author Rocket
 *
 */
public class ActionLog {

	private int adminid;
	private int type;
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
		return "[adminid=" + adminid + ", type=" + type + ", remark=" + remark + "]";
	}
	
}
