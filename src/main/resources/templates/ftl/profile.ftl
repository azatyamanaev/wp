<!doctype html>
<#import "spring.ftl" as spring/>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title><@spring.message 'profile.page.title'/></title>
</head>
<style>
    .error {
        color: #ff0000;
    }
</style>
<body>
<h1>${user.login}</h1>
<h1>${user.email}</h1>
<div>
    <@spring.bind "profileForm"/>
    <form action="/profile" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        <@spring.message 'profile.page.login'/>:<br>
        <@spring.formInput "profileForm.login"/>
        <@spring.showErrors "<br>", "error"/>
        <br><br>
        <@spring.message 'profile.page.email'/>:<br>
        <@spring.formInput "profileForm.email"/>
        <@spring.showErrors "<br>", "error"/>
        <input type="submit" value="<@spring.message 'profile.page.submit'/>">
    </form>
</div>
</body>
</html>