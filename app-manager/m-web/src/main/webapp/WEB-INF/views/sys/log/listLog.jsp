<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../../common/taglib.jsp"%>
<!DOCTYPE HTML>
<head>
		<title>日志列表</title>
	</head>
<body>
	<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> <a href="${staticServer}/index" class="maincolor">首页</a>
		<span class="c-gray en">&gt;</span> 系统管理
		<span class="c-gray en">&gt;</span> 日志管理
		<span class="c-gray en">&gt;</span> 日志列表
		<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.reload();" title="刷新" >
			<i class="Hui-iconfont">&#xe68f;</i>
		</a>
	</nav>
	<div class="Hui-article">
		<article class="cl pd-20">

            <form action="sysLog" method="post" class="form form-horizontal">
                <div class="text-l">
                    操作用户：<input type="text" class="input-text" style="width:100px" id="userName" name="userName" value="${sysLog.userName}">
                    日期范围：
                    <input type="text" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')||\'%y-%M-%d\'}'})" id="startDate" name="startDate" value="${sysLog.startDate}" class="input-text Wdate" style="width:120px;">
                    -
                    <input type="text" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}',maxDate:'%y-%M-%d'})" id="endDate" name="endDate" value="${sysLog.endDate}" class="input-text Wdate" style="width:120px;">
                    <button type="submit" class="btn btn-success"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
                </div>
            </form>

			<table class="table table-border table-bordered table-hover table-bg table-sort dataTables_wrapper  mt-10" id="dataTables-smsSendOrder">
				<thead>
					<tr class="text-c">
						<th width="20"><input type="checkbox" name="" value=""></th>
						<th width="20">序号</th>
						<th width="60">操作用户</th>
						<th width="100">操作IP</th>
						<th width="100">操作日期</th>
						<th width="120">URL</th>
						<th width="120">方法名</th>
						<th width="50">处理时间(毫秒)</th>
					</tr>
				</thead>
				<tbody>
                    <c:if test="${pageInfo.list.size() == 0}">
                        <td colspan="10">无数据</td>
                    </c:if>
                    <c:forEach items="${pageInfo.list }" var="sysLog" varStatus="status">
                        <tr class="text-c">
                            <td><input type="checkbox" value="2" name=""></td>
                            <td>${status.index+1}</td>
                            <td>${sysLog.userName}</td>
                            <td>${sysLog.operaIp }</td>
                            <td>${sysLog.operaDate}</td>
                            <td>${sysLog.operaUrl}</td>
                            <td>${sysLog.methodName}</td>
                            <td>${sysLog.dealTime}</td>
                        </tr>
                    </c:forEach>
				</tbody>
			</table>
            <jsp:include page="../../common/page.jsp">
                <jsp:param value="${pageInfo}" name="pageInfo"/>
            </jsp:include>
		</article>
	</div>
</body>
</html>