<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../../common/taglib.jsp"%>
<!DOCTYPE HTML>
<head>
	<title>修改密码</title>
</head>
<body>
    <article class="cl pd-20">
        <form action="updatePassWord" method="post" class="form form-horizontal" id="form-change-password">
            <div class="row cl">
                <label class="form-label col-xs-3 col-sm-3"><span class="c-red">*</span>用户名：</label>
                <div class="formControls col-xs-8 col-sm-9">${userSession.realName}</div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-3 col-sm-3"><span class="c-red">*</span>原密码：</label>
                <div class="formControls col-xs-8 col-sm-8">
                    <input type="password" class="input-text" autocomplete="off" name="oldPassWord" id="oldPassWord">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-3 col-sm-3"><span class="c-red">*</span>新密码：</label>
                <div class="formControls col-xs-8 col-sm-8">
                    <input type="password" class="input-text" autocomplete="off" name="passWord" id="passWord">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-3 col-sm-3"><span class="c-red">*</span>确认密码：</label>
                <div class="formControls col-xs-8 col-sm-8">
                    <input type="password" class="input-text" autocomplete="off" name="passWord2" id="passWord2">
                </div>
            </div>
            <div class="row cl">
                <div class="col-xs-12 col-sm-12 text-c">
                    <input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;确定&nbsp;&nbsp;">
                </div>
            </div>
        </form>
    </article>
<script type="text/javascript">
$(function(){
	$("#form-change-password").validate({
		rules:{
            oldPassWord:{
                required:true,
                minlength:6,
                maxlength:16
            },
            passWord:{
				required:true,
				minlength:6,
				maxlength:16
			},
            passWord2:{
				required:true,
				minlength:6,
				maxlength:16,
				equalTo: "#passWord"
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
                        layer.msg('修改密码成功!', { icon: 1, time: 2000 },function () {
                            window.parent.location = "${staticServer}/logout";
                        });
                    }else if(result==2){
                        layer.msg('原密码输入错误!', { icon: 2, time: 2000 },function () {
                        });
                    }else{
                        layer.msg('修改密码失败!', { icon: 2, time: 2000 },function () {
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