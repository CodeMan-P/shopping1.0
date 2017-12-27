package com.servelet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mod.bean.Goods;
import com.service.GoodsService;
import com.service.SpCarService;

@Component
public class ProInfo extends HttpServlet {
	@Autowired
	GoodsService goodsService;
	@Autowired
	SpCarService spCarService;
	/**
	 * 
	 */
	private static final long serialVersionUID = -7173722269864724052L;
	Logger log = Logger.getLogger(ProInfo.class.getName());

	/**
	 * Constructor of the object.
	 */
	public ProInfo() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	@Override
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		Object temp = request.getSession().getAttribute("uid");
		if(temp != null){
			int uid = (int) temp;
			int num = spCarService.getCarNum(uid);
			request.getSession().setAttribute("carnum", num);
		}
		int gid = 0;
		try {
			gid = Integer.parseInt(request.getParameter("gid"));
		} catch (NumberFormatException e) {
			log.warn("不能转换为Int:" + request.getParameter("gid"));
		}
		Goods goods = null;
		if (gid != 0) {
			goods = goodsService.getGoods(gid);
		}
		if (goods != null) {
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(goods);
			request.getSession().setAttribute("json", json);
			request.getSession().setAttribute("goods", goods);
			//request.getRequestDispatcher("jsp/goods.jsp").forward(request, response);
			response.sendRedirect("jsp/goods.jsp");
		}
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
	    SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,  
	              config.getServletContext());  
	}

}
