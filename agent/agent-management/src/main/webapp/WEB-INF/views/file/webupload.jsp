<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/commons/basejs.jsp" %>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<!--引入CSS-->
		<link rel="stylesheet" type="text/css" href="${staticPath }/static/webuploader/webuploader.css">
		<!--引入JS-->
		<script type="text/javascript" src="${staticPath }/static/webuploader/webuploader.js"></script>
		<title>xx</title>
	</head>
	<body>
		<form id="templeteForm" action="">
			<input type="hidden" id="batchId" name="batchId">
		</form>
		<div id="uploader" class="webuploader-container">
		    <!--用来存放文件信息-->
		    <div id="thelist" class="uploader-list"></div>
		    <div class="btns">
		        <div id="picker">选择文件</div>
		        <button id="ctlBtn" class="btn btn-default">开始上传</button>
		    </div>
		</div>
	</body>
</html>
<script type="text/javascript">
(function($){
	var $batchId = $('#batchId');
	var $list = $('#thelist'),
	$li = $('li .remove-this'),
	$btn = $('#ctlBtn'),
	state = 'pending',
	uploader;
	uploader = WebUploader.create({

	    // swf文件路径
	    swf:  '${staticPath }/static/webuploader/Uploader.swf',

	    // 文件接收服务端。
	    server: '${staticPath }/file/upload',

	    // 选择文件的按钮。可选。
	    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
	    pick: '#picker',

	    // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
	    resize: false,
	 	// 只允许选择图片文件。
	    accept: {
	        title: 'Images',
	        extensions: 'gif,jpg,jpeg,bmp,png',
	        mimeTypes: 'image/*'
	    }
	});


	uploader.on( 'all', function( type ) {
	    if ( type === 'startUpload' ) {
	        state = 'uploading';
	    } else if ( type === 'stopUpload' ) {
	        state = 'paused';
	    } else if ( type === 'uploadFinished' ) {
	        state = 'done';
	    }

	    if ( state === 'uploading' ) {
	        $btn.text('暂停上传');
	    } else {
	        $btn.text('开始上传');
	    }
	});

	$btn.on( 'click', function() {
	    if ( state === 'uploading' ) {
	        uploader.stop();
	    } else {
	        uploader.upload();
	    }
	});

	//当有文件被添加进队列的时候
	uploader.on( 'fileQueued', function( file ) {
		$list.append( '<div id="' + file.id + '" class="item">' +
	        '<h4 class="info">' + file.name + '</h4>' +
	        '<p class="state">等待上传...</p>' +
	        '<li class="remove-this">删除</li>' +
	    '</div>' );
		$('#'+file.id).find('.remove-this').on('click', function() {
		    uploader.removeFile( file ,true);
		    $('#'+file.id).remove();
		})
	});

	//文件上传过程中创建进度条实时显示。
	uploader.on( 'uploadProgress', function( file, percentage ) {
	    var $li = $( '#'+file.id ),
	        $percent = $li.find('.progress .progress-bar');

	    // 避免重复创建
	    if ( !$percent.length ) {
	        $percent = $('<div class="progress progress-striped active">' +
	          '<div class="progress-bar" role="progressbar" style="width: 0%">' +
	          '</div>' +
	        '</div>').appendTo( $li ).find('.progress-bar');
	    }

	    $li.find('p.state').text('上传中');

	    $percent.css( 'width', percentage * 100 + '%' );
	});

	uploader.on( 'uploadSuccess', function( file,response ) {
	    $( '#'+file.id ).find('p.state').text('已上传');
	    var batchId = $batchId.val();
	    if(batchId)
	    	batchId = batchId + "," + response._raw;
	    else
	    	batchId = response._raw;
	    $batchId.val(batchId);
	});

	uploader.on( 'uploadError', function( file ) {
	    $( '#'+file.id ).find('p.state').text('上传出错');
	});

	uploader.on( 'uploadComplete', function( file ) {
	    $( '#'+file.id ).find('.progress').fadeOut();
	});	
})(jQuery);

</script>
