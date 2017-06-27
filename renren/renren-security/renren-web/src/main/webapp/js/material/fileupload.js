(function($,vm){
	var $attachmentList = $('#attachment_list');
	var dragArea = 'drag-area';
	var removeCallbackFunc = "removeFile";
	
	var maxFileSize = '20000kb';
	var uploadURL = '../attachment/file/upload';
	var extensions = "jpg,gif,png,bmp,mp4,txt";
	
	//实例化一个plupload上传对象
	var option = { 
			browse_button : 'browse',
			url : uploadURL,
			flash_swf_url : '${rc.contextPath}/static/plugins/plupload/Moxie.swf',
			silverlight_xap_url : '${rc.contextPath}/static/plugins/plupload/Moxie.xap',
			drop_element : dragArea,
			max_file_size : maxFileSize,
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
					{ title : "素材文件", extensions : extensions }, 
					{ title : "RAR压缩文件", extensions : "zip" }
				],
				max_file_size : '20000kb', //最大只能上传100000kb的文件
				prevent_duplicates : true //不允许队列中存在重复文件
			}
	};
	
	vm.initUploader = function(){
		var uploader = new plupload.Uploader(option);
		uploader.init(); //初始化
		this.uploader = uploader;
		//绑定文件上传进度事件
		uploader.bind('UploadProgress',function(uploader,file){
			//$('#file-'+file.id+' .progress').css('width',file.percent + '%');//控制进度条
			$('#file-'+file.id+' .progress-text').text(file.percent + '%');//进度百分比
		});
		
		this.uploader.bind('FileUploaded',function(uploader,file,result){
			var fileDbIds = $.parseJSON(result.response);
			vm.appendAttachmentIds(file.id,fileDbIds);
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
				var html = '<li id="file-' + files[i].id +'" style="width: 135%;"><span class="file-name">' + file_name + '</span>'
				+'<span style="display: inline-block;float: right;">'
				+'<span class="progress-text"></span><button style="padding:4px" @click="'+removeCallbackFunc+'(\''+files[i].id+'\')" type="button" class="close">×</button></span></li>';
				
				var FileInfoItem = Vue.extend({
					   template:html,
					   methods: {
						   removeFile:function(fileId){
							   uploader.removeFile(fileId);
							   var $fileItem = $('#file-'+fileId);
							   $fileItem.remove();
							   var fileDbId = vm.fileItems[fileId];
							   if(fileDbId){
								   vm.material.attachments.splice($.inArray({id:fileDbId},vm.attachments),1);
								   vm.material.fileCount = vm.material.attachments.length;
							   }
						   }
					   }
				});
				
				$("#file-list").append((new FileInfoItem().$mount()).$el);
				//document.getElementById('file-list').appendChild((new FileInfoItem().$mount()).$el)
				/* !function(i) {
					previewImage(files[i], function(imgsrc) {
						$('#file-' + files[i].id).append('<img src="'+ imgsrc +'" />');
					})
				}(i); */
			}
		});
	}
	
	vm.destroyUploader = function(){
		if(this.uploader){
			this.uploader.destroy();
		}
		$("#file-list").empty();
	}
	
})(jQuery,/*VueJs对象*/vm)