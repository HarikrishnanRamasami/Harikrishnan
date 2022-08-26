<%-- 
    Document   : search
    Created on : 17 Apr, 2017, 4:37:57 PM
    Author     : palani.rajan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<div class="">
  <div class="col-md-12 right-pad">
<section class="actvt_main">
    <div class="dash-leads" style="border-top:0!important">
        <div class="row clearfix">
             <div class="col-lg-12 col-md-12 col-sm-6 col-xs-12">
                <div class="card">
                    <div class="my-bord">
                        <h3><s:text name="lbl.search.details"/></h3>
                        <span class="pull-right"><button class="btn btn-primary btn-sm m-5" onclick=" window.open('/crm', '_parent'); return false;" ><s:text name="btn.go.back"/></button></span>
                        <div class="clearfix"></div>
                        <ul class="header-dropdown m-r-0">
                            <%--li>
                                <a href="javascript:void(0);">
                                    <i class="icon-fullscreen" data-toggle="tooltip" data-placement="top" title="" data-original-title="Expand"></i>
                                </a>
                            </li--%>
                        </ul>
                    </div>
                    <div class="body">
                        <form>
                            <div class="row clearfix">
                                <div class="col-lg-6 col-md-6">
                                    <div class="input-group">
                                        <span class="input-group-addon" id="title_hdng"><s:text name="lbl.search.member"/></span>
                                        <div class="form-line">
                                                <s:if test='%{@qa.com.qic.common.util.ApplicationConstants@APP_TYPE_QLM.equals(#session.USER_INFO.appType)}'>
                                                    <select id="sel_search_member_by" class="form-control show-tick" onchange="setAddlTextField(this.value);">
                                                    <option value="MEM_ID">Member Id</option>
                                                    <option value="CIVIL_ID">Civil ID</option>
                                                    <option value="Mobile_No">Mobile No</option>
                                                    <option value="EMP_ID">Employee No</option>
                                                    <option value="MEM_NAME">Member Name</option>
                                                    </select>
                                                </s:if>
                                                <s:else>
                                                    <select id="sel_search_member_by" class="form-control show-tick">
                                                    <option value="MOBILE_NO">Mobile No</option>
                                                    <option value="CIVIL_ID">Civil ID</option>
                                                    </select>
                                                </s:else>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-6 col-md-6">
                                    <div class="input-group">
                                        <div class="form-line">
                                            <div style="display:none" id="div_policy_no">
                                                <div class="form-line" style="width: 50%;">
                                                    <input type="text" class="form-control" id="txt_member_policyNo" placeholder="Policy No" style="width: 100%;margin-left: -1px;"> 
                                                    <input type="text" class="form-control" id="txt_member_val" placeholder="Member" style="width: 100%;margin-top: -34px;margin-left: 282px;">
                                                </div>
                                            </div>
                                            <div id="div_member_name">
                                                <input type="text" class="form-control" id="txt_search_member_val" placeholder="Member">
                                            </div>
                                        </div>
                                        <span class="input-group-addon">
                                            <i class="fa fa-search" data-catagory="Member" id="btn_search_member"></i>
                                        </span>
                                    </div>
                                </div>
                            </div>
                            <div class="row clearfix">
                                <div class="col-lg-6 col-md-6">
                                    <div class="input-group">
                                        <span class="input-group-addon" id="title_hdng"><s:text name="lbl.search.policy"/></span>
                                        <div class="form-line">
                                            <select id="sel_search_policy_by" class="form-control show-tick">
                                                <s:if test='%{@qa.com.qic.common.util.ApplicationConstants@APP_TYPE_QLM.equals(#session.USER_INFO.appType)}'>
                                                    <option value="POLICY_NO">Policy No</option>
                                                    <option value="REF_NO">Ref No</option>
                                                    <option value="INS_NAME">Assured Name</option>
                                                    <option value="CUST_CODE">Customer</option>
                                                </s:if>
                                                <s:else>
                                                    <option value="0">--Select--</option>
                                                    <option value="POLICY_NO">Policy No</option>
                                                    <option value="POL_REF_NO">Ref No</option>
                                                    <option value="QUOT_NO">Quote No</option>
                                                    <option value="MOBILE_NO">Mobile No</option>
                                                    <option value="REG_NO">Plate No</option>
                                                    <option value="CIVIL_ID">Civil ID</option>
                                                    <option value="CHASSIS_NO">Chassis No</option>
                                                    <option value="ENGINE_NO">Engine No</option>
                                                    <option value="CERT_NO">Certificate No</option>
                                                    <option value="INV_NO">Invoice number</option>
                                                    <option value="BILL_LAD_NO">Purchase Order number</option>
                                                    <option value="LC_DATE_NO">LC No</option> 
                                                </s:else>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-6 col-md-6">
                                    <div class="input-group">
                                        <div class="form-line">
                                            <input type="text" class="form-control" id="txt_search_policy_val" placeholder="Policy">
                                        </div>
                                        <span class="input-group-addon">
                                            <i class="fa fa-search" data-catagory="Policy" id="btn_search_policy"></i>
                                        </span>
                                    </div>
                                </div>
                            </div>
                            <div class="row clearfix">
                                <div class="col-lg-6 col-md-6">
                                    <div class="input-group">
                                        <span class="input-group-addon" id="title_hdng"><s:text name="lbl.search.quote"/></span>
                                        <div class="form-line">
                                            <select id="sel_search_quote_by" class="form-control show-tick">
                                                <s:if test='%{@qa.com.qic.common.util.ApplicationConstants@APP_TYPE_QLM.equals(#session.USER_INFO.appType)}'>
                                                    <option value="QUOTE_NO">Quote No</option>
                                                    <option value="REF_NO">Ref No</option>
                                                    <option value="INS_NAME">Assured Name</option>
                                                </s:if>
                                                <s:else>
                                                    <option value="0">--Select--</option>
                                                    <option value="QUOT_NO">Quote No</option>
                                                    <option value="POL_REF_NO">Ref No</option>
                                                    <option value="MOBILE_NO">Mobile No</option>
                                                    <option value="CIVIL_ID">Civil ID</option>
                                                    <option value="CHASSIS_NO">Chassis No</option>
                                                    <option value="ENGINE_NO">Engine No</option>
                                                    <option value="REG_NO">Plate No</option> 
                                                </s:else>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-6 col-md-6">
                                    <div class="input-group">
                                        <div class="form-line">
                                            <input type="text" class="form-control" id="txt_search_quote_val" placeholder="Quote">
                                        </div>
                                        <span class="input-group-addon">
                                            <i class="fa fa-search" data-catagory="Quote" id="btn_search_quote"></i>
                                        </span>
                                    </div>
                                </div>
                            </div>
                            <div class="row clearfix">
                                <div class="col-lg-6 col-md-6">
                                    <div class="input-group">
                                        <span class="input-group-addon" id="title_hdng"><s:text name="lbl.search.claim"/></span>
                                        <div class="form-line">
                                            <select id="sel_search_claim_by" class="form-control show-tick">
                                                <s:if test='%{@qa.com.qic.common.util.ApplicationConstants@APP_TYPE_QLM.equals(#session.USER_INFO.appType)}'>
                                                    <option value="MEM_ID">Member Id</option>
                                                    <option value="EMP_MEM_ID">Employee Member Id</option>
                                                    <option value="CIVIL_ID">Civil Id</option>
                                                    <option value="POLICY_NO">Policy No</option>
                                                    <option value="MASTER_BATCH">Master Batch</option>
                                                    <option value="BATCH_NO">Batch No</option>
                                                    <option value="CLAIM_NO">Claim No</option>
                                                    <option value="INVOICE_NO">Invoice No</option>
                                                    <option value="VISIT_NO">Visit No</option>
                                                    <option value="DOC_NO">Document No</option>
                                                    <option value="PRE_APPR_NO">Pre-Approval No</option>
                                                </s:if>
                                                <s:else>
                                                    <option value="0">--Select--</option>
                                                    <option value="CLM_NO">Claim No</option>
                                                    <option value="CLM_POLICY_NO">Policy No</option>
                                                    <option value="CLM_CLMNT_MOBILE_NO">Mobile No</option>
                                                    <option value="CLM_CLMNT_CIVIL_ID">Civil ID</option>
                                                    <option value="CLM_POLICE_REF_NO">Police Ref No</option>
                                                    <option value="CRP_VEH_CHASSIS_NO">Chassis No</option>
                                                    <option value="CRP_VEH_REGN_NO">Plate No</option> 
                                                </s:else>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-6 col-md-6">
                                    <div class="input-group">
                                        <div class="form-line">
                                            <input type="text" class="form-control" id="txt_search_claim_val" placeholder="<s:text name="lbl.claim"/>">
                                        </div>
                                        <span class="input-group-addon">
                                            <i class="fa fa-search" data-catagory="Claim" id="btn_search_claim"></i>
                                        </span>
                                    </div>
                                </div>
                            </div>
                            <div class="row clearfix">
                                <div class="col-lg-6 col-md-6">
                                    <div class="input-group"></div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <script type="text/javascript">
                    $("#txt_search_member_val, #txt_search_policy_val, #txt_search_quote_val, #txt_search_claim_val").on("keypress", function (event) {
                        var keyCode = (event.keyCode ? event.keyCode : event.which);
                        if (keyCode === 13) {
                            //console.log("Id : "+($(this).prop("id").replace("txt", "btn")).substr(0, $(this).prop("id").lastIndexOf("_")));
                            $("#" + ($(this).prop("id").replace("txt", "btn")).substr(0, $(this).prop("id").lastIndexOf("_"))).trigger('click');
                            event.preventDefault();
                        }
                    });

                    $("#btn_search_member, #btn_search_policy, #btn_search_quote, #btn_search_claim").on("click", function () {
                        var _this = $(this);
                        var catagory, searchBy, searchValue;
                        catagory = $(_this).data("catagory");
                        var id = $(_this).prop("id");
                        searchBy = $("#" + id.replace("btn", "sel") + "_by").val();
                        searchValue = $("#" + id.replace("btn", "txt") + "_val").val();
                        if(searchBy != null && searchBy !='' && searchBy=='MEM_NAME'){
                            searchValue = $("#txt_member_val").val();
                        }
                        //console.log("catagory: "+catagory+", searchBy: "+searchBy+", searchValue: "+searchValue);
                        if (!catagory) {
                            alert("Search parameter missing");
                        } else if (searchBy === "0") {
                            alert("Please select search category");
                        } else if ($.trim(searchValue) === "") {
                            alert("Please enter value for search");
                        } else {
                            <s:if test='%{@qa.com.qic.common.util.ApplicationConstants@APP_TYPE_QLM.equals(#session.USER_INFO.appType)}'>
                                    let url = APP_CONFIG.context + "/QLMAppIntegration.do?operation=Y&flex1=" + catagory + "&flex2=" + searchBy + "&flex3=" + searchValue;
                                if(searchBy != null && searchBy !='' && searchBy=='MEM_NAME'){
                                    var policyNo=$("#txt_member_policyNo").val();
                                    url = url + "&flex4=" + policyNo;
                                }
                                var win = window.open(url, '_blank');
                            </s:if>
                            <s:else>
                            let url = APP_CONFIG.context + "/AnoudAppIntegration.do?operation=Y&flex1=" + catagory + "&flex2=" + searchBy + "&flex3=" + searchValue;
                            if (id === "btn_search_member") {
                                //window.location.href = url;
                                url = url + "&search=plugin";
                            }// else {
                                //window.open(url);
                                $("#if_anoud_app").attr("src", url);
                                /*if($("#block_crm_app").is(":visible")) {
                                    $("#block_crm_app").slideUp("slow");
                                }*/
                                if(!$("#block_anoud_app").is(":visible")) {
                                    $("#block_anoud_app").slideDown("slow");
                                }
                            //}
                            </s:else>
                        }
                    });
                    $(document).ready(function () {
                        $("#btn_anoud_app_close").on("click", function () {
                            $("#if_anoud_app").attr("src", APP_CONFIG.context + "/blank.jsp");
                            $("#block_crm_app").slideDown("slow");
                            $("#block_anoud_app").slideUp("slow");
                        });
                    });
                    function setAddlTextField(value){
                        if(value !== null && value !=='' && value ==='MEM_NAME'){
                            $("#div_policy_no").show();
                            $("#div_member_name").hide();
                            $("#txt_member_policyNo").val('');
                            $("#txt_search_member_val").val('').attr("placeholder","Member Name");
                        }else{
                            $("#div_policy_no").hide();
                            $("#div_member_name").show();
                            $("#txt_search_member_val").val('').removeAttr("placeholder");
                        }

                    }
                </script>
            </div>
        </div>
    </div>
</section>
</div>
</div>
<section id="block_anoud_app" style="display: none;">
    <div class="container-fluid">
        <div class="row clearfix">
            <button type="button" class="btn btn-danger waves-effect pull-right" id="btn_anoud_app_close"> <s:text name="btn.close"/></button>
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                <iframe name="if_anoud_app" id="if_anoud_app" style="width: 100%; border: 0px; height: 700px;"></iframe>
            </div>
        </div>
    </div>
</section>