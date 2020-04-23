<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>博客中心</title>
    <link rel="stylesheet" href="/static/client/layui/css/layui.css">
    <link rel="stylesheet" href="/static/client/cropper/cropper.css">
    <style>
        .site-tree {
            border-right: 1px solid #eee;
        }
        .site-tree {
            width: 220px;
            min-height: 900px;
            padding: 5px 0 20px;
        }
        .site-tree {
            display: inline-block;
            *display: inline;
            *zoom: 1;
            vertical-align: top;

        }
        .site-tree h2{
            background-color: rgb(242,242,242); font-size: 14px; line-height: 40px;
            border-left: 3px  rgb(65,148,136) solid; text-indent: 1em;
        }
        .site-tree  li{
            text-indent: 1em; line-height: 35px;
        }
        .site-tree  li a{ font-size: 13px; color: #333;  }
        .site-tree  li:hover{
            background-color: rgba(0,0,0,0.4);
            border-left: 3px  white solid;
        }
        .site-tree  li:hover a{
            color: white;
        }
        .site-tree-active{
            background-color: rgb(65,148,136);
            border-left: 3px  white solid;
        }
        .site-tree .site-tree-active a{
            color: white;
        }
    </style>

</head>

<body >
<!-- 引用头部 -->
<jsp:include page="component/header.jsp"></jsp:include>


<div class="layui-row layui-col-space30 "  >
	<!-- 引入左侧导航 -->
   <jsp:include page="component/left.jsp"/>
    <div class="layui-col-md8 layui-col-xs8">

        <div class="layui-tab"  lay-filter="tableInfo">
            <ul class="layui-tab-title">
                <li class="layui-this"  lay-id="userInfo"  > <i class="layui-icon layui-icon-about"></i> 个人信息</li>
                <li lay-id="resetPass"> <i class="layui-icon layui-icon-password"></i> 密码重置</li>
                <li lay-id="photo" > <i class="layui-icon layui-icon-picture"></i> 修改头像</li>
            </ul>
            <div class="layui-tab-content">
                <div class="layui-tab-item layui-show">
				

                    <form class="layui-form" action=""  id="userForm">
                    <input type="hidden" name="token" value="${token}">
                        <div class="layui-form-item">
                            <label class="layui-form-label">昵称</label>
                            <div class="layui-input-block">
                                <input type="text" name="name" value="${loginUser.name}" required  lay-verify="required" placeholder="请输入昵称" autocomplete="off" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">手机</label>
                            <div class="layui-input-block">
                                <input type="text" name="phone"  value="${loginUser.phone}"  placeholder="请输入手机" autocomplete="off" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">出生日期</label>
                            <div class="layui-input-block">
                                <input type="text" name="born" value="<fmt:formatDate value="${loginUser.born}" pattern="yyyy-MM-dd"/>" readonly   placeholder="请选择出生日期" autocomplete="off" class="layui-input date">
                            </div>
                        </div>

                        <div class="layui-form-item">
                            <label class="layui-form-label">QQ号码</label>
                            <div class="layui-input-block">
                                <input type="text" name="qq"   value="${loginUser.qq}"  placeholder="请输入QQ" autocomplete="off" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">微信二维码</label>
                            <div class="layui-input-block">
                            	<c:if test="${empty loginUser.wechat}">
                                	<img width="128" id="showImg"  height="128" src="/static/client/img/default-photo.jpg">
								</c:if>
								<c:if test="${not empty loginUser.wechat}">
                                	<img width="128" id="showImg"  height="128" src="${loginUser.wechat}">
								</c:if>
                                <button type="button" class="layui-btn layui-btn-sm layui-btn-normal cropperFilePicker" id="cropperFilePicker">更换二维码</button>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">感兴趣内容 </label>
                            <div class="layui-input-block">
                            	
                            	<c:forEach items="${articleTypes}" var="a">
                            	<c:set var="hobby" value="${a.id},"></c:set>
                            	
                            		<input type="checkbox" name="hobbys"  value="${a.id}" title="${a.name}" <c:if test="${fn:contains(loginUser.hobby,hobby)}">checked</c:if> lay-skin="primary" >                           	
                            	</c:forEach>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-input-block">
                                <button class="layui-btn" lay-submit lay-filter="formUser">保存</button>
                            </div>
                        </div>
                    </form>




                </div>
                <div class="layui-tab-item">


                    <div class="layui-row">

                        <div class="layui-col-md6 layui-col-md-offset1">
							
                            <form class="layui-form" action="">
                            	<input type="hidden" name="token" value='${token}'>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">原密码</label>
                                    <div class="layui-input-block">
                                        <input type="password" name="originpass" required  lay-verify="required|pass" lay-verType="tips" placeholder="请输入标题" autocomplete="off" class="layui-input">
                                    </div>
                                </div>

                                <div class="layui-form-item">
                                    <label class="layui-form-label">新密码</label>
                                    <div class="layui-input-block">
                                        <input type="password" name="pass" required  lay-verify="required|pass" lay-verType="tips" placeholder="请输入标题" autocomplete="off" class="layui-input">
                                    </div>
                                </div>

                                <div class="layui-form-item">
                                    <label class="layui-form-label">重复新密码</label>
                                    <div class="layui-input-block">
                                        <input type="password" name="repass" required  lay-verify="required|repass"  lay-verType="tips" placeholder="请输入标题" autocomplete="off" class="layui-input">
                                    </div>
                                </div>

                                <div class="layui-form-item">
                                    <div class="layui-input-block">
                                        <button class="layui-btn" lay-submit lay-filter="formPass">保存</button>
                                        <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                                    </div>
                                </div>
                            </form>

                        </div>

                    </div>


                </div>
                <div class="layui-tab-item" >


                        <div class="layui-row">
                            <div class="layui-col-md4  layui-col-md-offset1 userPhoto">
                                  <h3>我的头像</h3>
                                <c:if test="${empty loginUser.photourl}">
                                	<img width="128" id="photoImg"  height="128" src="/static/client/img/default-photo.jpg">
                                </c:if>
                                <c:if test="${not empty loginUser.photourl}">
                                	<img width="128" id="photoImg"  height="128" src="${loginUser.photourl}">
                                </c:if>
                               <p><br><button class="layui-btn"  id="changePhoto" >更换头像</button></p>
                            </div>
                        </div>

                </div>
            </div>
        </div>

    </div>
</div>





<div class="row" style="position: fixed; bottom: 0px; width: 100%">
    <div class="layui-col-md12"  style="background-color: #e8e8e8;line-height: 40px; text-align: center">
        版权所有&copy;2020-2099 Mr_JINGR .  备案号:<a href="">粤ICP1223123</a>
    </div>
</div>


<script src="/static/client/js/jquery-3.4.1.js"></script>
  <script type="text/javascript" src="/static/client/js/jquery-base64.js"></script>
<script src="/static/client/layui/layui.js"></script>
<script type="text/javascript" src="/static/client/cropper/cropper.js"></script>
<script src="/static/client/js/user-center.js"></script>
<script src="/static/client/js/checkLogin.js"></script>
</body>
</html>