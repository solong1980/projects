<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>plupload</title>

<link type="text/css" rel="stylesheet" href="${staticPath }/static/jquery-ui/css/jquery-ui.min.css" media="screen" />
<link type="text/css" rel="stylesheet" href="${staticPath }/static/plupload/jquery.ui.plupload/css/jquery.ui.plupload.css" media="screen" />
<%-- <link type="text/css" rel="stylesheet" href="${staticPath }/static/plupload/jquery.plupload.queue/css/jquery.plupload.queue.css"  media="screen">
  --%>
<script src="${staticPath }/static/easyui/jquery.min.js"></script>
<script src="${staticPath }/static/jquery-ui/jquery-ui.min.js"></script>
<script src="${staticPath }/static/plupload/moxie.min.js"></script>
<script src="${staticPath }/static/plupload/plupload.full.min.js"></script>
<script src="${staticPath }/static/plupload/jquery.ui.plupload/jquery.ui.plupload.min.js"></script>
<script src="${staticPath }/static/plupload/jquery.plupload.queue/jquery.plupload.queue.min.js"></script>
<script src="${staticPath }/static/plupload/i18n/zh_CN.js"></script>

<style>
body {
	font-size: 12px;
}

body, p, div {
	padding: 0;
	margin: 0;
}

.wraper {
	padding: 30px 0;
}

.btn-wraper {
	text-align: center;
}

.btn-wraper input {
	margin: 0 10px;
}

#file-list {
	width: 350px;
	margin: 20px auto;
}

#file-list li {
	margin-bottom: 10px;
}

.file-name {
	line-height: 30px;
}

.progress {
	height: 4px;
	font-size: 0;
	line-height: 4px;
	background: orange;
	width: 0;
}

.tip1 {
	text-align: center;
	font-size: 14px;
	padding-top: 10px;
}

.tip2 {
	text-align: center;
	font-size: 12px;
	padding-top: 10px;
	color: #b00
}

.catalogue {
	position: fixed;
	_position: absolute;
	_width: 200px;
	left: 0;
	top: 0;
	border: 1px solid #ccc;
	padding: 10px;
	background: #eee
}

.catalogue a {
	line-height: 30px;
	color: #0c0
}

.catalogue li {
	padding: 0;
	margin: 0;
	list-style: none;
}

#drag-area{ border: 1px solid #ccc; height: 50px; line-height: 50px; text-align: center; color: #aaa; width: 600px; margin: 10px auto;}

</style>
</head>
<body>
	<p class="tip1">模板文件上传</p>
	
	<form id="templeteForm" action="">
		<input type="hidden" id="batchId" name="batchId">
	</form>
	
	<div class="wraper">
		<div class="btn-wraper">
			<input type="button" value="选择文件..." id="browse" />
			<input type="button" value="开始上传" id="upload-btn" />
		</div>
		<p id="drag-area">把要上传的文件拖放到这里(请使用支持html5的浏览器)</p>
		<ul id="file-list">

		</ul>
	</div>
	<script>
		var $batchId = $('#batchId');
		var uploader = new plupload.Uploader({ //实例化一个plupload上传对象
			browse_button : 'browse',
			url : '${staticPath }/file/upload',
			flash_swf_url : '${staticPath }/static/plupload/Moxie.swf',
			silverlight_xap_url : '${staticPath }/static/plupload/Moxie.xap',
			drop_element : 'drag-area',
			max_file_size : '1kb',
			chunk_size: '1mb',
			rename: true,
			sortable: true,
			dragdrop: true,
			views: {
				list: true,
				thumbs: true, // Show thumbs
				active: 'thumbs'
			},
			filters : {
				mime_types : [ //只允许上传图片文件和rar压缩文件
					{ title : "图片文件", extensions : "jpg,gif,png,bmp" }, 
					{ title : "RAR压缩文件", extensions : "zip" }
				],
				max_file_size : '10kb', //最大只能上传100kb的文件
				prevent_duplicates : true //不允许队列中存在重复文件
			}
		});
		uploader.init(); //初始化

		//绑定文件上传进度事件
		uploader.bind('UploadProgress',function(uploader,file){
			$('#file-'+file.id+' .progress').css('width',file.percent + '%');//控制进度条
		});
		uploader.bind('FileUploaded',function(uploader,file,result){
			var batchId = $batchId.val();
		    if(batchId)
		    	batchId = batchId + "," + result.response;
		    else
		    	batchId = result.response;
		    $batchId.val(batchId);
		});
		
		/* uploader.on( 'uploadSuccess', function( file,response ) {
		    $( '#'+file.id ).find('p.state').text('已上传');
		    var batchId = $batchId.val();
		    if(batchId)
		    	batchId = batchId + "," + response._raw;
		    else
		    	batchId = response._raw;
		    $batchId.val(batchId);
		}); */
		
		//上传按钮
		$('#upload-btn').click(function(){
			uploader.start(); //开始上传
		});
		
		//绑定文件添加进队列事件
		uploader.bind('FilesAdded',function(uploader, files) {
			for (var i = 0, len = files.length; i < len; i++) {
				var file_name = files[i].name; //文件名
				//构造html来更新UI
				var html = '<li id="file-' + files[i].id +'"><p class="file-name">'
					+ file_name
					+ '</p><p class="progress"></p></li>';
				$(html).appendTo('#file-list');
				/* !function(i) {
					previewImage(files[i], function(imgsrc) {
						$('#file-' + files[i].id).append('<img src="'+ imgsrc +'" />');
					})
				}(i); */
			}
		});

		//plupload中为我们提供了mOxie对象
		//有关mOxie的介绍和说明请看：https://github.com/moxiecode/moxie/wiki/API
		//如果你不想了解那么多的话，那就照抄本示例的代码来得到预览的图片吧
		function previewImage(file, callback) {//file为plupload事件监听函数参数中的file对象,callback为预览图片准备完成的回调函数
			if (!file || !/image\//.test(file.type))
				return; //确保文件是图片
			if (file.type == 'image/gif') {//gif使用FileReader进行预览,因为mOxie.Image只支持jpg和png
				var fr = new mOxie.FileReader();
				fr.onload = function() {
					callback(fr.result);
					fr.destroy();
					fr = null;
				}
				fr.readAsDataURL(file.getSource());
			} else {
				var preloader = new mOxie.Image();
				preloader.onload = function() {
					preloader.downsize(300, 300);//先压缩一下要预览的图片,宽300，高300
					var imgsrc = preloader.type == 'image/jpeg' ? preloader
							.getAsDataURL('image/jpeg', 80) : preloader
							.getAsDataURL(); //得到图片src,实质为一个base64编码的数据
					callback && callback(imgsrc); //callback传入的参数为预览图片的url
					preloader.destroy();
					preloader = null;
				};
				preloader.load(file.getSource());
			}
		}
	</script>
</body>
</html>