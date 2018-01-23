package com.dengqiang.service.impl;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.dengqiang.controller.BaseController;
import com.dengqiang.controller.SynDataLogBean;
import com.dengqiang.dao.mysql.IMysqlDAO;
import com.dengqiang.service.IMysqlService;
import com.dengqiang.tran.Transactional;
@Service("mysqlService")
@Scope("prototype")
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
	public SynDataLogBean insertList(String tableName, List<Map<String, Object>> list,SynDataLogBean bean) throws Exception {
		Integer count=mysqlDao.getCount(tableName);
		if (count!=null&&count>0) {
			bean.setMsg("表中有数据,跳过!");
			return bean;
		}
		if (list!=null&&list.size()>0) {
			if (list.size()>500) {
				int len=list.size()/500;
				int len2=list.size()%500;
				ExecutorService threadPool = Executors.newFixedThreadPool(len);
				for (int i = 0; i < len; i++) {
					// TODO 复制list中的数据到每个分别的线程中
					Object[] obj= Arrays.copyOfRange(list.toArray(), i*500, (i+1)*500);
					InsertRunnable insert=new InsertRunnable(obj, tableName, bean);
					threadPool.execute(insert);
				}
				if (len2>0) {
					Object[] obj= Arrays.copyOfRange(list.toArray(), len*500, list.size());
					InsertRunnable insert=new InsertRunnable(obj, tableName, bean);
					threadPool.execute(insert); 
				}
				threadPool.shutdown();
			}else {
				insert(list, tableName, bean);
			}
		}
		return bean;
	}
	class InsertRunnable implements Runnable{
		private Object[] objs;
		private String tableName;
		private SynDataLogBean bean;
		
		public InsertRunnable(Object[] objs,String tableName,SynDataLogBean bean) {
			this.objs = objs;
			this.tableName = tableName;
			this.bean = bean;
		}
		@Override
		public void run() {
			for (int i = 0; i < objs.length; i++) {
				@SuppressWarnings("unchecked")
				Map<String, Object> data=(Map<String, Object>) objs[i];
				if (data!=null&&!data.isEmpty()) {
					String sqlStr=getSql(data, tableName);
					data.put("sql", sqlStr);
					try {
						mysqlDao.insert(data);
					} catch (Exception e) {
						System.out.println(objs[i]);
					}
					bean.setInsertNum(1);
					File file=new File("D:\\logs\\data.log");
					saveFile(file,sqlStr.toString()+"\r\n",true);
				}
			}
		}
		
	}
	/**
	 * 将sql中参数对应,并返回完整sql
	 * @param data
	 * @param tableName
	 * @return
	 */
	private static synchronized String getSql(Map<String, Object> data, String tableName){
		Set<Entry<String, Object>> set= data.entrySet();
		if (data==null||data.isEmpty()) {
			throw new RuntimeException("没有数据");
		}
		StringBuffer sql=new StringBuffer("insert into ");
		sql.append(tableName).append("(");
		StringBuffer vals=new StringBuffer(")VALUES(");
		for (Entry<String, Object> entry : set) {
			Object value=entry.getValue();
			if (value!=null) {
				String key=entry.getKey();
				if ("id".equals(key.toLowerCase())) {
				}else if(key.toLowerCase().contains("seeds_id")){
				}else if(value.toString().contains("BlobImpl")){
					File file=new File("D:\\logs\\data-sql.log");
					saveFile(file,value.toString()+"\r\n",true);
				}else{
					if ("show".equals(key.toLowerCase())) {
						key="f_show";
					}
					sql.append(key).append(",");
					if(value!=null){
						if (value instanceof String) {
							data.put(key, value.toString().trim());
						}
					}
//					if (value instanceof Number) {
//						vals.append(value.toString().trim()).append(",");
//					}else{
//						vals.append("'").append(value.toString().trim()).append("',");
//					}
					vals.append("#{").append(key).append("},");
				}
			}
		}
		String sqlStr=sql.substring(0, sql.length()-1);
		sqlStr=sqlStr+vals.substring(0, vals.length()-1);
		return sqlStr+")";
	}
	
	private synchronized void insert(List<Map<String, Object>> list,String tableName,SynDataLogBean bean) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> data=list.get(i);
			if (data!=null&&!data.isEmpty()) {
				String sqlStr=getSql(data, tableName);
				data.put("sql", sqlStr);
				try {
					mysqlDao.insert(data);
				} catch (Exception e) {
					System.out.println(list.get(i));
				}
				bean.setInsertNum(1);
				File file=new File("D:\\logs\\data.log");
				saveFile(file,sqlStr.toString()+"\r\n",true);
			}
		}
	}
	
	@Override
	public List<Map<String, Object>> getAllTableName(String tableName, String count) {
		List<Map<String, Object>> list=mysqlDao.getAllTableName(tableName);
		if ("true".equals(count)) {
			for (Iterator<Map<String, Object>> iterator = list.iterator(); iterator.hasNext();) {
				Map<String, Object> map = iterator.next();
				map.put("count", mysqlDao.getCount(MapUtils.getString(map, "table_name")));
			}
		}
		return list;
	}
	@Override
	public List<Map<String, Object>> getTableStructure(String tableName) {
		
		return mysqlDao.getTableStructure(tableName);
	}
	
	@Override
	public String cleraData(String[] tableNames) {
		Map<String, Object> map=new HashMap<>();
		map.put("tableNames", tableNames);
		mysqlDao.cleraData(map);
		System.out.println("清除完成!");
		return null;
	}
}