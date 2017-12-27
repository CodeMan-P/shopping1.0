package com.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mod.bean.Qrcheck;
import com.mod.mapper.QrcheckMapper;
@Repository
public class QrcheckDao {

	@Autowired
	private  QrcheckMapper qm;


	public  Qrcheck getQrcheck(String UUID){
		
		Qrcheck qrc = null;
		qrc=qm.selectByUUID(UUID);
		
		return qrc;
	}
	
	public  boolean deleQrcheck(String UUID){
		int i  = 0;
		i=qm.deleteByUUID(UUID);
		if(i>0){
			return true;
		}
		return false;
	}
	public  boolean insertQrcheck(Qrcheck qrc){
		int i  = 0;
		i=qm.insertSelective(qrc);
		if(i>0){
			return true;
		}
		return false;
	}
	public  boolean changeStatus(String UUID,Integer status){
		int i  = 0;
		i=qm.changeStatus(UUID, status);
		if(i>0){
			return true;
		}
		return false;
	}
	public  boolean update(Qrcheck qrc){
		int i  = 0;
		i=qm.update(qrc);
		if(i>0){
			return true;
		}
		return false;
	}

}
