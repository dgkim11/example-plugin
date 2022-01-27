<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <title>Login Page</title>
</head>
<body>
<h2>로그인 하세요</h2>
<#if errorMessage?has_content>
    <H3>${errorMessage}</H3>
</#if>
    <form action="/login"method="POST">
        <input type="text" name="username">
        <input type="password" name="password">
        <input type="submit" name="Login">
    </form>
</body>