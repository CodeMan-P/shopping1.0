package com.dao;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mod.bean.RegionInfo;
import com.mod.mapper.RegionMapper;

@Repository
public class RegionDao {

	Logger log = Logger.getLogger(RegionDao.class.getName());
	@Autowired
	private  RegionMapper gm;

	
	public  ArrayList<RegionInfo> getRegionInfo() {
		ArrayList<RegionInfo> list = null;
		list = gm.getRegionInfo();
		return list;
	};

}
