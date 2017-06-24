$(function () {
    $("#jqGrid").jqGrid({
        url: '../material/list',
        datatype: "json",
        colModel: [			
			{ label: 'ID', name: 'id', width: 30, key: true },
			{ label: '状态', name: 'status', width: 60 },
			{ label: '素材名称', name: 'name', width: 100 },
			{ label: '标签ID', name: 'tagIds', width: 80 },
			{ label: '关键词ID', name: 'keywordIds', width: 60 },
			{ label: '类型', name: 'type', width: 100 },
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
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
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
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			key: null
		},
		showList: true,
		title: null,
		material: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.material = {};
		},
		update: function () {
			debugger
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			
			$.get("../material/info/"+id, function(r){
                vm.showList = false;
                vm.title = "修改";
                vm.material = r.material;
            });
		},
		appendAttachmentIds:function(attachmentIds){
			if(!vm.material.attachments){
				vm.material.attachments = [];
			}
			$.each(attachmentIds,function(index,item){
				vm.material.attachments.push({id:item});
			})
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
		}
	}
});