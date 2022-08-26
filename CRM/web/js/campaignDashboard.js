/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {
    
});


function loadCampaignBouncedCount(mcCampId) {
        //campaign_action.clear();
        $.getJSON(APP_CONFIG.context + "/camp/loadCampaignBouncedCount.do", {"mcCampId": mcCampId}, function () {
        }).done(function (result) {
            loadBouncedCount(result);
        }).fail(function () {

        }).always(function () {

        });
    }
function loadBouncedCount(result) {
        var Totalratio = [], sent = [], pending = [], targAmt = [], bounceRatio = [], bounced = [], opened = [], openedRatio = [], uniqueOpened = [], uniqueOpenRatio = [], clicked = [], clickedRatio = [], uniqueClicked = [], uniqueClickedRatio = [], convertedAmt = [], converted = [], convertedRatio = [], unsubscribed = [], acheiveRatio = [], unSub = [], unSubRatio = [];
        if (result.aaData && result.aaData.length === 1) {
            let o = result.aaData[0];
            sent.push(Number(o.sent === null ? "0" : o.sent));
            pending.push(Number(o.pending === null ? "0" : o.pending));
            bounced.push(Number(o.bounced === null ? "0" : o.bounced));
            bounceRatio.push(Number(o.bounceRatio === null ? "0" : o.bounceRatio));
            opened.push(Number(o.opened === null ? "0" : o.opened));
            openedRatio.push(Number(o.openedRatio === null ? "0" : o.openedRatio));
            uniqueOpened.push(Number(o.uniqueOpened === null ? "0" : o.uniqueOpened));
            uniqueOpenRatio.push(Number(o.uniqueOpenRatio === null ? "0" : o.uniqueOpenRatio));
            clicked.push(Number(o.clicked === null ? "0" : o.clicked));
            clickedRatio.push(Number(o.clickedRatio === null ? "0" : o.clickedRatio));
            uniqueClicked.push(Number(o.uniqueClicked === null ? "0" : o.uniqueClicked));
            uniqueClickedRatio.push(Number(o.uniqueClickedRatio === null ? "0" : o.uniqueClickedRatio));
            converted.push(Number(o.converted === null ? "0" : o.converted));
            convertedRatio.push(Number(o.convertedRatio === null ? "0" : o.convertedRatio));
            targAmt.push(Number(o.targAmt).toLocaleString());
            convertedAmt.push(Number(o.convertedAmt) === null ? "0" : o.convertedAmt);
            acheiveRatio.push(Number(o.acheiveRatio === null ? "0" : o.acheiveRatio));
            unSub.push(Number(o.unSub === null ? "0" : o.unSub));
            unSubRatio.push(Number(o.unSubRatio === null ? "0" : o.unSubRatio));
            Totalratio.push((Number(100)));
        }

        var a = {normal: {label: {show: !0, position: "center",
                    formatter: "{b}\n", textStyle: {baseline: "middle", fontWeight: 300, fontSize: 15, color: "#0d5088"}},
                labelLine: {show: !1}}},
        l = {normal: {color: "#cccccc", label: {show: !0, position: "center", textStyle: {baseline: "middle"}},
                labelLine: {show: !1}}}, n = [45, 60];
        
        var r = {normal: {label: {show: !0, position: "center",
                    formatter: "{b}\n", textStyle: {baseline: "middle", fontWeight: 300, fontSize: 15, color: "#f50202"}},
                labelLine: {show: !1}}};

        var campaign_sent_options = {
            title: [
                {
                    text: 'Pending: ' + pending,
                    x: 'center',
                    y: '80%',
                    textStyle: {
                        fontWeight: 'normal',
                        fontSize: '13',
                        color: '#484848',
                        lineHeight: 'normal'

                    },
                    subtextStyle: {
                        fontWeight: 'normal',
                        fontSize: '13',
                        color: '#484848',
                        lineHeight: 'normal'

                    }
                }
            ],
            legend: {x: "center", y: "56%",
                data: [""]},
            color: ["#4caf50"],
            series: [
                {
                    name: 'bounced',
                    type: "pie",
                    x: '20%',
                    y: '78%',
                    radius: n,
                    itemStyle: {normal: {label: {textStyle: {baseline: "middle", fontWeight: 300, fontSize: 15, color: "#0d5088"}, formatter: function (e) {
                                    return"\n\n" + sent;
                                }}}},
                    data: [
                        {name: "" + sent, value: pending[0], itemStyle: l},
                        {name: (sent[0] / (sent[0] + pending[0]) * 100) + "%", value: sent[0], itemStyle: a}]
                }
            ]
        };
        campaign_sent.setOption(campaign_sent_options, true);
        var campaign_bounced_options = {
            title: [
                {
                    text: '',
                    x: 'center',
                    y: '80%',
                    textStyle: {
                        fontWeight: 'normal',
                        fontSize: '13',
                        color: '#484848',
                        lineHeight: 'normal'

                    },
                    subtextStyle: {
                        fontWeight: 'normal',
                        fontSize: '13',
                        color: '#484848',
                        lineHeight: 'normal'

                    }
                }
            ],
            legend: {x: "center", y: "56%",
                data: [""]},
            color: ["#e80000"],
            series: [
                {
                    name: 'bounced',
                    type: "pie",
                    x: '20%',
                    y: '78%',
                    radius: n,
                    itemStyle: {normal: {label: {textStyle: {baseline: "middle", fontWeight: 300, fontSize: 15, color: "#0d5088"}, formatter: function (e) {
                                    return"\n\n" + bounced;
                                }}}},
                    data: [{name: "", value: sent - bounced, itemStyle: l},
                        {name: bounceRatio + "%", value: bounced, itemStyle: r}]
                }
            ]
        };
        campaign_bounced.setOption(campaign_bounced_options, true);
        var campaign_opened_options = {
            title: [
                {
                    text: 'Total Opens: ' +  opened,
                    subtext: 'Open Ratio: ' + openedRatio + "%",
                    x: 'center',
                    y: '80%',
                    textStyle: {
                        fontWeight: 'normal',
                        fontSize: '13',
                        color: '#484848',
                        lineHeight: 'normal'

                    },
                    subtextStyle: {
                        fontWeight: 'normal',
                        fontSize: '13',
                        color: '#484848',
                        lineHeight: 'normal'

                    }
                }
            ],
            legend: {x: "center", y: "56%",
                data: [""]},
            color: ["#0b40a2"],
            series: [
                {
                    name: 'opened',
                    type: "pie",
                    x: '7%',
                    y: '78%',
                    radius: n,
                    itemStyle: {normal: {label: {textStyle: {baseline: "middle", fontWeight: 300, fontSize: 15, color: "#0d5088"}, formatter: function (e) {
                                    return"\n\n" + uniqueOpened;
                                }}}},
                    data: [{name: "", value: (sent - uniqueOpened), itemStyle: l},
                        {name: uniqueOpenRatio + "%", value: uniqueOpened, itemStyle: a}]
                }
            ]
        };
        campaign_opened.setOption(campaign_opened_options, true);
        var campaign_clicked_options = {
            title: [
                {
                    text: 'Total Clicks: ' + clicked,
                    subtext: 'Clicks Ratio: ' + clickedRatio + "%",
                    x: 'center',
                    y: '80%',
                    textStyle: {
                        fontWeight: 'normal',
                        fontSize: '13',
                        color: '#484848',
                        lineHeight: 'normal'

                    },
                    subtextStyle: {
                        fontWeight: 'normal',
                        fontSize: '13',
                        color: '#484848',
                        lineHeight: 'normal'

                    }
                }
            ],
            legend: {x: "center", y: "56%",
                data: [""]},
            color: ["#009688"],
            series: [
                {
                    name: 'clicked',
                    type: "pie",
                    x: '7%',
                    y: '78%',
                    radius: n,
                    itemStyle: {normal: {label: {textStyle: {baseline: "middle", fontWeight: 300, fontSize: 15, color: "#0d5088"}, formatter: function (e) {
                                    return"\n\n" + uniqueClicked;
                                }}}},
                    data: [{name: "", value: sent, itemStyle: l},
                        {name: uniqueClickedRatio + "%", value: uniqueClicked, itemStyle: a}]
                },
            ]
        };
        campaign_clicked.setOption(campaign_clicked_options, true);
        var campaign_converted_options = {
            title: [
                {
                    subtext: 'Target Amt: ' + targAmt,
                    text: 'Converted Amt: ' + convertedAmt,
                    x: 'center',
                    y: '80%',
                    textStyle: {
                        fontWeight: 'normal',
                        fontSize: '13',
                        color: '#484848',
                        lineHeight: 'normal'

                    },
                    subtextStyle: {
                        fontWeight: 'normal',
                        fontSize: '13',
                        color: '#484848',
                        lineHeight: 'normal'

                    }
                }
            ],
            legend: {x: "center", y: "56%",
                data: [""]},
            color: ["#ff9800"],
            series: [
                {
                    name: 'converted',
                    type: "pie",
                    x: '70%',
                    y: '78%',
                    radius: n,
                    color: ["#ff9800"],
                    itemStyle: {normal: {label: {textStyle: {baseline: "middle", fontWeight: 300, fontSize: 15, color: "#0d5088"}, formatter: function (e) {
                                    return"\n\n" + converted;
                                }}}},
                    data: [{name: "", value: (sent - converted), itemStyle: l},
                        {name: convertedRatio + "%", value: converted, itemStyle: a}]
                },
            ]
        };
        campaign_converted.setOption(campaign_converted_options, true);
        var campaign_unsubscribed_options = {
            title: [
                {
                    text: '',
                    x: 'center',
                    y: '80%',
                    textStyle: {
                        fontWeight: 'normal',
                        fontSize: '13',
                        color: '#484848',
                        lineHeight: 'normal'
                    },
                    subtextStyle: {
                        fontWeight: 'normal',
                        fontSize: '13',
                        color: '#484848',
                        lineHeight: 'normal'

                    }
                }
            ],
            legend: {x: "center", y: "56%",
                data: [""]},
            color: ["#f44336"],
            series: [
                {
                    name: 'unsubscribed',
                    type: "pie",
                    x: '85%',
                    y: '78%',
                    radius: n,
                    itemStyle: {normal: {label: {textStyle: {baseline: "middle", fontWeight: 300, fontSize: 15, color: "#0d5088"}, formatter: function (e) {
                                    return"\n\n" + unSub;
                                }}}},
                    data: [{name: "", value: (sent - unSub), itemStyle: l},
                        {name: unSubRatio + "%", value: unSub, itemStyle: a}]
                }
            ]
        };
        campaign_unsubscribed.setOption(campaign_unsubscribed_options, true);
        window.onresize = function () {
            setTimeout(function () {
                campaign_sent.resize();
                campaign_bounced.resize();
                campaign_opened.resize();
                campaign_clicked.resize();
                campaign_converted.resize();
                campaign_unsubscribed.resize();
            }, 200);
        }

        setTimeout(function () {
            campaign_sent.resize();
            campaign_bounced.resize();
            campaign_opened.resize();
            campaign_clicked.resize();
            campaign_converted.resize();
            campaign_unsubscribed.resize();
        }, 1000);
    }
   