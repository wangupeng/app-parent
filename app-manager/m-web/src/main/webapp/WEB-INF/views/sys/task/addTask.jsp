<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../../common/taglib.jsp"%>
<!DOCTYPE HTML>
<head>
	<title>任务列表</title>
</head>
<body>
<div layout:fragment="content">
    <article class="cl pd-10">
        <form action="add" method="post" class="form form-horizontal" id="addForm">
            <div class="row cl">
                <label class="form-label col-xs-3 col-sm-3"><span class="c-red">*</span>任务名称：</label>
                <div class="formControls col-xs-8 col-sm-8">
                    <input type="text" name="jobName" id="jobName" class="input-text" autocomplete="off"/>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-3 col-sm-3"><span class="c-red">*</span>任务分组：</label>
                <div class="formControls col-xs-8 col-sm-8">
                    <input type="text" name="jobGroup" id="jobGroup" class="input-text" autocomplete="off"/>
                </div>
            </div>

            <div class="row cl">
                <label class="form-label col-xs-3 col-sm-3"><span class="c-red">*</span>任务表达式：</label>
                <div class="formControls col-xs-8 col-sm-8">
                    <input type="text" name="cronExpression" id="cronExpression" class="input-text" autocomplete="off"/>
                </div>
            </div>

            <div class="row cl">
                <label class="form-label col-xs-3 col-sm-3"><span class="c-red">*</span>任务实现类：</label>
                <div class="formControls col-xs-8 col-sm-8">
                    <input type="text" name="jobClass" id="jobClass" class="input-text" autocomplete="off"/>
                </div>
            </div>

            <div class="row cl">
                <label class="form-label col-xs-3 col-sm-3">任务描述：</label>
                <div class="formControls col-xs-8 col-sm-8">
                    <input type="text" name="jobDescribe" id="jobDescribe" class="input-text" autocomplete="off"/>
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
            jobName:{
                required:true,
                minlength:2,
                maxlength:50
            },jobGroup:{
                required:true,
                minlength:2,
                maxlength:50
            },cronExpression:{
                required:true,
                minlength:2,
                maxlength:50
            },jobClass:{
                required:true,
                minlength:2,
                maxlength:50
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
                        layer.msg('新增任务成功!', { icon: 1, time: 1000 },function () {
                            window.parent.location.reload();
                        });
                    }else{
                        layer.msg('新增任务失败!', { icon: 2, time: 1000 },function () {
                            window.parent.location.reload();
                        });
                    }
                }
            });
		}
	});
});
</script>
</div>
</body>
</html>