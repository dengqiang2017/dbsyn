package com.dengqiang.dao.mysql;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface IMysqlDAO {
	/**
	 * 创建表
	 * @param tableName 表名
	 * @param filedList 字段列表
	 * @return
	 */
	Integer createTable(Map<String, Object> map);
	/**
	 * 插入数据
	 * @param data 
	 * @param filedList 字段名称
	 * @param list 数据集
	 * @return
	 */
	Integer insert(Map<String, Object> data);
	/**
	 * 获取所有数据表名称
	 * @param tableName 
	 * @return
	 */
	List<Map<String, Object>> getAllTableName(@Param("tableName")String tableName);
	/**
	 * 获取表结构
	 * @param tableName
	 * @return
	 */
	List<Map<String, Object>> getTableStructure(@Param("tableName")String tableName);
	/**
	 * 
	 * @param tableName
	 * @return
	 */
	Integer getCount(@Param("tableName")String tableName);
	/**
	 * 
	 * @param tableNames
	 * @return
	 */
	Integer cleraData(Map<String, Object> map);
	
}
