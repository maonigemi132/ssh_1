<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>   
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>博客中心</title>
    <link rel="stylesheet" href="/static/client/layui/css/layui.css">
    <link rel="stylesheet" href="/static/client/cropper/cropper.css">
    <link rel="stylesheet" href="/static/client/css/user-x.css">
</head>

<body >
<!-- 引用头部 -->
<jsp:include page="component/header.jsp"></jsp:include>


<div class="layui-row layui-col-space30 "  >
    <!-- 引入左侧导航 -->
    <jsp:include page="component/left.jsp"/>
    <!--  -->
    <div class="layui-col-md8 layui-col-xs8">

        <div class="layui-tab"  lay-filter="tableInfo">
            <ul class="layui-tab-title">
                <li class="layui-this"  lay-id="sendBlog"  > <i class="layui-icon layui-icon-about"></i> 发布博客</li>
                <li lay-id="blogList"> <i class="layui-icon layui-icon-password"></i> 博客列表</li>
                <li lay-id="blogDraft" > <i class="layui-icon layui-icon-picture"></i> 草稿箱</li>
            </ul>
            <div class="layui-tab-content">
                <div class="layui-tab-item layui-show">
                    <!--编辑页面-->
                    <!--<iframe  width="100%" height="1000px"  src="user-editblog.html"  frameborder="0" ></iframe>-->




                    <form class="layui-form" action=""  id="blogForm">
                    <input type="hidden" name="token" value='${token}'>
                    <input type="hidden" name="type" value='0'>
                        <div class="layui-form-item">
                            <label class="layui-form-label">标题</label>
                            <div class="layui-input-block">
                                <input type="text" name="title" required  lay-verify="required|title" lay-verType="tips" placeholder="请输入标题（50字内）" autocomplete="off" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">封面</label>
                            <div class="layui-input-block">
                                <img width="228" id="showImg"  height="128" src="/static/client/img/cover_default.png">

                                <button type="button" class="layui-btn layui-btn-sm layui-btn-normal" id="formCoverUpload">选择图片</button>
                            </div>
                        </div>

                        <div class="layui-form-item">
                            <label class="layui-form-label">分类</label>
                            <div class="layui-input-block">
                                <select name="articletypeid" lay-verify="required"  lay-verType="tips" >
                                    <option value=""></option>
                                    <c:forEach items="${articleTypes}" var="a">
                                    	<option value="${a.id}">${a.name}</option>
                                   
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="layui-form-item layui-form-text">
                            <label class="layui-form-label">摘要</label>
                            <div class="layui-input-block">
                                <textarea name="summary"  lay-verify="required|summary"  lay-verType="tips" placeholder="请输入摘要" class="layui-textarea"></textarea>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">内容</label> 
                            <div class="layui-input-block">
                                <textarea id="demo" name="content" style="display: none;" id="content" ></textarea>
                            </div>
                        </div>

                        <div class="layui-form-item">
                            <div class="layui-input-block">
                                <button  name="status" value="0" class="layui-btn layui-btn-normal"   lay-submit lay-filter="blogForm">保存并发布</button>
                                <button  name="status" value="2" class="layui-btn" lay-submit lay-filter="blogForm">保存草稿箱</button>
                            </div>
                        </div>
                    </form>






                </div>
                <div class="layui-tab-item">


                    <div class="layui-row">

                        <div class="layui-col-md12">
                            <form class="layui-form "  action="asd">


                                <div class="layui-form-item">

                                    <div class="layui-inline">
                                        <label class="layui-form-label">时间范围</label>
                                        <div class="layui-input-inline" style="width: 200px;">
                                            <input type="text" name="timeRange"  readonly placeholder="选择时间段" autocomplete="off" class="layui-input rangeTime">
                                        </div>
                                    </div>

                                    <div class="layui-inline">
                                        <div class="layui-input-inline" style="width: 290px;">
                                            <input type="text" name="title" autocomplete="off"  style="width: 200px;float: left"    placeholder="标题关键词" class="layui-input">
                                            <button type="button" class="layui-btn  layui-btn-normal" style="float:left" lay-submit lay-filter="searchForm">
                                                <i class="layui-icon layui-icon-search"></i>
                                                查看
                                            </button>
                                        </div>
                                    </div>

                                </div>


                                <table class="layui-table  infoTable" lay-filter="blogTable" id="pageBlogTable">
                                <colgroup>
                                    <col width="150">
                                    <col width="200">
                                    <col>
                                </colgroup>
                                <thead>
                                <tr>
                                    <th>封面(点击预览)</th>
                                    <th>标题</th>
                                    <th>摘要</th>
                                    <th width="80">状态</th>
                                    <th style="text-align: center;">发布时间</th>
                                    <th style="text-align: center;">操作</th>
                                </tr>
                                </thead>
                                <tbody>
                              
                               <!--  <tr>
                                    <td>
                                        <img src="/static/client/img/001.png">
                                    </td>
                                    <td>测试数据</td>
                                    <td>人生就像是一场修行</td>
                                    <td>
                                        <input type="checkbox" name="zzz" lay-skin="switch" lay-text="已发布|未发布">
                                    </td>
                                    <td>2020-02-19 19:56:27</td>
                                    <td>
                                        <button type="button"  blog-id="100022"  class="layui-btn layui-btn-xs layui-btn-normal  cover">
                                            <i class="layui-icon layui-icon-picture-fine"></i>
                                            更换封面
                                        </button>
                                        <button type="button" class="layui-btn layui-btn-xs layui-btn-primary"  blog-id="100022" lay-event="lookBlog">
                                            <i class="layui-icon layui-icon-search"></i>
                                            查&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;看
                                        </button> <br>
                                        <button type="button" class="layui-btn layui-btn-xs layui-btn-normal"  blog-id="100022"   lay-event="editBlog" >
                                            <i class="layui-icon layui-icon-edit"></i>
                                            编&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;辑
                                        </button> <br>
                                        <button type="button" class="layui-btn layui-btn-xs layui-btn-danger"  blog-id="100022"  lay-event="deleteBlog" >
                                            <i class="layui-icon layui-icon-delete"></i>
                                            删&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;除
                                        </button>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <img src="/static/client/img/001.png">
                                    </td>
                                    <td>测试数据</td>
                                    <td>人生就像是一场修行</td>
                                    <td>
                                        <input type="checkbox" name="zzz" lay-skin="switch" lay-text="已发布|未发布">
                                    </td>
                                    <td>2020-02-19 19:56:27</td>
                                    <td>
                                        <button type="button"  blog-id="100022"  class="layui-btn layui-btn-xs layui-btn-normal  cover">
                                            <i class="layui-icon layui-icon-picture-fine"></i>
                                            更换封面
                                        </button>
                                        <button type="button" class="layui-btn layui-btn-xs layui-btn-primary"  blog-id="100022" lay-event="lookBlog">
                                            <i class="layui-icon layui-icon-search"></i>
                                            查&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;看
                                        </button> <br>
                                        <button type="button" class="layui-btn layui-btn-xs layui-btn-normal"  blog-id="100022"   lay-event="editBlog" >
                                            <i class="layui-icon layui-icon-edit"></i>
                                            编&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;辑
                                        </button> <br>
                                        <button type="button" class="layui-btn layui-btn-xs layui-btn-danger"  blog-id="100022"  lay-event="deleteBlog" >
                                            <i class="layui-icon layui-icon-delete"></i>
                                            删&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;除
                                        </button>
                                    </td>
                                </tr> -->
                                </tbody>
                            </table>
                                <p style="text-align: center">
                                    <div id="blogList-page"></div>
                                </p>

                            </form>

                        </div>

                    </div>


                </div>
                <div class="layui-tab-item" >


                        <div class="layui-row">
                            <div class="layui-col-md12">

                                <form class="layui-form "  action="asd">


                                    <div class="layui-form-item">

                                        <div class="layui-inline">
                                            <label class="layui-form-label">时间范围</label>
                                            <div class="layui-input-inline" style="width: 200px;">
                                                <input type="text" name=""  readonly placeholder="选择时间段" autocomplete="off" class="layui-input rangeTime">
                                            </div>
                                        </div>

                                        <div class="layui-inline">
                                            <div class="layui-input-inline" style="width: 290px;">
                                                <input type="password" name="" autocomplete="off"  style="width: 200px;float: left"    placeholder="标题关键词" class="layui-input">
                                                <button type="button" class="layui-btn  layui-btn-normal" style="float: left">
                                                    <i class="layui-icon layui-icon-search"></i>
                                                    查看
                                                </button>
                                            </div>
                                        </div>

                                    </div>


                                    <table class="layui-table  infoTable" lay-filter="blogTable" id="pageBlogTable1">
                                        <colgroup>
                                            <col width="150">
                                            <col width="200">
                                            <col>
                                        </colgroup>
                                        <thead>
                                        <tr>
                                            <th>封面(点击预览)</th>
                                            <th>标题</th>
                                            <th>摘要</th>
                                            <th width="70">状态</th>
                                            <th>发布时间</th>
                                            <th>操作</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <!-- <tr>
                                            <td>
                                                <img src="/static/client/img/001.png">
                                            </td>
                                            <td>测试数据</td>
                                            <td>人生就像是一场修行</td>
                                            <td>
                                                草稿箱
                                            </td>
                                            <td>2020-02-19 19:56:27</td>
                                            <td>
                                                <button type="button" class="layui-btn layui-btn-xs layui-btn-normal"  blog-id="100022"   lay-event="editBlog" >
                                                    <i class="layui-icon layui-icon-edit"></i>
                                                    恢&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;复
                                                </button>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <img src="/static/client/img/001.png">
                                            </td>
                                            <td>测试数据</td>
                                            <td>人生就像是一场修行</td>
                                            <td>
                                                草稿箱
                                            </td>
                                            <td>2020-02-19 19:56:27</td>
                                            <td>
                                                <button type="button" class="layui-btn layui-btn-xs layui-btn-normal"  blog-id="100022"   lay-event="editBlog" >
                                                    <i class="layui-icon layui-icon-edit"></i>
                                                    恢&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;复
                                                </button>
                                            </td>
                                        </tr> -->
                                        </tbody>
                                    </table>
                                    <p style="text-align: center">
                                        <div id="blogList-page1"></div>
                                    </p>

                                </form>




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
<script src="/static/client/layui/layui.js"></script>
<script type="text/javascript" src="/static/client/cropper/cropper.js"></script>
<script src="/static/client/js/user-blog.js"></script>
</body>
</html>