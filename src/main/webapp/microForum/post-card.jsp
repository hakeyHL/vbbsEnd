<!DOCTYPE html>
<html lang="zh-cn">
<jsp:include page="jssdksignature.jsp"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script src="${pageContext.request.contextPath }/microForum/js/frame/jquery-1.12.1.min.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript">
    var arr = [];
    var serverArr = [];
    var isCheckSuccess = false;
    var syncUpload = function (localIds) {
        var localId = localIds.shift();
        if (localId != undefined) {
            wx.uploadImage({
                localId: localId,
                isShowProgressTips: 1,
                success: function (res) {
                    var serverId = res.serverId; // 返回图片的服务器端ID
                    serverArr.push(serverId);
                    //其他对serverId做处理的代码
                    if (localIds.length > 0) {
                        syncUpload(localIds);
                    } else {
                        //提交表单
                        for (var i = 0; i < serverArr.length; ++i) {
                            $("#myform").append("<input type='hidden' name='weixinserverid' value='" + serverArr[i] + "' />");
                        }
                        document.forms[0].submit();
                        $("#bybtn").attr("disabled", "disabled");
                    }
                }
            });
        } else {
            document.forms[0].submit();
            $("#bybtn").attr("disabled", "disabled");
        }
    };
    function toIndexPage() {
        window.location.href = "${pageContext.request.contextPath }/api/index";
    }
    function mysubmit() {
        var conlength = $("#content").val().length;
        if (conlength < 1) {
            $("#content").attr("placeholder", " 还是写点什么吧 ?");
            return;
        }
        /* var seclength = $("#sectionId").val().length;
        if (seclength < 1) {
            $("#content").attr("placeholder", "请选择一个版块.");
            return;
         }*/
        if (isCheckSuccess) {
            syncUpload(arr);
        } else {
            document.forms[0].submit();
            $("#bybtn").attr("disabled", "disabled");
        }
    }
    $(function () {
        wx.config({
            debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
            appId: "${jsSdkSignatureVoObj.appid}", // 必填，公众号的唯一标识
            timestamp: "${jsSdkSignatureVoObj.timestamp}", // 必填，生成签名的时间戳
            nonceStr: "${jsSdkSignatureVoObj.noncestr}", // 必填，生成签名的随机串
            signature: "${jsSdkSignatureVoObj.signature}",// 必填，签名，见附录1
            jsApiList: ["chooseImage", "uploadImage", "previewImage"] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
        });
        wx.ready(function () {
            wx.checkJsApi({
                jsApiList: ['chooseImage', "uploadImage", "previewImage"], // 需要检测的JS接口列表，所有JS接口列表见附录2,
                success: function (res) {
                    isCheckSuccess = true;
                    //验证成功之后才进行图片上传
                    $("#upload-box").on("click", ".upload-file:first", function () {
                        wx.chooseImage({
                            count: 9, // 默认9
                            sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
                            sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
                            success: function (res) {
                                var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
                                var img_len = $(".img-container").length;
                                if (img_len + localIds.length > 10) {
                                    layer.open({
                                        content: '最多只能上传10张图片！'
                                        , skin: 'msg'
                                        , time: 2 //2秒后自动关闭
                                        , offset: '200px'
                                    });

                                } else {
                                    for (var i = 0; i < localIds.length; ++i) {
                                        arr.push(localIds[i]);
                                        //在页面中展示图片
                                        var div = "<div class='img-container'><img class='preview' src='" + localIds[i] + "' /></div>";
                                        $(".upload-container").append(div);
                                    }
                                    $(".img-container").each(
                                            function (i, element) {
                                                element.onclick = function () {
                                                    wx.previewImage({
                                                        current: arr[i], // 当前显示图片的http链接
                                                        urls: arr // 需要预览的图片http链接列表
                                                    });
                                                };
                                            }
                                    );
                                }
                            }
                        });
                    });
                },
                fail: function (res) {
                    alert("当前客户端不支持 图片上传");
                }

            });
        });

    });
</script>
<head>
    <meta charset="UTF-8">
    <title>发帖</title>
    <meta name="wap-font-scale" content="no">
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/microForum/css/h5-all.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/microForum/js/frame/need/layer.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/microForum/css/post-card.css"/>

</head>
<body>
<form id="myform" onsubmit="return false" enctype="multipart/form-data"
      action="${pageContext.request.contextPath }/api/createPost" method="post">
    <div class="post-container">
        <div class="post-content">
            <textarea id="content" name="content" class="write-area" placeholder="写点什么发帖吧。。。"></textarea>

            <div class="upload-container clearfix">
                <div id="upload-box" class="upload-box">
                    <!-- <input class="upload-file" type="file" name="myFile" multiple="true"
                           accept="image/*;capture=camera" disabled="disabled"/> -->
                    <div class="upload-file"></div>
                    <p class="upload-btn">+</p>
                </div>
            </div>
        </div>
        <div class="sort-sifting clearfix">
            <div class="sort-item">全部</div>
            <input id="sectionId" name="sectionId" hidden="hidden">
            <c:forEach var="section" items="${sections}">
                <div class="sort-item">
                    <a href="#" onclick="change(${section.id})">${section.name}</a>
                </div>
                <script type="text/javascript">
                    function change(sec) {
                        $("#sectionId").attr("value", sec);
                    }
                </script>
            </c:forEach>
        </div>
    </div>
    <footer class="footer">
        <div class="footer-container">
            <div onclick="toIndexPage()" class="menu-btn"><span class="sprite sprite42"></span></div>
            <div>
                <button id="bybtn" class="post-btn" type="submit" onclick="mysubmit()">发帖</button>
            </div>
            <div class="post-header">
                <img src="${existUser.avatar}">
            </div>
        </div>
    </footer>
</form>
<script type="text/javascript"
        src="${pageContext.request.contextPath }/microForum/js/frame/jquery-1.12.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/microForum/js/frame/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/microForum/js/frame/diyUpload.js"></script>
</body>
</html>