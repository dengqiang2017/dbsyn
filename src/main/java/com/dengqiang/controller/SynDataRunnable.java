package com.dengqiang.controller;

import java.io.File;
import java.text.SimpleDateFormat;
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
public class SynDataRunnable extends BaseController implements Runnable{
	
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
	@Override
	public void run() {
		try {
			if (StringUtils.isNotBlank(tableName)) {
				IMssqlService  mssqlService = (IMssqlService) SpringContextHolder.getBean("mssqlService");
				IMysqlService  mysqlService = (IMysqlService) SpringContextHolder.getBean("mysqlService");
				Map<String, Object> param=new HashMap<>();
				param.put("tableName", tableName);
				List<Map<String, Object>> list=mssqlService.getDataByTableName(param);
				@SuppressWarnings("unchecked")
				List<SynDataLogBean> beans=(List<SynDataLogBean>) request.getAttribute("beans");
				SynDataLogBean bean=null;
				for (int i = 0; i < beans.size(); i++) {
					SynDataLogBean synDataBean=beans.get(i);
					if(tableName.equals(synDataBean.getTableName())){
						bean=synDataBean;
						break;
					}
				}
				if(bean==null){
					bean=new SynDataLogBean(tableName);
					beans.add(bean);
				}
				bean.setCountNum(list.size());
				if (list!=null&&list.size()>0) {
					bean=mysqlService.insertList(tableName,list,bean);
					File file=new File(path+"log\\insert"+format.format(new Date())+".log");
					try {
						JSONArray jsons=JSONArray.fromObject(beans.toString());
						saveFile(file,jsons.toString()+"\r\n",false);
					} catch (Exception e) {
						System.out.println(beans);
						e.printStackTrace();
					}
					request.setAttribute("beans", beans);
				}else{
					bean.setInsertNum(0);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
