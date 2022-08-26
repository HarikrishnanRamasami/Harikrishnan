<%-- 
    Document   : customer360V2
    Created on : 23 Oct, 2017, 6:21:36 PM
    Author     : sutharsan.g
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<style type="text/css">
    .home-card {
        min-height: 370px;
    }
    .no-data:after {
        content: "No data available";
        color: rgba(30, 127, 210, 0.62);
    }
    .list-group {
        padding-left: 0; 
        margin-bottom: 0px;
        word-wrap: break-word;
    }
</style>
<s:if test='"modal".equals(search)'>
    <%--When open from modal popup. Implemented in customerNotFound.jsp--%>
    <div class="modal-header">
        <h4 class="modal-title"><s:text name="lbl.customer.view"/></h4>
        <div style="margin-top: -31px; float: right;">
            <button class="close-btn btn" data-dismiss="modal" id="btn_email_close">&#10006; <s:text name="btn.close"/></button>
        </div>
    </div>
    <div class="row">
    </s:if>
    <s:else>
        <div>
        </s:else>
        <div class="col-md-3 right-pad">
            <div class="edit-pro">
                <h4><s:property value="customer.name"/></h4>
                <span class="sinc-eng"><s:text name="lbl.since"/> <s:property value="customer.startYear"/> (<s:if test='"A".equals(preferLang)'><s:text name="lbl.arabic"/></s:if><s:else><s:text name="lbl.english"/></s:else>)</span>
                    <br />
                    <!-- <a class=" edi-pro" href="javascript:void(0);" id="btn_cust_edit"><i class="la la-pencil"></i></a>-->
                    <div class="new-profile-edit-icos">
                            <span class="" href="#" data-toggle = "tooltip" data-placement = "top" title="<s:text name="lbl.feedback"/>" id="btn_feedback"><img src="<%=request.getContextPath()%>/plugins/innovate/images/ico_feedback_colorfull.svg" width="24"></span>
                    <span class="" id="btn_log" href="#"  data-toggle = "tooltip" data-placement = "top" title="<s:text name="lbl.activities"/>"><img src="<%=request.getContextPath()%>/plugins/innovate/images/ico_activity_white.svg" width="23"></span>
                    <span class=" edi-pro" href="javascript:void(0);" id="btn_cust_edit"  data-toggle = "tooltip" data-placement = "top" title="<s:text name="lbl.edit"/>"><img src="<%=request.getContextPath()%>/plugins/innovate/images/ico_edit_white.svg" width="24"></span>
                    <!--<span class=" edi-pro" href="javascript:void(0);" id="btn_cust_edit"  data-toggle = "tooltip" data-placement = "top" title="Edit"><i class="la la-pencil" style="font-size:20px; color:#fff;"></i></span>-->
                </div>
            </div>
            <div class="pro-tabs">
                <div id="exTab2" class="container-fluid1">	
                    <ul class="nav nav-tabs  nav-justified ">
                        <li class="active">
                            <a  href="#1" data-toggle="tab"><s:text name="lbl.profile"/></a>
                        </li>
                        <li><a href="#3" data-toggle="tab"><s:text name="lbl.personal.info"/></a>
                        </li>
                        <li><a href="#2" data-toggle="tab"><s:text name="lbl.contact.info"/></a>
                        </li>
                    </ul>

                    <div class="tab-content ">
                        <div class="tab-pane active" id="1">
                            <div class="tb-m-container">
                                <table id="example" class="table table-striped table-bordered" style="width:100%">
                                    <tbody>
                                        <tr>
                                            <td width="50%"><s:text name="lbl.civil.id"/></td>
                                            <td><s:property value="customer.civilId"/></td>
                                        </tr>
                                        <tr>
                                            <td><s:text name="lbl.po.box"/></td>
                                            <td><s:property value="customer.poBox"/></td>
                                        </tr>
                                        <tr>
                                            <td><s:text name="lbl.nationality"/></td>
                                            <td><s:property value="customer.nationality"/></td>
                                        </tr>
                                        <tr>
                                            <td><s:text name="lbl.country"/></td>
                                            <td><s:property value="customer.country"/></td>
                                        </tr>
                                        <tr>
                                            <td><s:text name="lbl.gender"/></td>
                                            <td><s:property value="customer.gender"/></td>
                                        </tr>
                                        <tr>
                                            <td><s:text name="lbl.age"/></td>
                                            <td><s:property value="customer.age"/></td>
                                        </tr>
                                        <tr>
                                            <td><s:text name="lbl.work.place"/></td>
                                            <td><s:property value="customer.workPlace"/></td>
                                        </tr>
                                        <tr>
                                            <td><s:text name="lbl.occupation"/></td>
                                            <td><s:property value="customer.occupation"/></td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div class="tab-pane" id="2">
                            <div class="tb-m-container">
                                <table id="example" class="table table-striped table-bordered" style="width:100%">
                                    <tbody>
                                        <tr>
                                            <td width="50%"><s:text name="lbl.mobile.no"/></td>
                                            <td>  <s:if test='customer.mobileNo != null && customer.mobileNo != ""'>
                                                    <span class="contact-icons">
                                                        <i class="la la-phone-square hand" onclick="callCustomer('<s:property value="customer.mobileNo"/>');"></i>
                                                        <i class="la la-comment hand" onclick="callSms(<s:property value="customer.mobileNo"/>);"></i>
                                                    </span>
                                                </s:if>
                                                <s:property value="customer.mobileNo"/>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td><s:text name="lbl.mobile.no1"/></td>
                                            <td>
                                                <s:if test='customer.mobileNo1 != null && customer.mobileNo1 != ""'>
                                                    <span class="contact-icons">
                                                        <i class="la la-phone-square hand" onclick="callCustomer('<s:property value="customer.mobileNo1"/>');"></i>
                                                        <i class="la la-comment hand" onclick="callSms(<s:property value="customer.mobileNo1"/>);"></i>
                                                    </span>
                                                </s:if>
                                                <s:property value="customer.mobileNo1"/>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td><s:text name="lbl.mobile.no2"/></td>
                                            <td>
                                                <s:if test='customer.mobileNo2 != null && customer.mobileNo2 != ""'>
                                                    <span class="contact-icons">
                                                        <i class="la la-phone-square hand" onclick="callCustomer('<s:property value="customer.mobileNo2"/>');"></i>
                                                        <i class="la la-comment hand" onclick="callSms(<s:property value="customer.mobileNo2"/>);"></i>
                                                    </span>
                                                </s:if>
                                                <s:property value="customer.mobileNo2"/>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td><s:text name="lbl.tel.no"/></td>
                                            <td>
                                                <s:if test='customer.telNo != null && customer.telNo != ""'>
                                                    <span class="contact-icons"><i class="la la-phone-square hand" onclick="callCustomer('<s:property value="customer.telNo"/>');"></i></span>
                                                    </s:if>
                                                    <s:property value="customer.telNo"/>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td><s:text name="lbl.email.id"/></td>
                                            <td>
                                                <s:if test='customer.emailId != null && customer.emailid != ""'>
                                                    <span class="contact-icons"><i class="la la-envelope hand" onclick="callEmail();"></i></span>
                                                    </s:if>
                                                    <s:property value="customer.emailId"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td><s:text name="lbl.fax.no"/></td>
                                            <td><s:property value="customer.faxNo"/></td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div class="tab-pane" id="3">
                            <div class="tb-m-container">
                                <table id="example" class="table table-striped table-bordered" style="width:100%">
                                    <tbody>
                                        <tr>
                                            <td width="50%"><s:text name="lbl.birth.date"/></td>
                                            <td><s:property value="customer.birthDt"/>&nbsp;</td>
                                        </tr>
                                        <tr>
                                            <td><s:text name="lbl.wedding.date"/></td>
                                            <td><s:property value="customer.weddingDt"/></td>
                                        </tr>
                                        <tr>
                                            <td><s:text name="lbl.id.expiry.date"/></td>
                                            <td><s:property value="customer.idExpDt"/></td>
                                        </tr>
                                        <tr>
                                            <td><s:text name="lbl.license.exp.date"/></td>
                                            <td><s:property value="customer.licenseExpDt"/></td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div> 

            <br />
            <div class="clear"></div>

            <div class="clearfix clear"></div>
        </div>

        <div class="col-md-9 ">
            <div class="col-md-12 nopad"> 

                <div class="elig-details">
                    <ul class="total-prm">
                        <li class="">
                            <div class="cards360 tpremium">
                                <div class="box-spc"></div>
                                <h5 ><s:text name="lbl.total.premium"/></h5>
                                <h3 class="formatted-number" id="div_tot_premium"></h3>
                            </div>
                        </li>  
                        <li>
                            <div class="cards360 tClaims">
                                <div class="box-spc"></div>
                                <h5 class="text-shadow"><s:text name="lbl.total.Claims"/></h5>
                                <h3 class="formatted-number" id="div_tot_claims"></h3>
                            </div>
                        </li>  
                        <li>
                            <div class="cards360 tRatio">
                                <div class="box-spc"></div>
                                <h5 class="text-shadow"><s:text name="lbl.loss.ratio"/></h5>
                                <h3 id="div_loss_ratio"></h3>
                            </div>
                        </li>  
                        <li>
                            <div class="cards360 sSource">
                                <div class="box-spc"></div>
                                <h5 class="text-shadow"><s:text name="lbl.source"/></h5>
                                <h3 style=" color:#fff;">
                                    <s:property value="customer.sourceDetails"/>
                                    <%--s:if test='customer.source != null && !"".equals(customer.source)'>
                                        <s:property value="customer.source"/>
                                        <s:if test="customer.sourceDetails != null">
                                            <a href="#" data-toggle="tooltip" data-placement="top" title="<s:property value="customer.sourceDetails"/>">
                                                <i class="fa fa-info-circle" style="color: #FFF;"></i>
                                            </a>
                                        </s:if --%>
                                    <%--/s:if--%>
                                    <%--s:else--%>
                                    <%--s:property value="customer.sourceDetails"/ --%>
                                    <%--/s:else--%>
                                </h3>
                            </div>
                        </li>  
                        <li>
                            <div class="cards360 cType">
                                <div class="box-spc"></div>
                                <h5 class="text-shadow"><s:text name="lbl.client.type"/></h5>
                                <h3><s:property value="customer.custTypeDesc"/></h3>
                            </div>
                        </li>  
                    </ul>
                </div>
                <div class="col-md-6 col-sm-6 col-xs-12 nopad">
                    <div class="prm-calums my-reminder">
                        <!--h4>Premium Vs Claims<span class="pullright grp-casl"><i class="la la-line-chart"></i> <i class="la la-bar-chart"></i><i class="la la-save"></i></span></h4-->
                        <div id="div_chart_productivity" style="height: 345px; width: 100%; margin-left: 10px;"></div>
                    </div>
                </div>
                <div class="col-sm-6 col-md-6 col-xs12 right-pad">
                    <div class="profile-pages my-reminder">
                        <h4><s:text name="lbl.profile"/></h4>
                        <div class="portfolio-wrap">
                            <div class="portfolio-blk">
                                <div class="portfolio-head">
                                    <div class="port-title">
                                        <i class="la la-minus la-plus"></i>
                                        <span><s:text name="lbl.quotes"/> <strong ></strong></span>
                                    </div>
                                    <div class="title-links">
                                        <div class="dropdown">
                                            <div id="solTitle"><a class="new-quote dropbtn"><s:text name="lbl.new.quotes"/></a></div>
                                            <div class="board-icons2 dropdown-content2" id="dr">
                                                <a class="car"  href="#" id="btn_car_quote"><span><s:text name="lbl.car"/></span></a>
                                                <a class="house"  href="#" id="btn_home_quote"><span><s:text name="lbl.home"/></span></a>
                                                <a class="employee"  href="#" id="btn_travel_quote"><span><s:text name="lbl.travel"/></span></a>
                                            </div>
                                        </div>
                                                <a href="#" id="btn_quote_Detaill"class="view-btn"><s:text name="lbl.view.all"/></a>
                                    </div>
                                </div>
                                <div class="portfolio-cont hiding" style="display: none;">
                                    <ul>
                                        <div class="list-group" id="div_quotes"></div>
                                    </ul>
                                </div>
                            </div>
                            <div class="portfolio-blk">
                                <div class="portfolio-head">
                                    <div class="port-title">
                                        <i class="la la-minus la-plus"></i>
                                        <span><s:text name="lbl.policies"/> <strong></strong></span>
                                    </div>
                                    <div class="title-links">
                                        <a href="#" id="btn_policy_Detaill"class="view-btn"><s:text name="lbl.view.all"/></a>
                                    </div>
                                </div>
                                <div class="portfolio-cont hiding" style="display: none;">
                                    <ul>
                                        <div class="list-group" id="div_policies"></div>
                                    </ul>
                                </div>
                            </div>
                            <div class="portfolio-blk">
                                <div class="portfolio-head">
                                    <div class="port-title">
                                        <i class="la la-minus la-plus"></i>
                                        <span><s:text name="lbl.active.claims"/> <strong></strong></span>
                                    </div>
                                    <div class="title-links">
                                        <div class="dropdown">
                                            <div><a href="#" class="new-quote dropbtn" id="btn_clm_car"><s:text name="lbl.new.claims"/></a></div>
                                        </div>
                                        <a href="#" id="btn_claim_Detaill" class="view-btn"><s:text name="lbl.view.all"/></a>
                                    </div>	
                                </div>
                                <div class="portfolio-cont hiding" style="display: none;">
                                    <ul>
                                        <div class="list-group" id="div_claims">        
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
        <div class="row p-15">

            <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="acti-off my-reminder" >
                    <div class="acti-off-heads">
                        <a href="javascript:void(0);" class="active" data-tab="activities"><s:text name="lbl.activities"/></a>
                        <!--<a href="javascript:void(0);" class="" data-tab="eligible">eligible offer</a>-->
                        <a href="javascript:void(0);" class="" data-tab="task"><s:text name="lbl.task"/></a>
                        <%--a href="javascript:void(0);" class="" data-tab="digimarket">digital marketing</a--%>
                        <%--a href="javascript:void(0);" class="" data-tab="calllog">call log</a--%>
                        <s:if test='%{@qa.com.qic.common.util.ApplicationConstants@COMPANY_CODE_DOHA.equals(company) && @qa.com.qic.common.util.ApplicationConstants@APP_TYPE_RETAIL.equals(#session.USER_INFO.appType)}'>
                            <a href="javascript:void(0);" class="" data-tab="whatsapp"><s:text name="lbl.whatsApp"/></a>
                        </s:if>
                    </div>
                    <ul class="act-name current" id="activities">
                        <div class="body" id="div_activities">
                        </div>
                        <div class="view_more" style="margin-top: 50px">
                            <button type="button" class="view-btn mbtn" id="btn_acti_more" title="<s:text name="lbl.activities.details"/>"> <s:text name="btn.more"/></button>
                        </div>
                    </ul>
                    <!--  <ul class="act-name" id="eligible">
                         
                      </ul>-->
                    <ul class="act-name" id="task">
                        <table id="tbl_closed_task" class="table" width="100%">
                            <thead>
                                <tr style="box-shadow:0 1px 3px 1px rgba(29, 77, 105, 0.14)">
                                    <th class="text-center"><s:text name="lbl.id"/></th>
                                    <th class="text-center"><s:text name="lbl.sub.category"/></th>
                                    <th class="text-center"><s:text name="lbl.ref.no"/></th>
                                    <th class="text-center"><s:text name="lbl.remarks"/></th>
                                    <th class="text-center"><s:text name="lbl.close.data"/></th>
                                    <th class="text-center"><s:text name="lbl.closed.by"/></th>
                                </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                    </ul>
                    <%--ul class="act-name" id="digimarket">
                        <div class="body" id="div_campmail">
                            <table class="table table-hover dashboard-task-infos" id="tbl_campmail">
                                <thead>
                                    <tr>
                                        <th width="8%">Camp Name</th>
                                        <th width="8%">Description</th>
                                        <th width="8%">Opened</th>
                                        <th width="8%">Visited</th>
                                    </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                        </div> 
                    </ul>
                    <ul class="act-name" id="calllog">
                        <div class="body" id="div_call_log">
                            <table class="table table-hover dashboard-task-infos" id="tbl_call_log">
                                <thead>
                                    <tr>
                                        <th width="8%">Name</th>
                                        <th width="8%">Type</th>
                                        <th width="8%">Duration</th>
                                        <th width="8%">Remarks</th>
                                    </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                        </div> 
                    </ul--%>
                    <ul class="act-name" id="whatsapp">
                        <div class="body" id="div_call_log">
                            <table class="table table-hover dashboard-task-infos" id="tbl_wa_log" width="100%" >
                                <thead>
                                    <tr>
                                        <th class="never"><s:text name="lbl.id"/></th>
                                        <th><s:text name="lbl.date"/></th>
                                        <th><s:text name="lbl.mobile"/></th>
                                        <th><s:text name="lbl.mode"/></th>
                                        <th><s:text name="lbl.message"/></th>
                                        <th><s:text name="lbl.type"/></th>
                                    </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                        </div> 
                    </ul>
                </div>
            </div>
        </div>           
    </div>
    <script type="text/javascript">
            var data = {"company": "${company}", "agentid": "${agentid}", "customer.mobileNo": "${customer.mobileNo}", "customer.civilId": "${customer.civilId}", "customer.emailId": "${customer.emailId}", "customer.name": "${customer.name}", "customer.mobileN01": "${customer.mobileNo1}", "customer.mobileNo2": "${customer.mobileNo2}"};
            $(document).ready(function () {
                $(".formatted-number").autoNumeric('init');
                $('.port-title i').click(function (e) {
                    e.preventDefault();

                    var $this = $(this);

                    if ($this.parent().parent().next().hasClass('showing')) {
                        $this.parent().parent().next().addClass("hiding").removeClass('showing');
                        $this.parent().parent().next().slideUp(350);
                        $this.removeClass('la-plus').addClass('la-plus');
                    } else {
                        $this.parent().parent().parent().parent().find('.portfolio-cont').removeClass('showing');
                        $this.parent().parent().parent().parent().find('.portfolio-cont').slideUp(350);
                        $this.parent().parent().next().addClass("showing").removeClass('hiding');

                        $this.parent().parent().parent().parent().find('.la').addClass('la-plus');
                        $this.removeClass('la-plus').addClass('la-minus');

                        $this.parent().parent().next().slideToggle(350);
                    }
                });
                $(document).mouseup(function () {
                    $("#solTitle").click(function () {

                        if ($("#dr").css('display') === "none") {
                            $("#dr").show();
                        } else {
                            $("#dr").hide();
                        }
                    });
                });
                $(document).mouseup(function (e)
                {
                    var container = $(".dropdown-content2");
                    // if the target of the click isn't the container nor a descendant of the container
                    if (!container.is(e.target) && container.has(e.target).length === 0)
                    {
                        container.hide();
                    }
                });
                $(document).ready(function () {
                    $('.fl-logo').click(function () {
                        if (mobile !== null) {
                            $('.feedback-form').show();
                        }
                    });
                    $('.close-btn').click(function () {
                        $('.feedback-form').hide();
                    });

                });

                $('.acti-off-heads a').click(function () {
                    var tab_id = $(this).attr('data-tab');

                    $('.acti-off-heads a').removeClass('active');
                    $('.act-name').removeClass('current');

                    $(this).addClass('active');
                    $("#" + tab_id).addClass('current');
                })
                $("#plugin_modal_dialog").on('shown.bs.modal', function () {
                    $(this).find("input:visible:enabled:first").focus();
                });
                $("#btn_sms, #btn_mail, #btn_log, #btn_cust_edit, #btn_feedback, #btn_quote_Detaill, #btn_policy_Detaill, #btn_claim_Detaill, #btn_acti_more").on("click", function () {
                    var _this = $(this);
                    var url = APP_CONFIG.context;
                    if ($(_this).prop("id") === "btn_sms") {
                        url += "/openSmsActivityForm.do";
                    } else if ($(_this).prop("id") === "btn_mail") {
                        url += "/openEmailActivityForm.do";
                    } else if ($(_this).prop("id") === "btn_log") {
                        url += "/openLogActivityForm.do";
                    } else if ($(_this).prop("id") === "btn_cust_edit") {
                        url += "/openCustomerEntryForm.do?operation=edit";
                    } else if ($(_this).prop("id") === "btn_feedback") {
                        url += "/openFeedback.do";
                    } else if ($(_this).prop("id") === "btn_quote_Detaill") {
                        url += "/openCustomerQuoteDetailsPage.do?";
                    } else if ($(_this).prop("id") === "btn_policy_Detaill") {
                        url += "/openCustomerPolicyDetailsPage.do?";
                    } else if ($(_this).prop("id") === "btn_claim_Detaill") {
                        url += "/openCustomerClaimDetailsPage.do?";
                    } else if ($(_this).prop("id") === "btn_acti_more") {
                        url += "/openLogActivityPage.do";
                    }
                    block('block_body');
                    $.ajax({
                        type: "POST",
                        url: url,
                        data: data,
                        async: true,
                        success: function (result) {
                            if (($(_this).prop("id") === "btn_cust_edit")) {
                                $('#popup_custom').html(result);
                                $('.popup-wrap').addClass('popup-open');
                                $('#overlay').show();
                            } else {
                                if ($(_this).prop("id") === "btn_quote_Detaill" || $(_this).prop("id") === "btn_policy_Detaill" || $(_this).prop("id") === "btn_claim_Detaill" || $(_this).prop("id") === "btn_acti_more") {
                                    console.log("calling Pop up...")
                                    //$('#plugin_modal_dialog').modals().mx(result);
                                    $('#plugin_modal_dialog .modal-dialog').removeClass("modal-lg modal-sm").addClass("modal-mx");
                                    $('#plugin_modal_dialog .modal-content').empty().html(result);
                                    $('#plugin_modal_dialog').modal('show');
                                } else {
                                    $('#plugin_modal_dialog .modal-dialog').removeClass("modal-lg modal-sm").addClass("modal-mm");
                                    $('#plugin_modal_dialog .modal-content').empty().html(result);
                                    $('#plugin_modal_dialog').modal('show');
                                }
                            }
                        },
                        error: function (xhr, status, error) {
                            alert("Error: " + error);
                        },
                        complete: function () {
                            unblock('block_body');
                        }
                    });
                });

                $("#btn_car_quote, #btn_travel_quote, #btn_home_quote").on("click", function () {
                    var code = "";
                    if ($(this).prop("id") === "btn_car_quote") {
                        code = "01";
                    } else if ($(this).prop("id") === "btn_travel_quote") {
                        code = "08";
                    } else if ($(this).prop("id") === "btn_home_quote") {
                        code = "04";
                    }
                    window.open(APP_CONFIG.context + "/AnoudAppIntegration.do?flex1=" + code + "&customer.civilId=" + data["customer.civilId"] + "&customer.mobileNo=" + data["customer.mobileNo"] + "&customer.emailId=" + data["customer.emailId"]);
                });
                var badgeClass = ["bg-orange", "bg-red", "bg-green", "bg-blue", "bg-orange", "bg-red", "bg-green", "bg-blue"];
                $.ajaxSettings.async = false;
                $.getJSON(APP_CONFIG.context + "/showPoliciesFigure.do", data, function () {
                }).done(function (result) {
                    var data = "";
                    $.each(result.aaData, function (i, o) {
                        data = $("#div_policies").append('<a href="javascript:void(0);" class="list-group-item"><span class="badge ' + badgeClass[i] + '">' + o.value + '</span>' + o.key + '</a>');
                    });
//            if (data === "") {
//                data = $("#div_policies").append('<li>No Data Available</li>');
//            }
                }).fail(function () {

                }).always(function () {

                });
                $.getJSON(APP_CONFIG.context + "/showQuotesFigure.do", data, function () {
                }).done(function (result) {
                    var data = "";
                    $.each(result.aaData, function (i, o) {
                        data = $("#div_quotes").append('<a href="javascript:void(0);" class="list-group-item"><span class="badge ' + badgeClass[i] + '">' + o.value + '</span>' + o.key + '</a>');
                    });
//            if (data === "") {
//                data = $("#div_quotes").append('<li>No Data Available</li>');
//            }
                }).fail(function () {

                }).always(function () {
                });
                $.getJSON(APP_CONFIG.context + "/showClaimsFigure.do", data, function () {
                }).done(function (result) {
                    var data = "";
                    $.each(result.aaData, function (i, o) {
                        data = $("#div_claims").append('<a href="javascript:void(0);" class="list-group-item"><span class="badge ' + badgeClass[i] + '">' + o.value + '</span>' + o.key + '</a>');
                    });
//            if (data === "") {
//                data = $("#div_claims").append('<li>No Data Available</li>');
//            }
                }).fail(function () {

                }).always(function () {
                });
                loadActivities();
                $.getJSON(APP_CONFIG.context + "/showTotalAndLossRatioFigure.do", data, function () {
                }).done(function (result) {
                    var tot_premium = 0, tot_claims = 0, loss_ratio = 0;
                    if (result.aaData) {
                        tot_premium = result.aaData[0].info1 ? result.aaData[0].info1 : 0;
                        tot_claims = result.aaData[0].info2 ? result.aaData[0].info2 : 0;
                        loss_ratio = result.aaData[0].info3 ? result.aaData[0].info3 : 0;
                        $.getJSON(APP_CONFIG.context + "/loadOffersFigure.do", {"company": "${company}", "customer.civilId": "${customer.civilId}", "info1": tot_premium, "info2": tot_claims}, function () {
                        }).done(function (result) {
                            var data = "";
                            $.each(result.aaData, function (i, o) {
                                data = $("#div_offers").append('<li><div class="m-b-5">' + o.value + '</div></li>');
                            });
                            if (data === "") {
                                data = $("#div_offers").append('<li>No Data Available</li>');
                            }
                        }).fail(function () {

                        }).always(function () {

                        });
                    }
                    $("#div_tot_premium").autoNumeric("set", tot_premium);
                    $("#div_tot_claims").autoNumeric("set", tot_claims);
                    $("#div_loss_ratio").text(loss_ratio + "%");
                }).fail(function () {

                }).always(function () {

                });

                $.getJSON(APP_CONFIG.context + "/loadProductivityFigure.do", data, function () {
                }).done(function (result) {

                    loadPieChart(result);
                }).fail(function () {

                }).always(function () {

                });
                //loadActivities();
                $("#btn_clm_car").on("click", function () {
                    window.open(APP_CONFIG.context + "/AnoudAppIntegration.do?flex1=Policy&flex2=CIVIL_ID&flex3=${customer.civilId}");
                });
                $("#btn_mail_more").on("click", function () {
                    window.location.href = "<%=request.getContextPath()%>/openMailDetailsFigure.do?customer.emailId=${customer.emailId}&company=${company}";
                            });
                        });
                        function callCustomer(mobileNo) {
        <s:if test='%{!"N".equals(#session.USER_INFO.ameyoChannel) && #session.USER_INFO.agentType == 1}'>
                            if (window.opener) {
                                window.opener.doDial(mobileNo);
                            } else {
                                doDial(mobileNo);
                            }
        </s:if>
        <s:else>
                            var data = {"customer.mobileNo": mobileNo};
                            $.ajax({
                                type: "POST",
                                url: APP_CONFIG.context + "/callCustomer.do",
                                data: data,
                                async: true,
                                success: function (result) {
                                    if (result.messageType === "S") {
                                        $.notify(data["customer.mobileNo"] + " calling...", "custom");
                                    }
                                },
                                error: function (xhr, status, error) {
                                    alert("Error: " + error);
                                },
                                complete: function () {
                                }
                            });
        </s:else>
                        }
    function loadActivities() {
        block('block_body');
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/loadLogFigure.do",
            data: data,
            async: true,
            success: function (result) {
                var data = "";
                $("#div_activities").html("");
                $.each(result.aaData, function (i, o) {
                    if (o.info2 === null) {
                        o.info2 = "";
                    }
                    var logs =
                            '<div class="container-fluid" style="padding:0px;">' +
                            '<div class="row border-bottom">' +
                            ' <div class="col-md-2 ">' + '<span class="badge ' + o.info4 + '">' + o.value + '</span>' + '</div>' +
                            '<div class="col-md-2">' + '<span class="hdng-dg bold">' + o.info2 + '</span>' + '<br />' + '<span class="hdng-dg bold">Date: </span>' + o.info3 + '</div>' +
                            // '<div class="col-md-3 pinfo">'+ '<span class="' + o.info4 + '">' + o.value + '</span>' + '<br />'+ '<span class="hdng-dg bold">REF: </span>' +'</div>' +
                            '<div class="col-md-8">' + o.info1 + '</div>' +
                            ' </div>' +
                            '</div>';


                    // '<div class="list-group shakeel">' +
                    // '<a href="javascript:void(0);" class="nocursor list-group-item">' +
                    // '<span class="badge ' + o.info4 + '">' + o.value + '</span><h5 class="list-group-item-heading cstm-item-heading">' + o.info2 + '<span>' + o.info3 + '</span></h5>' +
                    // '<p class="list-group-item-text">' + o.info1 + '</p>' +
                    // '</a>' +
                    // '</div>'; 
                    data = $("#div_activities").append(logs);
                });
                if (data === "") {
                    $("#div_activities").append('<li>No Data Available</li>');
                }

            },
            error: function (xhr, status, error) {
                alert("Error: " + error);
            },
            complete: function () {
                unblock('block_body');
                /*loadEmail();
                 loadCallLog();*/
                loadTask();
                loadWhatsApp();
            }
        });
    }

    function loadPieChart(result) {
        var xAxisData = [], premiumData = [], claimsData = [];
        $.each(result.aaData, function (i, o) {
            xAxisData.push(o.key);
            premiumData.push(Number(o.value));
            claimsData.push(Number(o.info1));
        });
        var chart_productivity = echarts.init(document.getElementById('div_chart_productivity'));
        var chart_productivity_options = {
            title: {
                text: 'Premium vs Claims'/*,
                 subtext: ''*/
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                y: "bottom",
                data: [
                    'Premium', 'Claims'
                ]
            },
            toolbox: {
                show: true,
                feature: {
                    mark: {show: false},
                    dataView: {show: false, readOnly: false},
                    magicType: {show: true, type: ['line', 'bar']},
                    restore: {show: false},
                    saveAsImage: {show: true}
                }
            },
            calculable: true,
            //grid: {y: 70, y2: 30, x2: 20},
            xAxis: [
                {
                    type: 'category',
                    axisLine: {show: false},
                    axisTick: {show: false},
                    axisLabel: {show: true},
                    splitArea: {show: false},
                    splitLine: {show: false},
                    data: xAxisData
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    //splitArea : {show : true},
                    axisLabel: {formatter: '{value}'}
                }
            ],
            series: [
                {
                    name: 'Premium',
                    type: 'bar',
                    itemStyle: {normal: {label: {show: true, formatter: function (p) {
                                    return p.value > 0 ? (p.value + '\n') : '';
                                }}}},
                    data: premiumData
                },
                {
                    name: 'Claims',
                    type: 'bar',
                    itemStyle: {normal: {label: {show: true}}},
                    data: claimsData
                }
            ],
            color: ['#88c069', '#ef0e73', '#8ad0f9', '#fdc16c', '#b1d653', '#fa5840', '#00a640', '#cd5c5c', '#ba55d3', '#ffa500', '#40e0d0', '#ff69b4', '#6495ed', '#32cd32', '#da70d6', '#87cefa', '#87cefa', '#ff69b4', '#c37cca', '#8f95e7']
        };
        chart_productivity.setOption(chart_productivity_options);
        window.onresize = function () {
            setTimeout(function () {
                chart_productivity.resize();
            }, 200);
        }

        setTimeout(function () {
            chart_productivity.resize();
        }, 1000);
    }
    function loadEmail() {
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/loadEmailCampFigure.do",
            data: data,
            async: true,
            success: function (response) {
                var data = "";
                $.each(response.aaData, function (i, o) {
                    data += ("<tr><td>" + o.key + "</td><td>" + o.value + "</td><td>" + o.info + "</td><td>" + o.info1 + "</td></tr>");
                });
                if (data === "") {
                    data = "<tr><td colspan=\"4\"><div class=\"no-data\"></div></td></tr>";
                }
                $("#tbl_campmail > tbody").html(data);
            },
            error: function (xhr, status, error) {
                alert("Error: " + error);
            },
            complete: function () {
            }
        });
    }
    function loadCallLog() {
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/loadCallLogFigure.do",
            data: data,
            async: true,
            success: function (response) {
                var data = "";
                $.each(response.aaData, function (i, o) {
                    data += ("<tr><td>" + o.key + "</td><td>" + o.value + "</td><td>" + o.info + "</td><td>" + o.info1 + "</td></tr>");
                });
                if (data === "") {
                    data = "<tr><td colspan=\"4\"><div class=\"no-data\"></div></td></tr>";
                }
                $("#tbl_call_log > tbody").html(data);
            },
            error: function (xhr, status, error) {
                alert("Error: " + error);
            },
            complete: function () {
            }
        });
    }

    function loadTask() {
        $("#tbl_closed_task").DataTable({
            paging: true,
            searching: false,
            responsive: true,
            ordering: true,
            info: true,
            lengthChange: false,
            pageLength: 5,
            "ajax": {
                "url": APP_CONFIG.context + "/loadTaskFigure.do?customer.civilId=<s:property value='customer.civilId'/>&company=<s:property value='company'/>",
                "type": "POST",
                "contentType": "application/json",
                "dataSrc": "aaData"
            },
            columns: [
                {data: "ctId",
                    render: function (data, type, row, meta) {
                        if (type === "sort") {
                            return data;
                        }
                        if (row.ctSlaViolated === "1") {
                            data += '&nbsp;<span><i class="fa fa-flag" title="SLA Violated" style="color: #ef1515"></i></span>';
                        }
                        if (row.ctFlex05 !== "" && row.ctFlex05 !== null) {
                            data += '&nbsp;<span><i class="fa fa-paperclip" title="Attachments" style="color: #080808"></i></span>';
                        }
                        return data;
                    }
                },
                {data: "ctSubCatgDesc",
                    render: function (data, type, row, meta) {
                        if (type === "sort") {
                            return data;
                        }
                        if (data === null) {
                            data = "";
                        }
                        if (row.ctPriority !== "" && row.ctPriority !== null && row.ctPriority === 'C') {
                            return '<div class="task-critical priority" title="Critical" data-toggle="tooltip" data-placement="top">&nbsp;</div>&nbsp;' + data;
                        } else if (row.ctPriority !== "" && row.ctPriority !== null && row.ctPriority === 'H') {
                            return '<div class="task-high priority" title="High" data-toggle="tooltip" data-placement="top">&nbsp;</div>&nbsp;' + data;
                        } else if (row.ctPriority !== "" && row.ctPriority !== null && row.ctPriority === 'N') {
                            return '<div class="task-normal priority" title="Normal" data-toggle="tooltip" data-placement="top">&nbsp;</div>&nbsp;' + data;
                        } else if (row.ctPriority !== "" && row.ctPriority !== null && row.ctPriority === 'L') {
                            return '<div class="task-low priority" title="Low" data-toggle="tooltip" data-placement="top">&nbsp;</div>&nbsp;' + data;
                        }
                    }
                },
                {data: "ctRefNo"},
                {data: "ctCloseRemarks", orderable: false,
                    render: function (data, type, row, meta) {
                        return '<div style="white-space: pre-wrap;">' + data + '</div>';
                    }
                },
                {data: "ctCloseDate",
                    render: function (data, type, row, meta) {
                        if (data && type === "sort") {
                            data = moment(data, "YYYY-MM-DDTHH:mm:ss").format('YYYYMMDDHHmmss');
                        } else {
                            data = moment(data, "YYYY-MM-DDTHH:mm:ss").format('DD/MM/YYYY');
                        }
                        return data;
                    }
                },
                {data: "ctAssignedToDesc"}
            ],
            order: [4, 'desc'],
            initComplete: function () {
            }
        });
    }

    function loadWhatsApp() {
        var mobileNo = '<s:if test='customer.mobileNo != null && customer.mobileNo != ""'>,<s:property value="customer.mobileNo"/></s:if><s:if test='customer.mobileNo1 != null && customer.mobileNo1 != ""'>,<s:property value="customer.mobileNo1"/></s:if><s:if test='customer.mobileNo2 != null && customer.mobileNo2 != ""'>,<s:property value="customer.mobileNo2"/></s:if>';
            if (mobileNo.length > 0) {
                mobileNo = mobileNo.substr(1);
            }
            $("#tbl_wa_log").DataTable({
                paging: true,
                searching: false,
                responsive: true,
                ordering: true,
                info: true,
                lengthChange: false,
                pageLength: 5,
                "ajax": {
                    "url": APP_CONFIG.context + "/loadWhatsAppLatestConversation.do?log.wlMobileNo=" + mobileNo + "&company=<s:property value='company'/>",
                    "type": "POST",
                    "contentType": "application/json",
                    "dataSrc": "aaData"
                },
                columns: [
                    {data: "wlLogId"},
                    {data: "wlLogDate",
                        render: function (data, type, row, meta) {
                            if (data && type === "sort") {
                                data = moment(data, "YYYY-MM-DDTHH:mm:ss").format('YYYYMMDDHHmmss');
                            } else {
                                data = moment(data, "YYYY-MM-DDTHH:mm:ss").format('DD/MM/YYYY');
                            }
                            return data;
                        }
                    },
                    {data: "wlMobileNo"},
                    {data: "wlMsgMode",
                        render: function (data, type, row, meta) {
                            if (type === "sort") {
                                return data;
                            }
                            if (row.wlMsgMode == "I") {
                                return 'Incoming';
                            } else if (row.wlMsgMode == "O") {
                                return 'Outgoing';
                            }
                            return data;
                        }
                    },
                    {data: "wlText"},
                    {data: "wlMsgType",
                        render: function (data, type, row, meta) {
                            if (type === "sort") {
                                return data;
                            }
                            if (row.wlMsgType === "I") {
                                return 'Image';
                            } else if (row.wlMsgType === "V") {
                                return 'Voice';
                            } else if (row.wlMsgType === "T") {
                                return 'Text';
                            } else if (row.wlMsgType === "D") {
                                return 'Document';
                            }
                            return data;
                        }
                    }
                ],
                order: [1, 'desc'],
                initComplete: function () {
                }
            });
        }
    </script>
    <script>
        $(document).ready(function () {
            $('.collapse.in').prev('.panel-heading').addClass('active');
            $('#accordion, #bs-collapse')
                .on('show.bs.collapse', function (a) {
                    $(a.target).prev('.panel-heading').addClass('active');
                })
                .on('hide.bs.collapse', function (a) {
                    $(a.target).prev('.panel-heading').removeClass('active');
                });
        });
    </script>
