<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:include page="/pages/common.jsp"></jsp:include>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Index</title>
</head>

<body>
    <div>Host : ${host}</div>
    <div>Port : ${port}</div>
    <br />
	<div class="msg">Msg：${msg}</div>
	<br />
	<div>Date: ${date}</div>
	<div>Context path : ${ctx}</div>
    <br />
    <div>Param : ${name}</div>
    <br />
    <img alt="pic" src="/images/1A.gif">

</body>
</html>

