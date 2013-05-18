package com.alipay;

public class Trades{
	private String name;
	private String date;
	private String money;
	private String Status;
	
	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return this.name;
	}
	public void setDate(String date){
		this.date = date;
	}
	public String getDate(){
		return this.date;
	}
	public void setMoney(String money){
		this.money = money;
	}
	public String getMoney(){
		return this.money;
	}
	public void setStatus(String status){
		this.Status = status;
	}
	public String getStatus(){
		return this.Status;
	}
}