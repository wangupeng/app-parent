<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../../common/taglib.jsp"%>
<head>
	<title>用户列表</title>
</head>
<body>
	<article class="cl pd-10">
		<form action="update" method="post" class="form form-horizontal" id="updateForm">
			<div class="row cl">
				<label class="form-label col-xs-3 col-sm-3"><span class="c-red">*</span>角色代码：</label>
				<div class="formControls col-xs-8 col-sm-8">
					<input type="text" name="roleCode" id="roleCode" class="input-text" autocomplete="off" readonly/>
				</div>
			</div>

			<div class="row cl">
				<label class="form-label col-xs-3 col-sm-3"><span class="c-red">*</span>角色名称：</label>
				<div class="formControls col-xs-8 col-sm-8">
					<input type="text" name="roleName" id="roleName" class="input-text" autocomplete="off" placeholder="2~10个中文"/>
				</div>
			</div>

			<div class="row cl">
				<label class="form-label col-xs-3 col-sm-3">角色说明：</label>
				<div class="formControls col-xs-8 col-sm-8">
					<textarea name="description" id="description" cols="" rows="" class="textarea" placeholder="0～50个中文"></textarea>
				</div>
			</div>

			<div class="row cl">
				<div class="col-xs-12 col-sm-12 text-c">
					<input class="btn btn-primary radius" type="submit" value="修改">
				</div>
			</div>
		</form>
	</article>
<script type="text/javascript">
$(function(){
    $.ajax({
        type:'post',
        url:'getRoleByRoleCode',
        data:{
            roleCode: parent.data.roleCode,
        },
        success:function(data){
            $("#roleCode").val(data.roleCode);
            $("#roleName").val(data.roleName);
            $("#description").val(data.description);
        }
    });
    $("#updateForm").validate({
        rules:{
            roleName:{
                required:true,
                minlength:2,
                maxlength:10
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
                        layer.msg('修改角色成功!', { icon: 1, time: 1000 },function () {
                            window.parent.location.reload();
                        });
                    }else{
                        layer.msg('修改角色失败!', { icon: 2, time: 1000 },function () {
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