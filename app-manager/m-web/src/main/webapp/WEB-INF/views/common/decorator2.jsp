<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <title><sitemesh:write property='title'/></title>
    <%@include file="styles.jsp" %>
    <sitemesh:write property='head'/>
    <script type="text/javascript" src="${staticServer}/static/lib/jquery/1.9.1/jquery.min.js"></script>
</head>
<body>
    <sitemesh:write property='body'/>
</body>

<%@include file="scripts.jsp" %>
</html>
