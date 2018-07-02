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

    <meta name="keywords" content="${webTitle}">
    <meta name="description" content="${staticServer}">
    <title><sitemesh:write property='title'/> - ${webTitle}</title>

    <%@include file="styles.jsp" %>
    <sitemesh:write property='head'/>
</head>
<body>
    <%@ include file="header.jsp" %>
    <%@ include file="menu.jsp" %>
    <section class="Hui-article-box">
        <sitemesh:write property='body'/>
    </section>
</body>
<%@include file="scripts.jsp" %>
</html>
