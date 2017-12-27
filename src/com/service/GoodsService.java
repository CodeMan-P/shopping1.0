package com.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.GoodsDao;
import com.mod.bean.Goods;

@Service
public class GoodsService {
	@Autowired
	GoodsDao goodsDao;
	public  Goods getGoods(int gid) {

		return goodsDao.getGoods(gid);
	}
	public  LinkedList<LinkedHashMap<String, Object>> getGoddsAnd(){
		return goodsDao.getGoddsAnd();
	};
	public  ArrayList<Goods> productList(String st,String st1){
		return goodsDao.productList(st,st1);	
	 }
}
