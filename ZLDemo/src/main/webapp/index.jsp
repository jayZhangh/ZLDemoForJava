<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
Hello World!<br />
世界，你好！
<form action="http://localhost:8080/ZLDemo/uploadImage" method="post" enctype="multipart/form-data">
	姓名：<input type="text" name="userName" /><br />
	图片1：<input type="file" name="pic1" /><br />
	图片2：<input type="file" name="pic2" /><br />
	图片3：<input type="file" name="pic3" /><br />
	<button type="submit">提交</button>
</form>
</body>
</html>