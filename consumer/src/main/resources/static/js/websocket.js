$(function () {
    wsUri ="ws://127.0.0.1:9003/orange?userId=1";

});
function disconnect() {
    setConnected(false);
    websocket.close();
}
function setConnected(connected)
{
    $("#connect").attr("disabled",connected);
    $("#disconnect").attr("disabled",!connected);
    if(!connected) {
        $("#inputDiv").hide();
    }else{
        $("#inputDiv").show();
    }
    $("#reponse").html("");
}

function connect() {

    setConnected(true);
    websocket = new WebSocket(wsUri);
    websocket.onmessage = function(evt) {
        console.log("接收到消息");
        onMessage(evt)
    };
    websocket.onopen = function(evt) {
        console.log("连接已经建立");
        onOpen(evt)
    };
    websocket.onclose = function(evt) {
        console.log("连接已经关闭");
        onClose(evt)
    };

    websocket.onerror = function(evt) {
        console.log("发生了错误");
        onError(evt)
    };
}

function sendName() {
    doSend($("#name").val())
    
}

function onOpen(evt) {
    writeToScreen("socket连接已经建立");
    doSend("WebSocket rocks");
}

function onClose(evt) {
    writeToScreen("socket连接已经断开");
}

function onMessage(evt) {
    console.log(evt);
    $("#reponse").html(evt.data);
    writeToScreen('<span style="color: blue;">服务端的响应: '+ evt.data+'</span>');
}

function onError(evt) {
    writeToScreen('<span style="color: red;">服务端的错误:</span> '+ evt.data);
}

function doSend(message) {
    writeToScreen("发送的消息: " + message);
    websocket.send(message);
}

function writeToScreen(message) {
    var pre = document.createElement("p");
    pre.style.wordWrap = "break-word";
    pre.innerHTML = message;
    output.appendChild(pre);
}
