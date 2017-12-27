package com.tests;

import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.mod.bean.Goods;
import com.mod.mapper.OrderFormMapper;

public class MyBatisTest {

	private static SqlSessionFactory sqlSessionFactory;
	private static Reader reader;

	static {
		try {
			reader = Resources.getResourceAsReader("mybatis-config.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "unused", "hiding" })
	public static void main(String[] args) {
		long te = 20171109223512L;
		int t = 2017110922;
		System.out.println(te);
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		System.out.println(sf.format(new Date()));
		te = Long.parseLong(sf.format(new Date()));
		System.out.println(te);
		OrderFormMapper of = sqlSessionFactory.openSession().getMapper(OrderFormMapper.class);
		te = 20171110164436L;
		LinkedList<HashMap<String, Object>> list2 = (LinkedList<HashMap<String, Object>>) of.getOrderList(te, 1);
		System.out.println(list2.size());

		// System.out.println(list2.getFirst().get("date").toString());
		ObjectMapper mapper = new ObjectMapper();
		SerializerProvider sp = mapper.getSerializerProvider();
		// sp.setAttribute(list2, te);
		
		sp.setNullValueSerializer(new JsonSerializer<Object>() {
			@Override
			public void serialize(Object arg0, JsonGenerator arg1, SerializerProvider arg2)
					throws IOException, JsonProcessingException {
				arg1.writeString("-");
			}
		});
		Map<String, Object> m = new HashMap<String, Object>();
		sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		mapper.setDateFormat(sf);
		// 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
		// objectMapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
		// objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS,
		// false);

		try {
			System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(list2));
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("============我是分割线================================");

		// 提取公共属性到最外层
		m.put("address", list2.get(0).remove("address"));
		m.put("oid", list2.get(0).remove("oid"));
		m.put("uid", list2.get(0).remove("uid"));
		m.put("state", list2.get(0).remove("state"));
		m.put("sum", list2.get(0).remove("sum"));
		list2.get(1).remove("sum");
		list2.get(1).remove("state");
		list2.get(1).remove("uid");

		list2.get(1).remove("address");
		Date d = null;
		try {
			String s = (String) list2.get(1).remove("oid");
			sf = new SimpleDateFormat("yyyyMMddHHmmss");
			d = sf.parse(s.toString());
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		m.put("date", d);
		m.put("goods_1", list2.get(0));
		m.put("goods_2", list2.get(1));

		SimpleModule module = new SimpleModule();

		try {

			System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(m));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 */
	public static void goods() {
		Goods em = null;
		Goods em2 = null;
		em = sqlSessionFactory.openSession().selectOne("com.mod.mapper.GoodsMapper.selectByPrimaryKey", 3);
		ObjectMapper mapper = new ObjectMapper();
		try {
			System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(em));
			String json = mapper.writeValueAsString(em);
			em2 = mapper.readValue(json, Goods.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (em != null) {
			System.out.println(em.getFilepath());
		}
		if (em2 != null) {
			System.out.println(em2.getFilepath());
		}
	}

}
