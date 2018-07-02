<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../../common/taglib.jsp"%>
<head>
	<title>角色授权</title>
</head>
<body>
    <article class="cl pl-30 pr-30">
        <div class="row cl" >
            <ul id="tree" class="ztree"></ul>
        </div>
    </article>
<script type="text/javascript">
    var setting = {
        check: {
            enable: true,//设置 zTree 的节点上是否显示 checkbox / radio
            chkboxType:{ "Y" : "ps", "N" : "ps" }//Y 属性定义 checkbox 被勾选后的情况；N 属性定义 checkbox 取消勾选后的情况；"p" 表示操作会影响父级节点；"s" 表示操作会影响子级节点。
        },
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
            url: "${staticServer}/sys/sysResource/createResourceZtree",
            autoParam: ["id"]
        },
        callback: {
        	onClick: onClick,
            onAsyncSuccess: setCheckNode
    	}
    };

    //设置已选择的权限
    function setCheckNode() {
        var treeObj = $.fn.zTree.getZTreeObj("tree");
        $.ajax({
            type:'post',
            url:'${staticServer}/sys/sysRole/listResourceByRoleCode',
            traditional: true,
            data:{
                roleCode: parent.data.roleCode,
            },
            success:function(data){
                $.each(data,function(i,item){
                    treeObj.checkNode(treeObj.getNodeByParam("id", item.resourceId, null), true, false, true);
                });
            }
        });
        var nodes = treeObj.getNodes();
        for (var i = 0; i < nodes.length; i++) { //设置节点展开
            treeObj.expandNode(nodes[i], true, false, true);
        }
    };

    //点击展开
    function onClick(e,treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("tree");
        zTree.expandNode(treeNode);
    }

	//获取已经选择的权限，父页面用
    function getSelectCheck(){
		var treeObj = $.fn.zTree.getZTreeObj("tree");
		var nodes = treeObj.getCheckedNodes(true);
		return nodes;
	}

    $(function(){
        $.fn.zTree.init($("#tree"), setting);
    });
</script>
</body>
</html>