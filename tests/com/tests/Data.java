package com.tests;

import java.util.LinkedList;

public class Data {
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
