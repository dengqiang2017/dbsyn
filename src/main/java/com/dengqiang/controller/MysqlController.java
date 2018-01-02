package com.dengqiang.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dengqiang.service.IMssqlService;
import com.dengqiang.service.IMysqlService;

@Controller
@RequestMapping("/mysql")
public class MysqlController extends BaseController{
	public static Logger log = Logger.getLogger(MysqlController.class);
	@Autowired
	private IMysqlService mysqlService;
	
	@Autowired
	private IMssqlService mssqlService;
	
	/**
	 * 同步数据表结构
	 * @param tableNames {tableName}
	 * @return
	 */
	@RequestMapping("synTable")
	@ResponseBody
	public ResultInfo synTable(HttpServletRequest request,String tableName) {
		String msg=null;
		boolean success=false;
		try {
			if (StringUtils.isNotBlank(tableName)) {
				String[] tableNames=tableName.split(",");
				for (int i = 0; i < tableNames.length; i++) {
					String name=tableNames[i];
					if(name.contains("$")){
						continue;
					}
					if (StringUtils.isNotBlank(name)) {
						List<Map<String, Object>> filedList=mssqlService.getTableStructure(name);
						try {
							boolean b=false;
							for (Iterator<Map<String, Object>> iterator = filedList.iterator(); iterator
									.hasNext();) {
								Map<String, Object> map =iterator.next();
								if("tinyint".equals(map.get("type_name"))){
									map.put("type_name", "int");
								}
								if("uniqueidentifier".equals(map.get("type_name"))){
									map.put("type_name", "varchar");
								}
								if("show".equals(map.get("column_name"))){
									map.put("column_name", "f_show");
								}
								if("image".equals(map.get("type_name"))){
									iterator.remove();
								}
								if("seeds_id".equals(map.get("column_name"))){
									b=true;
								}
							}
							if(b){
								for (Iterator<Map<String, Object>> iterator = filedList.iterator(); iterator
										.hasNext();) {
									Map<String, Object> map =iterator.next();
									if("id".equals(map.get("column_name").toString().toLowerCase())){
										iterator.remove();break;
									}
								}
							}
							log.error(name);
							msg=mysqlService.createTable(name,filedList);
						} catch (Exception e) {
							e.printStackTrace();
							break;
						}
					}
				}
				success=true;
			}else{
				msg="没有获取到数据表名称";
			}
		} catch (Exception e) {
			msg=e.getMessage();
			e.printStackTrace();
		}
		return new ResultInfo(success, msg);
	}
	/**
	 * 同步数据
	 * @param request
	 * @return
	 */
	@RequestMapping("synData")
	@ResponseBody
	public ResultInfo synData(HttpServletRequest request,String tableName) {
		String msg=null;
		boolean success=false;
		try {
			if (StringUtils.isNotBlank(tableName)) {
				String[] tableNames=tableName.split(",");
				Map<String, Object> map=getKeyAndValue(request);
				for (int i = 0; i < tableNames.length; i++) {
					map.put("tableName", tableNames[i]);
					List<Map<String, Object>> list=mssqlService.getDataByTableName(map);
					List<Map<String, Object>> filedList=mssqlService.getTableStructure( tableNames[i]);
					Map<String, Object> param=new HashMap<String, Object>();
					param.put("filedList", filedList);
					param.put("list", list);
					param.put("tableName", tableNames[i]);
					msg=mysqlService.insertList(tableNames[i],filedList,list);
				}
				success=true;
			}else{
				msg="没有获取到数据表名称";
			}
		}catch (Exception e) {
			msg=e.getMessage();
			e.printStackTrace();
		}
		return new ResultInfo(success, msg);
	}
	/**
	 * 
	 * @param request
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getAllTableName")
	@ResponseBody
	public List<Map<String, Object>> getAllTableName(HttpServletRequest request,String tableName) throws Exception {
		if (StringUtils.isNotBlank(tableName)) {
			if(!"undefined".equals(tableName)){
				tableName="%"+tableName+"%";
			}else{
				tableName=null;
			}
		}else{
			tableName=null;
		}
		log.error("----");
		return mysqlService.getAllTableName(tableName);
	}
	/**
	 * 获取表结构
	 * @param request
	 * @param tableName
	 * @return
	 */
	@RequestMapping("getTableStructure")
	@ResponseBody
	public List<Map<String, Object>> getTableStructure(HttpServletRequest request,String tableName) {
		if (StringUtils.isBlank(tableName)) {
		    return null;
		}
		return mysqlService.getTableStructure(tableName);
	}
	
}