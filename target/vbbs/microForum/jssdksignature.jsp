<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="com.bbs.util.JSSDKSignatureUtil" %>
<%@ page import="com.bbs.controller.api.vo.JSSDKSignature" %>
<%
String url=request.getRequestURL().toString();
JSSDKSignature jsSdkSignatureVoObj=JSSDKSignatureUtil.getJsSdkSignatureVoObj(url);
request.setAttribute("jsSdkSignatureVoObj", jsSdkSignatureVoObj);

%>