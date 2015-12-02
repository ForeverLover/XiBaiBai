package com.jph.xibaibai.alipay;

import java.io.Serializable;

public class Product implements Serializable {

	private String name;// 名称
	private String intro;// 介绍
	private double price;// 价格
	private String outTradeNo;// 商户外联

	public Product() {
		super();
	}

	public Product(String name, String intro, double price, String outTradeNo) {
		super();
		this.name = name;
		this.intro = intro;
		this.price = price;
		this.outTradeNo = outTradeNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

}
