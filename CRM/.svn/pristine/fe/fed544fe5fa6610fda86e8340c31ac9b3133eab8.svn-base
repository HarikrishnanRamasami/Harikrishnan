<%-- 
    Document   : campaignView
    Created on : Sep 25, 2019, 12:23:10 PM
    Author     : soumya.gaur
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<div id="body">
    <div class="card" id="form_temp_field">
        <div class="col-md-12 right-pad">
            <div class="dash-leads" style="border-top:0!important">
                <div class="my-bord bor2">
                    <h3> </h3>
                    <div class="col-sm-12 col-md-3 board-icons1 Rbtn">
                        <button type="button" class="save-btn btn btn-primary" id="btn_config_save" style="margin-top: -8px" data-toggle="tooltip" data-placement="top" title="Save"><i class="fa fa-save"></i> <s:text name="btn.save"/></button>
                        <button type="button" class="btn btn-danger waves-effect" style="margin-top: -8px" id="btn_config_close" data-toggle="tooltip" data-placement="top" title="Close"><i class="fa fa-close"></i> <s:text name="btn.close"/></button>
                    </div>
                </div>
                <div style="padding-left: 15px; padding-right: 15px; padding-top: 10px;padding-top: 23px;">
                    <div id="msg_camp_temp" class="alert-danger" style="display: none;"></div>
                    <s:form id="frm_view" theme="simple" name="frm_view" method="post" autocomplete="off">
                        <s:hidden name="campForm.mcfFormId"  id="mcfFormId" /> 
                        <s:hidden id="test"/>
                        <div class="row" style="align:center;">
                            <div class="col-lg-6 col-md-6 col-sm-9 col-xs-12">
                                <div class="form-group">
                                    <input type="text" style="align:center;" value="<s:property  value="campForm.mcfFormTitle" />" class="form-control" disabled="true" />
                                </div>
                            </div>
                        </div>
                        <div class="row"  style="align:center;">
                            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                <div class="form-group">
                                    <input type="text" value="<s:property  value="campForm.mcfBannerUrl" />" class="form-control" disabled="true" />
                                </div>
                            </div>
                        </div>
                        <div class="row" style="align:center;">
                            <table class="table table-striped table-bordered display dataTable dtr-inline">
                                <s:if test="aaData != null && aaData.size() > 0">
                                    <s:iterator value="aaData" status="stat">
                                        <tr>
                                            <td>
                                                <s:property value="mcffFieldName"/>
                                                <s:if test='"1".equals(mcffMandetoryYn)'>                           
                                                    <span>*</span>  
                                                </s:if>
                                            </td>
                                            <td>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <div class="form-group">
                                                    <s:if test='"D".equals(mcffFieldType)'>
                                                        <s:textfield id="mctfData[%{#stat.index}]" name="txnForm.mctfData[%{#stat.index}]" cssClass="form-control textbox datepicker calicon"  placeholder="DD/MM/YYYY" data-toggle="tooltip" data-placement="top" />        
                                                    </s:if>
                                                    <s:elseif test='"T".equals(mcffFieldType)'>   
                                                        <s:textfield id="mctfData[%{#stat.index}]" name="txnForm.mctfData[%{#stat.index}]" cssClass="form-control"   data-toggle="tooltip" data-placement="top" />     
                                                    </s:elseif>
                                                    <s:elseif test='"A".equals(mcffFieldType)'>
                                                        <s:textfield id="mctfData[%{#stat.index}]" name="txnForm.mctfData[%{#stat.index}]" cssClass="form-control"   data-toggle="tooltip" data-placement="top" />           
                                                    </s:elseif>  
                                                    <s:elseif test='"R".equals(mcffFieldType)'>
                                                        <s:radio id="mctfData[%{#stat.index}]" name="txnForm.mctfData[%{#stat.index}]" list="#{'1': 'Yes','0' :'No'}"   data-toggle="tooltip" data-placement="top" />                                                
                                                    </s:elseif> 
                                                    <s:elseif test='"S".equals(mcffFieldType)'>
                                                        <s:select id="mctfData[%{#stat.index}]" name="txnForm.mctfData[%{#stat.index}]" list="#{}" listKey="key" listValue="value" required="true"  data-toggle="tooltip" data-placement="top" cssClass="form-control" />
                                                    </s:elseif> 
                                                </div>
                                            </td>
                                        </tr>
                                    </s:iterator>
                                </s:if>
                                <s:else>
                                    <s:text name="lbl.campaign.no.dynamic.rows"/>
                                </s:else>
                            </table>
                        </div>
                        <div class="row" style="align:center;">
                            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                <div class="form-group">
                                    <input type="text" value="<s:property  value="campForm.mcfFormFooter" />" class="form-control" disabled="true" />
                                </div>
                            </div>
                        </div>                      
                    </s:form>
                </div>
            </div>  
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function () {
        $(".datepicker").datepicker({format: 'dd/mm/yyyy'});
    });
    $(document).ready(function () {
        $("#btn_config_close").on("click", function () {
            $('#camp_list').show();
            $('#camp_form').empty().hide();
        });

    });
</script>