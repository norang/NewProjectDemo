$('.confirm').on('click', function (e) {
	return confirm($(this).data('confirm'));
});

function openUModal(id){
	$.ajax({
		url:"/uploadFile/viewDetail/"+id,
		success:function(data){
			$("#uModalHolder").html(data);
			$("#file-type").val(data.filetype);
			$("#file-name").val(data.name);
			$("#file-id").val(data.id);
			$("#file-pic").val(data.pic);
			$("#uModal").modal("show");
		},
		error: function(e){  
          alert('Error: ' + e);  
        }  
	});
}