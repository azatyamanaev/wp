<!DOCTYPE html>
<#import "spring.ftl" as spring/>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">
    <script
            src="https://code.jquery.com/jquery-3.4.1.min.js"
            integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
            crossorigin="anonymous"></script>
    <script src="http://knockoutjs.com/downloads/knockout-3.4.0.js"></script>
    <title><@spring.message 'chat.page.title'/></title>
</head>
<body onload="joinChat()">

<input id="user_login" type="hidden" value="${user.login}">

<p>
    <input id="message" name="message" type="text"/>
    <button id="post" type="submit" onclick="postMessage()"><@spring.message 'chat.page.post'/></button>
</p>

<ul id="messages"></ul>

<form id="joinChatForm" action="/messages" aria-hidden="true">
    <p>
        <input name="messageIndex" type="hidden" data-bind="value: messageIndex"/>
    </p>
</form>

<script type="text/javascript">

    let that = this;

    let viewModel = {
        messageIndex: ko.observable(0)
    }

    that.joinChat = function () {
        pollForMessages();
    }

    function pollForMessages() {
        let form = $("#joinChatForm");
        $.ajax({
            url: form.attr("action"), type: "GET", data: form.serialize(), cache: false,
            success: function (messages) {
                for (let i = 0; i < messages.length; i++) {
                    $('#messages').first().after('<li>' + "[" + messages[i]['login'] + "]" + messages[i]['text'] + '</li>');
                    viewModel.messageIndex(viewModel.messageIndex() + 1);
                }
            },
            complete: pollForMessages
        });
        $('#message').focus();
    }

    function postMessage() {
        let token = $("meta[name='_csrf']").attr("content");
        let header = $("meta[name='_csrf_header']").attr("content");
        $.ajax({
            beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
            },
            url: "/messages",
            method: "POST",
            data: "message=" + $('#message').val() + "&login=" + $('#user_login').val(),
            error : function(xhr) {
                console.error("Error posting chat message: status=" + xhr.status + ", statusText=" + xhr.statusText);
            }
        });
    }

    ko.applyBindings(viewModel);
</script>
</body>
</html>