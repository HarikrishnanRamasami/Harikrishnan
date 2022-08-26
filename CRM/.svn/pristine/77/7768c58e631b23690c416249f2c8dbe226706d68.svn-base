<%-- 
    Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
    * All Rights Reserved
    * Unauthorized copying of this file, via any medium is strictly prohibited

    Document   : lock
    Created on : Apr 11, 2017, 8:10:15 AM
    Author     : ravindar.singh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<link rel="stylesheet" href="<%=request.getContextPath()%>/plugins/innovate/css/font-awesome.css" />
<body class="login_bg">
    <div class="container-fluid">
        <div class="logout-btn"><button type="button" class="btn bg-red waves-effect" id="btn_logout" style="height: 44px;"><i class="fa fa-power-off" style="float: left; margin-right: 5px;"></i> <span style="line-height: 24px;font-size: 16px;"><s:text name="btn.logout"/></span></button></div>
        <div class="row clearfix">
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 signpage outer">
                <div class="login-page ls-closed middle">
                    <div class="login-box1 inner m-b-100">
                        <div class="logo">
                            <a href="javascript:void(0);"><img src="<%=request.getContextPath()%>/images/logo.png" class="img-responsive" style="max-width: 170px; text-align: center; margin:0 auto;"></a>
                        </div>
                    </div>
                    <div class="row clearfix">
                        <%--div class="col-lg-8 col-md-8 col-sm-12 col-xs-12 col-md-offset-2 col-lg-offset-2">
                         <div id="msg_login" style="display:<s:if test="hasActionErrors()">block</s:if><s:else>none</s:else>;" class="alert alert-warning">
                            <s:iterator value="actionErrors">
                                <s:property escapeHtml="true"/>
                            </s:iterator>
                        </div>
                        </div--%>
                        <div class="col-lg-8 col-md-8 col-sm-12 col-xs-12 col-md-offset-2 col-lg-offset-2" id="login_frm">
                            <p class="usr_img"><img src="<%=request.getContextPath()%>/images/user_mm.png" class="img-responsive"/></p>
                            <div class="signin_out">
                                <s:form id="frm_unlock" action="/unlock.do">                                   
                                    <div class="col-lg-5 col-md-5 col-sm-10 col-xs-10 col-sm-offset-1 col-xs-offset-1 col-md-offset-1 col-lg-offset-1 cst-m-b-15">
                                        <div class="cnt_inside cnt_lft">
                                            <div class="form-group">
                                                <div class="form-line">
                                                    <s:property value="#session.USER_INFO.userName" />
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-lg-5 col-md-5 col-sm-10 col-xs-10 col-sm-offset-1 col-xs-offset-1 col-md-offset-0 cst-m-b-15">
                                        <div class="cnt_inside cnt_ryt">
                                            <div class="form-group">
                                                <div class="form-line">
                                                    <input type="password" class="form-control" name="psswrd" id="psswrd" placeholder="Password" required>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                                <div class="col-lg-1 col-md-1 col-sm-6 col-xs-6 col-lg-offset-0 col-md-offset-0 col-xs-offset-5 col-sm-offset-5" style="padding: 0px;">
                                        <p id="sendlog">
                                            <button type="button" class="btn bg-green waves-effect" id="btn_unlock" style="height: 44px;"><i class="fa fa-unlock"></i>&nbsp;&nbsp;<s:text name="btn.login="/></button>
                                        </p>
                                    </div>
                                </s:form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
<script type="text/javascript">
    $(document).ready(function () {
    <s:if test="hasActionErrors()">
        setTimeout(function () {
            $("#psswrd").effect("bounce", "slow");
        }, 1000);
    </s:if>

        $("#psswrd").on("keypress", function (event) {
            var keyCode = (event.keyCode ? event.keyCode : event.which);
            if (keyCode == 13) {
                $("#btn_unlock").trigger('click');
                event.preventDefault();
            }
        });

        $("#btn_unlock").on("click", function () {
            $("#frm_unlock").submit();
        });

        $("#btn_logout").on("click", function () {
            $("#frm_unlock").attr("action", APP_CONFIG.context + "/logout.do");
            $("#frm_unlock").submit();
        });
    });
</script>