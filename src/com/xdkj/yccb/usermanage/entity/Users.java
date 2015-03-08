package com.xdkj.yccb.usermanage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="USER")
public class Users {
	@Id
	@Column(name="UID")
	private String uid;
	@Column(name="UNAME")
	private String uname;
	@Column(name="PASSWD")
	private String passwd;
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
}
