<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/admin/jsp/";
%>
<%@page import="com.fasterxml.jackson.databind.ser.SerializerFactory" import="com.fasterxml.jackson.core.JsonGenerator.Feature"%>
<%@page import="java.io.PrintWriter"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"  import="com.fasterxml.jackson.databind.ObjectMapper"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:if test="${not empty param.table}">
	<c:import url="conn.jsp"></c:import>
	<sql:query dataSource="${sessionScope.shop}" var="result" scope="session">
	SELECT * from <c:out value="${param.table}"></c:out> limit 1;
	</sql:query>
	<c:set value="${result.columnNames}" var="columnNames"></c:set>
<%
String json = (String)request.getAttribute("jtype");
ObjectMapper mapper = new ObjectMapper();
LinkedHashMap<String,LinkedHashMap<String, Object>> jtypeList = (LinkedHashMap<String,LinkedHashMap<String, Object>>)mapper.readValue(json, LinkedHashMap.class);
LinkedHashMap<String, Object> temp;
LinkedHashMap<String, Object> idField=jtypeList.get("idField");
String idKey = "";
if(!idField.isEmpty()){
	 
	
	idKey = idField.keySet().iterator().next();
	String res="";
	String[] colNameList = (String[])pageContext.getAttribute("columnNames");
	for(int i = 0;colNameList.length>i;i++){
		res = colNameList[i];
		if(res.equalsIgnoreCase(idKey)){
			idKey = res;
			break;
		}
	}
}
String colName;
String type;
%>
	
	<table id="<c:out value="${param.id}"></c:out>"  style="width:100%;height:<c:out value="${param.h}"></c:out>px;"
			toolbar="div[name=<c:out value="${param.id}"></c:out>" pagination="true" collapsible="false"
			rownumbers="true" fitColumns="true" singleSelect="true" striped="true" idField="<%=idKey!=""?idKey:"id" %>" 
			pageList=[2,5,10,20,30,40,50]
			>
		<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<c:forEach var="col" items="${result.columnNames}">
				<c:set var="colName" value="${col}" ></c:set>
				<%colName = (String)pageContext.getAttribute("colName");//驼峰
				//System.out.println(colName);
				temp = jtypeList.get(colName.toLowerCase());
				type= (String)temp.get("type");
				//pageContext.setAttribute("temp", temp);
					//System.out.println(temp);
				%>			
				<th field='<c:out value="${col}"/>' width="auto" sortable="true"
				editor="{type:'<%=type.equals("dateTime")?"datetimebox":type.equals("date")?"datebox":type.equals("number")?"numberbox":"validatebox" %>',options:{<%=type.equals("number")?"precision:2,":""%><%=type.equals("mail")?"validType:'email',":type.equals("date")?"validType:'date',":type.equals("intonly")?"validType:'intonly',":type.equals("phone")?"validType:['intonly','phone'],":type.equals("bool")?"validType:'bool',":""%>required:<%=temp.get("required")%>}}">	
				<c:out value="${fn:toUpperCase(fn:substring(col, 0,1))}${fn:substring(col, 1,fn:length(col))}"/></th>
			
				</c:forEach>
			</tr>
		</thead>
	</table>

<div  name="<c:out value="${param.id}"></c:out>">
		<a href="javascript:void(0);" class="easyui-linkbutton"  iconCls="icon-add"  plain="true" onclick="javascript:$('#<c:out value="${param.id}"></c:out>').edatagrid('addRow')">New</a>
		|<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="javascript:$('#<c:out value="${param.id}"></c:out>').edatagrid('destroyRow')">Destroy</a>
		|<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="javascript:$('#<c:out value="${param.id}"></c:out>').edatagrid('saveRow')" >Save</a>
		|<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="javascript:$('#<c:out value="${param.id}"></c:out>').edatagrid('cancelRow')">Cancel</a>
		|
	<span>Selection Mode: </span>
		<select onchange="$('#<c:out value="${param.id}"></c:out>').datagrid({singleSelect:(this.value==0)})">
			<option value="0">Single Row</option>
			<option value="1">Multiple Rows</option>
		</select>
		|SelectOnCheck: <input type="checkbox" checked onchange="$('#<c:out value="${param.id}"></c:out>').datagrid({selectOnCheck:$(this).is(':checked')})">
		|CheckOnSelect: <input type="checkbox" checked onchange="$('#<c:out value="${param.id}"></c:out>').datagrid({checkOnSelect:$(this).is(':checked')})">
	</div>


</c:if>


