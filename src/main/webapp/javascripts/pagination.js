;
(function($) {

    $.fn.staticPaginate = function(numPerPage) {
        $(this).each(function() {
            var currentPage = 0;
            var $table = $(this);
            var $theadth = $table.find('thead tr th');
            var $tfootth = $table.find('tfoot tr th');

            $table.bind('repaginate', function() {
                $table.find('tbody tr').hide().slice(currentPage * numPerPage, (currentPage + 1) * numPerPage).show();
            });
            var numRows = $table.find('tbody tr').length;
            var numPages = Math.ceil(numRows / numPerPage);
            var $pager = $('<div class="pager"></div>');

            if (numPages <= 1) {
                $table.find('tfoot').empty();
                return;
            }
            for ( var page = 0; page < numPages; page++) {
                $('<span class="page-number"></span>').text(page + 1).bind('click', {
                    newPage : page
                }, function(event) {
                    currentPage = event.data['newPage'];
                    $table.trigger('repaginate');
                    $(this).addClass('active').siblings().removeClass('active');
                }).appendTo($pager).addClass('clickable');
            }

            $tfootth.attr('colspan', $theadth.length);
            $pager.appendTo($tfootth).find('span.page-number:first').addClass('active');
            $table.trigger('repaginate');
        });
    };

    /**
     * @param url
     *            数据请求地址
     * @param currentPage
     *            当前处于第几页
     * @param numPerPage
     *            每页返回多少行数据
     * @param numPages
     *            总页数
     * @param loadImmediately
     *            是否取数据
     */
    $.fn.ajaxPaginate = function(url, currentPage, numPerPage, numPages) {
        $(this).each(function() {
            var $table = $(this);
            var $theadth = $table.find('thead tr th');
            var $tfootth = $table.find('tfoot tr th');
            var $tbody = $table.find('tbody');
            $table.find('tfoot th').empty();

            $tbody.empty();
            $.getJSON(url, {
                page : currentPage,
                pageSize : numPerPage
            }, function(data) {
                //set data body
                var html = "";
                $.each($.parseJSON(data['jsonData']), function(entryIndex, entry) {
                    html += "<tr>";
                    html += "<td>";
                    html += entry['id'];
                    html += "</td>";
                    html += "<td>";
                    html += entry['name'];
                    html += "</td>";
                    html += "<td>";
                    html += entry['age'];
                    html += "</td>";
                    html += "</tr>";
                });
                $tbody.html(html);
                numPages = data['pageCount'];

                //set page toolbar
                var $pager = $('<div class="pager"></div>');
                for ( var page = 1; page <= numPages; page++) {
                    $('<span class="page-number" id=></span>').text(page).bind('click', {
                        newPage : page
                    }, function(event) {
                        $table.ajaxPaginate(url, event.data['newPage'], numPerPage, numPages);
                    }).appendTo($pager).addClass('clickable');
                }
                $tfootth.attr('colspan', $theadth.length);
                $pager.appendTo($tfootth).find('span.page-number:eq(' + (currentPage - 1) + ')').addClass('active');
            });
        });
    };

})(jQuery);

var goPage = function(e, url) {
	$(e).closest("form").attr("action", url).submit();
};