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

</style>
</head>
<body>
	<p class="tip1">模板文件上传</p>
	
	<form id="templeteForm" action="">
		<input type="hidden" id="batchId" name="batchId">
		<table>
			<tr></tr>
			<tr>
				<td><label>上传文件</label></td><td><button id="upFile" onclick="javascript:return false;">Upload</button></td>
			</tr>
			<tr>
				<td colspan="2">
					<ul id="file-list">
					</ul>
				</td>
			</tr>
		</table>		
	</form>
	
	<div id="uploader">
		<p>Your browser doesn't have Flash, Silverlight or HTML5 support.</p>
	</div>
	
	<pre id="log" style="height: 100px; overflow: auto">[Init] Info: runtime=html5 Features: chunks=true, multipart=true, multi_selection=true, dragdrop=true 
	[PostInit] 
	</pre>
	<script>
	$(function() {
  		$( "#uploader" ).dialog({
		      resizable: true,
		      height:420,
		      width:800,
		      modal: true,
		      buttons: {
		        "Ok": function() {
		          $( this ).dialog( "close" );
		        },
		        Cancel: function() {
		          $( this ).dialog( "close" );
		        }
		      }
		});  
		
		 $( "#uploader" ).dialog("close");
		
		$('#upFile').bind('click',function(){
			$( "#uploader" ).dialog("open");
		});
		
		var $batchId = $('#batchId');
		var fileUploadCompletedArr = [];
		var uploader = $("#uploader").plupload({
			// General settings
			runtimes : 'html5,flash,silverlight,html4',
			url : '${staticPath }/file/upload',
			flash_swf_url : '${staticPath }/static/plupload/Moxie.swf',
			silverlight_xap_url : '${staticPath }/static/plupload/Moxie.xap',
			
			unique_names : true,
			// Maximum file size
			max_file_size : '20mb',

			//chunk_size: '1mb',

			// Resize images on clientside if we can
			// resize : {width : 320, height : 240, quality : 90},
			resize : {
				//width : 200, 
				//height : 400, 
				quality : 100,
				crop: true // crop to exact dimensions
			},

			// Specify what files to browse for
			filters : {
	            max_file_size : '10mb',
	            mime_types: [
	                {title : "Image files", extensions : "jpg,gif,png"},
	                {title : "Zip files", extensions : "zip"}
	            ]
	        },

			// Rename files by clicking on their titles
			rename: true,
			
			// Sort files
			sortable: true,

			// Enable ability to drag'n'drop files onto the widget (currently only HTML5 supports that)
			dragdrop: true,

			// Views to activate
			views: {
				list: true,
				thumbs: true, // Show thumbs
				active: 'list'
			},
			// PreInit events, bound before any internal events
	        preinit : {
	            Init: function(up, info) {
	                log('[Init]', 'Info:', info, 'Features:', up.features);
	            },
	 
	            UploadFile: function(up, file) {
	                log('[UploadFile]', file);
	 
	                // You can override settings before the file is uploaded
	                // up.setOption('url', 'upload.php?id=' + file.id);
	                // up.setOption('multipart_params', {param1 : 'value1', param2 : 'value2'});
	            }
	        },
	        // Post init events, bound after the internal events
	        init : {
	            PostInit: function() {
	                // Called after initialization is finished and internal event handlers bound
	                log('[PostInit]');
	                 
	                /* document.getElementById('uploadfiles').onclick = function() {
	                    uploader.start();
	                    return false;
	                }; */
	            },
	 
	            Browse: function(up) {
	                // Called when file picker is clicked
	                log('[Browse]');
	            },
	 
	            Refresh: function(up) {
	                // Called when the position or dimensions of the picker change
	                log('[Refresh]');
	            },
	  
	            StateChanged: function(up) {
	                // Called when the state of the queue is changed
	                log('[StateChanged]', up.state == plupload.STARTED ? "STARTED" : "STOPPED");
	            },
	  
	            QueueChanged: function(up) {
	                // Called when queue is changed by adding or removing files
	                log('[QueueChanged]');
	            },
	 
	            OptionChanged: function(up, name, value, oldValue) {
	                // Called when one of the configuration options is changed
	                log('[OptionChanged]', 'Option Name: ', name, 'Value: ', value, 'Old Value: ', oldValue);
	            },
	 
	            BeforeUpload: function(up, file) {
	                // Called right before the upload for a given file starts, can be used to cancel it if required
	                log('[BeforeUpload]', 'File: ', file);
	            },
	  
	            UploadProgress: function(up, file) {
	                // Called while file is being uploaded
	                log('[UploadProgress]', 'File:', file, "Total:", up.total);
	            },
	 
	            FileFiltered: function(up, file) {
	                // Called when file successfully files all the filters
	                log('[FileFiltered]', 'File:', file);
	            },
	  
	            FilesAdded: function(up, files) {
	                // Called when files are added to queue
	                log('[FilesAdded]');
	  
	                plupload.each(files, function(file) {
	                    log('  File:', file);
	                });
	            },
	  
	            FilesRemoved: function(up, files) {
	                // Called when files are removed from queue
	                log('[FilesRemoved]');
	  
	                plupload.each(files, function(file) {
	                    log('  File:', file);
	                });
	            },
	  
	            FileUploaded: function(up, file, info) {
	                // Called when file has finished uploading
	                log('[FileUploaded] File:', file, "Info:", info);
	                debugger
	    		    
	                var file_name = file.name; //文件名
	                var html = '<li id="file-' + file.id +'">'
	                +'<a href="${staticPath }/file/download/'+info.response
	                +'"><p class="file-name">'
					+ file_name
					+ '</p></a><p class="progress"></p></li>';
	                
	                fileUploadCompletedArr.push(html);

	                var batchId = $batchId.val();
	    		    if(batchId)
	    		    	batchId = batchId + "," + info.response;
	    		    else
	    		    	batchId = info.response;
	    		    $batchId.val(batchId);
	            },
	  
	            ChunkUploaded: function(up, file, info) {
	                // Called when file chunk has finished uploading
	                log('[ChunkUploaded] File:', file, "Info:", info);
	            },
	 
	            UploadComplete: function(up, files) {
	                // Called when all files are either uploaded or failed
	                log('[UploadComplete]');
	                
	                
	                for (var i = 0, len = fileUploadCompletedArr.length; i < len; i++) {
	    				$(fileUploadCompletedArr[i] ).appendTo('#file-list');
	    				/* !function(i) {
	    					previewImage(files[i], function(imgsrc) {
	    						$('#file-' + files[i].id).append('<img src="'+ imgsrc +'" />');
	    					})
	    				}(i); */
	    			}
	                
	                var html = '<li id="file-' + files[i].id +'"><p class="file-name">'
						+ file_name
						+ '</p><p class="progress"></p></li>';
					$(html).appendTo('#file-list');
	            },
	 
	            Destroy: function(up) {
	                // Called when uploader is destroyed
	                log('[Destroy] ');
	            },
	  
	            Error: function(up, args) {
	                // Called when error occurs
	                log('[Error] ', args);
	            }
	        }
		});
		
		function log() {
	        var str = "";
	        plupload.each(arguments, function(arg) {
	            var row = "";
	            if (typeof(arg) != "string") {
	                plupload.each(arg, function(value, key) {
	                    // Convert items in File objects to human readable form
	                    if (arg instanceof plupload.File) {
	                        // Convert status to human readable
	                        switch (value) {
	                            case plupload.QUEUED:
	                                value = 'QUEUED';
	                                break;
	 
	                            case plupload.UPLOADING:
	                                value = 'UPLOADING';
	                                break;
	 
	                            case plupload.FAILED:
	                                value = 'FAILED';
	                                break;
	 
	                            case plupload.DONE:
	                                value = 'DONE';
	                                break;
	                        }
	                    }
	 
	                    if (typeof(value) != "function") {
	                        row += (row ? ', ' : '') + key + '=' + value;
	                    }
	                });
	 
	                str += row + " ";
	            } else {
	                str += arg + " ";
	            }
	        });
	 
	        var log = $('#log');
	        log.append(str + "\n");
	        log.scrollTop(log[0].scrollHeight);
	    }
		
	});
	</script>
</body>
</html>