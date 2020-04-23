<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'left.jsp' starting page</title>
    
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
    <div class="layui-col-md2 layui-col-xs3 layui-col-md-offset1  layui-col-xs-offset1">

        <div class="site-tree">
            <ul class="layui-tree">

                <li><h2>基本设置</h2></li>

                <li class="site-tree-noicon site-tree-active">
                    <a href="JavaScript:void(0);" lay-event="userInfo" lay-url="/client/user-center.html#tab=userInfo">
                        <i class="layui-icon layui-icon-about"></i>
                        <cite>个人信息</cite>
                        <em>UserInfo</em>
                    </a>
                </li>
                <li class="site-tree-noicon layui-this">
                    <a href="JavaScript:void(0);" lay-event="resetPass" lay-url="/client/user-center.html#tab=resetPass">

                        <i class="layui-icon layui-icon-password"></i>
                        <cite>重置密码</cite>
                        <em>Password</em>
                    </a>
                </li>
                <li class="site-tree-noicon ">
                    <a href="JavaScript:void(0);" lay-event="photo" lay-url="/client/user-center.html#tab=photo" >
                        <i class="layui-icon layui-icon-picture"></i>
                        <cite>修改头像</cite>
                        <em>Photo</em>
                    </a>
                </li>


                <li><h2>博客管理</h2></li>


                <li class="site-tree-noicon    ">
                    <a href="JavaScript:void(0);"  lay-event="sendBlog" lay-url="/client/user-blog.html#tab=sendBlog" >
                        <i class="layui-icon layui-icon-add-circle"></i>
                        <cite>发布博客</cite>
                        <em>Send Blog</em>
                    </a>
                </li>
                <li class="site-tree-noicon ">
                    <a href="JavaScript:void(0);" lay-event="blogList" lay-url="/client/user-blog.html#tab=blogList" >
                        <i class="layui-icon layui-icon-list"></i>
                        <cite>博客列表</cite>
                        <em>Blog List</em>
                    </a>
                </li>
                <li class="site-tree-noicon ">
                    <a href="JavaScript:void(0);" lay-event="blogDraft"  lay-url="/client/user-blog.html#tab=blogDraft" >
                        <i class="layui-icon layui-icon-delete"></i>
                        <cite>草稿箱</cite>
                        <em>Draft box</em>
                    </a>
                </li>


                <li><h2>微语管理</h2></li>

                <li class="site-tree-noicon ">
                    <a href="JavaScript:void(0);" lay-even="sendWhisper"  lay-url="/client/user-whisper.html#tab=sendWhisper" >
                        <i class="layui-icon layui-icon-add-circle"></i>
                        <cite>发布微语</cite>
                        <em>Send Whisper</em>
                    </a>
                </li>
                <li class="site-tree-noicon ">
                    <a href="JavaScript:void(0);" lay-even="whisperList"  lay-url="/client/user-whisper.html#tab=whisperList" >
                        <i class="layui-icon layui-icon-list"></i>
                        <cite>微语列表</cite>
                        <em>Whisper List</em>
                    </a>
                </li>
                <li class="site-tree-noicon ">
                    <a href="JavaScript:void(0);" lay-even="whisperDraft" lay-url="/client/user-whisper.html#tab=whisperDraft" >
                        <i class="layui-icon layui-icon-delete"></i>
                        <cite>草稿箱</cite>
                        <em>Draft box</em>
                    </a>
                </li>

                <li><h2>相册管理</h2></li>

                <li class="site-tree-noicon ">
                    <a href="JavaScript:void(0);" lay-even="newAlbum" lay-url="/client/user-album.html#tab=newAlbum" >
                        <i class="layui-icon layui-icon-add-circle"></i>
                        <cite>创建新相册</cite>
                        <em>New Album</em>
                    </a>
                </li>
                <li class="site-tree-noicon ">
                    <a href="JavaScript:void(0);" lay-even="albumList" lay-url="/client/user-album.html#tab=albumList" >
                        <i class="layui-icon layui-icon-list"></i>
                        <cite>相册列表</cite>
                        <em>Album List</em>
                    </a>
                </li>
                <li class="site-tree-noicon "  >
                    <a href="JavaScript:void(0);" lay-even="albumDraft"  lay-url="/client/user-album.html#tab=albumDraft" >
                        <i class="layui-icon layui-icon-delete"></i>
                        <cite>草稿箱</cite>
                        <em>Draft box</em>
                    </a>
                </li>
            </ul>
        </div>


    </div>
  </body>
</html>
