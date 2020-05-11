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
    $.ajax({
        url: "/messages",
        method: "POST",
        data: "message=" + $('#message').val() + "&login=" + $('#user_login').val(),
        error : function(xhr) {
            console.error("Error posting chat message: status=" + xhr.status + ", statusText=" + xhr.statusText);
        }
    });
}

ko.applyBindings(viewModel);