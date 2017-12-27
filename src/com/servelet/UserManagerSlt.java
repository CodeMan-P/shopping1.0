package com.servelet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jspsmart.upload.Files;
import com.jspsmart.upload.SmartUpload;
import com.mod.bean.Users;
import com.service.UserService;

/**
 * Servlet implementation class UserManagerSlt
 */
@Controller
public class UserManagerSlt extends HttpServlet {
	@Autowired
	UserService userService;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1469930882421413690L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserManagerSlt() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, response);
	}

	Logger log = Logger.getLogger(UserManagerSlt.class.getName());

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String flag = request.getParameter("flag");
		ObjectMapper mapper = new ObjectMapper();
		if (flag != null) {
			switch (flag) {
			case "regist":
				regist(request, out, mapper);
				return;
			case "changePwd":

				try {
					changePwd(request, out, mapper);
				} catch (Exception e) {
					e.printStackTrace();
					out.write("{\"message\":\"密码修改失败！\"}");
					out.flush();
					out.close();
				}
				return;

			default:
				;
			}
		}

		// 上传请求
		uploadAvator(request, response, out);

	}

	/**
	 * @param request
	 * @param out
	 * @param mapper
	 * @throws JsonProcessingException
	 * @throws IOException
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 */
	public void changePwd(HttpServletRequest request, PrintWriter out, ObjectMapper mapper)
			throws JsonProcessingException, IOException, JsonParseException, JsonMappingException {
		String code = request.getParameter("checkcode");
		if (code != null && !code.equals(request.getSession().getAttribute("code"))) {
			out.write("{\"message\":\"验证码错误，修改失败!\"}");
			out.close();
			return;
		}
		String json;
		json = mapper.writeValueAsString(request.getParameterMap());
		json = json.replaceAll("\\[|\\]", "");
		@SuppressWarnings("unchecked")
		HashMap<String, String> hm = mapper.readValue(json, HashMap.class);
		String pwd = hm.get("opwd");
		String name = (String) request.getSession().getAttribute("name");
		Users users = new Users(name, pwd);
		users = userService.findUser(users);
		if (users == null) {
			out.write("{\"message\":\"旧密码错误，修改失败!\"}");
			out.flush();
		} else {
			Users newUser = new Users(name, hm.get("npwd"));
			newUser.setUid(users.getUid());
			boolean b = userService.updateByPrimaryKeySelective(newUser);
			if (b) {
				out.write("{\"message\":\"密码修改成功！\"}");
			} else {
				out.write("{\"message\":\"密码修改失败！\"}");
			}
			out.flush();
		}
		out.close();
		return;
	}

	/**
	 * @param request
	 * @param response
	 * @param out
	 * @throws ServletException
	 */
	public void uploadAvator(HttpServletRequest request, HttpServletResponse response, PrintWriter out)
			throws ServletException {
		SmartUpload su = new SmartUpload();
		su.initialize(this, request, response);
		su.setAllowedFilesList("jpg,png,JPG,PNG");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSS");

		log.info(sdf.format(new Date()));
		try {
			su.setDeniedFilesList("exe");
			su.upload();
			Files files = su.getFiles();
			com.jspsmart.upload.File file;
			String name1, name2, type;
			int index;

			file = files.getFile(0);
			// 判断是否为空上传项
			if (file.isMissing()) {
				log.info(sdf.format(new Date()) + ":file missing!!!");
				out.write("#");
				out.flush();
				out.close();
				return;
			}
			name1 = file.getFileName();
			index = name1.lastIndexOf('.');
			type = name1.substring(index);// 获得文件类型
			name1 = name1.substring(0, index);// 获得文件名不包括后缀
			name2 = name1 + "_date_" + sdf.format(new Date()) + type;
			char sp = File.separatorChar;
			file.saveAs("jsp" + sp + "img" + sp + name2, com.jspsmart.upload.File.SAVEAS_VIRTUAL);
			try {
				file.saveAs("D:" + sp + "MyEclipse" + sp + "Workspaces" + sp + "onlineShopping2" + sp + "WebRoot" + sp
						+ "jsp" + sp + "img" + sp + name2, com.jspsmart.upload.File.SAVEAS_PHYSICAL);
			} catch (Exception e) {

			}
			log.info("接收：" + name1 + "\r\n保存为：---->" + name2);
			out.write("img/" + name2);
			out.flush();
			out.close();
			// com.jspsmart.upload.File.SAVEAS_VIRTUAL 下载到tomcat部署文件夹
			// auto 部署文件优先
			// PHYSICAL 硬盘指定目录
			// su.save("down");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param request
	 * @param out
	 * @param mapper
	 * @throws JsonProcessingException
	 */
	public void regist(HttpServletRequest request, PrintWriter out, ObjectMapper mapper)
			throws JsonProcessingException {
		Users user;
		String json;
		json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(request.getParameterMap());
		// System.out.println(json);

		// ajaxSubmit 提交时每个参数以数组传递，需要删除[]
		json = json.replace("[", "");
		json = json.replace("]", "");
		try {
			user = mapper.readValue(json, Users.class);
			String message = userService.addUser(user);
			if (message.contains("成功")) {
				request.getSession().setAttribute("name", user.getUname());
				request.getSession().setAttribute("uid", user.getUid());
				request.getSession().setAttribute("avatar", user.getAvatar());
				request.getSession().setAttribute("city", user.getCity());
				out.write("注册成功！");
				log.info("新用户注册：" + user.getUname());
			} else if (message.contains("Duplicate")) {
				out.write("用户名已存在！注册失败！");
				log.warn(message);
			} else {
				log.warn(message);
				out.write("注册失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.write("请输入正确的注册信息！");
			out.flush();
			out.close();
			return;
		}
		out.flush();
		out.close();
		return;
	}

}
