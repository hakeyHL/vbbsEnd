<!DOCTYPE html>
<html lang="zh-cn">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<head>
    <meta charset="UTF-8">
    <title>帖子正文</title>
    <meta name="wap-font-scale" content="no">
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/microForum/css/h5-all.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/microForum/css/post-text.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/microForum/css/reply.css?233423253523445234"/>
</head>
<body>
<div class="from-box">
    <span class="from-tit">来自：</span>
    <span class="from-who">${postVo.section}</span>
    <span class="sprite sprite51 right-btn"></span>
</div>
<div class="test-box">
    <div class="test-header clearfix">
        <img class="test-img" src="${postVo.avatarUrl}" alt=""/>
        <img class="test-rank" src="${pageContext.request.contextPath }/microForum/images/header-rank.png" alt=""/>

        <div class="test-text">
            <p class="test-p">${postVo.userName}</p>

            <p class="test-p"><span>${postVo.publishTime}</span></p>
        </div>
    </div>
    <div class="test-content">
        <p class="test-tit">${postVo.content}</p>

        <div class="img-box clearfix">
            <c:forEach items="${postVo.images}" var="image">
                <div class="img-item">
                    <img src="${image}" alt=""/>
                </div>
            </c:forEach>
        </div>
        <c:if test="${postVo.votePost==1}">
            <div class="question-box clearfix" onclick="votPostInfo(${postVo.postId})">
                <img class="question-img" src="${pageContext.request.contextPath }/microForum/images/test-img.png"
                     alt=""/>

                <div class="question-text">
                    <p class="question-p">${postVo.voteDescription}</p>

                    <p class="question-p"><span>人数 ${postVo.voteNum} 人</span>
                    <span>
                         <c:choose>
                             <c:when test="${postVo.status==0}">
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
        </c:if>

        <div class="handle-box clearfix">
            <p class="handle-p">
                <span class="btn-info">浏览</span>
                <span>${postVo.browserNum}</span>
            </p>

            <p class="handle-p">
                <span class="btn-info">留言</span>
                <span style="display:inline-block;margin-top:2px;">${postVo.commentNum}</span>
            </p>

            <p class="handle-p" onclick="doThumb(${postVo.postId})">
                <span class="btn-info">点赞</span>
                <span id="vbbsComment">${postVo.thumbNum}</span>
            </p>
        </div>
    </div>
</div>
<div class="reply-box">
    <div class="reply-head clearfix">
        <p class="rhead-p"><span id="defaultSort" class="rhead-span rhead-active"
                                 onclick="addOrdeleteComment(${postVo.postId},10,0,0)">全部回复</span></p>

        <p class="rhead-p" onclick="addOrdeleteComment(${postVo.postId},10,0,1)"><span
                id="onlyonley" class="rhead-span"><span class="sprite sprite54"></span>只看楼主</span></p>

        <p class="rhead-p" onclick="addOrdeleteComment(${postVo.postId},10,0,2)"><span
                id="revers" class="rhead-span"><span class="sprite sprite54"></span>倒序排序</span></p>
    </div>
    <div class="reply-content" id="replyComments">
        <c:forEach items="${postVo.comments}" varStatus="step" var="comment">
            <div class="reply-item">
                <div class="item-head clearfix">
                    <img src="${comment.avatarUrl}" alt=""/>
                    <span class="item-name">${comment.nickName}</span>
                    <span class="item-floor">${step.count}楼</span>
                </div>
                <div class="item-content">${comment.content}</div>
                <div class="item-foot clearfix">
                    <span class="item-time">${comment.publishTime}</span>
                    <span class="sprite sprite12 message"></span>
                    <span class="item-delate"
                          onclick="addOrdeleteComment(${postVo.postId},0,${comment.commentId},0)">删除</span>
                </div>
            </div>
        </c:forEach>

    </div>

    <div class="reply-content" style="text-align:center">
        <div class="reply-item">
            <textarea id="commentSub" class="write-area" placeholder="写点什么发帖吧。。。"></textarea>
            <button class="submit" type="button" onclick="addOrdeleteComment(${postVo.postId},1,0)">发表</button>
        </div>
    </div>

</div>
<footer class="footer">
    <div class="footer-container">
        <div onclick="toIndexPage()" class="menu-btn"><span class="sprite sprite42"></span></div>
        <div class="post-header" onclick="doThumb(${postVo.postId})">
            <span style="margin:9px 12px;" class="sprite sprite61"></span>
        </div>
    </div>
</footer>
<script type="text/javascript" src="/microForum/js/frame/jquery-1.12.1.min.js"></script>
<script type="text/javascript">
    function addOrdeleteComment(postId, type, commentId, sort) {
        if (sort == 1) {
            $("#onlyonley").attr("class", "rhead-span rhead-active");
            $("#revers").attr("class", "rhead-span");
            $("#defaultSort").attr("class", "rhead-span");
        } else if (sort == 2) {
            $("#onlyonley").attr("class", "rhead-span");
            $("#revers").attr("class", "rhead-span rhead-active");
            $("#defaultSort").attr("class", "rhead-span");
        } else {
            $("#onlyonley").attr("class", "rhead-span");
            $("#revers").attr("class", "rhead-span");
            $("#defaultSort").attr("class", "rhead-span rhead-active");
        }
        var content = $("#commentSub").val();
        $("#commentSub").val("");
        $.post("${pageContext.request.contextPath }/api/addOrDeleteComment", {
            "sort": sort,
            "id": commentId,
            "type": type,
            "postId": postId,
            "content": content
        }, function (data) {
            if (data.errorCode == "00000") {
                $("#replyComments").empty();
                var daList = data.dataList;
                var i = 1;
                $.each(daList, function () {
                    $("#replyComments").append(
                            "<div class=\"reply-item\">" +
                            "<div class=\"item-head clearfix\">" +
                            "<img src=\"" + this.avatarUrl + "\" alt=\"\"/>" +
                            "<span class=\"item-name\">" + this.nickName + "</span>" +
                            "<span class=\"item-floor\">" + i + "楼" + "</span>" +
                            "</div>" +
                            "<div class=\"item-content\">" + this.content + "</div>" +
                            "<div class=\"item-foot clearfix\">" +
                            "<span class=\"item-time\">" + this.publishTime + "</span>" +
                            "<span class=\"sprite sprite12 message\"></span>" +
                            "<span class=\"item-delate\" onclick=\"addOrdeleteComment(" +
                            postId +
                            ",0," +
                            this.commentId +
                            ",0" +
                            ")\" >删除</span>" +
                            "</div>" +
                            "</div>"
                    )
                    ;
                });
                i = i + 1;
            } else if (data.errorCode == "10000") {
                alert("您无权删除此贴!");
            }
        }, "json");
    }
    function votPostInfo(postId) {
        window.location.href = "${pageContext.request.contextPath }/api/post/info?id=" + postId + "&toVotePage=1";
    }

    function toIndexPage() {
        window.location.href = "${pageContext.request.contextPath }/api/index";
    }

    function doThumb(postId) {
        $.getJSON("${pageContext.request.contextPath }/api/thumb?id=" + postId, function (data) {
            if (data.errorCode == "00000") {
                //add
                $("#vbbsComment").html(data.thumbNumber);
            } else if (data.errorCode == "10000") {
                $("#vbbsComment").html(data.thumbNumber);
            }
        });
    }
</script>
</body>
</html>