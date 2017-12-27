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
@WebServlet("/goods_detailAnd")
public class Goods_detailAnd extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Goods_detailAnd() {
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
		String fn = request.getParameter("fn");
		ObjectMapper mapper = new ObjectMapper();
		//System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(request.getParameterMap()));
		
			
			String id = request.getParameter("goods_id");
			String fileName = "goods_detail_data_" + id + ".json";
			file = new File(path+fileName);
			String json = JsonFileToStr.getJson(file);
			PrintWriter out = response.getWriter();
			out.write(json);
			out.flush();
			out.close();
		
		
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
