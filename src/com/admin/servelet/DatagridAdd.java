package com.admin.servelet;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.dao.TableDao;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.util.LoadBean;

/**
 * Servlet implementation class DatagridAdd
 */
@WebServlet("/dataAdd")
@Component
public class DatagridAdd extends HttpServlet {
    @Autowired
	TableDao tableDao;
	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(DatagridAdd.class.getName());
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DatagridAdd() {
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
		String flag = request.getParameter("flag");
		if(flag == null){
			return;
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(request.getParameterMap());
		String path = request.getServletContext().getRealPath("/WEB-INF/classes/com/mod/");
		Object result = null;
		String errowMsg = null;
		String traceMsg = null;
		PrintWriter out = response.getWriter();
		try {
			result = tableDao.reflect(flag, path, json,"insertSelective");
		}catch (Exception e) {
			e.printStackTrace();
			log.warn("添加失败：--------------》");
			String msg = null;
			Throwable targetEx = null; 
			if (e instanceof InvocationTargetException){
				targetEx= ((InvocationTargetException) e)
		                    .getTargetException();
		            if (targetEx != null)
		            {
		                msg = targetEx.getMessage();
		            }
			 }else{
				 msg=e.getMessage();
			 }
			log.warn(msg);
			errowMsg = "添加失败！！！";
			//e.printStackTrace();
			StringWriter sw = null;
			PrintWriter pw = null;
			try{
				sw = new StringWriter();
				pw = new PrintWriter(sw, true);
				if(targetEx!=null){
					targetEx.printStackTrace(pw);
				}else{
					e.printStackTrace(pw);	
				}
				 
				traceMsg = sw.toString();
				int index = traceMsg.indexOf("Exception");
				errowMsg = traceMsg.substring(0, index);
				errowMsg = errowMsg.replaceAll("\"|\\[|\\}|\\]|\\{|\\s*|\r\n", "");
			}catch(Exception e2){
				e.printStackTrace();
			}finally {
				if(sw != null){
					sw.close();
				}
				if(pw != null){
					pw.close();
				}
			}
			//e.printStackTrace(s);
			
		}finally {
		//	System.out.println(errowMsg);
		//	System.out.println(traceMsg);
			if(traceMsg != null){
				//替换影响json解析的字符串
				traceMsg = traceMsg.replaceAll("\"|\\[|\\}|\\]|\\{|\\s*|\r\n", "");
				out.write("{\"isError\":\"true\",\"msg\":\""+errowMsg+"\",\"trace\":\""+traceMsg+"\"}");
				out.flush();
				out.close();
				return;

			}else if(errowMsg != null){
				out.write("{\"isError\":\"true\",\"msg\":\""+errowMsg+"\"}");
				out.flush();
				out.close();
				return;
			}
		}
		if(result != null){
			json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result );
			
			//System.out.println(json);
			out.write(json);
			out.flush();
			
		}else{
			out.write("{\"isError\":\"true\",\"msg\":\"添加失败！！！\"}");
			out.flush();
		}
		//System.out.println(json);
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
