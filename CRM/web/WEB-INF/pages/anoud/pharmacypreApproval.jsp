<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<div class="modal-header">
    <h4 class="modal-title"><s:text name="lbl.pharmacy.preapproval.form"/></h4>
</div>
<div class="body" style="padding: 7px;">
	<div id="msg_pharmacy" class="alert alert-danger" style="display: none;"></div>
	<div class="row">
		<s:form id="frm_pharmacy" name="frm_pharmacy" method="" theme="simple" >
			<s:hidden name="flex2" id="hid_flex2" />
			<s:if test='flex2 == null || flex2.equals("")'>
				<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                    <label><s:text name="lbl.provider.name"/><span>*</span></label>
					<div class="form-group">
						<s:textfield name="provideCodeDesc" id="provideCodeDesc"
							cssClass="form-control" required="true" title="%{getText('btn.search.provider')}"
							placeholder="Enter min 3 chars" />
					</div>
				</div>
			</s:if>
			<div class="col-lg-5 col-md-5 col-sm-12 col-xs-12" id="member_div">
                            <label><s:text name="lbl.mem.id"/><span></span></label>
				<div class="form-group">
					<s:textfield name="memberId" id="memberId" cssClass="form-control"
						required="true" title="%{getText('lbl.member.id')}" />
				</div>
			</div>
                        <div class="col-lg-2 col-md-2 col-sm-12 col-xs-12">(<s:text name="lbl.or"/>)</div>
			<div class="col-lg-5 col-md-5 col-sm-12 col-xs-12">
                            <label><s:text name="lbl.qatar.id"/><span></span></label>
				<div class="form-group">
					<s:textfield name="qatarId" id="qatarId" cssClass="form-control"
						required="true" title="%{getText('lbl.title.qatar.id')}" />
				</div>
			</div>
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <label><s:text name="lbl.primary.diagnosis"/><span>*</span></label>
				<div class="form-group">
					<s:textfield name="diagnosis" id="diagnosis"
						cssClass="form-control" required="true" title="" />
					<s:hidden name="diagCode" id="diagCode" />
				</div>
			</div>
			<div class="form-group col-sm-12 col-md-12 col-xs-12 spac">
                            <label><s:text name="lbl.pharmacy.drugs"/><span></span></label>
				<div class="form-group">
					<s:textarea name="details" id="details" cssClass="form-control"
						required="true" title="%{getText('lbl.pharmacy.drugs')}" />
				</div>
			</div>
			<div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
                            <label><s:text name="lbl.amount"/><span>*</span></label>
				<div class="form-group">
					<s:textfield name="amount" id="amount" cssClass="form-control"
						required="true" title="%{getText('lbl.amount')}" />
				</div>
			</div>
		</s:form>
		<div class="col-lg-6 col-md-6 col-sm-12 col-xs-12"
			style="margin-top: 23px">
			<button class="save-btn btn btn-primary" id="btn_create_preapproval"
                                onclick="callPharmacyApproval();"><s:text name="btn.get.me.approval"/></button>
			<button class="close-btn btn" data-dismiss="modal"
				id="btn_approval_close"><s:text name="btn.close"/></button>
		</div>
	</div>
</div>

<script type="text/javascript">

	$(document).ready(function() {
		 //Auto Complete
		var has_valid_values = false;
        var has_values_modified = false;
        $("#diagnosis").autocomplete({
            source: function (request, response) {
                var flex1 = request.term;
                $.ajax({
                    url: APP_CONFIG.context + "/loadProviderDiagnosis.do",
                    data: {
                        flex1: flex1
                    },
                    success: function (data) {
                        unblock();
                        var rows = [];
                        $.each(data.diagnosisList, function (i, v) {

                            rows.push({label: v.key + "-" + v.value, value: v.value, key: v.key});
                        });
                        response(rows);
                    },
                    error: function (xhr, status, error) {
                        displayAlert('E', error);
                    }
                });
            },
            select: function (event, ui) {
                has_valid_values = true;
                $("#diagCode").val(ui.item.key);
                $(this).val(ui.item.key + "-" + ui.item.value);
                event.preventDefault();
            },
            change: function (event, ui) {
                has_values_modified = true;
            },
            open: function () {
                $(this).removeClass("ui-corner-all ui-corner-top");
            },
            close: function () {
                $(this).removeClass("ui-corner-top ui-corner-all"); 
            },
            minLength: 3,
            autoFocus: true
        }).on("blur", function (event) {
            if (!has_valid_values && has_values_modified) {
                $(this).val("");
            }
            has_valid_values = false;
            has_values_modified = false;
        });
        
		 $("#provideCodeDesc").autocomplete({
		        source: function (request, response) {
		            
		            var val = request.term;
		          
		            $.ajax({
		                url: APP_CONFIG.context +"/LoadProviderList.do",
		                dataType: "json",
		                data: {
		                    searchValue: val
		                },
		                success: function (data) {
		                
		                    var rows = [];
		                    if (data.aaData == null) {
		                        has_valid_values = false;
		                    } else {
		                        $.each(data.aaData, function (i, v) {
		                        	rows.push({label: v.key + "-" + v.value, value: v.value, key: v.key});
		                        });
		                    }
		                    if(rows.length>0){
		                    	response(rows);
		                    }else{
		                    	$("#msg_pharmacy").html("Searched Provider Name Not Available").show();
		                    	
		                    }
		                    console.log(rows);
		                },
		                error: function (xhr, status, error) {
		                    console.log("error: " + error);
		                }
		            });
		        },
		        minLength: 3,
		        select: function (event, ui) {
		        $("#msg_pharmacy").html("").hide(); 	
		        has_valid_values = true;
                $("#hid_flex2").val(ui.item.key);
                $(this).val(ui.item.key + "-" + ui.item.value);
                event.preventDefault();
            },
            change: function (event, ui) {
                has_values_modified = true;
            },
            open: function () {
                $(this).removeClass("ui-corner-all ui-corner-top");
            },
            close: function () {
                $(this).removeClass("ui-corner-top ui-corner-all");
            },
            autoFocus: true
        }).on("blur", function (event) {
            if (!has_valid_values && has_values_modified) {
                $(this).val("");
                 $("#hid_flex2").val("");
                  
            }
            has_valid_values = false;
            has_values_modified = false;
        });
	});
	
    function callPharmacyApproval() {
        if (($("#hid_flex2").val() == null || $("#hid_flex2").val()) == "") {
            $("#msg_pharmacy").html("Enter ProviderName").show();
            return false;
        }
        if (($("#memberId").val() == null || $("#memberId").val()) == "" && ($("#qatarId").val() == "" || $("#qatarId").val() == null)) {
            $("#msg_pharmacy").html("Enter Qatar Id Or Member Id").show();
            return false;
        }
        if (($("#diagCode").val() == null || $("#diagCode").val()) == "") {
            $("#msg_pharmacy").html("Enter Primary Diagnosis").show();
            return false;
        }
        if (($("#amount").val() == null || $("#amount").val()) == "") {
            $("#msg_pharmacy").html("Enter Amount").show();
            return false;
        }
       
        
        $.ajax({
        	type: "POST",
            url: APP_CONFIG.context + "/SaveMemberPreApproval.do?",
            data :{
            	operation :"Y",flex1 : "ProviderPharmacyPreApproval",memberId: $("#memberId").val(),qatarId: $("#qatarId").val(),flex4: $("#diagCode").val(),flex3 : $("#details").val(), amount : $("#amount").val(), flex2: $("#hid_flex2").val()
            },
            success: function (data) {
           		if(data.messageType === "S") {
					var url = APP_CONFIG.context + "/QLMAppIntegration.do?operation=Y&flex1=ProviderPharmacyPreApproval&keyValue.info1=" + data.flex1;
					window.open(url, '_blank');
					$("#plugin_modal_dialog").modal("hide");
           	   	} else {
           		   $("#msg_pharmacy").html(data.message).show();  
           	   	}
            },
            error: function (xhr, status, error) {
                console.log("error: " + error);
            }
        });
    }    
</script>
