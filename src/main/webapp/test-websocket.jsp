<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Demo</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<script src="javascripts/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript">

$(document).ready(function() {
    if (window.WebSocket) {
        var connection = new WebSocket('ws://<%=request.getServerName()%>:<%=request.getServerPort()%><%=request.getContextPath()%>/websocket/test');
        connection.onopen = function(evt) { console.log("CONNECTED"); };
        connection.onclose = function(evt) { console.log("DISCONNECTED"); };
        connection.onerror = function(evt) { alert("ERROR: " + evt);console.log(evt); };
        connection.onmessage = function(e) {
            console.log(e.data);
        };
        $('#btnWS').click(function() {
            var message = {
                'name': 'Hello',
                'comment': 'World'
            };
            connection.send(JSON.stringify(message));
            connection.send($('#form1').serialize());
        });
    } else {
        /*WebSockets are not supported. Try a fallback method like long-polling etc*/
    }
});

</script>
</head>

<body>

<form id="form1">
    <input name="t1"><br>
    <textarea name="t2"></textarea><br>
    <input id="btnWS" type="button" value="click"/>
</form>

</body>
</html>
