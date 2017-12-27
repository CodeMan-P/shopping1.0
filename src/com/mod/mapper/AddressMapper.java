package com.mod.mapper;

import java.util.LinkedList;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.mod.bean.Address;
@MapperScan
public interface AddressMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table address
	 * @mbggenerated  Mon Nov 27 11:55:23 CST 2017
	 */
	int deleteByPrimaryKey(Integer adressid);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table address
	 * @mbggenerated  Mon Nov 27 11:55:23 CST 2017
	 */
	int insert(Address record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table address
	 * @mbggenerated  Mon Nov 27 11:55:23 CST 2017
	 */
	int insertSelective(Address record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table address
	 * @mbggenerated  Mon Nov 27 11:55:23 CST 2017
	 */
	Address selectByPrimaryKey(Integer adressid);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table address
	 * @mbggenerated  Mon Nov 27 11:55:23 CST 2017
	 */
	int updateByPrimaryKeySelective(Address record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table address
	 * @mbggenerated  Mon Nov 27 11:55:23 CST 2017
	 */
	int updateByPrimaryKey(Address record);
	int updateByPrimaryKeyChangePk(@Param("a")Address a,@Param("pk")int pk);
	LinkedList<Address> getAdrsListByUid(@Param("uid") Integer uid);
}