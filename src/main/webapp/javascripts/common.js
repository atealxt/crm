var deleteRecord = function(e, url, refreshUrl, soft) {
	var msg = soft ? "您确定要删除所选信息？（删除后，该记录将会被放入回收站）" : "您确定要删除所选信息？";
	if (confirm(msg)) {
		$.ajax({
			type : "DELETE",
			url : url,
			success : function(data) {
				if (refreshUrl) {
					window.location.href = refreshUrl;
				} else {
					window.location.refresh(true);
				}
			},
			error : function() {
				alert('error!');
			}
		});
	}
};

var viewRecord = function(url) {
	window.location.href = url;
};

var editRecord = function(url) {
	window.location.href = url;
};

var restoreRecord = function(e, url) {
	if (confirm("您确定要恢复所选信息？")) {
		$(e).closest("form").attr("action", url).submit();
	}
};

$(document).ready(function() {
	$('.btnDel').click(function() {
		// if (confirm("您确定要删除所选信息？")) {
		// $(this).closest("form").attr("method", "delete").attr("action",
		// url).submit(); // TODO
		// }
	});
});