package com.servelet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;


/**
 * Servlet implementation class CheckCode
 */
@WebServlet("/ck")
@Controller
public class CheckCode extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckCode() {
        super();
        // TODO Auto-generated constructor stub
    }
    private static final int IMG_HEIGHT = 80;
	private static final int IMG_WIDTH = 200;
	private static final int CODE_LEN = 4;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		BufferedImage bi = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics graphics = bi.getGraphics();
		graphics.setColor(new Color(100, 230, 200));
		graphics.fillRect(0, 0, 300, 100);
		char[] codeChar = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
		String code = "";
		graphics.setFont(new Font("Times New Roman", Font.BOLD, 58));
		Random rand = new Random();
		for (int i = 0; i < CODE_LEN; i++) {
			int index = rand.nextInt(codeChar.length);
			graphics.setColor(new Color(rand.nextInt(150), rand.nextInt(150), rand.nextInt(150)));
			code+=codeChar[index];
			graphics.drawString(codeChar[index] + "", (i * 40) + 20, 59);
		}
		request.getSession().setAttribute("code", code);
		ImageIO.write(bi, "JPG", response.getOutputStream());
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
