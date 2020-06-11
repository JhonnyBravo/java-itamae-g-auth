<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home</title>
</head>
<body>
    <a href="${fn:escapeXml(authUrl)}">ログイン</a>
    <a href="https://www.google.com/accounts/Logout" target="_blank">ログアウト</a>
</body>
</html>