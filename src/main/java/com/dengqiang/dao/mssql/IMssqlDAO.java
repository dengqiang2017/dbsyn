package com.dengqiang.dao.mssql;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface IMssqlDAO {
	/**
	 * 所有表名称
	 * @param tableName 
	 * @return
	 */
	List<Map<String, Object>> getAllTableName(@Param("tableName")String tableName);
	/**
	 * 获取表结构
	 * @param tableName 表名
	 * @return
	 */
	List<Map<String, Object>> getTableStructure(@Param("tableName")String tableName);
	/**
	 * 获取指定表下指定运营商的数据
	 * @param tableName
	 * @param com_id
	 * @return
	 */
	List<Map<String, Object>> getDataByTableName(Map<String, Object> map);
	/**
	 * 
	 * @param tableName
	 * @param rows
	 * @return
	 */
	List<Map<String, Object>> getTableData(@Param("tableName")String tableName, @Param("rows")Integer rows);

}
