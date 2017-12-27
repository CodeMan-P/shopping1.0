package com.dao;

class DaoInterface {
	public <T> T insertSelective(T t) throws  Exception{
	
		return null;
	}
	public <T> int updateByObj(T newT,T oldT) throws Exception{
		return 0;
	}
	public int deleteByPrimaryKey(String id)throws Exception{
		return 0;
	}
	public <T> int deleByObj(T t)throws Exception{
		return 0;
	}
}
