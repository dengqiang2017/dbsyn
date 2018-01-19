package com.dengqiang.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.dengqiang.dao.mssql.IMssqlDAO;
import com.dengqiang.service.IMssqlService;
import com.dengqiang.tran.ServiceAspect;
import com.dengqiang.tran.Transactional;

@Service("mssqlService")
@Scope("prototype")
public class MssqlServiceImpl implements IMssqlService {

	@Autowired
	private IMssqlDAO mssqlDao;
	
	@Override
	@Transactional(name=ServiceAspect.transactionManagerMssqlName)
	public List<Map<String, Object>> getAllTableName(String tableName)throws Exception {
		return mssqlDao.getAllTableName(tableName);
	}
	
	@Override
	public List<Map<String, Object>> getTableStructure(String tableName)throws Exception {
		return mssqlDao.getTableStructure(tableName);
	}

	@Override
	public List<Map<String, Object>> getDataByTableName(Map<String, Object> map) {
		return mssqlDao.getDataByTableName(map);
	}

	@Override
	public List<List<Map<String, Object>>> getTableData(String tableName, Integer rows)
			throws Exception {
		List<Map<String, Object>> filedList= mssqlDao.getTableStructure(tableName);
		List<Map<String, Object>> dataList= mssqlDao.getTableData(tableName,rows);
		List<List<Map<String, Object>>> list=new ArrayList<>();
		list.add(filedList);
		list.add(dataList);
		return list;
	}
}
