package com.util;

import com.annotation.JdbcType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class ColInfo {
	String fn;
	//JdbcType jd;
	String type;
	int maxLen,minLen;
	boolean required;
	public ColInfo() {
	}
	public ColInfo(String fn, JdbcType jd) {
		this.fn = fn;
		if(jd != null){
			this.type = jd.type()+"";
			this.maxLen = jd.maxLen();
			this.minLen = jd.minLen();
			this.required = jd.required();			
		}else{
			this.type = "varchar";
			this.maxLen = 0;
			this.minLen = 0;
			this.required = false;	
		}
	}
	public String getFn() {
		return fn;
	}
	public void setFn(String fn) {
		this.fn = fn;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getMaxLen() {
		return maxLen;
	}
	public void setMaxLen(int maxLen) {
		this.maxLen = maxLen;
	}
	public int getMinLen() {
		return minLen;
	}
	public void setMinLen(int minLen) {
		this.minLen = minLen;
	}
	public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	
	
	
}
