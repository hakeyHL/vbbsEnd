<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
    String contextPath = request.getContextPath();
%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>砸金蛋</title>
    <meta name="wap-font-scale" content="no">
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link rel="stylesheet" href="css/h5-all.css"/>
    <link rel="stylesheet" href="css/egg.css"/>
</head>
<body>
<div class="egg-header"></div>
<div class="egg-container">
    <div class="egg-box">
        <div class="egg-whole"></div>
        <div class="egg-break"></div>
        <div class="egg-spark"></div>
        <div class="hammer"></div>
    </div>
</div>

<script type="text/javascript" src="js/frame/jquery-1.12.1.min.js"></script>
<script type="text/javascript" src="js/frame/jquery.rotate.min.js"></script>
<script type="text/javascript" src="js/frame/layer.js"></script>
<script>
    $(function(){
        $('.egg-box').rotate({
            bind: {
                click: function(){
                    $(".hammer").show();
                    $(".hammer").rotate({
                        angle: 45,
                        animateTo: -45,
                        duration:1000,
                        easing: $.easing.easeInOutExpo
                    });

                    $.ajax({
                        url: '<%=contextPath%>/egg/brokeEgg',
                        type: 'POST',
                        success: function(result) {
                            if(result.code == 200){
                                hammer(result.map.prize);
                            }

                        },
                        error: function(xhr, textStatus, errorThrown){
                            layer.open({
                                content:  '发生未知错误，请联系管理员'
                                ,skin: 'msg'
                                ,time: 5 //2秒后自动关闭
                                ,offset:'200px'
                            });
                        }
                    });
                    $(this).unbind('click');

                }
            }

        });
    });
    function hammer(message){
        setTimeout(function(){
            $(".hammer").hide();
            $(".egg-break").show();
            $(".egg-spark").show();
            $(".egg-whole").hide();
            layer.open({
                content:  message
                ,skin: 'msg'
                ,time: 5 //2秒后自动关闭
                ,offset:'200px'
            });
        },1000);
    }
</script>

</body>
</html>