<%-- 
    Document   : abPerformance
    Created on : Sep 19, 2019, 11:50:55 AM
    Author     : soumya.gaur
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<s:hidden name="campaign.mcCampId" id="mcCampId" />
<%-- <div class="form-fields clearfix">
    <div class="form-group col-sm-12 col-md-12 col-xs-12 spac">
        <label class="textboxlabel">Record Count<span>*</span></label>
        <s:textfield name="campaign.mcAbDataCount" id="mcAbDataCount" title="Please Enter Count"  cssClass="form-control"/>

    </div>
    <div class="form-group col-sm-12 col-md-12 col-xs-12 spac">
        <label class="textboxlabel">End Date<span>*</span></label>
        <s:textfield name="campaign.mcAbEndDate"  id="mcAbEndDate"  cssClass="textbox datepicker calicon"  placeholder="DD/MM/YYYY" data-toggle="tooltip" data-placement="top" title="End Date" />
    </div>

</div> --%>

<div class="row">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <label><s:text name="lbl.common.record.count"/>: <span>*</span></label>
        <div class="form-group">
            <div class="form-line">
                <s:textfield name="campaign.mcAbDataCount" id="mcAbDataCount" title="%{getText('lbl.campaign.please.enter.count')}"  cssClass="form-control"/>
            </div>
        </div>
    </div>
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <label><s:text name="lbl.end.date"/>: </label>
        <div class="form-group">
            <div class="form-line">
                <s:textfield name="campaign.mcAbEndDate"  id="mcAbEndDate"  cssClass="textbox datepicker  calicon" placeholder="DD/MM/YYYY HH:mm"  data-toggle="tooltip" data-placement="top" title="%{getText('lbl.end.date')}" />
            </div>
        </div>
    </div>
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <label><s:text name="lbl.common.action.type"/>: </label>
        <div class="form-group">
            <div class="form-line">
                <s:select name="campaign.mcAbActionType" id="mcAbActionType" list="#{}" listKey="key" listValue="value" cssClass="form-control" />   
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function () {
       //  $(".datepicker").datepicker({format: 'dd/mm/yyyy'});
       $(".datepicker").datetimepicker({format: 'DD/MM/YYYY HH:mm'});
    });
</script>


