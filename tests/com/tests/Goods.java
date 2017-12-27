package com.tests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Goods {
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
