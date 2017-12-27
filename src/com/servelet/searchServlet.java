package com.servelet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.mod.bean.Goods;
import com.service.GoodsService;
import com.service.SpCarService;
@Component
public class searchServlet extends HttpServlet {
	@Autowired
	GoodsService goodsService;
	@Autowired
	SpCarService spCarService;
	/**
	 * 
	 */
	private static final long serialVersionUID = 6540417266194506013L;

	/**
	 * Constructor of the object.
	 */
	public searchServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
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
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
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
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		Object temp = request.getSession().getAttribute("uid");
		if(temp != null){
			int uid = (int) temp;
			int num = spCarService.getCarNum(uid);
			request.getSession().setAttribute("carnum", num);
		}
		String word = request.getParameter("word");
		if(word == null){
			word = (String) request.getSession().getAttribute("word");
			if(word == null){
				return;
			}
		}
		String sort = null;
		sort = (String) request.getSession().getAttribute("sort");
		if(sort!=null&&!sort.equals("")&&sort.equalsIgnoreCase("asc")){
			sort = "desc";
		}else{
			sort = "asc";
		}
		 
		ArrayList<Goods> productList = null;
		 productList = goodsService.productList(word, sort);
		
		request.getSession().setAttribute("productList", productList);
		request.getSession().setAttribute("word", word);
		request.getSession().setAttribute("sort", sort);
		response.sendRedirect("jsp/list.jsp");

	}

	@Override
	public void init(ServletConfig config) throws ServletException {
	    SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,  
	              config.getServletContext());  
	}

}
