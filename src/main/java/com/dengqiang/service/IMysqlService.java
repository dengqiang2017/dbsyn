package com.dengqiang.service;

import java.util.List;
import java.util.Map;

import com.dengqiang.controller.SynDataLogBean;

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
	 * @param list 数据集
	 * @param bean 同步数据log
	 * @return
	 * @throws Exception 
	 */
	SynDataLogBean insertList(String tableName,
			List<Map<String, Object>> list, SynDataLogBean bean) throws Exception;
	/**
	 * 获取所有用户表
	 * @param tableName
	 * @param count true-显示所有表中的数据总数
	 * @return
	 */
	List<Map<String, Object>> getAllTableName(String tableName, String count);
	/**
	 * 获取表结构
	 * @param tableName
	 * @return
	 */
	List<Map<String, Object>> getTableStructure(String tableName);
	/**
	 * 清空数据
	 * @param tableNames
	 * @return
	 */
	String cleraData(String[] tableNames);

}
