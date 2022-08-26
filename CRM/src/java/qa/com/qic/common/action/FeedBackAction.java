/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.common.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import qa.com.qic.anoud.config.FieldConstants;
import qa.com.qic.anoud.dao.AnoudDAO;
import qa.com.qic.anoud.vo.Customer;
import qa.com.qic.anoud.vo.FeedBackInfoBO;
import qa.com.qic.anoud.vo.KeyValue;
import qa.com.qic.common.dao.FeedBackDAO;
import qa.com.qic.utility.helpers.LogUtil;

/**
 *
 * @author ravindar.singh
 */
public class FeedBackAction extends ActionSupport implements SessionAware, ServletRequestAware {

    private static final Logger logger = LogUtil.getLogger(FeedBackAction.class);
    private HttpServletRequest request;
    private Map<String, Object> session;
    private String formId;
    private String userName;
    private String custId;
    private String emailId;
    private String mobileNo;
    private String refNo;
    private String suggestions;
    private String remarks;
    private int maxColSize;
    private List<String> questions = null;
    private String errorMsg;
    private String successMsg;
    private InputStream inputStream;
    private String company;
    private transient List<KeyValue> keyValueList;
    private List<? extends Object> aaData;
    private Customer customer;
    public int getMaxColSize() {
        return maxColSize;
    }

    public void setMaxColSize(int maxColSize) {
        this.maxColSize = maxColSize;
    }

    public String openFeedback() {
        String navigation = INPUT;
        FeedBackDAO dao = new FeedBackDAO(company);
        AnoudDAO anoudao = new AnoudDAO(company);
        try {
            setRefNo(customer.getCivilId());
            setCustId(customer.getCivilId());
            setEmailId(customer.getEmailId());
            setUserName(customer.getName());
            setMobileNo(customer.getMobileNo());
            setKeyValueList(anoudao.getMAppCodes(FieldConstants.AppCodes.FEEDBACK_TYPE));
            navigation = "insured";
        } catch (Exception e) {
            logger.error("", e);
            setInputStream(new ByteArrayInputStream(("").getBytes()));
        }
        return navigation;
    }

    public String showQIFeedback(){
           String navigation = INPUT;
           FeedBackDAO dao = new FeedBackDAO(company);
           List<qa.com.qic.anoud.vo.FeedBackInfoBO> feedbackList = dao.getFeedbackList(formId, getCompany());

           if (feedbackList == null || feedbackList.isEmpty()) {
                setInputStream(new ByteArrayInputStream(("").getBytes()));
            } else {
                request.setAttribute("feedbackList", feedbackList);
                navigation = "question";
            }
        return navigation;
    }

    public String loadListFeedback() {
        FeedBackDAO dao = new FeedBackDAO(company);
        try {
            List<KeyValue> list = dao.loadFeedBackList(getCustId());
            setAaData(list);

        } catch (Exception e) {
        }
        return "data";
    }

    public String submitFeedback() {
        List<FeedBackInfoBO> feedbackList = new ArrayList<>();
        FeedBackDAO dao = new FeedBackDAO(company);
        String answer = null;
        if (getFormId() == null || "".equals(getFormId())) {
            errorMsg = "Some parameters are missing";
        } else {
            for (String question : questions) {
                answer = request.getParameter("q_opt_" + question);
                if (answer == null || "".equals(answer.trim())) {
                    continue;
                }
                FeedBackInfoBO fb = new FeedBackInfoBO();
                fb.setQuestion(question);
                fb.setId(answer);
                feedbackList.add(fb);
                logger.info("question:" + question + " : " + request.getParameter("q_opt_" + question));
            }
            if (feedbackList.isEmpty() && (suggestions == null || "".equals(suggestions.trim()))) {
                errorMsg = "There is no answer";
            } else {
                int status = dao.submitFeedback(formId, userName, custId, refNo, emailId, mobileNo, suggestions, remarks, feedbackList, getCompany());
                if (status == 1) {
                    successMsg = "Thanks for your feedback";
                } else {
                    errorMsg = "Unable to process your request";
                }
            }
        }
        return "success_json";
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public String getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(String suggestions) {
        this.suggestions = suggestions;
    }

    public List<String> getQuestions() {
        return questions;
    }

    public void setQuestions(List<String> questions) {
        this.questions = questions;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getSuccessMsg() {
        return successMsg;
    }

    public void setSuccessMsg(String successMsg) {
        this.successMsg = successMsg;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public List<KeyValue> getKeyValueList() {
        return keyValueList;
    }

    public void setKeyValueList(List<KeyValue> keyValueList) {
        this.keyValueList = keyValueList;
    }

    public List<? extends Object> getAaData() {
        return aaData;
    }

    public void setAaData(List<? extends Object> aaData) {
        this.aaData = aaData;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }



}
