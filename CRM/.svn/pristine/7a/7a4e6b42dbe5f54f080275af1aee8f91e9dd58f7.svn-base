<%-- 
    Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
    * All Rights Reserved
    * Unauthorized copying of this file, via any medium is strictly prohibited

    Document   : pluginLayout
    Created on : Mar 21, 2017, 7:08:20 AM
    Author     : ravindar.singh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" import="qa.com.qic.common.util.ApplicationConstants"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="utf-8" />
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/plugins/font-awesome/css/font-awesome.css" />
        <link rel="stylesheet" href="<%=request.getContextPath()%>/plugins/innovate/css/bootstrap.min.css" />
        <link href="<%=request.getContextPath()%>/plugins/bootstrap-select/css/bootstrap-select.css" rel="stylesheet" />
        <link href="<%=request.getContextPath()%>/css/styleV2.css?v=${application.R_TOCKEN}" rel="stylesheet" />
        <link rel="stylesheet" href="<%=request.getContextPath()%>/plugins/innovate/css/main.css?v=${application.R_TOCKEN}" />
        <link rel="stylesheet" href="<%=request.getContextPath()%>/plugins/innovate/css/dataTables.min.css" />
        <link rel="stylesheet" href="<%=request.getContextPath()%>/plugins/datatables/Responsive/css/dataTables.responsive.css">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/plugins/innovate/css/flags.css" />
        <link rel="stylesheet" href="<%=request.getContextPath()%>/plugins/magnific-popup/magnific-popup.css">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/plugins/innovate/css/line-awesome.min.css">
        <link href="<%=request.getContextPath()%>/plugins/richText/css/jquery-te-1.4.0.css" rel="stylesheet" />
        <link href="<%=request.getContextPath()%>/plugins/innovate/css/datepicker.css" rel="stylesheet" />
        <link href="<%=request.getContextPath()%>/plugins/bootstrap-fileupload/bootstrap-fileupload.min.css" rel="stylesheet" />
        <link href="<%=request.getContextPath()%>/plugins/bootstrap-multiselect/bootstrap-multiselect.css" rel="stylesheet" />
        <link href="<%=request.getContextPath()%>/plugins/bootstrap-material-datetimepicker/css/bootstrap-material-datetimepicker.css" rel="stylesheet" />
        <link rel="stylesheet" href="<%=request.getContextPath()%>/plugins/innovate/css/jquery-ui.css" />
        
        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/jquery/jquery.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/jquery/jquery-ui.js"></script>
        <script src="<%=request.getContextPath()%>/plugins/innovate/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/datatables/bootstrap3/js/datatables.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/cookie/jquery.cookie.js"></script>

        <title><s:text name="lbl.common.annoud.crm"/></title>
        <script type="text/javascript">
            var APP_CONFIG = {
                context: "<%=request.getContextPath()%>",
                crmOrigin: window.location.href,
                messageType: {
                    success: "<%=ApplicationConstants.MESSAGE_TYPE_SUCCESS%>",
                    error: "<%=ApplicationConstants.MESSAGE_TYPE_ERROR%>"
                },
                companyList: {"001": {"code": "QA", "name": "Doha"}, "002": {"code": "AE", "name": "Dubai"}, "005": {"code": "KW", "name": "Kuwait"}, "006": {"code": "OM", "name": "Oman"}},
                companyCode: "<s:property value="#session.USER_INFO.companyCode" />",
                ameyoChannel: "<s:property value="#session.USER_INFO.ameyoChannel" />",
                extension: "<s:property value="#session.USER_INFO.userTelNo" />"
            };
            
            setInterval(function() {
                var login = $.cookie('login') ? $.cookie('login') : "";
                //console.log("Plugin: "+login);
                if(login === "L") {
                    if((window.location.href).indexOf("lock.do") === -1) {
                        window.location.href = APP_CONFIG.context + "/lock.do";
                    }
                } else if(login !== "Y") {
                    if((window.location.href).indexOf("login.do") === -1 && (window.location.href).indexOf("authorize.do") === -1) {
                        window.location.href = APP_CONFIG.context + "/logout.do";
                    }
                }
            }, 500);
        </script>
    </head>
    
    <tiles:insertAttribute name="body"></tiles:insertAttribute>

    <!--Common modal window -->
    <div class="modal fade" id="plugin_modal_dialog" data-backdrop="static" tabindex="-1" role="dialog" aria-labelledby="plugin_modal_dialog_heading" aria-hidden="true">
        <div class="modal-dialog  modal-lg">
            <div class="modal-content">
                <%--div class="modal-header" style="padding-left: 25px;padding-right: 10px;padding-top: 10px;">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h3 id="plugin_modal_dialog_heading" class="modal-title custom-font"></h3>
                </div>
                <div class="modal-body">

                </div--%>
            </div>
        </div>
    </div>
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/innovate/js/jquery.flagstrap.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/jquery/jquery.form.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/validator/jquery.validate.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/echarts/echarts.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/autoNumeric/autoNumeric.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/echarts/plotly-latest.min.js"></script> 
    <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/innovate/js/jquery.slimscroll.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/node-waves/waves.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/blockUI/blockui.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/notify/notify.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/momentjs/moment.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/innovate/js/datepicker.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/richText/jquery-te-1.4.0.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/datatables/Responsive/js/dataTables.responsive.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/bootstrap-material-datetimepicker/js/bootstrap-material-datetimepicker.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/bootstrap-fileupload/bootstrap-fileupload.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/bootstrap-multiselect/bootstrap-multiselect.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/admin.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/util.min.js"></script>
</html>