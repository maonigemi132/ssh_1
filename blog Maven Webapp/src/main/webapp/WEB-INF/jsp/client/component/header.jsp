<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'header.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <div class="layui-layout layui-layout-admin">
    <div class="layui-header layui-bg-green">
        <div class="layui-logo">
            <img src="/static/client/img/logo.png" width="130" height="40">
        </div>
        <!-- 头部区域（可配合layui已有的水平导航） -->
        <div class="layui-nav layui-layout-left" style="margin-top: 12px">        
            <form class="layui-form" action="">
                <div class="layui-inline">
                    <div class="layui-input-inline" style="width: 300px;">
                        <input type="password"  placeholder="搜索博客" name="" autocomplete="off" class="layui-input">
                    </div>
                   <div style="float: right">
                       <button class="layui-btn layui-btn-normal" lay-submit lay-filter="formDemo">搜索</button>
                   </div>
                </div>
            </form>
        </div>

        <ul class="layui-nav layui-layout-right">
        
        <c:if test="${empty loginUser}">
	        <li class="layui-nav-item"><a href="/client/login.html">登录</a></li>
	        <li class="layui-nav-item"><a href="/client/register.html">注册博客</a></li>
	    </c:if>
	    
        <c:if test="${not empty loginUser}">
            <li class="layui-nav-item">
                <a href="javascript:;" class="userPhoto">
                    <img src="${loginUser.photourl}" class="layui-nav-img">
                    <span id="uname">${loginUser.name}</span>
                </a>
                <dl class="layui-nav-child">
                    <dd><a href="/client/user-center.html">个人中心</a></dd>
                    <dd><a href="/client/index.html">我的主页</a></dd>
                </dl>
            </li>
            
            <li class="layui-nav-item"><a href="/user/logout?token=${token}">退出</a></li>
        </c:if>
        </ul>
    </div>
</div>
<!-- <script src="/static/client/layui/layui.js"></script>
<script src="/static/client/js/checkLogin.js"></script> -->

  </body>
</html>
