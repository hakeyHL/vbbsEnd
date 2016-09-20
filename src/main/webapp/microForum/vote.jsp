<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
    String contextPath = request.getContextPath();
    String basePath  = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
%>


<head>
    <meta charset="UTF-8">
    <title>投票</title>
    <meta name="wap-font-scale" content="no">
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/microForum/css/h5-all.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/microForum/css/vote.css"/>
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
        <img class="test-rank" src="${pageContext.request.contextPath}/microForum/images/header-rank.png" alt=""/>

        <div class="test-text">
            <p class="test-p">${postVo.userName}</p>

            <p class="test-p"><span>${postVo.publishTime}</span><%--<span>14:24</span>--%></p>
        </div>
    </div>
    <div class="test-content">
        <p class="test-tit">${postVo.content}</p>

        <p class="vote-ex">投票说明：</p>

        <p class="vote-type">${postVo.voteDescription}</p>
    </div>
    <div class="vote-state clearfix">
        <p class="state-p">当前状态： <span class="state-span">>
            <c:choose>
                <c:when test="${postVo.status==0}">
                    正在进行
                </c:when>
                <c:otherwise>
                    已经结束
                </c:otherwise>
            </c:choose>
        </span></p>

        <p class="people-p">已有 <span class="people-span">${postVo.voteNum}人</span> 投票</p>
    </div>
</div>
<div class="vote-content clearfix">

    <c:forEach items="${candidates}" var="candidate">

    <div class="vote-item">
        <div class="vote-box">
            <div class="vote-head">

                <div class="head-img">
                    <img src="<%=basePath%>${candidate.avatar}" alt=""/>
                </div>

                <div class="vote-name">
                    ${candidate.name}
                </div>
            </div>

            <div class="vote-info">
                    ${candidate.introduction}
            </div>

            <div class="vote-count">
                <span class="count-span">${candidate.votes}</span>
                <span>票</span>
            </div>

            <div class="vote-btn-contain">
                <button class="vote-btn">
                    <span class="unsel">投我一票</span>
                    <span class="sel sprite sprite24"></span>
                </button>
            </div>
        </div>
    </div>

    </c:forEach>

    <footer class="footer">
        <input type="hidden" id="vote_id" value="${postVo.voteId}"/>
        <input type="hidden" id="names"/>
        <button class="vote-submit" onclick="vote()">投票</button>
    </footer>

    <%--<div class="vote_success" style="display: none">
        投票成功，<a href="${pageContext.request.contextPath}/microForum/egg.jsp">点击进入砸金蛋</a>
    </div>--%>

</div>


<script type="text/javascript" src="${pageContext.request.contextPath}/microForum/js/frame/jquery-1.12.1.min.js"></script>
<script src="${pageContext.request.contextPath}/microForum/js/vote.js?_=1468217384575"></script>

</body>

<script>

    var userVotes = '${userVotes}';
    var voteJson = $.parseJSON(userVotes);
    $(function(){
        if(voteJson.length > 0){
             $('.vote-submit').unbind('click');
             var voteBtns = $('.vote-btn');
            $(".vote-submit").attr("disabled",true);
            $(".vote-submit").css("background-color","#ddd");
            $(".vote-btn").attr("disabled",true);
             $.each(voteBtns, function(i, val){
                 var _this = $(val);
                 var name = _this.closest('.vote-box').find('.vote-name').html();
                 name = name.replace(/\s+/g,"");
                 $.each(voteJson, function(i, vote){
                     var voteName = vote.name;
                     if(name == voteName){
                         _this.find(".unsel").hide();
                         _this.find(".sel").show();
                         _this.addClass('btn-sel');
                     }

                 });


             });


         }
     });

    function vote(){

        var arr = [];
        var btnsels = $('.btn-sel');
        $.each(btnsels, function(i, val){
            var _this = $(val), obj = _this.find('.sel');
            var name = _this.closest('.vote-box').find('.vote-name').html();
            if(obj.is(':visible')){
                arr.push(name);
            }
        });

        if(arr.length == 0){
            alert("请选择一个候选人！");
        }

        var data = {voteId:$('#vote_id').val(), names:arr};

        $(".vote-submit").attr("disabled",true);
        $(".vote-submit").css("background-color","#ddd");

        $.ajax({
            url: '<%=contextPath%>/vote/submit',
            type: 'POST',
            data : data,
            dataType : 'json',
            success: function(result) {
                if(result.code == 200){
                    window.location.href = '<%=contextPath%>/egg/info?postId=' + '${postVo.postId}';
                }

            },
            error: function(xhr, textStatus, errorThrown){
                alert('未知错误，请联系管理员');
            }
        });
    }
</script>
