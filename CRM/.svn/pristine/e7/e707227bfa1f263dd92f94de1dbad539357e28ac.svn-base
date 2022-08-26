<%@page import="qa.com.qic.common.util.ApplicationConstants, java.util.Base64"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<html lang="en">
    <head>
        <title><s:text name="lbl.login.crmlogin"/></title>
        <meta charset="utf-8">
        <s:if test="%{@qa.com.qic.common.util.ApplicationConstants$Environment@MVP_QA == @qa.com.qic.common.util.ApplicationConstants@ENVIRONMENT ||
              @qa.com.qic.common.util.ApplicationConstants$Environment@MVP_DEV == @qa.com.qic.common.util.ApplicationConstants@ENVIRONMENT}">
            <link rel="shortcut icon" href="<%=request.getContextPath()%>/images/favicon_at.ico">
        </s:if>
        <s:elseif test="%{@qa.com.qic.common.util.ApplicationConstants@COMPANY_CODE_BEEMA.equals(@qa.com.qic.common.util.ApplicationConstants@DEFAULT_COMPANY_CODE)}">
            <link rel="shortcut icon" href="<%=request.getContextPath()%>/images/favicon_bem.ico">
        </s:elseif>
        <s:elseif test="%{@qa.com.qic.common.util.ApplicationConstants@COMPANY_CODE_MED_DOHA.equals(@qa.com.qic.common.util.ApplicationConstants@DEFAULT_COMPANY_CODE)}">
            <link rel="shortcut icon" href="<%=request.getContextPath()%>/images/favicon_qlm.ico">
        </s:elseif>
        <s:else>
            <link rel="shortcut icon" href="<%=request.getContextPath()%>/images/favicon_qic.ico">
        </s:else>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link a href="<%=request.getContextPath()%>/plugins/innovate/css/bootstrap.min.css" type="text/css" rel="stylesheet">
        <link a href="<%=request.getContextPath()%>/plugins/innovate/css/main.css" type="text/css" rel="stylesheet">
        <link a href="<%=request.getContextPath()%>/plugins/flag/css/flag-icon.min.css" type="text/css" rel="stylesheet">
        <link a href="<%=request.getContextPath()%>/plugins/innovate/css/animate.min.css" type="text/css" rel="stylesheet">
       
        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/jquery/jquery.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/innovate/js/bootstrap.min.js"></script>
        <%
            String id = "", name = "", company = ApplicationConstants.DEFAULT_COMPANY_CODE;
            Cookie cookies[] = request.getCookies();
            for (Cookie cookie : cookies) {
                if ("usrnm".equals(cookie.getName())) {
                    if (cookie.getValue() != null) {
                        String s[] = null;
                        try {
                            s = new String(Base64.getDecoder().decode(cookie.getValue())).split("---");
                        } catch(Exception e) {}
                        if (s != null && s.length == 3) {
                            company = s[0];
                            id = s[1];
                            name = s[2];
                        }
                    }
                    break;
                }
            }
        %>
        <script type="text/javascript">

            $(document).ready(function () {
            <%--var body = $('body');
            setInterval(function () {
                var d = new Date();
                var n = d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds();
                if (n >= '06:00:00' && n <= '11:59:59') {
                    body.css('background-image', 'url(<%=request.getContextPath()%>/plugins/innovate/images/background.jpg)');
                } else if (n >= '12:00:01' && n <= '17:59:59') {
                    body.css('background-image', 'url(<%=request.getContextPath()%>/plugins/innovate/images/bg_nav.jpg)');
                } else if (n >= '18:00:00' && n <= '05:59:59') {
                    body.css('background-image', 'url(<%=request.getContextPath()%>/plugins/innovate/images/login-banner.jpg)');
                }
            }, (1000 * 30));--%>

                $("#frm_reset").hide();
                $("#forgotbtn").click(function () {
                    $("#frm_login").hide();
                    $("#frm_reset").show();
                });
                $("#signin").click(function () {
                    $("#frm_reset").hide();
                    $("#frm_login").show();
                });
                $("#btn_change_user").click(function () {
                    $("input[name='usrnm']").val("").parent().removeClass("hide").parent().children("div:eq(0)").addClass("hide");
                    $("input[name='usrnm']").focus();
                });
                $('.lang [data-name="country"]').click(function () {
                    //$('[data-name="country"]').removeClass("active").addClass("deactive");
                    //$(this).removeClass("deactive").addClass("active");
                    $(this).parents('form').prev().removeClass().addClass("bg-tipTop-login bg-login-" + $(this).data("code"));
                    $("input[name='company']").val($(this).data("code"));
                });
                $('.dropdown-menu li a').click(function () {
                    $(this).parents(".dropdown").find(".dropdown-toggle").html($(this).html() + '<span class="caret" style="margin-top: 9px; float: right;"></span>');
                });
                $('[data-name="country"][data-code="<%=company%>"]').trigger("click");
                $('form').keypress(function (e) {
                    var code = e.keyCode || e.which;
                    if (code === 13) {
                        e.preventDefault();
                        $(this).submit();
                    }
                });

                if ($("input[name='usrnm']").parent().hasClass("hide")) {
                    $("input[name='psswrd']").focus();
                } else {
                    $("input[name='usrnm']").focus();
                }
            });
        </script>

        <style>

            /* Responsive Full Background Image Using CSS
             * Tutorial URL: http://sixrevisions.com/css/responsive-background-image/
            */
 
            .flag-icon {
                height: 30px;
                width: 50px;
                max-width: 100%;
                cursor: pointer;
            }
            .font-12 {
                font-size: 12px;
            }            


            .flag-medical {
                background-size: 80px 75px;
                background-repeat: no-repeat;
                display: inline-block;
                height: 90px;
                width: 80px;
            }
            .flag-medical-qa {
                background-image: url(<%=request.getContextPath()%>/images/QLM.jpg);
            }
            .flag-medical-om {
                background-image: url(<%=request.getContextPath()%>/images/logo_med_oman.png);
            }
            /**
 * ----------------------------------------
 * animation flip-scale-up-hor
 * ----------------------------------------
 */
@-webkit-keyframes flip-scale-up-hor {
  0% {
    -webkit-transform: scale(1) rotateX(0);
            transform: scale(1) rotateX(0);
  }
  50% {
    -webkit-transform: scale(2.5) rotateX(-90deg);
            transform: scale(2.5) rotateX(-90deg);
  }
  100% {
    -webkit-transform: scale(1) rotateX(-180deg);
            transform: scale(1) rotateX(-180deg);
  }
}
@keyframes flip-scale-up-hor {
  0% {
    -webkit-transform: scale(1) rotateX(0);
            transform: scale(1) rotateX(0);
  }
  50% {
    -webkit-transform: scale(2.5) rotateX(-90deg);
            transform: scale(2.5) rotateX(-90deg);
  }
  100% {
    -webkit-transform: scale(1) rotateX(-180deg);
            transform: scale(1) rotateX(-180deg);
  }
}

            .flip-scale-up-hor {
	-webkit-animation: flip-scale-up-hor 0.5s linear both;
	        animation: flip-scale-up-hor 0.5s linear both;
}
        </style>
    </head>
    <body class="login-bg">
        <%--div class="wrap">
            <div class="header">
                <img src="<%=request.getContextPath()%>/plugins/innovate/images/logo.png" alt="Anoud CRM">
            </div>
        </div--%>
    <section>
        <div class=" login-container">
            <div class="container login-wrap ">
                <div class="row row-flex">
                    <div class="col-md-5 p-0 m-0">
                        <div class="bg-tipTop-login">
                            <s:if test="%{@qa.com.qic.common.util.ApplicationConstants$Environment@MVP_QA == @qa.com.qic.common.util.ApplicationConstants@ENVIRONMENT ||
                                    @qa.com.qic.common.util.ApplicationConstants$Environment@MVP_DEV == @qa.com.qic.common.util.ApplicationConstants@ENVIRONMENT}">
                                <img id="logo" class="logo-crm1" src="<%=request.getContextPath()%>/images/logo_at_crm.svg" alt="CRM" border="0" name="logo" width="200" />
                            </s:if>
                                <s:else>
                                <img id="logo" class="logo-crm1" src="<%=request.getContextPath()%>/plugins/innovate/images/anoud_crm_logo.png" alt="CRM" border="0" name="logo" width="200" />
                            </s:else>
                        </div>
                        <s:form id="frm_login" method="POST" action="/authorize.do">
                            <div class="bg-login content">
                               <div class="lgn-form">
                                    <div id="msg_login" style="font-weight:normal;color:red;display:<s:if test="hasActionErrors()">block</s:if><s:else>none</s:else>;" class="alert alert-warning-new">
                                        <s:iterator value="actionErrors">
                                            <s:property escapeHtml="true"/>
                                        </s:iterator>
                                    </div>

                                    <div class="Mpanel-default panel-default">     
                                        <s:if test='%{@qa.com.qic.common.util.ApplicationConstants@COMPANY_CODE_BEEMA.equals(company)}'>
                                            <div class="pheader" style="margin-bottom: 20px;">
                                                <img src="<%=request.getContextPath()%>/plugins/innovate/images/beemalogo.png" width="150" />
                                            </div>
                                        </s:if>
                                        <s:else>
                                            <div class="lang_select lang">
                                                <div class="dropdown">
                                                    <button class="btn btn-default dropdown-toggle new-lg-btn" data-toggle="dropdown" style="background-color: #f5f5f5; width: 100%; min-width: 100%; text-align:left;">
                                                        -- Select -- <span class="caret" style="margin-top: 9px; float: right;"></span>
                                                    </button>
                                                    <ul class="dropdown-menu" style="background-color: #FFF; text-align: left;">
                                                        <s:iterator value="@qa.com.qic.common.util.ApplicationConstants@DATASOURCE_LIST">
                                                            <li>
                                                                <a href="#" data-name="country" data-code="<s:property value="compCode"/>">
                                                                    <img src="<%=request.getContextPath()%><s:property value="compFlag"/>" width="25"/>
                                                                    <s:property escapeHtml="false" value="compName"/>
                                                                </a>
                                                            </li>
                                                        </s:iterator>
                                                    </ul>
                                                </div>
                                            </div>
                                            <s:hidden name="company"/>
                                        </s:else>
                                        <%--s:elseif test='%{@qa.com.qic.common.util.ApplicationConstants@APP_TYPE_QLM.equals(appType)}'>
                                            <div class="lang_select lang">
                                                <div class="dropdown">
                                                    <button class="btn btn-default dropdown-toggle new-lg-btn" data-toggle="dropdown" style="background-color: #f5f5f5; width: 100%; min-width: 100%; text-align:left;">
                                                        -- Select -- <span class="caret" style="margin-top: 9px; float: right;"></span>
                                                    </button>
                                                    <ul class="dropdown-menu" style="background-color: #FFF; text-align: left;">
                                                        <li>
                                                            <a href="#" data-name="country" data-code="009">
                                                                <img src="<%=request.getContextPath()%>/images/QLM.jpg" width="25"/>
                                                                &nbsp;&nbsp;QLM
                                                            </a>
                                                        </li>
                                                        <li>
                                                            <a href="#" data-name="country" data-code="006">
                                                                <img src="<%=request.getContextPath()%>/images/logo_med_oman.png" width="25"/>
                                                                &nbsp;&nbsp;OQIC
                                                            </a>
                                                        </li>
                                                    </ul>
                                                </div>
                                            </div>
                                            <s:hidden name="company"/>
                                        </s:elseif>
                                        <s:else>
                                            <div class="lang_select lang">
                                                <div class="dropdown" >
                                                    <button class="btn btn-default dropdown-toggle new-lg-btn" data-toggle="dropdown">
                                                        <span class="caret" style="margin-top: 9px; float: right; position: absolute; right: 15px;"></span>
                                                    </button>
                                                    <ul class="dropdown-menu" style="background-color: #FFF;">
                                                        <li>
                                                            <a href="#" data-name="country" data-code="001">
                                                                <img src="<%=request.getContextPath()%>\plugins\flag\flags\1x1\qa.svg" />
                                                                &nbsp;&nbsp;Qatar
                                                            </a>
                                                        </li>
                                                        <li>
                                                            <a href="#" data-name="country" data-code="002">
                                                                <img src="<%=request.getContextPath()%>\plugins\flag\flags\1x1\ae.svg" />
                                                                &nbsp;&nbsp;UAE
                                                            </a>
                                                        </li>
                                                        <li>
                                                            <a href="#" data-name="country" data-code="006">
                                                               <img src="<%=request.getContextPath()%>\plugins\flag\flags\1x1\om.svg" />
                                                                &nbsp;&nbsp;Oman
                                                            </a>
                                                        </li>
                                                        <li>
                                                            <a href="#" data-name="country" data-code="005">
                                                              <img src="<%=request.getContextPath()%>\plugins\flag\flags\1x1\kw.svg" />
                                                                &nbsp;&nbsp;Kuwait
                                                            </a>
                                                        </li>
                                                    </ul>
                                                </div>
                                            </div>
                                            <s:hidden name="company"/>
                                        </s:else--%>
                                        <div class="">
                                            <%if (!"".equals(id)) {%>
                                            <div class="">
                                                <input type="text" class="textboxloguser" placeholder="<s:text name="lbl.login.username"/>" value="<%=name%>" readonly/>
                                                <a class="diff-user" id="btn_change_user"><s:text name="lbl.login.differentuser"/>&nbsp;</a>
                                            </div>
                                            <%}%>
                                            <div class="<%=("".equals(id) ? "" : "hide")%>">
                                                <input type="text" class="textboxloguser" placeholder="<s:text name="lbl.login.username"/>" name="usrnm" value="<%=id%>"/>
                                                <span class="please"></span>
                                            </div>
                                        </div>
                                        <div class="">
                                            <input type="password" class="textboxlogpass" placeholder="<s:text name="lbl.login.password"/>" name="psswrd"/> <br>
                                            <span class="please"></span>
                                        </div>
                                        <div class="">
                                            <label class="container-checkbox"><input type="checkbox" name="rememberme" id="rememberme" value="Y"> <s:text name="lbl.login.rememberme"/> <span class="checkmark"></span></label>
                                            <s:if test='%{@qa.com.qic.common.util.ApplicationConstants@COMPANY_CODE_MED_DOHA.equals(company)}'>
                                            <label class="container-checkbox"><input type="checkbox" name="joinCallCenter" id="joinCallCenter" value="Y"> <s:text name="lbl.login.joinme"/> <span class="checkmark"></span></label>
                                            </s:if>
                                            <%--label style="float: right;"> <a class="forgot" id="forgotbtn">Forgot Password?</a></label--%>
                                        </div>
                                        <div class="text-center">
                                            <button class="btn  lgn-nbtn signbtn2" type="submit" id="btn_sign"><s:text name="lbl.login.signin"/></button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </s:form>
                    </div> 
                    <div class="col-md-7 p-0 m-0">
                        <div class="bg-login-right">
                            <div class="content">
                                <div class="bg-all-boxes">
                                    <div class="bBoxes">&nbsp;</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>        
        </div>
    </section>
    <script>
        $(document).ready(function() {
            <s:if test="hasActionErrors()">
                $('body').addClass('animated shake');
            </s:if>

            // sidebar collide on body click function
            $('#block_body').click(function() {
                $('body').find('sidebar-collapsed-back').addClass('sidebar-collapsed');
            });
        });
 </script>   
</html>
