<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<head>
	<title>错误</title>
</head>
<body>

<div layout:fragment="content">
	<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> <a href="index" class="maincolor">首页</a>
		<span class="c-gray en">&gt;</span> 系统错误
	</nav>
	<div class="Hui-article">
		<article class="cl pd-20">
			<section class="page-404 minWP text-c">
				<p class="error-title"><i class="Hui-iconfont va-m" style="font-size:80px">&#xe688;</i><span class="va-m"> 500</span></p>
				<p class="error-description">服务器内部错误，不能执行该请求~</p>
				<p class="error-info">
					您可以：<a href="javascript:;" onclick="history.go(-1)" class="c-primary">&lt; 返回上一页</a>
					<span class="ml-20">|</span><a href="/templates/demo/index" class="c-primary ml-20">去首页 &gt;</a></p>
			</section>
		</article>
	</div>
</div>
</body>
</html>