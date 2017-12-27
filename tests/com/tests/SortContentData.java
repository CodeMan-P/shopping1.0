package com.tests;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SortContentData {

	public SortContentData() {
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
		File file = new File(path);
		File file2 = new File(path2);
		File file3 = new File(path3);
		SortContentData scd = new SortContentData();
		SortContentData scd2 = new SortContentData();
		LinkedList<SortContentData> scd3 = new LinkedList<SortContentData>();
		try {

			scd = mapper.readValue(file, SortContentData.class);
			scd2 = mapper.readValue(file2, SortContentData.class);
			scd3.add(scd);
			scd3.add(scd2);
			// file3.createNewFile();
			// mapper.writerWithDefaultPrettyPrinter().writeValue(file3, scd3);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(scd3));

		} catch (JsonProcessingException e) {
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

	public static class Data {
		public Data() {
		}

		int id;
		String section;
		LinkedList<Goods> goods;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getSection() {
			return section;
		}

		public void setSection(String section) {
			this.section = section;
		}

		public LinkedList<Goods> getGoods() {
			return goods;
		}

		public void setGoods(LinkedList<Goods> goods) {
			this.goods = goods;
		}

	}

	public static class Goods {
		public Goods() {
		}

		@JsonProperty("goods_id")
		int id;
		@JsonProperty("goods_thumb")
		String thumb;
		@JsonProperty("goods_name")
		String name;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getThumb() {
			return thumb;
		}

		public void setThumb(String thumb) {
			this.thumb = thumb;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

}
