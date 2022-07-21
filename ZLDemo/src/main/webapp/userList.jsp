<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="http://localhost:8080/ZLDemo/addUser" method="POST">
	<input type="text" name="userName" />
	<input type="text" name="password" />
	<button type="submit">添加</button>
</form>
<table width="580" border="1">
	<thead>
		<tr>
			<th>序号</th>
			<th>姓名</th>
			<th>密码</th>
			<th>头像</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${users}" var="user" varStatus="status">
			<tr>
				<td align="center"><c:out value="${status.index + 1}"></c:out></td>
				<td align="center"><c:out value="${user.user_name}"></c:out></td>
				<td align="center"><c:out value="${user.user_password}"></c:out></td>
				<td align="center"><img width="50px" height="50px" alt="${user.user_name}" src="http://localhost:8080/ZLDemo/downloadImage2?file=${user.user_portrait}"></td>
				<td align="center">
					<form action="http://localhost:8080/ZLDemo/getUserInfo" method="POST">
						<input type="hidden" name="userId" value="${user.user_id}" />
						<button type="submit">修改</button>
					</form>
					<form action="http://localhost:8080/ZLDemo/deleteUser" method="POST">
						<input type="hidden" name="userId" value="${user.user_id}" />
						<button type="submit">删除</button>
					</form>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>

</body>
</html>