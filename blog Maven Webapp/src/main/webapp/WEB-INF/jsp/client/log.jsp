<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'logs.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="/static/client/layui/css/layui.css">

  </head>
  
  <body>
  
<ul class="layui-timeline">
	<c:forEach items="${logs}" var="l">
		  <li class="layui-timeline-item">
		    <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
		    <div class="layui-timeline-content layui-text">
		      <h3 class="layui-timeline-title"><fmt:formatDate value="${l.checktime}" pattern="yyyy-MM-dd HH:mm:ss"/></h3>
		      <p>
		        	审核结果:${l.status}
		        <br>
		        	原由：<c:out value="${l.event}" default="无"></c:out>
		      </p>
		    </div>
		  </li>
  </c:forEach>
</ul>
  
  
  
  
	<script src="/static/client/js/jquery-3.4.1.js"></script>
	<script src="/static/client/layui/layui.js"></script>
	<script type="text/javascript"></script>
</body>
  
 
</html>
