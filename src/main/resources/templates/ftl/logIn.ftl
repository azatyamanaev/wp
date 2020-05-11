<!doctype html>
<#import "spring.ftl" as spring/>
<html lang="en">
<head>
    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/>
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title><@spring.message 'login.page.title'/></title>
</head>
<body>
<form action="/logIn" method="post">
    <input name="login" placeholder="<@spring.message 'pages.placeholder.login'/>">
    <input type="password" name="password" placeholder="<@spring.message 'pages.placeholder.password'/>">
    <label>
        <input type="checkbox" name="remember-me"><@spring.message 'login.page.remember_me'/>
    </label>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <input type="submit" value="<@spring.message 'login.page.submit'/>">
</form>
<p><@spring.message 'login.page.message'/></p>
<a href="/signUp"><@spring.message 'login.page.link.sign_up'/></a>
</body>
</html>