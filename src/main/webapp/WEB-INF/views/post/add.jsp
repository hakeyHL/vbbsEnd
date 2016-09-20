<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
    String contextPath = request.getContextPath();
%>

<jsp:include page="../include/header.jsp"/>
<jsp:include page="../include/left.jsp"/>

<link rel="stylesheet" href="<%=contextPath%>/static/css/datetime/bootstrap-datetimepicker.min.css">


<div id="page-wrapper">

    <div class="col-lg-12" style="margin: 20px"></div>

    <form class="form-horizontal" action="<%=contextPath%>/post/add" id="post_form"enctype="multipart/form-data" method="POST" >

        <div class="form-group">
            <label  class="col-sm-3 control-label">版块</label>
            <div class="col-sm-2">
                <select name="sectionId" class="form-control" id="section">
                    <option value="">--请选择版块--</option>
                </select>
            </div>
        </div>

        <div class="form-group">
            <label  class="col-sm-3 control-label">标题</label>
            <div class="col-sm-5">
                <input type="text" name="title" class="form-control" value="${post.title}" >
            </div>
        </div>

        <div class="form-group">
            <label  class="col-sm-3 control-label">内容</label>
            <div class="col-sm-7">
                <textarea name="content"  class="span12 ckeditor m-wrap" rows="10">${post.content}</textarea>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-3 control-label">帖子状态</label>
            <div class="col-sm-7" style="margin-top: 8px">
                    <input type="radio" name="status" id="status_radio0" value="0"/>
                    普通
                    <input type="radio" name="status" id="status_radio1" value="1" />
                    精华
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-3 control-label">帖子类型</label>
            <div class="col-sm-7" style="margin-top: 8px">

                    <input type="radio" name="type" class="type_radio" id="type_radio0"  value="0"/>
                    讨论贴

                    <input type="radio" name="type" class="type_radio" id="type_radio1" value="1"  />
                    投票贴

            </div>
        </div>

        <div class="form-group expireTime" style="display: none">
            <label  class="col-sm-3 control-label">结束时间</label>
            <div class="col-sm-2">
                <input type="text" name="expireTime" id="expireTime" class="form-control datetimepicker" value="${post.expireTime}" >
            </div>
        </div>
        <script>
            $("#expireTime").datepicker({ dateFormat: 'yy-mm-dd' });
        </script>
        <div class="form-group">
            <div class="col-sm-offset-3 col-sm-9">
                <input type="hidden" name="id" value="${post.id}">
                <input type="hidden" name="top" value="${post.top}">
                <button type="submit" class="btn btn-primary" id="button_submit">确定</button>
            </div>
        </div>

    </form>


</div>

<script>


    $(function(){
        var sections = $.parseJSON('${sections}');
        $.each(sections, function(i, val){
            $("#section").append("<option value='"+val.id+"'>"+val.name+"</option>");
            if($('#section').val() == val.id){
                $("#section").val(val.name);
            }
        });

        var status = '${post.status}';
        if(status == 0 || status == '' || status == null){
            $('#status_radio0').attr("checked", true);
            $('#status_radio1').attr("checked", false);
        }else{
            $('#status_radio10').attr("checked", false);
            $('#status_radio1').attr("checked", true);
        }

        var type = '${post.type}';
        if(type == 0 || type == '' || type == null){
            $('#type_radio0').attr("checked", true);
            $('#type_radio1').attr("checked", false);
            $('.expireTime').hide();
        }else{
            $('#type_radio0').attr("checked", false);
            $('#type_radio1').attr("checked", true);
            $('.expireTime').show();
        }

        var type_radio = $('.type_radio');
        type_radio.click(function(){
            var val =$(this).val();
            if(val == '0'){
                $('.expireTime').hide();
            }else{
                $('.expireTime').show();
            }
        })

    });

</script>

<jsp:include page="../include/footer.jsp"/>
<script type="text/javascript" src="<%=contextPath%>/static/js/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/datetime-picker/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/datetime-picker/bootstrap-datetimepicker.zh-CN.js"></script>