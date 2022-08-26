<%-- 
    Document   : campaignTemplateForm
    Created on : 16 Sep, 2019, 11:55:53 AM
    Author     : syed.basha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%--<%@ taglib prefix="sjr" uri="/struts-jquery-richtext-tags"%>--%>
<script type="text/javascript" src="<%=request.getContextPath()%>/plugins/innovate/js/jquery.slimscroll.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/plugins/jquery/jquery-ui.js"></script>



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
    <div class="card" id="block_temp_form">
        <div class="col-md-12 right-pad">
            <div class="dash-leads" style="border-top:0!important">
                <div class="my-bord bor2">
                    <h3><s:text name="lbl.campaign.template.form"/></h3>
                </div>
                <div style="padding-left: 15px; padding-right: 15px; padding-top: 10px;padding-top: 23px;">
                    <div id="msg_camp_temp" class="alert-danger" style="display: none;"></div>
                    <s:form id="frm_template" theme="simple" name="frm_template" method="post" autocomplete="off">
                        <s:hidden name="mcCampId" id="mcCampId"/>
                        <s:hidden name="oper" />
                        <s:hidden name="campTemplate.mctType" id="tempType" />
                        <div class="row">
                            <div class="col-lg-6 col-md-6 col-sm-9 col-xs-12">
                                <label><s:text name="lbl.common.name"/><span>*</span></label>
                                <div class="form-group">                                   
                                    <s:textfield name="campTemplate.mctName" required="true"  cssClass="form-control" />
                                </div>
                            </div>
                            <div id="sub" class="col-lg-6 col-md-6 col-sm-9 col-xs-12">
                                <label><s:text name="lbl.subject"/><span>*</span></label>
                                <div class="form-group">
                                    <s:textfield  name="campTemplate.mctSubject"  id="mctSubject" required="true" cssClass="form-control" onblur="setId(this);"/>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-3 form-group keyDiv">
                                <div ><em class="span_hd_txt"><s:text name="lbl.campaign.key.elements"/></em></div>
                                <div class="some-content-related-div">
                                    <div class="key-ele-div">  </div>
                                </div>
                            </div>
                            <div  id="emailBody" >
                                <label> <s:text name="lbl.common.body"/> <span>*</span></label>
                                <div class="col-md-9 form-group">        
                                    <s:textarea id="bodyTemp"  name="campTemplate.mctBody" required="true" cssClass="form-control" data-toggle="tooltip" data-placement="top" style="min-height:475px;" title="%{getText('lbl.campaign.enter.campaign.body')}" onblur="setId(this);"/> 
                                </div>
                            </div>
                        </div>
                    </s:form>
                </div>
                <div class="form-group col-md-7" style="margin-top: 32px; float: right">
                    <button type="button" class="save-btn btn btn-primary" id="btn_save_temp" style="margin-top: -8px" data-toggle="tooltip" data-placement="top" title="Save"><i class="fa fa-save"></i> <s:text name="btn.save"/></button>
                    <button type="button" class="btn btn-danger waves-effect" style="margin-top: -8px" id="btn_camp_close" data-toggle="tooltip" data-placement="top" title="Close"><i class="fa fa-close"></i> <s:text name="btn.close"/></button>
                </div>
            </div>  
        </div>
    </div>
</div>

<script type="text/javascript">
    var idName;
    $(document).ready(function () {
        //check_url();
        getMapKeyElements();
        $('#frm_template').validate({
            onfocusout: false,
            onkeyup: false,
            onclick: false,
            wrapper: 'li',
            ignore: ':hidden:not(#tempBody)',
            messages: {
                "campTemplate.mctName": {
                    required: "Please Enter Template Name"
                },
                "campTemplate.mctSubject": {
                    required: "Please Enter Subject"
                },
                "campTemplate.mctBody": {
                    required: "Please Enter Body"
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

    $("#btn_save_temp").on("click", function () {
        if (!$('#frm_template').valid()) {
            return false;
        }
        block('block_body');
        $.ajax({
            type: "POST",
            url: APP_CONFIG.context + "/camp/SaveDataTemplate.do?campTemplate.mctTemplateId=<s:property value="campTemplate.mctTemplateId"/>",
            data: $("#frm_template").serialize(),
            success: function (result) {
                if (result.messageType === "S") {
                    //$('#msg_camp_temp').html(result.message).show();
                    $.notify(result.message, "custom");
                    $('#block_template').show();
                    $('#block_temp_form').empty().hide();
                    $('#block_camp_form').empty().hide();

                    reloadDt("tbl_camp_temp");
                } else {
                    $('#msg_camp_temp').empty().html(result.message).show();
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

    $("#btn_camp_close").on("click", function () {
        $('#block_template').show();
        $('#block_camp_form').empty().hide();
    });

    function getMapKeyElements() {
        block('block_body');
        $.ajax({
            url: "GetMapKeyElements.do",
            dataType: "json",
            type: 'POST',
            data: {"mcCampId": "${mcCampId}"},
            success: function (response) {
                //var data = JSON.parse(response);
                $("#tempType").val(response.campaign.mcCampType);
                if ($("#tempType").val() === 'S') {
                    $("#sub").hide();
                } else if ($("#tempType").val() === 'E') {
                    $("#sub").show();
                }
                var items = '<ul>';
                $.each(response.aaData, function (i, o) {
                    items = items + '<li class="liClik" value="' + o.key + '">' + o.value + '<span class="forward-Arrow"></span></li>';
                });
                items = items + '</ul>';
                $('.key-ele-div').html(items);

                $('li.liClik').on('click', function () {
                    var code = $(this).attr('value');
                    var target = document.getElementById(idName);
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

    function setId(_this) {
        idName = $(_this).attr("id");
        console.log(idName);
    }
</script>
