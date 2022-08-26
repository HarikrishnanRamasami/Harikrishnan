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

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import qa.com.qic.anoud.config.FieldConstants;
import qa.com.qic.anoud.dao.AnoudDAO;
import qa.com.qic.anoud.vo.Activity;
import qa.com.qic.anoud.vo.Customer;
import qa.com.qic.anoud.vo.KeyValue;
import qa.com.qic.common.dao.CommonDAO;
import qa.com.qic.common.dao.DealsDAO;
import qa.com.qic.common.util.ApplicationConstants;
import qa.com.qic.common.util.TypeConstants;
import qa.com.qic.common.util.TypeConstants.DateFilter;
import qa.com.qic.common.vo.CrmDeal;
import qa.com.qic.common.vo.CrmDealEnquiry;
import qa.com.qic.common.vo.CrmDealPipeline;
import qa.com.qic.common.vo.UserInfo;
import qa.com.qic.utility.helpers.LogUtil;

/**
 *
 * @author karthik.j
 */
public class DealsAction extends ActionSupport implements SessionAware, ServletRequestAware {

    private static final Logger LOGGER = LogUtil.getLogger(DealsAction.class);


    private transient HttpServletRequest request;
    private transient Map<String, Object> session;

    private transient CommonDAO commonDao;
    private transient AnoudDAO anoudDao;
    private transient DealsDAO dao;

    private transient List<KeyValue> stageData;
    private transient List<CrmDealPipeline> plData;

    private transient List<KeyValue> dealActivityData;
    private transient List<KeyValue> dealPastActivityData;
    private transient List<KeyValue> dealFutureActivityData;
	private transient List<CrmDealPipeline> cdPipelineList;
	private transient List<KeyValue> pipelineList;
    private transient List<KeyValue> cdContactTypList;
    private transient List<KeyValue> cdContactList;
    private transient List<KeyValue> cdEnquiryList;
    private transient List<KeyValue> cdQuoteList;
    private transient List<KeyValue> cdPolicyList;
    private transient List<KeyValue> cdLostReasonList;

	private List<KeyValue> agentList;
	private List<KeyValue> productList;
	private List<KeyValue> quoteModeList;
	private List<KeyValue> quoteTypeList;
	private List<KeyValue> underWriterList;
	private List<KeyValue> dateRangeList;


    private List<? extends Object> aaData;

    private transient UserInfo userInfo;
    private transient Customer customer;
    private transient Activity activity;
    private transient CrmDeal crmDeal;
    private transient CrmDealPipeline crmDealPl;
    private transient CrmDealEnquiry crmDealEnquiry;


    private String message;
    private String messageType;
    private String company;
    private String baseCurrency;
    private String operation;
    private String dealClose;
    private String viewMode;
    private String origin;
    private String flex1;
    private String flex2;
    private String flex3;


    public String openDealDetails() {
        return SUCCESS;

    }


    public String openDealsForm() {

  	    setDao(new DealsDAO(getCompany()));
        anoudDao = new AnoudDAO(getCompany());
        commonDao = new CommonDAO(getCompany());
        cdContactTypList =  anoudDao.getMAppCodes(FieldConstants.AppCodes.CRM_CONTACT_TYPE);
        cdLostReasonList = anoudDao.getMAppCodes(FieldConstants.AppCodes.CRM_DEAL_LOST_REASON);
        pipelineList =  dao.loadDealPipelineList(true);
        agentList = commonDao.loadCrmAgentList(getUserInfo().getUserId());
        productList = anoudDao.getProducts();
        setBaseCurrency(dao.getBaseCurrShortForCompany(getCompany()));
        if ("add".equals(getOperation())) {
        	crmDeal.setOperation(getOperation());
            if(crmDeal.getCdPlId() == null)
               crmDeal.setCdPlId(ApplicationConstants.DEALS_DEFAULT_PIPELINE);
            if(StringUtils.isBlank(crmDeal.getCdCrUid()))
               crmDeal.setCdCrUid(getUserInfo().getUserId());
        }else if("edit".equals(getOperation())){
        	crmDeal = dao.loadDealById(getCompany(), crmDeal);
        	crmDeal.setOperation(getOperation());
        }else if("delete".equals(getOperation())){
        	crmDeal = dao.loadDealById(getCompany(), crmDeal);
        	crmDeal.setOperation(getOperation());
        }


 	    stageData = dao.loadStageData(crmDeal, getCompany());
        return "deals_form";
   }

    public String openDealsEmailForm() {
        setActivity(new Activity());
        getActivity().setCivilId(getCustomer().getCivilId());
        getActivity().setTo(getCustomer().getEmailId());
        getActivity().setDealId(crmDeal.getCdDealId().toString());
        return "deals_email_form";
    }

    public String openDealsPipelineView(){
  	    setDao(new DealsDAO(getCompany()));
  	    setViewMode("list");

  	    if(StringUtils.equals("add", crmDealPl.getOperation())){
  	    	cdPipelineList = dao.loadTemplatePipelIne();
  	    }else if(StringUtils.equals("edit", crmDealPl.getOperation())){
  	    	cdPipelineList = dao.loadPipelineById(crmDealPl.getCdpPlId());
  	    	crmDealPl.setCdpPlName(cdPipelineList.get(0).getCdpPlName());
  	    }


        return "deals_pipeline";
   }

    public String openDealsListView() {

        commonDao = new CommonDAO(getCompany());
  	    setDao(new DealsDAO(getCompany()));
  	    pipelineList = dao.loadDealPipelineList(false);

  	    if(crmDeal.getCdPlId() == null)
  	    	crmDeal.setCdPlId(ApplicationConstants.DEALS_DEFAULT_PIPELINE);

        if(StringUtils.isBlank(crmDeal.getCdCrUid()))
        		crmDeal.setCdCrUid(getUserInfo().getUserId());

        stageData = dao.loadStageData(crmDeal,getCompany());
        crmDeal.setCdPipelineName(stageData.get(0).getText());
  	    if(StringUtils.isBlank(getViewMode()))
  	    setViewMode("list");

        KeyValue kv = null;
        dateRangeList = new LinkedList<>();
        for (TypeConstants.DateRange d : TypeConstants.DateRange.values()) {
            kv = new KeyValue(d.name(), d.getDesc());
            dateRangeList.add(kv);
        }

        agentList = commonDao.loadCrmAgentList(getUserInfo().getUserId());
        if (null != agentList) {
        	agentList.add(0, new KeyValue("ALL", "All"));
        }


        return "deals_list";

    }


    public String openDealsDashBoardView() {

        KeyValue kv = null;
        dateRangeList = new LinkedList<>();
        for (TypeConstants.DateRange d : TypeConstants.DateRange.values()) {
            kv = new KeyValue(d.name(), d.getDesc());
            dateRangeList.add(kv);
        }

        TypeConstants.DateRange start = TypeConstants.DateRange.THIS_YEAR;
        setCrmDeal(new CrmDeal());
        crmDeal.setCdDateRange(start );

        return "deals_dash_board";
    }

    public String openDealsTimelineView() {

	    dao = new DealsDAO(getCompany());
	    setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
	    crmDeal = dao.loadDealById(getCompany(), crmDeal);
	    crmDeal.setShowClosed(true);
        if(StringUtils.isBlank(crmDeal.getCdCrUid()))
    		crmDeal.setCdCrUid(getUserInfo().getUserId());
	    stageData = dao.loadStageData(crmDeal, getCompany());
	    dealActivityData = dao.loadDealsActivityList(  crmDeal.getCdDealId().toString() , DateFilter.CURRENT.getCode());
	    dealPastActivityData = dao.loadDealsActivityList(  crmDeal.getCdDealId().toString() , DateFilter.PAST.getCode());
	    dealFutureActivityData = dao.loadDealsActivityList(  crmDeal.getCdDealId().toString() , DateFilter.FUTURE.getCode());

	   // useProto();

        return "deals_timeline";
    }

    public String openDealsEnquiryForm() {

	    setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);

	    dao = new DealsDAO(getCompany());
	    anoudDao = new AnoudDAO(crmDeal.getCdCrmId());
	    quoteModeList = anoudDao.getMAppParameter(FieldConstants.AppParameter.CORP_QUOT_MODE);
	    underWriterList = dao.getUnderWriterList(crmDeal.getCdCrmId(), getUserInfo().getUserId());
	    crmDeal = dao.loadDealById(getCompany(), crmDeal);
	 	   if(crmDeal.getCdEnqNo() != null){
               setMessageType(ApplicationConstants.MESSAGE_TYPE_RECORD_EXISTS);
               return "data";
 	   }else{
 		    setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
 		    crmDeal.setShowClosed(true);
 		    setCrmDealEnquiry(new CrmDealEnquiry());
 		    crmDealEnquiry.setCustName(crmDeal.getCdCustName());
 		    crmDealEnquiry.setAssrName(crmDeal.getCdCustName());
 		    crmDealEnquiry.setReceivedBy(getUserInfo().getUserId());
 		    crmDealEnquiry.setCrmId(crmDeal.getCdCrmId());
 		    crmDealEnquiry.setDealId(crmDeal.getCdDealId());
 		    crmDealEnquiry.setQuoteType(crmDeal.getCdProdCode());
 		    crmDealEnquiry.setQuoteLOB(crmDeal.getCdProdLob());
 	        return "deals_enquiry";
 	   }



    }




    public String saveDealsForm() {

      	    dao = new DealsDAO(getCompany());
            setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);

          try {

        	  if(StringUtils.equals("add", crmDeal.getOperation()))
        	        crmDeal.setCdCrUid(getUserInfo().getUserId());
    	       else if(StringUtils.equals("edit", crmDeal.getOperation()))
    	    	   crmDeal.setCdUpdUid(getUserInfo().getUserId());

    	       int recCount = dao.saveDealDetail(crmDeal, crmDeal.getOperation());
               if (recCount == 1) {
                   setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
                   crmDeal = dao.loadDealById(getCompany(), crmDeal);
               }

    		} catch (Exception e) {
                LOGGER.error("Exception saving deal => {}", e);
                setMessage("Error occurred during save operation. Please contact IT support.");
    		}

            return "deals_data";

    }

    public String saveDealsPipeLineForm(){

  	    dao = new DealsDAO(getCompany());
        setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
        List<CrmDealPipeline> stageList = new LinkedList<>();


        for(CrmDealPipeline record: cdPipelineList){

         record.setCdpPlId(crmDealPl.getCdpPlId());
          record.setCdpPlName(crmDealPl.getCdpPlName());
       	  if(StringUtils.equals("add", crmDealPl.getOperation()))
          	 record.setCdpCrUid(getUserInfo().getUserId());
   	       else if(StringUtils.equals("edit", crmDealPl.getOperation())){
   	    	 record.setCdpPlId(crmDealPl.getCdpPlId());
             record.setCdpUpUid(getUserInfo().getUserId());
   	       }
          stageList.add(record);
       }

        try {
          	 KeyValue result = dao.saveDealPipeline(stageList ,crmDealPl.getOperation());

             if (Integer.parseInt(result.getValue()) >= 1) {
           	  if(StringUtils.equals("add", crmDealPl.getOperation()))
           		 setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
           	 else if(StringUtils.equals("edit", crmDealPl.getOperation()))
                 setMessageType(ApplicationConstants.MESSAGE_TYPE_EDIT_SUCCESS);
         	 else if(StringUtils.equals("delete", crmDealPl.getOperation()))
                 setMessageType(ApplicationConstants.MESSAGE_TYPE_DELETE_SUCCESS);
             }else{
            	 setMessage("Error occurred during operation. Please contact IT support.");
             }

  		} catch (Exception e) {
              LOGGER.error("Exception saving deal pipeline => {}", e);
              setMessage("Error occurred during  operation. Please contact IT support.");
  		}

    	   return "deals_data";
    }

    public String saveDealsEmailForm() {
        commonDao = new CommonDAO(getCompany());
        anoudDao = new AnoudDAO(getCompany());
        dao = new DealsDAO(getCompany());
        setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
        try {
            activity.setUserId(getUserInfo().getUserId());
            commonDao.sendEMail("", "", activity.getTo(), activity.getSubject(), activity.getMessage());
            activity.setTemplate(TypeConstants.CrmLogType.EMAIL_SENT.getCode());
            //Log the activity
            anoudDao.callCRMPackage(activity, CommonDAO.ActivityTypes.ACTIVITY);
            setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);

        } catch (Exception e) {
        }
        return "data";
    }

    public String saveDealsStageUpdate() {

  	    dao = new DealsDAO(getCompany());
        setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);

       try {
    	       Long newStageNo = crmDeal.getCdStageNo();
	    	   crmDeal = dao.loadDealById(getCompany(), crmDeal);

	    	   if(crmDeal.getCdStageNo() == newStageNo){
	                  setMessageType(ApplicationConstants.MESSAGE_TYPE_RECORD_EXISTS);
	    	   }else{
	    		   if( newStageNo < crmDeal.getCdStageNo() ){
		                  setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
		    	   }else{
		    		   crmDeal.setCdUpdUid(getUserInfo().getUserId());
			    	   crmDeal.setCdStageNo(newStageNo);
			    	   int recCount = dao.saveDealDetail(crmDeal, "edit");
		               if (recCount == 1) {
		                  setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
		               }
		    	   }

	    	   }


		} catch (Exception e) {
              LOGGER.error("Exception updating deal stage => {}", e);
              setMessage("Error occurred during stage changed operation. Please contact IT support.");
		}

        return "deals_data";

}

    private String saveDealsEnquiryNumber(Long dealId, String enqNo) {

  	    dao = new DealsDAO(getCompany());
        setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);

       try {
    	       setCrmDeal(new CrmDeal());
    	       getCrmDeal().setCdDealId(dealId);
	    	   crmDeal = dao.loadDealById(getCompany(), crmDeal);
	    	   if(crmDeal.getCdEnqNo() != null){
	                  setMessageType(ApplicationConstants.MESSAGE_TYPE_RECORD_EXISTS);
	    	   }else{
	    		   crmDeal.setCdUpdUid(getUserInfo().getUserId());
	    		   crmDeal.setCdStageNo(2L);
		    	   crmDeal.setCdEnqNo(enqNo);
		    	   int recCount = dao.saveDealDetail(crmDeal, "edit");
	               if (recCount == 1) {
	                  setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
	               }
	    	   }


		} catch (Exception e) {
              LOGGER.error("Exception updating deal stage => {}", e);
              setMessage("Error occurred during save enquiry number. Please contact IT support.");
		}

        return getMessageType();

}

    public String saveDealsClose() {

  	    dao = new DealsDAO(getCompany());
        setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);

       try {
    	       Long stageNo =     dao.getWonLostStageNo(crmDeal.getCdPlId(),getDealClose());
	    	   crmDeal = dao.loadDealById(getCompany(), crmDeal);
	    	   if(crmDeal.getCdStageNo() == stageNo){
	                  setMessageType(ApplicationConstants.MESSAGE_TYPE_RECORD_EXISTS);
	    	   }else{
	    		   crmDeal.setCdUpdUid(getUserInfo().getUserId());
		    	   crmDeal.setCdStageNo(stageNo);
		    	   int recCount = dao.saveDealDetail(crmDeal, "edit");
	               if (recCount == 1) {
	                  setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
	               }
	    	   }


		} catch (Exception e) {
              LOGGER.error("Exception updating deal close stage => {}", e);
              setMessage("Error occurred during stage changed operation. Please contact IT support.");
		}

        return "deals_data";

}





   public String saveDealsActivity() {

  	    dao = new DealsDAO(getCompany());
        setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);

       try {
                      activity.setUserId(getUserInfo().getUserId());
                      int recCnt = dao.saveDealActivity(activity);
                      if(recCnt==1)
                    	  setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);

		} catch (Exception e) {
              LOGGER.error("Exception updating deal stage => {}", e);
              setMessage("Error occurred during save deal activity operation. Please contact IT support.");
		}

        return "deals_data";
}


   public String saveDealsEnquiryForm() {
	        dao = new DealsDAO(getCompany());
            setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
            List<KeyValue> lobApplDept = dao.getLobApplDept(crmDealEnquiry.getQuoteLOB());
            crmDealEnquiry.setCompanyCode(lobApplDept.get(0).getValue());
            crmDealEnquiry.setDivisionCode(lobApplDept.get(0).getInfo());
            crmDealEnquiry.setDeptCode(lobApplDept.get(0).getInfo1());
            crmDealEnquiry.setStatus("P");
            crmDealEnquiry.setRemarks(crmDealEnquiry.getQuoteEnqRemarks());
            crmDealEnquiry.setCreatedBy(getUserInfo().getUserId());
    	    dao = new DealsDAO(crmDealEnquiry.getCrmId());
            Long enqNo  = dao.saveDealsEnquiryDetail(crmDealEnquiry, getCompany());
            if (enqNo != null) {
        	    dao = new DealsDAO(getCompany());
        	   String message = saveDealsEnquiryNumber(crmDealEnquiry.getDealId(), enqNo.toString());
               setMessageType(message);
           }

       return "data";
   }



    public String loadCustomerDealsData() {

     return "data";
   }

   public String loadDealsList() {

 	    dao = new DealsDAO(getCompany());
        setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);
        setAaData(dao.loadDealData(crmDeal,getCompany()));
        return "deals_data";
   }

   public String loadDealsActivity() {

       return "data";
   }

  public String loadContactListAutoFill(){
      dao = new DealsDAO(getCompany());
      setCdContactList(dao.loadContactList(flex1,flex2,flex3));
      return SUCCESS;
  }

  public String loadEnquiryListAutoFill(){
      dao = new DealsDAO(crmDeal.getCdCrmId());
      cdEnquiryList = dao.loadEnquiryList(flex1);
      return SUCCESS;
  }
  public String loadQuoteListAutoFill(){
      dao = new DealsDAO(crmDeal.getCdCrmId());
      cdQuoteList = dao.loadQuoteList(flex1);
      return SUCCESS;
  }

  public String loadPolicyListAutoFill(){
      dao = new DealsDAO(crmDeal.getCdCrmId());
      cdPolicyList = dao.loadPolicyList(flex1);
      return SUCCESS;
  }


public String loadDealsChartData() {
     dao = new DealsDAO(getCompany());
     aaData = dao.loadDealsChartData(crmDeal);
     return "data";
}
	public Customer getCustomer() {
	      return customer;
	}

    public void setCustomer(Customer customer) {
	      this.customer = customer;
	}


	public CrmDeal getCrmDeal() {
		return crmDeal;
	}

	public void setCrmDeal(CrmDeal crmDeal) {
		this.crmDeal = crmDeal;
	}

	public CrmDealPipeline getCrmDealPl() {
		return crmDealPl;
	}


	public void setCrmDealPl(CrmDealPipeline crmDealPl) {
		this.crmDealPl = crmDealPl;
	}






	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
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

	public DealsDAO getDao() {
		return dao;
	}

	public void setDao(DealsDAO dao) {
		this.dao = dao;
	}


	public List<KeyValue> getStageData() {
		return stageData;
	}

	public void setStageData(List<KeyValue> stageData) {
		this.stageData = stageData;
	}

	public List<CrmDealPipeline> getPlData() {
		return plData;
	}


	public void setPlData(List<CrmDealPipeline> plData) {
		this.plData = plData;
	}


	public List<KeyValue> getDealActivityData() {
		return dealActivityData;
	}

	public void setDealActivityData(List<KeyValue> dealActivityData) {
		this.dealActivityData = dealActivityData;
	}

	public List<CrmDealPipeline> getCdPipelineList() {
		return cdPipelineList;
	}

	public void setCdPipelineList(List<CrmDealPipeline> cdPipelineList) {
		this.cdPipelineList = cdPipelineList;
	}

	public List<KeyValue> getPipelineList() {
		return pipelineList;
	}


	public void setPipelineList(List<KeyValue> pipelineList) {
		this.pipelineList = pipelineList;
	}


	public List<KeyValue> getCdContactTypList() {
		return cdContactTypList;
	}

	public void setCdContactTypList(List<KeyValue> cdContactTypList) {
		this.cdContactTypList = cdContactTypList;
	}


	public List<KeyValue> getAgentList() {
		return agentList;
	}

	public void setAgentList(List<KeyValue> agentList) {
		this.agentList = agentList;
	}

	public List<KeyValue> getProductList() {
		return productList;
	}


	public void setProductList(List<KeyValue> productList) {
		this.productList = productList;
	}


	public List<KeyValue> getQuoteModeList() {
		return quoteModeList;
	}


	public List<KeyValue> getQuoteTypeList() {
		return quoteTypeList;
	}


	public void setQuoteTypeList(List<KeyValue> quoteTypeList) {
		this.quoteTypeList = quoteTypeList;
	}


	public List<KeyValue> getUnderWriterList() {
		return underWriterList;
	}


	public void setUnderWriterList(List<KeyValue> underWriterList) {
		this.underWriterList = underWriterList;
	}


	public void setQuoteModeList(List<KeyValue> quoteModeList) {
		this.quoteModeList = quoteModeList;
	}


	public List<KeyValue> getDateRangeList() {
		return dateRangeList;
	}

	public void setDateRangeList(List<KeyValue> dateRangeList) {
		this.dateRangeList = dateRangeList;
	}

	public List<KeyValue> getCdContactList() {
		return cdContactList;
	}

	public void setCdContactList(List<KeyValue> cdContactList) {
		this.cdContactList = cdContactList;
	}

	public List<KeyValue> getCdEnquiryList() {
		return cdEnquiryList;
	}

	public void setCdEnquiryList(List<KeyValue> cdEnquiryList) {
		this.cdEnquiryList = cdEnquiryList;
	}

	public List<KeyValue> getCdQuoteList() {
		return cdQuoteList;
	}

	public void setCdQuoteList(List<KeyValue> cdQuoteList) {
		this.cdQuoteList = cdQuoteList;
	}

	public List<KeyValue> getCdPolicyList() {
		return cdPolicyList;
	}

	public void setCdPolicyList(List<KeyValue> cdPolicyList) {
		this.cdPolicyList = cdPolicyList;
	}

	public List<KeyValue> getCdLostReasonList() {
		return cdLostReasonList;
	}

	public void setCdLostReasonList(List<KeyValue> cdLostReasonList) {
		this.cdLostReasonList = cdLostReasonList;
	}

    public List<KeyValue> getDealPastActivityData() {
		return dealPastActivityData;
	}

	public void setDealPastActivityData(List<KeyValue> dealPastActivityData) {
		this.dealPastActivityData = dealPastActivityData;
	}

	public List<KeyValue> getDealFutureActivityData() {
		return dealFutureActivityData;
	}

	public void setDealFutureActivityData(List<KeyValue> dealFutureActivityData) {
		this.dealFutureActivityData = dealFutureActivityData;
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

	public String getDealClose() {
		return dealClose;
	}

	public void setDealClose(String dealClose) {
		this.dealClose = dealClose;
	}

	public String getViewMode() {
		return viewMode;
	}

	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
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

	public CrmDealEnquiry getCrmDealEnquiry() {
		return crmDealEnquiry;
	}


	public void setCrmDealEnquiry(CrmDealEnquiry crmDealEnquiry) {
		this.crmDealEnquiry = crmDealEnquiry;
	}


	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}



	public String getBaseCurrency() {
		return baseCurrency;
	}


	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
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
