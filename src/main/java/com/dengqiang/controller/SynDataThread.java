package com.dengqiang.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;

import com.dengqiang.service.IMssqlService;
import com.dengqiang.service.IMysqlService;
/**
 * 数据同步线程类
 * @author Administrator
 *
 */
public class SynDataThread extends Thread{
	
	private IMysqlService mysqlService;
	
	private IMssqlService mssqlService;
	
	private HttpSession request;
	
	private Map<String, Object> map;
	
	private String tableName;
	
	public SynDataThread(String tableName) {
		if (StringUtils.isBlank(tableName)) {
			throw new RuntimeException("没有获取到表名!");
		}
		 this.tableName=tableName;
	}
	
	public void setMysqlService(IMysqlService mysqlService) {
		this.mysqlService = mysqlService;
	}
	public void setMssqlService(IMssqlService mssqlService) {
		this.mssqlService = mssqlService;
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	public void setRequest(HttpSession request) {
		this.request = request;
	}
	/**
	 * 保存数据到文件中
	 * @param file 文件存储路径
	 * @param str 存储数据
	 */
	public synchronized void saveFile(File file,String str){
			try {
				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}
				OutputStreamWriter outputStream = new OutputStreamWriter(
						new FileOutputStream(file),
						"UTF-8");
				outputStream.write(str);
				outputStream.flush();
				outputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	@Override
	public void run() {
		try {
			String[] tableNames=tableName.split(",");
			for (int j = 0; j < tableNames.length; j++) {
				String name=tableNames[j];
				if (StringUtils.isNotBlank(name)) {
					map.put("tableName", name);
					List<Map<String, Object>> list=mssqlService.getDataByTableName(map);
					if (list!=null&&list.size()>0) {
						@SuppressWarnings("unchecked")
						List<SynDataBean> beans=(List<SynDataBean>) request.getAttribute("beans");
						SynDataBean bean=null;
						if(beans==null){
							beans=new ArrayList<>();
							bean=new SynDataBean(name);
							beans.add(bean);
						}else{
							for (int i = 0; i < beans.size(); i++) {
								SynDataBean synDataBean=beans.get(i);
								if(name.equals(synDataBean.getTableName())){
									bean=synDataBean;
									break;
								}
							}
							if(bean==null){
								bean=new SynDataBean(name);
								beans.add(bean);
							}
						}
						List<Map<String, Object>> filedList=mssqlService.getTableStructure(name);
						Map<String, Object> param=new HashMap<String, Object>();
						param.put("filedList", filedList);
						param.put("list", list);
						param.put("tableName", name);
						bean.setCountNum(list.size());
						mysqlService.insertList(name,filedList,list,bean);
						request.setAttribute("beans", beans);
						File file=new File("E:\\dbsyn\\insert.log");
						JSONArray jsons=JSONArray.fromObject(beans);
						saveFile(file,jsons.toString() );
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
