package com.admin.servelet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

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

/**
 * Servlet implementation class DatagridDele
 */
@WebServlet("/dataDele")
@Component
public class DatagridDele extends HttpServlet {
    @Autowired
	TableDao tableDao;
	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(DatagridDele.class.getName());
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DatagridDele() {
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
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		String flag = request.getParameter("flag");
		if(flag == null){
			return;
		}
		String id = request.getParameter("id");
		String path = request.getServletContext().getRealPath("/WEB-INF/classes/com/mod/");
		//PrintWriter out = response.getWriter();
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(request.getParameterMap());
		//System.out.println(json);
		
		Object result = null;
		String errowMsg = null;
		String traceMsg = null;
		PrintWriter out = response.getWriter();
		try {
			if(id != null){
					result = tableDao.reflect(flag, path, json, "deleByPk");
			}else{
				result = tableDao.reflect(flag, path, json, "deleByObj");
			}
			
		} catch (Exception e) {
			log.warn("删除失败：--------------》");
			log.warn(e.getMessage());
			errowMsg = "删除失败！！！";
			//e.printStackTrace();
			StringWriter sw = null;
			PrintWriter pw = null;
			try{
				sw = new StringWriter();
				pw = new PrintWriter(sw, true);
				e.printStackTrace(pw); 
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
		log.info("dele result:--------------->"+result);
		if(result != null&&(int)result!=0){
			json = "{\"success\":\"true\"}";
			out.write(json);
			out.flush();
			
		}else{
			out.write("{\"isError\":\"true\",\"msg\":\"删除失败！！！\"}");
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
