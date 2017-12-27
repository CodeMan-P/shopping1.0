package com.util;

import java.io.File;
import java.io.Reader;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
@Component
public class DbConn {
	@Autowired
	private  SqlSessionFactory sqlSessionFactory = null;
	private static Reader reader;
	static URL path = Thread.currentThread().getContextClassLoader().getResource(File.separator);
	static Logger log = Logger.getLogger(DbConn.class.getName());

//	public static SqlSessionFactory getFactory() {
//		if (sqlSessionFactory == null) {
//			try {
//				//reader = Resources.getResourceAsReader("mybatis-config.xml");
//				//sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
//				
//				ApplicationContext ctx=null;
//				 ctx=new ClassPathXmlApplicationContext(path.toString()+"applicationContext.xml");
//				sqlSessionFactory = (SqlSessionFactory) ctx.getBean("sqlSessionFactory");
//			} catch (Exception e) {
//				log.warn(e.getLocalizedMessage());
//			}
//		}
//		return sqlSessionFactory;
//	}

	public static BasicDataSource bds;
	// jdbc:mysql://localhost:3306/mydb
	// jdbc:oracle:thin:@localhost:1521:orcl
	final String Url = "jdbc:mysql://localhost:3306/shop?characterEncoding=UTF-8";

	public DbConn() {
		bds = new BasicDataSource();
		bds.setDriverClassName("com.mysql.jdbc.Driver");
		bds.setUrl(Url);
		bds.setUsername("root");
		bds.setPassword("mysql");
		bds.setMaxActive(5);
		bds.setMaxIdle(5);
		bds.setMaxWait(1000);
	}

	public static Connection getCon() {
		Connection con = null;
		if (bds == null)
			new DbConn();
		try {
			con = bds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	public static void closeBds() {
		try {
			if (bds != null) {
				bds.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void closeConn(ResultSet rs, PreparedStatement pst, Connection con) {

		try {
			if (rs != null) {
				rs.close();
			}
			if(pst != null){
				pst.close();
			}
			if(con != null){
				con.close();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
