<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>SSH Demo</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<script src="javascripts/jquery.min.js" type="text/javascript"></script>
<script src="javascripts/json.min.js" type="text/javascript"></script>
</head>

<body>

REST test
<br>
<a href="rest/login/atea">login</a>
<br>
<a id="json" href="###">get json(要想做到不乱码，应配置web server)</a>
<div id="divJson1">divJson1</div>
<div id="divJson2">divJson2</div>
<div id="divJson3">divJson3</div>
<script type="text/javascript">
    $(document).ready(function() {
        $('#json').click(function() {
            $.getJSON('rest/json1', {
                param : 'test测试'
            }, function(data) {
                $('#divJson1').text(data['id'] + " " + data['name'] + " " + data['registerTime']);
            });
            $.getJSON('rest/json2/222/test' + encodeURI('测试'), function(data) {
                $('#divJson2').text(data['id'] + " " + data['name'] + " " + data['registerTime']);
            });
            $.postJSON("rest/json3", {
                id : '333',
                name : 'test测试',
                registerTime : '2010-07-20'
            }, function(data) {
                $('#divJson3').text(data['id'] + " " + data['name'] + " " + data['registerTime']);
            });
            /*jQuery.ajax({
                'type': 'POST',
                'url': 'rest/json3',
                'contentType': 'application/json',
                'data': JSON.stringify({
                        id : '333',
                        name : 'test测试',
                        registerTime : '2010-07-20'
                    }),
                'dataType': 'json',
                'success': function(data) {
                    $('#divJson3').text(data['id'] + " " + data['name'] + " " + data['registerTime']);
                }
            });*/
            return false;
        });
    });
</script>

</body>
</html>
