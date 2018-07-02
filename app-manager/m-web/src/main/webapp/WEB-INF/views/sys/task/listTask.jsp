<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../../common/taglib.jsp"%>
<head>
		<title>任务列表</title>
	</head>
<body>
	<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> <a href="${staticServer}/index" class="maincolor">首页</a>
        <span class="c-gray en">&gt;</span> 系统管理
        <span class="c-gray en">&gt;</span> 任务管理
		<span class="c-gray en">&gt;</span> 任务列表
		<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.reload();" title="刷新" >
			<i class="Hui-iconfont">&#xe68f;</i>
		</a>
	</nav>

	<div class="Hui-article">
		<article class="cl pd-20">
			<form action="sysTask" method="post" class="form form-horizontal">
				<div class="text-l">
					任务名：<input type="text" class="input-text" style="width:100px" id="jobName" name="jobName" value="${sysTask.jobName}">
					<button type="submit" class="btn btn-success"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
				</div>
			</form>

			<div class="cl pd-5 bg-1 bk-gray mt-10">
				<span class="l">
					<a href="javascript:;" onclick="toAddTask('新增任务','sysTask/toAdd','10001','800','350')" class="btn btn-primary radius"  shiro:hasPermission="/sys/sysTask/add"><i class="Hui-iconfont">&#xe600;</i> 新增任务</a>
				</span>
			</div>

			<table class="table table-border table-bordered table-hover table-bg table-sort dataTables_wrapper" id="dataTables-smsSendOrder">
				<thead>
					<tr class="text-c">
						<th width="25"><input type="checkbox" name="" value=""></th>
						<th width="30">序号</th>
						<th width="50">任务名称</th>
						<th width="50">任务分组</th>
                        <th width="100">任务实现类</th>
						<th width="100">任务描述</th>
						<th width="80">任务表达式</th>
						<th width="50">任务状态</th>
						<th width="100">创建时间</th>
						<th width="50">创建用户</th>
						<th width="100">修改时间</th>
						<th width="50">修改用户</th>
						<th width="60">操作</th>
					</tr>
				</thead>
				<tbody>
                <c:if test="${pageInfo.list.size() == 0}">
                    <td colspan="20">无数据</td>
                </c:if>
                <c:forEach items="${pageInfo.list }" var="task" varStatus="status">
					<tr class="text-c">
						<td><input type="checkbox" value="2" name=""></td>
						<td>${status.index+1}</td>
						<td>${task.jobName}</td>
						<td>${task.jobGroup}</td>
						<td>${task.jobClass}</td>
						<td>${task.jobDescribe}</td>
						<td>${task.cronExpression}</td>
						<td class="td-status">
                            <c:choose>
                                <c:when test="${task.status == '1'}">
                                    <span class="label label-success radius">正在运行</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="label label-default radius">已暂停</span>
                                </c:otherwise>
                            </c:choose>
						</td>
						<td>${task.createDate}</td>
						<td>${task.createUser}</td>
						<td>${task.updateDate}</td>
						<td>${task.updateUser}</td>
						<td class="td-manage">
                            <shiro:hasPermission name="/sys/sysTask/update">
                                <c:choose>
                                    <c:when test="${task.status == '0'}">
                                        <a title="启动" onclick="startTask('${task.jobId}')" href="javascript:;" data-toggle="tooltip" data-placement="top" style="text-decoration:none">
                                            <i class="Hui-iconfont f-16">&#xe6e1;</i>
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <a title="暂停" onclick="pauseTask('${task.jobId}')" href="javascript:;" data-toggle="tooltip" data-placement="top" style="text-decoration:none">
                                            <i class="Hui-iconfont f-16">&#xe631;</i>
                                        </a>
                                    </c:otherwise>
                                </c:choose>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="/sys/sysTask/update">
                                <a title="修改" onclick="toUpdate('修改用户','sysTask/toUpdate','10001','800','350','${task.jobId}')" class="ml-5" data-toggle="tooltip" data-placement="top" style="text-decoration:none"><i class="Hui-iconfont f-16">&#xe6df;</i></a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="/sys/sysTask/delete">
                                <a title="删除" onclick="del('${task.jobId}')" href="javascript:;" class="ml-5" data-toggle="tooltip" data-placement="top" style="text-decoration:none"><i class="Hui-iconfont f-16">&#xe6e2;</i></a>
                            </shiro:hasPermission>
						</td>
					</tr>
                </c:forEach>
				</tbody>
			</table>
            <jsp:include page="../../common/page.jsp">
                <jsp:param value="${pageInfo}" name="pageInfo"/>
            </jsp:include>
		</article>
	</div>
<script type="text/javascript">
    /*启动任务*/
    function startTask(jobId){
        layer.confirm('确认要启动吗？',{icon: 3, title:'提示'},function(index){
            $.post("sysTask/start",{jobId: jobId}, function(result) {
                if(result==1){
                    layer.msg('任务已启动!', { icon: 1, time: 1000 },function () {
                        location.reload();
                    });
                }else{
                    layer.msg('任务启动失败!', { icon: 2, time: 1000 },function () {
                        location.reload();
                    });
                }
            });
        });
    }

    /*暂停任务*/
    function pauseTask(jobId){
        layer.confirm('确认要暂停吗？',function(index){
            $.post("sysTask/pause",{jobId: jobId}, function(result) {
                if(result==1){
                    layer.msg('任务已暂停!', { icon: 1, time: 1000 },function () {
                        location.reload();
                    });
                }else{
                    layer.msg('任务暂停失败!', { icon: 2, time: 1000 },function () {
                        location.reload();
                    });
                }
            });
        });
    }

    /*添加任务*/
    function toAddTask(title,url,id,w,h){
		layer_show(title,url,w,h);
    }

    /*修改任务*/
    function toUpdate(title,url,id,w,h,jobId){
        data = {jobId:jobId};
        layer_show(title,url,w,h);
    }

	/*删除*/
	function del(jobId){
		layer.confirm("确认要删除吗？", {icon: 3, title:'提示'}, function(index){
			$.post("sysTask/delete",{jobId: jobId}, function(result) {
				if(result==1){
					layer.msg('删除成功!', { icon: 1, time: 1000 },function () {
						location.reload();
					});
				}else{
					layer.msg('删除失败!', { icon: 2, time: 1000 },function () {
						location.reload();
					});
				}
			});
		});
	}
</script>
</body>
</html>