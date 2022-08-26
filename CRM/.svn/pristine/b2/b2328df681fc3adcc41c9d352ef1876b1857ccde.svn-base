<%@page import="java.util.HashMap"%>
<!DOCTYPE html>
<%@page contentType="text/html" import="java.util.Map, java.util.HashMap, java.util.Iterator" pageEncoding="UTF-8"%>
<link href="<%=request.getContextPath()%>/css/campaignFlowchart.css" rel="stylesheet" />
<div class="col-md-12 right-pad" id="block_body">
    <div class="dash-leads" style="border-top:0!important">
        <div class="mermaid">
            stateDiagram
            SendMail: Send Mail
            splitYn: SplitY/N
            SplitYes: Yes
            SplitNo: NO
            template1: Template1
            template2: Template2
            WaitFreq: Waiting Freq
            splitPerc: Split Perc
            [*] --> Start
            Start --> Motor
            Start --> Travel
            Motor --> SendMail
            SendMail --> splitYn
            splitYn-->SplitYes
            splitYn-->SplitNo
            SplitNo-->template1
            template1-->sendMail
            SplitYes-->splitPerc
            splitPerc-->template2
            template2-->sendMail
            sendMail-->Wait:2 Days
            Wait --> WaitFreq
            WaitFreq-->MailOpen
            MailOpen --> MailBounced
            MailBounced --> MailClicked
            MailClicked-->MailConverted
            MailConverted-->SendMobileNotification
            SendMobileNotification-->sendSms
            sendSms-->End
            Travel --> SendMail2
            SendMail2 --> Wait2 
            Wait2 --> MailOpen2
            MailOpen2 -->MailBounced1
            MailBounced1-->MailClicked1
            MailClicked1-->MailConverted1
            MailConverted1-->SendMobileNotification1
            SendMobileNotification1-->sendSms1
            sendSms1-->End
            
        </div>
    </div>
</div>
<%
                HashMap params = (HashMap)request.getAttribute("FLOW_CHART_DATA");
                out.print(params);
%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/mermaid/8.4.3/mermaid.min.js"></script>

<script>
    $(document).ready(function () {
        mermaid.initialize({startOnLoad: true, theme: null,
            state: {
                dividerMargin: 10,
                sizeUnit: 10,
                padding: 8,
                textHeight: 5,
                titleShift: -15,
                noteMargin: 10,
                forkWidth: 20,
                forkHeight: 7,
                // Used
                miniPadding: 2,
                // Font size factor, this is used to guess the width of the edges labels before rendering by dagre
                // layout. This might need updating if/when switching font
                fontSizeFactor: 5.02,
                fontSize: 10,
                labelHeight: 10,
                edgeLengthFactor: '20',
                compositTitleSize: 35,
                radius: 5
            }
        }
        );
    });
</script>