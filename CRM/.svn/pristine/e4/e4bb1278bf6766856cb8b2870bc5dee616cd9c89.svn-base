<%-- 
    Document   : leadsForm
    Created on : 20 Mar, 2017, 3:53:36 PM
    Author     : haridass.a
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
    <a href="#" class="close-link">&#10006;</a>
    <div class="task-form">
        <div class="popup-title">
            <h3><s:text name="lbl.leads.head.lead.form"/></h3>
            <div style="float:right;" class="">
                <button class="save-btn btn btn-primary" id="btn_lead_save">&#10004;<s:text name="btn.save"/></button>
                <button class="close-btn btn">&#10006;<s:text name="btn.close"/></button>
            </div><hr>
        </div>
                <div id="msg_lead" class="alert alert-danger" style="display: none;"></div>
         <s:form id="frm_lead" name="frm_lead" method="post" theme="simple">
            <s:hidden name="operation" id="operation" />
            <s:hidden name="leads.clId"/>
        <div class="form-fields clearfix">
            <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <s:textfield name="leads.clRefId" id="clRefId" cssClass="textbox" title="%{getText('lbl.common.refid')}"/>
                <label class="textboxlabel"><s:text name="lbl.common.refid"/></label>
            </div>
            <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <s:textfield name="leads.clRefNo" id="clRefNo" cssClass="textbox"  title="%{getText('lbl.ref.no')}"/>
                <label class="textboxlabel"><s:text name="lbl.ref.no"/></label>
            </div>
        </div>
        <div class="form-fields clearfix">
            <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <s:textfield name="leads.clName" id="clName" cssClass="textbox" title="Enter Name"/>
                <label class="textboxlabel"><s:text name="lbl.common.name"/></label>
            </div>
            <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <s:select name="leads.clGender" id="clGender" list="genderList" listKey="key" listValue="value" headerKey="" headerValue="-- Select --" cssClass="textbox" title="%{getText('lbl.common.select.gender')}"/>
                <label class="textboxlabel"><s:text name="lbl.gender"/><span>*</span></label>
            </div>
        </div>
        <div class="form-fields clearfix">
            <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <s:textfield name="leads.clBirthDt" id="datepicker" cssClass="textbox calicon"  placeholder="DD/MM/YYYY" title="%{getText('lbl.common.dob')}"/>
                <label class="textboxlabel"><s:text name="lbl.birth.date"/></label>
            </div>
                <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <s:textfield name="leads.clTelNo" id="clTelNo" maxlength="12" cssClass="form-control numbers textbox" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.telephone.no')}"/>                
                <label class="textboxlabel"><s:text name="lbl.tel.no"/></label>
            </div>
        </div>
        <div class="form-fields clearfix">
            <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <s:textfield name="leads.clPoBox" id="clPoBox" maxlength="10" cssClass="form-control numbers textbox" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.post.box')}"/>
                <label class="textboxlabel"><s:text name="lbl.post.box"/></label>
            </div>
            <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <s:select name="leads.clCountry" id="clCountry" list="countryList" listKey="key" listValue="value" headerKey="" headerValue="-- Select --" cssClass="textbox" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.country')}" onchange="loadCityDropDown(this.value, 'clCity');"/>
                <label class="textboxlabel"><s:text name="lbl.country"/></label>
            </div>
        </div>
        <div class="form-fields clearfix">
            <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <s:select name="leads.clCity" id="clCity" list="cityList" listKey="key" listValue="value" headerKey="" headerValue="-- Select --" cssClass="textbox" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.city')}"/>
                <label class="textboxlabel"><s:text name="lbl.city"/></label>
            </div>
              <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <s:select name="leads.clNationality" id="clNationality" list="nationalityList" listKey="key" listValue="value" headerKey="" headerValue="-- Select --" cssClass="textbox" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.nationality')}"/>
                <label class="textboxlabel"><s:text name="lbl.nationality"/></label>
            </div>
        </div>
        <div class="form-fields clearfix">
            <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <s:textfield name="leads.clMobileNo" id="clMobileNo" maxlength="10" cssClass="form-control numbers textbox" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.common.enter.mobno')}"/>
                <label class="textboxlabel"><s:text name="lbl.mobile.no"/><span>*</span></label>
            </div>
            <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <s:textfield name="leads.clFaxNo" id="clFaxNo" maxlength="10" cssClass="form-control numbers textbox" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.common.enter.faxno')}"/>
                <label class="textboxlabel"><s:text name="lbl.fax.no"/></label>
            </div>
        </div>
        <div class="form-fields clearfix">
            <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <s:textfield name="leads.clEmailId" id="clEmailId" maxlength="75" cssClass="textbox" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.email.id')}"/>
                <label class="textboxlabel"><s:text name="lbl.email.id"/></label>
            </div>
            <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                 <s:select name="leads.clWorkPlace" list="categoryList" listKey="key" listValue="value" cssClass="textbox" id="clWorkPlace" headerKey="" headerValue="--Select--"/>
                 <label class="textboxlabel"><s:text name="lbl.data.source"/><span>*</span></label>
            </div>
        </div>
        <div class="form-fields clearfix">
            <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <s:select name="leads.clOccupation" id="clOccupation" list="occupationList" listKey="key" listValue="value" headerKey="" headerValue="-- Select --" cssClass="textbox"  data-toggle="tooltip" data-placement="top" title="%{getText('lbl.occupation')}"/>
                <label class="textboxlabel"><s:text name="lbl.occupation"/></label>
            </div>
            <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <s:select name="leads.clSource" id="clSource" list="sourceList" listKey="key" listValue="value" headerKey="" headerValue="-- Select --" cssClass="textbox" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.source')}"/>
                <label class="textboxlabel"><s:text name="lbl.source"/><span>*</span></label>
            </div>
        </div>
        <div class="form-fields clearfix">
            <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <s:textfield name="leads.clFlex01" id="clFlex01" maxlength="200" cssClass="form-control textbox"/>
                <label class="textboxlabel"><s:text name="lbl.flex1"/></label>
            </div>
            <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <s:textfield name="leads.clFlex02" id="clFlex02" maxlength="200" cssClass="form-control textbox"/>
                <label class="textboxlabel"><s:text name="lbl.flex2"/></label>
            </div>
        </div>
        <div class="form-fields clearfix">
            <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <s:textfield name="leads.clFlex03" id="clFlex03" maxlength="200" cssClass="form-control textbox"/>
                <label class="textboxlabel"><s:text name="lbl.flex3"/></label>
            </div>
            <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <s:textfield name="leads.clFlex04" id="clFlex04" maxlength="200" cssClass="form-control textbox"/>
                <label class="textboxlabel"><s:text name="lbl.flex4"/></label>
            </div>
        </div>
        <%--div class="form-fields clearfix">
           <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <s:select name="leads.clAssignedTo"  id="clAssignedTo" list="assignToList" listKey="key" listValue="value" cssClass="textbox" data-toggle="tooltip" data-placement="top" title="Assinged To"/>
                <label class="textboxlabel">Assigned To</label>
            </div>
            <div class="form-group col-sm-6 col-md-6 col-xs-12 spac">
                <s:if test='operation !=null && "edit".equalsIgnoreCase(operation)'>
                <s:select name="leads.clStatus"  id="clStatus" list="statusList" listKey="key" listValue="value" headerKey="" headerValue="-- Select --" cssClass="textbox" data-toggle="tooltip" data-placement="top" title="Status"/>
                <label class="textboxlabel">Status</label>
                </s:if>
            </div>
        </div--%>
        <s:hidden name="leads.clStatus"/>
        <div class="form-fields clearfix">
             <div class="form-group col-sm-12 col-md-12 col-xs-12 spac">
                 <s:textarea name="leads.clRemarks" id="clRemarks" cssClass="textbox " data-toggle="tooltip" data-placement="top" title="Remarks"/>
                 <label class="textboxlabel"><s:text name="lbl.remarks"/></label>
            </div>
        </div>
        </s:form>
    </div>
<script type="text/javascript">
    $( function() {
     $( "#datepicker").datepicker({format: 'dd/mm/yyyy'});
	
	 
  } );
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
    /*$('#clBirthDt').bootstrapMaterialDatePicker({
        format: 'DD/MM/YYYY',
        clearButton: true,
        weekStart: 1,
        time: false
    });*/
    $(document).ready(function () {
         <s:if test='"add".equals(operation)'>
        $("#clSource").val('O');
        //$("#clWorkPlace").val('2');
         </s:if>
        $('[data-toggle="tooltip"]').tooltip();
        $('#frm_lead').validate({
            onfocusout: false,
            onkeyup: false,
            onclick: false,
            wrapper: 'li',
            rules: {
                "leads.clName": {
                    required: true
                },
                "leads.clGender": {
                    required: true
                },
                "leads.clMobileNo": {
                    required: true,
                    digits: true,
                    minlength: 8,
                    maxlength: 10
                },
                 "leads.clPoBox": {
                   digits: true,
                },
                 "leads.clTelNo": {
                   digits: true,
                },
                 "leads.clFaxNo": {
                   digits: true,
                },
                "leads.clWorkPlace": {
                    required: true
                },
                "leads.clSource": {
                    required: true
                },
            },
            messages: {
                "leads.clName": {
                    required: "Please Enter Name"
                },
                "leads.clGender": {
                    required: "Please Select Gender"
                },
                "leads.clMobileNo": {
                    required: "Please Enter MobileNo"
                },
                 "leads.clWorkPlace": {
                    required: "Please Select Data Source"
                },
                 "leads.clSource": {
                    required: "Please Select Source"
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
                    $("#msg_lead").hide();
            },
            errorPlacement: function (error, element) {
                $("#msg_lead").html(error);
            },
            invalidHandler: function (form, validator) {
                var errors = validator.numberOfInvalids();
                if (errors)
                    $("#msg_lead").show();
                else
                    $("#msg_lead").hide();
            }
        });
        $("#btn_lead_save").on("click", function () {
            if (!$('#frm_lead').valid()) {
                return false;
            }
             block('block_body');
            $.ajax({
                type: "POST",
                url: APP_CONFIG.context + "/saveLeadsEntryForm.do",
                data: $("#frm_lead").serialize(),
                success: function (result) {
                    if (result.messageType === "S") {
                        //$("#msg_lead").removeClass("alert-danger").addClass("alert-success").html(result.message).show();
                        //$('#plugin_modal_dialog').modal('hide');
                        $.notify("Lead details saved successfully", "custom");
                         $('.popup-wrap').removeClass('popup-open');
                         $('#overlay').hide();
                        $("#lead_tbl").DataTable().ajax.url(getLeadDataTableUrl()).load();
                    } else {
                        $('#msg_lead').empty().html(result.message).show();
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
    });
    function loadCityDropDown(val, id) {
        $('#clCity').html("");
        $('#clCity').append('<option value="">--Select-</option>')
        $.ajax({
            type: "POST",
            url: "<%=request.getContextPath()%>/GetCityList.do",
            data: {"customer.country": val, "company": $("#company").val()},
            success: function (data) {
                $.each(data.cityList, function (i, d) {
                    $('#' + id).append('<option value="' + d.key + '">' + d.value + '</option>');
                });
            }
        });
    }
    function getLeadDataTableUrl() {
        return APP_CONFIG.context + "/loadLeadsEntryData.do?" + $('#frm_search').serialize();
    }
</script>