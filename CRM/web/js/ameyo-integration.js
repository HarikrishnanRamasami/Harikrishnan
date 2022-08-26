// attach postMessage event to handler
if (window.attachEvent) {
    window.attachEvent('onmessage', processPostMessage);
} else {
    window.addEventListener('message', processPostMessage, false);
}

// Registration of custom Functions
var customFunctions = new Array();

function registerCustomFunction(key, value) {
    customFunctions[key] = value;
}

function processPostMessage(event) {
    /*
     * if (event.origin !== "http://localhost:8888/") { return; }
     */
    var theObject = JSON.parse(event.data);
    console.log("method: "+theObject.method);
    if (theObject.method == 'showCrm') {
        var phone = theObject.phone;
        var additionalParams = theObject.additionalParams;
        if (customFunctions['showCrm']) {
            customFunctions['showCrm'].showCrm(phone, additionalParams);
        } else {
            showCrm(phone, additionalParams);
        }
    } else if (theObject.method == 'intializeUI') {
        try {
            var uiElementIds = theObject.uiElementIds;
            var disabledUiIds = configureUI(uiElementIds);
            hideUI(disabledUiIds);
        } catch (e) {
        }
    } else if (theObject.method == 'intializeLoginCredentials') {
            ameyoLogin();
        try {
            var loginCredential = getLoginInfo();
            setLoginInfo(loginCredential);
        } catch (e) {
        }
    } else if (theObject.method == 'intializeExtensionInfo') {
        try {
            var extensionInfo = getExtensionInfo();
            setExtensionInfo(extensionInfo);
        } catch (e) {
        }
    } else if (theObject.method == 'logoutHandler') {
        var reason = theObject.reason;
        if (customFunctions['logoutHandler']) {
            customFunctions['logoutHandler'].logoutHandler(reason);
        } else {
            logoutHandler(reason);
        }
    } else if (theObject.method == 'loginHandler') {
        var reason = theObject.reason;
        if (customFunctions['loginHandler']) {
            customFunctions['loginHandler'].loginHandler(reason);
        } else {
            loginHandler(reason);
        }
    } else if (theObject.method == 'onLoadHandler') {
        if (customFunctions['onLoadHandler']) {
            customFunctions['onLoadHandler'].onLoadHandler();
        } else {
            onLoadHandler();
        }
    } else if (theObject.method == 'loginStatusHandler') {
        var reason = theObject.reason;
        if (customFunctions['loginStatusHandler']) {
            customFunctions['loginStatusHandler'].loginStatusHandler(reason);
        } else {
            loginStatusHandler(reason);
        }
    } else if (theObject.method == 'forceLoginHandler') {
        var reason = theObject.reason;
        if (customFunctions['forceLoginHandler']) {
            customFunctions['forceLoginHandler'].forceLoginHandler(reason);
        } else {
            forceLoginHandler(reason);
        }
    } else if (theObject.method == 'selectExtensionHandler') {
        var reason = theObject.reason;
        if (customFunctions['selectExtensionHandler']) {
            customFunctions['selectExtensionHandler']
                    .selectExtensionHandler(reason);
        } else {
            selectExtensionHandler(reason);
        }
    } else if (theObject.method == 'modifyExtensionHandler') {
        var reason = theObject.reason;
        if (customFunctions['modifyExtensionHandler']) {
            customFunctions['modifyExtensionHandler']
                    .modifyExtensionHandler(reason);
        } else {
            modifyExtensionHandler(reason);
        }

    } else if (theObject.method == 'selectCampaignHandler') {
        var reason = theObject.reason;
        if (customFunctions['selectCampaignHandler']) {
            customFunctions['selectCampaignHandler']
                    .selectCampaignHandler(reason);
        } else {
            selectCampaignHandler(reason);
        }
    } else if (theObject.method == 'autoCallOnHandler') {
        var reason = theObject.reason;
        if (customFunctions['autoCallOnHandler']) {
            customFunctions['autoCallOnHandler'].autoCallOnHandler(reason);
        } else {
            autoCallOnHandler();
        }
    } else if (theObject.method == 'autoCallOffHandler') {
        var reason = theObject.reason;
        if (customFunctions['autoCallOffHandler']) {
            customFunctions['autoCallOffHandler'].autoCallOffHandler(reason);
        } else {
            autoCallOffHandler();
        }
    } else if (theObject.method == 'readyHandler') {
        var reason = theObject.reason;
        if (customFunctions['readyHandler']) {
            customFunctions['readyHandler'].readyHandler(reason);
        } else {
            readyHandler(reason);
        }
    } else if (theObject.method == 'breakHandler') {
        var reason = theObject.reason;
        if (customFunctions['breakHandler']) {
            customFunctions['breakHandler'].breakHandler(reason);
        } else {
            breakHandler(reason);
        }
    } else if (theObject.method == 'hangupHandler') {
        var reason = theObject.reason;
        if (customFunctions['hangupHandler']) {
            customFunctions['hangupHandler'].hangupHandler(reason);
        } else {
            hangupHandler(reason);
        }
    } else if (theObject.method == 'disposeCall') {
        if (customFunctions['disposeCall']) {
            customFunctions['disposeCall'].disposeCall();
        } else {
            disposeCall();
        }
    } else if (theObject.method == 'transferToPhoneHandler') {
        var reason = theObject.reason;
        if (customFunctions['transferToPhoneHandler']) {
            customFunctions['transferToPhoneHandler']
                    .transferToPhoneHandler(reason);
        } else {
            transferToPhoneHandler(reason);
        }
    } else if (theObject.method == 'transferInCallHandler') {
        var reason = theObject.reason;
        if (customFunctions['transferInCallHandler']) {
            customFunctions['transferInCallHandler']
                    .transferInCallHandler(reason);
        } else {
            transferInCallHandler(reason);
        }
    } else if (theObject.method == 'transferToAQHandler') {
        var reason = theObject.reason;
        if (customFunctions['transferToAQHandler']) {
            customFunctions['transferToAQHandler'].transferToAQHandler(reason);
        } else {
            transferToAQHandler(reason);
        }
    } else if (theObject.method == 'transferToIVRHandler') {
        var reason = theObject.reason;
        if (customFunctions['transferToIVRHandler']) {
            customFunctions['transferToIVRHandler']
                    .transferToIVRHandler(reason);
        } else {
            transferToIVRHandler(reason);
        }
    } else if (theObject.method == 'transferToUserHandler') {
        var reason = theObject.reason;
        if (customFunctions['transferToUserHandler']) {
            customFunctions['transferToUserHandler']
                    .transferToUserHandler(reason);
        } else {
            transferToUserHandler(reason);
        }
    } else if (theObject.method == 'transferToCampaignHandler') {
        var reason = theObject.reason;
        if (customFunctions['transferToCampaignHandler']) {
            customFunctions['transferToCampaignHandler']
                    .transferToCampaignHandler(reason);
        } else {
            transferToCampaignHandler(reason);
        }
    } else if (theObject.method == 'conferWithPhoneHandler') {
        var reason = theObject.reason;
        if (customFunctions['conferWithPhoneHandler']) {
            customFunctions['conferWithPhoneHandler']
                    .conferWithPhoneHandler(reason);
        } else {
            conferWithPhoneHandler(reason);
        }
    } else if (theObject.method == 'conferWithTPVHandler') {
        var reason = theObject.reason;
        if (customFunctions['conferWithTPVHandler']) {
            customFunctions['conferWithTPVHandler']
                    .conferWithTPVHandler(reason);
        } else {
            conferWithTPVHandler(reason);
        }
    } else if (theObject.method == 'conferWithUserHandler') {
        var reason = theObject.reason;
        if (customFunctions['conferWithUserHandler']) {
            customFunctions['conferWithUserHandler']
                    .conferWithUserHandler(reason);
        } else {
            conferWithUserHandler(reason);
        }
    } else if (theObject.method == 'conferWithLocalIVRHandler') {
        var reason = theObject.reason;
        if (customFunctions['conferWithLocalIVRHandler']) {
            customFunctions['conferWithLocalIVRHandler']
                    .conferWithLocalIVRHandler(reason);
        } else {
            conferWithLocalIVRHandler(reason);
        }
    } else if (theObject.method == 'getDispositionCodesHandler') {
        var reason = theObject.dispositionCodes;
        if (customFunctions['getDispositionCodesHandler']) {
            customFunctions['getDispositionCodesHandler']
                    .getDispositionCodesHandler(reason);
        } else {
            handleGetDispositionCodes(reason);
        }
    } else if (theObject.method == 'disposeCallByDispositionCodeHandler') {
        var reason = theObject.reason;
        if (customFunctions['disposeCallByDispositionCodeHandler']) {
            customFunctions['disposeCallByDispositionCodeHandler']
                    .disposeCallByDispositionCodeHandler(reason);
        } else {
            handleDisposeCall(reason);
        }
    } else if (theObject.method == 'disposeAndDialHandler') {
        var reason = theObject.reason;
        if (customFunctions['disposeAndDialHandler']) {
            customFunctions['disposeAndDialHandler']
                    .handleDisposeAndDial(reason);
        } else {
            handleDisposeAndDial(reason);
        }
    }
}

function doPostMessage(message) {
    var theIframe = document.getElementById("ameyoIframe");
    theIframe.contentWindow.postMessage(message, APP_CONFIG.ameyo.url);
}

function openAmeyoToolbar() {
    if($("#softPhone .softPhone").css("right") !== "0px") {
        $("#softPhoneIcon").trigger("click");
    }
}

function doLogin(username, password, authPolicy) {
    authPolicy = authPolicy ? authPolicy : 'auth.type.crm.http';
    var theObject = {
        method: 'doLogin',
        username: username,
        password: password,
        authPolicy: authPolicy
    };
    var message = JSON.stringify(theObject);
    console.log("Login => message: " + message);
    doPostMessage(message);
}

function doForceLogin(username, password, authPolicy, toolbarLayout) {
    var theObject = {
        method: 'doForceLogin',
        username: username,
        password: password,
        authPolicy: authPolicy,
        toolbarLayout: toolbarLayout
    };
    var message = JSON.stringify(theObject);
    doPostMessage(message);
}

function doLogout() {
    var theObject = {
        method: 'doLogout'
    };
    var message = JSON.stringify(theObject);
    doPostMessage(message);
    /*$("#ameyoIframe").prop("src", APP_CONFIG.ameyo.url + 'ameyowebaccess/logout').load(function() {ameyoLogout();});*/
}

function disposeNDial(dispositionCode, phone) {
    if (typeof dispositionCode == 'undefined') {
        alert('no disposition code to dispose call');
        return;
    }

    if (typeof dispositionCode == 'Callback') {
        alert("disposition code cannot be Callback");
        return;
    }

    if (typeof phone == 'undefined') {
        alert('no number to dial');
        return;
    }
    var theObject = {
        method: 'disposeNDial',
        dispositionCode: dispositionCode,
        phone: phone,
    };
    var message = JSON.stringify(theObject);
    doPostMessage(message);
}

function getDispositionCodes(campaignId) {
    var theObject = {
        method: 'getDispositionCodes',
        campaignId: campaignId,
    };
    var message = JSON.stringify(theObject);
    doPostMessage(message);
}

function disposeCallByDisposition(dispositionCode) {
    if (typeof dispositionCode == 'undefined') {
        alert('no disposition code to dispose call');
        return;
    }

    var theObject = {
        method: 'disposeCallByDisposition',
        dispositionCode: dispositionCode,
    };
    var message = JSON.stringify(theObject);
    doPostMessage(message);
}

function populateNumberInDialBox(phone) {
    var theObject = {
        method: 'populateNumberInDialBox',
        phone: phone,
    };
    var message = JSON.stringify(theObject);
    doPostMessage(message);
}

function doDial(phone, addlParams, searchable, customerRecords) {
    console.log("Dialing... "+phone);
    addlParams = addlParams ? JSON.parse(addlParams) : {"callType":"click_to_dial"};
    searchable = searchable ? searchable : "";
    var theObject = {
        method: 'doDial',
        phone: "9" + phone,
        additionalParams: addlParams,
        searchable: searchable
    };
    if(customerRecords) {
        theObject["customerRecords"] = customerRecords;
    }
    var message = JSON.stringify(theObject);
    doPostMessage(message);
    if(addlParams.callType === "click_to_dial") {
        openAmeyoToolbar();
    }
}

function setLoginInfo(loginCredential) {
    var theObject = {
        method: 'setLoginCredentials',
        userId: loginCredential.userName,
        password: loginCredential.password
    };
    var message = JSON.stringify(theObject);
    doPostMessage(message);
}

function setExtensionInfo(extensionInfo) {
    var theObject = {
        method: 'setExtensionMetadata',
        extensionName: extensionInfo.name,
        extensionPhone: extensionInfo.phone
    };
    var message = JSON.stringify(theObject);
    doPostMessage(message);

}
function hideUI(uiElements) {
    var theObject = {
        method: 'configureUI',
        uiElements: uiElements
    };
    var message = JSON.stringify(theObject);
    doPostMessage(message);
}

function checkAmeyoSession(reason) {
    if(reason === "success") {
        ls.setItem('CRM_CALL_CENTER', APP_CONFIG.messageType.success);
    } else if(reason !== "") {
        ls.setItem('CRM_CALL_CENTER', APP_CONFIG.messageType.error);
    }
    APP_CONFIG.ameyo.loggedIn = (ls.getItem('CRM_CALL_CENTER') === APP_CONFIG.messageType.success ? APP_CONFIG.messageType.success : APP_CONFIG.messageType.error);
}

function loginHandler(reason) {
    console.log("logged In: " + reason);
    if(reason.startsWith("forceLoginRequired")){
        doForceLogin();
    }
}

function forceLoginHandler(reason) {
    console.log("force logged In: " + reason);
}

function logoutHandler(reason) {
    console.log("logout: " + reason);
    ameyoLogout();
}

function onLoadHandler() {
    console.log("Loaded");
}

function loginStatusHandler(status) {
    console.log("Login Status:" + status);
}

function handleSelectCampaign(reason) {
    console.log("Select Campaign:" + reason);
    var extensionInfo = {
        name : APP_CONFIG.ameyo.channel,
        phone : APP_CONFIG.extension
    };
    setExtensionInfo(extensionInfo);
}

var popWin = null;
function customShowCrm(phone, addlParams) {
    console.log(addlParams);
    addlParams = JSON.parse(addlParams);
    console.log(ss.getItem('CRM_POPUP'));
    if(ss.getItem('CRM_POPUP') !== addlParams.crtObjectId) {
        ss.setItem('CRM_POPUP', addlParams.crtObjectId);
        if(addlParams.callType === "inbound.call.dial") {
            if(popWin == null || popWin.closed) {

            } else {
                popWin.close();
            }
            $.ajax({
                type: "POST",
                url: APP_CONFIG.context + "/saveActivityIncomingCall.do",
                data: {company: APP_CONFIG.companyCode, mobile: phone, crtObjectId: addlParams.crtObjectId},
                success: function () {
                    openCallFeedback(phone);
                },
                complete: function () {
                    popWin = window.open(APP_CONFIG.context + "/customer360.do?company=" + APP_CONFIG.companyCode + "&mobile=" + phone + "&crtObjectId=" + addlParams.crtObjectId, "customer360", APP_CONFIG.popupFeatures);
                    popWin.moveTo(0, 0);
                    popWin.resizeTo(screen.width, screen.height);
                    popWin.focus();
                }
            });
        }
    }
    openAmeyoToolbar();
}

function selectExtensionHandler(status) {
    console.log("Select Extention:" + status);
}
function handleModifyExtension(status) {
    console.log("Modify Extention:" + status);
}
function handleAutoCallOn(status) {
    console.log("Auto Call On:" + status);
}
function handleAutoCallOff(status) {
    console.log("Auto Call Off:" + status);
}
function handleReady(status) {
    console.log("Ready :" + status);
}
function handleBreak(status) {
    console.log("Break :" + status);
}
function handleHangup(reason) {
    console.log("Hangup :" + reason);
}
function handleTransferToPhone(reason) {
    console.log("Transfer to Phone :" + reason);
}
function handleTransferInCall(reason) {
    console.log("Transfer in Call :" + reason);
}
function handleTransferToAQ(reason) {
    console.log("Transfer to AQ :" + reason);
}
function handleTransferToIVR(reason) {
    console.log("Transfer to IVR :" + reason);
}
function handleTransferToUser(reason) {
    console.log("Transfer to user :" + reason);
}
function handleTransferToCampaign(reason) {
    console.log("Transfer to campaign :" + reason);
}
function handleConferWithPhone(reason) {
    console.log("Confer With Phone :" + reason);
}
function handleConferWithTPV(reason) {
    console.log("Confer With TPV :" + reason);
}
function handleConferWithUser(reason) {
    console.log("Confer With User :" + reason);
}
function handleConferWithLocalIVR(reason) {
    console.log("Confer With Local IVR :" + reason);
}
function handleDisposeAndDial(reason) {
    console.log("handleDisposeAndDial: ", reason);
}
function disposeCallByDispositionCodeHandler(reason) {
    console.log("disposeCallByDispositionCodeHandler: ", reason);
}

customIntegration = {};
customIntegration.showCrm = customShowCrm;
customIntegration.loginHandler = loginHandler;
customIntegration.forceLoginHandler = forceLoginHandler;
customIntegration.logoutHandler = logoutHandler;
customIntegration.onLoadHandler = onLoadHandler;
customIntegration.loginStatusHandler = loginStatusHandler;
customIntegration.selectExtensionHandler = selectExtensionHandler;
customIntegration.modifyExtensionHandler = handleModifyExtension;
customIntegration.selectCampaignHandler = handleSelectCampaign;
customIntegration.autoCallOnHandler = handleAutoCallOn;
customIntegration.autoCallOffHandler = handleAutoCallOff;
customIntegration.readyHandler = handleReady;
customIntegration.breakHandler = handleBreak;
customIntegration.hangupHandler = handleHangup;
customIntegration.transferToPhoneHandler = handleTransferToPhone;
customIntegration.transferInCallHandler = handleTransferInCall;
customIntegration.transferToAQHandler = handleTransferToAQ;
customIntegration.transferToIVRHandler = handleTransferToIVR;
customIntegration.transferToUserHandler = handleTransferToUser;
customIntegration.transferToCampaignHandler = handleTransferToCampaign;
customIntegration.conferWithPhoneHandler = handleConferWithPhone;
customIntegration.conferWithTPVHandler = handleConferWithTPV;
customIntegration.conferWithUserHandler = handleConferWithUser;
customIntegration.conferWithLocalIVRHandler = handleConferWithLocalIVR;
customIntegration.handleDisposeAndDial = handleDisposeAndDial;
customIntegration.disposeCallByDispositionCodeHandler = disposeCallByDispositionCodeHandler;

registerCustomFunction("showCrm", customIntegration);
registerCustomFunction("loginHandler", customIntegration);
registerCustomFunction("forceLoginHandler", customIntegration);
registerCustomFunction("logoutHandler", customIntegration);
registerCustomFunction("onLoadHandler", customIntegration);
registerCustomFunction("loginStatusHandler", customIntegration);
registerCustomFunction("selectCampaignHandler", customIntegration);
registerCustomFunction("modifyExtensionHandler", customIntegration);
registerCustomFunction("autoCallOnHandler", customIntegration);
registerCustomFunction("autoCallOffHandler", customIntegration);
registerCustomFunction("readyHandler", customIntegration);
registerCustomFunction("breakHandler", customIntegration);
registerCustomFunction("hangupHandler", customIntegration);
registerCustomFunction("transferToPhoneHandler", customIntegration);
registerCustomFunction("transferInCallHandler", customIntegration);
registerCustomFunction("transferToAQHandler", customIntegration);
registerCustomFunction("transferToIVRHandler", customIntegration);
registerCustomFunction("transferToUserHandler", customIntegration);
registerCustomFunction("transferToCampaignHandler", customIntegration);
registerCustomFunction("conferWithPhoneHandler", customIntegration);
registerCustomFunction("conferWithTPVHandler", customIntegration);
registerCustomFunction("conferWithUserHandler", customIntegration);
registerCustomFunction("conferWithLocalIVRHandler", customIntegration);
registerCustomFunction("disposeAndDialHandler", customIntegration);
registerCustomFunction("disposeCallByDispositionCodeHandler", customIntegration);