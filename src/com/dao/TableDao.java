package com.dao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mod.mapper.UsersMapper;
import com.util.LoadBean;

@Repository
public class TableDao {
	 Logger log = Logger.getLogger(TableDao.class.getName());
	@Autowired
	 private  UsersMapper um;
	@Resource(name="objectMapper")
	 ObjectMapper mapper;
//	@Resource(type=RequestMappingHandlerMapping.class)
//	ApplicationObjectSupport cos;
	@Autowired
	ApplicationContext cxt;
	public  List getTable(String tableName) {
		// Class<?> table = null;
		// try {
		// table = Class.forName(tableName);
		// } catch (ClassNotFoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		List<?> list = null;
		try {
			LinkedList<LinkedHashMap<String, Object>>  temp = um.getTable(tableName);
			list = temp;
		} catch (Exception e) {
			e.printStackTrace();
//			log.warn(e.getMessage());
		}
		return list;
	}
	/** 
	 * 首字母大写 
	 *  
	 * @param string 
	 * @return 
	 */  
	public static String initialToUpper(String string) {  
	    char[] methodName = string.toCharArray();  
	    methodName[0] = toUpperCase(methodName[0]);  
	    return String.valueOf(methodName);  
	} 
	/** 
	 * 字符转成大写 
	 *  
	 * @param chars 
	 * @return 
	 */  
	public static char toUpperCase(char chars) {  
	    if (97 <= chars && chars <= 122) {  
	        chars ^= 32;  
	    }  
	    return chars;  
	}  
	@SuppressWarnings("unchecked")
	public  Object reflect(String flag, String path, String json, String method) throws InvocationTargetException,Exception {
		ObjectMapper mapper = new ObjectMapper();
		json = json.replaceAll("\\[|\\]", "");
//		System.out.println("==========================");
//		System.out.println(cxt);
//		System.out.println("==========================");
//		System.out.println(cos.getApplicationContext());
//		System.out.println("==========================");
//		System.out.println(cos.getApplicationContext()==cxt);//true
//		cxt = cos.getApplicationContext();
		
		HashMap<String, String> beanMap = LoadBean.getBeanMap(path);
	//	HashMap<String, String> mapperMap = LoadBean.getMapperMap(path);
		Class<?> c = null;
		Class<?> mp = null;
		Class<?> dao = null;
		HashMap<String, String> daoMap = LoadBean.getDaoMap(path);

		if (beanMap.containsKey(flag)) {
			c = Class.forName("com.mod.bean." + beanMap.get(flag));
		} else {
			return null;
		}
//		if (mapperMap.containsKey(flag)) {
//			mp = Class.forName("com.mod.mapper." + mapperMap.get(flag));
//		} else {
//			return null;
//		}
		
		if (daoMap.containsKey(flag)) {
			dao = Class.forName("com.dao." + daoMap.get(flag));
		} else {
			return null;
		}
		Object tempDao = cxt.getBean(dao);
		Object temp = null;
		Method meth;
		switch (method) {
		case "insertSelective":
			log.info("insert request:------------------------------>");
			log.info(json);
			temp = mapper.readValue(json, c);
			meth = dao.getMethod("insertSelective", c);
			temp = meth.invoke(tempDao, c.cast(temp));
			break;
		case "deleByPk":
			log.info("deleByPk request:------------------------------>");
			log.info(json);
			meth = dao.getMethod("deleteByPrimaryKey", String.class);
			String id = mapper.reader().readTree(json).get("id").asText();
			temp = meth.invoke(tempDao, id);
			break;
		case "deleByObj":
			log.info("deleByObj request:------------------------------>");
			log.info(json);
			temp = mapper.readValue(json, c);
			meth = dao.getMethod("deleByObj", c);
			temp = meth.invoke(tempDao, c.cast(temp));
			break;
		case "updateByObj":
			log.info("updateByObj request:------------------------------>");
			log.info(json);
			HashMap<String,String> hmTemp =mapper.readValue(json, HashMap.class); 
			Object newRow = mapper.readValue(hmTemp.get("new"),c);
			Object oldRow = mapper.readValue(hmTemp.get("old"),c);
			meth = dao.getMethod("updateByObj", c,c);
			temp = meth.invoke(tempDao, c.cast(newRow),c.cast(oldRow));
			break;
		}

		// temp =
		// session.insert("com.mod.mapper."+mapperList.get(flag)+".insertSelective",
		// c.cast(temp));

		return temp;
	}

}
