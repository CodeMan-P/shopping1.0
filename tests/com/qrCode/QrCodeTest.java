package com.qrCode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;


import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
//import cern.colt.bitvector.BitMatrix;

public class QrCodeTest {

	public static void main(String[] args) {
		creatQRQode();
		
//		testDecode();
//		System.out.println(getUUID());
//		System.out.println(getUUID());
	}

	/**
	 * 
	 */
	public static void creatQRQode() {
		String st = null;
		ObjectMapper mapper = new ObjectMapper();
		// mapper.setSerializationInclusion(Include.NON_NULL);
//		ObjectNode ob = mapper.createObjectNode();
//		ob.put("xy", 123235235);
//		ob.put("zz", "http://192.168.1.102:8080/onlineShopping/");
//		try {
//			st = mapper.writeValueAsString(ob);
//			System.out.println(st);
//		} catch (JsonProcessingException e1) {
//			e1.printStackTrace();
//		}
		st = "http://192.168.1.102:8080/onlineShopping/qrCheck?UUID="+getUUID();
		int width = 200;
		int height = 200;
		String format = "png";// 图像类型
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		//控制边宽
		hints.put(EncodeHintType.MARGIN, 1);
		BitMatrix bitMatrix = null;
		try {
			bitMatrix = new MultiFormatWriter().encode(st, BarcodeFormat.QR_CODE, width, height, hints);
		} catch (WriterException e) {
			e.printStackTrace();
		} 
		// 生成矩阵
//		String fileName = "zxing.png";
//		String filePath = System.getProperty("user.dir")+File.separator+"tests"+File.separator;
		
//		Path path = FileSystems.getDefault().getPath(filePath, fileName);
//		try {
//			MatrixToImageWriter.writeToPath(bitMatrix, format, path);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} // 输出图像
//		System.out.println("输出成功.");
	}

	
	public static void testDecode() {
		
		String filePath = System.getProperty("user.dir")+File.separator+"tests"+File.separator+"zxing.png";
		BufferedImage image;
		try {
			image = ImageIO.read(new File(filePath));
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			Binarizer binarizer = new HybridBinarizer(source);
			BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
			Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
			hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
			Result result = new MultiFormatReader().decode(binaryBitmap, hints);// 对图像进行解码
//			ObjectMapper mapper = new ObjectMapper();
//			mapper.setSerializationInclusion(Include.NON_NULL);
//			@SuppressWarnings("unchecked")
//			HashMap<String, Object> hm = mapper.readValue(result.getText(), HashMap.class);

			System.out.println("图片中内容：  ");
			System.out.println(result.getText());
//			System.out.println("xy： " + hm.get("xy"));
//			System.out.println("zz：  " + hm.get("zz"));
			System.out.println("图片中格式：  ");
			System.out.println("encode： " + result.getBarcodeFormat());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
	}

	static SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssSSS");

	public static String getUUID() {
		String time = sf.format(new Date());
		// Long timeM = System.currentTimeMillis();
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString();
		String uuidStr = str.replace("-", "");
		return time +"-"+ uuidStr;
	}
}
