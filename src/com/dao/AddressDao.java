package com.dao;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.mod.bean.Address;
import com.mod.mapper.AddressMapper;

@Repository
public class AddressDao{
	 Logger log = Logger.getLogger(AddressDao.class.getName());
	@Autowired
	private AddressMapper am;
	
	public AddressDao() {
	}

	public  Address insertSelective(Address address) throws Exception {
		// TODO Auto-generated method stub
		int i = 0;
		i = am.insertSelective(address);
		if(i==1){
			return address;
		}
		return null;
	}

	public  int updateByObj(Address newT, Address oldT) throws Exception {
		// TODO Auto-generated method stub
		int i = 0;
		i = am.updateByPrimaryKeyChangePk(newT, oldT.getAdressid());
		if(i==1){
			return i;
		}
		return 0;
	}

	public  int deleteByPrimaryKey(String id) throws Exception {
		// TODO Auto-generated method stub
		int i = 0;
		i = am.deleteByPrimaryKey(Integer.parseInt(id));
		if(i==1){
			return i;
		}
		return 0;
	}

	public  int deleByObj(Address t) throws Exception {
		int i = 0;
		i = am.deleteByPrimaryKey(t.getAdressid());
		if(i==1){
			return i;
		}
		return 0;
	}

	
}
