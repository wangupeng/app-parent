<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/taglib.jsp"%>
	<head>
		<title>资源列表</title>
	</head>
<body>
	<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> <a href="${staticServer}/index}" class="maincolor">首页</a>
		<span class="c-gray en">&gt;</span> 系统管理
		<span class="c-gray en">&gt;</span> 资源管理
		<span class="c-gray en">&gt;</span> 资源列表
		<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.reload();" title="刷新" >
			<i class="Hui-iconfont">&#xe68f;</i>
		</a>
	</nav>

	<div class="Hui-article">
		<div class="pos-a" style="width:200px;left:0;top:10; bottom:0; height:100%; border-right:1px solid #e5e5e5; background-color:#f5f5f5">
			<ul id="tree" class="ztree"></ul>
		</div>
		<div style="margin-left:200px;">
			<div class="pd-20">
				<div class="cl pd-5 bg-1 bk-gray">
				<span class="l">
                    <shiro:hasPermission name="/sys/sysResource/add">
					    <a href="javascript:;" onclick="toAddResource('新增资源','sysResource/toAdd','10001','800','550')" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 新增资源</a>
                    </shiro:hasPermission>
				</span>
				</div>

				<table class="table table-border table-bordered table-hover table-bg table-sort dataTables_wrapper" id="dataTables-smsSendOrder">
					<thead>
					<tr class="text-c">
						<th width="25"><input type="checkbox" name="" value=""></th>
						<th width="30">序号</th>
						<th width="90">模块名称</th>
						<th width="90">模块链接</th>
						<th width="50">类型</th>
						<th width="150">描述</th>
						<th width="70">图标</th>
						<th width="30">排序</th>
						<th width="50">操作</th>
					</tr>
					</thead>
                    <tbody id="resourceList">
					</tbody>
				</table>
			</div>
		</div>
	</div>
<script type="text/javascript">
    var setting = {
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "pid",
                rootPId: 0
            }
        },
        view: {
            showLine: false,//设置 zTree 是否显示节点之间的连线。
            dblClickExpand: false//双击节点时，是否自动展开父节点的标识,设置为false为了避免与"点击展开"冲突
        },
        async: {
            enable: true,
            url: "${staticServer}/sys/sysResource/listModuleResource",
            autoParam: ["id"]
        },
        callback: {
            onClick: showResource,
            onAsyncSuccess: expand
        }
    };

    //根据父节点显示资源列表
    function showResource(event,treeId, treeNode) {
        var str = "";
        $.ajax({
            type:'post',
            url:'sysResource/listResourceByParentId',
            traditional: true,
            data:{
                parentId: treeNode.id,
            },
            success:function(data){
                $.each(data,function(i,item){
                    str+="<tr class='text-c'>"+
                        "<td><input type='checkbox' name=''></td>"+
                        "<td>"+(i+1)+"</td>"+
                        "<td>"+item.resourceName+"</td>"+
                        "<td>"+item.resourceUrl+"</td>"+
                        "<td>"+(item.resourceType==1?'模块':'按钮')+"</td>"+
                        "<td>"+(item.description==null?'':item.description)+"</td>"+
                        "<td><i class='Hui-iconfont'>"+(item.icon==null?'':item.icon)+"</i></td>"+
                        "<td>"+item.displayOrder+"</td>"+
                        "<td class='td-manage'>"+
                        "<a title='修改' onclick='toUpdateResource(\"修改资源\",\"sysResource/toUpdate\",\"10001\",\"800\",\"550\",\""+item.resourceId+"\")' href='javascript:;' class='ml-5' data-toggle='tooltip' data-placement='top' style='text-decoration:none'><i class='Hui-iconfont f-16'>&#xe6df;</i></a>"+
                        "<a title='删除' onclick='deleteResource("+item.resourceId+")' href='javascript:;' class='ml-5' data-toggle='tooltip' data-placement='top' style='text-decoration:none'><i class='Hui-iconfont f-16'>&#xe6e2;</i></a>"+
                    "</td>"+
                    "</tr>";
                });
                $("#resourceList").html(str);
            }
        });
    }

    function toAddResource(title,url,id,w,h){
        var treeObj = $.fn.zTree.getZTreeObj("tree");
        var nodes = treeObj.getSelectedNodes();
        if(nodes.length<=0){
            layer.msg('请在左侧选择一个节点!', { icon: 3, time: 2000 });
        }else{
            data = {resourceId:nodes[0].id,resourceName:nodes[0].name,resourceType:nodes[0].level}
            layer_show(title,url,w,h);
        }
    }

    function toUpdateResource(title,url,id,w,h,resourceId){
        data = {resourceId:resourceId}
        layer_show(title,url,w,h);
    }

    function deleteResource(resourceId){
        layer.confirm("确认要删除资源吗？", {icon: 3, title:'提示'}, function(index){
            $.post("sysResource/delete",{resourceId: resourceId}, function(result) {
                if(result>=0){
                    layer.msg('删除资源成功!', { icon: 1, time: 1000 },function () {
                        reoladZtreeNode();
                    });
                }else{
                    layer.msg('删除资源失败!', { icon: 2, time: 1000 },function () {
                        reoladZtreeNode();
                    });
                }
            });
        });
    }

    $(function(){
        $.fn.zTree.init($("#tree"), setting);
    });

    //重新点击之前选中的节点
    var ztreeNodes;
    function reoladZtreeNode() {
        var treeObj = $.fn.zTree.getZTreeObj("tree");
        ztreeNodes = treeObj.getSelectedNodes();
        treeObj.selectNode(treeObj.getNodeByParam("id",ztreeNodes[0].id,null));//选中指定节点
        treeObj.setting.callback.onClick(null, treeObj.setting.treeId, treeObj.getNodeByParam("id",ztreeNodes[0].id,null));//触发函数
        $.fn.zTree.init($("#tree"), setting);//重新加载树
    }

    //设置节点展开
    function expand(){
        var treeObj = $.fn.zTree.getZTreeObj("tree");
        var nodes = treeObj.getNodes();
        for (var i = 0; i < nodes.length; i++) {
            treeObj.expandNode(nodes[i], true, false, true);
        }
        if(typeof(ztreeNodes) != "undefined"){
            treeObj.selectNode(ztreeNodes[0]);
        }
    }
</script>
</body>
</html>