package com.dao;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.mod.bean.Goods;
import com.mod.bean.OrderForm;
import com.mod.bean.Orders;
import com.mod.mapper.GoodsMapper;
import com.mod.mapper.OrderFormMapper;
import com.mod.mapper.OrdersMapper;
import com.mod.mapper.ShoppingCarMapper;
@Transactional
@Repository
public class OrdersDao {
	@Autowired
	private  OrdersMapper om;
	@Autowired
	private  OrderFormMapper ofm;
	@Autowired
	private  ShoppingCarMapper sc;
	@Autowired
	private  GoodsMapper gm;
	@Autowired
	GoodsDao goodsDao;


	@Transactional(readOnly=true)
	public  LinkedList<LinkedHashMap<String, Object>> getOGViewGoupByOid(Integer uid) {

		LinkedList<LinkedHashMap<String, Object>> list = null;
		list = om.getOGViewGoupByOid(uid);

		return list;
	}
	@Transactional(readOnly=true)
	public  LinkedList<HashMap<String, Object>> getOrderList(Long oid, Integer uid) {
		LinkedList<HashMap<String, Object>> list = null;
		list = ofm.getOrderList(oid, uid);
		return list;
	}
	@Transactional(readOnly=true)
	public  LinkedList<HashMap<String, Object>> getOrderList(String oid, Integer uid) {
		LinkedList<HashMap<String, Object>> list = null;
		list = ofm.getOrderList(oid, uid);
		return list;
	}

	public  boolean updateByOUid(String oid, Integer uid) {
		Long oidTemp = 0L;
		try {
			oidTemp = Long.parseLong(oid);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new RuntimeException("updateByOUid更新失败！"+oid+"( to long false)");
		}
		return updateByOUid(oidTemp, uid);
	}

	public  boolean updateByOUid(Long oid, Integer uid) {
		int i = om.updateByOUid(oid, uid);
		if (i < 0) {
			throw new RuntimeException("updateByOUid更新失败!");
		}
		return true;
	}

	 Logger log = Logger.getLogger(OrdersDao.class.getName());

	public  boolean addOrders(Orders orders, OrderForm orderform) {
		int i = 0;
		
			i = om.insertSelective(orders);// 添加orders表
			if (i > 0) {
				i = ofm.insertSelective(orderform);// 添加orderForm表
				if (i <= 0) {
					throw new RuntimeException("addOrders失败！");
				}
				int gid = orderform.getGid();
				Goods goods = goodsDao.getGoods(gid);
				int gnum = orderform.getGnum();
				int stock = goods.getStock() - gnum;
				if (stock  < 0) {
					throw new RuntimeException("addOrders更新失败!库存不足");
				}
				goods.setStock(stock);
				i = gm.updateByPrimaryKeySelective(goods);
				if (i <= 0) {
					throw new RuntimeException("addOrders失败！");
				}
			}
		
		// 正常插入完成，提交事务
		if (i > 0) {
			return true;
		}

		return false;
	}

	public  boolean deleOrders(Long oid, Integer uid) {
		LinkedList<HashMap<String, Object>> list = ofm.getOrderList(oid, uid);
		Iterator<HashMap<String, Object>> it = list.iterator();
		int i = 0;
		Goods goods = null;
		int gid, gnum, stock;
		while (it.hasNext()) {
			HashMap<String, Object> temp = it.next();
			
			gid = Integer.parseInt(String.valueOf(temp.get("gid")));
			stock = Integer.parseInt(String.valueOf(temp.get("stock")));
			gnum = Integer.parseInt(String.valueOf(temp.get("gnum")));
			stock = stock + gnum;
			goods = new Goods();
			goods.setGid(gid);
			goods.setStock(stock);
			i = gm.updateByPrimaryKeySelective(goods);
			if (i <= 0) {
				throw new RuntimeException("deleOrders失败！");
			}
		}

		try {
			i = om.deleByOUid(oid, uid);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("updateByOUid更新失败!");
		}
		if (i > 0) {
			return true;
		}else{
			throw new RuntimeException("updateByOUid更新失败!");
		}
		
	}

	public  boolean deleOrders(String oid, Integer uid) {
		Long oidTemp = 0L;
		try {
			oidTemp = Long.parseLong(oid);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new RuntimeException("deleOrders删除失败!"+oid+" to long type false!");
		}
		return deleOrders(oidTemp, uid);
	}

	public  boolean addOrders(Orders orders, LinkedList<OrderForm> orderlist, LinkedList<Integer> buyCids) {
		int i = 0;

			i = om.insertSelective(orders);
			if (i > 0) {
				for (OrderForm orderform : orderlist) {
					i = ofm.insertSelective(orderform);
					if (i <= 0) {
						throw new RuntimeException("addOrders更新失败!");
					}
					// 删减数据库对应商品
					int gid = orderform.getGid();
					Goods goods = goodsDao.getGoods(gid);
					int gnum = orderform.getGnum();
					int stock = goods.getStock() - gnum;
					if (stock  < 0) {
						throw new RuntimeException("addOrders更新失败!库存不足");
					}
					goods.setStock(stock);
					i = gm.updateByPrimaryKeySelective(goods);
					if (i <= 0) {
						throw new RuntimeException("addOrders更新失败!");
					}
				}
				for (Integer cid : buyCids) {
					i = sc.deleteByPrimaryKey(cid);
					if (i <= 0) {
						throw new RuntimeException("addOrders更新失败!");
					}
				}
			}
		
		// 正常插入完成，提交事务
		if (i > 0) {
			
			orderLog(orders, orderlist);
			return true;
		}
		return false;
	}

	private  void orderLog(Orders orders, OrderForm orderform) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			log.info("添加新订单(" + orders.getOid() + ")："
					+ mapper.writerWithDefaultPrettyPrinter().writeValueAsString(orders));
			log.info("订单详情(" + orders.getOid() + ")："
					+ mapper.writerWithDefaultPrettyPrinter().writeValueAsString(orderform));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

	}

	private  void orderLog(Orders orders, LinkedList<OrderForm> orderlist) {
		ObjectMapper mapper = new ObjectMapper();
		SerializerProvider sp = mapper.getSerializerProvider();
		// sp.setAttribute(list2, te);

		sp.setNullValueSerializer(new JsonSerializer<Object>() {
			@Override
			public void serialize(Object arg0, JsonGenerator arg1, SerializerProvider arg2)
					throws IOException, JsonProcessingException {
				arg1.writeString("待付款");
			}
		});
		
		try {
			log.info("添加新订单(" + orders.getOid() + ")："
					+ mapper.writerWithDefaultPrettyPrinter().writeValueAsString(orders));
			log.info("订单详情(" + orders.getOid() + ")："
					+ mapper.writerWithDefaultPrettyPrinter().writeValueAsString(orderlist));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

	}
	
	//------------------------
	public Orders insertSelective(Orders t) throws Exception {
		// TODO Auto-generated method stub
		int i = 0;
		i = om.insertSelective(t);
		if (i == 1) {
			return t;
		}
		return null;
	}

	public int updateByObj(Orders newT, Orders oldT) throws Exception {
		int i = 0;
		i = om.updateByPrimaryKeyChangePk(newT, oldT);
		return i;
	}

	public int deleteByPrimaryKey(String id) throws Exception {
		log.warn("此类未设置deleteByPrimaryKey方法！");

		throw new Exception("此类未设置deleteByPrimaryKey方法！");
	}

	public int deleByObj(Orders t) throws Exception {
		// TODO Auto-generated method stub
		int i = 0;
		i = om.deleByOUid(Long.parseLong(t.getOid()), t.getUid());
		return i;
	}
	
}
