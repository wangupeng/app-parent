<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../../common/taglib.jsp"%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>用户列表</title>
	</head>
<body>
	<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> <a href="${staticServer}/index" class="maincolor">首页</a>
		<span class="c-gray en">&gt;</span> 系统管理
		<span class="c-gray en">&gt;</span> 用户管理
		<span class="c-gray en">&gt;</span> 用户列表
		<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.reload();" title="刷新" >
			<i class="Hui-iconfont">&#xe68f;</i>
		</a>
	</nav>

	<div class="Hui-article">
		<article class="cl pd-20">
			<form action="sysUser" method="post" class="form form-horizontal">
				<div class="text-l">
					用户名：<input type="text" class="input-text" style="width:100px" id="userName" name="userName" value="${sysUser.userName}">
					姓名：<input type="text" class="input-text" style="width:100px" id="realName" name="realName" value="${sysUser.realName}">
					<button type="submit" class="btn btn-success"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
				</div>
			</form>

			<div class="cl pd-5 bg-1 bk-gray mt-10">
				<span class="l">
                    <shiro:hasPermission name="/sys/sysUser/add">
					    <a href="javascript:;" onclick="toAddUser('新增用户','sysUser/toAdd','10001','800','350')" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 新增用户</a>
                    </shiro:hasPermission>
					<!--<a href="javascript:;" onclick="datadel()" class="btn btn-danger radius" shiro:hasPermission="/sysUser/add"><i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a>-->
				</span>
			</div>
			<table class="table table-border table-bordered table-hover table-bg table-sort dataTables_wrapper" id="dataTables-smsSendOrder">
				<thead>
					<tr class="text-c">
						<th width="25"><input type="checkbox" name="" value=""></th>
						<th width="40">序号</th>
						<th width="80">用户名</th>
						<th width="100">姓名</th>
						<th width="80">角色</th>
						<th width="100">手机号</th>
						<th width="80">创建用户</th>
						<th width="100">创建时间</th>
						<th width="70">状态</th>
						<th width="100">操作</th>
					</tr>
				</thead>
				<tbody>
                    <c:if test="${pageInfo.list.size() == 0}">
                        <td colspan="10">无数据</td>
                    </c:if>
                    <c:forEach items="${pageInfo.list }" var="user" varStatus="status">
                        <tr class="text-c">
                            <td><input type="checkbox" value="2" name=""></td>
                            <td>${status.index+1}</td>
                            <td>${user.userName}</td>
                            <td>${user.realName}</td>
                            <td>${user.roleName}</td>
                            <td>${user.mobile}</td>
                            <td>${user.createUser}</td>
                            <td>${user.createDate}</td>
                            <td class="td-status">
                                <c:choose>
                                    <c:when test="${user.status == '1'}">
                                        <span class="label label-success radius">正常</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="label label-default radius">已锁定</span>
                                    </c:otherwise>
                                </c:choose>

                            </td>
                            <td class="td-manage">
                                <shiro:hasPermission name="/sys/sysUser/update">
                                    <a title="修改" onclick="toUpdate('修改用户','sysUser/toUpdate','10001','800','350','${user.userName}')" class="ml-5" data-toggle="tooltip" data-placement="top" style="text-decoration:none"><i class="Hui-iconfont f-16">&#xe6df;</i></a>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="/sys/sysUser/update">
                                    <c:choose>
                                        <c:when test="${user.status == '1'}">
                                            <a title="锁定" onclick="lockUser('${user.userName}')" href="javascript:;" data-toggle="tooltip" data-placement="top" style="text-decoration:none">
                                                <i class="Hui-iconfont f-16">&#xe60e;</i>
                                            </a>
                                        </c:when>
                                        <c:otherwise>
                                            <a title="解锁" onclick="unlockUser('${user.userName}')" href="javascript:;" data-toggle="tooltip" data-placement="top" style="text-decoration:none">
                                                <i class="Hui-iconfont f-16">&#xe63f;</i>
                                            </a>
                                        </c:otherwise>
                                    </c:choose>


                                </shiro:hasPermission>
                                <shiro:hasPermission name="/sys/sysUser/reset">
                                    <a title="重置密码" onclick="resetPassWord('${user.userName}')" class="ml-5" href="javascript:;" data-toggle="tooltip" data-placement="top" style="text-decoration:none" >
                                        <i class="Hui-iconfont f-16">&#xe6f7;</i>
                                    </a>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="/sys/sysUser/delete">
                                    <a title="删除" onclick="del('${user.userName}')" href="javascript:;" class="ml-5" data-toggle="tooltip" data-placement="top" style="text-decoration:none"><i class="Hui-iconfont f-16">&#xe6e2;</i></a>
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
    /*添加用户*/
    function toAddUser(title,url,id,w,h){
		layer_show(title,url,w,h);
    }

    /*修改用户*/
    function toUpdate(title,url,id,w,h,userName){
        data = {userName:userName};
        layer_show(title,url,w,h);
    }

	/*管理员-删除*/
	function del(userName){
		layer.confirm("确认要删除吗？", {icon: 3, title:'提示'}, function(index){
			$.post("${staticServer}/sys/sysUser/delete",{userName: userName}, function(result) {
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

	/*锁定*/
	function lockUser(userName){
		layer.confirm('确认要锁定吗？',{icon: 3, title:'提示'},function(index){
			$.post("sysUser/lockUser",{userName: userName}, function(result) {
				if(result==1){
					layer.msg('已锁定!', { icon: 1, time: 1000 },function () {
						location.reload();
					});
				}else{
					layer.msg('锁定失败!', { icon: 2, time: 1000 },function () {
						location.reload();
					});
				}
			});
		});
	}

	/*解锁*/
	function unlockUser(userName){
		layer.confirm('确认要解锁吗？',function(index){
			$.post("sysUser/unlockUser",{userName: userName}, function(result) {
				if(result==1){
					layer.msg('已锁定!', { icon: 1, time: 1000 },function () {
						location.reload();
					});
				}else{
					layer.msg('锁定失败!', { icon: 2, time: 1000 },function () {
						location.reload();
					});
				}
			});
		});
	}

	/*重置密码*/
	function resetPassWord(userName){
		layer.confirm("确认要重置密码吗？", {icon: 3, title:'提示'}, function(index){
			$.post("sysUser/resetPassWord",{userName: userName}, function(result) {
				if(result==1){
					layer.msg('重置密码成功!', { icon: 1, time: 1000 },function () {
						location.reload();
					});
				}else{
					layer.msg('重置密码失败!', { icon: 2, time: 1000 },function () {
						location.reload();
					});
				}
			});
		});
	}
</script> 
</body>
</html>