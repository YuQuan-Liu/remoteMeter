package com.xdkj.yccb.main.sys.dto;

public class AuthorityView {
	private Integer pid;
	private Integer ppid;//父级权限,根权限菜单该值为0
	private String authorityCode;//权限编码配置国际化
	private String actUrl;//操作路径 host port之后的路径
	private char valid;
	private String remark;
	private String pname;//父级名称
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public Integer getPpid() {
		return ppid;
	}
	public void setPpid(Integer ppid) {
		this.ppid = ppid;
	}
	public String getAuthorityCode() {
		return authorityCode;
	}
	public void setAuthorityCode(String authorityCode) {
		this.authorityCode = authorityCode;
	}
	public String getActUrl() {
		return actUrl;
	}
	public void setActUrl(String actUrl) {
		this.actUrl = actUrl;
	}
	public char getValid() {
		return valid;
	}
	public void setValid(char valid) {
		this.valid = valid;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getPname() {
		if(this.ppid==0){
			return "顶级";
		}
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	

}
