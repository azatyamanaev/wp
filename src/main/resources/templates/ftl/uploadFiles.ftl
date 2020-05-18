<!doctype html>
<#import "spring.ftl" as spring/>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <script
            src="https://code.jquery.com/jquery-3.4.1.js"
            integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
            crossorigin="anonymous"></script>
    <title><@spring.message 'pages.title'/></title>
    <script>
        function sendFile() {
            let token = $("meta[name='_csrf']").attr("content");
            let header = $("meta[name='_csrf_header']").attr("content");
            let formData = new FormData();
            let files = ($('#file'))[0]['files'];
            [].forEach.call(files, function (file, i, files) {
                formData.append("file", file);
            });

            $.ajax({
                beforeSend: function(xhr) {
                    xhr.setRequestHeader(header, token);
                },
                type: "POST",
                url: "http://localhost:8080/files",
                data: formData,
                processData: false,
                contentType: false,
                cache: false
            })
                .done(function (response) {
                    alert(response)
                })
                .fail(function () {
                    alert(<@spring.message 'upload_files.page.error'/>)
                });
        }
    </script>
</head>
<body>
<div>
    <input type="file" id="file" name="file" placeholder="<@spring.message 'upload_files.page.file_name'/>"/>
    <button onclick="sendFile()">
        <@spring.message 'upload_files.page.download_file'/>
    </button>
    <input type="hidden" id="file_hidden">
    <div class="filename"></div>
</div>
</body>
</html>
