<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.Locale" %>
<%@ page import="org.claros.commons.configuration.PropertyFile" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/i18n-1.0" prefix="i18n" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<%
response.setHeader("Expires","-1");
response.setHeader("Pragma","no-cache");
response.setHeader("Cache-control","no-cache");

String lang = (String)session.getAttribute("lang");

String defaultLang = org.claros.commons.configuration.PropertyFile.getConfiguration("/config/config.xml").getString("common-params.default-lang");
if (lang == null) lang = defaultLang;
Locale loc = new Locale(defaultLang);
try {
	loc = new Locale(lang);
} catch (Exception e) {}

session.setAttribute("lang", lang);
%>

<script>var title = '<%=PropertyFile.getConfiguration("/config/config.xml").getString("common-params.title")%>';</script>
<i18n:bundle baseName="org.claros.intouch.i18n.lang" locale="<%= loc %>"/>

<link rel="stylesheet" href="<c:url value="/stylesheets/common.css"/>" type="text/css" media="screen" />
<link type="text/css" rel="stylesheet" href="<c:url value="/css/all.css"/>" />
<link type="text/css" rel="stylesheet" href="<c:url value="/css/body.css"/>">
<link type="text/css" rel="stylesheet" href="<c:url value="/css/ie6.css"/>">
<style>
#main .inboxholder {
	margin: 0 10px;
}
</style>
<script>var lang = "<%=loc%>";</script>
<script type="text/javascript" src="<c:url value="/tinymce/tiny_mce.js"/>"></script>

<div id="main">

<!-- COMPOSE STARTS -->
<div id="compose" style="">
	<div class="inboxholder">
		<div class="inner">
			<ul id="tools">
				<li id="sendMail"><a id="aSendMail" href="javascript:sendMail();"><span><img alt="" src="<c:url value="/images/send-icon.gif"/>" id="okMailImg"/></span><i18n:message key="send.mail"/></a></li>

				<%// BEST UPLOADING SOLUTION (M.O.)%>
				<%// start region%>
				<li id="attachMail">
					<div class="uploader">
						<input onchange="addNewUpload()" class="uploadbox" type="file" id="inputfile" name="inputfile" />
						<img alt="" src="<c:url value="/images/new-icon.gif"/>" id="attachMailImg" /><i18n:message key="attach.file" />
					</div>
				<%// end region%>
				<li id="preferencesMail"><a href="javascript:showHidePrefsMail();"><span><img alt="" src="<c:url value="/images/message-preferences.gif"/>" id="preferencesMailImg"/></span><i18n:message key="options"/></a></li>
				<li id="cancelMail"><a href="javascript:cancelMail();"><span><img alt="" src="<c:url value="/images/delete-icon.gif"/>" id="cancelMailImg"/></span><i18n:message key="cancel"/></a></li>
			</ul>
			<div class="inboxTitle" id="inboxTitle2"><i18n:message key="compose.mail"/></div>
			<div id="composer">
                   <table width="100%" border="0" cellspacing="3" cellpadding="3" align="center">
				  <!-- Begin Compose Table -->
				  <tr>
				  	<td>
			          <table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
			            <tr>
			              <td valign="top">
			              	<table width="100%" align="center" cellpadding="0" cellspacing="1">
				                <tr>
				                  <td width="100%">
									<form action="<c:url value="/webmail/sendMail.cl"/>" method="post" id="composeForm">
										<input type="hidden" id="from" name="from" value="<c:out value="${sessionScope.auth.username}" />"/>
										<input type="hidden" name="enterprise" value="true" />
										<input type="hidden" name="htmlEmail" value="false" />
										<table border="0"  cellspacing="1"  cellpadding="3" width="100%">
											<tbody class="tableBody" >
												<%-- TODO 模板 --%>
												<tr>
													<td colspan="2" nowrap="nowrap"><spring:message code="Enterprise.sendMailCount" text="Enterprise.sendMailCount" arguments="${EnterpriseCount}"/></td>
												</tr>
												<tr>
													<td width="1%" nowrap="nowrap"><strong><i18n:message key="subject"/>:</strong> </td>
													<td width="99%" nowrap="nowrap"><input type="text" name="subject" id="subject" size="80" onkeydown="return(subjectJump(event));"/></td>
												</tr>
												<tr id="messageOptions" style="display: none;">
													<td width="1%" nowrap="nowrap">&nbsp;</td>
													<td width="99%" nowrap="nowrap">
														<strong><i18n:message key="priority"/>:</strong>
														<select name="priority" id="priority" style="border: 1px solid #999999;font-size: 9px;" nohide="true">
														  <option value="5"><i18n:message key="priority.low"/></option>
														  <option value="3" selected><i18n:message key="priority.normal"/></option>
														  <option value="1"><i18n:message key="priority.high"/></option>
														</select>&nbsp;&nbsp;&nbsp;&nbsp;
														<strong><i18n:message key="sensitivity"/>:</strong>
														<select name="sensitivity" id="sensitivity" style="border: 1px solid #999999;font-size: 9px;" nohide="true">
														  <option value="1" selected><i18n:message key="sensitivity.normal"/></option>
														  <option value="2"><i18n:message key="sensitivity.personal"/></option>
														  <option value="3"><i18n:message key="sensitivity.private"/></option>
														  <option value="4"><i18n:message key="sensitivity.confidential"/></option>
														</select>&nbsp;&nbsp;&nbsp;&nbsp;
														<strong><i18n:message key="request.read.receipt"/>:</strong>
														<input name="requestReceiptNotification" id="requestReceiptNotification" type="checkbox" value="1" style="border: 1px solid #999999;font-size: 9px;width:15px;">
													</td>
												</tr>
												<tr id="attachmentstr" style="display:none;background-color: #F6F6F6;">
													<td width="100%" nowrap="nowrap" colspan="2" style="padding-right: 10px;">
														<div style="border: 1px solid #d2d2d2;width: 100%;overflow: visible;padding: 4px;">
															<table border="0" cellspacing="0" cellpadding="0" width="100%">
																<tr>
																	<td style="float: left;" width="99%">
																		<div style="float: left;">
																			<ul id="composeAttachmentList">
																			</ul>
																		</div>
																	</td>
																	<td align="right" nowrap="nowrap" width="1%" valign="top">
																		<div style="float: right;font-size: 11px;font-weight:bold;color: #999999;padding-right: 10px;">
																			<i18n:message key="total.size"/>: <span id="totalSize">0K</span>
																		</div>
																	</td>
																</tr>
															</table>
														</div>
													</td>
												</tr>
											</tbody>
										</table>
										<br/><%-- TODO html link 404? --%>
										<script>
											tinyMCE.init({
												mode : "exact",
												theme : "advanced",
												elements: "composeBody",
												plugins : "iespell",
												theme_advanced_toolbar_location : "top",
												theme_advanced_toolbar_align : "left",
												theme_advanced_buttons1 : "separator, newdocument, bold, italic, underline, strikethrough, fontsizeselect, separator, justifyleft, justifycenter, justifyright, justifyfull, separator, bullist, numlist, separator, outdent, indent, separator, link, image, forecolor, backcolor, charmap, separator, iespell, code",
												theme_advanced_buttons2 : "",
												theme_advanced_buttons3 : "",
												content_css : "<c:url value='/css/editor.css'/>",
												language : lang,
												force_p_newlines: false,
												force_br_newlines: true,
												auto_resize : false,
												verify_html : "false"
											});

										</script>
										<textarea id="composeBody" rows="25" cols="60" style="width:85%;" name="composeBody"></textarea>
										<br>
									</form>
				                  </td>
				                </tr>
				              </table>
				            </td>
				          </tr>
				       	</table>
				  	</td>
				  </tr>
				  <!-- End Compose Table -->
			    </table>

			</div>

		</div>
	</div>
</div>
<!-- COMPOSE ENDS -->

</div>
<div id="uploader"></div>

<script type="text/javascript">
var uploadingGif = "<c:url value="/images/uploading.gif"/>";
var uploadAttachment = "<c:url value="/webmail/uploadAttachment.cl"/>";
var deleteAttachment = "<c:url value="/webmail/deleteAttachment.cl"/>";
var txtRemove = "<i18n:message key="remove"/>";
</script>
<script type="text/javascript" src="<c:url value="/javascripts/admin/mail.js"/>"></script>