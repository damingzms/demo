<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:include page="/pages/common.jsp"></jsp:include>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8;" />
<title>跨域测试</title>
<script>
	function submitForm() {
		$("#searchForm").submit();
	}

	function submitFormByAjax() {
		var url = "http://localhost:8080/seatReserve/queryAllReserveTicketInfo/";
		var params = {};
		params.termType = $("input[name='termType']").val();
		params.termCode = $("input[name='termCode']").val();
		$.ajax({
			url : url,
			data : params,
			type : "post",
			success : function(data) {
				alert(data);
			},
			error : function() {
				alert("error");
			}
		});
	}
</script>
</head>
<body>
<div>
	<div>
		<form id="searchForm" name="searchForm" action="http://localhost:8080/seatReserve/queryAllReserveTicketInfo/">
		<table><tbody><tr>
			<th width="120px;">输入：</th>
			<td>
				<input type="hidden" id="pnrNo" name="termType" value="PNR" />
				<input type="text" id="termCode" name="termCode" value="PBL5EQ" />
				<font color="red">*</font>
				<a href="javascript:void(0);" class="searchBtn" onclick="submitForm()"><span>查询</span></a>
				<input type="submit" value="提交" />
				<a href="javascript:void(0);" class="searchBtn" onclick="submitFormByAjax()"><span>Ajax查询</span></a>
			</td>
		</tr></tbody></table>
		</form>
	</div>
</div>
</body>
</html>
