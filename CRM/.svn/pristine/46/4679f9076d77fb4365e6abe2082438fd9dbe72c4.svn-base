<%-- 
    Document   : medical360
    Created on : 5 Dec, 2017, 3:33:40 PM
    Author     : sutharsan.g
--%>



<%@page import="qa.com.qic.common.util.ApplicationConstants"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
 <script src="<%=request.getContextPath()%>/plugins/magnific-popup/jquery.magnific-popup.min.js"></script>
<style type="text/css">
    
 /* padding-bottom and top for image */
.mfp-no-margins img.mfp-img {
	padding: 0;
}
/* position of shadow behind the image */
.mfp-no-margins .mfp-figure:after {
	top: 0;
	bottom: 0;
}
/* padding for main container */
.mfp-no-margins .mfp-container {
	padding: 0;
}


/* 

for zoom animation 
uncomment this part if you haven't added this code anywhere else

*/
/*

.mfp-with-zoom .mfp-container,
.mfp-with-zoom.mfp-bg {
	opacity: 0;
	-webkit-backface-visibility: hidden;
	-webkit-transition: all 0.3s ease-out; 
	-moz-transition: all 0.3s ease-out; 
	-o-transition: all 0.3s ease-out; 
	transition: all 0.3s ease-out;
}

.mfp-with-zoom.mfp-ready .mfp-container {
		opacity: 1;
}
.mfp-with-zoom.mfp-ready.mfp-bg {
		opacity: 0.8;
}

.mfp-with-zoom.mfp-removing .mfp-container, 
.mfp-with-zoom.mfp-removing.mfp-bg {
	opacity: 0;
}
*/

    <%-- Chat Style --%>
    #div_chats span
    {
        display: inline-block;
        max-width: 209px;
        background-color: #e8f9f2;
        padding: 5px;
        border-radius: 4px;
        position: relative;
        border-width: 1px;
        border-style: solid;
        border-color: grey;
    }

    left
    {
        float: left;
    }

    #div_chats span.left:after
    {
        content: "";
        display: inline-block;
        position: absolute;
        left: -8.5px;
        top: 7px;
        height: 0px;
        width: 0px;
        border-top: 8px solid transparent;
        border-bottom: 8px solid transparent;
        border-right: 8px solid #e8f9f2;
    }

    #div_chats span.left:before
    {
        content: "";
        display: inline-block;
        position: absolute;
        left: -9px;
        top: 7px;
        height: 0px;
        width: 0px;
        border-top: 8px solid transparent;
        border-bottom: 8px solid transparent;
        border-right: 8px solid black;
    }

    #div_chats span.right:after
    {
        content: "";
        display: inline-block;
        position: absolute;
        right: -8px;
        top: 6px;
        height: 0px;
        width: 0px;
        border-top: 8px solid transparent;
        border-bottom: 8px solid transparent;
        border-left: 8px solid #dbedfe;
    }

    #div_chats span.right:before
    {
        content: "";
        display: inline-block;
        position: absolute;
        right: -9px;
        top: 6px;
        height: 0px;
        width: 0px;
        border-top: 8px solid transparent;
        border-bottom: 8px solid transparent;
        border-left: 8px solid black;
    }

    #div_chats span.right
    {
        float: right;
        background-color: #dbedfe;
    }

    .clear
    {
        clear: both;
        margin-bottom:5px;  
    }
    <%-- Chat Style --%>
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
    ul.prolist-details .pull-left:first-child {
        color: #0e0a0a;
        font-weight: bolder !important;
    }
    .prioritya{
        display: inline-block;
        width: 67px;
        height: 32px;
        background-color: #e20808;
    }
    .priority{
        display: inline-block;
        width: 5px;
        height: 20px;
    }

    .ui-front {
        z-index: 99999!important;
    }
    .dataTables_wrapper .dataTables_filter input {
    margin-left: -4.5em !important;
    }
    
    #task_tbl_wrapper .row .col-sm-12{
        text-align:right !important;
    }
</style>

<s:hidden id="hid_memId"/>
<s:hidden id="hid_memSrNo"/>

<div>    
    <div class="col-md-3 right-pad">
        <div class="edit-pro">
            <h4><a class="pull-right edi-pro" href="javascript:void(0);" id="btn_cust_edit"><i class="la la-pencil"></i></a><s:property value="customer.name"/></h4>
            <span class="sinc-eng"><s:text name="lbl.since"/> <s:property value="customer.startYear"/> (<s:if test='"A".equals(preferLang)'><s:text name="lbl.arabic"/></s:if><s:else><s:text name="lbl.english"/></s:else>)</span>
            <p><span class="notes" id="btn_log"><a href="#" title="<s:text name="lbl.activities"/>" ><img src="<%=request.getContextPath()%>/plugins/innovate/images/notes.png"></a></span>
                <span class="activites" id="btn_feedback"><a href="#" data-toggle = "tooltip" data-placement = "top" title="FeedBack"><img src="<%=request.getContextPath()%>/plugins/innovate/images/activity_2.png"></a></span></p>
        </div>
        <div class="pro-details">
            <h4><s:text name="lbl.profile"/></h4>
            <ul class="prolist-details">
                <s:if test='"M".equals(customer.sourceType)'>
                    <li><div class="pull-left id-deta"><s:text name="lbl.civil.id"/></div><div class="pull-left id-deta"><s:property value="customer.civilId"/></div></li>
                    </s:if>
                    <s:if test='"P".equals(customer.sourceType)'>
                    <li><div class="pull-left id-deta"><s:text name="lbl.provider.code"/></div><div class="pull-left id-deta"><s:property value="customer.civilId"/></div></li>
                    </s:if>
                <li><div class="pull-left id-deta"><s:text name="lbl.po.box"/></div><div class="pull-left id-deta"><s:property value="customer.poBox"/></div></li>
                <li><div class="pull-left id-deta"><s:text name="lbl.nationality"/></div><div class="pull-left id-deta"><s:property value="customer.nationality"/></div></li>
                <li><div class="pull-left id-deta"><s:text name="lbl.country"/></div><div class="pull-left id-deta"><s:property value="customer.country"/></div></li>
                <li><div class="pull-left id-deta"><s:text name="lbl.gender"/></div><div class="pull-left id-deta"><s:property value="customer.gender"/></div></li>
                <li><div class="pull-left id-deta"><s:text name="lbl.age"/></div><div class="pull-left id-deta"><s:property value="customer.age"/></div></li>
                <li><div class="pull-left id-deta"><s:text name="lbl.work.place"/></div><div class="pull-left id-deta"><s:property value="customer.workPlace"/></div></li>
                <li><div class="pull-left id-deta"><s:text name="lbl.occupation"/></div><div class="pull-left id-deta"><s:property value="customer.occupation"/></div></li>
            </ul>
            <h4><s:text name="lbl.contact.information"/></h4>

            <ul class="user-information">
                <li>
                    <div class="pull-left det-mbl"><s:text name="lbl.mobile.no"/></div>
                    <div class="pull-right det-no">
                        <s:if test='customer.mobileNo != null && customer.mobileNo != ""'>
                            <span class="contact-icons">
                                <i class="la la-phone-square hand" onclick="callCustomer('<s:property value="customer.mobileNo"/>');"></i>
                                <i class="la la-comment hand" onclick="callSms(<s:property value="customer.mobileNo"/>);"></i>
                            </span>
                        </s:if>
                        <s:property value="customer.mobileNo"/>
                    </div>
                </li>
                <li>
                    <div class="pull-left det-mbl"><s:text name="lbl.mobile.no1"/></div>
                    <div class="pull-right det-no">
                        <s:if test='customer.mobileNo1 != null && customer.mobileNo1 != ""'>
                            <span class="contact-icons">
                                <i class="la la-phone-square hand" onclick="callCustomer('<s:property value="customer.mobileNo1"/>');"></i>
                                <i class="la la-comment hand" onclick="callSms(<s:property value="customer.mobileNo1"/>);"></i>
                            </span>
                        </s:if>
                        <s:property value="customer.mobileNo1"/>
                    </div>
                </li>
                <li>
                    <div class="pull-left det-mbl"><s:text name="lbl.mobile.no2"/></div>
                    <div class="pull-right det-no">
                        <s:if test='customer.mobileNo2 != null && customer.mobileNo2 != ""'>
                            <span class="contact-icons">
                                <i class="la la-phone-square hand" onclick="callCustomer('<s:property value="customer.mobileNo2"/>');"></i>
                                <i class="la la-comment hand" onclick="callSms(<s:property value="customer.mobileNo2"/>);"></i>
                            </span>
                        </s:if>
                        <s:property value="customer.mobileNo2"/>
                    </div>
                </li>
                <li>
                    <div class="pull-left det-mbl"><s:text name="lbl.tel.no"/></div>
                    <div class="pull-right det-no">
                        <s:if test='customer.telNo != null && customer.telNo != ""'>
                            <span class="contact-icons"><i class="la la-phone-square hand" onclick="callCustomer('<s:property value="customer.telNo"/>');"></i></span>
                        </s:if>
                        <s:property value="customer.telNo"/>
                    </div>
                </li>
                <li>
                    <div class="pull-left det-mbl"><s:text name="lbl.email.id"/></div>
                    <div class="pull-right det-no">
                        <s:if test='customer.emailId != null && customer.emailid != ""'>
                            <span class="contact-icons"><i class="la la-envelope hand" onclick="callEmail();"></i></span>
                        </s:if>
                        <s:property value="customer.emailId"/>
                    </div>
                </li>
                <li><div class="pull-left det-mbl"><s:text name="lbl.fax.no"/></div>
                    <div class="pull-right det-no">
                        <s:property value="customer.faxNo"/>
                    </div>
                </li>
            </ul>

            <h4><s:text name="lbl.personal.information"/></h4>
            <ul class="per-info">
                <li><div class="pull-left det-mbl"><s:text name="lbl.birth.date"/></div><div class="pull-right det-no"><s:property value="customer.birthDt"/></div></li>
                <li><div class="pull-left det-mbl"><s:text name="lbl.wedding.date"/></div><div class="pull-right det-no"><s:property value="customer.weddingDt"/></div></li>
                <li><div class="pull-left det-mbl"><s:text name="lbl.id.expiry.date"/></div><div class="pull-right det-no"><s:property value="customer.idExpDt"/></div></li>
                <li><div class="pull-left det-mbl"><s:text name="lbl.license.exp.date"/></div><div class="pull-right det-no"><s:property value="customer.licenseExpDt"/></div></li>
            </ul>
        </div>
    </div>
    <s:if test='"M".equals(customer.sourceType)'>
        <div class="col-md-9" style=" margin-top: 10px;">
            <div class="row clearfix top_search">
                <div class="col-sm-12 col-md-12 Tmar">               
                    <s:form class="form-inline" id="frm_policy" name="frm_policy" method="post" theme="simple">
                        <div class="form-group padR">
                            <label for="email"><s:text name="lbl.policy.no"/></label>
                            <s:select class="form-control" name="PolicyStatus" id="policyNo" list="policyList" listKey="trmTransId" listTitle="trmMemSrNo" listValue="trmPolicyNo" onchange="loadMemberList(this,this.value, 'member');"/>
                        </div>  
                        <div class="form-group padR">
                            <label for="pwd"><s:text name="lbl.member"/></label>
                            <s:select class="form-control" name="member" id="member" list="memberList" listKey="trmMemSrNo" listTitle="trmMemId" listValue="trmMemberName" onchange="loadMemberDetails(this,this.value);"/>

                            <div class="form-group padR">
                                <div class="task-critical priority" id="trmStatus">
                                    <p style="margin-top: 7px;">
                                        <span id="trm_status"></span>
                                    </p>  
                                </div>
                            </div>
                            <div class="form-group padR">
                                <div class="task-critical prioritya" id="trmVipYn">
                                    
                                </div>
                            </div>
                        </s:form>
                    </div>
                </div>
            </div>
        </div>
    </s:if>
                
    <div class="col-md-9" style="color: red; font-weight: 600; margin-top: 10px; margin-bottom: -10px;">
        * <s:text name="lbl.info.provided.for.last"/> <s:if test='"M".equals(customer.sourceType)'>30</s:if><s:else>7</s:else> <s:text name="lbl.days"/>
    </div>
   
    <s:hidden name="customer.civilId" id="id_provCode"/>
    <div class="col-md-9 ">
        <div class="col-md-12 col-sm-12 col-xs-12 nopad">
            <div class="mem-off my-reminder">
                <s:if test='"M".equals(customer.sourceType)'>
                    <div id="member" class="mem-off-heads ">
                        <a href="javascript:void(0);" id="btn_policy" onclick="policyDetails();" class="active" data-tab="policies"><s:text name="lbl.policy.details"/></a>
                        <a href="javascript:void(0);" onclick="claimDetails();" class="" data-tab="claims"><s:text name="lbl.claims"/>&nbsp;(&nbsp;<strong id="claims_count"></strong>&nbsp;)</a>
                        <a href="javascript:void(0);" onclick="preApprovalDetails();" class="" data-tab="claims"><s:text name="lbl.pre.approvals"/>&nbsp;(&nbsp;<strong id="pre_approval_count"></strong>&nbsp;) </a>
                        <a href="javascript:void(0);" id="btn_enquiries" class="" data-tab="claims"><s:text name="lbl.enquiries"/>&nbsp;(&nbsp;<strong id="enquiries_count"></strong>&nbsp;)</a>
                        <a href="javascript:void(0);" id="btn_views" class="" data-tab="claims"><s:text name="lbl.visits"/>&nbsp;(&nbsp;<strong id="visits_count"></strong>&nbsp;)</a>
                    </div>
                </s:if>
                <s:if test='"P".equals(customer.sourceType)'>
                    <div class="mem-off-heads">
                        <a href="javascript:void(0);" id="btn_provider_approvals" onclick="preApprovalList();" class="" data-tab="PRE-APPROVALS"><s:text name="lbl.pre.approval"/></a>
                        <a href="javascript:void(0);" onclick="EclaimsDetails();" class="" data-tab="E-CLAIMS"><s:text name="lbl.e.Claims"/></a>
                        <a href="javascript:void(0);" onclick="providerEnquiries();" class="" data-tab="claims"><s:text name="lbl.enquiries"/></a>
                    </div>
                </s:if>
                <s:if test='"M".equals(customer.sourceType)'>
                    <ul class="mem-name current" id="div_policies">
                        <div class="body"  >
                            <ul class="prolist-details" style="margin-top: 10px;">
                                <div class="row mb-20">
                                    <div class="col-md-2 form-group"><b><s:text name="lbl.customer.name"/></b></div>
                                    <div class="col-md-4 form-group" >
                                        <span id="trm_ins_name"></span>
                                    </div>
                                </div>
                                <div class="row mb-20">
                                    <div class="col-md-2 form-group"><b><s:text name="lbl.Parent.name"/></b></div>
                                    <div class="col-md-4 form-group">
                                        <span id="trm_mem_name"></span>
                                    </div>
                                </div>
                                <div class="row mb-20">
                                    <div class="col-md-2 form-group"><b><s:text name="lbl.member.id"/></b></div>
                                    <div class="col-md-4 form-group">
                                        <span id="trm_mem_id" style="color:#0066FF;text-decoration:underline; cursor: pointer;" onclick="loadMedMemberDetails();"></span>
                                    </div>
                                    <div class="col-md-2 form-group"><b><s:text name="lbl.employee.no"/></b></div>
                                    <div class="col-md-4 form-group">
                                        <span id="trm_emp_no"></span>                
                                    </div>               
                                </div>
                                <div class="row mb-20">
                                    <div class="col-md-2 form-group"><b><s:text name="lbl.effective.from"/></b></div>
                                    <div class="col-md-4 form-group">
                                        <span id="trm_entry_dt"></span>
                                    </div>
                                    <div class="col-md-2 form-group"><b><s:text name="lbl.effective.to"/></b></div>
                                    <div class="col-md-4 form-group">
                                        <span id="trm_exit_dt"></span>             
                                    </div>               
                                </div>
                                <div class="row mb-20">
                                    <div class="col-md-2 form-group"><b><s:text name="lbl.plan"/></b></div>
                                    <div class="col-md-4 form-group">
                                        <span id="trm_plan"></span>
                                    </div>
                                    <div class="col-md-2 form-group"><b><s:text name="lbl.sub.plan"/></b></div>
                                    <div class="col-md-4 form-group">
                                        <span id="trm_sub_plan"></span>               
                                    </div>               
                                </div>
                                <div class="row mb-20">
                                    <div class="col-md-2 form-group"><b><s:text name="lbl.mobile.app"/></b></div>
                                    <div class="col-md-4 form-group">
                                        <span id="id_mobile_app"></span>     
                                    </div>
                                    <div class="col-md-2 form-group"><b><s:text name="lbl.member.portal"/></b></div>
                                    <div class="col-md-4 form-group">
                                        <span id="id_mobile_portal"></span>                
                                    </div>               
                                </div>
                            </ul>
                            <div class="col-sm-12 board-icons1 text-center" style="margin-left: 22px;">
                                <button class="view-btn" id="btn_add_mclaim"><s:text name="btn.add.claim"/></button>&nbsp;
                                <button class="view-btn" id="btn_search_provider"><s:text name="btn.search.provider"/></button>&nbsp;
                                <button class="view-btn" id="btn_push_notification" style="display: none"><s:text name="btn.push.notification"/></button>
                            </div>
                        </div>
                    </ul>
                </s:if>
                <div class="mem-name" id="div_claims">
                    <div class="dash-leads" style="border-top:0!important">
                        <div class="my-bord">
                        </div>
                        <div class="col-md-12">
                            <table id="tbl_claims" class="table table-striped table-bordered display dataTable dtr-inline">
                                <thead>
                                    <tr style="box-shadow:0 1px 3px 1px rgba(29, 77, 105, 0.14)">
                                        <th><s:text name="lbl.mb.ref.no"/></th>
                                        <th><s:text name="lbl.intimation.date"/></th>
                                        <th><s:text name="lbl.portal"/></th>
                                        <th><s:text name="lbl.requested.amt"/></th>
                                        <th><s:text name="lbl.approved.amt"/></th>
                                        <th><s:text name="lbl.mb.status"/></th>
                                        <th><s:text name="lbl.payment.status"/></th>
                                    </tr>
                                </thead>
                                <tbody>	   
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <%--div class="view_more" style="margin-top: 50px">
                        <button type="button" class="view-btn mbtn" id="btn_chat_more" title="Activities Details"> More</button>
                    </div--%>
                </div>
                <ul class="mem-name" id="div_pre_approvals">
                    <div class="dash-leads" style="border-top:0!important">
                        <div class="my-bord">
                        </div>
                        <div class="table" style="overflow-y: hidden">
                            
                            <table id="tbl_pre_approval" class="table table-striped table-bordered display dataTable dtr-inline">
                                <thead>
                                    <tr style="box-shadow:0 1px 3px 1px rgba(29, 77, 105, 0.14)">
                                        <th width="8%"><s:text name="lbl.pa.ref.no"/></th>
                                        <th width="8%"><s:text name="lbl.provider.name"/></th>
                                        <th width="8%"><s:text name="lbl.intimation.date"/></th>
                                        <th width="8%"><s:text name="lbl.service.type"/></th>
                                        <th width="8%"><s:text name="lbl.primary.diag"/></th>
                                        <th width="8%"><s:text name="lbl.est.amt"/></th>
                                        <th width="8%"><s:text name="lbl.appr.amt"/></th>
                                        <th width="8%"><s:text name="lbl.status"/></th>
                                    </tr>
                                </thead>
                                <tbody>	   
                                </tbody>
                            </table>
                        </div>
                    </div>
                </ul>
                <ul class="mem-name" id="div_enquiries">
                    <div class="dash-leads" style="border-top:0!important">
                        <div class="my-bord">
                        </div>
                        <div class="table responsive" style="overflow-y: hidden">
                            <table id="tbl_enquiries" class="table table-striped table-bordered display dataTable dtr-inline">
                                <thead>
                                    <tr style="box-shadow:0 1px 3px 1px rgba(29, 77, 105, 0.14)">
                                        <th width="8%"><s:text name="lbl.me.ref.id"/></th>
                                        <th width="8%"><s:text name="lbl.portal"/></th>
                                        <th width="8%"><s:text name="lbl.enquiry.type"/></th>
                                        <th width="8%"><s:text name="lbl.enquiry.details"/></th>
                                        <th width="8%"><s:text name="lbl.status"/></th>
                                    </tr>
                                </thead>
                                <tbody>	   
                                </tbody>
                            </table>
                        </div>
                    </div>
                </ul>
                <ul class="mem-name" id="div_visits">
                    <div class="dash-leads" style="border-top:0!important">
                        <div class="my-bord">
                        </div>
                        <div class="table responsive" style="overflow-y: hidden">
                            <table id="tbl_visits" class="table table-striped table-bordered display dataTable dtr-inline">
                                <thead>
                                    <tr style="box-shadow:0 1px 3px 1px rgba(29, 77, 105, 0.14)">
                                        <th width="8%"><s:text name="lbl.mv.visit.no"/></th>
                                        <th width="8%"><s:text name="lbl.provider.name"/></th>
                                        <th width="8%"><s:text name="lbl.visit.dt"/></th>
                                    </tr>
                                </thead>
                                <tbody>	   
                                </tbody>
                            </table>
                        </div>
                    </div>
                </ul>
                <ul class="mem-name" id="div_eclaims">
                    <div class="dash-leads" style="border-top:0!important">
                        <div class="my-bord">
                        </div>
                        <div class="col-md-12">
                            <table id="tbl_provider_eclaims" class="table table-striped table-bordered display dataTable dtr-inline">
                                <thead>
                                    <tr style="box-shadow:0 1px 3px 1px rgba(29, 77, 105, 0.14)">
                                        <th><s:text name="lbl.MS.ref.no"/></th>
                                        <th><s:text name="lbl.prov.ref.no"/></th>
                                        <th><s:text name="lbl.year"/></th>
                                        <th><s:text name="lbl.month"/></th>
                                        <th><s:text name="lbl.submission.date"/></th>
                                        <th><s:text name="lbl.status"/></th>
                                    </tr>
                                </thead>
                                <tbody>	   
                                </tbody>
                            </table>
                        </div>
                    </div>
                </ul>
                <ul class="mem-name" id="div_preApprovals">
                    <div class="dash-leads" style="border-top:0!important">
                        <div class="my-bord">
                        </div>
                        <div class="col-md-12">
                            <table id="tbl_provider_preapprovals" class="table table-striped table-bordered display dataTable dtr-inline">
                                <thead>
                                    <tr style="box-shadow:0 1px 3px 1px rgba(29, 77, 105, 0.14)">
                                        <th><s:text name="lbl.pa.ref.no"/></th>
                                        <th><s:text name="lbl.member.name"/></th>
                                        <th><s:text name="lbl.insured.name"/></th>
                                        <th><s:text name="lbl.invoice.date"/></th>
                                        <th><s:text name="lbl.status"/></th>
                                    </tr>
                                </thead>
                                <tbody>	   
                                </tbody>
                            </table>
                        </div>
                        <div class="col-sm-12 board-icons1 text-center" style="margin-left: 22px;">
                            <button class="view-btn" id="btn_pharmacy_pre_approval"><s:text name="btn.pharmacy.pre.approval"/></button>&nbsp;
                            <button class="view-btn" id="btn_general_pre_approval"><s:text name="btn.add.general.pre.approval"/></button>&nbsp;
                            <!--button class="view-btn" id="btn_pro_management" onclick="callProviderMgmt();">GO TO PROVIDER MANAGEMENT</button-->
                        </div>
                    </div>
                </ul>
                <ul class="mem-name" id="div_provider_enquiries">
                    <div class="dash-leads" style="border-top:0!important">
                        <div class="my-bord">
                        </div>
                        <div class="table responsive" style="overflow-y: hidden">
                            <table id="tbl_provider_enquiries" class="table table-striped table-bordered display dataTable dtr-inline">
                                <thead>
                                    <tr style="box-shadow:0 1px 3px 1px rgba(29, 77, 105, 0.14)">
                                        <th width="8%"><s:text name="lbl.me.ref.id"/></th>
                                        <th width="8%"><s:text name="lbl.portal"/></th>
                                        <th width="8%"><s:text name="lbl.enquiry.type"/></th>
                                        <th width="8%"><s:text name="lbl.enquiry.details"/></th>
                                        <th width="8%"><s:text name="lbl.status"/></th>
                                    </tr>
                                </thead>
                                <tbody>	   
                                </tbody>
                            </table>
                        </div>
                    </div>
                </ul>
            </div>
        </div>
        <div class="col-md-12 col-sm-12 col-xs-12 nopad">
            <div class="acti-off my-reminder">
                <div id="log" class="acti-off-heads">
                    <a href="javascript:void(0);" class="active" data-tab="activities"><s:text name="lbl.activities"/></a>
                    <a href="javascript:void(0);" class="" data-tab="task"><s:text name="lbl.task"/></a>
                    <%--s:if test='"M".equals(customer.sourceType)'>
                    <a href="javascript:void(0);" class="" data-tab="eligible">Chat History</a>
                    </s:if--%>
                </div>
                <ul class="act-name current" id="activities">
                    <div class="body" id="div_activities">
                    </div>
                    <div class="view_more" style="margin-top: 50px">
                        <button type="button" class="view-btn mbtn" id="btn_acti_more" title="Activities Details"> <s:text name="btn.more"/></button>
                    </div>
                </ul>   
                <ul class="act-name" id="task">
                    <table id="task_tbl" class="table" width="100%">
                        <thead>
                            <tr style="box-shadow:0 1px 3px 1px rgba(29, 77, 105, 0.14)">
                                <th class="text-center"><s:text name="lbl.id"/></th>
                                <th class="text-center"><s:text name="lbl.sub.category"/></th>
                                <th class="text-center"><s:text name="lbl.ref.no"/></th>
                                <th class="text-center"><s:text name="lbl.created.on"/></th>
                                <th class="text-center"><s:text name="lbl.remarks"/></th>
                                <th class="text-center"><s:text name="lbl.close.data"/></th>
                                <th class="text-center"><s:text name="lbl.closed.by"/></th>
                                <th class="text-center"><s:text name="lbl.action"/></th>
                            </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </ul>    
                <%--ul class="act-name" id="eligible">
                    <div class="col-sm-12" id="div_chats" style="height: 360px;overflow:  scroll;"></div--%>
                    <%--div class="view_more" style="margin-top: 50px">
                        <button type="button" class="view-btn mbtn" id="btn_chat_more" title="Activities Details"> More</button>
                    </div--%>
                <%--/ul--%>
            </div>
        </div>
    </div>
    <div class="feedback-form" id="notify" style="display:none;">
        <a href="javascript:void(0);" class="close-btn">X</a>
        <h3><s:text name="lbl.notification"/></h3>
        <div class="form-group">
            <s:form id="notifyform" name="notifyform" method="post" theme="simple">
                <textarea class="form-control" name="notifydata" id="notifydata"></textarea>
            </s:form>
        </div>
        <button class="save-btn btn btn-primary" onclick="sendNotification();"><s:text name="btn.send"/></button>
        <button type="button" class="close-btn btn"><s:text name="btn.cancel"/></button>
    </div> 
</div>

<div class="modal fade" id="preapproval_modal_dialog" data-backdrop="static" tabindex="-1" role="dialog" aria-hidden="false">
    <div class="modal-dialog  modal-mm">
        <div class="modal-content" style="height: 350px;">
            <div class="modal-header">
                <h4 class="modal-title"><s:text name="lbl.general.pre.approval.form"/></h4>
            </div>
            <div class="body" style="margin-top: 40px;">
                <div id="msg_provider" class="alert alert-danger" style="display: none;"></div>
                <s:form id="frm_general" name="frm_general" method="" theme="simple">

                    <div class="col-lg-5 col-md-5 col-sm-12 col-xs-12">
                        <label><s:text name="lbl.mem.id"/><span></span></label>
                        <div class="form-group">
                            <s:textfield id="preApproval_memberId"  cssClass="form-control"  required="true" title="Member Id"/>
                        </div>
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-12 col-xs-12">(<s:text name="lbl.or"/>)</div>
                    <div class="col-lg-5 col-md-5 col-sm-12 col-xs-12">
                        <label><s:text name="lbl.qatar.id"/><span></span></label>
                        <div class="form-group">
                            <s:textfield id="preApproval_qatarId"  cssClass="form-control"  required="true" title="%{getText('lbl.title.qatar.id')}"/>
                        </div>
                    </div>
                </s:form>
                <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12"style="margin-top: 23px">
                    <button class="save-btn btn btn-primary" id="" onclick="callGeneralApproval();"><s:text name="btn.get.me.approval"/></button>
                    <button class="close-btn btn" data-dismiss="modal" id="btn_approval_close"><s:text name="btn.close"/></button>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">

    var data = {"company": "${company}", "agentid": "${agentid}", "customer.mobileNo": "${customer.mobileNo}", "customer.civilId": "${customer.civilId}", "customer.emailId": "${customer.emailId}", "customer.name": "${customer.name}", "customer.mobileN01": "${customer.mobileNo1}", "customer.mobileNo2": "${customer.mobileNo2}", "customer.sourceType": "${customer.sourceType}"};
    $(document).ready(function () {
        $("#btn_search_provider").click(function () {
            var url = APP_CONFIG.context + "/QLMAppIntegration.do?operation=Y&flex1=ProviderSearch";
            var win = window.open(url, '_blank');
        });
        loadActivities();
        loadTask();
        //datatables for medical
        var transId = $("#policyNo").val();
        var memId = $("#hid_memId").val();
        var memSrNo = $("#hid_memSrNo").val();
    <s:if test='"M".equals(customer.sourceType)'>
        //loadChat();
        var tbl_claims = $("#tbl_claims").DataTable({
            paging: true,
            searching: true,
            ordering: true,
            info: false,
            //  "destroy": true,
            "lengthChange": false,
            "pageLength": 20,
            "responsive": true,
            autoWidth: false,
            "ajax": {
                "url": APP_CONFIG.context + "/loadMemberClaims.do?riskMedical.trmTransId=" + transId + "&riskMedical.trmMemId=" + memId,
                "type": "POST",
                "contentType": "json",
                "dataSrc": "aaData"
            },
            columns: [
                {data: "flex1",
                    render: function (data, type, row, meta) {
                        if (row.flex1 === null || row.flex1 === '' || row.flex1 === 'undefined') {
                            return "";
                        } else {
                            return '<span style="color:#0066FF;text-decoration:underline; cursor: pointer;" \n\
            onclick="loadClaimDetails(\'' + data + '\',\'' + row.flex8 + '\',\'' + row.flex9 + '\')">' + data + '</span>';
                        }
                    }
                },
                {data: "flex2"},
                {data: "flex3"},
                {data: "flex4"},
                {data: "flex5"},
                {data: "flex6"},
                {data: "flex7"}
            ],
            dom: 'lftrpi',
            tableTools: {
                "sRowSelect": "single",
                "aButtons": []
            },
            order: [[0, "desc"]],
            initComplete: function () {
                $("#tbl_claims tr td").css('cursor', 'default');
                //$('#datatable_search').html($('#tbl_claims_filter label input').attr('placeholder', 'Search..').removeAttr('type'));
            }
        });


        var tbl_pre_approval = $("#tbl_pre_approval").DataTable({
            paging: true,
            searching: true,
            ordering: true,
            info: true,
            lengthChange: false,
            autoWidth: false,
            pageLength: 5,
            responsive: true,
            destory: true,
            "ajax": {
                "url": APP_CONFIG.context + "/loadMemberPreApproval.do?riskMedical.trmTransId=" + transId + "&riskMedical.trmMemId=" + memId,
                "type": "POST",
                "contentType": "json",
                "dataSrc": "aaData"
            },
            columns: [
                {data: "flex1",
                    render: function (data, type, row, meta) {
                        if (row.flex1 === null || row.flex1 === '' || row.flex1 === 'undefined') {
                            return "";
                        } else {
                            return '<span style="color:#0066FF;text-decoration:underline; cursor: pointer;" \n\
            onclick="loadPreapprovalDetails(\'' + row.flex9 + '\',\'' + row.flex10 + '\')">' + data + '</span>';
                        }
                    }
                },
                {data: "flex2"},
                {data: "flex3"},
                {data: "flex4"},
                {data: "flex5"},
                {data: "flex6"},
                {data: "flex7"},
                {data: "flex8"}
            ],
            dom: 'lftrpi',
            tableTools: {
                "sRowSelect": "single",
                "aButtons": []
            },
            order: [[0, "desc"]],
            initComplete: function () {
                $("#tbl_pre_approval tr td").css('cursor', 'default');
                //$('#datatable_search1').html($('#tbl_pre_approval_filter label input').attr('placeholder', 'Search..').removeAttr('type'));
            }
        });

        var tbl_enquiries = $("#tbl_enquiries").DataTable({
            paging: true,
            searching: true,
            ordering: true,
            info: true,
            lengthChange: false,
            autoWidth: false,
            pageLength: 5,
            responsive: true,
            destory: true,
            "ajax": {
                "url": APP_CONFIG.context + "/loadMemberEnquiries.do?riskMedical.trmTransId=" + transId + "&riskMedical.trmMemId=" + memId,
                "type": "POST",
                "contentType": "json",
                "dataSrc": "aaData"
            },
            columns: [
                {data: "flex1",
                    render: function (data, type, row, meta) {
                        if (row.flex1 === null || row.flex1 === '' || row.flex1 === 'undefined') {
                            return "";
                        } else {
                            return '<span style="color:#0066FF;text-decoration:underline; cursor: pointer;" \n\
            onclick="loadEnquiryView(\'' + row.flex1 + '\')">' + data + '</span>';
                        }
                    }
                },
                {data: "flex2"},
                {data: "flex3"},
                {data: "flex4"},
                {data: "flex5"}

            ],
            dom: 'lftrpi',
            tableTools: {
                "sRowSelect": "single",
                "aButtons": []
            },
            order: [[0, "desc"]],
            initComplete: function () {
                $("#tbl_enquiries tr td").css('cursor', 'default');
                //$('#datatable_search2').html($('#tbl_enquiries_filter label input').attr('placeholder', 'Search..').removeAttr('type'));
            }
        });

        var tbl_visits = $("#tbl_visits").DataTable({
           paging: true,
            searching: true,
            ordering: true,
            info: true,
            lengthChange: false,
            autoWidth: false,
            pageLength: 5,
            responsive: true,
            destory: true,
            "ajax": {
                "url": APP_CONFIG.context + "/loadMemberVisits.do?riskMedical.trmTransId=" + transId + "&riskMedical.trmMemSrNo=" + memSrNo,
                "type": "POST",
                "contentType": "json",
                "dataSrc": "aaData"
            },
            columns: [
                {data: "flex1"},
                {data: "flex2"},
                {data: "flex3"}

            ],
            dom: 'lftrpi',
            tableTools: {
                "sRowSelect": "single",
                "aButtons": []
            },
            order: [[0, "desc"]],
            initComplete: function () {
                $("#tbl_visits tr td").css('cursor', 'default');
                //$('#datatable_search3').html($('#tbl_visits_filter label input').attr('placeholder', 'Search..').removeAttr('type'));
            }
        });
    </s:if>
        //Provider DataTables
        var tbl_provider_preapprovals = $("#tbl_provider_preapprovals").DataTable({
            paging: true,
            searching: true,
            ordering: true,
            info: true,
            lengthChange: false,
            autoWidth: false,
            pageLength: 5,
            responsive: true,
            destory: true,
            "ajax": {
                "url": APP_CONFIG.context + "/loadProviderPreApproval.do?customer.civilId=${customer.civilId}",
                "type": "POST",
                "contentType": "json",
                "dataSrc": "aaData"
            },
            columns: [
                {data: "flex1",
                    render: function (data, type, row, meta) {
                        if (row.flex1 === null || row.flex1 === '' || row.flex1 === 'undefined') {
                            return "";
                        } else {
                            return '<span style="color:#0066FF;text-decoration:underline; cursor: pointer;" \n\
            onclick="loadPreapprovalDetails(\'' + row.flex6 + '\',\'' + row.flex7 + '\')">' + data + '</span>';
                        }
                    }
                },
                {data: "flex2"},
                {data: "flex3"},
                {data: "flex4"},
                {data: "flex5"},
            ],
            dom: 'lftrpi',
            tableTools: {
                "sRowSelect": "single",
                "aButtons": []
            },
            order: [[0, "desc"]],
            initComplete: function () {
                $("#tbl_provider_preapprovals tr td").css('cursor', 'default');
                //$('#datatable_search4').html($('#tbl_provider_preapprovals_filter label input').attr('placeholder', 'Search..').removeAttr('type'));
            }
        });
        var tbl_provider_eclaims = $("#tbl_provider_eclaims").DataTable({
            paging: true,
            searching: true,
            ordering: true,
            info: true,
            lengthChange: false,
            autoWidth: false,
            pageLength: 5,
            responsive: true,
            destory: true,
            "ajax": {
                "url": APP_CONFIG.context + "/loadProviderEclaims.do?customer.civilId=${customer.civilId}",
                "type": "POST",
                "data": data,
                "contentType": "json",
                "dataSrc": "aaData"
            },
            columns: [
                {data: "flex1",
                    render: function (data, type, row, meta) {
                        if (row.flex1 === null || row.flex1 === '' || row.flex1 === 'undefined') {
                            return "";
                        } else {
                            return '<span style="color:#0066FF;text-decoration:underline; cursor: pointer;" \n\
            onclick="loadProviderClaims(\'' + data + '\')">' + data + '</span>';
                        }
                    }
                },
                {data: "flex2"},
                {data: "flex3"},
                {data: "flex4"},
                {data: "flex5"},
                {data: "flex6"},
            ],
            dom: 'lftrpi',
            tableTools: {
                "sRowSelect": "single",
                "aButtons": []
            },
            order: [[0, "desc"]],
            initComplete: function () {
                $("#tbl_provider_eclaims tr td").css('cursor', 'default');
                $('#datatable_search5').html($('#tbl_provider_eclaims_filter label input').attr('placeholder', 'Search..').removeAttr('type'));
            }
        });
         var tbl_provider_enquiries = $("#tbl_provider_enquiries").DataTable({
            paging: true,
            searching: true,
            ordering: true,
            info: true,
            lengthChange: false,
            autoWidth: false,
            pageLength: 5,
            responsive: true,
            destory: true,
            "ajax": {
                "url": APP_CONFIG.context + "/loadProviderEnquiries.do?customer.civilId=${customer.civilId}",
                "type": "POST",
                "contentType": "json",
                "dataSrc": "aaData"
            },
            columns: [
                {data: "flex1",
                    render: function (data, type, row, meta) {
                        if (row.flex1 === null || row.flex1 === '' || row.flex1 === 'undefined') {
                            return "";
                        } else {
                            return '<span style="color:#0066FF;text-decoration:underline; cursor: pointer;" \n\
            onclick="loadEnquiryView(\'' + row.flex1 + '\')">' + data + '</span>';
                        }
                    }
                },
                {data: "flex2"},
                {data: "flex3"},
                {data: "flex4"},
                {data: "flex5"}

            ],
            dom: 'lftrpi',
            tableTools: {
                "sRowSelect": "single",
                "aButtons": []
            },
            order: [[0, "desc"]],
            initComplete: function () {
                $("#tbl_provider_enquiries tr td").css('cursor', 'default');
                //$('#datatable_search6').html($('#tbl_provider_enquiries_filter label input').attr('placeholder', 'Search..').removeAttr('type'));
            }
        });




        //end 

        $('.mem-off-heads a').click(function () {
            var tab_id = $(this).attr('data-tab');

            $('.mem-off-heads a').removeClass('active');
            $('.mem-name').removeClass('current');

            $(this).addClass('active');
            $("#div_" + tab_id).addClass('current');
        });
        $('.acti-off-heads a').click(function () {
            var tab_id = $(this).attr('data-tab');

            $('.acti-off-heads a').removeClass('active');
            $('.act-name').removeClass('current');

            $(this).addClass('active');
            $("#" + tab_id).addClass('current');
        });

        $('[data-toggle="tooltip"]').tooltip();

        $("#btn_add_mclaim").on("click", function () {
            //window.open(APP_CONFIG.context + "/AnoudAppIntegration.do?flex1=Policy&flex2=CIVIL_ID&flex3=${customer.civilId}");
            var url = APP_CONFIG.context + "/QLMAppIntegration.do?operation=Y&flex1=AddClaim&flex2=MEM_ID&flex3=" + $("#hid_memId").val();
            var win = window.open(url, '_blank');

        });

        $("#btn_push_notification").on("click", function () {
            $('#notify').show();
        });
        $('.close-btn').click(function () {
            $('#notify').hide();
        });

        $("#search_member").on("click", function () {
            var data = $("#frm_policy").serialize();
            console.log(data);
        });
        $("#btn_search_provider").on("click", function () {
        });
        $("#btn_activ_more").on("click", function () {
        });
        $("#btn_chat_more").on("click", function () {
        });
        $("#btn_pharmacy_pre_approval").on("click", function () {
            $.ajax({
                type: "POST",
                url: APP_CONFIG.context +"/AddPharmacypreApproval.do?flex2="+$("#id_provCode").val(),
                success: function (result) {
                    $('#plugin_modal_dialog .modal-dialog').removeClass("modal-lg modal-lg").addClass("modal-mm");
                    $('#plugin_modal_dialog .modal-content').empty().html(result);
                    $('#plugin_modal_dialog').modal('show');
                },
                error: function (xhr, status, error) {
                    alert("Error: " + error);
                },
                complete: function () {
                
                }
            });
        });
        $("#btn_general_pre_approval").on("click", function () {
            $("#preapproval_modal_dialog").modal("show");

        });
       
        $("#btn_sms, #btn_mail, #btn_log, #btn_cust_edit, #btn_feedback, #btn_acti_more").on("click", function () {
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
                        if ($(_this).prop("id") === "btn_quote_Detaill" || $(_this).prop("id") === "btn_policy_Detaill" || $(_this).prop("id") === "btn_claim_Detaill" || $(_this).prop("id") === "btn_feedback" || $(_this).prop("id") === "btn_acti_more") {
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
    <s:if test='"M".equals(customer.sourceType)'>
        $("#member").trigger('change');
        $("#btn_policy").trigger("click");
    </s:if>
        $("#btn_provider_approvals").trigger("click");
    });
    function claimDetails() {
        var transId = $("#policyNo").val();
        var memId = $("#hid_memId").val();
        $("#div_claims").show();
        var claimsUrl = APP_CONFIG.context + "/loadMemberClaims.do?riskMedical.trmTransId=" + transId + "&riskMedical.trmMemId=" + memId;
        $("#tbl_claims").DataTable().ajax.url(claimsUrl).load();
        //reloadDt("tbl_claims");
        $("#div_policy").hide();
        $("#div_visits").hide();
        $("#div_enquiries").hide();
        $("#div_pre_approvals").hide();
    }
 function preApprovalDetails() {
        var transId = $("#policyNo").val();
        var memId = $("#hid_memId").val();
        var preapprovalsUrl = APP_CONFIG.context + "/loadMemberPreApproval.do?riskMedical.trmTransId=" + transId + "&riskMedical.trmMemId=" + memId;
        $("#tbl_pre_approval").DataTable().ajax.url(preapprovalsUrl).load();
        //reloadDt("tbl_pre_approval");
        $("#div_pre_approvals").show();
        $("#div_policy").hide();
        $("#div_visits").hide();
        $("#div_enquiries").hide();
        $("#div_claims").hide();
    }
    $("#btn_enquiries").on("click", function () {
        var transId = $("#policyNo").val();
        var memId = $("#hid_memId").val();
        var enquiriesUrl = APP_CONFIG.context + "/loadMemberEnquiries.do?riskMedical.trmTransId=" + transId + "&riskMedical.trmMemId=" + memId;
        $("#tbl_enquiries").DataTable().ajax.url(enquiriesUrl).load();
        //reloadDt("tbl_enquiries");
        $("#div_enquiries").show();
        $("#div_pre_approvals").hide();
        $("#div_policy").hide();
        $("#div_visits").hide();
        $("#div_claims").hide();
        $("#div_eclaims").hide();
    });
    $("#btn_views").on("click", function () {
        var transId = $("#policyNo").val();
        var memSrNo = $("#hid_memSrNo").val();
        var visitsUrl = APP_CONFIG.context + "/loadMemberVisits.do?riskMedical.trmTransId=" + transId + "&riskMedical.trmMemSrNo=" + memSrNo;
        $("#tbl_visits").DataTable().ajax.url(visitsUrl).load();
        //reloadDt("tbl_visits");
        $("#div_visits").show();
        $("#div_pre_approvals").hide();
        $("#div_policy").hide();
        $("#div_enquiries").hide();
        $("#div_claims").hide();

    });
    function preApprovalList() {
        var transId = $("#policyNo").val();
        var memId = $("#hid_memId").val();
        var providerUrl = APP_CONFIG.context + "/loadProviderPreApproval.do?customer.civilId=${customer.civilId}";
        $("#tbl_provider_preapprovals").DataTable().ajax.url(providerUrl).load();
        $("#div_preApprovals").show();
        $("#div_pre_approvals").hide();
        $("#div_policy").hide();
        $("#div_visits").hide();
        $("#div_enquiries").hide();
        $("#div_claims").hide();
        $("#div_eclaims").hide();
        $("#div_provider_enquiries").hide();

    }
    function EclaimsDetails() {
        var transId = $("#policyNo").val();
        var memId = $("#hid_memId").val();
        var eClaimsUrl = APP_CONFIG.context + "/loadProviderEclaims.do?customer.civilId=${customer.civilId}";
        $("#tbl_eclaims").DataTable().ajax.url(eClaimsUrl).load();
        $("#div_eclaims").show();
        $("#div_pre_approvals").hide();
        $("#div_policy").hide();
        $("#div_visits").hide();
        $("#div_enquiries").hide();
        $("#div_claims").hide();
        $("#div_preApprovals").hide();
        $("#div_provider_enquiries").hide();


    }
    function providerEnquiries() {
        var transId = $("#policyNo").val();
        var memId = $("#hid_memId").val();
        var eClaimsUrl = APP_CONFIG.context + "/loadProviderEnquiries.do?customer.civilId=${customer.civilId}";
        $("#tbl_eclaims").DataTable().ajax.url(eClaimsUrl).load();
        $("#div_provider_enquiries").show();
        $("#div_eclaims").hide();
        $("#div_pre_approvals").hide();
        $("#div_policy").hide();
        $("#div_visits").hide();
        $("#div_enquiries").hide();
        $("#div_claims").hide();
        $("#div_preApprovals").hide();
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
                            '<div class="list-group">' +
                            '<a href="javascript:void(0);" class="list-group-item">' +
                            '<span class="badge ' + o.info4 + '">' + o.value + '</span><h5 class="list-group-item-heading cstm-item-heading">' + o.info2 + '<span>' + o.info3 + '</span></h5>' +
                            '<p class="list-group-item-text">' + o.info1 + '</p>' +
                            '</a>' +
                            '</div>';
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
            }
        });
        $("#btn_policy").on("click", function () {
            $("#div_policy").show();
            $("#div_claims").hide();
        });
    }
     function claimsCount(memId, memSrNo) {
        var transId = $("#policyNo").val();
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/loadCountMedicalDashBoard.do",
            data: {"riskMedical.trmTransId": transId, "riskMedical.trmMemId": memId, "riskMedical.trmMemSrNo": memSrNo},
            async: true,
            success: function (result) {
                if (result.messageType === "S") {
                    if (result.keyValue.info1 == null || result.keyValue.info1 == "") {
                        result.keyValue.info1 === 0
                        $("#claims_count").text(result.keyValue.info1);
                    } else {
                        $("#claims_count").text(result.keyValue.info1);
                    }
                    if (result.keyValue.info2 == null || result.keyValue.info2 == "") {
                        result.keyValue.info2 === 0
                        $("#pre_approval_count").text(result.keyValue.info2);
                    } else {
                        $("#pre_approval_count").text(result.keyValue.info2);
                    }
                    if (result.keyValue.info3 == null || result.keyValue.info3 == "") {
                        result.keyValue.info3 === 0
                        $("#enquiries_count").text(result.keyValue.info3);
                    } else {
                        $("#enquiries_count").text(result.keyValue.info3);
                    }
                    if (result.keyValue.info4 == null || result.keyValue.info4 == "") {
                        result.keyValue.info4 === 0
                        $("#visits_count").text(result.keyValue.info4);
                    } else {
                        $("#visits_count").text(result.keyValue.info4);
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
    }

    function loadChat() {
        block('block_body');
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/loadMemberChat.do",
            data: data,
            async: true,
            success: function (result) {
                var data = "";
                $("#div_chat").html("");
                var logs = "";
                $.each(result.aaData, function (i, o) {
                    if (o.info2 === null) {
                        o.info2 = "";
                    }

                    if (o.value === 'E') {

                        logs = '<span class="right">' + o.info1 + '<br><font style="font-size:x-small;">' + o.key + '</font></span>' +
                                '<div class="clear"></div>'
                    } else {
                        var str = o.info1;
                        
                        if(o.info === 'I'){
                             var compcode = "<s:property value="company" />";
                             var urlString = "<%=request.getContextPath()%>/ShowDmsImageServlet?isThumbNail=N&docCode=11&docName="+o.info1+"&module="+compcode+"&project=<%= ApplicationConstants.DMS_PROJECT_MED %>&para1=MOBILE-CHAT&para2=${customer.civilId}";
                            str = '<a class="image-popup-fit-width"  href='+urlString+ '>\n\
                                    <img src=\"'+urlString+'\" width=\"150px\" height=\"30px\"/>' +
                                '</a>' 
                       
                        logs = '<span class="left">'+ str +'<br><font style="font-size:x-small;">'+ o.key+'</font></span>' +
                                '<div class="clear"></div>'
                             }
                        else if (o.info === 'V') {
                            var str = o.info1;
                            var compcode = "<s:property value="company" />";
                            str = '<object type="application/x-shockwave-flash" data="<%=request.getContextPath()%>/swf/player_mp3.swf" width="200" height="20">' +
                                    '<param name="movie" value="<%=request.getContextPath()%>/swf/player_mp3.swf" />' +
                                    '<param name="bgcolor" value="#F44336" />' +
                                    '<param name="FlashVars" value="mp3=<%=request.getContextPath()%>/ShowDmsImageServlet?isThumbNail=N|docCode=11|docName='+o.info1+'|module='+compcode+'|project=<%= ApplicationConstants.DMS_PROJECT_MED%>|para1=MOBILE-CHAT|para2=<s:property escapeJavaScript="true" value="customer.civilId"/>&amp;showstop=0" />' +
                                    '</object>'
                            logs = '<span class="left">' + str + '<br><font style="font-size:x-small;">' + o.key + '</font></span>' +
                                    '<div class="clear"></div>'
                        } else {
                            var str = o.info1;
                            logs =
                                    '<span class="left">' + str + '<br><font style="font-size:x-small;">' + o.key + '</font></span>' +
                                    '<div class="clear"></div>'
                            /*'<div class="list-group">' +
                             '<a href="javascript:void(0);" class="list-group-item" style="width: 50%;margin-bottom: 7px;right:0px;background-color: #e6eeff;">' +
                             '<span class="badge bg-green ' + o.key + '">' + o.value + '</span><h5 class="list-group-item-heading cstm-item-heading">' + o.key + '<span>' + o.info1+ '</span></h5>' +
                             '<p class="list-group-item-text">' + o.info + '</p>' +
                             '</a>' +
                             '</div>';*/
                        }
                    }
                    data = $("#div_chats").append(logs);
                });
                if (data === "") {
                    $("#div_chats").append('<li>No Data Available</li>');
                }

            },
            error: function (xhr, status, error) {
                alert("Error: " + error);
            },
            complete: function () {
                unblock('block_body');
                $('.image-popup-fit-width').magnificPopup({
		type: 'image',
		closeOnContentClick: true,
		image: {
			verticalFit: false
                    }
                    });
            }
            
        });
    }

    function loadMemberDetails(_this, memSrNo) {
        $('#btn_policy').trigger("click");
        var memId = $(_this).find(':selected').attr('title');
        //  block('div_policy');
        var transId = $("#policyNo").val();
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/loadMemberData.do",
            data: {"riskMedical.trmTransId": transId, "riskMedical.trmMemSrNo": memSrNo},
            success: function (result) {
                if (result.riskMedical != null && result.riskMedical != undefined) {
                    $("#trm_ins_name").html(result.riskMedical.trmCustName);
                    console.log("Parent Name :: " + result.riskMedical.trmParentName);
                    $("#trm_mem_name").html(result.riskMedical.trmParentName === undefined ? "" : result.riskMedical.trmParentName);
                    $("#trm_mem_id").html(result.riskMedical.trmMemId);
                    $("#trm_emp_no").html(result.riskMedical.trmEmpNo);
                    $("#trm_entry_dt").html(moment(result.riskMedical.trmEntryDate, "YYYY-MM-DDTHH:mm:ss").format('DD/MM/YYYY'));
                    $("#trm_exit_dt").html(moment(result.riskMedical.trmExitDate, "YYYY-MM-DDTHH:mm:ss").format('DD/MM/YYYY'));
                    $("#trm_plan").html(result.riskMedical.trmPlan);
                    $("#trm_sub_plan").html(result.riskMedical.trmSubPlan);
                    if (result.riskMedical.trmStatus !== null && result.riskMedical.trmStatus !== "" && result.riskMedical.trmStatus == "Active") {
                        $("#trm_status").html(result.riskMedical.trmStatus);
                        $("#trmStatus").removeClass('prioritya').addClass('priority');
                    } else if (result.riskMedical.trmStatus !== null && result.riskMedical.trmStatus !== "" && result.riskMedical.trmStatus == "In Active") {
                        $("#trm_status").html(result.riskMedical.trmStatus);
                        $("#trmStatus").removeClass('priority').addClass('prioritya');
                    }

                    $("#trm_vip").html(result.riskMedical.trmVipYn);
                    $("#id_mobile_portal").html(result.riskMedical.trmPortalYn);
                    $("#id_mobile_app").html(result.riskMedical.trmMobileAppYn);
                    if (result.riskMedical.trmMobileAppYn == "Yes") {
                        $("#btn_push_notification").show();
                    } else {
                        $("#btn_push_notification").hide();    
                    }
                    if (result.riskMedical.trmVipYn === "1") {
                        $("#trmVipYn").html('<p style="margin-top: 7px;">VIP</span></p>').show();
                    } else if (result.riskMedical.trmVipYn === "2") {
                        $("#trmVipYn").html('<p style="margin-top: 7px;">VVIP</span></p>').show();
                    } else {
                        $("#trmVipYn").empty().hide();
                    }
                    //var memId = result.riskMedical.trmMemId;
                    claimsCount(memId, memSrNo);
                    $("#hid_memId").val(memId);
                    $("#hid_memSrNo").val(memSrNo);
                }
            },
            error: function (xhr, status, error) {
                alert("Error: " + error);
            },
            complete: function () {

                //  unblock('div_policy');
            }
        });
    }
    function loadMemberList(_this, transId, id) {
        var memSrNo = $(_this).find(':selected').attr('title');
        //console.log(transId+":"+memSrNo);
        block('div_policy');
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/loadMemberList.do",
            data: {"riskMedical.trmTransId": transId, "riskMedical.trmMemSrNo": memSrNo},
            success: function (result) {
                var opt = '<option value="">--Select-</option>';
                $.each(result.aaData, function (i, v) {
                    opt += "<option value=\"" + v.trmMemSrNo + "\" title=\"" + v.trmMemId + "\">" + v.trmMemberName + "</option>";
                });
                $('#' + id).html(opt);
            },
            error: function (xhr, status, error) {
                alert("Error: " + error);
            },
            complete: function () {

                unblock('div_policy');
            }
        });
    }
    function policyDetails() {
        $("#div_policy").show();
        $("#div_claims").hide();
        $("#div_visits").hide();
        $("#div_enquiries").hide();
        $("#div_pre_approvals").hide();
    }
    function loadMedMemberDetails() {
        var url = APP_CONFIG.context + "/QLMAppIntegration.do?operation=Y&flex1=Member&flex2=MEM_ID&flex3=" + $("#hid_memId").val();
        var win = window.open(url, '_blank');
    }
    function loadClaimDetails(mbRefNo, mbTransId, mbStatus) {
        var url = APP_CONFIG.context + "/QLMAppIntegration.do?operation=Y&flex1=MasterBatch&flex2=" + mbRefNo + "&flex3=" + mbTransId + "&flex4=" + mbStatus;
        var win = window.open(url, '_blank');
    }
    function loadPreapprovalDetails(transId, tranSrNo) {
        var url = APP_CONFIG.context + "/QLMAppIntegration.do?operation=Y&flex1=PreApproval&flex2=" + transId + "&flex3=" + tranSrNo;
        var win = window.open(url, '_blank');
    }
    function loadEnquiryView(refId) {
        var url = APP_CONFIG.context + "/QLMAppIntegration.do?operation=Y&flex1=Enquiry&flex2=" + refId;
        var win = window.open(url, '_blank');
    }
    function callProviderMgmt() {
        var url = APP_CONFIG.context + "/QLMAppIntegration.do?operation=Y&flex1=ProviderManagement&flex2=" + $("#id_provCode").val();
        var win = window.open(url, '_blank');
    }
    function loadProviderClaims(mbRefNo) {
        var url = APP_CONFIG.context + "/QLMAppIntegration.do?operation=Y&flex1=Eclaims&flex2=" + mbRefNo;
        var win = window.open(url, '_blank');
    }
    function sendNotification() {
        var text = $("#notifydata").val();
        console.log(text);
        //return false;
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/saveMemberNotification.do?customer.civilId=${customer.civilId}+&flex2=" + text,
            success: function (result) {
                $('#notify').hide();
                $.notify("Notification Send Successfully", "custom");

            },
            error: function (xhr, status, error) {
                alert("Error: " + error);
            },
            complete: function () {
            }
        });

    }
    
    function callGeneralApproval() {
        if (($("#preApproval_memberId").val() == null || $("#preApproval_memberId").val()) == "" && ($("#preApproval_qatarId").val() == "" || $("#preApproval_qatarId").val() == null)) {
            $("#msg_provider").html("Enter Qatar Id Or Member Id").show();
            return false;
        }
        var url = APP_CONFIG.context + "/QLMAppIntegration.do?operation=Y&flex1=ProviderPreApproval&memberId=" + $("#preApproval_memberId").val() + "&qatarId=" + $("#preApproval_qatarId").val() + "&flex2=" + $("#id_provCode").val();
        window.open(url, '_blank');
        $("#preapproval_modal_dialog").modal("hide");
    }
    function callCustomer(mobileNo) {
        <s:if test='%{!"N".equals(#session.USER_INFO.ameyoChannel) && #session.USER_INFO.agentType == 1}'>
            if(window.opener) {
                window.opener.doDial(mobileNo);
            } else {
                doDial(mobileNo);
            }
        </s:if>
    }
    
    function loadTask() {
        $("#task_tbl").DataTable({
            paging: true,
            searching: false,
            responsive: true,
            ordering: true,
            info: true,
            lengthChange: false,
            pageLength: 10,
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
                {data: "ctCrDt",
                    render: function (data, type, row, meta) {
                        if (data && type === "sort") {
                            data = moment(data, "YYYY-MM-DDTHH:mm:ss").format('YYYYMMDDHHmmss');
                        } else {
                            data = moment(data, "YYYY-MM-DDTHH:mm:ss").format('DD/MM/YYYY');
                        }
                            return data;
                    }
                },
                {data: "ctCloseRemarks", orderable: false,
                    render: function (data, type, row, meta) {
                        return '<div style="white-space: pre-wrap;">' + (data == null ? '' : data) + '</div>';
                    }
                },
                {data: "ctCloseDate",
                    render: function (data, type, row, meta) {
                        if (data === null) {
                            return "";
                        } else if (data && type === "sort") {
                            data = moment(data, "YYYY-MM-DDTHH:mm:ss").format('YYYYMMDDHHmmss');
                        } else {
                            data = moment(data, "YYYY-MM-DDTHH:mm:ss").format('DD/MM/YYYY');
                        }
                            return data;
                    }
                },
                {data: "ctAssignedToDesc"},
                {data: "ctId",
                    render: function(data, type, row, meta) {
                        return '<center><i class="fa fa-eye" title="View Message" data-toggle="tooltip" style="cursor:pointer;color: #418bca;" onclick="viewTasks(' + row.ctId + ');"></i></center>';                        
                    }
                }
            ],
            order: [4, 'desc'],
            dom: 'lBftrpi',
            buttons: [                
                {
                    text: '<i class="fa fa-plus"></i> Add',
                    className: 'btn btn-warning mb-15',
                    action: function(e, dt, node, config) {
                        block('block_body');
                        $.ajax({
                            type: "POST",
                            url: APP_CONFIG.context + "/openTaskEntryForm.do?operation=add",
                            data: {},
                            success: function(result) {
                                $('#popup_custom').html(result);
                                $('.popup-wrap').addClass('popup-open');
                                $('#ctRefId').prop('readonly',true).val('<s:property value="customer.civilId"/>');
                                $('#overlay').show();
                            },
                            error: function(xhr, status, error) {
                                alert("Error: " + error);
                            },
                            complete: function() {
                                unblock('block_body');
                            }
                        });
                    }
                }               
            ],
            initComplete: function () {
            }
        });
    }
</script>