/**
 * Created by Administrator on 2016/9/5.
 */
    //多图片上传路径数组
var arr = [];
$(function() {
	$("#upload-box").on("change",".upload-file:first",function(){
        var $file = $(this);
        var fileObj = $file[0];
        var windowURL = window.URL || window.webkitURL;
        var dataURL;
        //console.log($(this).val());
        for(var i=0;i<fileObj.files.length;++i){
        	var singleFilename=fileObj.files[i].name;
        	var extStart = singleFilename.lastIndexOf(".");
            var ext = singleFilename.substring(extStart,singleFilename.length).toUpperCase();
            if(ext!=".BMP"&&ext!=".PNG"&&ext!=".GIF"&&ext!=".JPG"&&ext!=".JPEG"){
                layer.open({
                    content: '图片限于bmp,png,gif,jpeg,jpg格式！'
                    ,skin: 'msg'
                    ,time: 2 //2秒后自动关闭
                    ,offset:'200px'
                });
                return false;
            }
        }
        var img_len = $(".img-container").length;
        if(img_len+fileObj.files.length>10){
        	layer.open({
                content: '最多只能上传10张图片！'
                ,skin: 'msg'
                ,time: 2 //2秒后自动关闭
                ,offset:'200px'
            });
            return false;
        }
        for(var i=0;i<fileObj.files.length;++i){
        	dataURL = windowURL.createObjectURL(fileObj.files[i]);
        	//将图片插入到DOM中
            var div = "<div class='img-container'><img class='preview' src='"+dataURL+"' /></div>";
            $(".upload-container").append(div);
        }
        $(this).css("display","none");
        $(this).before('<input  class="upload-file" type="file" name="myFile" multiple="true" accept="image/*;capture=camera" />');
	});


    $('#write-area').bind('focus',function(){
        $('.footer').css('position','static');
        //或者$('#viewport').height($(window).height()+'px');
    }).bind('blur',function(){
        $('.footer').css({'position':'fixed','bottom':'0'});
        //或者$('#viewport').height('auto');
    });

/*   $(".upload-file").change(function() {

        if(fileObj && fileObj.files && fileObj.files[0]){
            dataURL = windowURL.createObjectURL(fileObj.files[0]);
            //$img.attr('src',dataURL);

            //判断上传的文件是否为图片格式
            var filepath = $("input[name='myFile']").val();
            var extStart = filepath.lastIndexOf(".");
            var ext = filepath.substring(extStart,filepath.length).toUpperCase();
            if(ext!=".BMP"&&ext!=".PNG"&&ext!=".GIF"&&ext!=".JPG"&&ext!=".JPEG"){
                layer.open({
                    content: '图片限于bmp,png,gif,jpeg,jpg格式！'
                    ,skin: 'msg'
                    ,time: 2 //2秒后自动关闭
                    ,offset:'200px'
                });
                return false;
            };
           //console.log($(".img-container").length);
            var img_len = $(".img-container").length;
            if(img_len >= 8){
                layer.open({
                    content: '最多只能上传8张图片！'
                    ,skin: 'msg'
                    ,time: 2 //2秒后自动关闭
                    ,offset:'200px'
                });
                return false;
            }
            //将图片插入到DOM中
            var div = "<div class='img-container'><img class='preview' src='"+dataURL+"' /></div>";
            $(".upload-container").append(div);

            arr.push($(".upload-file").val());


        }else{
            dataURL = $file.val();
            var imgObj = document.getElementById("preview");
            // 两个坑:
            // 1、在设置filter属性时，元素必须已经存在在DOM树中，动态创建的Node，也需要在设置属性前加入到DOM中，先设置属性在加入，无效；
            // 2、src属性需要像下面的方式添加，上面的两种方式添加，无效；
            //imgObj.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
            //imgObj.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = dataURL;

        }
    });*/
});

//$(".post-btn").click(function(){
//    console.log(arr);
//    console.log($(".upload-file").val());
//})