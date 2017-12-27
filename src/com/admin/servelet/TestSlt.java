package com.admin.servelet;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.filechooser.FileFilter;

import com.annotation.JdbcType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mod.bean.Orders;
import com.util.LoadBean;

/**
 * Servlet implementation class TestSlt
 */
@WebServlet("/ts")
public class TestSlt extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestSlt() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		//System.out.println(request.getServletContext().getRealPath("/"));
		//D:\MyEclipse\apache-tomcat-9.0.1\webapps\easyUI2\
		String path = request.getServletContext().getRealPath("/WEB-INF/classes/com/mod/bean");
//		System.out.println(path);
//		int index = path.indexOf("com");
//		path = path.substring(index);
//		path = path.replaceAll("\\\\", "."); 
		//com.mod.bean
//		File file = new File(path);
//		File files[] = file.listFiles();
//		for(File f :files){
//			System.out.println(f.getName().replace(".class", ""));
//		}
		HashMap<String,String> beanMap = LoadBean.getBeanMap(path);
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(beanMap));
		Class<?> c = null;
		try {
		c = Class.forName("com.mod.bean."+"Orders");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if( c == null){
			return;
		};
		Field[] field = c.getDeclaredFields();
		Annotation[] a = c.getAnnotations();
		JdbcType jd =(JdbcType) field[0].getAnnotation(JdbcType.class);
		
		System.out.println(jd.type()+""+jd.required()+"--"+(jd.type()+"").equals("number"));
		try {
			System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(field));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	@Override
	public ServletContext getServletContext() {
		// TODO Auto-generated method stub
		return super.getServletContext();
	}
	String path = "";
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		
	}

	
}
