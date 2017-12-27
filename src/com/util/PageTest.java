package com.util;

import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dao.GoodsDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mod.bean.Goods;
//@Component
public class PageTest {

	//@Autowired
	GoodsDao goodsDao;
	public void main(String[] args) {
		Goods good = new Goods();
		ObjectMapper mapper = new ObjectMapper();
		PageHelper.startPage(1, 2);
		LinkedList<LinkedHashMap<String, Object>> list = goodsDao.getGoddsByTid(2);
		try {
			System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(list));
			System.out.println("========================");
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		String s;
		
		PageInfo<LinkedHashMap<String, Object>> pi = new PageInfo<LinkedHashMap<String, Object>>(list);
		long sum = pi.getTotal();
		System.out.println(sum);
		pi.getNextPage();

	}
}
