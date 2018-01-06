package com.mod.bean;
import org.springframework.stereotype.Component;

import com.annotation.JdbcType;
import com.annotation.JdbcType.TYPE;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@Component
@JsonIgnoreProperties(ignoreUnknown = true,value={"province","city"})
public class Address {
 	@JsonProperty("adressId")
	@JdbcType(type = TYPE.intonly, required = false, idField = true)
	private Integer adressid;
 	@JdbcType(type = TYPE.intonly, required = true)
	private Integer uid;
 	@JdbcType(required = true)
	private String address;
 	@JsonProperty("default")
 	@JdbcType(required = false, type = TYPE.bool)
	private Boolean def;
 	@JdbcType(required = true, type = TYPE.phone)
	private String aphone;
 	@JdbcType(required = true)
	private String aname;
 	private String province;
 	private String city;
 	public Integer getAdressid() {
		return adressid;
	}
 	public void setAdressid(Integer adressid) {
		this.adressid = adressid;
	}
 	public Integer getUid() {
		return uid;
	}
 	public void setUid(Integer uid) {
		this.uid = uid;
	}
 	public String getAddress() {
		return address;
	}
 	public void setAddress(String address) {
		this.address = address;
	}
 	public Boolean getDef() {
		return def;
	}
 	public void setDef(Boolean def) {
		this.def = def;
	}
 	public String getAphone() {
		return aphone;
	}
 	public void setAphone(String aphone) {
		this.aphone = aphone;
	}
 	public String getAname() {
		return aname;
	}
 	public void setAname(String aname) {
		this.aname = aname;
	}
 	public String getProvince() {
		return province;
	}
 	public void setProvince(String province) {
		this.province = province;
	}
 	public String getCity() {
		return city;
	}
 	public void setCity(String city) {
		this.city = city;
	}
 	public Address() {
		super();
	}
 	public Address(Integer uid, Boolean def, String aphone, String aname, String address) {
		super();
		this.uid = uid;
		this.def = def;
		this.aphone = aphone;
		this.aname = aname;
		this.address = address;
	}
}