<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="http://localhost:8080/ZLDemo/updateUser" method="POST" enctype="multipart/form-data">
	<input type="hidden" name="userId" value="${userInfo.user_id}" />
	账号：<input type="text" name="userName" value="${userInfo.user_name}" /><br />
	密码：<input type="text" name="password" value="${userInfo.user_password}" /><br />
	头像：<input type="file" name="portrait" />
	<button type="submit">修改</button>
</form>
<br /><br />
<img width="200px" height="200px" alt="${userInfo.user_name}" src="http://localhost:8080/ZLDemo/downloadImage?file=${userInfo.user_portrait}" />
</body>
</html>