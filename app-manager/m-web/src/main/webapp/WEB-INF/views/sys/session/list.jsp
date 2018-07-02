<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../../common/taglib.jsp"%>
<head>
		<title>任务列表</title>
	</head>
<body>
	<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> <a href="${staticServer}/index" class="maincolor">首页</a>
        <span class="c-gray en">&gt;</span> 系统管理
        <span class="c-gray en">&gt;</span> 会话管理
		<span class="c-gray en">&gt;</span> 会话列表
		<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.reload();" title="刷新" >
			<i class="Hui-iconfont">&#xe68f;</i>
		</a>
	</nav>

	<div class="Hui-article">
		<article class="cl pd-20">
			<%--<form action="sysTask" method="post" class="form form-horizontal">
				<div class="text-l">
					任务名：<input type="text" class="input-text" style="width:100px" id="jobName" name="jobName" value="${sysTask.jobName}">
					<button type="submit" class="btn btn-success"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
				</div>
			</form>--%>
			<div class="cl pd-5 bg-1 bk-gray mt-10">
				<span class="l">
                    当前在线人数：${sesessionCount}人
				</span>
			</div>

			<table class="table table-border table-bordered table-hover table-bg table-sort dataTables_wrapper" id="dataTables-smsSendOrder">
				<thead>
					<tr class="text-c">
						<th width="25"><input type="checkbox" name="" value=""></th>
                        <th width="30">序号</th>
                        <th width="150">会话ID</th>
                        <th width="70">用户名</th>
                        <th width="100">主机地址</th>
                        <th width="100">最后访问时间</th>
                        <th width="50">已强制退出</th>
                        <th width="50">操作</th>
					</tr>
				</thead>
				<tbody>
                <c:if test="${sesessionCount == 0}">
                    <td colspan="20">无数据</td>
                </c:if>
                <c:forEach items="${sessions}" var="session" varStatus="status">
                    <tr class="text-c">
                        <td><input type="checkbox" value="2" name=""></td>
                        <td>${status.index+1}</td>
                        <td>${session.id}</td>
                        <td>${wangfn:principal(session)}</td>
                        <td>${session.host}</td>
                        <td><fmt:formatDate value="${session.lastAccessTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td>${wangfn:isForceLogout(session) ? '是' : '否'}</td>
                        <td>
                            <c:if test="${not wangfn:isForceLogout(session)}">
                                <a title="强制退出" onclick="javascript:forceLogout('${session.id}')" href="#" class="ml-5" data-toggle="tooltip" data-placement="top" style="text-decoration:none"><i class="Hui-iconfont f-16">&#xe631;</i>强制退出</a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
				</tbody>
			</table>
            <%--<jsp:include page="../../common/page.jsp">
                <jsp:param value="${pageInfo}" name="pageInfo"/>
            </jsp:include>--%>
		</article>
	</div>
<script type="text/javascript">
    /*强制退出*/
    function forceLogout(id){
        layer.confirm('确认要强制退出吗？',{icon: 3, title:'提示'},function(index){
            $.post("sessions/"+id+"/forceLogout", function(result) {
                location.reload();
            });
        });
    }
</script>
</body>
</html>