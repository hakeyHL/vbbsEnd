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
                <form  class="form-inline" action="<%=contextPath%>/post/find" method="GET">
                    <div class="form-group">
                        <label class="control-label" style="margin-left: 15px"></label>
                       <select name="section" id="section" class="form-control" >
                           <option value="">--请选择版块--</option>
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
                            <td>发帖人</td>
                            <td>版块</td>
                            <td>标题</td>
                            <td style="width:18%">内容</td>
                            <td>发帖时间</td>
                            <td>浏览量</td>
                            <td>评论数量</td>
                            <td>点赞数量</td>
                            <td>帖子状态</td>
                            <td>帖子类型</td>
                            <td>是否置顶</td>
                            <td>操作</td>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${posts}" var="post">
                            <tr>
                                <td>${post.user}</td>
                                <td>${post.section}</td>
                                <td>${post.title}</td>
                                <td>${post.content}</td>
                                <td><fmt:formatDate value="${post.publishTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                <td>${post.browseNum}</td>
                                <td>${post.commentNum}</td>
                                <td>${post.thumbNum}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${post.status == '0'}">
                                            <span class="label label-default">普通</span>
                                        </c:when>
                                        <c:when test="${post.status == '1'}">
                                            <span class="label label-warning">精华</span>
                                        </c:when>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${post.type == '0'}">
                                            <span class="label label-default">讨论帖</span>
                                        </c:when>
                                        <c:when test="${post.type == '1'}">
                                            <span class="label label-warning">投票贴</span>
                                        </c:when>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${post.top == '0'}">
                                            <span class="label label-default">否</span>
                                        </c:when>
                                        <c:when test="${post.top == '1'}">
                                            <span class="label label-warning">是</span>
                                        </c:when>
                                    </c:choose>
                                </td>
                                <td>
                                    <a href="<%=contextPath%>/post/detail/${post.id}" class="btn btn-primary"><span class="glyphicon glyphicon-edit"></span>编辑</a>
                                    <a href="<%=contextPath%>/post/top/${post.id}" class="btn btn-primary"><span class="glyphicon glyphicon-top"></span>置顶</a>
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
            url: '<%=contextPath%>/post/getSections',
            type: 'GET',
            success: function(result) {
                if(result.code == 200){
                    $.each(result.data, function(i, val){
                        $("#section").append("<option value='"+val.id+"'>"+val.name+"</option>");
                        if($('#section').val() == val.id){
                            $("#section").val(val.name);
                        }
                    });
                }

            },
            error: function(xhr, textStatus, errorThrown){

            }
        });
    })
</script>