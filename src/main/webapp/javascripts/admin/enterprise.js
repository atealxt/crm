var sendEmail = function(e, url) {
	$(e).closest("form").attr("action", url).submit();
};

$('#newCountry').click(function() {
    $('#spanNewCountry').show();
});