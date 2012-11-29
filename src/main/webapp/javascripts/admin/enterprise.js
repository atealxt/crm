var sendEmail = function(e, url) {
    $(e).closest("form").attr("action", url).submit();
};

$('#newCountry').click(function() {
    $('#spanNewCountry').show();
});

$('#aAddMemo').click(function() {
    $('#divNewMemo').show('middle');
});

var deleteMemo = function(id) {
    if (confirm("您确定要删除所选信息？")) {
        $('#memoId').val(id);
        $('#formMemo').submit();
    }
};