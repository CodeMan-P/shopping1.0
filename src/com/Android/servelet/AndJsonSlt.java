package com.Android.servelet;



import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.util.JsonFileToStr;

/**
 * Servlet implementation class Goods_detailAnd
 */
@WebServlet("/addJson")
public class AndJsonSlt extends HttpServlet {
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AndJsonSlt() {
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
		ObjectMapper mapper = new ObjectMapper();
		
		//System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(request.getParameterMap()));
		String fn = request.getParameter("fn");
		//System.out.println(fn);
		if(fn!=null){
			file = new File(path+fn);
			String json = JsonFileToStr.getJson(file);
			PrintWriter out = response.getWriter();
			out.write(json);
			out.flush();
			out.close();
		}else{
			String[] fns = request.getParameterValues("fn");
			if(fns.length >0){
				file = new File(path+fns[0]);
				String json = JsonFileToStr.getJson(file);
				PrintWriter out = response.getWriter();
				out.write(json);
				out.flush();
				out.close();
			}
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	File file;
	String path;
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		path = this.getServletContext().getRealPath("/json")+ File.separatorChar;				
		
	}
}
