<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../../common/taglib.jsp"%>
<!DOCTYPE HTML>
<head>
	<title>添加用户</title>
</head>
<body>
    <article class="cl pd-10">
        <form action="add" method="post" class="form form-horizontal" id="addForm">
            <div class="row cl">
                <label class="form-label col-xs-3 col-sm-3"><span class="c-red">*</span>用户名：</label>
                <div class="formControls col-xs-8 col-sm-8">
                    <input type="text" name="userName" id="userName" class="input-text" autocomplete="off" placeholder="4~16个字符，字母/中文/数字/下划线"/>
                </div>
            </div>

            <div class="row cl">
                <label class="form-label col-xs-3 col-sm-3"><span class="c-red">*</span>姓名：</label>
                <div class="formControls col-xs-8 col-sm-8">
                    <input type="text" name="realName" class="input-text" autocomplete="off" placeholder="2~10个中文"/>
                </div>
            </div>

            <div class="row cl">
                <label class="form-label col-xs-3 col-sm-3">手机号：</label>
                <div class="formControls col-xs-8 col-sm-8">
                    <input type="text" name="mobile" class="input-text" autocomplete="off" placeholder="手机号"/>
                </div>
            </div>

            <div class="row cl">
                <label class="form-label col-xs-3 col-sm-3"><span class="c-red">*</span>角色：</label>
                <div class="formControls col-xs-8 col-sm-8">
                    <span class="select-box">
                        <select class="select" size="1" name="roleCode" id="roleCode">
                            <option value="" selected="">请选择角色</option>
                        </select>
                    </span>
                </div>
            </div>

            <div class="row cl">
                <div class="col-xs-12 col-sm-12 text-c">
                    <input class="btn btn-primary radius" type="submit" value="保存">
                    <!--<input class="btn btn-primary radius" type="button" onclick="history.back(-1)" value="返回">-->
                </div>
            </div>
        </form>
    </article>
<script type="text/javascript">
$(function(){
	$("#addForm").validate({
		rules:{
            userName:{
				required:true,
				minlength:3,
				maxlength:16,
                remote: {
                    url: "checkExist", //后台处理程序
                    type: "post", //数据发送方式
                    //dataType: "json", //接受数据格式
                    data: { //要传递的数据
                        userName: function () {
                            return $("#userName").val();
                        }
                    }
                }
			},realName:{
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
        messages: {
            userName: {
                remote: "用户名已存在！"
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
                        layer.msg('新增用户成功!', { icon: 1, time: 1000 },function () {
                            window.parent.location.reload();
                        });
                    }else{
                        layer.msg('新增用户失败!', { icon: 2, time: 1000 },function () {
                            window.parent.location.reload();
                        });
                    }
                }
            });
		}
	});

    $.ajax({
        type:'post',
        url:'${staticServer}/sys/sysRole/getRole',
        traditional: true,
        data:{},
        success:function(data){
            $.each(data,function(i,item){
                $("#roleCode").append("<option value='"+item.roleCode+"'>"+item.roleName+"</option>");
            });
        }
    });
});
</script>
</body>
</html>