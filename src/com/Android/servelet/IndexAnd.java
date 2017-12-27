package com.Android.servelet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.WeakHashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.GoodsService;

/**
 * Servlet implementation class IndexAnd
 */
@WebServlet("/IndexAnd")
@Component
public class IndexAnd extends HttpServlet {
	@Autowired
	GoodsService goodsService;
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public IndexAnd() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		LinkedList<LinkedHashMap<String, Object>> list = goodsService.getGoddsAnd();
		LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("code", 0);
		result.put("message", "OK");
		result.put("total", 100);
		result.put("page_size", 6);
		result.put("data", list);
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		String json = mapper.writeValueAsString(result);
		@SuppressWarnings("unchecked")
		WeakHashMap<String, Object> whm = mapper.readValue(json, WeakHashMap.class);
		json = mapper.writeValueAsString(whm);
		PrintWriter out = response.getWriter();
		out.write(json);
		out.close();

	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
