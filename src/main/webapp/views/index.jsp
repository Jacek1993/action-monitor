<%@ include file="init.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title><%=appName %></title>
    <script src="<spring:url value="/sockjs.js" />"></script>
    <script src="<spring:url value="/stomp.js" />"></script>
    <script src="<spring:url value="/jquery.min.js" />"></script>
    <script type="text/javascript">
        var stompClient = null;

        function setConnected(connected) {
            $('#connect').attr('disabled',connected);
            $('#disconnect').attr('disabled',!connected);
            if(connected){
                $('#events').show();
            } else {
                $('#events').hide();
            }
            $('#response').html("");
        }

        function connect() {
            var socket = new SockJS('/ws');
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function(frame) {
                setConnected(true);
                console.log('Connected: ' + frame);
                stompClient.subscribe('/topic/actions', function(jsonMessage){
                    showActionMessage(JSON.parse(jsonMessage.body).content);
                });
            });
        }

        function disconnect() {
            if (stompClient != null) {
                stompClient.disconnect();
            }
            setConnected(false);
            console.log("Disconnected");
        }

        function showActionMessage(message) {
            var response = document.getElementById('response');
            var p = document.createElement('p');
            p.style.wordWrap = 'break-word';
            p.appendChild(document.createTextNode(message));
            response.appendChild(p);
        }
    </script>
</head>
<body onload="disconnect()">
<noscript><h2 style="color: #ff0000">Seems your browser doesn't support Javascript! Websocket relies on Javascript being enabled. Please enable
    Javascript and reload this page!</h2></noscript>
<div>
    <div>
        <button id="connect" onclick="connect();">Connect</button>
        <button id="disconnect" disabled="disabled" onclick="disconnect();">Disconnect</button>
    </div>
    <div id="events">
        <p id="response"></p>
    </div>
</div>
</body>
</html>