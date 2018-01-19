package com.dengqiang.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;

import com.dengqiang.service.IMssqlService;
import com.dengqiang.service.IMysqlService;
import com.dengqiang.service.SpringContextHolder;
/**
 * 数据同步线程类
 * @author Administrator
 *
 */
public class SynDataRunnable extends BaseController  implements Runnable{
	
	private HttpSession request;
	
	private String tableName;
	private String path;
	
	public SynDataRunnable(String tableName,String path) {
		if (StringUtils.isBlank(tableName)) {
			throw new RuntimeException("没有获取到表名!");
		}
		 this.tableName=tableName;
		 this.path=path;
	}
	private SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
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
						new FileOutputStream(file,true),
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
			if (StringUtils.isNotBlank(tableName)) {
				IMssqlService  mssqlService = (IMssqlService) SpringContextHolder.getBean("mssqlService");
				IMysqlService  mysqlService = (IMysqlService) SpringContextHolder.getBean("mysqlService");
				Map<String, Object> param=new HashMap<>();
				param.put("tableName", tableName);
				List<Map<String, Object>> list=mssqlService.getDataByTableName(param);
				if (list!=null&&list.size()>0) {
					@SuppressWarnings("unchecked")
					List<SynDataBean> beans=(List<SynDataBean>) request.getAttribute("beans");
					SynDataBean bean=null;
					if(beans==null){
						beans=new CopyOnWriteArrayList<>();
						bean=new SynDataBean(tableName);
						beans.add(bean);
					}else{
						for (int i = 0; i < beans.size(); i++) {
							SynDataBean synDataBean=beans.get(i);
							if(tableName.equals(synDataBean.getTableName())){
								bean=synDataBean;
								break;
							}
						}
						if(bean==null){
							bean=new SynDataBean(tableName);
							beans.add(bean);
						}
					}
					List<Map<String, Object>> filedList=mssqlService.getTableStructure(tableName);
					bean.setCountNum(list.size());
					mysqlService.insertList(tableName,filedList,list,bean);
					request.setAttribute("beans", beans);
					File file=new File(path+"log\\insert"+format.format(new Date())+".log");
					try {
						JSONArray jsons=JSONArray.fromObject(beans);
						saveFile(file,jsons.toString());
					} catch (Exception e) {
						System.out.println(beans);
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
