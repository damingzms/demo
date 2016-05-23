<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:include page="/pages/common.jsp"></jsp:include>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8;" />
<title>闭包测试</title>
<script>
	var str = "a  1";
	document.write(str);
	document.write("<br />");
	//alert(str.trimSam());
	String.prototype.trimSam = function () {
		return "a1";
	}
	document.write(str.trimSam());
	document.write("<br />");
	
	var obj = {};
	/* obj.trim = function () {
		return "myobj";
	}
	document.write(obj.trim());
	document.write("<br />"); */
	
	obj.prototype.trim = function () {
		return "myobj";
	}
	document.write(obj.trim());
	document.write("<br />");
</script>
</head>
<body>
</body>
</html>
