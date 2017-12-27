package com.tests;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonFormat.Value;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class JacksonTest {
	// 子类多态
	// @JsonTypeInfo(use=JsonTypeInfo.Id.CLASS,
	// include=JsonTypeInfo.As.PROPERTY, property="@class")
	// @JsonSubTypes({@Type(value=Lion.class,name="lion"),@Type(value=Elephant.class,name="elephant")})
	static ObjectMapper mapper = new ObjectMapper();

	@Test
	public void testMethod2() {
		mapper.setSerializationInclusion(Include.NON_NULL);
		HashMap<String, String> m = new HashMap<String, String>();
		m.put("版本", "Mate 10,Mate 10 62G+128G,麦芒6,P10,畅享7");
		m.put("网络类型", "4G全网通");
		m.put("机身颜色", "钻雕金,钻雕蓝,草木绿,曜石黑,玫瑰金");
		m.put("套餐类型", "官方标配,套餐一,套餐二,套餐三,套餐四,套餐五");
		m.put("存储容量", "64GB,128GB");
		m.put("购买方式", "裸机");
		LinkedList<HashMap<String, String>> list = new LinkedList<HashMap<String, String>>();
		list.add(m);
		try {
			System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(list));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testMethod4() {
		String path = System.getProperty("user.dir") + File.separatorChar + "tests" + File.separatorChar + "json";
		String target = System.getProperty("user.dir") + File.separatorChar + "tests" + File.separatorChar
				+ "result.txt";
		File file = new File(path);
		File f2 = new File(target);
		try {
			f2.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File[] fs = file.listFiles();
		writeURL(fs, f2);
	}

	@Test
	public void testMethod6() {
		LinkedList<HashMap<String, String>> list = testMethod5();
		downloadFromUrl(list);
	}

	public LinkedList<HashMap<String, String>> testMethod5() {
		String target = System.getProperty("user.dir") + File.separatorChar + "tests" + File.separatorChar
				+ "result.txt";
		File f2 = new File(target);
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		InputStreamReader isr = null;
		BufferedReader bfr = null;
		LinkedList<HashMap<String, String>> list = new LinkedList<HashMap<String, String>>();
		try {
			fis = new FileInputStream(f2);
			bis = new BufferedInputStream(fis);
			isr = new InputStreamReader(bis);
			bfr = new BufferedReader(isr);
			String s = "";
			String filename = "";
			String type = "";
			int i, j;
			while (bfr.ready()) {
				s = bfr.readLine();
				i = s.lastIndexOf("?");
				j = s.lastIndexOf("/");
				if (i != -1 && j != -1) {
					filename = s.substring(j + 1, i);
				} else {
					filename = s.substring(j + 1);
				}
				i = filename.lastIndexOf(".");
				if (i == -1) {
					type = ".jpg";
				} else {
					type = filename.substring(i);
				}
				HashMap<String, String> gm = new HashMap<String, String>();
				gm.put("url", s);
				gm.put("name", filename);
				gm.put("type", type);
				list.add(gm);
				System.out.println(s);
				System.out.println(type);
				System.out.println("====================");
			}
			ObjectMapper mapper = new ObjectMapper();
			System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(list));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
				if (bis != null) {
					bis.close();
				}
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return list;
	}

	public static void downloadFromUrl(LinkedList<HashMap<String, String>> list) {
		URL url = null;
		HttpURLConnection con = null;
		InputStream ips = null;
		String path = System.getProperty("user.dir") + File.separatorChar + "tests" + File.separatorChar + "json"
				+ File.separatorChar;
		FileOutputStream fos = null;
		String temp = "";
		int sum = list.size();
		Long time = System.currentTimeMillis();
		int i = 1;
		for (HashMap<String, String> hm : list) {
			System.out.println("传输：(" + i + "/" + sum + ")-->");
			try {
				temp = hm.get("url");
				System.out.println(temp);

				url = new URL(temp);
				con = (HttpURLConnection) url.openConnection();
				con.setConnectTimeout(3000);
				con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
				ips = con.getInputStream();
				byte[] getData = readInputStream(ips);
				temp = hm.get("name");

				File file = new File(path + temp);
				if (!file.exists()) {
					file.createNewFile();
				}
				fos = new FileOutputStream(file);
				fos.write(getData);
				if (fos != null) {
					fos.close();
				}
				if (ips != null) {
					ips.close();
				}

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("耗时：" + (System.currentTimeMillis() - time) + "ms");
			time = System.currentTimeMillis();
			i++;
		}
	}

	public static byte[] readInputStream(InputStream inputStream) throws IOException {
		byte[] buffer = new byte[1024];
		int len = 0;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while ((len = inputStream.read(buffer)) != -1) {
			bos.write(buffer, 0, len);
		}
		bos.close();
		return bos.toByteArray();
	}

	public void writeURL(File[] files, File target) {
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		InputStreamReader isr = null;
		BufferedReader bfr = null;
		PrintWriter pw = null;
		FileWriter fw = null;
		HashSet<String> hs = new HashSet<String>();
		for (File file : files) {
			try {
				fw = new FileWriter(target, true);
				pw = new PrintWriter(fw);

				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				isr = new InputStreamReader(bis);
				bfr = new BufferedReader(isr);
				String s = "";
				String reg = "\"(http:.+?)\"";
				Matcher mat;
				Pattern pat = Pattern.compile(reg);
				while (bfr.ready()) {
					s = bfr.readLine();
					mat = pat.matcher(s);
					if (mat.find()) {
						s = mat.group(1);
						if (hs.contains(s)) {
							continue;
						}
						hs.add(s);
						System.out.println(s);
						pw.println(s);
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (fw != null) {
						fw.close();
					}
					if (pw != null) {
						pw.close();
					}

					if (bfr != null) {
						bfr.close();
					}
					if (isr != null) {
						isr.close();
					}
					if (bis != null) {
						bis.close();
					}
					if (fis != null) {
						fis.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	@Test
	public void testMethod3() {
		String path = System.getProperty("user.dir") + File.separatorChar + "tests" + File.separatorChar
				+ "sort_content_data.json";
		File file = new File(path);
		try {
			FileReader fr = new FileReader(file);

			char c[] = new char[1024];
			int i = 0;
			StringBuffer sb = new StringBuffer();
			while ((i = fr.read(c)) != -1) {
				sb.append(c, 0, i);
			}
			System.out.println(sb);

			fr.close();
			System.out.println(sb.capacity());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unused")
	@Test
	public void testMethod() {
		// TreeMap tm = new TreeMap<>(new Comparator<Goods>() {
		//
		// @Override
		// public int compare(Goods o1, Goods o2) {
		// return o1.name.compareTo(o2.name);
		// }
		// });
		// tm.keySet().iterator();
		// TreeSet tss = new TreeSet<Goods>(new Comparator<Goods>() {
		//
		// @Override
		// public int compare(Goods o1, Goods o2) {
		// return o1.name.compareTo(o2.name);
		// }
		// });

		String path = System.getProperty("user.dir") + File.separatorChar + "tests" + File.separatorChar
				+ "sort_content_data.json";
		// sort_content_data scd = new sort_content_data();
		File file = new File(path);

		Class<?> c = null;
		sort_content_data temp = new sort_content_data();
		try {
			c = Class.forName(temp.getClass().getName());

		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} // 获取Class对象，注意这里必须是包名+类名
		HashSet<Object> ts = new HashSet<Object>();
		HashMap<String, Object> hm = new HashMap<String, Object>();
		String s = "sort_content_data";
		LinkedList<sort_content_data> scd = new LinkedList<>();

		// scd.getFirst();
		try {
			// scd = mapper.readValue(file, sort_content_data.class);
			scd = mapper.readValue(file, new TypeReference<LinkedList<sort_content_data>>() {
			});
			System.out.println(scd.getClass().getCanonicalName());
			System.out.println(scd.getFirst().data.getFirst().section);
			Goods g1 = null;
			Data d = null;
			d = scd.getFirst().data.getFirst();
			g1 = d.getGoods().getFirst();
			hm.put("data", d);
			hm.put("goods", g1);
			// ts.add(d);
			// ts.add(g1);
			System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(hm));
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ObjectMapper mapper = new ObjectMapper();
		String json = "";
		String path = System.getProperty("user.dir") + File.separatorChar + "WebRoot\\json\\";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSS");
		String fileName = "pro_date_" + sdf.format(new Date()) + ".json";
		String fileName2 = "proView_date_20171106162557681.json";

		// File file = new File(path+fileName);
		File file2 = new File(path + fileName2);
		try {
			// if(!file.exists()){
			// file.createNewFile();
			// }
			if (!file2.exists()) {
				file2.createNewFile();
			}
			// mapper.writeValue(file, list);//单行文本输出
			System.out.println("down!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			JsonFactory jsf = mapper.getFactory();

			JsonGenerator jsong = jsf.createGenerator(file2, JsonEncoding.UTF8);
			System.out.println(json);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	 * 1.注解使用在 类名,接口头上
	 * 
	 * @JsonIgnoreProperties(value={"comid"}) //希望动态过滤掉的属性 2。该注解使用在get方法头上
	 * 
	 * @JsonIgnore
	 */
}
