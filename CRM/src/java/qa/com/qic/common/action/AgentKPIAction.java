/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.common.action;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import qa.com.qic.anoud.dao.AnoudDAO;
import qa.com.qic.anoud.vo.Customer;
import qa.com.qic.anoud.vo.KeyValue;
import qa.com.qic.common.dao.AgentKPIDAO;
import qa.com.qic.common.dao.CommonDAO;
import qa.com.qic.common.util.ApplicationConstants;
import qa.com.qic.common.util.TypeConstants;
import qa.com.qic.common.util.TypeConstants.DateRange;
import qa.com.qic.common.vo.UserInfo;
import qa.com.qic.utility.helpers.LogUtil;

/**
 *
 * @author karthik.j
 */
public class AgentKPIAction extends ActionSupport implements SessionAware, ServletRequestAware {

    private static final Logger LOGGER = LogUtil.getLogger(AgentKPIAction.class);


    private transient HttpServletRequest request;
    private transient Map<String, Object> session;

    private transient CommonDAO commonDao;
    private transient AnoudDAO anoudDao;
    private transient AgentKPIDAO dao;

	private List<KeyValue> agentList;
    private String agent;

	private List<KeyValue> productList;
	private List<KeyValue> dateRangeList;
	private transient DateRange dateRange;
    private List<? extends Object> aaData;
    private transient UserInfo userInfo;
    private transient Customer customer;
    private String message;
    private String messageType;
    private String company;
    private String operation;
    private String viewMode;
    private String summaryType;
    private String chartType;
    private String origin;
    private String flex1;
    private String flex2;
    private String flex3;


    public String openAgentKPIDetails() {
        return SUCCESS;

    }
    public String openAgentKPIDashBoardView() {
    	commonDao = new CommonDAO(getCompany());
        KeyValue kv = null;
        dateRangeList = new LinkedList<>();
        for (TypeConstants.DateRange d : TypeConstants.DateRange.values()) {
            kv = new KeyValue(d.name(), d.getDesc());
            dateRangeList.add(kv);
        }
        dateRangeList = dateRangeList.subList(4, 9);
        TypeConstants.DateRange start = TypeConstants.DateRange.THIS_MONTH;
        setDateRange(start );
        agentList = commonDao.loadCrmAgentList(getUserInfo().getUserId());
        if (null != agentList) {
        	agentList.add(0, new KeyValue("ALL", "All"));
        }

        setAgent(getUserInfo().getUserId());

        return "agent_kpi_dashboard";

    }


    public String loadAgentKPISummaryData(){

    	dao = new AgentKPIDAO(getCompany());
    	setAaData(dao.loadAgentKPISummaryData(getSummaryType(),getAgent(),getDateRange()));
    	return "agent_kpi_data";
    }

    public String loadAgentKPIChartData(){

    	dao = new AgentKPIDAO(getCompany());
    	setAaData(dao.loadAgentKPIChartData(getChartType(),getAgent(),getDateRange()));
    	return "agent_kpi_data";
    }


	public Customer getCustomer() {
	      return customer;
	}

    public void setCustomer(Customer customer) {
	      this.customer = customer;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public Map<String, Object> getSession() {
		return session;
	}



	public CommonDAO getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(CommonDAO commonDao) {
		this.commonDao = commonDao;
	}

	public AnoudDAO getAnoudDao() {
		return anoudDao;
	}

	public void setAnoudDao(AnoudDAO anoudDao) {
		this.anoudDao = anoudDao;
	}






	public AgentKPIDAO getDao() {
		return dao;
	}













	public void setDao(AgentKPIDAO dao) {
		this.dao = dao;
	}













	public List<KeyValue> getAgentList() {
		return agentList;
	}

	public void setAgentList(List<KeyValue> agentList) {
		this.agentList = agentList;
	}

	public String getAgent() {
		return agent;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}
	public List<KeyValue> getProductList() {
		return productList;
	}


	public void setProductList(List<KeyValue> productList) {
		this.productList = productList;
	}


	public List<KeyValue> getDateRangeList() {
		return dateRangeList;
	}

	public void setDateRangeList(List<KeyValue> dateRangeList) {
		this.dateRangeList = dateRangeList;
	}



	public DateRange getDateRange() {
		return dateRange;
	}
	public void setDateRange(DateRange dateRange) {
		this.dateRange = dateRange;
	}
	public List<? extends Object> getAaData() {
		return aaData;
	}

	public void setAaData(List<? extends Object> aaData) {
		this.aaData = aaData;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}


	public String getViewMode() {
		return viewMode;
	}

	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
	}

	public String getSummaryType() {
		return summaryType;
	}
	public void setSummaryType(String summaryType) {
		this.summaryType = summaryType;
	}
	public String getChartType() {
		return chartType;
	}
	public void setChartType(String chartType) {
		this.chartType = chartType;
	}
	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getFlex1() {
		return flex1;
	}

	public void setFlex1(String flex1) {
		this.flex1 = flex1;
	}

	public String getFlex2() {
		return flex2;
	}

	public void setFlex2(String flex2) {
		this.flex2 = flex2;
	}

	public String getFlex3() {
		return flex3;
	}

	public void setFlex3(String flex3) {
		this.flex3 = flex3;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}



	@Override
	public void setSession(Map<String, Object> session) {
	      this.session = session;
	      setCompany((String) session.get(ApplicationConstants.SESSION_NAME_COMPANY_CODE));
	      this.userInfo = (UserInfo) session.get(ApplicationConstants.SESSION_NAME_USER_INFO);
	}

    @Override
	public void setServletRequest(HttpServletRequest request) {
	      this.request = request;
	}


}
