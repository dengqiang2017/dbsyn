package com.dengqiang.controller;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

public class SynDataLogBean implements Serializable{
	private static final long serialVersionUID = 557416195042025195L;
	public SynDataLogBean(String tableName) {
		this.tableName=tableName;
	}
	String tableName;
	String msg="";
	Integer insertNum;
	Integer countNum;
	AtomicInteger a=new AtomicInteger();
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		if (this.msg==null) {
			this.msg =msg;
		}else{
			this.msg =this.msg+"<br>"+ msg;
		}
	}
	public Integer getInsertNum() {
		return insertNum;
	}
	public void setInsertNum(Integer insertNum) {
		this.insertNum =a.incrementAndGet();
	}
	public Integer getCountNum() {
		return countNum;
	}
	public void setCountNum(Integer countNum) {
		this.countNum = countNum;
	}
	@Override
	public String toString() {
		return "{\"tableName\":\"" + tableName + "\", \"msg\":\"" + msg
				+ "\", \"insertNum\":" + insertNum + ", \"countNum\":" + countNum + "}";
	}
}
