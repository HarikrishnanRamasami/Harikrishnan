<%-- 
    Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
    * All Rights Reserved
    * Unauthorized copying of this file, via any medium is strictly prohibited

    Document   : ameyoLogin
    Created on : Feb 22, 2018, 7:12:19 AM
    Author     : ravindar.singh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<div class="col-md-12 right-pad">
    <div class="dash-leads" style="border-top:0!important">
        <div class="my-bord">
            <h3><s:text name="lbl.ameyo.wallboard.login"/></h3>
        </div>
        <div id="msg_ameyo_agent" class="alert alert-danger" style="display: none;"></div>
        <div class="my-bord bor2">
            <div class="col-sm-12 col-md-9 Tmar">
                <s:form class="form-inline" id="frm_ameyo" name="frm_ameyo" method="post" theme="simple">
                    <div class="form-group padR">
                        <label><s:text name="lbl.ameyo.user.id"/></label>
                        <s:textfield name="ameyoUser" id="ameyoUser" cssClass="form-control"/>
                    </div>
                    <div class="form-group padR">
                        <label><s:text name="lbl.ameyo.password"/></label>
                        <s:textfield name="ameyoPasswd" id="ameyoPasswd" cssClass="form-control" type="password"/>
                    </div>
                    <div class="form-group padR">
                        <button type="button" id="search_task" class="btn btn-primary tmargin cbtn" onclick="wallboardLogin();" style="margin-top: 1px;"><s:text name="btn.login"/></button>
                    </div>
                </s:form>
            </div>
        </div>
    </div>
</div>    
<script type="text/javascript">
    $(document).ready(function () {
        $('[data-toggle="tooltip"]').tooltip();
    });

    function wallboardLogin() {
        var userId = $.trim($("#ameyoUser").val());
        var password = $.trim($("#ameyoPasswd").val());
        if (userId === "") {
            $("#msg_ameyo_agent").html("Please enter user id").show();
            return false;
        }
        if (password === "") {
            $("#msg_ameyo_agent").html("Please enter password").show();
            return false;
        }
        block('block_body');
        $.ajax({
            type: "GET",
            dataType: "json",
            url: URL_WALLBOARD,
            data: {command: "force-login", data: '{"password": '+password+', "terminal": "<%= request.getRemoteAddr()%>", "userId": '+userId+'}'},
            success: function (result) {
                console.log(JSON.stringify(result));
                if(result.message) {
                    $("#msg_ameyo_agent").html(result.message).show();
                    return;
                } else {
                    localStorage.setItem("USER_INFO", JSON.stringify(result));
                    $("#block_body").load(APP_CONFIG.context + "/wallboardPage.do", {appType: "wallboard", sessionId: result.sessionId}, function() {
                        $("#agent_details").show();
                    });
                }
            },
            error: function (xhr, status, error) {
                $("#msg_ameyo_agent").html("Opps! something went wrong").show();
            },
            complete: function () {
                unblock('block_body');
            }
        });
    }
</script>