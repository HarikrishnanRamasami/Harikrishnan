/* 
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
function getParameter(e){
    try{var r=new URLSearchParams(window.location.search).get(e);return decodeURIComponent(r)}catch(e){}
    var a=RegExp("[?&]"+e+"=([^&]*)").exec(window.location.search);return a&&decodeURIComponent(a[1].replace(/\+/g," "))
}
$.when(
    $.ajax({
        type: "GET",
        url: "https://app.engati.com/static/js/chat_widget.js",
        dataType: "script",
        cache: true
    })
).then(function () {
    var k = $('script[src*="engati.js"]').data('key');
    EngtChat.init({"bot_key": k, "e": "p", "bot_name": "Ask Anoud CRM", "welcome_msg": true, "use_icon_dimensions": true});
});
$('<style>').text('#engt-container .engt-sheet{width: 80% !important;}#engt-container .engt-composer {background-color: #d4d7d9 !important;}#engt-container .engt-comment-body {padding: 6px 8px;}#engt-container .engt-sheet-content-container {max-width: inherit !important;}').appendTo('body');
!function engt() {
    $("#engt-container #txMessage").length > 0 ? EngtChat.updatePlaceholder("Type a message") : setTimeout(engt, 1e3)
}();