package com.admin.servelet;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.annotation.JdbcType;
import com.util.ColInfo;
import com.util.LoadBean;

/**
 * Servlet implementation class DataTableSlt
 */
@WebServlet("/dataTable")
public class DataTableSlt extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DataTableSlt() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		String tn = request.getParameter("table");
		if(tn==null){
			return;
		}
		String path = request.getServletContext().getRealPath("/WEB-INF/classes/com/mod/");
		//获得javaBean
		HashMap<String,String> beanMap = LoadBean.getBeanMap(path);
		Class<?> c = null;
		try {
			if(beanMap.containsKey(tn)){
				c = Class.forName("com.mod.bean."+beanMap.get(tn));
			}else{
				return;
			}
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//field[x].name
		//获得所有列的类型反射
		JdbcType jd; 
		Field[] field = c.getDeclaredFields();
		String fname="";
		ColInfo col; 
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		String json;
		LinkedHashMap<String,LinkedHashMap<String, Object>> result = new LinkedHashMap<String,LinkedHashMap<String,Object>>();
		LinkedHashMap<String, Object> idField = new LinkedHashMap<String, Object>();
		
		for(Field f :field){
			fname = f.getName();
			jd =(JdbcType) f.getAnnotation(JdbcType.class);
			if(jd!=null&&jd.idField()){
				idField.put(fname.toLowerCase(), 1);
			}
			col = new ColInfo(fname, jd);
			json = mapper.writeValueAsString(col);
			@SuppressWarnings("unchecked")
			LinkedHashMap<String, Object> temp = mapper.readValue(json, LinkedHashMap.class);
			
			result.put(fname,temp);
			
		}
		result.put("idField", idField);
		//System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
		json  = mapper.writeValueAsString(result);
		//System.out.println(json);
		request.setAttribute("jtype", json);
		//System.out.println(jd.type()+""+jd.required()+"--"+(jd.type()+"").equals("number"));
		
		request.getRequestDispatcher("/admin/jsp/dataTable.jsp").forward(request, response);
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
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}


}
