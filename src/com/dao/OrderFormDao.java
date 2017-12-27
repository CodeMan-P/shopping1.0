package com.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mod.bean.OrderForm;
import com.mod.mapper.OrderFormMapper;
import com.util.DbConn;

@Repository
public class OrderFormDao{
	@Autowired
	private  OrderFormMapper ofm;
	 Logger log = Logger.getLogger(OrderFormDao.class.getName());
	
	public  OrderForm insertSelective(OrderForm t) throws Exception {
		// TODO Auto-generated method stub
		int i =0;
		i=ofm.insertSelective(t);
		if(i==1){
			return t;
		}
		return null;
	}

	public  int updateByObj(OrderForm newT,OrderForm oldT) throws Exception {
		// TODO Auto-generated method stub
		int i = 0;
		i = ofm.updateByPrimaryKeyChangePk(newT, oldT);
		return i;
	}

	public  int deleteByPrimaryKey(String id) throws Exception {
		log.warn("此类未设置deleteByPrimaryKey方法！");
		return 0;
	}

	public  int deleByObj(OrderForm t) throws Exception {
		Long oid = Long.parseLong(t.getOid());
		int i = 0;
		i = ofm.deleByOUid(oid, t.getUid());
		if(i==1){
			return i;
		}
		return 0;
	}

}
