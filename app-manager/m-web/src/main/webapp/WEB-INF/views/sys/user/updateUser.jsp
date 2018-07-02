<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../../common/taglib.jsp"%>
<!DOCTYPE HTML>
<head>
	<title>用户列表</title>
</head>
<body>
    <article class="cl pd-10">
        <form action="update" method="post" class="form form-horizontal" id="updateForm">
                <div class="row cl">
                    <label class="form-label col-xs-3 col-sm-3"><span class="c-red">*</span>用户名：</label>
                    <div class="formControls col-xs-8 col-sm-8">
                        <input type="text" name="userName" id="userName" readonly class="input-text" autocomplete="off"/>
                    </div>
                </div>

                <div class="row cl">
                    <label class="form-label col-xs-3 col-sm-3"><span class="c-red">*</span>姓名：</label>
                    <div class="formControls col-xs-8 col-sm-8">
                        <input type="text" name="realName" id="realName" class="input-text" autocomplete="off" placeholder="2~10个中文"/>
                    </div>
                </div>

                <div class="row cl">
                    <label class="form-label col-xs-3 col-sm-3">手机号：</label>
                    <div class="formControls col-xs-8 col-sm-8">
                        <input type="text" name="mobile" id="mobile" class="input-text" autocomplete="off" placeholder="手机号"/>
                    </div>
                </div>

                <div class="row cl">
                    <label class="form-label col-xs-3 col-sm-3"><span class="c-red">*</span>角色：</label>
                    <div class="formControls col-xs-8 col-sm-8">
                        <span class="select-box">
                            <select name="roleCode" id="roleCode" class="select">
                                <option value="" selected="">请选择角色</option>
                            </select>
                        </span>
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
    var roleCode="";
    $.ajax({
        type:'post',
        url:'getUserByUserName',
        data:{
            userName: parent.data.userName,
        },
        success:function(data){
            $("#userName").val(data.userName);
            $("#realName").val(data.realName);
            $("#mobile").val(data.mobile);
            roleCode=data.roleCode;
            $.ajax({
                type:'post',
                url:'../sysRole/getRole',
                traditional: true,
                data:{},
                success:function(data){
                    $.each(data,function(i,item){
                        if(item.roleCode==roleCode){
                            $("#roleCode").append("<option value='"+item.roleCode+"' selected>"+item.roleName+"</option>");
                        }else{
                            $("#roleCode").append("<option value='"+item.roleCode+"'>"+item.roleName+"</option>");
                        }
                    });
                }
            });
        }
    });

	$("#updateForm").validate({
		rules:{
            realName:{
                required:true,
                minlength:2,
                maxlength:10
            },mobile:{
                required:false,
                isPhone:true,
            },
			roleCode:{
				required:true,
			},
		},
		onkeyup:false,
		focusCleanup:true,
		success:"valid",
		submitHandler:function(form){
            $(form).ajaxSubmit({
                dataType:"json",
                success:function(result){
                    if(result==1){
                        layer.msg('修改用户成功!', { icon: 1, time: 1000 },function () {
                            window.parent.location.reload();
                        });
                    }else{
                        layer.msg('修改用户失败!', { icon: 2, time: 1000 },function () {
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