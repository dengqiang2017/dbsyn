$("#all").click(function(){
	var b=$(this).prop("checked");
	$("#mssqlList input").prop("checked",b);
});
$("#find").bind("input",function(){
	var tableName=$.trim($(this).val());
	loadData(tableName);
});
loadData();
function loadData(tableName){
	if(!tableName){
		tableName="";
	}
	$("#mssqlList").html("");
	pop_up_box.loadWait();
	$.get("mssql/getAllTableName.do",{"tableName":tableName},function(data){
		for (var i = 0; i < data.length; i++) {
			var n=data[i];
			var li=$("<li><label><input type='checkbox' class='check'><span class='tableName'>"+n.name
					+"</span></label>&emsp;&emsp;<button type='button' class='btn btn-xs btn-info'>表结构数据</button></li>");
			$("#mssqlList").append(li);
			if(i%2==0){
				li.css("background-color","#cccccc");
			}
			li.find("button:eq(0)").click(function(){
				var tableName=$(this).parent().find("span").html(); 
					$("#mssqlStructure").html("<table id=mssqlGrid></table>");
					$("#mssqlGrid").jqGrid({url :"mssql/getTableStructure.do?tableName="+tableName,
				        datatype : "json",
				        colNames : [ '字段名称', '类型', '是否为空', '长度', '主键'],
				        colModel : [
				                     {name : 'column_name',index : 'column_name',width:"100px"}, 
				                     {name : 'type_name',index : 'type_name',width:"80px"}, 
				                     {name : 'is_nullable',index : 'is_nullable',align : "right",width:"80px"}, 
				                     {name : 'max_length',index : 'max_length',align : "right",width:"50px"}, 
				                     {name : 'is_identity',index : 'is_identity',width:"50px"}
				                   ],
				        rowNum : 100,
				        height:"380px", 
						shrinkToFit:false,
				        autowidth:true,
				        viewrecords : true,
				        caption : "表结构"});
				pop_up_box.loadWait();
				$.get("mssql/getTableData.do",{
					"tableName":tableName,
					"rows":20
				},function(data){
					if(data&&data.length>0){ 
						var fildList=data[0];
						var colNames=[];
						var colModel=[];
						for (var k = 0; k < fildList.length; k++) {
							var json=fildList[k];
							$("#dataTable thead tr").append("<th data-name='"+json.column_name+"'>"+json.column_name+"</th>");
							colNames.push(json.column_name);
							colModel.push({name :json.column_name,index :json.column_name});
						}
						$("#dataTable").html("<table id=datagrid></table>");
						$("#datagrid").jqGrid({datatype : "local",
					        colNames : colNames,
					        colModel : colModel,
					        rowNum : 10,
					        height:"100%", 
							shrinkToFit:false,
					        autowidth:true,
					        viewrecords : true,
					        caption : "表示例数据" });
						for (var j = 0; j < data[1].length; j++) {
							$("#datagrid").jqGrid("addRowData",j+1,data[1][j]);
						}
					}else{
						$("#dataTable").html("无数据!");
					}
		   			 pop_up_box.loadWaitClose();
				});
			});
			//
			li.click(function(){
				$(this).parent().find("li").removeClass("select");
				$(this).addClass("select");
			});
		}
		$("#all").parent().append("共"+data.length); 
	    pop_up_box.loadWaitClose();
	});
}
$("#clearTable").click(function(){
	$("#dataTable,#mssqlStructure").html("");
});
$("#syn").click(function(){
	var checks=$(".check:checked");
	var tableName="";
	for (var i = 0; i < checks.length; i++) {
		var name=$(checks[i]).next().html();
		tableName=tableName+","+name;
	}
	pop_up_box.postWait();
	$.get("mysql/synTable.do",{
		"tableName":tableName
	},function(data){
		pop_up_box.loadWaitClose();
		if(data.success){
			pop_up_box.showMsg("创建成功!");
		}else{
			pop_up_box.showMsg(data.msg);
		}
	});
});
$("#synData").click(function(){
	var checks=$(".check:checked");
	var tableName="";
	for (var i = 0; i < checks.length; i++) {
		var name=$(checks[i]).next().html();
		tableName=tableName+","+name;
	}
	pop_up_box.postWait();
	$.get("mysql/synData.do",{
		"tableName":tableName
	},function(data){
		pop_up_box.loadWaitClose();
		if(data.success){
			pop_up_box.showMsg("创建成功!");
		}else{
			pop_up_box.showMsg(data.msg);
		}
	});
});

$("#findMysql").click(function(){
	var tableName=$.trim($(this).val());
	loadMysqlData(tableName);
});
loadMysqlData();
function loadMysqlData(tableName){ 
	if(!tableName){
		tableName="";
	}
	$("#mysqlList").html("");
	pop_up_box.loadWait();
	$.get("mysql/getAllTableName.do",{"tableName":tableName},function(data){
		pop_up_box.loadWaitClose();
		for (var i = 0; i < data.length; i++) {
			var n=data[i];
			var li=$("<li><span class='tableName'>"+n.table_name+"</span></li>");
			$("#mysqlList").append(li);
			if(i%2==0){
				li.css("background-color","#ccc");
			}
		}
		$("#allmysql").parent().append("共"+data.length); 
		$("#mysqlList li").bind("click",function(){
			$(this).parent().find("li").removeClass("select");
			$(this).addClass("select");
			var tableName=$(this).find("span").html();
			$("#mysqlStructure").html("<table id=mysqlgrid></table>");
			$("#mysqlgrid").jqGrid({url :"mysql/getTableStructure.do?tableName="+tableName,
				datatype : "json",
				colNames : [ '字段名称', '类型', '是否为空', '长度', '主键'],
				//column_name,is_nullable,data_type,column_type,character_maximum_length,column_key
				colModel : [
				            {name : 'column_name',index : 'column_name',width:"100px"}, 
				            {name : 'data_type',index : 'type_name',width:"80px"}, 
				            {name : 'is_nullable',index : 'is_nullable',align : "right",width:"100px"}, 
				            {name : 'character_maximum_length',index : 'character_maximum_length',align : "right",width:"50px"}, 
				            {name : 'column_key',index : 'column_key',width:"50px"}
				            ],
				            rowNum : 100,
				            height:"380px", 
				            shrinkToFit:false,
				            autowidth:true,
				            viewrecords : true,
				            caption : "表结构"});
		});
	});
}