<%-- 
    Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
    * All Rights Reserved
    * Unauthorized copying of this file, via any medium is strictly prohibited

    Document   : callMonitor
    Created on : Jun 12, 2017, 9:40:51 PM
    Author     : ravindar.singh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<style type="text/css">
    .crm_info{
        cursor:pointer;	
    }

    .crm_info .active {
        background-color: #000;
    }

    .crm_info.active {
        background-color: #2196f3;
        color: #fff;
    }

    .crm_info.active .icon i {
        color: #fff !important;
        opacity: 0.4;
        -moz-transform: rotate(-32deg) scale(1.4);
        -ms-transform: rotate(-32deg) scale(1.4);
        -o-transform: rotate(-32deg) scale(1.4);
        -webkit-transform: rotate(-32deg) scale(1.4);
        transform: rotate(-32deg) scale(1.4);
    }

    .crm_info.active .content .text,
    .crm_info.active .content .number {
        color: #fff;
    }
</style>
<section class="admin_main m-b-20">
    <div class="container">
        <div class="row">
             <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
                 <div class="info-box-4 hover-zoom-effect crm_info" data-toggle="tooltip" title="<s:text name="lbl.call.details"/>" id="btn_missed_call">
                    <div class="icon">
                        <i class="material-icons col-light-blue">callhistory</i>
                    </div>
                    <div class="content">
                        <div class="text">Call</div>
                        <div class="number">Log</div>
                    </div>
                </div>
            </div>
            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
                <div class="info-box-4 hover-zoom-effect crm_info" id="btn_mon_1" data-toggle="tooltip" data-placement="top" title="<s:text name="lbl.lower.agent.status"/>">
                    <div class="icon">
                        <i class="material-icons col-light-blue">accessible</i>
                    </div>
                    <div class="content">
                        <div class="text">Agent</div>
                        <div class="number">Status</div>
                    </div>
                </div>
            </div>
            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
                <div class="info-box-4 hover-zoom-effect crm_info" id="btn_mon_2" data-toggle="tooltip" data-placement="top" title="View IVR">
                    <div class="icon">
                        <i class="material-icons col-blue">settings_input_antenna</i>
                    </div>
                    <div class="content">
                        <div class="text"><s:text name="lbl.view"/></div>
                        <div class="number"><s:text name="lbl.ivr"/></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<section>
    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                <div id="block_task_allocate"></div>
            </div>
        </div>  
    </div>
</section>
<script type="text/javascript">
    $(document).ready(function () {
        $('[data-toggle="tooltip"]').tooltip();
        $(".crm_info").click(function () {
            if ($('.crm_info').hasClass('.active')) {
                $('.crm_info').removeClass('active');
                $(this).addClass("active");
            } else {
                $('.crm_info').removeClass('active');
                $(this).addClass("active");
            }
        });
    });
    
    $("#btn_missed_call").on("click", function() {
       block('block_body');
       $("#block_task_allocate").load(APP_CONFIG.context + "/admin/openMissedCallPage.do", {}, function() {
          unblock('block_body');
    });
    });
    $("#btn_mon_1").on("click", function() {
        window.open(APP_CONFIG.context + "/monitor/loadCiscoMonitorPage.do?status=1");
    });
    $("#btn_mon_2").on("click", function() {
        window.open(APP_CONFIG.context + "/monitor/loadCiscoMonitorPage.do?status=2");
    });
</script>