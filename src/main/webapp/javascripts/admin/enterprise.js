var sendEmail = function(e, url) {
    $(e).closest("form").attr("action", url).submit();
};

var deleteMemo = function(id) {
    if (confirm("您确定要删除所选信息？")) {
        $('#memoId').val(id);
        $('#formMemo').submit();
    }
};

$(document).ready(function() {
	$('#newCountry').click(function() {
	    $('#spanNewCountry').show();
	});

	$('#aAddMemo').click(function() {
	    $('#divNewMemo').show('middle');
	});

	$('#submitReturnToList').click(function(e) {
		$('#returnToList').val(true);
		$('form').submit();
	});
});
