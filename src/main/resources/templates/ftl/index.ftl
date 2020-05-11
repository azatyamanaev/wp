<!doctype html>
<#import "spring.ftl" as spring/>
<html>
  <head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title><@spring.message 'pages.title'/></title>
    <title></title>
  </head>
  <body>
  <button onclick="location.href='/profile'"><@spring.message 'index.page.profile'/></button>
  <button onclick="location.href='/uploadFiles'"><@spring.message 'index.page.upload_files'/></button>
  <button onclick="location.href='/chat'"><@spring.message 'index.page.chat'/></button>
  </body>
</html>
