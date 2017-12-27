package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mod.bean.Address;
import com.mod.bean.AgeInfo;
import com.mod.bean.Users;
import com.mod.mapper.AddressMapper;
import com.mod.mapper.UsersMapper;
import com.util.DbConn;
@Repository
@Transactional
public class UsersDao {
	@Autowired
	private  UsersMapper um;
	@Autowired
	private  AddressMapper am;
	
//	private  SqlSession session = null;
//
//	 {
//		try {
//			session = DbConn.getFactory().openSession();
//			um = session.getMapper(UsersMapper.class);
//			am = session.getMapper(AddressMapper.class);
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.warn(e.getLocalizedMessage());
//		}
//	}
	public  boolean updateByPrimaryKeySelective(Users record){
		int i = 0;
		try {
			i = um.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		if(i>0){
			return true;
		}
		return false;
	}


	public  boolean addAddress(Address address) {
		int i = 0;

		try {
			if(address.getDef()){
				//其他全部设置为非默认
				Connection con = DbConn.getCon();
				PreparedStatement pst = con.prepareStatement("update address set def = 0 where uid = ?");
				pst.setInt(1, address.getUid());
				pst.executeUpdate();
				DbConn.closeConn(null, pst, con);
			}
			i = am.insertSelective(address);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		if (i > 0) {
			return true;
		}
		return false;
	}

	public  boolean editAddress(Address address) {
		int i = 0;

		try {
			if(address.getDef()){
				//其他全部设置为非默认
				Connection con = DbConn.getCon();
				PreparedStatement pst = con.prepareStatement("update address set def = 0 where uid = ?");
				pst.setInt(1, address.getUid());
				pst.executeUpdate();
				DbConn.closeConn(null, pst, con);
			}
			i = am.updateByPrimaryKeySelective(address);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		if (i > 0) {
			return true;
		}
		return false;
	}

	public  boolean deleAddress(Integer addressId) {
		int i = 0;

		try {
			
			
			Address address = am.selectByPrimaryKey(addressId);
			int uid = address.getUid();
			Connection con = DbConn.getCon();
			PreparedStatement pst = con.prepareStatement("select * from address where def = 1 and uid = ?");
			pst.setInt(1, address.getUid());
			i = am.deleteByPrimaryKey(addressId);
			ResultSet rs = pst.executeQuery();
			if(!rs.next()){
				ResultSet temp = pst.executeQuery("select * from address where uid = "+address.getUid());
				if(temp.next()){
					String sql1="SELECT adressId FROM address WHERE  uid = "+address.getUid()+" ORDER BY adressId LIMIT 1";
					ResultSet temp2 = pst.executeQuery(sql1);
					int aid = -1;
					if(temp2.next()){
						aid = temp2.getInt("adressId");
						Address a = new Address();
						a.setAdressid(aid);
						a.setDef(true);
						a.setUid(uid);
						//System.out.println(aid);
						editAddress(a);
					}
					if(temp2!=null){
						temp2.close();
					}
				}
				if(temp != null){
					temp.close();
				}
			}
			DbConn.closeConn(rs, pst, con);
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		if (i > 0) {
			return true;
		}
		return false;
	}

	public  LinkedList<Address> getAdress(Integer uid) {
		LinkedList<Address> list = am.getAdrsListByUid(uid);
		return list;
	}

	public  Users findUser(Users users) {
		Users temp = (Users) um.getUser(users.getUname(), users.getUpwd());
		return temp;
	}

	public  String addUser(Users users) {
		String res = null;
		try {
			int temp = um.insert(users);
			if (temp > 0) {
				res = "注册成功！";
			}
		} catch (Exception e) {
			res = e.getLocalizedMessage();
		}
		return res;

	}

	public int updateByObj(Users users,Users old) throws Exception{
		int i = 0;
		//i = um.updateByPrimaryKeySelective(users);
		 i = um.updateByPrimaryKeyChangePk(users,old.getUid());

		return i;
	}
	public Users insertSelective(Users users) throws  Exception{
		//String res = null;
		int temp = 0;
		temp = um.insertSelective(users);
		if(temp ==1){
			return users;
		}
		return null;
	}
	public ArrayList<HashMap> getRegDateInfo(String year){
		ArrayList<HashMap> list = null;
		list = um.getRegDateInfo(year);
		return list;
	}
	public ArrayList<AgeInfo> getAgeInfo(){
		ArrayList<AgeInfo> list=null;
		list = um.getAgeInfo();
		return list;
	}
	public ArrayList<HashMap> getXingInfo(Integer sum){
		ArrayList<HashMap> list = null;
		list=um.getXingInfo(sum);
		return list;
	}
	public ArrayList<HashMap> getMingInfo(Integer sum){
		ArrayList<HashMap> list = null;
		list=um.getMingInfo(sum);
		return list;
	}
	public int deleteByPrimaryKey(String id){
		int i = 0;
		int pk=0;
		
		try {
			pk = Integer.parseInt(id);
			i = um.deleteByPrimaryKey(pk);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return i;
	}
	public  int deleByObj(Users u) throws Exception {
		int i = 0;
		i = um.deleteByPrimaryKey(u.getUid());
		if(i==1){
			return i;
		}
		return 0;
	}
	
}
