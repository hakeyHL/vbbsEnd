<!DOCTYPE html>
<html lang="zh-cn">
<jsp:include page="jssdksignature.jsp"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<head>
    <meta charset="UTF-8">
    <title>微论坛</title>
    <meta name="wap-font-scale" content="no">
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/microForum/css/h5-all.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/microForum/css/search.css"/>
    <script src="${pageContext.request.contextPath }/microForum/js/frame/jquery-1.12.1.min.js"></script>
</head>
<body>
<header class="header">
    <img class="header-logo" src="${pageContext.request.contextPath }/microForum/images/header-logo.png" alt=""/>

    <div class="card-info">
        <p class="card-item">帖子 <span class="card-num">${totalPostNumber}</span></p>

        <p class="card-item">成员 <span class="menber-num">${totalUsers}</span></p>

        <p class="card-item">浏览 <span class="scan-num">${totalBrowserNum}</span></p>
    </div>
</header>

<div class="top">
    <div class="clear"></div>
    <div class="search">
        <input name="stra" id="searchContent" class="insetword">
        <input type="button" value="搜索" onclick="onSearch()" class="search_but">
        <script type="text/javascript">
            function onSearch() {
                var content = $("#searchContent").val();
                $.post("${pageContext.request.contextPath }/api/search", {
                    "content": content
                }, function (data) {
                    if (data.errorCode == "00000") {
                        $("#searchResultUL").empty();
                        var daList = data.dataList;
                        $.each(daList, function () {
                            $("#searchResultUL").append(
                                    "<li>" +
                                    "<a onclick=\"comment(" + this.postId + ")\">" +
                                    "<div class=\"fplist\">" +
                                    "<h3>" + this.title + "</h3>" +
                                    "<p>" +
                                    "<span class=\"sprite sprite11\"></span><span class=\"mr5\">" + this.browserNum + "</span>" +
                                    "<span class=\"sprite sprite12\"></span><span class=\"mr5\">" + this.commentNum + "</span>" +
                                    "<span class=\"sprite sprite13\"></span><span class=\"mr5\">" + this.thumbNum + "</span>" +
                                    "</p>" +
                                    "</div>" +
                                    "<div class=\"fplistr\">" +
                                    "<span>" + this.sCommentUserNames + "</span>" +
                                    "<span class=\"star\">" + this.searchPageTime + "</span></div>" +
                                    "</a>" +
                                    "</li>"
                            )
                            ;
                        });
                    } else if (data.errorCode == "10000") {
                        alert("异常,请联系管理员!");
                    }
                }, "json");
            }
            function comment(postId) {
                window.location.href = "${pageContext.request.contextPath }/api/post/info?id=" + postId;
            }
        </script>
    </div>
</div>
<div class="middle">
    <ul class="fpappli" id="searchResultUL">
    </ul>
</div>

<div class="clear"></div>

<footer class="footer">
    <div class="footer-container">
        <div class="menu-btn"><span class="sprite sprite42"></span></div>
        <div class="post-header">
            <img src="${existUser.avatar}">
        </div>
    </div>
</footer>

</body>
</html>