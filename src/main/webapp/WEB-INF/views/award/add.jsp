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

    <div class="col-lg-12" style="margin: 20px"></div>

    <form class="form-horizontal" action="<%=contextPath%>/award/add"  method="POST" >

        <div class="form-group">
            <label  class="col-sm-3 control-label">奖品名称</label>
            <div class="col-sm-3">
                <input type="text" name="awardsName" class="form-control" value="${award.awardsName}" >
            </div>
        </div>


        <div class="form-group">
            <label  class="col-sm-3 control-label">奖品数量</label>
            <div class="col-sm-3">
                <input type="number" name="awardsNum" class="form-control" value="${award.awardsNum}" >
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-offset-3 col-sm-9">
                <input type="hidden" name="id" value="${award.id}">
                <button type="submit" class="btn btn-primary" id="button_submit">确定</button>
            </div>
        </div>

    </form>


</div>

<script>

    $(function(){

    });

</script>

<jsp:include page="../include/footer.jsp"/>