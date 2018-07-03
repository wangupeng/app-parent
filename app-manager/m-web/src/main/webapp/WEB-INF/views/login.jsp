<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <!--[if lt IE 9]>
    <script type="text/javascript" src="${staticServer}/static/lib/html5shiv.js"></script>
    <script type="text/javascript" src="${staticServer}/static/lib/respond.min.js"></script>
    <![endif]-->
    <link href="${staticServer}/static/h-ui/css/H-ui.min.css" rel="stylesheet" type="text/css" />
    <link href="${staticServer}/static/h-ui.admin/css/H-ui.login.css" rel="stylesheet" type="text/css" />
    <link href="${staticServer}/static/h-ui.admin/css/style.css" rel="stylesheet" type="text/css" />
    <link href="${staticServer}/static/lib/Hui-iconfont/1.0.8/iconfont.css" rel="stylesheet" type="text/css" />
    <!--[if IE 6]>
    <script type="text/javascript" src="${staticServer}/static/lib/DD_belatedPNG_0.0.8a-min.js" ></script>
    <script>DD_belatedPNG.fix('*');</script>
    <![endif]-->

    <script type="text/javascript" src="${staticServer}/static/lib/jquery/1.9.1/jquery.min.js"></script>
    <script type="text/javascript" src="${staticServer}/static/h-ui/js/H-ui.js"></script>
    <script type="text/javascript" src="${staticServer}/static/lib/layer/2.4/layer.js"></script>

    <title>后台登录 - H-ui.admin.page v3.0</title>
    <meta name="keywords" content="${webTitle}">
    <meta name="description" content="${staticServer}">
</head>
<body>
<input type="hidden" id="TenantId" name="TenantId" value="" />
<div class="header"></div>
<div class="loginWraper">
    <div id="loginform" class="loginBox">
        <form class="form form-horizontal" action="login" method="post">
            <div class="row cl">
                <label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe60d;</i></label>
                <div class="formControls col-xs-8">
                    <input id="userName" name="userName" type="text" value="${user.userName}" placeholder="账户" class="input-text size-L">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe60e;</i></label>
                <div class="formControls col-xs-8">
                    <input id="passWord" name="passWord" type="password" value="${user.passWord}" placeholder="密码" class="input-text size-L">
                </div>
            </div>
            <c:if test="${jcaptchaEbabled&&yzm}">
                <div class="row cl">
                    <div class="formControls col-xs-8 col-xs-offset-3">
                        <input class="input-text size-L" name="jcaptchaCode" type="text" placeholder="验证码" onblur="if(this.value==''){this.value='验证码:'}" onclick="if(this.value=='验证码:'){this.value='';}" value="验证码:" style="width:150px;">
                        <img id="yzm" src="${staticServer}/jcaptcha.jpg">
                        <a class="jcaptcha-btn" id="kanbuq" onclick="getPic()" href="javascript:;" style="text-decoration: none">看不清，换一张</a>
                    </div>
                </div>
            </c:if>

            <div class="row cl">
                <div class="formControls col-xs-8 col-xs-offset-3">
                    <label for="rememberMe">
                        <input type="checkbox" name="rememberMe" id="rememberMe" value="">记住密码
                    </label>
                </div>
            </div>
            <%--<div class="row cl">
                <label class="form-label col-xs-3"></label>
                <div class="col-xs-8">
                    <p class="f-14">${msg}${error}</p>
                </div>
            </div>--%>
            <div class="row cl">
                <div class="formControls col-xs-8 col-xs-offset-3">
                    <input name="" type="submit" class="btn btn-success radius size-L" value="&nbsp;登&nbsp;&nbsp;&nbsp;&nbsp;录&nbsp;">
                    <input name="" type="reset" class="btn btn-default radius size-L" value="&nbsp;重&nbsp;&nbsp;&nbsp;&nbsp;置&nbsp;">
                </div>
            </div>
        </form>
    </div>
</div>
<div class="footer">Copyright 公司名称 by H-ui.admin.page.v3.0</div>
<script type="text/javascript">
    $(function () {
        <c:if test="${not empty msg}">
        layer.ready(function(){
            layer.msg("${msg}");
        });
        </c:if>

        //记住密码功能
        var str = getCookie("sid");
        str = str.substring(1,str.length-1);
        var username = str.split(",")[0];
        var password = str.split(",")[1];
        //自动填充用户名和密码
        $("#username").val(username);
        $("#pwd").val(password);
        if(str!=null && str!=""){
            $("input[type='checkbox']").attr("checked", true);
        }
    })

    function getPic(){
        $("#yzm").attr("src","${staticServer}/jcaptcha.jpg?flag="+Math.random());
    };

    //记住密码复选框的点击事件
    function remember(){
        var remFlag = $("input[type='checkbox']").is(':checked');
        if(remFlag==true){ //如果选中设置remFlag为true
            $("#remFlag").val(true);
        }else{ //如果没选中设置remFlag为false
            $("#remFlag").val(false);
        }
    }

    function getCookie(cname) {
        var name = cname + "=";
        var ca = document.cookie.split(';');
        console.log(ca)
        for(var i=0; i<ca.length; i++) {
            var c = ca[i];
            while (c.charAt(0)==' ') c = c.substring(1);
            if (c.indexOf(name) != -1)
                console.log(c.substring(name.length, c.length));
                return c.substring(name.length, c.length);
        }
        return "";
    }
</script>
</body>
</html>

