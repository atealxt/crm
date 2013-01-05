var connection;
$(document).ready(function() {
    if (window.WebSocket) {
    	function js_yyyy_mm_dd_hh_mm_ss() {
    		var now = new Date();
    		year = "" + now.getFullYear();
    		month = "" + (now.getMonth() + 1); if (month.length == 1) { month = "0" + month; }
    		day = "" + now.getDate(); if (day.length == 1) { day = "0" + day; }
    		hour = "" + now.getHours(); if (hour.length == 1) { hour = "0" + hour; }
    		minute = "" + now.getMinutes(); if (minute.length == 1) { minute = "0" + minute; }
    		second = "" + now.getSeconds(); if (second.length == 1) { second = "0" + second; }
    		return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
    	}
        var sent = 0;
        var success = 0;
        connection = new WebSocket(baseWebSocketPath + '/webmail/sendMailWebSocket.cl');
        connection.onopen = function(evt) {};
        connection.onclose = function(evt) {};
        connection.onerror = function(evt) { alert("ERROR: " + evt);console.log(evt); };
        connection.onmessage = function(e) {
            if (e.data == 'success') {
                connection.close();
                $('#dynamicInfoMessage').text('');
                return;
            } else if (e.data.indexOf('System Error') == 0) {
                connection.close();
                $('#dynamicInfoMessage').text('');
                $('#dynamicErrorMessage').text(e.data);
                return;
            } else if (e.data.indexOf('log:') == 0) {
                $('#tbodyMailLog').append('<tr><td>' + js_yyyy_mm_dd_hh_mm_ss() + ' ' + e.data.substring(4) + '</td></tr>');
                return;
            }
            var data = JSON.parse(e.data);
            $('#tbodyMailSentInfo').append('<tr><td>' + data['name'] + '</td><td>' + data['mail'] + '</td><td>' + data['status'] + '</td></tr>');
            sent++;
            if (data['success']) {
                success++;
            }
            $('#tdSent').text(sent);
            $('#tdSuccess').text(success);
        };
    }
});

function sendMail(e) {
    var attMail = parent.document.getElementById('composeAttachmentList');
    if (!attMail) {
        attMail = document.getElementById('composeAttachmentList');
    }
    var lis = attMail.getElementsByTagName("li");

    for (var i=0;i<lis.length;i++) {
        var li = lis[i];

        if (li.getElementsByTagName("img")[0].src.indexOf("images/uploading.gif") > 0) {
            alert('There are active uploads, please wait.');
            return;
        }
    }

    $('#dynamicInfoMessage').text('邮件正在发送，请稍候...');
    $('#aSendMail').attr('href', '###');

    if (window.WebSocket) {
        var eid = tinyMCE.getEditorId('composeBody');
        var te = document.getElementById(eid);
        var mybody = te.contentWindow.document.body.innerHTML;
        $('#composeBody').text(mybody);
        var formVal = $('#composeForm').serialize();
        var html = '<h2>发送报告</h2>';
        html += '<p><table id="tableStat">';
        html += '<tr><td>总发送数</td><td id="tdTotal">' + getSendMailCount() + '</td></tr>';
        html += '<tr><td>已发送数</td><td id="tdSent">0</td></tr>';
        html += '<tr><td>发送成功</td><td id="tdSuccess">0</td></tr>';
        html += '</table></p><p><table id="tableDetail"><thead><th>企业名</th><th>邮箱</th><th>状态</th></thead>';
        html += '<tbody id="tbodyMailSentInfo"></tbody></table></p>';
        html += '<p><table><thead><th align="left">系统日志</th></thead><tbody id="tbodyMailLog"></tbody></table></p>';
        $('#divMail').html(html);
        connection.send(formVal);
    } else {
        $("#composeForm").submit();
    }
}

function getSendMailCount() {
    var sendMailCountLimitation = $('#sendMailCountLimitation').val();
    if (sendMailCountLimitation != '') {
        return Math.min(parseInt(sendMailCountLimitation, 10), parseInt($('#spanSendMailCount').text(), 10));
    }
    return $('#spanSendMailCount').text();
}

var uploadIframeCount = 0;
function addNewUpload() {
    $('#attachmentstr').show();

    //get filename
    var f=$('#inputfile').val();
    var pos=f.lastIndexOf('\\');
    if(pos>=0) f=f.substr(pos+1);
    pos=f.lastIndexOf('/');
    if(pos>=0) f=f.substr(pos+1);

    //add attachment to list
    var li=document.createElement("li");
    li.id="uploadli"+uploadIframeCount;
    var img=document.createElement("img");
    img.src=uploadingGif;
    li.appendChild(img);
    var sp=document.createElement("span");
    sp.innerHTML=f;
    sp.style.paddingRight=5;
    li.appendChild(sp);
    var a=document.createElement("a");
    a.href="javascript:removeAttach('"+f+"')";
    a.innerHTML=" "+txtRemove;
    a.style.color='#5A799E';
    a.style.display='none';
    li.appendChild(a);
    var list=$("#composeAttachmentList");
    list.append(li);

    var theFile = document.getElementById("inputfile");
    var fileParent = theFile.parentNode;
    var theDiv = document.createElement('div');
    theDiv.style.display = 'none';
    theDiv.innerHTML = '<iframe id="uploadiframe'+uploadIframeCount+'" name="uploadiframe'+uploadIframeCount+'" src=""></iframe>' +
        '<form id="uploadform'+uploadIframeCount+'" target="uploadiframe'+uploadIframeCount+'" action="' + uploadAttachment +'" enctype="multipart/form-data" method="post">' +
        '<input type="hidden" name="iframeid" id="iframeid" value="'+uploadIframeCount+'"/>' +
        '</form>';
    $('#uploader').append(theDiv);
    var uploadform = document.getElementById("uploadform"+uploadIframeCount);
    fileParent.removeChild(theFile);
    uploadform.appendChild(theFile);
    uploadIframeCount++;
    uploadform.submit();
    uploadform.removeChild(theFile);
    fileParent.appendChild(theFile);

//	fixLayout();
}

function removeAttach(fileName) {

    var lis = $('#composeAttachmentList li');

    for (var i=0;i<lis.length;i++) {
        var li = lis[i];

        if (li.innerHTML.indexOf(fileName) >= 0) {
            li.style.display = "none";

            var actCount = 0;
            if (lis.length > 1) {
                for (var j=0;j<lis.length;j++) {
                    if (lis[j].style.display != 'none') {
                        actCount++;
                    }
                }
            }
            if (actCount == 0) {
                $('#attachmentstr')[0].style.display = 'none';
            }
        }
    }

    // remove it from the server
    var paramData = "f=" + myEncode(fileName);

    $.ajax({
        type : "POST",
        url : deleteAttachment + "?" + paramData,
        success : function(data) {
//			fixLayout();
        },
        error : function() {
            alert('error!');
        }
    });
}

function myEncode(str) {
    str = encodeURI(str);
    str = str.replace(new RegExp("&",'g'), "%26");
    str = str.replace(new RegExp(";",'g'), "%3B");
    str = str.replace(new RegExp("[?]",'g'), "%3F");
    return str;
}

function showHidePrefsMail() {
    showHide('messageOptions');
}

function showHide(id) {
    var obj = $('#' + id)[0];
    if (obj.style.display == 'none') {
        obj.style.display = '';
    } else {
        obj.style.display = 'none';
    }
}

function cancelMail() {
    history.back();
}