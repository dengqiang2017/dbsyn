package com.dengqiang.controller;

import java.util.ArrayList;
import java.util.List;
/**
 * 分页对象
 * @author dengqiang
 *
 * @param <T> 数据集数据类型
 */
public class PageList<T> {
	/**
	 * 当前页
	 */
	private int currentPage;
	/**
	 * 每页记录数
	 */
	private int pageRecord;
	/**
	 * 总记录数
	 */
	private int totalRecord;
	/**
	 * 当前所在页数
	 */
	private int nowPage ;
	/**
	 * 当前所在记录数，
	 */
	private int nowRecord;
	/**
	 * 总页数
	 */
	private int totalPage;
	/**
	 * 数据集
	 */
	private List<T> rows = new ArrayList<T>();

	/**
	 * 分页
	 * @param page 当前页
	 * @param rows 每页记录数
	 * @param totalRecord 总记录数
	 */
	public PageList(int page, int rows, int totalRecord) {
		super();
		this.pageRecord = rows < 1 ? 10 : rows;
		this.totalRecord = totalRecord;
		this.totalPage =( totalRecord+rows - 1 )/ rows;
		this.currentPage=page;
		if (page < 1) {
			this.currentPage = 1;
		}
		if (page > this.totalPage) {
			this.currentPage = totalPage;
		}
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public int getPage() {
		return currentPage;
	}
	
	public void setCurrentPage(int currentPage) {
		if (currentPage < 1) {
			currentPage = 1;
		}
		if (currentPage > this.totalPage) {
			currentPage = this.totalPage;
		}
		this.currentPage = currentPage;
	}
	public int getTotalRecord() {
		return totalRecord;
	}
	public int getRecords() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}

	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}
	public int getPageRecord() {
		return pageRecord;
	}
	public int getNowRecord() {
		return nowRecord;
	}

	public void setNowRecord(int nowRecord) {
		this.nowRecord = nowRecord;
	}
	
	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	// 总页数= 总记录数/每页记录数
	public int getTotalPage() {//从0开始总页数就需要减去1
		return this.totalPage;
	}
	public int getTotal() {//从0开始总页数就需要减去1
		return this.totalPage;
	}

	// 下一页=当前页加1
	public int getNextPage() {
		return Math.min(currentPage + 1, getTotalPage());
	}

	// 上一页
	public int getUpPage() {
		return Math.max(currentPage - 1, 1);
	}
	public int getNowPage() {
		return (currentPage - 1)* pageRecord;
	}
	@Override
	public String toString() {
		return "PageList [currentPage=" + currentPage + ", pageRecord="
				+ pageRecord + ", totalRecord=" + totalRecord + ", nowPage="
				+ nowPage + ", nowRecord=" + nowRecord + ", totalPage="
				+ totalPage + ", rows=" + rows + "]";
	}
 
}
