<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../../common/taglib.jsp"%>
<head>
    <title>角色列表</title>
</head>
<body>
	<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> <a href="${staticServer}/index" class="maincolor">首页</a>
		<span class="c-gray en">&gt;</span> 系统管理
		<span class="c-gray en">&gt;</span> 角色管理
		<span class="c-gray en">&gt;</span> 角色列表
		<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.reload();" title="刷新" >
			<i class="Hui-iconfont">&#xe68f;</i>
		</a>
	</nav>
	<div class="Hui-article">
		<article class="cl pd-20">
			<div class="cl pd-5 bg-1 bk-gray">
				<span class="l">
                    <shiro:hasPermission name="/sys/sysRole/delete">

                    </shiro:hasPermission>
					<a href="javascript:;" onclick="toAddRole('新增角色','sysRole/toAdd','10001','800','350')" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 新增角色</a>
				</span>
			</div>

			<table class="table table-border table-bordered table-hover table-bg table-sort dataTables_wrapper" id="dataTables-smsSendOrder">
				<thead>
					<tr class="text-c">
						<th width="25"><input type="checkbox" name="" value=""></th>
						<th width="40">序号</th>
						<th width="100">角色代码</th>
						<th width="100">角色名称</th>
						<th width="120">描述</th>
						<th width="80">创建用户</th>
						<th width="100">创建时间</th>
						<th width="100">操作</th>
					</tr>
				</thead>
				<tbody>
                <c:if test="${pageInfo.list.size() == 0}">
                    <td colspan="10">无数据</td>
                </c:if>
                <c:forEach items="${pageInfo.list }" var="role" varStatus="status">
					<tr class="text-c">
						<td><input type="checkbox" value="2" name=""></td>
						<td>${status.index+1}</td>
						<td>${role.roleCode}</td>
						<td>${role.roleName}</td>
						<td>${role.description}</td>
						<td>${role.createUser}</td>
						<td>${role.createDate}</td>
						<td class="td-manage">
                            <shiro:hasPermission name="/sys/sysRole/update">
							    <a title="修改" onclick="javascript:toUpdate('修改角色','sysRole/toUpdate','10001','800','350','${role.roleCode}')" class="ml-5" data-toggle="tooltip" data-placement="top" style="text-decoration:none"><i class="Hui-iconfont f-16">&#xe6df;</i></a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="/sys/sysRole/authorize">
							    <a title="授权" onclick="javascript:authorize('${role.roleCode}','${role.roleName}')" href="javascript:;" class="ml-5" data-toggle="tooltip" data-placement="top" style="text-decoration:none"><i class="Hui-iconfont f-16">&#xe62c;</i></a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="/sys/sysRole/delete">
							    <a title="删除" onclick="javascript:del('${role.roleCode}')" href="javascript:;" class="ml-5" data-toggle="tooltip" data-placement="top" style="text-decoration:none"><i class="Hui-iconfont f-16">&#xe6e2;</i></a>
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
	<%--<script type="text/javascript" src="lib/zTree/v3/js/jquery.ztree.all-3.5.min.js" th:src="@{/h-ui/lib/zTree/v3/js/jquery.ztree.all-3.5.min.js}"></script>--%>
<script type="text/javascript">
    /*添加角色*/
    function toAddRole(title,url,id,w,h){
        layer_show(title,url,w,h);
    }

    /*修改角色*/
    function toUpdate(title,url,id,w,h,roleCode){
        data = {roleCode:roleCode};
        layer_show(title,url,w,h);
    }

    /*删除角色*/
    function del(roleCode){
        layer.confirm("确认要删除角色吗？", {icon: 3, title:'提示'}, function(index){
            $.post("${staticServer}/sys/sysRole/delete",{roleCode: roleCode}, function(result) {
                if(result>=0){
                    layer.msg('删除角色成功!', { icon: 1, time: 1000 },function () {
                        location.reload();
                    });
                }else{
                    layer.msg('删除角色失败!', { icon: 2, time: 1000 },function () {
                        location.reload();
                    });
                }
            });
        });
    }
    /*授权*/
    function authorize(roleCode,roleName){
        data = {roleCode:roleCode}
        layer.open({
            type: 2
            ,title: '授权'+' -- '+roleName
            ,area: ['400px','500px']
            ,shade: 0.4
            ,maxmin: false
            ,content: '${staticServer}/sys/sysRole/toAuthorize'
            ,btn: ['保存', '关闭']
            ,btnAlign: 'l'
            ,yes: function(index){
                var resources = window["layui-layer-iframe" + index].getSelectCheck();
                var resourceIds = new Array()
                for(var i=0;i<resources.length;i++){
                    resourceIds[i] = resources[i].id
                }
                $.ajax({
                    type:'post',
                    url:'${staticServer}/sys/sysRole/addRoleResource',
                    traditional: true,
                    data:{
                        roleCode: roleCode,
                        resourceIds:resourceIds
                    },
                    success:function(result){
                        if(result>0){
                            layer.msg('权限设置成功!', { icon: 1, time: 1000 },function () {
                                location.reload();
                            });
                        }else if(result==0){
                            layer.msg('已取消全部权限!', { icon: 1, time: 1000 },function () {
                                location.reload();
                            });
                        }else{
                            layer.msg('权限设置失败!', { icon: 2, time: 1000 },function () {
                                location.reload();
                            });
                        }
                    }
                });
            }
            ,btn2: function(){
                layer.close();
            }
        });
    }
</script>
</body>
</html>