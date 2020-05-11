<html>
<head>
    <TITLE>Crunchify - Spring MVC Example with AJAX call</TITLE>

    <style type="text/css">
        body {
            background-image:
                    url('https://cdn.crunchify.com/wp-content/uploads/2013/03/Crunchify.bg_.300.png');
        }
    </style>
    <script type="text/javascript"
            src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
    <script type="text/javascript">
        function crunchifyAjax() {
            $.ajax({
                url : '/ajaxtest',
                success : function(data) {
                    $('#result').html(data);
                }
            });
        }
    </script>


</head>

<body>
<button onclick="crunchifyAjax()">randomNumber</button>
<div align="center">
    <br> <br> ${message} <br> <br>
    <div id="result"></div>
    <br>
    <p>
        by <a href="https://crunchify.com">Crunchify.com</a>
    </p>
</div>
</body>
</html>