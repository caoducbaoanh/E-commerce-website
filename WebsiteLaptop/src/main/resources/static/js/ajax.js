/** 
 *title  :ajax admin 
 *auther :lưu văn pốt
 */
$(document).ready(function (){
		$('#dataTables-products').dataTable({
			"processing" : true,
			"serverSide" : false,
		
			"ajax" : {
				"url" : '/admin/manage/product/get/list',
				"type" : 'POST',
				"datatype" : 'json',
				"contentType" : "application/json; charset=utf-8",
				"dataSrc" : ""
			},

			"columns" : [ {
				"data" : "id"
			},{
				"data" : "name"
			},
			{
				"data":"images.1.name",
					render: function(image){
						var src= "<img class='image_products center' src='/"+image+"' width='100%' />";
                           return  src}
			},
			{
				"data": "details.trademark"
			},
			{
				"data": "price"
			},
			{
				"data": "description","width":"30%"
			},{
				"data": "quantity"
			},
			{"data":"id",
				render : function(data, type, row) {
					return "<button type='button'  onclick='deleteProducts("+data+")'  value='"+data+"'>Xóa</button> ";
				}
			}, 
			{
				"data":"id",
				render : function(data, type, row) {
					return "<button type='button'  onclick='editProduct("+data+")' data-toggle='modal' data-target='#editProduct"+data+"' >Sửa</button><div  class='table-responsive' id='edit'></div>";
				}
			},
			],
		})
	});
	$(document).ready(function() {
			$('#product').submit(function(event) {
				event.preventDefault();
				ajaxProduct();
			})
			function ajaxProduct() {
				var form = $('#product')[0];
				var data = new FormData(form);
				$.ajax({
					url : "/admin/manage/product/add",
					type : "POST",
					enctype : 'multipart/form-data',
					data : data,
					processData : false,
					contentType : false,
					success : function(data) {
						if (data) {
							alert("Bạn đã them sản phẩm thành công");
							$('#dataTables-products').DataTable().ajax.reload();

						} else {
							alert("lỗi quá trình xử lý ");
						}
					}
				})
			}
		});
		$(document).ready(function() {
	$('#form-edit').submit(function(event) {
		event.preventDefault();
		ajaxProductEdit();
	});
			function ajaxProductEdit() {
				var form = $('#form-edit')[0];
				var data = new FormData(form);
				$.ajax({
					url : "/admin/manage/product/edit",
					type : "POST",
					enctype : 'multipart/form-data',
					data : data,
					processData : false,
					contentType : false,
					success : function(data) {
						if (data) {
							alert("Bạn đã cập nhập sản phẩm thành công");
							$('#dataTables-products').DataTable().ajax.reload();
						} else {
							alert("lỗi quá trình xử lý ");
						}
					}
				})
			}	});
				function preview_image() 
		{
			 $('#image_preview').html("");

		 var total_file=document.getElementById("upload_file").files.length;
		 for(var i=0;i<total_file;i++)
		 {
		  $('#image_preview').append("<img  src='"+URL.createObjectURL(event.target.files[i])+"' width='25%'>");
		 }
		};
			function preview_image_up() 
		{
			 $('#image_preview_up').html("");

		 var total_file=document.getElementById("uploadfileup").files.length;
		 for(var i=0;i<total_file;i++)
		 {
		  $('#image_preview_up').append("<img  src='"+URL.createObjectURL(event.target.files[i])+"' width='25%'>");
		 }
		};
		function  deleteProducts(data){
		if(confirm("Bạn muốn xóa sản phẩm")){
		$.ajax({
			url:'/admin/manage/product/delete',
			type:"get",
			data :{
				idP:data
			},
		success : function (response){
			if(response){
				alert("Bạn đã xóa thành công");
				$('#dataTables-products').DataTable().ajax.reload();
			}else {
				alert("xảy ra quá trình xử lý");
			}
		}
		})
		
	}};
	function editProduct(data){
		$.ajax({
			url:"/admin/manage/product/get",
			type:"get",
			data :{
				idP:data
			},
			success :function (response){
				console.log(response);
				$('#product-edit').html(response);
				$("#edit-product").modal('show') ;      

			}

		})

	};