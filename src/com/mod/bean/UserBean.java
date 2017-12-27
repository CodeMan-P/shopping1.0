package com.mod.bean;

import org.springframework.stereotype.Component;

@Component("userBean")
public class UserBean {
	
	private int userid;
	private String username;
	
	
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	

}
