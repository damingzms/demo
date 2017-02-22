<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:include page="/pages/common.jsp"></jsp:include>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8;" />
<title>闭包测试</title>
<script>
	var innerFunc;
	var obj = {};
	obj.count = 10;
	$(function () {
		$("#outerRunner").click(function () {
			innerFunc = outerFunc(10, obj);
		});
		$("#innerRunner").click(function () {
			if (!innerFunc) {
				alert("innerFunc 未定义。");
				return false;
			}
			innerFunc(10);
		});
	});
	
	function outerFunc(x, obj) {
		var i = 10;
		$("input[name='outi']").val(i);
		$("input[name='outx']").val(x);
		$("input[name='outobjcount']").val(obj.count);
		return function (y) {
			$("input[name='ini']").val(i);
			$("input[name='inx']").val(x);
			$("input[name='inobjcount']").val(obj.count);
			$("input[name='iny']").val(y);
			i++; x++; y++; obj.count++;
		};
	}
</script>
</head>
<body>
<input id="outerRunner" type="button" value="run outer" /><br />
<input id="innerRunner" type="button" value="run inner" /><br />
<br />

out i = <input name="outi"/><br />
out x = <input name="outx"/><br />
out obj.count = <input name="outobjcount"/><br />
<br />

in i = <input name="ini"/><br />
in x = <input name="inx"/><br />
in obj.count = <input name="inobjcount"/><br />
in y = <input name="iny"/><br />
</body>
</html>
