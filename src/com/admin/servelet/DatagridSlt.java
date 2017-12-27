package com.admin.servelet;


import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.dao.TableDao;
import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * Servlet implementation class DatagridSlt
 */
@WebServlet("/datagrid")
@Component
public class DatagridSlt extends HttpServlet {
	private static final long serialVersionUID = 1L;
    @Autowired
    JdbcTemplate jdbcTemplate;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DatagridSlt() {
        super();
        // TODO Auto-generated constructor stub
    }
	@Override
	public void init(ServletConfig config) throws ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String flag = request.getParameter("flag");
		if(flag == null){
			out.close();
			return;
		}
		String pageTemp = request.getParameter("page");
		String rowsTemp = request.getParameter("rows");
		int pageIndex = pageTemp==null?1:Integer.parseInt(pageTemp);
		int rows = rowsTemp==null?10:Integer.parseInt(rowsTemp);
		//int offset = (page-1)*rows;
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(Feature.WRITE_NUMBERS_AS_STRINGS,true);
		LinkedHashMap<String,Object> map = new LinkedHashMap<String,Object>();
		int start = (pageIndex -1)* rows;
		//Page<?> page = PageHelper.startPage(pageIndex, rows);
		List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from "+flag +" LIMIT " + start +","+rows);
		//System.out.println(flag);
		//List<?> list = tableDao.getTable(flag +" LIMIT " + start +","+rows + "-- ");
		int sum;
		//= page.getTotal();
		sum = jdbcTemplate.queryForObject("select count(1) from " + flag, Integer.class);
		//PageInfo<?> pi =page.toPageInfo();		
		
		//System.out.println(pi.toString());
		//PageInfo{pageNum=2, pageSize=5, size=1, startRow=6, endRow=6, total=6, pages=2, list=Page{count=true, pageNum=2, pageSize=5, startRow=5, endRow=10, total=6, pages=2, reasonable=false, pageSizeZero=false}, prePage=1, nextPage=0, isFirstPage=false, isLastPage=true, hasPreviousPage=true, hasNextPage=false, navigatePages=8, navigateFirstPage1, navigateLastPage2, navigatepageNums=[1, 2]}
		//System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(list));
		map.put("total", sum);
		map.put("rows", list);
		DeserializationConfig cfg = mapper.getDeserializationConfig();  
//		//设置JSON时间格式    
		SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
//		  
		mapper.setDateFormat(myDateFormat); 
//		
		String json = mapper.writeValueAsString(map);
		
		out.write(json);
		out.flush();
		out.close();
	}
	public Field[] getColnName(Class c){
		Field[] field = c.getDeclaredFields();
		return field;
	}
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
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
}
