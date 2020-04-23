<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>注册</title>
    <link rel="stylesheet" href="/static/client/layui/css/layui.css">
    <link rel="stylesheet" href="/static/client/css/account.css">
</head>
<body>
<header>
    <div class="header-line"></div>
</header>
<div class="content">
    <img class="content-logo" src="/static/client/img/001.png" alt="logo">
    <h1 class="content-title">会员注册</h1>
    <div class="content-form">
       <form class="layui-form" action="/user/register" method="post">
  			<input type="hidden" name="token" value='${token}' id="token">
            <div id="change_margin_1">
                <input class="user" type="text"  lay-verify="required|email"  lay-verType="tips" name="email" placeholder="请输入邮箱" >
            </div>
            <!-- input的value为空时弹出提醒 -->
            <p id="remind_1"></p>

            <div id="change_margin_6">
                <input class="user" type="text"  lay-verify="required|username"  lay-verType="tips" name="name" placeholder="昵称" >
            </div>

            <div id="change_margin_2">
                <input class="password" type="password" lay-verify="required|pass" lay-verType="tips" name="password" placeholder="请输入密码" >
            </div>
            <!-- input的value为空时弹出提醒 -->
            <p id="remind_2"></p>

            <div id="change_margin_5">
                <input class="password" type="password" lay-verify="required|repass" lay-verType="tips" name="repassword" placeholder="重复密码" >
            </div>

            <div id="change_margin_4" >
                <input class="user captcha" type="text" lay-verify="required|captcha" lay-verType="tips" name="captcha" placeholder="请输入验证码" >
                <a href="javascriot:void(0)"  id="captcha">
                    <img  class="captcha_img" src="/kaptcha" >
                    看不清
                </a>

            </div>
            <div id="change_margin_3">
                <input class="content-form-signup"  lay-submit lay-filter="registerForm" type="submit" value="注册">
            </div>
        </form>
    </div>
    <div class="content-login-description">已有账户？<a class="content-login-link" href="login.html">马上去登录</a></div>
</div>

<script type="text/javascript" src="/static/client/js/jquery-3.4.1.js"></script>
  <script type="text/javascript" src="/static/client/js/jquery-base64.js"></script>
<script src="/static/client/layui/layui.js"></script>
<script src="/static/client/js/account.js"></script>
</body>
</html>