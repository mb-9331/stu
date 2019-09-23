<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文件上传</title>
</head>
<body>
	<form action="http://localhost:8091/file"
	method="post" enctype="multipart/form-data">
	文件：<input name="pic" type="file"/>
	<input type="submit" value="提交"/>
	</form>
</body>
</html>