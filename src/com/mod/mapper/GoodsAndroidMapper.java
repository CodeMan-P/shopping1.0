package com.mod.mapper;

import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

import com.mod.bean.GoodsAndroid;
@MapperScan
public interface GoodsAndroidMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table goodsand
	 * @mbggenerated  Mon Nov 27 11:55:23 CST 2017
	 */
	int insert(GoodsAndroid record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table goodsand
	 * @mbggenerated  Mon Nov 27 11:55:23 CST 2017
	 */
	int insertSelective(GoodsAndroid record);

	@Select("select * from goodsand")
	LinkedList<LinkedHashMap<String, Object>> getGoddsAnd();
}