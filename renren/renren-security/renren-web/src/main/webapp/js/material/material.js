$(function () {
	function statusFormat( cellvalue, options, rowObject ){
		if(cellvalue==0)return '未发布';
		if(cellvalue==1)return '已发布';
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
			{ label: '状态', name: 'status', formatter:statusFormat, width: 60 },
			{ label: '素材名称', name: 'name', width: 100 },
			{ label: '标签ID', name: 'tagIds', width: 80 },
			{ label: '关键词ID', name: 'keywordIds', width: 60 },
			{ label: '类型', name: 'type', formatter:typeFormat, width: 100 },
			{ label: '编者ID', name: 'makerid', width: 80 },
			{ label: '编者', name: 'maker', width: 60 },
			{ label: '上传文件数', name: 'fileCount', width: 100 },
			{ label: '描述', name: 'description', width: 80 },
			{ label: '创建人id', name: 'createrId', width: 60 },
			{ label: '创建时间', name: 'createTime', width: 100 },
			{ label: '更新人id', name: 'updaterId', width: 80 },
			{ label: '更新时间', name: 'updateTime', width: 80 }
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
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.material = {};
			//消除类型radio已有的选择,共用一个form表单导致
			var typeCheckedRadioEls = $("div .btn-group > div[class='radio'] > ins[class='checked']");
			typeCheckedRadioEls.trigger("click");
			//新增置空tag控件数据
			vm.tagapp.tags = [];
			vm.tagapp.tagIds = [];
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
                debugger
                //获取tag信息,设置标签控件
                $.ajax({
    				type: "POST",
    			    url: "../materialtag/queryByIds",
    			    sync: true,
    			    contentType: "application/json",
    			    data : JSON.stringify(material.tagIds.split(",")),
    			    success: function(r){
    			    	debugger
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
                
                //设置类型icheck控件
                //$("div .btn-group > div[class='radio'][value=60]")
                var typeRadioEl = $("div .btn-group > div[class='radio'][value="+type+"]");
                typeRadioEl.trigger("click");
            });
		},
		appendAttachmentIds:function(fileId,attachmentIds/**数组只有一个元素 */){
			if(!vm.material.attachments){
				vm.material.attachments = [];
				vm.material.fileCount = 0;
			}
			$.each(attachmentIds,function(index,item){
				vm.material.attachments.push({id:item});
				vm.fileItems[fileId] = item;
			})
			vm.material.fileCount = vm.material.attachments.length;
		},
		updateMaterialType:function(type){
			vm.material.type = type;
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
			var url = vm.material.id == null ? "../material/save" : "../material/update";
			vm.material.tagIds = vm.tagapp.tagIds.join(",");
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
				}
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