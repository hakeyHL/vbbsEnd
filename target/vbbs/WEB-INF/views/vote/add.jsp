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

    <!-- 导入结果提示 -->
    <div class="modal fade in" id="import_result" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabel" style="display: none;top:20%">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title" id="myModalLabel">导入结果</h4>
                </div>
                <div class="modal-body">
                    <ul>
                        <li>本次导入共<span id="totalRows"></span>条</li>
                        <li>导入成功<span id="successRows"></span></li>
                        <li>失败数量<span id="failRows"></span></li>
                    </ul>
                </div>
                <div class="modal-footer">
                    <button id="confirm_button" type="button" class="btn btn-primary">确定</button>
                </div>
            </div>
        </div>
    </div>

    <div class="col-lg-12" style="margin: 20px"></div>

    <form class="form-horizontal" action="<%=contextPath%>/vote/add" id="candidate_form" enctype="multipart/form-data"  method="POST" >

        <div class="form-group">
            <label  class="col-sm-3 control-label">投票帖</label>
            <div class="col-sm-2">
                <select name="postId" class="form-control" id="votePost">
                    <option value="">--请选择投票帖--</option>
                </select>
            </div>
        </div>

        <div class="form-group">
            <label  class="col-sm-3 control-label">介绍</label>
            <div class="col-sm-7">
                <textarea name="instructions" class="form-control" rows="3">${vote.instructions}</textarea>
            </div>
        </div>

        <div class="form-group">
            <label  class="col-sm-3 control-label">候选人</label>
            <div class="col-sm-7">
                <input type="file" name="candidateFile" id="candidateFile" class="file-loading" />
            </div>
        </div>


        <div class="form-group">
            <div class="col-sm-offset-3 col-sm-9">
                <button class="btn btn-primary" id="button_import">导入</button>
            </div>
        </div>

    </form>


</div>

<script>

    $(function(){
        var votePosts = $.parseJSON('${votePosts}');
        $.each(votePosts, function(i, val){
            $("#votePost").append("<option value='"+val.id+"'>"+val.title+"</option>");
        });

        $('#button_import').click(function(){
            $("#candidate_form").ajaxSubmit({
                //定义返回JSON数据，还包括xml和script格式
                dataType:'json',
                beforeSend: function() {
                    //表单提交前做表单验证
                },
                success: function(data) {
                    if(data.code == 200){
                        $('#import_result').modal('show');
                        $("#confirm_button").click(function(){
                            $('#import_result').modal('hide');
                            window.location.href = '<%=contextPath%>/vote/list';
                        });
                    }else{
                        BootstrapDialog.show({
                            title: '导入失败',
                            message: '请检查Excel格式，重新导入!'
                        });
                    }


                }
            });
        })

    });

</script>

<jsp:include page="../include/footer.jsp"/>
