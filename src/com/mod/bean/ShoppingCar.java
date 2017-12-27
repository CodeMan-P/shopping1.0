package com.mod.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.sql.Timestamp;

import org.springframework.stereotype.Component;

@JsonIgnoreProperties(ignoreUnknown = true)
@Component("shoppingCar")
public class ShoppingCar {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column shoppingcar.cid
	 * @mbggenerated  Mon Nov 27 11:55:23 CST 2017
	 */
	private Integer cid;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column shoppingcar.addGoodsTime
	 * @mbggenerated  Mon Nov 27 11:55:23 CST 2017
	 */
	@JsonFormat(timezone = "GTM+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp addgoodstime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column shoppingcar.uid
	 * @mbggenerated  Mon Nov 27 11:55:23 CST 2017
	 */
	private Integer uid;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column shoppingcar.gid
	 * @mbggenerated  Mon Nov 27 11:55:23 CST 2017
	 */
	private Integer gid;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column shoppingcar.gnum
	 * @mbggenerated  Mon Nov 27 11:55:23 CST 2017
	 */
	private Integer gnum;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column shoppingcar.descption
	 * @mbggenerated  Mon Nov 27 11:55:23 CST 2017
	 */
	private String descption;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column shoppingcar.cid
	 * @return  the value of shoppingcar.cid
	 * @mbggenerated  Mon Nov 27 11:55:23 CST 2017
	 */
	public Integer getCid() {
		return cid;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column shoppingcar.cid
	 * @param cid  the value for shoppingcar.cid
	 * @mbggenerated  Mon Nov 27 11:55:23 CST 2017
	 */
	public void setCid(Integer cid) {
		this.cid = cid;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column shoppingcar.addGoodsTime
	 * @return  the value of shoppingcar.addGoodsTime
	 * @mbggenerated  Mon Nov 27 11:55:23 CST 2017
	 */
	public Timestamp getAddgoodstime() {
		return addgoodstime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column shoppingcar.addGoodsTime
	 * @param addgoodstime  the value for shoppingcar.addGoodsTime
	 * @mbggenerated  Mon Nov 27 11:55:23 CST 2017
	 */
	public void setAddgoodstime(Timestamp addgoodstime) {
		this.addgoodstime = addgoodstime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column shoppingcar.uid
	 * @return  the value of shoppingcar.uid
	 * @mbggenerated  Mon Nov 27 11:55:23 CST 2017
	 */
	public Integer getUid() {
		return uid;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column shoppingcar.uid
	 * @param uid  the value for shoppingcar.uid
	 * @mbggenerated  Mon Nov 27 11:55:23 CST 2017
	 */
	public void setUid(Integer uid) {
		this.uid = uid;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column shoppingcar.gid
	 * @return  the value of shoppingcar.gid
	 * @mbggenerated  Mon Nov 27 11:55:23 CST 2017
	 */
	public Integer getGid() {
		return gid;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column shoppingcar.gid
	 * @param gid  the value for shoppingcar.gid
	 * @mbggenerated  Mon Nov 27 11:55:23 CST 2017
	 */
	public void setGid(Integer gid) {
		this.gid = gid;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column shoppingcar.gnum
	 * @return  the value of shoppingcar.gnum
	 * @mbggenerated  Mon Nov 27 11:55:23 CST 2017
	 */
	public Integer getGnum() {
		return gnum;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column shoppingcar.gnum
	 * @param gnum  the value for shoppingcar.gnum
	 * @mbggenerated  Mon Nov 27 11:55:23 CST 2017
	 */
	public void setGnum(Integer gnum) {
		this.gnum = gnum;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column shoppingcar.descption
	 * @return  the value of shoppingcar.descption
	 * @mbggenerated  Mon Nov 27 11:55:23 CST 2017
	 */
	public String getDescption() {
		return descption;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column shoppingcar.descption
	 * @param descption  the value for shoppingcar.descption
	 * @mbggenerated  Mon Nov 27 11:55:23 CST 2017
	 */
	public void setDescption(String descption) {
		this.descption = descption;
	}

	public ShoppingCar(Timestamp addgoodstime, Integer uid, Integer gid, Integer gnum, String descption) {
		super();
		this.addgoodstime = addgoodstime;
		this.uid = uid;
		this.gid = gid;
		this.gnum = gnum;
		this.descption = descption;
	}

	public ShoppingCar(Timestamp addgoodstime, Integer uid, Integer gid, Integer gnum) {
		super();
		this.addgoodstime = addgoodstime;
		this.uid = uid;
		this.gid = gid;
		this.gnum = gnum;
	}

	public ShoppingCar() {
		super();
	}

	public ShoppingCar(Integer cid, Timestamp addgoodstime, Integer uid, Integer gid, Integer gnum) {
		super();
		this.cid = cid;
		this.addgoodstime = addgoodstime;
		this.uid = uid;
		this.gid = gid;
		this.gnum = gnum;
	}
}