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
    <div class="row">
        <form  class="form-inline" action="<%=contextPath%>/vote/find" method="GET">
            <div class="form-group">
                <label class="control-label" style="margin-left: 15px"></label>
                <select name="post" id="post" class="form-control" >
                    <option value="">--请选择帖子--</option>
                </select>
                <button type="submit" class="btn"><span class="glyphicon glyphicon-search"></span> 查询</button>
            </div>
        </form>
    </div>

    <!--页面操作详细内容 开始-->
    <div class="row">
        <div class="col-lg-12">
            <table class="table table-bordered table-hover">
                <thead>
                <tr>
                    <td>帖子</td>
                    <td style="width:18%">说明</td>
                    <td>投票状态</td>
                    <td>投票数量</td>
                    <td>操作</td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${votes}" var="vote">
                    <tr>
                        <td>${vote.post}</td>
                        <td>${vote.instructions}</td>
                        <td>
                            <c:choose>
                                <c:when test="${vote.status == '0'}">
                                    <span class="label label-default">进行中</span>
                                </c:when>
                                <c:when test="${vote.status == '1'}">
                                    <span class="label label-warning">已结束</span>
                                </c:when>
                            </c:choose>
                        </td>
                        <td>${vote.voteNum}</td>
                        <td>
                            <c:if test="${vote.status == '0'}" >
                            <a href="<%=contextPath%>/vote/stop/${vote.id}" class="btn btn-primary"><span class="glyphicon glyphicon-top"></span>停止投票</a>
                            </c:if>
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

        $.ajax({
            url: '<%=contextPath%>/vote/getVotePosts',
            type: 'GET',
            success: function(result) {
                if(result.code == 200){
                    $.each(result.data, function(i, val){
                        $("#post").append("<option value='"+val.id+"'>"+val.title+"</option>");
                        if($('#post').val() == val.id){
                            $("#post").val(val.title);
                        }
                    });
                }

            },
            error: function(xhr, textStatus, errorThrown){

            }
        });
    })
</script>