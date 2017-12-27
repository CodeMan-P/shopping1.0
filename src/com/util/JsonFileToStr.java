package com.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class JsonFileToStr {
	public static synchronized String getJson(String path) {
		File file = new File(path);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		InputStreamReader isr = new InputStreamReader(fis);
		BufferedReader bfr = new BufferedReader(isr);
		char c[] = new char[100];
		int i = 0;
		StringBuffer sbf = new StringBuffer();
		try {
			while ((i = bfr.read(c)) != -1) {
				sbf.append(c, 0, i);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (bfr != null) {
					bfr.close();
				}
				if (isr != null) {
					isr.close();
				}
				if (fis != null) {
					fis.close();
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		String json = sbf.toString();
		return json;
	}
	public static synchronized String getJson(File file) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		InputStreamReader isr = new InputStreamReader(fis);
		BufferedReader bfr = new BufferedReader(isr);
		char c[] = new char[100];
		int i = 0;
		StringBuffer sbf = new StringBuffer();
		try {
			while ((i = bfr.read(c)) != -1) {
				sbf.append(c, 0, i);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (bfr != null) {
					bfr.close();
				}
				if (isr != null) {
					isr.close();
				}
				if (fis != null) {
					fis.close();
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		String json = sbf.toString();
		return json;
	}
}
