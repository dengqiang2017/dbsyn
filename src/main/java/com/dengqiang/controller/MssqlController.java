package com.dengqiang.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dengqiang.service.IMssqlService;

@Controller
@RequestMapping("mssql")
public class MssqlController extends BaseController{

	@Autowired
	private IMssqlService mssqlService;
	/**
	 * 获取表名称
	 * @param request
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getAllTableName")
	@ResponseBody
	public List<Map<String, Object>> getAllTableName(HttpServletRequest request) throws Exception {
		String tableName=request.getParameter("tableName");
		String count=request.getParameter("count");
		if (StringUtils.isNotBlank(tableName)) {
			if(!"undefined".equals(tableName)){
				tableName="%"+tableName+"%";
			}else{
				tableName=null;
			}
		}else{
			tableName=null;
		}
		return mssqlService.getAllTableName(tableName,count);
	}
	/**
	 * 获取表结构
	 * @param request
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getTableStructure")
	@ResponseBody
	public List<Map<String, Object>> getTableStructure(HttpServletRequest request,String tableName) throws Exception {
		if (StringUtils.isBlank(tableName)) {
		    return null;
		}
		return mssqlService.getTableStructure(tableName);
	}
	/**
	 * 获取表中的数据
	 * @param request
	 * @param tableName 表名称
	 * @param rows 数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getTableData")
	@ResponseBody
	public List<List<Map<String, Object>>> getTableData(HttpServletRequest request,String tableName,Integer rows) throws Exception {
		if (StringUtils.isBlank(tableName)) {
			return null;
		}
		if (rows==null||rows<=0) {
			rows=10;
		}
		return mssqlService.getTableData(tableName,rows);
	}
	@RequestMapping("getTableDataByPage")
	@ResponseBody
	public PageList<Map<String, Object>> getTableDataByPage(HttpServletRequest request,String tableName,Integer rows) throws Exception {
		if (StringUtils.isBlank(tableName)) {
			return null;
		}
		if (rows==null||rows<=0) {
			rows=10;
		}
		String pageStr=request.getParameter("page");
		String sidx=request.getParameter("sidx");
		String sord=request.getParameter("sord");
		if(StringUtils.isBlank(pageStr)){
			pageStr="0";
		}
		Integer page=Integer.parseInt(pageStr);
		PageList<Map<String, Object>> pages=new PageList<>(page, rows, 100);
		List<Map<String, Object>> list=mssqlService.getTableData(tableName,100).get(1);
		pages.setRows(list);
		return pages;
	}
	
}
