<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="taglib.jsp"%>
    <div id="DataTables_Table_0_wrapper" class="dataTables_wrapper no-footer">
        <div class="dataTables_length" id="DataTables_Table_0_length">
            <label class="dataTables_info">
                显示 <span text="${pageInfo.startRow}"></span> 到 <span>${pageInfo.endRow}</span> 条，共 <span>${pageInfo.total}</span> 条，每页显示
                <select name="DataTables_Table_0_length" aria-controls="DataTables_Table_0" class="select" id="fySelect">
                    <option value="1" ${pageInfo.pageSize == 1?'selected':'false'}>1</option>
                    <option value="2" ${pageInfo.pageSize == 2?'selected':'false'}>2</option>
                    <option value="10" ${pageInfo.pageSize == 10?'selected':'false'}>10</option>
                    <option value="20" ${pageInfo.pageSize == 20?'selected':'false'}>20</option>
                </select> 条
            </label>
        </div>
        <div class="dataTables_paginate paging_simple_numbers" id="DataTables_Table_0_paginate">
            <c:if test="${pageInfo.hasPreviousPage}">
                <a onclick="javascript:page('${pageInfo.prePage}')" class="paginate_button previous disabled" aria-controls="DataTables_Table_0" data-dt-idx="0" tabindex="0" id="DataTables_Table_0_previous" >上一页</a>
            </c:if>
            <span>
                <c:forEach items="${pageInfo.navigatepageNums }" var="nav">
                    <a onclick="javascript:page('${nav}')" class="paginate_button" style="${nav==pageInfo.pageNum?'background:#5a98de;color:#fff':''}" aria-controls="DataTables_Table_0" data-dt-idx="1" tabindex="0">${nav}</a>
                </c:forEach>
            </span>

            <c:if test="${pageInfo.hasNextPage}">
                <a onclick="javascript:page('${pageInfo.nextPage}')" class="paginate_button next disabled" aria-controls="DataTables_Table_0" data-dt-idx="2" tabindex="0" id="DataTables_Table_0_next">下一页</a>
            </c:if>
        </div>
    </div>
    <script type="text/javascript">
        var pathname = window.location.pathname;
        pathname = pathname.substr(pathname.indexOf("/",2),pathname.length);
        var f = $("form");
        $(function(){
            if(f.length>0){
                f.append("<input type='hidden' id='pageSize' name='pageSize'/>").append("<input type='hidden' id='pageNum' name='pageNum' value='1'/>");
                $("#pageSize").val($("#fySelect").val());
            }

            $("#fySelect").bind("change",function(){
                page(1);
            });

        });

        function page(pageNum) {
            if(f.length>0){
                $("#pageSize").val($("#fySelect").val());
                $("#pageNum").val(pageNum);
                f.submit();
            }else{
                window.location.href = '${staticServer}'+pathname+"?pageSize="+$("#fySelect").val()+"&pageNum="+pageNum;
            }
        }
    </script>
</html>