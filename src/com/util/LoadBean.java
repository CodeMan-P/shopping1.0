package com.util;

import java.io.File;
import java.util.HashMap;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LoadBean {
	private static HashMap<String,String> beanMap = null;
//	private static HashMap<String,String> mapperMap = null;
	private static HashMap<String,String> daoMap = null;
	public static HashMap<String,String> getBeanMap(String path){
		path = path+"bean";
		if(beanMap==null){
			File file = new File(path);
			File files[] = file.listFiles();
			String key="";
			String val="";
			beanMap = new HashMap<String, String>();
			for(File f :files){
				val = f.getName().replace(".class", "");
				key = val.toLowerCase();
				beanMap.put(key, val);
				
			}
			
		}
		return beanMap;
	}
//	public LoadBean(){}
//	@Test
//	public void testM(){
//		String path = "D:\\MyEclipse\\apache-tomcat-9.0.1\\webapps\\easyUI2\\WEB-INF\\classes\\com\\mod\\";
//		getMapperList(path);
//		getDaoList(path);
//		ObjectMapper mapper = new ObjectMapper();
//		try {
//			System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapperList));
//			System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(list));
//			System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(daoList));
//		} catch (JsonProcessingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
//	public static HashMap<String,String> getMapperMap(String path){
//		HashMap<String,String> l = getBeanMap(path);
//		path = path+"mapper";
//		if(mapperMap==null){
//			
//			File file = new File(path);
//			File files[] = file.listFiles();
//			String key="";
//			String val="";
//			String fn="";
//			mapperMap = new HashMap<String, String>();
//			for(File f :files){
//				fn = f.getName();
//				if(fn.contains("xml")){
//					continue;
//				}
//				val = fn.replace(".class", "");
//				key = val.toLowerCase();
//				for(String k:l.keySet()){
//					if(key.contains(k+"mapper")){
//						mapperMap.put(k, val);
//						break;
//					}
//				}
//			}
//		}
//		return mapperMap;
//	}
	
	public static HashMap<String,String> getDaoMap(String path){
		HashMap<String,String> l = getBeanMap(path);
		path = path.replace("mod", "dao");
		//path = path+"mapper";
		if(daoMap==null){
			
			File file = new File(path);
			File files[] = file.listFiles();
			String key="";
			String val="";
			String fn="";
			daoMap = new HashMap<String, String>();
			for(File f :files){
				fn = f.getName();
				val = fn.replace(".class", "");
				key = val.toLowerCase();
				for(String k:l.keySet()){
					if(key.contains(k+"dao")){
						daoMap.put(k, val);
						break;
					}
				}
			}
		}
		return daoMap;
	}
}
