<%-- 
    Document   : campaignForm
    Created on : Sep 23, 2019, 1:28:17 PM
    Author     : soumya.gaur
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/campaignFilter.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/struts/optiontransferselect.js"></script>
<style type="text/css">
    /*    .jqte{
            min-height: 300px;
        }*/
    .span_hd{ margin: 0px; height: 40px; width: 100%; display: inline-block; background-color: #981b45;margin-left:-15px;padding-top: 10px}
    .span_hd_txt{ margin: 10px 0px 0px 30px; color: white; font-style:normal; font-size: 14px; font-weight: bold; font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;}
    .key-ele-div > ul { list-style-type: none; height: 462px; overflow: auto;margin-left: -15px;width: 100%;}
    .key-ele-div > ul > li {color: #24476B; margin-left: 3px; margin-right: 3px; padding-top: 5px; padding-bottom: 5px; border-bottom: 1px solid #E0E0E0; text-align: left;}
    .key-ele-div:hover > ul > li:hover { color: #CC0000; background-color: #E6E6F0; cursor: pointer;}

    .forward-Arrow:AFTER{float: right; content: '>'; font-weight: bold; margin-right: 8px;}
</style>
<div id="body">
    <div class="card" id="camp_temp_form">
        <div class="col-md-12 right-pad">
            <div class="dash-leads" style="border-top:0!important">
                <div class="my-bord bor2">
                    <h3> Form</h3>
                    <!--div class="col-sm-12 col-md-3 board-icons1 Rbtn">
                        <button type="button" class="save-btn btn btn-primary" id="btn_form_save" style="margin-top: -8px" data-toggle="tooltip" data-placement="top" title="Save"><i class="fa fa-save"></i> Save</button>
                        <button type="button" class="btn btn-danger waves-effect" style="margin-top: -8px" id="btn_form_close" data-toggle="tooltip" data-placement="top" title="Close"><i class="fa fa-close"></i> Close</button>
                    </div-->
                </div>
                <div style="padding-left: 15px; padding-right: 15px; padding-top: 10px;padding-top: 23px;">
                    <div id="msg_camp_temp" class="alert-danger" style="display: none;"></div>
                    <s:form id="frm_campaign_form" theme="simple" name="frm_campaign_form" method="post" autocomplete="off">

                        <s:hidden name="oper" />
                        <s:hidden name="campForm.mcfFormId" id="mcfFormId" />
                        <div class="row">
                            <div class="col-lg-6 col-md-6 col-sm-9 col-xs-12">
                                <label><s:text name="lbl.name"/><span>*</span></label>
                                <div class="form-group">                                   
                                    <s:textfield id="mcfName" name="campForm.mcfName" cssClass="form-control" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.common.enter.name')}"/>
                                </div>
                            </div>
                            <div class="col-lg-6 col-md-6 col-sm-9 col-xs-12">
                                <label><s:text name="lbl.common.title"/><span>*</span></label>
                                <div class="form-group">
                                    <s:textfield id="mcfFormTitle" name="campForm.mcfFormTitle" cssClass="form-control" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.common.enter.title')}"/>
                                </div>
                            </div>
                        </div>
                        <div class="row" id="banner" >
                            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                <label><s:text name="lbl.campaign.banner.url"/> <span></span></label>
                                <div class="form-group">
                                    <s:textfield id="mcfBannerUrl" name="campForm.mcfBannerUrl" cssClass="form-control" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.common.enter.banner')}"/>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="col-md-3 form-group keyDiv">
                                <div ><em class="span_hd_txt"><s:text name="lbl.campaign.key.elements"/></em></div>
                                <div class="some-content-related-div">
                                    <div class="key-ele-div">  </div>
                                </div>
                            </div>
                            <div  id="header">
                                <label> <s:text name="lbl.common.header"/> <span>*</span></label>
                                <div class="col-md-9 form-group">    
                                    <s:textarea id="mcfFormHeader" name="campForm.mcfFormHeader" cssClass="form-control" data-toggle="tooltip" style="min-height:475px;" data-placement="top" title="%{getText('lbl.common.enter.header')}"/> 
                                </div>
                            </div>
                        </div>

                        <div class="row" id="footer" >
                            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                <label><s:text name="lbl.common.footer"/><span></span></label>
                                <div class="form-group">
                                    <s:textarea id="mcfFormFooter" name="campForm.mcfFormFooter" cssClass="form-control" data-toggle="tooltip" data-placement="top" title="%{getText('lbl.common.enter.title')}"/>
                                </div>
                            </div>
                        </div>
                        <div id="validity" class="row" >
                            <div class="col-lg-6 col-md-6 col-sm-9 col-xs-12">
                                <label><s:text name="lbl.common.color"/></label>
                                <div class="form-group">                                   
                                 <s:select id="mcfFormColor" name="campForm.mcfFormColor" list="colorList"  listKey="key" listValue="value"  data-toggle="tooltip" data-placement="top"   cssClass="form-control"/>
                                </div>
                            </div>
                            <div class="col-lg-6 col-md-6 col-sm-9 col-xs-12">
                                <label><s:text name="lbl.common.validity"/><span>*</span></label>
                                <div class="form-group">
                                    <s:textfield id="mcfValidity" name="campForm.mcfValidity" cssClass="form-control textbox calicon" placeholder="DD/MM/YYYY" required="true" title="%{getText('lbl.common.enter.title')}"/>
                                </div>
                            </div>
                        </div>
                    </s:form>
                </div>
                <div class="form-group col-md-7" style="margin-top: 32px; float: right">
                    <button type="button" class="save-btn btn btn-primary" id="btn_form_save" style="margin-top: -8px" data-toggle="tooltip" data-placement="top" title="Save"><i class="fa fa-save"></i> <s:text name="btn.save"/></button>
                    <button type="button" class="btn btn-danger waves-effect" style="margin-top: -8px" id="btn_form_close" data-toggle="tooltip" data-placement="top" title="Close"><i class="fa fa-close"></i> <s:text name="btn.close"/></button>
                </div>
            </div>  
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function () {
        $(".datepicker").datepicker({format: 'dd/mm/yyyy'});
        $("#mcfValidity").datetimepicker({
            format: 'DD/MM/YYYY HH:mm',
            minDate: new Date()
        });
    });
    $(document).ready(function () {
        getMapKeyElements();

        $('#frm_campaign_form').validate({
            onfocusout: false,
            onkeyup: false,
            onclick: false,
            wrapper: 'li',
            ignore: ':hidden:not(#tempBody)',
            messages: {
                "campForm.mcfName": {
                    required: "Please Enter Form Name"
                },
                "campForm.mcfFormTitle": {
                    required: "Please Enter Form Title"
                },
                "campForm.mcfValidity": {
                    required: "Please Enter Validity"
                }

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
                    $("#msg_camp_temp").hide();
            },
            errorPlacement: function (error, element) {
                $("#msg_camp_temp").html(error);
            },
            invalidHandler: function (form, validator) {
                var errors = validator.numberOfInvalids();
                if (errors)
                    $("#msg_camp_temp").show();
                else
                    $("#msg_camp_temp").hide();
            }
        });
    });

    $("#btn_form_save").on("click", function () {
        
        if (!$('#frm_campaign_form').valid()) {
            return false;
        }
        block('block_body');
        $.ajax({
            type: "POST",
            url: '${pageContext.request.contextPath}/camp/saveCampaignFormData.do?mcCampId=' +${mcCampId},
            data: $("#frm_campaign_form").serialize(),
            async: true,
            success: function (result) {
                if (result.messageType === "S") {
                    $.notify(result.message, "custom");
                    $('#camp_list').show();
                    $('#camp_temp_form').empty().hide();
                    $('#camp_form').empty().hide();
                    reloadDt("tbl_form_view");

                } else {
                    $('#msg_camp_temp').empty().html(result.message).show();
                    // $.notify(result.message, "custom");
                }
            },
            error: function (xhr, status, error) {
                alert("Error: " + error);
                block('block_body');
            },
            complete: function () {
                unblock('block_body');
            }
        });
    });

    $("#btn_form_close").on("click", function () {
        $('#camp_list').show();
        $('#camp_temp_form').empty().hide();
    });


    function getMapKeyElements() {
        block('block_body');
        $.ajax({
            url: "GetMapKeyElements.do",
            dataType: "json",
            type: 'POST',
            data: {"mcCampId": "${mcCampId}"},
            success: function (response) {
                var items = '<ul>';
                $.each(response.aaData, function (i, o) {
                    items = items + '<li class="liClik" value="' + o.key + '">' + o.value + '<span class="forward-Arrow"></span></li>';
                });
                items = items + '</ul>';
                $('.key-ele-div').html(items);

                $('li.liClik').on('click', function () {
                    var code = $(this).attr('value');
                    var target = document.getElementById("mcfFormHeader");
                    if (target.setRangeText) {
                        //if setRangeText function is supported by current browser
                        target.setRangeText('@' + code + '@');
                    }
                });

            },
            complete: function () {
                unblock('block_body');
            },
            error: function (xhr, status, error) {
                displayAlert('E', error);
            }
        });
    }

</script>
