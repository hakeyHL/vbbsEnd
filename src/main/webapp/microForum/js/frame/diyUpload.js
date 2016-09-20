/* 
 *	jQuery文件上传插件,封装UI,上传处理操作采用Baidu WebUploader;
 *	@Author 黑爪�?;
 */
(function ($) {

    $.fn.extend({
        /*
         *	上传方法 opt为参数配�?;
         *	serverCallBack回调函数 每个文件上传至服务端�?,服务端返回参�?,无论成功失败都会调用 参数为服务器返回信息;
         */
        diyUpload: function (opt, serverCallBack) {
            if (typeof opt != "object") {
                alert('参数错误!');
                return;
            }

            var $fileInput = $(this);
            var $fileInputId = $fileInput.attr('id');

            //组装参数;
            if (opt.url) {
                opt.server = opt.url;
                delete opt.url;
            }

            if (opt.success) {
                var successCallBack = opt.success;
                delete opt.success;
            }

            if (opt.error) {
                var errorCallBack = opt.error;
                delete opt.error;
            }

            //迭代出默认配�?
            $.each(getOption('#' + $fileInputId), function (key, value) {
                opt[key] = opt[key] || value;
            });

            if (opt.buttonText) {
                opt['pick']['label'] = opt.buttonText;
                delete opt.buttonText;
            }

            var webUploader = getUploader(opt);

            if (!WebUploader.Uploader.support()) {
                alert(' 上传组件不支持您的浏览器�?');
                return false;
            }

            //绑定文件加入队列事件;
            webUploader.on('fileQueued', function (file) {
                createBox($fileInput, file, webUploader);

            });

            //进度条事�?
            webUploader.on('uploadProgress', function (file, percentage) {
                var $fileBox = $('#fileBox_' + file.id);
                var $diyBar = $fileBox.find('.diyBar');
                $diyBar.show();
                percentage = percentage * 100;
                showDiyProgress(percentage.toFixed(2), $diyBar);

            });

            //全部上传结束后触�?;
            webUploader.on('uploadFinished', function () {
                $fileInput.next('.parentFileBox').children('.diyButton').remove();
            });
            //绑定发�?�至服务端返回后触发事件;
            webUploader.on('uploadAccept', function (object, data) {
                if (serverCallBack) serverCallBack(data);
            });

            //上传成功后触发事�?;
            webUploader.on('uploadSuccess', function (file, response) {
                var $fileBox = $('#fileBox_' + file.id);
                var $diyBar = $fileBox.find('.diyBar');
                $fileBox.removeClass('diyUploadHover');
                $diyBar.fadeOut(1000, function () {
                    $fileBox.children('.diySuccess').show();
                });
                if (successCallBack) {
                    successCallBack(response);
                }
            });

            //上传失败后触发事�?;
            webUploader.on('uploadError', function (file, reason) {
                var $fileBox = $('#fileBox_' + file.id);
                var $diyBar = $fileBox.find('.diyBar');
                showDiyProgress(0, $diyBar, '上传失败!');
                var err = '上传失败! 文件:' + file.name + ' 错误�?:' + reason;
                if (errorCallBack) {
                    errorCallBack(err);
                }
            });

            //选择文件错误触发事件;
            webUploader.on('error', function (code) {
                var text = '';
                switch (code) {
                    case  'F_DUPLICATE' :
                        text = '该文件已经被选择�?!';
                        break;
                    case  'Q_EXCEED_NUM_LIMIT' :
                        text = '上传文件数量超过限制!';
                        break;
                    case  'F_EXCEED_SIZE' :
                        text = '文件大小超过限制!';
                        break;
                    case  'Q_EXCEED_SIZE_LIMIT' :
                        text = '�?有文件�?�大小超过限�?!';
                        break;
                    case 'Q_TYPE_DENIED' :
                        text = '文件类型不正确或者是空文�?!';
                        break;
                    default :
                        text = '未知错误!';
                        break;
                }
                alert(text);
            });
        }
    });

    //Web Uploader默认配置;
    function getOption(objId) {
        /*
         *	配置文件同webUploader�?�?,这里只给出默认配�?.
         *	具体参照:http://fex.baidu.com/webuploader/doc/index.html
         */
        return {
            //按钮容器;
            pick: {
                id: objId,
                label: "点击选择图片"
            },
            //类型限制;
            accept: {
                title: "Images",
                extensions: "gif,jpg,jpeg,bmp,png",
                mimeTypes: "image/*"
            },
            //配置生成缩略图的选项
            thumb: {
                width: 170,
                height: 150,
                // 图片质量，只有type为`image/jpeg`的时候才有效�?
                quality: 70,
                // 是否允许放大，如果想要生成小图的时�?�不失真，此选项应该设置为false.
                allowMagnify: false,
                // 是否允许裁剪�?
                crop: true,
                // 为空的话则保留原有图片格式�??
                // 否则强制转换成指定的类型�?
                type: "image/jpeg"
            },
            //文件上传方式
            method: "POST",
            //服务器地�?;
            server: "",
            //是否已二进制的流的方式发送文件，这样整个上传内容php://input都为文件内容
            sendAsBinary: false,
            // �?起分片上传�?? thinkphp的上传类测试分片无效,图片丢失;
            chunked: true,
            // 分片大小
            chunkSize: 512 * 1024,
            //�?大上传的文件数量, 总文件大�?,单个文件大小(单位字节);
            fileNumLimit: 50,
            fileSizeLimit: 5000 * 1024,
            fileSingleSizeLimit: 500 * 1024
        };
    }

    //实例化Web Uploader
    function getUploader(opt) {

        return new WebUploader.Uploader(opt);
        ;
    }

    //操作进度�?;
    function showDiyProgress(progress, $diyBar, text) {

        if (progress >= 100) {
            progress = progress + '%';
            text = text || '上传完成';
        } else {
            progress = progress + '%';
            text = text || progress;
        }

        var $diyProgress = $diyBar.find('.diyProgress');
        var $diyProgressText = $diyBar.find('.diyProgressText');
        $diyProgress.width(progress);
        $diyProgressText.text(text);

    }

    //取消事件;
    function removeLi($li, file_id, webUploader) {
        webUploader.removeFile(file_id);
        if ($li.siblings('li').length <= 0) {
            $li.parents('.parentFileBox').remove();
        } else {
            $li.remove();
        }

    }

    //创建文件操作div;
    function createBox($fileInput, file, webUploader) {

        var file_id = file.id;
        var $parentFileBox = $fileInput.next('.parentFileBox');

        //添加父系容器;
        if ($parentFileBox.length <= 0) {

            var div = '<div class="parentFileBox"> \
						<ul class="fileBoxUl"></ul>\
					</div>';
            $fileInput.after(div);
            $parentFileBox = $fileInput.next('.parentFileBox');

        }

        //创建按钮
        if ($parentFileBox.find('.diyButton').length <= 0) {

            var div = '<div class="diyButton"> \
						<a class="diyStart" href="javascript:void(0)">�?始上�?</a> \
						<a class="diyCancelAll" href="javascript:void(0)">全部取消</a> \
					</div>';
            $parentFileBox.append(div);
            var $startButton = $parentFileBox.find('.diyStart');
            var $cancelButton = $parentFileBox.find('.diyCancelAll');

            //�?始上�?,暂停上传,重新上传事件;
            var uploadStart = function () {
                webUploader.upload();
                $startButton.text('暂停上传').one('click', function () {
                    webUploader.stop();
                    $(this).text('继续上传').one('click', function () {
                        uploadStart();
                    });
                });
            }

            //绑定�?始上传按�?;
            $startButton.one('click', uploadStart);

            //绑定取消全部按钮;
            $cancelButton.bind('click', function () {
                var fileArr = webUploader.getFiles('queued');
                $.each(fileArr, function (i, v) {
                    removeLi($('#fileBox_' + v.id), v.id, webUploader);
                });
            });

        }

        //添加子容�?;
        var li = '<li id="fileBox_' + file_id + '" class="diyUploadHover"> \
					<div class="viewThumb"></div> \
					<div class="diyCancel"></div> \
					<div class="diySuccess"></div> \
					<div class="diyFileName">' + file.name + '</div>\
					<div class="diyBar"> \
							<div class="diyProgress"></div> \
							<div class="diyProgressText">0%</div> \
					</div> \
				</li>';

        $parentFileBox.children('.fileBoxUl').append(li);

        //父容器宽�?;
        var $width = $('.fileBoxUl>li').length * 180;
        var $maxWidth = $fileInput.parent().width();
        $width = $maxWidth > $width ? $width : $maxWidth;
        $parentFileBox.width($width);

        var $fileBox = $parentFileBox.find('#fileBox_' + file_id);

        //绑定取消事件;
        var $diyCancel = $fileBox.children('.diyCancel').one('click', function () {
            removeLi($(this).parent('li'), file_id, webUploader);
        });

        if (file.type.split("/")[0] != 'image') {
            var liClassName = getFileTypeClassName(file.name.split(".").pop());
            $fileBox.addClass(liClassName);
            return;
        }

        //生成预览缩略�?;
        webUploader.makeThumb(file, function (error, dataSrc) {
            if (!error) {
                $fileBox.find('.viewThumb').append('<img src="' + dataSrc + '" >');
            }
        });
    }

    //获取文件类型;
    function getFileTypeClassName(type) {
        var fileType = {};
        var suffix = '_diy_bg';
        fileType['pdf'] = 'pdf';
        fileType['zip'] = 'zip';
        fileType['rar'] = 'rar';
        fileType['csv'] = 'csv';
        fileType['doc'] = 'doc';
        fileType['xls'] = 'xls';
        fileType['xlsx'] = 'xls';
        fileType['txt'] = 'txt';
        fileType = fileType[type] || 'txt';
        return fileType + suffix;
    }

})(jQuery);