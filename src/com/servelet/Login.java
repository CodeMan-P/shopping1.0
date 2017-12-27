package com.servelet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mod.bean.Users;
import com.service.SpCarService;
import com.service.UserService;

@Controller
public class Login extends HttpServlet {

	@Autowired
	private UserService userService;
	@Autowired
	private SpCarService spCarService;
	/**
	 * 
	 */
	private static final long serialVersionUID = -6959422295223958502L;

	/**
	 * Constructor of the object.
	 */
	public Login() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	@Override
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
		// DbConn.closeBds();
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
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		String name = request.getParameter("name");
		PrintWriter out = response.getWriter();
		String flag = request.getParameter("flag");
		String code = request.getParameter("checkcode");
		if (code != null && !code.equalsIgnoreCase((String) request.getSession().getAttribute("code"))) {
			// System.out.println(request.getSession().getAttribute("code"));
			// System.out.println(code);
			out.write("{\"message\":\"验证码错误\"}");
			out.close();
			return;
		}
		if (flag != null && flag.equalsIgnoreCase("flush")) {
			int uid;
			try {
				Object temp = request.getSession().getAttribute("uid");
				uid = Integer.parseInt(String.valueOf(temp));
			} catch (Exception e) {
				out.close();
				return;
			}
			int num = spCarService.getCarNum(uid);
			request.getSession().setAttribute("carnum", num);
			out.write("{\"message\":\"刷新成功！\",\"cnum\":" + num + "}");
			out.flush();
			out.close();
			return;
		}
		if (name.equalsIgnoreCase("@quit")) {
			request.getSession().invalidate();
			out.write("注销成功！");
			out.flush();
			out.close();
			return;
		}
		String pwd = request.getParameter("pwd");
		Users users = new Users(name, pwd);
		users = userService.findUser(users);
		if (users == null) {
			response.sendRedirect("index.jsp");
		} else {
			Integer uid = users.getUid();
			request.getSession().setAttribute("name", name);
			request.getSession().setAttribute("uid", uid);
			request.getSession().setAttribute("avatar", users.getAvatar());
			request.getSession().setAttribute("city", users.getCity());
			int num = spCarService.getCarNum(uid);
			request.getSession().setAttribute("carnum", num);

			ObjectMapper mapper = new ObjectMapper();
			// 借密码变量 存储 购物车数量
			users.setUpwd(num + "");
			String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(users);
			out.write(json);
			out.flush();
			if (out != null) {
				out.close();
			}
		}
		// PrintWriter out = response.getWriter();
		// out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01
		// Transitional//EN\">");
		// out.println("<HTML>");
		// out.println(" <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		// out.println(" <BODY>");
		// String message = String.format("用户：%s存在!%d:%6.2f",
		// name,user.getCardNum(),user.getMoney());
		// out.println(message);
		// out.println(" </BODY>");
		// out.println("</HTML>");
		// out.flush();
		// out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException
	 *             if an error occurs
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}

}
