package com.dengqiang.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dengqiang.dao.mysql.IMysqlDAO;
import com.dengqiang.service.IMysqlService;
import com.dengqiang.tran.Transactional;
@Service("mysqlService")
public class MysqlServerImpl implements IMysqlService {

	@Autowired
	private IMysqlDAO mysqlDao;
	
	@Transactional
	@Override
	public String createTable(String tableName,
			List<Map<String, Object>> filedList)throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("tableName", tableName);
		map.put("filedList", filedList);
		return mysqlDao.createTable(map)+"";
	}

	@Transactional
	@Override
	public String insertList(String tableName,
			List<Map<String, Object>> filedList, List<Map<String, Object>> list) {
		for (Iterator<Map<String, Object>> iterator = list.iterator(); iterator.hasNext();) {
			Map<String, Object> map = iterator.next();
			Object column_name=map.get("column_name");
			if ("seeds_id".equals(column_name)) {
				iterator.remove();
			}
			if("id".equals(column_name.toString().toLowerCase())){
				iterator.remove();
			}
		}
		for (Map<String, Object> data : list) {
			StringBuffer buffer=new StringBuffer("insert into ");
			buffer.append(tableName).append("(");
			for (Map<String, Object> map : filedList) {
				buffer.append(map.get("column_name")).append(",");
			}
			buffer=new StringBuffer(buffer.substring(0, buffer.length()-1));
			buffer.append(")VALUES(");
			for (Map<String, Object> map : filedList) {
				buffer.append(data.get(map.get("column_name")));
			}
			buffer=new StringBuffer(buffer.substring(0, buffer.length()-1));
			buffer.append(");");
			Integer i=mysqlDao.insert(buffer.toString());
		}
		return null;
	}
	
	@Override
	public List<Map<String, Object>> getAllTableName(String tableName) {
		return mysqlDao.getAllTableName(tableName);
	}
	@Override
	public List<Map<String, Object>> getTableStructure(String tableName) {
		
		return mysqlDao.getTableStructure(tableName);
	}	
}
