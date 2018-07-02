<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<body>
<script type="text/javascript" src="${staticServer}/static/lib/jquery/1.9.1/jquery.min.js"></script>
<aside class="Hui-aside">
	<div class="menu_dropdown bk_2" id="menu">
	</div>

	<script  th:inline="javascript">
        /*加载菜单*/
        $.ajax({
            cache: true,
            type: "POST",
            url:'${staticServer}/sys/sysResource/loadMenu',
            dataType:"json",
            async:false,
            success: function(data) {
                var html="",parentId = "";
                for(var i=0;i<data.length;i++){
                    if(data[i].parentId=="0"){
                        parentId = data[i].resourceId;

                        html+="<dl id='menu-system'>"+
                            "<dt><i class='Hui-iconfont'>"+data[i].icon+"</i> "+data[i].resourceName+"<i class='Hui-iconfont menu_dropdown-arrow'>&#xe6d5;</i></dt><dd><ul class='submenu'>";
                        for(var j=0;j<data.length;j++){
                            var resourceUrl = data[j].resourceUrl;
                            resourceUrl = resourceUrl.substr(1,resourceUrl.length);
                            if(data[j].parentId==parentId){
                                html+="<li id='"+resourceUrl+"'><a href='${staticServer}/"+resourceUrl+"' title='"+data[j].resourceName+"'>"+data[j].resourceName+"</a></li>";
                            }
                        }
                    }
                    html+="</li></ul></dd></dl>";
                }
                $("#menu").append(html);
            }
        });
        $(function(){
            $(".submenu li").find("a").click(function() {
                //当前选择的下标
                var index = $(this).parent().attr("id");
                //记录下标
                jQuery.jCookie('current', index, 30, {
                    path : '/'
                });
            });
            var current = jQuery.jCookie('current');
            if (current != null && current != '') {
                $('.current').removeClass('current').parent().parent().css('display','');;
                var $current = $(document.getElementById(current));
                $current.addClass("current").parent().parent().css('display','block').prev().addClass('selected');
            }
        });
	</script>
</aside>
<div class="dislpayArrow hidden-xs"><a class="pngfix" href="javascript:void(0);" onClick="displaynavbar(this)"></a></div>
</body>
</html>