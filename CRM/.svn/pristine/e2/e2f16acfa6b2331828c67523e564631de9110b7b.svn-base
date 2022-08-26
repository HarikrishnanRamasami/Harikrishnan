<%-- 
    Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
    * All Rights Reserved
    * Unauthorized copying of this file, via any medium is strictly prohibited

    Document   : customerForm
    Created on : Feb 28, 2017, 6:03:50 PM
    Author     : ravindar.singh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<head>
    <link href="<%=request.getContextPath()%>/plugins/innovate/css/datepicker.css" rel="stylesheet" />
</head>
<a href="#" class="close-link">&#10006;</a>

    <div class="popup-title">
        <h3><s:text name="lbl.customer.form"/></h3>
        <div style="float:right;" class="">
            <button class="save-btn btn btn-primary" id="btn_custome_save">&#10004;<s:text name="btn.save"/></button>
            <button class="close-btn btn">&#10006;<s:text name="btn.close"/></button>
        </div><hr>
    </div>
        <div id="msg_customer" class="alert alert-danger" style="display: none;"></div>
    <!--                                         <div class="sub-header"><h4>General</h4></div>-->
    <s:form id="frm_customer" name="frm_customer" method="post" theme="simple">
        <s:hidden name="company" id="company" />
        <s:hidden name="operation" id="operation" />        
        <div class="form-fields clearfix">
            <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <s:textfield name="customer.name" id="name" cssClass="textbox" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.enter.name')}"></s:textfield>
                <label class="textboxlabel"><s:text name="lbl.name"/><span>*</span></label>
            </div>
            <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <s:textfield name="customer.civilId" cssClass="textbox" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.please.enter.civilid')}" maxlength="11" disabled='"edit".equals(operation)'/>
                <s:if test='"edit".equals(operation)'>
                    <s:hidden name="customer.civilId"/>
                </s:if>
                <label class="textboxlabel"><s:text name="lbl.civil.id"/><span>*</span></label>
            </div>
        </div> 
        <div class="form-fields clearfix">
            <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <s:textfield id="PoBox" name="customer.poBox" maxlength="10" cssClass="textbox" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.post.box')}"></s:textfield>
                <label class="textboxlabel"><s:text name="lbl.po.box"/></label>
            </div>
            <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <s:select name="customer.country"  id="country" list="countryList" listKey="key" listValue="value" headerKey="" headerValue="-- Select --" cssClass="textbox" data-toggle="tooltip" data-placement="top" title="Country" onchange="loadDropDown(this.value, 'city');"/>

                <label class="textboxlabel"><s:text name="lbl.country"/></label>
            </div>
        </div><div class="form-fields clearfix">
            <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <s:select name="customer.nationality" id="nationlity" list="nationalityList" listKey="key" listValue="value" headerKey="" headerValue="-- Select --" cssClass="textbox" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.nationality')}"/>
                <label class="textboxlabel"><s:text name="lbl.nationality"/></label>
            </div>
            <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <s:select name="customer.city"  cssClass="textbox" id="city" list="cityList" listKey="key" listValue="value" headerKey="" headerValue="-- Select --" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.city')}"/>

                <label class="textboxlabel"><s:text name="lbl.city"/></label>
            </div>
        </div><div class="form-fields clearfix">
            <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <s:select cssClass="textbox" id="gender" name="customer.gender" list="genderList" listKey="key" listValue="value" headerKey="" headerValue="-- Select --" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.gender')}"/>
                <label class="textboxlabel"><s:text name="lbl.gender"/></label>
            </div>
            <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <s:textfield id="age" name="customer.age" maxlength="3" cssClass="textbox" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.age')}"></s:textfield>
                <label class="textboxlabel"><s:text name="lbl.age"/></label>
             </div>
            </div>
            <div class="form-fields clearfix">
                <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                    <s:select cssClass="textbox" id="occupation" name="customer.occupation" list="occupationList" listKey="key" listValue="value" headerKey="" headerValue="-- Select --" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.occupation')}"/>
                    <label class="textboxlabel"><s:text name="lbl.occupation"/></label>
                </div>
                <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                    <s:textfield id="workPlace" name="customer.workPlace" cssClass="textbox" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.work.place')}"></s:textfield>
                    <label class="textboxlabel"><s:text name="lbl.workplace"/></label>
                </div>
            </div>
            <div class="form-fields clearfix">
                <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                    <s:select name="customer.custType" cssClass="textbox" id="custType" list="custTypeList" listKey="key" listValue="value" headerKey="" headerValue="-- Select --" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.client.type')}"/>
                    <label class="textboxlabel"><s:text name="lbl.client.type"/></label>
                </div>
                <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                    <s:select name="customer.source" id="source" list="sourceList" listKey="info1" listValue="value" headerKey="" cssClass="form-control textbox"  headerValue="-- Select --" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.source')}"/>
                    <label class="textboxlabel"><s:text name="lbl.source"/><span>*</span></label>
                </div>
            </div>
             <div class="form-fields clearfix">
                <div class="form-group col-sm-6 col-md-6 col-xs-12 spac" id="div_sourceDetails">
                    <s:textfield id="sourceDetails" name="customer.sourceDetails" maxlength="200"  cssClass="textbox" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.source.details')}"></s:textfield>
                    <label class="textboxlabel"><s:text name="lbl.source.details"/></label>
                </div>
                <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                    <s:textfield id="mobileNo" name="customer.mobileNo" maxlength="10"  cssClass="textbox" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.mobile.no')}"></s:textfield>

                        <label class="textboxlabel"><s:text name="lbl.mobile"/><span>*</span></label>
                </div>
            </div> 
            <div class="form-fields clearfix">
                <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                    <s:textfield name="customer.mobileNo1" id="mobileNo1" cssClass="textbox" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.mobileno.optiona11')}"></s:textfield>
                    <label class="textboxlabel"><s:text name="lbl.mobileno.optiona11"/></label>
                </div>
                <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                    <s:textfield name="customer.mobileNo2" id="mobileNo2" cssClass="textbox" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.mobileno.optiona12')}"></s:textfield>
                    <label class="textboxlabel"><s:text name="lbl.mobileno.optiona12"/></label>
                </div>
            </div>
           <div class="form-fields clearfix">
                <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                    <s:textfield name="customer.telNo" id="datepicker3"  cssClass="textbox" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.telephone.no')}" />
                    <label class="textboxlabel"><s:text name="lbl.tel.no"/></label>
                </div>
                <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                    <s:textfield id="emailId" name="customer.emailId" maxlength="75" cssClass="textbox" email="true" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.email.id')}"></s:textfield>
                    <label class="textboxlabel"><s:text name="lbl.email.id"/></label>
                </div>
            </div>        
            <div class="form-fields clearfix">
                <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                    <s:textfield id="faxNo" name="customer.faxNo" maxlength="12" cssClass="textbox" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.fax.no')}"></s:textfield>
                    <label class="textboxlabel"><s:text name="lbl.fax.no"/></label>
                </div>
                <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                    <s:textfield name="customer.birthDt"  id="datepicker"  cssClass="textbox datepicker calicon"  placeholder="DD/MM/YYYY" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.birth.date')}" />
                    <label class="textboxlabel"><s:text name="lbl.birth.date"/></label>
                </div>
            </div>
            <div class="form-fields clearfix">
                <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                    <s:textfield name="customer.idExpDt" id="datepicker2"  cssClass="textbox datepicker calicon " data-toggle="tooltip" data-placement="top" title="%{getText('lbl.expiry.date')}" placeholder="DD/MM/YYYY" />
                    <label class="textboxlabel"><s:text name="lbl.id.expiry.date"/></label>
                </div>
                <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                    <s:textfield name="customer.weddingDt" id="datepicker3"  cssClass="textbox datepicker calicon "  placeholder="DD/MM/YYYY" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.wedding.date')}" />
                    <label class="textboxlabel"><s:text name="lbl.wedding.date"/></label>
                </div>
            </div>
            <div class="form-fields clearfix">
                <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                    <s:textfield name="customer.licenseExpDt" id="datepicker4" cssClass="textbox datepicker calicon" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.license.expiry.date')}" placeholder="DD/MM/YYYY" />
                    <label class="textboxlabel"><s:text name="lbl.license.exp.date"/></label>
                </div>
            </div>
    </s:form>
<script type="text/javascript">

    $(function () {

        //  $('#overflow').slimScroll();

        $('#sidebar').slimScroll({
            height: '100%',
            width: '100%',
        });
        $('.leads-tab').click(function () {
            $('.popup-wrap').addClass('popup-open');
            $('#overlay').show();

        });
        $('.close-link').click(function () {
            $('.popup-wrap').removeClass('popup-open');
            $('#overlay').hide();
        });
        $('.close-btn').click(function () {
            $('.popup-wrap').removeClass('popup-open');
            $('#overlay').hide();
        });


    });

</script>     
<script type="text/javascript">
    $(function () {
        $(".datepicker").datepicker({format: 'dd/mm/yyyy'});
    });
    /*  $('#birthDt').bootstrapMaterialDatePicker({
     format: 'DD/MM/YYYY',
     clearButton: true,
     weekStart: 1,
     time: false
     });
     $('#idExpDt').bootstrapMaterialDatePicker({
     format: 'DD/MM/YYYY',
     clearButton: true,
     weekStart: 1,
     time: false
     });
     $('#weddingDt').bootstrapMaterialDatePicker({
     format: 'DD/MM/YYYY',
     clearButton: true,
     weekStart: 1,
     time: false
     });
     $('#licenseExpDt').bootstrapMaterialDatePicker({
     format: 'DD/MM/YYYY',
     clearButton: true,
     weekStart: 1,
     time: false
     });*/
    function loadDropDown(val, id) {
        $('#city').html("");
        $('#city').append('<option value="">--Select-</option>')
        $.ajax({
            type: "POST",
            url: "<%=request.getContextPath()%>/GetCityList.do",
            data: {"customer.country": val, "company": $("#company").val()},
            success: function (data) {
                $.each(data.cityList, function (i, d) {
                    $('#' + id).append('<option value="' + d.key + '">' + d.value + '</option>');
                });
                $('#' + id).selectpicker('refresh');
            }
        });
    }
    <s:if test='"view".equals(operation)'>
    $("#frm_customer").find('input, textarea, button, select').attr('disabled', 'disabled');
    </s:if>

    $(document).ready(function () {
        //  $('[data-toggle="tooltip"]').tooltip();

        $('#frm_customer').validate({
            onfocusout: false,
            onkeyup: false,
            onclick: false,
            wrapper: 'li',
            rules: {
                "customer.name": {
                    required: true
                },
                "customer.civilId": {
                    required: true
                },
                "customer.source": {
                    required: true
                },
                "customer.mobileNo": {
                    required: true,
                    digits: true,
                    minlength: 8,
                    maxlength: 10
                },
                "customer.emailId": {
                    email: true,
                    maxlength: 75
                },
            },
            messages: {
                "customer.name": {
                    required: "Please Enter Name"
                },
                "customer.civilId": {
                    required: "Please Enter CivilId"
                },
                "customer.source": {
                    required: "Please Select Source"
                },
                "customer.mobileNo": {
                    required: "Please Enter MobileNo"
                },
                "customer.emailId": {
                    required: "Please Enter EmailId"
                },
            },
            highlight: function (element) {
                $(element).removeClass('error');
            },
            showErrors: function (errorMap, errorList) {
                if (errorList.length)
                {
                    var s = errorList.shift();
                    var n = [];
                    n.push(s);
                    this.errorList = n;
                }
                if (this.numberOfInvalids())
                    this.defaultShowErrors();
                else
                    $("#msg_customer").hide();
            },
            errorPlacement: function (error, element) {
                $("#msg_customer").html(error);
            },
            invalidHandler: function (form, validator) {
                var errors = validator.numberOfInvalids();
                if (errors)
                    $("#msg_customer").show();
                else
                    $("#msg_customer").hide();
            }
        });

        $("#frm_customer #source").on("change", function () {
            if ($(this).val() === 'O') {
                $("#div_sourceDetails").show();
            } else {
                $("#div_sourceDetails").hide();
            }
        });

        $("#btn_custome_save").on("click", function () {
            if (!$('#frm_customer').valid()) {
                return false;
            }
            var _this = $(this);
            // $(_this).attr("disabled", true);
            block('block_body');
            $.ajax({
                type: "POST",
                url: APP_CONFIG.context + "/saveCustomerEntryForm.do",
                data: $("#frm_customer").serialize(),
                success: function (result) {
                    if (result.messageType === "S") {
                    $.notify("Record saved successfully", "custom");
                    // $("#msg_customer").removeClass("alert-danger").addClass("alert-success").html("Successfully Updated").show();
                    // $('#btn_custome_save').attr('disabled', true);
                    //$('#plugin_modal_dialog').modal('hide');
                    $('.popup-wrap').removeClass('popup-open');
                    $('#overlay').hide();
                    $("#frm_customer").attr("action", "<%=request.getContextPath()%>/customer360.do?civilid=${customer.civilId}&search=view&randId=" + Math.random());
                    $("#frm_customer").submit()
                    //  $.notify("Customer details saved successfully", "custom");
                    }else {
                        //$.notify("Customer Already Added", "custom");
                        $("#msg_customer").removeClass("alert-success").addClass("alert-danger").html("Already Civil Id Mapped With Another Customer").show();
                    }
                },
                error: function (xhr, status, error) {
                    alert("Error: " + error);
                },
                complete: function () {
                    unblock('block_body');
                    // $(_this).removeAttr("disabled");
                }
            });
        });
    });

</Script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/admin.js"></script>