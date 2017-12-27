package com.tests;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class ImgTset {

	private static final int IMG_HEIGHT = 80;
	private static final int IMG_WIDTH = 200;
	private static final int CODE_LEN = 4;

	public static void main(String[] args) {

		BufferedImage bi = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics graphics = bi.getGraphics();
		graphics.setColor(new Color(100, 230, 200));
		graphics.fillRect(0, 0, 300, 100);
		char[] codeChar = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

		graphics.setFont(new Font("Times New Roman", Font.BOLD, 58));
		Random rand = new Random();
		for (int i = 0; i < CODE_LEN; i++) {
			int index = rand.nextInt(codeChar.length);
			graphics.setColor(new Color(rand.nextInt(150), rand.nextInt(150), rand.nextInt(150)));
			graphics.drawString(codeChar[index] + "", (i * 40) + 20, 59);
		}
		String path = System.getProperty("user.dir") + File.separatorChar + "123.jpg";
		File file = new File(path);
		try {
			file.createNewFile();
			ImageIO.write(bi, "JPG", file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
