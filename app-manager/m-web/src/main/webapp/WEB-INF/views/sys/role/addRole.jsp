<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../../common/taglib.jsp"%>
<head>
	<title>新增角色</title>
</head>
<body>
	<article class="cl pd-10">
		<form action="add" method="post" class="form form-horizontal" id="addForm">
			<div class="row cl">
				<label class="form-label col-xs-3 col-sm-3"><span class="c-red">*</span>角色代码：</label>
				<div class="formControls col-xs-8 col-sm-8">
					<input type="text" name="roleCode" id="roleCode" class="input-text" autocomplete="off" placeholder="3~20个字符，字母/中文/数字/下划线"/>
				</div>
			</div>

			<div class="row cl">
				<label class="form-label col-xs-3 col-sm-3"><span class="c-red">*</span>角色名称：</label>
				<div class="formControls col-xs-8 col-sm-8">
					<input type="text" name="roleName" class="input-text" autocomplete="off" placeholder="2~10个中文"/>
				</div>
			</div>

			<div class="row cl">
				<label class="form-label col-xs-3 col-sm-3">角色说明：</label>
				<div class="formControls col-xs-8 col-sm-8">
					<textarea name="description" cols="" rows="" class="textarea" placeholder="0～50个中文"></textarea>
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
	$("#addForm").validate({
		rules:{
            roleCode:{
				required:true,
				minlength:3,
				maxlength:20,
                remote: {
                    url: "checkExist", //后台处理程序
                    type: "post", //数据发送方式
                    data: {
                        roleCode: function () {
                            return $("#roleCode").val();
                        }
                    }
                }
			},roleName:{
                required:true,
                minlength:2,
                maxlength:10
            }
		},
        messages: {
            roleCode: {
                remote: "角色代码已存在！"
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
                        layer.msg('新增角色成功!', { icon: 1, time: 1000 },function () {
                            window.parent.location.reload();
                        });
                    }else{
                        layer.msg('新增角色失败!', { icon: 2, time: 1000 },function () {
                            window.parent.location.reload();
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