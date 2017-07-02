$(function () {
	function statusFormat( cellvalue, options, rowObject ){
		if(cellvalue==0)return '未发布';
		if(cellvalue==50)return '已发布';
		return '未知';
	}
	function typeFormat( cellvalue, options, rowObject ){
		if(cellvalue==10)return '图片';
		if(cellvalue==20)return '视频';
		if(cellvalue==30)return '音频';
		if(cellvalue==40)return '字体';
		if(cellvalue==50)return '挂件';
		if(cellvalue==60)return '滤镜';
		return '未知';
	}
    $("#jqGrid").jqGrid({
        url: '../material/list',
        datatype: "json",
        colModel: [			
			{ label: 'ID', name: 'id', width: 30, key: true },
			{ label: '状态', name: 'status',index: "m.status", formatter:statusFormat, width: 60 },
			{ label: '素材名称', name: 'name', width: 100 },
			{ label: '标签ID', name: 'tagIds',index: "tag_ids", width: 100 },
			{ label: '关键词ID', name: 'keywordIds',index: "keyword_ids", width: 60 },
			{ label: '类型', name: 'type', formatter:typeFormat, width: 100 },
			{ label: '编者ID', name: 'makerid', width: 80 },
			{ label: '编者', name: 'maker', width: 100 },
			{ label: '上传文件数', name: 'fileCount',index: "file_count", width: 100 },
			{ label: '描述', name: 'description', width: 80 },
			{ label: '创建人', name: 'createrName',index: "s1.username", width: 100 },
			{ label: '创建时间', name: 'createTime',index: "m.create_time", width: 100 },
			{ label: '更新人', name: 'updaterName',index: "s2.username", width: 100 },
			{ label: '更新时间', name: 'updateTime',index: "m.update_time",sortable:false, width: 100 }
        ],
		viewrecords: true,
		height:'auto',
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        shrinkToFit:false,   
        autoScroll: true,
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "scroll" }); 
        }
    });
});
/*
 new Vue({
	el : '#app',
	data : function() {
		return {
			visible : false
		}
	}
})
var tagVm = new Vue({
	el : '#tagsapp',
	data : function() {
		return 
	},
	mounted : function() {
		debugger
	    this.list = this.states.map(function(item,index) {
	        return { value: index, label: item };
	    });
	},
	methods: {
		onInput : function () {
			debugger
			//this.$emit('tagIds', this.tagIds);
	        //if (this.tagIds.trim()) {
	        //}
	    },
		remoteMethod : function(query) {
			debugger
			if (query !== '') {
				tagVm.loading = true;
				setTimeout(function(){
					tagVm.loading = false;
					tagVm.tags = this.list.filter(function(item) {
						return item.label.toLowerCase().indexOf(query.toLowerCase()) > -1;
					});
				}, 200);
	        } else {
	        	tagVm.tags = [];
	        }
		}
	}
})*/

var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			key: null
		},
		showList: true,
		title: null,
		fileItems:{},
		materialPriceEmptySettings:[],
		material: {
		},
		tagapp:{
			tagIds:[],
			tags: [],
	        loading: false
		}
	},
	mounted : function() {
		this.tagapp.tagIds = this.material.tagIds||[];
	    this.tagapp.tags = this.tagapp.tagIds.map(function(item,index) {
	        return { value: index, label: item };
	    });
	    
	    //于mounted时调用后台,避免每次新增后台调用,获取一个等级相关价格空的配置
	    $.get("../materialprice/materialpricesettings/0",function(r){
			if(r.code == 0){
				vm.materialPriceEmptySettings = r.materialPriceSettings;
			}else{
				alert(r.msg);
			}
		});
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			
			//消除类型radio已有的选择,共用一个form表单导致
			var typeCheckedRadioEls = $("div .btn-group > div[class='radio'] > ins[class='checked']");
			//如果存在typeCheckedRadioEls,则触发一个点击事件
			typeCheckedRadioEls.trigger("click");
			
			//新增置空tag控件数据
			vm.tagapp.tags = [];
			vm.tagapp.tagIds = [];
			
			//清空并重置文件控件
			vm.destroyUploader();
			vm.initUploader();
			
			vm.material = {};
			//查询等级列表,关联出需要配置的价格条目,设置到vm.material中,有vuejs渲染价格配置输入界面
			vm.material.materialPrices = vm.materialPriceEmptySettings;
			//于mounted时调用后台,避免每次新增后台调用,获取一个等级相关价格空的配置
		    $.get("../materialprice/materialpricesettings/0",function(r){
				if(r.code == 0){
					vm.materialPriceEmptySettings = r.materialPriceSettings;
				}else{
					alert(r.msg);
				}
			});
		},
		update: function () {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			$.get("../material/info/"+id, function(r){
                vm.showList = false;
                vm.title = "修改";
                var material = r.material;
                vm.material = material;
                var type = material.type;
                
                //清空文件控件
    			vm.destroyUploader();
    			vm.initUploader();
    			
    		     //设置类型icheck控件
                //$("div .btn-group > div[class='radio'][value=60]")
                var typeRadioEl = $("div .btn-group > div[class='radio'][value="+type+"] > ins[class='checked']");
                if(typeRadioEl.length == 0){
                	$("div .btn-group > div[class='radio'][value="+type+"]").trigger("click");
                }
    			
                //初始现有文件
    			if(vm.material&&vm.material.attachments){
    				$.each(vm.material.attachments,function(index,item){
	    				//构造html来更新UI
	    				var html = '<li id="file-' + item.id +'" style="width: 135%;"><span class="file-name">' + item.name + '</span>'
	    				+'<span style="display: inline-block;float: right;">'
	    				+'<span class="progress-text">100%</span><button style="padding:4px" @click="removeRemoteFile(\''+item.id+'\')" type="button" class="close">×</button></span></li>';
	    				
	    				var FileInfoItem = Vue.extend({
	    					   template:html,
	    					   methods:{
	    						   removeRemoteFile: function(attachmentId){
		    							$.ajax({
		    								type: "GET",
		    							    url: "../attachment/file/delFromMaterial/"+attachmentId,
		    							    success: function(r){
		    									if(r.code == 0){
		    										alert('删除成功', function(index){
		    											//do remove attachment from vm data
		    											if(vm.material.attachments&&vm.material.attachments.length>0){
		    												var tMatchIdx = -1;
		    												$.each(vm.material.attachments,function(index,item){
		    													if(item.id==attachmentId){
		    														tMatchIdx = index;
		    													}
		    												});
		    												if(tMatchIdx>-1){
		    													//splice从1或-1开始,index从0开始
		    													vm.material.attachments.splice(tMatchIdx,1);
		    												}
		    												vm.material.fileCount = vm.material.attachments.length;
		    												var $fileItem = $('#file-'+attachmentId);
		    												$fileItem.remove();
		    											}
		    										});
		    									}else{
		    										alert(r.msg);
		    									}
		    								}
		    							});
		    					   }
	    					   }
	    				});
	    				
	    				$("#file-list").append((new FileInfoItem().$mount()).$el);
	    			});
    			}
                //获取tag信息,设置标签控件
                $.ajax({
    				type: "POST",
    			    url: "../materialtag/queryByIds",
    			    sync: true,
    			    contentType: "application/json",
    			    data : JSON.stringify(material.tagIds.split(",")),
    			    success: function(r){
    					if(r.code == 0){
    						vm.tagapp.tags = r.materialTags.map(function(item){
    							return { value: item.id, label: item.tagName };
    						});
    						
    						vm.tagapp.tagIds = r.materialTags.map(function(item){
    							return item.id;
    						});
    						
    					}else{
    						alert(r.msg);
    					}
    				}
    			});
           
            });
		},
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../material/delete",
                    contentType: "application/json",
				    data: JSON.stringify(ids),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								vm.reload();
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		saveOrUpdate: function (event) {
			this.$validator.validateAll().then(function(result) {
				var typeRadioEl = $("div .btn-group > div[class='radio'] > ins[class='checked']");
				//未选择素材类型
                if(typeRadioEl.length == 0){
                	alert("请选择素材类型");
                	return;
                }
                //未上传文件
                if(!vm.material.attachments||vm.material.attachments.length == 0){
                	alert("请选择上传素材文件");
                	return;
                }
				
				var url = vm.material.id == null ? "../material/save" : "../material/update";
				vm.material.tagIds = vm.tagapp.tagIds.join(",");
				confirm('确定提交？',function(){
					//disable提交按钮,防止重复提交
					var $btn = $(event.currentTarget);
					$btn.attr("disabled",true);
					
					$.ajax({
						type: "POST",
					    url: url,
		                contentType: "application/json",
					    data: JSON.stringify(vm.material),
					    success: function(r){
					    	if(r.code === 0){
								alert('操作成功', function(index){
									vm.reload();
								});
							}else{
								alert(r.msg);
							}
						},
						complete:function(){
							//恢复按钮
							$btn.attr("disabled",""); 
						}
					});
				});
			});
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                postData:{'key': vm.q.key},
                page:page
            }).trigger("reloadGrid");
		},
		updateMaterialType:function(type){
			vm.material.type = type;
		},
		appendAttachmentIds:function(fileId,attachments/**数组只有一个元素 */){
			if(!vm.material.attachments){
				vm.material.attachments = [];
				vm.material.fileCount = 0;
			}
			$.each(attachments,function(index,item){
				vm.material.attachments.push(item);
				vm.fileItems[fileId] = item;
			})
			vm.material.fileCount = vm.material.attachments.length;
		},
		onTagInput : function () {
			//this.$emit('tagIds', this.tagIds);
	        //if (this.tagIds.trim()) {
	        //}
	    },
		tagRemoteMethod : function(query) {
			if (query !== '' && query.length>1) {
				vm.tagapp.loading = true;
				setTimeout(function(){
					vm.tagapp.loading = false;
					vm.loadTags(query.toLowerCase());
				}, 200);
	        } else {
	        	vm.tagapp.tags = [];
	        }
		},
	    loadTags : function(q){
	    	$.ajax({
				type: "GET",
			    url: "../materialtag/search?key="+q,
			    success: function(r){
					if(r.code == 0){
						vm.tagapp.tags = r.list.map(function(item){
							return { value: item.id, label: item.tagName };
						});
					}else{
						alert(r.msg);
					}
				}
			});
	    }
	}
});