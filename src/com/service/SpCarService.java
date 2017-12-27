package com.service;

import java.util.HashMap;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dao.ShoppingCarDao;
import com.mod.bean.ShoppingCar;

@Repository
public class SpCarService {
	@Autowired
	ShoppingCarDao spCarDao;
	public  boolean addGoods(ShoppingCar car) {
		return spCarDao.addGoods(car);
	}

	public  int getCarNum(Integer uid) {
		return spCarDao.getCarNum(uid);
	}

	public  boolean deleGoods(Integer cid) {
		return spCarDao.deleGoods(cid);
	}

	public  LinkedList<ShoppingCar> getCarListByUid(Integer uid) {
		return spCarDao.getCarListByUid(uid);
	}

	public  LinkedList<HashMap<String, Object>> getCarView(Integer uid) {
		return spCarDao.getCarView(uid);
	}
	public  boolean updateByPrimaryKeySelective(ShoppingCar car){
		return spCarDao.updateByPrimaryKeySelective(car);
	}
}
