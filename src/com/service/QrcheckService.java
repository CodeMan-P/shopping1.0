package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.QrcheckDao;
import com.mod.bean.Qrcheck;

@Service
public class QrcheckService {
	@Autowired
	QrcheckDao qrcheckDao;
	public  Qrcheck getQrcheck(String UUID){
		return qrcheckDao.getQrcheck(UUID);
	}
	
	public  boolean deleQrcheck(String UUID){
		return qrcheckDao.deleQrcheck(UUID);
	}
	
	public  boolean insertQrcheck(Qrcheck qrc){
		return qrcheckDao.insertQrcheck(qrc);
	}
	
	public  boolean changeStatus(String UUID,Integer status){
		return qrcheckDao.changeStatus(UUID, status);
	}
	public  boolean update(Qrcheck qrc){
		return qrcheckDao.update(qrc);
	}
}
