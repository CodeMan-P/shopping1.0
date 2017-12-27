package com.servelet;

import java.io.IOException;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;

import com.dao.RoleUserDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mod.bean.RoleBean;
import com.mod.bean.UserBean;


@WebServlet("/RoleServlet")
@Controller
public class RoleServlet extends HttpServlet {
	


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public RoleServlet() {
		super();
	}

	
	public void destroy() {
		super.destroy();
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
		

		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String flag = request.getParameter("flag");
		if(flag!=null&&flag.equals("u")){
			int id=Integer.parseInt(request.getParameter("rid"));
			RoleUserDao rub=new RoleUserDao();
			
			try {
				ArrayList<UserBean> list = rub.getUserList(id);
					
					ObjectMapper mapper = new ObjectMapper();
					String json = mapper.writeValueAsString(list);
					out.print(json);
					out.flush();
					out.close();
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}else{
			RoleUserDao ru=new RoleUserDao();
			try {
				ArrayList <RoleBean> list=ru.getRoleList();
				//JSONArray jlist =JSONArray.fromObject(list);
				ObjectMapper mapper = new ObjectMapper();
				String json = mapper.writeValueAsString(list);
				//System.out.println(jlist);
				out.print(json);
				out.flush();
				out.close();
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
	
				
		
		
	}

	
	public void init() throws ServletException {
		// Put your code here
	}

}
