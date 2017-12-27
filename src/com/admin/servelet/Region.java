package com.admin.servelet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.dao.RegionDao;
import com.dao.UsersDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mod.bean.AgeInfo;
import com.mod.bean.RegionInfo;

/**
 * Servlet implementation class eRegion
 */
@WebServlet("/erg")
@Component("regionServelet")
public class Region extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Autowired
	RegionDao regionDao;
	@Autowired
	UsersDao usersDao;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Region() {
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
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		String flag = request.getParameter("flag");
		if(flag == null){
			return;
		}
		ObjectMapper mapper = new ObjectMapper();
		PrintWriter out = response.getWriter();
		if(flag.equals("regionInfo")){
			ArrayList<RegionInfo> list = null;
			list = regionDao.getRegionInfo();
			String json = mapper.writeValueAsString(list);
			out.write(json);
		}else if(flag.equals("ageInfo")){
			ArrayList<AgeInfo> list = null;
			list = usersDao.getAgeInfo();
			String json = mapper.writeValueAsString(list);
			out.write(json);
		}else if(flag.equals("dateInfo")){
			String year = request.getParameter("year");
			ArrayList<HashMap> list = null;
			list = usersDao.getRegDateInfo(year);
			 SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd");

		 	mapper.setDateFormat(myDateFormat);
			String json = mapper.writeValueAsString(list);
			out.write(json);
		}else if(flag.equals("xingInfo")){
			Integer sum = Integer.parseInt(request.getParameter("sum"));
			ArrayList<HashMap> list = null;
			list = usersDao.getXingInfo(sum);
			String json = mapper.writeValueAsString(list);
			out.write(json);
		}else if(flag.equals("mingInfo")){
			Integer sum = Integer.parseInt(request.getParameter("sum"));
			ArrayList<HashMap> list = null;
			list = usersDao.getMingInfo(sum);
			String json = mapper.writeValueAsString(list);
			out.write(json);
		}
		//System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(request.getParameterMap()));
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

}
