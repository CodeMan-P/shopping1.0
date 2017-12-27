package com.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RequestMapping("/ucc.do")
@Controller
public class UserController {
	
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public void messageNotReadable(HttpMessageNotReadableException exception, HttpServletResponse response){
	 //LOGGER.error("请求参数不匹配。", exception);
	 System.out.println(exception.getMessage());
//		return CodeMsg.error(exception.getMessage());
	}
	
	@Resource(name="objectMapper")
	ObjectMapper mapper;
	// application/json
	//,params={"user=root"}
	@RequestMapping(value="/uc",method=RequestMethod.GET)
	//@ResponseBody
	public String testM(Model model){
		model.addAttribute("aaa", "123");
		//request.getAttribute``````
		System.out.println("asdsa");
		//跳到jsp/asdasdasd.jsp
		return "asdasdasd";
	}
	@RequestMapping(value="/uc2",method=RequestMethod.GET,params={"user=root"})
	@ResponseBody
	public String testM2(){
		System.out.println("asdsa");
		return "asdasdasd";
	}
	
	
	@RequestMapping(value="/uc6",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> testM6(@RequestBody Map<String,Object> json_data){
		try {
		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json_data));
	} catch (JsonProcessingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return json_data;
	}
	
	@RequestMapping(value="/uc44",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> testM4(@RequestParam("d") Date asd){
		System.out.println(asd);
		Map<String,Object> json_data = new HashMap<String, Object>();
		json_data.put("ddd", asd);
		try {
		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json_data));
	} catch (JsonProcessingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return json_data;
	}
}
