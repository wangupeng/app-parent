<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/taglib.jsp"%>
<head>
	<title>新增资源</title>
</head>
<body>
    <article class="cl pd-10">
        <form action="add" method="post" class="form form-horizontal" id="addForm">
            <div class="row cl">
                <label class="form-label col-xs-3 col-sm-3"><span class="c-red">*</span>资源名称：</label>
                <div class="formControls col-xs-8 col-sm-8">
                    <input type="text" name="resourceName" id="resourceName" class="input-text" autocomplete="off" placeholder="1~20个汉字"/>
                </div>
            </div>

            <div class="row cl">
                <label class="form-label col-xs-3 col-sm-3">资源类型：</label>
                <div class="formControls col-xs-8 col-sm-8">
                    <input type="text" name="resourceTypeName" id="resourceTypeName" class="input-text" autocomplete="off" readonly/>
                    <input type="hidden" name="resourceType" id="resourceType"/>
                </div>
            </div>

            <div class="row cl">
                <label class="form-label col-xs-3 col-sm-3">上级节点：</label>
                <div class="formControls col-xs-8 col-sm-8">
                    <input type="text" name="parentName" id="parentName" class="input-text" autocomplete="off" readonly/>
                    <input type="hidden" name="parentId" id="parentId"/>
                </div>
            </div>

            <div class="row cl">
                <label class="form-label col-xs-3 col-sm-3"><span class="c-red">*</span>资源链接：</label>
                <div class="formControls col-xs-8 col-sm-8">
                    <input type="text" name="resourceUrl" class="input-text" autocomplete="off" placeholder="1~40个字符"/>
                </div>
            </div>

            <div class="row cl">
                <label class="form-label col-xs-3 col-sm-3">图标：</label>
                <div class="formControls col-xs-8 col-sm-8">
                    <input type="text" name="icon" id="icon" class="input-text" autocomplete="off" placeholder="请参考图标示例"/>
                </div>
            </div>

            <div class="row cl">
                <label class="form-label col-xs-3 col-sm-3">描述：</label>
                <div class="formControls col-xs-8 col-sm-8">
                    <textarea name="description" cols="" rows="" class="textarea" placeholder="0～50个中文"></textarea>
                </div>
            </div>

            <div class="row cl">
                <label class="form-label col-xs-3 col-sm-3"><span class="c-red">*</span>排序：</label>
                <div class="formControls col-xs-8 col-sm-8">
                    <input type="text" name="displayOrder" class="input-text" autocomplete="off" placeholder=""/>
                </div>
            </div>

            <div class="row cl">
                <div class="col-xs-12 col-sm-12 text-c">
                    <input class="btn btn-primary radius" type="submit" value="保存">
                </div>
            </div>
        </form>
    </article>
<script type="text/javascript">
$(function(){
    $("#parentName").val(parent.data.resourceName);
    $("#parentId").val(parent.data.resourceId);
    var resourceType = parent.data.resourceType;
    if(resourceType=="0"||resourceType=="1"){
        $("#resourceTypeName").val("模块");
        $("#resourceType").val("1");
    }else if(resourceType=="2"){
        $("#resourceTypeName").val("按钮");
        $("#resourceType").val("2");
    }

	$("#addForm").validate({
		rules:{
            resourceName:{
				required:true,
				maxlength:20
			},
            resourceUrl:{
                required:true,
                maxlength:40
            },
            displayOrder: {
                required: true,
                number: true,
                maxlength: 10
            },
            description: {
                maxlength: 50
            }
		},
		onkeyup:false,
		focusCleanup:true,
		success:"valid",
		submitHandler:function(form){
            $(form).ajaxSubmit({
                dataType:"json",
                success:function(result){
                    if(result==1){
                        layer.msg('新增资源成功!', { icon: 1, time: 1000 },function () {
                            window.parent.reoladZtreeNode();
                            layer_close();
                        });
                    }else{
                        layer.msg('新增资源失败!', { icon: 2, time: 1000 },function () {
                            window.parent.reoladZtreeNode();
                            layer_close();
                        });
                    }
                }
            });
		}
	});


});
</script>
</body>
</html>