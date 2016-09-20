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
                    <td>用户</td>
                    <td>中奖内容</td>
                    <td>中奖时间</td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${records}" var="record">
                    <tr>
                        <td>
                            <c:forEach items="${users}" var="user">
                                <c:if test="${record.userId == user.id}">
                                    ${user.nickName}
                                </c:if>
                            </c:forEach>
                        </td>
                        <td>${record.awardsContent}</td>
                        <td><fmt:formatDate value="${record.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <jsp:include page="../include/page.jsp"/>
</div>

<jsp:include page="../include/footer.jsp"/>
