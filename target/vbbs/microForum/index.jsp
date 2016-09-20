<!DOCTYPE html>
<html lang="zh-cn">
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
    <link rel="stylesheet" href="${pageContext.request.contextPath }/microForum/css/index.css"/>
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
<div class="sort-container">
    <div class="sort-head">
        <p id="indexP" class="sort-nav sort-active" onclick="backToIndex()"><span>首页</span> <span
                class="br"></span></p>

        <p id="creamP" class="sort-nav" onclick="goToCream()"><span>精华</span></p>
        <input id="cream" hidden value="${cream}"/>
    </div>
    <div id="sections" class="sort-sifting clearfix">
        <div id="section0" class="sort-wing" onclick="getPostBySection(0)">全部</div>
        <input id="sectionId" hidden value="${sectionId}"/>
        <c:forEach items="${sections}" var="section">
            <div id="section${section.id}" class="sort-wing"
                 onclick="getPostBySection(${section.id})">${section.name}</div>
        </c:forEach>
    </div>
    <div class="top-card">
        <div class="top-header clearfix">
            <p class="top1">置顶帖</p>
            <%--<p class="top2">更多...</p>--%>
        </div>
        <div class="top-content">
            <c:forEach items="${tops}" var="top">
                <div class="top-item clearfix" onclick="comment(${top.id})">
                    <p class="top-btn">置顶</p>

                    <p class="top-text">${top.title}</p>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
<c:forEach var="post" items="${postVos}">
    <c:if test="${post.votePost == 1}">
        <div class="test-box">
            <div class="test-header clearfix">
                <img class="test-img" src="${post.avatarUrl}" alt=""/>
                <img class="test-rank" src="${pageContext.request.contextPath }/microForum/images/header-rank.png"
                     alt=""/>

                <div class="test-text">
                    <p class="test-p">${post.userName}</p>

                    <p class="test-p"><span>${post.publishTime}</span></p>
                </div>
            </div>
            <div class="test-content">
                <p class="test-tit">${post.content}</p>

                <div class="question-box clearfix" onclick="comment(${post.postId})">
                    <img class="question-img" src="${pageContext.request.contextPath }/microForum/images/test-img.png"
                         alt=""/>

                    <div class="question-text">
                        <p class="question-p">${post.voteDescription}</p>

                        <p class="question-p"><span>人数: ${post.voteNum} 人</span>
                            <span>
                                <c:choose>
                                    <c:when test="${post.status==0}">
                                        正在进行
                                    </c:when>
                                    <c:otherwise>
                                        已经结束
                                    </c:otherwise>
                                </c:choose>
                            </span>
                        </p>
                    </div>
                </div>
                <div class="handle-box clearfix">
                    <p class="handle-p">
                        <span class="sprite sprite11"></span>
                        <span> ${post.browserNum}</span>
                    </p>

                    <p class="handle-p" onclick="comment(${post.postId})">
                        <span class="sprite sprite12"></span>
                        <span style="display:inline-block;margin-top:2px;">${post.commentNum}</span>
                    </p>

                    <p class="handle-p great-box" onclick="doThumb(${post.postId})">
                        <c:choose>
                            <c:when test="${post.thisUserLike==true}">
                                <span id="greatClassId${post.postId}" class="sprite sprite14 great"></span>
                            </c:when>
                            <c:otherwise>
                                <span id="greatClassId${post.postId}" class="sprite sprite13 great"></span>
                            </c:otherwise>
                        </c:choose>
                        <span id="vbbs${post.postId}">${post.thumbNum}</span>
                        <span class="add">+1</span>
                    </p>
                </div>
            </div>
        </div>
    </c:if>

    <c:if test="${post.votePost==0 &&post.hasComment==0 }">
        <%--普通帖且无评论--%>
        <div class="post-item">
            <div class="test-header clearfix">
                <img class="test-img" src="${post.avatarUrl}" alt=""/>
                <img class="test-rank" src="${pageContext.request.contextPath }/microForum/images/header-rank.png"
                     alt=""/>

                <div class="test-text">
                    <p class="test-p">${post.userName}</p>

                    <p class="test-p"><span> ${post.publishTime}</span></p>
                </div>
            </div>
            <div class="post-text" onclick="comment(${post.postId})">${post.content}</div>
            <div class="img-box clearfix">
                <c:forEach items="${post.images}" var="imgUrl">
                    <div class="img-item">
                        <img src="${imgUrl}" alt=""/>
                    </div>
                </c:forEach>
            </div>
            <div class="handle-box clearfix">
                <p class="handle-p">
                    <span class="sprite sprite11"></span>
                    <span>${post.browserNum}</span>
                </p>

                <p class="handle-p" onclick="comment(${post.postId})">
                    <span class="sprite sprite12"></span>
                    <span style="display:inline-block;margin-top:2px;">${post.commentNum}</span>
                </p>

                <p class="handle-p great-box" onclick="doThumb(${post.postId})">
                    <c:choose>
                        <c:when test="${post.thisUserLike==true}">
                            <span id="greatClassId${post.postId}" class="sprite sprite14 great"></span>
                        </c:when>
                        <c:otherwise>
                            <span id="greatClassId${post.postId}" class="sprite sprite13 great"></span>
                        </c:otherwise>
                    </c:choose>
                    <span id="vbbs${post.postId}">${post.thumbNum}</span>
                    <span class="add">+1</span>
                </p>
            </div>
        </div>

    </c:if>

    <c:if test="${post.hasComment==1}">
        <%--普通贴且有评论--%>
        <div class="post-item mb80">
            <div class="test-header clearfix">
                <img class="test-img" src="${post.avatarUrl}" alt=""/>
                <img class="test-rank" src="${pageContext.request.contextPath }/microForum/images/header-rank.png"
                     alt=""/>

                <div class="test-text">
                    <p class="test-p">${post.userName}</p>

                    <p class="test-p"><span>${post.publishTime}</span></p>
                </div>
            </div>
            <div class="post-text" onclick="comment(${post.postId})">${post.content}</div>
            <div class="img-box clearfix">
                <c:forEach items="${post.images}" var="image">
                    <div class="img-item">
                        <img src="${image}" alt=""/>
                    </div>
                </c:forEach>

            </div>
            <div class="comment-box clearfix">
                <c:forEach items="${post.comments}" var="comment">
                    <div class="comment-item">
                        <img class="comment-img" src="${comment.avatarUrl}" alt="用户头像"/>

                        <div class="comment-text">
                            <p class="comment-name">${comment.nickName}</p>

                            <p class="comment-content">${comment.content}</p>
                        </div>
                    </div>
                </c:forEach>


                <div class="handle-box clearfix">
                    <p class="handle-p">
                        <span class="sprite sprite11"></span>
                        <span>${post.browserNum}</span>
                    </p>

                    <p class="handle-p" onclick="comment(${post.postId})">
                        <span class="sprite sprite12"></span>
                        <span style="display:inline-block;margin-top:2px;">${post.commentNum}</span>
                    </p>

                    <p class="handle-p great-box" onclick="doThumb(${post.postId})">
                        <c:choose>
                            <c:when test="${post.thisUserLike==true}">
                                <span id="greatClassId${post.postId}" class="sprite sprite14 great"></span>
                            </c:when>
                            <c:otherwise>
                                <span id="greatClassId${post.postId}" class="sprite sprite13 great"></span>
                            </c:otherwise>
                        </c:choose>
                        <span id="vbbs${post.postId}">${post.thumbNum}</span>
                        <span class="add">+1</span>
                    </p>
                </div>
            </div>
        </div>

    </c:if>

</c:forEach>

<footer class="footer">
    <div class="search-box" onclick="forward2Search()">
        <img class="search-btn" src="${pageContext.request.contextPath }/microForum/images/search-btn.png" alt=""/>
    </div>
    <div class="post-card">
        <a href="${pageContext.request.contextPath }/microForum/post-card.jsp"><img
                src="${pageContext.request.contextPath }/microForum/images/post-btn.png" alt=""/></a>
    </div>
    <div class="post-header">
        <img src="${existUser.avatar}">
    </div>
</footer>
</body>
<script>
    $(function(){
        var img = $(".img-item img");
        var width = img.width();
        img.height(width);
    });

</script>
<script type="text/javascript">
    function comment(postId) {
        window.location.href = "${pageContext.request.contextPath }/api/post/info?id=" + postId;
    }
    function doThumb(postId) {
        $.getJSON("${pageContext.request.contextPath }/api/thumb?id=" + postId, function (data) {
            var id = "vbbs" + postId;
            var greatId = "greatClassId" + postId;
            if (data.errorCode == "00000") {
                //add
                $("#" + id).html(data.thumbNumber);
                if (data.msg == "add") {
                    $("#" + greatId).attr("class", "sprite sprite13 great");
                } else {
                    $("#" + greatId).attr("class", "sprite sprite14 great");
                }
            } else if (data.errorCode == "10000") {
                alert("错误,请联系管理员");
                /*$("#" + id).html(data.thumbNumber);*/
            }
        });
    }

    function backToIndex() {
        var sectionId = ${sectionId}
                window.location.href = "${pageContext.request.contextPath }/api/index?sectionId=" + sectionId + "&cream=" + 0;
    }
    function forward2Search() {
        window.location.href = "${pageContext.request.contextPath }/api/toSearchPage";
    }
    function getPostBySection(sectionId) {

        var cream = $("#cream").val();

        window.location.href = "${pageContext.request.contextPath }/api/index?sectionId=" + sectionId + "&cream=" + cream;
    }

    function goToCream() {

        var sectionId = $("#sectionId").val();

        window.location.href = "${pageContext.request.contextPath }/api/index?sectionId=" + sectionId + "&cream=1";
    }

    //重新处理样式
    $(document).ready(function () {

        var cream = ${cream};

        //处理首页和精华的样式
        if (cream == 1) {
            $("#indexP").attr("class", "sort-nav");
            $("#creamP").attr("class", "sort-nav sort-active");
        }

        var sectionId = ${sectionId};
        var selectedSectionId = "section" + sectionId;
        $("#" + selectedSectionId).attr("class", "sort-item");
    });
</script>
</html>