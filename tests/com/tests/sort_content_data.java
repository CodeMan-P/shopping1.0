package com.tests;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class sort_content_data {

	public sort_content_data() {
	};

	int code;
	String message;
	LinkedList<Data> data;

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		String path = System.getProperty("user.dir") + File.separatorChar + "test" + File.separatorChar
				+ "sort_content_data_1.json";
		String path2 = System.getProperty("user.dir") + File.separatorChar + "test" + File.separatorChar
				+ "sort_content_data_2.json";
		String path3 = System.getProperty("user.dir") + File.separatorChar + "test" + File.separatorChar
				+ "sort_content_data.json";
		System.out.println(path);
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true); 
		String json = "";
		File file = new File(path);
		File file2 = new File(path2);
		File file3 = new File(path3);
		sort_content_data scd = new sort_content_data();
		sort_content_data scd2 = new sort_content_data();
		LinkedList<sort_content_data> scd3 = new LinkedList<sort_content_data>();
		try {

			scd = mapper.readValue(file, sort_content_data.class);
			scd2 = mapper.readValue(file2, sort_content_data.class);
			scd3.add(scd);
			scd3.add(scd2);
			file3.createNewFile();
			mapper.writerWithDefaultPrettyPrinter().writeValue(file3, scd3);
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
		try {
			System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(scd3));

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LinkedList<Data> getData() {
		return data;
	}

	public void setData(LinkedList<Data> data) {
		this.data = data;
	}

}
