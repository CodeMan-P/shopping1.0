package com.service;

import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.UsersDao;
import com.mod.bean.Address;
import com.mod.bean.Users;
@Service
public class UserService {
	@Autowired
	UsersDao usersDao;
	public  Users findUser(Users user) {
		return usersDao.findUser(user);
	}

	public  String addUser(Users user) {

		return usersDao.addUser(user);
	}

	public  LinkedList<Address> getAdress(Integer uid) {
		return usersDao.getAdress(uid);
	}

	public  boolean deleAddress(Integer addressId) {
		return usersDao.deleAddress(addressId);
	}

	public  boolean editAddress(Address address) {
		return usersDao.editAddress(address);
	}

	public  boolean addAddress(Address address) {
		return usersDao.addAddress(address);
	}
	public  boolean updateByPrimaryKeySelective(Users record){
		return usersDao.updateByPrimaryKeySelective(record);
	}
}
