package com.servelet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mod.bean.Address;
import com.mod.bean.ShoppingCar;
import com.service.SpCarService;
import com.service.UserService;

@Controller
public class ShoppingCarSlt extends HttpServlet {

	@Autowired
	UserService userService;
	@Autowired
	SpCarService spCarService;
	/**
	 * 
	 */
	private static final long serialVersionUID = -2979228988959015623L;

	/**
	 * Constructor of the object.
	 */
	public ShoppingCarSlt() {
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
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		String flag = request.getParameter("flag");
		
		if(flag == null){
			return;
		}
		int uid = (int) request.getSession().getAttribute("uid");
		int num = spCarService.getCarNum(uid);
		request.getSession().setAttribute("carnum", num);
		if (flag.equalsIgnoreCase("change")) {
			changeSpc(request, response);
		}else if (flag.equalsIgnoreCase("add")) {
			addGoods(request, response);
		} else if (flag.equalsIgnoreCase("dele")) {
			deleteCarItem(request, response, uid);
		} else if (flag.equalsIgnoreCase("view")) {
			// LinkedList<ShoppingCar> list = spCarService.getCarListByUid(uid);
			// request.getSession().setAttribute("carlist", list);

			ObjectMapper mapper = new ObjectMapper();
			LinkedList<Address> adresList = userService.getAdress(uid);
			String adresJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(adresList);
			LinkedList<HashMap<String, Object>> view = spCarService.getCarView(uid);
			request.getSession().setAttribute("view", view);
			request.getSession().setAttribute("adresJson", adresJson);
			//request.getRequestDispatcher("jsp/shoppingcar.jsp").forward(request, response);
			response.sendRedirect("jsp/shoppingcar.jsp");
		}

		// request.setAttribute("list", ProductService.getList());
		// request.getRequestDispatcher("/jsp/shopping.jsp").forward(request,
		// response);
	}

	/**
	 * @param request
	 * @param response
	 * @param uid
	 * @throws IOException
	 */
	public void deleteCarItem(HttpServletRequest request, HttpServletResponse response, int uid) throws IOException {
		PrintWriter out = response.getWriter();
		int cid = Integer.parseInt(request.getParameter("cid"));
		if (spCarService.deleGoods(cid)) {
			out.write("{\"message\":\"删除成功！\",\"num\":" + 0 + "}");
			int num = spCarService.getCarNum(uid);
			request.getSession().setAttribute("carnum", num);
		} else {
			out.write("{\"message\":\"删除失败！\"}");
		}
		out.flush();
		out.close();
	}

	/**
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void changeSpc(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		int cid = Integer.parseInt(request.getParameter("cid"));
		int gnum = Integer.parseInt(request.getParameter("gnum"));
		ShoppingCar record = new ShoppingCar();
		record.setCid(cid);
		record.setGnum(gnum);
		boolean b = spCarService.updateByPrimaryKeySelective(record);
		if(b){
			out.write("{\"message\":\"修改成功！\"}");
		}else{
			out.write("{\"message\":\"修改失败！\"}");
		}
		out.flush();
		out.close();
	}

	/**
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void addGoods(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		try {
			int uid = (int) request.getSession().getAttribute("uid");
			int gid = Integer.parseInt(request.getParameter("gid"));
			int gnum = Integer.parseInt(request.getParameter("gnum"));
			String desc = request.getParameter("desc");
			Timestamp tm = new Timestamp(new Date().getTime());
			ShoppingCar sc = new ShoppingCar(tm, uid, gid, gnum, desc);
			// ObjectMapper mapper = new ObjectMapper();
			// System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(sc));
			if (spCarService.addGoods(sc)) {
				request.getSession().removeAttribute("carnum");
				int num = spCarService.getCarNum(uid);
				request.getSession().setAttribute("carnum", num);

				out.write("{\"message\":\"添加成功！\",\"num\":" + num + "}");

			} else {
				out.write("{\"message\":\"添加失败！\"}");
			}

		} catch (Exception e) {
			log.warn(e.getLocalizedMessage());
			out.write("{\"message\":\"添加失败！\"}");
		} finally {
			out.flush();
			out.close();
		}
	}

	Logger log = Logger.getLogger(ShoppingCarSlt.class.getName());

	@Override
	public void init(ServletConfig config) throws ServletException {
	    SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,  
	              config.getServletContext());  
	}
}
