package com.dengqiang.controller;

public class SynDataBean {
	public SynDataBean(String tableName) {
		this.tableName=tableName;
	}
	String tableName;
	String msg=null;
	Integer insertNum;
	Integer countNum;
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
		this.insertNum = insertNum;
	}
	public Integer getCountNum() {
		return countNum;
	}
	public void setCountNum(Integer countNum) {
		this.countNum = countNum;
	}
	
	
	
}
