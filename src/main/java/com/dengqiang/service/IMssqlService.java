package com.dengqiang.service;

import java.util.List;
import java.util.Map;

public interface IMssqlService {
	/**
	 * 获取所有表名称
	 * @param tableName 
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> getAllTableName(String tableName) throws Exception;
	/**
	 * 获取表结构
	 * @param tableName 表名
	 * @return
	 */
	List<Map<String, Object>> getTableStructure(String tableName) throws Exception;
	/**
	 * 获取指定表下指定运营商的数据
	 * @param tableName
	 * @param com_id
	 * @return
	 */
	List<Map<String, Object>> getDataByTableName(Map<String, Object> map) throws Exception;
	/**
	 * 获取表中的数据
	 * @param request
	 * @param tableName 表名称
	 * @param rows 数据
	 * @return
	 * @throws Exception
	 */
	List<List<Map<String, Object>>> getTableData(String tableName, Integer rows) throws Exception;
	
}
