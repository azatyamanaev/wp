<!doctype html>
<#import "spring.ftl" as spring/>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title><@spring.message 'sign_up.page.title'/></title>
</head>
<body>
<form action="/signUp" method="post">
    <input name="login" placeholder="<@spring.message 'pages.placeholder.login'/>">
    <input type="email" name="email" placeholder="<@spring.message 'pages.placeholder.email'/>">
    <input type="password" name="password" placeholder="<@spring.message 'pages.placeholder.password'/>">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <input type="submit" value="<@spring.message 'sign_up.page.submit'>">
</form>
<p><@spring.message 'sign_up.page.message'/></p>
<a href="/logIn"><@spring.message 'sign_up.page.link.login'/></a>
</body>
</html>