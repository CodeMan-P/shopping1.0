package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mod.bean.Goods;
import com.mod.mapper.GoodsAndroidMapper;
import com.mod.mapper.GoodsMapper;
import com.util.DbConn;

@Transactional
@Repository
public class GoodsDao {

	@Autowired
	private GoodsMapper gm;
	@Autowired
	private GoodsAndroidMapper gam;

	@Transactional(readOnly = true)
	public Goods getGoods(int gid) {
		Goods g = null;

		g = gm.selectByPrimaryKey(gid);

		return g;
	}

	@Transactional(readOnly = true)
	public LinkedList<LinkedHashMap<String, Object>> getGoddsByTid(Integer tid) {
		LinkedList<LinkedHashMap<String, Object>> list = null;

		list = gm.getGoddsByTid(tid);

		return list;
	}

	@Transactional(readOnly = true)
	public LinkedList<LinkedHashMap<String, Object>> getGoddsByRegExp(String s) {
		LinkedList<LinkedHashMap<String, Object>> list = null;
		list = gm.getGoddsByRegExp(s);

		return list;
	}

	@Transactional(readOnly = true)
	public LinkedList<LinkedHashMap<String, Object>> getGoddsAnd() {
		LinkedList<LinkedHashMap<String, Object>> list = gam.getGoddsAnd();
		return list;
	};

	PreparedStatement pst = null;
	Connection con = null;
	boolean flog = false;
	ResultSet rs = null;

	@Transactional(readOnly = true)
	public ArrayList<Goods> productList(String st, String st1) {

		ArrayList<Goods> productList = new ArrayList<Goods>();
		con = DbConn.getCon();
		try {
			pst = con.prepareStatement("SELECT gid, gname, tid, price, descption, imgpath,filepath,stock FROM goods"
					+ " where gname like '%" + st + "%' ORDER BY price " + st1);

			rs = pst.executeQuery();

			while (rs.next()) {
				Goods b = new Goods();
				b.setGid(rs.getInt("gid"));
				b.setGname(rs.getString("gname"));
				b.setTid(rs.getInt("tid"));
				b.setPrice(rs.getDouble("price"));
				b.setDescption(rs.getString("descption"));
				b.setImgpath(rs.getString("imgpath"));
				b.setFilepath(rs.getString("filepath"));
				b.setStock(rs.getInt("stock"));
				productList.add(b);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DbConn.closeConn(rs, pst, con);
		}
		return productList;
	}
	
	//-----------------------------------------------------
	public Goods insertSelective(Goods goods) throws  Exception{
		//String res = null;
		int temp = 0;
		temp = gm.insertSelective(goods);
		if(temp ==1){
			return goods;
		}
		return null;
	}
	public int updateByObj(Goods goods,Goods old) throws Exception{
		int i = 0;
		//i = um.updateByPrimaryKeySelective(users);
		 i = gm.updateByPrimaryKeyChangePk(goods,old.getGid());

		return i;
	}
	public int deleteByPrimaryKey(String id)throws Exception{
		int i = 0;
		int pk=0;
		try {
			pk = Integer.parseInt(id);
			i = gm.deleteByPrimaryKey(pk);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return i;
	}
	
	public int deleByObj(Goods goods)throws Exception{
		int i = 0;
		
		i = gm.deleteByPrimaryKey(goods.getGid());
		
		return i;
	}





	
	
}
