<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>博客中心</title>
    <link rel="stylesheet" href="/static/client/layui/css/layui.css">
    <style>

        .flow-ct{
            background-color: rgb(238,238,238);
            border: 5px solid white;
            color: grey; border-radius: 10px;
            padding: 5px;
        }
        .flow-img{
            width: 100%;
            border-radius: 5px;  vertical-align: middle;
        }
        .flow-ct h3{
            line-height: 40px;
        }
        .flow-ct a{ color: rgb(65,148,36)}
        .flow-info {
            display: inline-block;
            width: 100%;
            padding-left: 6px;
        }
        .flow-info>p {
            height: 54px;
            display: -webkit-box;
            -webkit-box-orient: vertical;
            -webkit-line-clamp: 3;
            overflow: hidden;
            font-size: 12px; line-height: 18px;
        }
        .flow-info>span{
            display: inline-block; width: 45%;
            overflow: hidden;
            text-overflow:ellipsis;
            white-space: nowrap;
        }

        .layui-flow-more{
            clear: both;

        }

    </style>
</head>

<body >
<!-- 引用头部 -->
<jsp:include page="component/header.jsp"></jsp:include>


<div  class="layui-row">
      <div class="layui-col-md12" style="padding: 20px">
          <div class="layui-carousel" id="test1">
              <div carousel-item>
              	<c:forEach items="${banners}" var="b">
                  <div>
                  	<a href="${b.tourl}" target="_bank"><img alt="" src="${b.url}"></a>	
                  </div>
                </c:forEach>
              </div>
          </div>
      </div>
</div>

<div  class="layui-row">
    <div class="layui-col-md12" >
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
            <legend>博客推荐</legend>
        </fieldset>
    </div>
</div>


	<input type="hidden" value='${token}' id="token">

<div class="layui-row" style="padding: 20px">
    <div class="layui-col-md12">
        <div class="layui-tab" lay-filter="blogType">
            <ul class="layui-tab-title">
            
            <c:forEach items="${articleTypes}" var="t" varStatus="s">           	
                <li lay-id="blogType_${s.index}" id="${t.id}" <c:if test="${s.index==0}">class="layui-this"</c:if>>${t.name}</li>
             </c:forEach>  
            </ul>
            <div class="layui-tab-content">
             <c:forEach items="${articleTypes}" var="t" varStatus="s">  
                <div class="layui-tab-item <c:if test="${s.index==0}">layui-show</c:if>">
                   <div class="layui-row   "  id="LAY_demo${s.index}"></div>
                </div>
             </c:forEach> 
            </div>
        </div>
    </div>
</div>


<div class="row">
    <div class="layui-col-md12"  style="background-color: #e8e8e8;line-height: 40px; text-align: center">
           版权所有&copy;2020-2099 Mr_JINGR .  备案号:<a href="">粤ICP1223123</a>
    </div>
</div>

<script src="/static/client/layui/layui.js"></script>
<script src="/static/client/js/home.js"></script>
</body>
</html>