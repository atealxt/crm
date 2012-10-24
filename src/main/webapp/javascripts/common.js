var deleteRecord = function(e, url, refreshUrl) {
	if (confirm("您确定要删除所选信息？")) {
		$.ajax({
			type : "DELETE",
			url : url,
			success : function(data) {
				if (refreshUrl) {
					window.location.href= refreshUrl;
				}
				else {
					window.location.refresh(true);
				}
			},
			error : function() {
				alert('error!');
			}
		});
	}
};

$(document).ready(function() {
	$('.btnDel').click(function() {
//		if (confirm("您确定要删除所选信息？")) {
//			$(this).closest("form").attr("method", "delete").attr("action", url).submit(); // TODO
//		}
	});
});