package com.tests;

import java.io.File;
import java.io.FileFilter;

public class FilesTest {

	public static void main(String[] args) {
		String path = System.getProperty("user.dir") + File.separatorChar + "src" + File.separatorChar;
		File file = new File(path);
		FileFilter ff = new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				if (pathname.getName().toUpperCase().endsWith(".XML")) {
					return true;
				}
				return false;
			}
		};
		for (File f : file.listFiles(ff)) {
			System.out.println(f.getAbsolutePath());
			System.out.println(f.getName());
			System.out.println(f.getPath());
			System.out.println("-----------");
		}

	}

}
