<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link  rel="stylesheet"  type="text/css"  href="/static/client/layui/css/layui.css" />
    <link rel="stylesheet" href="/static/client/cropper/cropper.css">
</head>
<body style="padding-top: 30px;  padding-bottom: 100px;">


    <div class="layui-row">
        <div class="layui-col-md7  layui-col-md-offset1">


           <form class="layui-form" action=""  id="blogForm">
          			 <input type="hidden" name="uuid" value='${article.uuid}'>
                    <input type="hidden" name="token" value='${token}'>
                    <input type="hidden" name="type" value='0'>
                        <div class="layui-form-item">
                            <label class="layui-form-label">标题</label>
                            <div class="layui-input-block">
                                <input type="text" name="title" value="${article.title}" required  lay-verify="required|title" lay-verType="tips" placeholder="请输入标题（50字内）" autocomplete="off" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">封面</label>
                            <div class="layui-input-block">
                                <img width="228" id="showImg"  height="128" src="${article.cover}">
							<c:if test="${flag=='update'}">
                                <button type="button" class="layui-btn layui-btn-sm layui-btn-normal" id="formCoverUpload">选择图片</button>
                            </c:if>
                            </div>
                        </div>

                        <div class="layui-form-item">
                            <label class="layui-form-label">分类</label>
                            <div class="layui-input-block">
                                <select name="articletypeid" lay-verify="required"  lay-verType="tips" >
                                    <option value=""></option>
                                    <c:forEach items="${articleTypes}" var="a">
                                    	<option value="${a.id}" <c:if test="${a.id==article.articletypeid}">selected</c:if>>${a.name}</option>
                                   
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="layui-form-item layui-form-text">
                            <label class="layui-form-label">摘要</label>
                            <div class="layui-input-block">
                                <textarea name="summary"  lay-verify="required|summary"  lay-verType="tips" placeholder="请输入摘要" class="layui-textarea">${article.summary}</textarea>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">内容</label> 
                            <div class="layui-input-block">
                            <c:if test="${flag=='update'}">
                                <textarea id="demo"  style="display: none;" id="content" >${article.content}</textarea>
                            </c:if>
                            <c:if test="${flag=='look'}">
                            	${article.content}
                            </c:if>
                            </div>
                        </div>

                        <div class="layui-form-item">
                            <div class="layui-input-block">
                            <c:if test="${flag=='update'}">
                                <button  name="status" value="0" class="layui-btn layui-btn-normal"   lay-submit lay-filter="blogForm">保存并发布</button>
                                <button  name="status" value="2" class="layui-btn" lay-submit lay-filter="blogForm">保存草稿箱</button>
                            </c:if>
                            <c:if test="${flag=='look'}">
                            	 <button type="button" class="layui-btn layui-btn-normal closewindow" >返回列表</button>
                            </c:if>
                            </div>
                        </div>
                    </form>





        </div>
    </div>

    <script src="/static/client/js/jquery-3.4.1.js"></script>
    <script type="text/javascript" src="/static/client/cropper/cropper.js"></script>
    <script src="/static/client/layui/layui.js"></script>
    <script src="/static/client/js/user-edit.js"></script>
</body>
</html>