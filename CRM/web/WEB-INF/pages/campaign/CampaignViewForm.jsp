<%-- 
    Document   : CampaignViewForm
    Created on : 22 Oct, 2019, 3:51:33 PM
    Author     : sutharsan.g
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<style type="text/css">
    body,html {
        height: 100%;
        font-size:14px;
    }
    html body.bg-emailer {
        background: url(<%=request.getContextPath()%>/plugins/innovate/images/background_1360x768.jpg) #fff fixed no-repeat center center;
        background-size: cover;
        /* padding: 175px 35px 0px 15px; */

        -webkit-background-size: cover;
        -moz-background-size: cover;
        -o-background-size: cover;

    }

    .masthead {
        height: 100vh;
        /* width: 750px;
     min-height: 200px;*/
        max-height: 200px;
        overflow:hidden;
        /* background-image: url('../images/it-background.jpg');*/
        background-image: url('../images/it-background-3.jpg');
        background-size: cover;
        background-position: center;
        background-repeat: no-repeat;
        margin: 0px auto 10px auto;
    }

    .masthead img {
        width:100%;
    }


    section {margin:0px 0px;}

    /*---------------------------*/

    .checkbox {
        /* padding-left: 20px;*/
    }

    .checkbox label {
        display: inline-block;
        vertical-align: middle;
        position: relative;
        padding-left: 5px;
    }

    .checkbox label::before {
        content: "";
        display: inline-block;
        position: absolute;
        width: 17px;
        height: 17px;
        left: 0;
        top:3px;
        margin-left: -20px;
        border: 1px solid #cccccc;
        border-radius: 3px;
        background-color: #fff;
        -webkit-transition: border 0.15s ease-in-out, color 0.15s ease-in-out;
        -o-transition: border 0.15s ease-in-out, color 0.15s ease-in-out;
        transition: border 0.15s ease-in-out, color 0.15s ease-in-out;
    }

    .checkbox label::after {
        display: inline-block;
        position: absolute;
        width: 16px;
        height: 16px;
        left: 0;
        top: 2px;
        margin-left: -20px;
        padding-left: 3px;
        padding-top: 1px;
        font-size: 11px;
        color: #555555;
    }

    .checkbox input[type="checkbox"] {
        opacity: 0;
        z-index: 1;
    }

    .checkbox input[type="checkbox"]:focus + label::before {
        outline: thin dotted;
        outline: 5px auto -webkit-focus-ring-color;
        outline-offset: -2px;
    }

    .checkbox input[type="checkbox"]:checked + label::after {
        font-family: 'FontAwesome';
        content: "\f00c";
    }

    .checkbox input[type="checkbox"]:disabled + label {
        opacity: 0.65;
    }

    .checkbox input[type="checkbox"]:disabled + label::before {
        background-color: #eeeeee;
        cursor: not-allowed;
    }

    .checkbox.checkbox-circle label::before {
        border-radius: 50%;
    }

    .checkbox.checkbox-inline {
        margin-top: 0;
    }

    .checkbox-primary input[type="checkbox"]:checked + label::before {
        background-color: #428bca;
        border-color: #428bca;
    }

    .checkbox-primary input[type="checkbox"]:checked + label::after {
        color: #fff;
    }

    .checkbox-danger input[type="checkbox"]:checked + label::before {
        background-color: #d9534f;
        border-color: #d9534f;
    }

    .checkbox-danger input[type="checkbox"]:checked + label::after {
        color: #fff;
    }

    .checkbox-info input[type="checkbox"]:checked + label::before {
        background-color: #5bc0de;
        border-color: #5bc0de;
    }

    .checkbox-info input[type="checkbox"]:checked + label::after {
        color: #fff;
    }

    .checkbox-warning input[type="checkbox"]:checked + label::before {
        background-color: #f0ad4e;
        border-color: #f0ad4e;
    }

    .checkbox-warning input[type="checkbox"]:checked + label::after {
        color: #fff;
    }

    .checkbox-success input[type="checkbox"]:checked + label::before {
        background-color: #5cb85c;
        border-color: #5cb85c;
    }

    .checkbox-success input[type="checkbox"]:checked + label::after {
        color: #fff;
    }


    .radio label {
        display: inline-block;
        vertical-align: middle;
        position: relative;
        padding-left: 5px;
    }

    .radio label::before {
        content: "";
        display: inline-block;
        position: absolute;
        width: 17px;
        height: 17px;
        left: 0;
        top:3px;
        margin-left: -20px;
        border: 1px solid #cccccc;
        border-radius: 50%;
        background-color: #fff;
        -webkit-transition: border 0.15s ease-in-out;
        -o-transition: border 0.15s ease-in-out;
        transition: border 0.15s ease-in-out;
    }

    .radio label::after {
        display: inline-block;
        position: absolute;
        content: " ";
        width: 11px;
        height: 11px;
        left: 3px;
        top: 6px;
        margin-left: -20px;
        border-radius: 50%;
        background-color: #555555;
        -webkit-transform: scale(0, 0);
        -ms-transform: scale(0, 0);
        -o-transform: scale(0, 0);
        transform: scale(0, 0);
        -webkit-transition: -webkit-transform 0.1s cubic-bezier(0.8, -0.33, 0.2, 1.33);
        -moz-transition: -moz-transform 0.1s cubic-bezier(0.8, -0.33, 0.2, 1.33);
        -o-transition: -o-transform 0.1s cubic-bezier(0.8, -0.33, 0.2, 1.33);
        transition: transform 0.1s cubic-bezier(0.8, -0.33, 0.2, 1.33);
    }

    .radio input[type="radio"] {
        opacity: 0;
        z-index: 1;
    }

    .radio input[type="radio"]:focus + label::before {
        outline: thin dotted;
        outline: 5px auto -webkit-focus-ring-color;
        outline-offset: -2px;
    }

    .radio input[type="radio"]:checked + label::after {
        -webkit-transform: scale(1, 1);
        -ms-transform: scale(1, 1);
        -o-transform: scale(1, 1);
        transform: scale(1, 1);
    }

    .radio input[type="radio"]:disabled + label {
        opacity: 0.65;
    }

    .radio input[type="radio"]:disabled + label::before {
        cursor: not-allowed;
    }

    .radio.radio-inline {
        margin-top: 0;
    }

    .radio-primary input[type="radio"] + label::after {
        background-color: #428bca;
    }

    .radio-primary input[type="radio"]:checked + label::before {
        border-color: #428bca;
    }

    .radio-primary input[type="radio"]:checked + label::after {
        background-color: #428bca;
    }

    .radio-danger input[type="radio"] + label::after {
        background-color: #d9534f;
    }

    .radio-danger input[type="radio"]:checked + label::before {
        border-color: #d9534f;
    }

    .radio-danger input[type="radio"]:checked + label::after {
        background-color: #d9534f;
    }

    .radio-info input[type="radio"] + label::after {
        background-color: #5bc0de;
    }

    .radio-info input[type="radio"]:checked + label::before {
        border-color: #5bc0de;
    }

    .radio-info input[type="radio"]:checked + label::after {
        background-color: #5bc0de;
    }

    .radio-warning input[type="radio"] + label::after {
        background-color: #f0ad4e;
    }

    .radio-warning input[type="radio"]:checked + label::before {
        border-color: #f0ad4e;
    }

    .radio-warning input[type="radio"]:checked + label::after {
        background-color: #f0ad4e;
    }

    .radio-success input[type="radio"] + label::after {
        background-color: #5cb85c;
    }

    .radio-success input[type="radio"]:checked + label::before {
        border-color: #5cb85c;
    }

    .radio-success input[type="radio"]:checked + label::after {
        background-color: #5cb85c;
    }


    /*------------------------*/


    /*.col-form-label {text-align: right;}*/

    .text-white {color:#fff;}
    .no-radius {border-radius: none !important;}
    .border-grey {border: solid #eaeaea 1px;}


    .styling-select {
        border: 1px solid #ced4da !important;
        background: #fff !important;

        width: 100%;
        padding: 0.375rem 0.75rem;
        font-size: 1rem;
        line-height: 1.5;
        color: #495057;
        background-clip: padding-box;
        border-radius: 0.25rem !important;
        transition: border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out !important;

    }

    .container {max-width:750px;}



    .styling-select .btn-light:hover {
        color: #495057 !important;
        background-color: #fff !important;
        /* border-color: inherit; */
        border: none !important;
        outline: none;
    }

    .styling-select .btn-light.dropdown-toggle {
        color: #212529;
        background-color: #fff;
        border-color: #ced4da;
        border:none;

    }

    .bold {font-weight:bold;}
    .wrapper {margin:20px auto; border: 0px; max-width: 800px; background: #fff; padding: 25px 0px;}
    .wrapper .container {margin:0px auto; padding:0px;}
    .tp-text { 
        font-size: 16px;
        padding: 0px;
        background: #f8f9fa;
        min-height: 45px;
        line-height: 1em;
        display: inline-block;
        width: 100%;
        text-align: center;
        margin: 0px 0px 10px 0px;
    }
    .tp-text h4 { 

        font-size: 22px;
        padding: 0px 20px;
        margin: 12px 0px;
        /*color: #4e4e4e;*/
        color:#fff;
        font-weight: 400;
    }
    .tp-text h4 {
        /*  background-image: url('../images/banner_background2.jpg');
        background-size: cover;
        background-position: center;
        background-repeat: no-repeat;
        margin: auto auto;
        height: 100vh;
        max-height: 45px;
        width:100%;
        line-height: 2em;*/
    }
    .tp-text p{ font-size: 16px;  line-height: 22px; padding:0px 20px; margin:12px 0px; color:#212529; font-weight: 300;}


    .lead-text {background: #fff; font-size: 16px; }
    .lead-text h4 {background: #fff; font-size: 16px; padding:0px 0px; margin:12px 0px; color:#484848; font-weight: 400;}
    .lead-text p{ font-size: 16px;  line-height: 22px; padding:0px 0px; margin:12px 0px; color:#484848; font-weight: normal;}
    .col-form-label {text-align: left; font-size:14px;}
    .checkbox label, .radio label  { font-size:14px;}

    /* Medium devices (tablets, 768px and up)*/
    @media only screen and (max-width: 600px) {
        .wrapper {margin:20px; border: 0px;  padding: 25px 25px;}

        .col-form-label {text-align: left;}

    }


    @media (max-width: 767.98px) {

        .wrapper {margin:20px; border: 0px;  padding: 25px 25px;}

    }

    @media (max-width: 991.98px) {

        .wrapper {margin:20px; border: 0px;  padding: 25px 25px;}
    }

    footer p {margin-bottom:0px;}

    .main-logo {margin: 0px auto 25px auto;}



</style>

<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title><s:text name="lbl.campaign.form.html"/></title>
    </head>
    <body class="bg-emailer">
        <div class="wrapper shadow">
            <div class="container">
                <div class="row">
                    <div class="col-md-12">
                        <div class="tp-text bg-info" style="background-color: #17a2b8 !important;">
                            <h4><s:property value="campForm.mcfFormTitle"/></h4>
                        </div>
                    </div>
                </div>
                <header class="masthead">
                    <div class="container h-100">
                        <div class="row h-100 align-items-center">
                            <div class="col-12 text-center">
                                <img src='<s:property value="campForm.mcfBannerUrl"/>'/>
                            </div>
                        </div>
                    </div>
                </header>
            </div>
            <div class="container">
                <div class="container">
                    <div class="row">

                        <div class="col-md-12">
                            <div class="lead-text">
                                <s:property value="campForm.mcfFormHeader"/>
                                <h4></h4>
                                <br />
                                <div class=" py-4 bg-light p-3 border-grey">
                                    <div class="row">
                                        <div class="col-md-12 mx-auto">
                                            <div class="row">
                                                <s:iterator value="formFieldList"  status="stat">
                                                    <s:if test='(formFieldList[#stat.index].mcffFieldName)!= null'>
                                                        <div class="col-md-6">
                                                            <div class="form-group row">
                                                                <label for="inputFname" class="col-sm-4 col-form-label"><s:property value="mcffFieldName"/>
                                                                    <s:if test='(formFieldList[#stat.index].mcffFieldHint)!= null'>
                                                                        <i class="fas fa-info-circle"
                                                                           title="<s:property value="campForm.mcffFieldHint"/>"></i>
                                                                    </s:if>
                                                                    <span style="color: red">*</span>
                                                                </label>
                                                                <div class="col-sm-8">
                                                                    <input type="text" cssClass="form-control" />
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </s:if>
                                                </s:iterator>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="pull-right">
                                    <p><s:property value="campForm.mcfFormFooter"/></p>
                                    <br />
                                </div>
                                <div class="clearfix"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="container">
                <footer class="text-center border-grey">
                    <div class="container-fluid bg-light ">
                        <div class="row p-2">
                            <div class="col-12">
                                <p class="small text-secondary"><s:text name="lbl.common.copyright"/></p>
                            </div>
                        </div>
                    </div>
                </footer>
            </div>
            <!-- end of wrapper div --->
        </div>
    </body>
</html>