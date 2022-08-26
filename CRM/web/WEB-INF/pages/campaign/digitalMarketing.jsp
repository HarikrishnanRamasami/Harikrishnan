<%-- 

  Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
    * All Rights Reserved
    * Unauthorized copying of this file, via any medium is strictly prohibited


    Document   : digitalMarketing
    Created on : Aug 23, 2017, 1:52:19 PM
    Author     : haridass.a
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<style type="text/css">
    .crm_digitmark{
        cursor:pointer;	
    }

    .crm_digitmark .active {
        background-color: #000;
    }

    .crm_digitmark.active {
        background-color: #2196f3;
        color: #fff;
    }

    .crm_digitmark.active .icon i {
        color: #fff !important;
        opacity: 0.4;
        -moz-transform: rotate(-32deg) scale(1.4);
        -ms-transform: rotate(-32deg) scale(1.4);
        -o-transform: rotate(-32deg) scale(1.4);
        -webkit-transform: rotate(-32deg) scale(1.4);
        transform: rotate(-32deg) scale(1.4);
    }

    .crm_digitmark.active .content .text,
    .crm_digitmark.active .content .number {
        color: #fff;
    }
</style>
<section class="admin_main m-b-20">
    <div class="container">
        <div class="row">
            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
                <div class="info-box-4 hover-zoom-effect crm_info" data-toggle="tooltip" title="<s:text name="lbl.bulk.email"/>" id="btn_bulk_sms">
                    <div class="icon">
                        <i class="fa fa-envelope"></i>
                    </div>
                    <div class="content">
                        <div class="text"><s:text name="lbl.bulk"/></div>
                        <div class="number"><s:text name="lbl.email"/></div>
                    </div>
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
                <div id="block_digital_marketing"></div>
            </div>
        </div>  
    </div>
</section>
<script type="text/javascript">
    $(document).ready(function () {
        $('[data-toggle="tooltip"]').tooltip();
        $(".crm_digitmark").click(function () {
            if ($('.crm_digitmark').hasClass('.active')) {
                $('.crm_digitmark').removeClass('active');
                $(this).addClass("active");
            } else {
                $('.crm_digitmark').removeClass('active');
                $(this).addClass("active");
            }
        });
    });
   
     $("#btn_bulk_sms").on("click", function() {
        block('block_body');
        $("#block_digital_marketing").load(APP_CONFIG.context + "/camp/openEmailSmsPage.do", {}, function() {
            unblock('block_body');
        });
    });
</script>
