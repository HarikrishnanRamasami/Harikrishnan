/* 
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
"use strict";
var URL_WALLBOARD = 'https://devapi.anoudapps.com/crm/api/ameyo/wallboard';
var CHART_TOP_10_AGENTS, CHART_AGENT_CALL_DETAILS;
var THREAD_AGENTS_STATUS, THREAD_AGENT_CALL_DETAILS, THREAD_TOP10_AGENT_DETAILS, THREAD_CAROUSEL = null;
var CURRENT_SLIDE_INDEX = 1, SESSION_PUSH_SEQ_NO = 0;
var ONE_MINUTE = (1000 * 60), ONE_HOUR = ((1000 * 60) * 60);
    
function getTime(a) {
    var d = new Date();
    var n = 0;
    var m = 1000 * 60 * 6;
    if (a === 'R') {
        var ms = d.getTime();
        n = ms - m;
    } else if (a === 'F') {
        d.setHours(0, 0, 0, 0);
        n = d.getTime();
    }
    return n;
}

var OPTION_TOP_10_AGENTS = {
    backgroundColor: {
        type: 'pattern',
        itemStyle: {
            normal: {
                color: '#a01212'
            }
        }
    },
    textStyle: {
        fontSize: '20'
    },
    tooltip: {},
    grid: [{
            top: 10,
            width: '90%',
            bottom: '25%',
            left: 10,
            containLabel: true
        }],
    xAxis: [{
            type: 'value',
            max: 0, // Maximun value
            splitLine: {
                show: false
            },
            axisLabel: {
                interval: 0,
                rotate: 0,
                textStyle: {
                    fontSize: '20',
                    color: '#ffffff'
                },
            }
        }],
    yAxis: [{
            type: 'category',
            data: [], // Keys e.g.: [ravindar, singh, kiran]
            scale: true,
            axisLabel: {
                interval: 0,
                rotate: 0,
                textStyle: {
                    fontSize: '20',
                    fontWeight: 'bold',
                    color: '#ffffff'
                }
            },
            splitLine: {
                show: false,
                fontSize: 40
            }
        }],
    series: [{
            type: 'bar',
            stack: 'chart',
            z: 3,
            label: {
                normal: {
                    show: true,
                    textBorderColor: '#333',
                    textBorderWidth: 20
                }
            },
            itemStyle: {
                normal: {
                    color: '#5c8205f7',
                    show: true
                }
            },
            data: [] // Values e.g.: [24, 10, 35]
        }]
};

var LABEL_ANSWERED = {
    normal: {
        color: '#4cd857'
    }
};
var LABEL_ABANDONED = {
    normal: {
        color: '#a01212'
    },
};
var OPTION_AGENT_CALL_DETAILS = {
    tooltip: {
        trigger: 'item',
        formatter: "{a} <br/>{b}: {c} ({d}%)"
    },
    legend: {
        orient: 'vertical',
        x: 'left',
        color: 'solid white',
        data: [],
        textStyle: {
            fontSize: '20',
            fontWeight: 'bold',
            color: '#ffffff'
        }
    },
    series: [
        {
            type: 'pie',
            radius: ['50%', '70%'],
            avoidLabelOverlap: false,
            label: {
                normal: {
                    show: true,
                    textStyle: {
                        fontSize: '30',
                        fontWeight: 'bolder'
                    }
                },
                emphasis: {
                    show: true,
                    textStyle: {
                        fontSize: '30',
                        fontWeight: 'bolder'
                    }
                }
            },
            labelLine: {
                normal: {
                    show: true,
                    textStyle: {
                        fontSize: '50',
                        fontWeight: 'bolder'
                    }
                }
            },
            data: []
        }
    ]
};
    
function clearThread() {
    clearInterval(THREAD_AGENTS_STATUS);
    clearInterval(THREAD_TOP10_AGENT_DETAILS);
    clearInterval(THREAD_AGENT_CALL_DETAILS);
}

function startThread() {
    loadTop10AgentDetails();
    loadAgentCallDetails();
    loadAgentsStatus();

    THREAD_AGENTS_STATUS = setInterval(function() {
        loadAgentsStatus();
    }, (1000 * 60 * 5));

    THREAD_TOP10_AGENT_DETAILS = setInterval(function() {
        loadAgentCallDetails();
    }, (1000 * 60 * 5));

    THREAD_AGENT_CALL_DETAILS = setInterval(function() {
        loadTop10AgentDetails();
    }, (1000 * 60 * 5));
}

function pingSession() {
    $.ajax({
        type: "GET",
        async: true,
        dataType: "json",
        url: URL_WALLBOARD,
        data: {command: "ping-session", data: '{"sessionId": "' + WALLBOARD_DATA.sessionId + '", "sessionPushSeqNo": "' + SESSION_PUSH_SEQ_NO + '"}'},
        success: function (result) {
            console.log(JSON.stringify(result));
            if(result.status && result.status === "error") {
                window.location.href = APP_CONFIG.context + "/wallboardLogin.do";
            }
        },
        complete: function () {
            SESSION_PUSH_SEQ_NO++;
        }
    });
}

function wallboardLogout() {
    $.ajax({
        type: "GET",
        async: true,
        dataType: "json",
        url: URL_WALLBOARD,
        data: {command: "logout", data: '{"sessionId": "' + WALLBOARD_DATA.sessionId + '"}'},
        success: function (result) {
            console.log(JSON.stringify(result));
            window.location.href = APP_CONFIG.context + "/wallboardLogin.do";
        }
    });
}