(function($){
	var $attachmentList = $('#attachment_list');
	var uploader = new plupload.Uploader({ //实例化一个plupload上传对象
		browse_button : 'browse',
		url : '../attachment/file/upload',
		flash_swf_url : '${rc.contextPath}/static/plugins/plupload/Moxie.swf',
		silverlight_xap_url : '${rc.contextPath}/static/plugins/plupload/Moxie.xap',
		drop_element : 'drag-area',
		max_file_size : '20000kb',
		chunk_size: '0',
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
				{ title : "素材文件", extensions : "jpg,gif,png,bmp,mp4,txt" }, 
				{ title : "RAR压缩文件", extensions : "zip" }
			],
			max_file_size : '20000kb', //最大只能上传100000kb的文件
			prevent_duplicates : true //不允许队列中存在重复文件
		}
	});
	uploader.init(); //初始化

	//绑定文件上传进度事件
	uploader.bind('UploadProgress',function(uploader,file){
		$('#file-'+file.id+' .progress').css('width',file.percent + '%');//控制进度条
	});
	
	uploader.bind('FileUploaded',function(uploader,file,result){
		vm.appendAttachmentIds($.parseJSON(result.response));
		/* $.each($.parseJSON(result.response),function(index,item){
			debugger
			var $inputFileId = $('<input type="hidden" v-model="material.attachments.id" value="'+item+'"/>');
			$attachmentList.append($inputFileId);
		}) */
		/* var batchId = $batchId.val();
	    if(batchId)
	    	batchId = batchId + "," + result.response;
	    else
	    	batchId = result.response;
	    $batchId.val(batchId); */
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
})(jQuery)