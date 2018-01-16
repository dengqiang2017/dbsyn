package com.dengqiang.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dengqiang.controller.BaseController;
import com.dengqiang.controller.SynDataBean;
import com.dengqiang.dao.mysql.IMysqlDAO;
import com.dengqiang.service.IMysqlService;
import com.dengqiang.tran.Transactional;
@Service("mysqlService")
public class MysqlServerImpl extends BaseController implements IMysqlService {

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
			List<Map<String, Object>> filedList, List<Map<String, Object>> list,SynDataBean bean) {
		Integer count=mysqlDao.getCount(tableName);
		if (count!=null&&count>0) {
			return "表中有数据,跳过!";
		}
		StringBuffer keys=new StringBuffer("insert into ");
		keys.append(tableName).append("(");
		StringBuffer vals=new StringBuffer(")VALUES(");
		Map<String, String> filedMap=new HashMap<>();
		for (Iterator<Map<String, Object>> iterator = filedList.iterator(); iterator.hasNext();) {
			Map<String, Object> map = iterator.next();
			Object column_name=map.get("column_name");
			if("image".equals(map.get("type_name"))){
				iterator.remove();
			}else if (column_name.toString().toLowerCase().contains("seeds_id")) {
				iterator.remove();
			}else if("id".equals(column_name.toString().toLowerCase())){
				iterator.remove();
			}else{
				keys.append(map.get("column_name")).append(",");
				vals.append("#{").append(map.get("column_name")).append("},");
				if("varchar".equals(map.get("type_name"))||"char".equals(map.get("type_name"))){
					filedMap.put(MapUtils.getString(map, "column_name"), "String");
				}
			}
		}
		keys=new StringBuffer(keys.substring(0, keys.length()-1));
		vals=new StringBuffer(vals.substring(0, vals.length()-1));
		vals.append(");");
		keys.append(vals.toString());
		for (int i = 0; i < list.size(); i++) {
			//数据处理,去空串
			Map<String, Object> data=list.get(i);
			Set<Entry<String, Object>> set= data.entrySet();
			for (Entry<String, Object> entry : set) {
				if("String".equals(filedMap.get(entry.getKey()))){
					if (entry.getValue()==null) {
						data.put(entry.getKey(), "");
					}else{
						data.put(entry.getKey(), entry.getValue().toString().trim());
					}
				}
			}
			data.put("sql", keys.toString());
			try {
				mysqlDao.insert(data);
				bean.setInsertNum(i);
			} catch (Exception e) {//
				bean.setMsg(e.getMessage()+data.toString());
			}
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
