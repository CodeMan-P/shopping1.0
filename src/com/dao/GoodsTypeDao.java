package com.dao;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.mod.bean.GoodsType;
import com.mod.mapper.GoodsTypeMapper;

@Repository
public class GoodsTypeDao{
	@Autowired
	private  GoodsTypeMapper gtm;
	 Logger log = Logger.getLogger(GoodsTypeDao.class.getName());
	
	
	public   GoodsType insertSelective(GoodsType t) throws Exception {
		// TODO Auto-generated method stub
		int i = 0;
		i = gtm.insertSelective(t);
		if(i==1){
			return t;
		}
		return null;
	}

	public  int updateByObj(GoodsType newT, GoodsType oldT) throws Exception {
		// TODO Auto-generated method stub
		int i = 0;
		i = gtm.updateByPrimaryKeyChangePk(newT, oldT.getTid());
		if(i==1){
			return i;
		}
		return 0;
	}

	public  int deleteByPrimaryKey(String id) throws Exception {
		// TODO Auto-generated method stub
		int i = 0;
		i = gtm.deleteByPrimaryKey(Integer.parseInt(id));;
		if(i==1){
			return i;
		}
		return 0;
	}

	public  int deleByObj(GoodsType t) throws Exception {
		int i = 0;
		i = gtm.deleteByPrimaryKey(t.getTid());;
		if(i==1){
			return i;
		}
		return 0;
	}

	
}
