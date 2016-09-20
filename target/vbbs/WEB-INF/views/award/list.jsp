<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
    String contextPath = request.getContextPath();
%>

<jsp:include page="../include/header.jsp"/>
<jsp:include page="../include/left.jsp"/>

<div id="page-wrapper">

    <div class="col-lg-12" style="margin: 10px"></div>

    <!--页面操作详细内容 开始-->
    <div class="row">
        <div class="col-lg-12">
            <table class="table table-bordered table-hover">
                <thead>
                <tr>
                    <td>奖品名称</td>
                    <td>数量</td>
                    <td>操作</td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${awards}" var="award">
                    <tr>
                        <td>${award.awardsName}</td>
                        <td>${award.awardsNum}</td>
                        <td>
                            <a href="<%=contextPath%>/award/detail/${award.id}" class="btn btn-primary"><span class="glyphicon glyphicon-edit"></span>编辑</a>
                            <a href="<%=contextPath%>/award/delete/${award.id}" class="btn btn-primary"><span class="glyphicon glyphicon-edit"></span>删除</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <jsp:include page="../include/page.jsp"/>
</div>

<jsp:include page="../include/footer.jsp"/>

<script>

    $(function(){


    })
</script>