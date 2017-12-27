package com.spring.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SpringTest {

	
	@Test
	public void testM(){
		ApplicationContext cxt = new ClassPathXmlApplicationContext("ApplicationContext.xml");
		ObjectMapper mapper = (ObjectMapper) cxt.getBean("objectMapper");
		System.out.println(mapper);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("1", 123);
		Date d = new Date();
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(sdf.format(d));
		System.out.println();
		map.put("2", d);
		map.put("3", null);
		try {
			System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
