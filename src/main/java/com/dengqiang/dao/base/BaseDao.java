package com.dengqiang.dao.base;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface BaseDao {
	/**
	 * 查询指定的实体的所有记录
	 * 
	 * @param table 指定的实体的Class 对象    
	 * 
	 * @return T ArrayList 实体集合
	 */
	public List<Map<String, Object>> queryBySql(Map<String, Object> map);
	List<Map<String, Object>> getAll();
	/**
	 *查询指定ID的实体
	 * 
	 * @param table
	 *          指定实体的Class对象	 
	 * @param pk
	 *         指定实体主键
	 * @param pkValue
	 * 			指定实体主键的值
	 * 
	 * @return T 对应Id的实体
	 */
	public <T> T queryByID(Object pkValue);
	/**
	 * 写入实体对像
	 * 
	 * @param table
	 *            指定实体的class对象
	 * @param obj
	 *           实体对象的实例
	 * @return T 对应Id的实体
	 */
	public void insert(Map<String, Object> map);
	/**
	 * 删除指定的ID实体
	 * 
	 * @param table
	 *          指定的实体的class对象
	 * @param pk
	 *          指定实体主键
	 * @param pkValue
	 * 			指定实体主键的值
	 * 
	 * @return T 对应Id的实体
	 */
	public void deleteByID(Object pkValue);
	/**
	 * 更新
	 * @param table
	 * @param obj
	 * @param pk
	 * @param pkValue
	 * @return T 对应Id的实体
	 */
	public void updateByID(Map<String, Object> map);
	/**
	 * 统计信息条数
	 * @param obj
	 * @return
	 */
	public int count(Object obj); 
	/**
	 * 根据动态sql插入或者更新数据表
	 * @param sSql
	 */
	Integer insertSql(@Param("sSql")String sSql);
	/**
	 * 查询一个字段的值
	 * @param table 表名
	 * @param showFiledName 显示字段名称
	 * @param findFiledName 查询字段名称加查询值
	 * @return 数据值
	 */
	Object getOneFiledNameByID(Map<String, Object> map);
}
