package com.dengqiang.service;

import java.util.List;
import java.util.Map;

import com.dengqiang.controller.SynDataBean;

public interface IMysqlService {
	/**
	 * 创建表
	 * @param tableName 表名
	 * @param filedList 字段列表
	 * @return
	 */
	String createTable(String tableName, List<Map<String, Object>> filedList) throws Exception;
	/**
	 * 批量插入数据
	 * @param tableName 表名称
	 * @param filedList 字段名称
	 * @param list 数据集
	 * @param bean 
	 * @return
	 */
	String insertList(String tableName, List<Map<String, Object>> filedList,
			List<Map<String, Object>> list, SynDataBean bean);
	/**
	 * 获取所有用户表
	 * @param tableName
	 * @return
	 */
	List<Map<String, Object>> getAllTableName(String tableName);
	/**
	 * 获取表结构
	 * @param tableName
	 * @return
	 */
	List<Map<String, Object>> getTableStructure(String tableName);

}
