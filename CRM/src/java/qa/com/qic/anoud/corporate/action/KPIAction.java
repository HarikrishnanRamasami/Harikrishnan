/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.anoud.corporate.action;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import qa.com.qic.anoud.corporate.dao.KpiDAO;
import qa.com.qic.anoud.corporate.vo.KPITarget;
import qa.com.qic.anoud.vo.KeyValue;
import qa.com.qic.common.util.ApplicationConstants;
import qa.com.qic.common.util.TypeConstants;
import qa.com.qic.common.vo.UserInfo;
import qa.com.qic.utility.helpers.LogUtil;

/**
 *
 * @author karthik.j
 */


public class KPIAction extends ActionSupport implements SessionAware {

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;


	private static final Logger LOGGER = LogUtil.getLogger(KPIAction.class);
	private transient Map<String, Object> session;

	/**
     * messageType - S: Success, E: Error message - details description about
     * success/error
     */
    private String message;
    private String messageType;

    private String operation;
    boolean initialLoad;
    boolean approveDisabled;

    private Long maxRev;
    private Long deptRev;
    private String deptEffDt;


    private transient List<String> targetYears;
    private transient List<KeyValue> trgtTypList;
    private transient List<KeyValue> trgtLvlList;
    private transient List<KeyValue> companyList;
    private transient List<KeyValue> divsionList;
    private transient List<KeyValue> deptList;
    private transient List<KeyValue> salesPlanBusUnitList;


    private List<? extends Object> aaData;

    private transient KpiDAO dao;
    private String company;
    private String trgtCurrency;
    private transient UserInfo userInfo;

    private transient KPITarget targetBean;
    private transient KPITarget parentTarget;


    public String openTargetDefinitionsPage() {

    	dao = new KpiDAO(getCompany());
    	setTrgtCurrency(dao.getBaseCurrencyForCompany(getCompany()));
    	setTargetYears(loadTargetYears());

        return "target_definitions";
    }

    public String openSalesPlanningPage() {

    	dao = new KpiDAO(getCompany());
    	setTrgtCurrency(dao.getBaseCurrencyForCompany(getCompany()));
    	setTargetBean(new KPITarget());
    	targetBean.setTargetType(TypeConstants.KPITargetType.GROSS_PREMIUM.getCode());
    	targetBean.setCompCode(getCompany());

        return "sales_planning";
    }



    public String openTargetAddForm() {

		try {

    	dao = new KpiDAO(getCompany());
    	setOperation("add");

      	if(StringUtils.equals(TypeConstants.KPITargetLevel.COMPANY.getCode(), targetBean.getLevel() )){
      		companyList = dao.getCompanyList();
            setTrgtTypList(dao.getCrmTargetTypeList());
            setTargetYears(loadTargetYears());
            targetBean.setRevision(0L);

    	} else if(StringUtils.equals(TypeConstants.KPITargetLevel.DIVISION.getCode(), targetBean.getLevel() )){

    		targetBean.setPlanType(TypeConstants.KPIPlanType.TARGET.getCode());
    		targetBean.setLevel(TypeConstants.KPITargetLevel.COMPANY.getCode().toUpperCase());    //  Get corresponding parent level details
    		targetBean = dao.loadTargetById(targetBean).get(0);
        	divsionList = dao.getDivisionList(targetBean.getCompCode());
        	targetBean.setLevel(TypeConstants.KPITargetLevel.DIVISION.getCode());

    	} else if(StringUtils.equals(TypeConstants.KPITargetLevel.DEPARTMENT.getCode(), targetBean.getLevel() )){

    		targetBean.setPlanType(TypeConstants.KPIPlanType.TARGET.getCode());
    		targetBean.setLevel(TypeConstants.KPITargetLevel.DIVISION.getCode().toUpperCase());   //  Get corresponding parent level details
    		targetBean = dao.loadTargetById(targetBean).get(0);
    		deptList = dao.getDepartmentList(targetBean.getCompCode(), targetBean.getDivCode());
    		targetBean.setLevel(TypeConstants.KPITargetLevel.DEPARTMENT.getCode());

    	} else {

    		targetBean.setPlanType(TypeConstants.KPIPlanType.SALES.getCode());
        	setTrgtLvlList(dao.loadTargetLvlsRemainingForSalesPlan(targetBean));
    		String currentlvl = targetBean.getLevel();
    		String currEffDt = 	targetBean.getEffFrmDt();
    		KPITarget bean = new KPITarget();
    	    BeanUtils.copyProperties(bean, targetBean);
    	    List<KeyValue> currentLvls =dao.loadCurrentTargetLvlsForSalesPlan(bean);
    	    List<String>  currLvlvalues = currentLvls.stream().map(KeyValue::getValue).collect(Collectors.toList());

    	    if(StringUtils.equals(currentlvl, "new")){
    	    	Long maxRev = dao.getMaxRevNumForSalesPlan(bean);
    	    	if(maxRev == null){
        	    	bean.setRevision(0L);
    	    	}else{
        	    	bean.setRevision(maxRev+1);
    	    	}
    	 	    bean.setLevel(currentLvls.get(currentLvls.size()-1).getValue().toUpperCase()); //  Get corresponding parent level details
    	    }
    	    else  {
    	    	bean.setLevel(currentLvls.get(currLvlvalues.indexOf(currentlvl.toLowerCase())-1).getValue().toUpperCase());  //  Get corresponding parent level details

    	    }

    	    if(StringUtils.equalsIgnoreCase(bean.getLevel(), TypeConstants.KPITargetLevel.DEPARTMENT.getCode())){
    	    	bean.setPlanType(TypeConstants.KPIPlanType.TARGET.getCode());
    	    	bean.setTrgtLvlNo(3L);
    	    	bean.setRevision(getDeptRev());
    	    	bean.setEffFrmDt(getDeptEffDt());
    	    }
    	    else{
    	    	bean.setPlanType(TypeConstants.KPIPlanType.SALES.getCode());
    	    }

    	    targetBean = dao.loadTargetById(bean).get(0);
	    	targetBean.setLevel(currentlvl);
	    	targetBean.setEffFrmDt(currEffDt);
	    	targetBean.setTrgtLvlNo(targetBean.getTrgtLvlNo()+1);
	    	Long updatedRev =   dao.getMaxRevNumForSalesPlan(getTargetBean());
	      	if(updatedRev == null){
	      		updatedRev = 0L;
	    	}
      		targetBean.setRevision(updatedRev);



    	}

		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	return "add_trgt_frm";

    }

    public String openTargetEditForm() {

    	try{

    	Long deptRev = null;
    	dao = new KpiDAO(getCompany());
    	setTrgtCurrency(dao.getBaseCurrencyForCompany(getCompany()));
    	setOperation("edit");

    	if(targetBean.getTrgtLvlNo() == 4){
    	    deptRev = targetBean.getRevision();
    		targetBean.setRevision(dao.getMaxRevNumForSalesPlan(targetBean));
    	}
		targetBean = dao.loadTargetById(targetBean).get(0);

		if(StringUtils.equals(TypeConstants.KPITargetLevel.COMPANY.getCode(), targetBean.getLevel())){
			targetBean.setPlanType(TypeConstants.KPIPlanType.TARGET.getCode());
		} else if(StringUtils.equals(TypeConstants.KPITargetLevel.DIVISION.getCode(), targetBean.getLevel())){
        	setParentTarget(new KPITarget());
        	BeanUtils.copyProperties(parentTarget, targetBean);
        	parentTarget.setPlanType(TypeConstants.KPIPlanType.TARGET.getCode());
        	parentTarget.setLevel(TypeConstants.KPITargetLevel.COMPANY.getCode().toUpperCase());
        	parentTarget.setDivCode(null);
        	parentTarget = dao.loadTargetById(getParentTarget()).get(0);
 	    } else if(StringUtils.equals(TypeConstants.KPITargetLevel.DEPARTMENT.getCode(), targetBean.getLevel())){
        	setParentTarget(new KPITarget());
        	BeanUtils.copyProperties(parentTarget, targetBean);
        	parentTarget.setPlanType(TypeConstants.KPIPlanType.TARGET.getCode());
        	parentTarget.setLevel(TypeConstants.KPITargetLevel.DIVISION.getCode().toUpperCase());
        	parentTarget.setDeptCode(null);
        	parentTarget = dao.loadTargetById(getParentTarget()).get(0);
 	     } else {
        	setParentTarget(new KPITarget());
        	String currentlvl = targetBean.getLevel();
        	BeanUtils.copyProperties(parentTarget, targetBean);
    	    List<KeyValue> currentLvls =dao.loadCurrentTargetLvlsForSalesPlan(parentTarget);
    	    List<String>  currLvlvalues = currentLvls.stream().map(KeyValue::getValue).collect(Collectors.toList());
    	    parentTarget.setLevel(currentLvls.get(currLvlvalues.indexOf(currentlvl)-1).getValue().toUpperCase());  //  Get corresponding parent level details

    	    if(StringUtils.equalsIgnoreCase(parentTarget.getLevel(), TypeConstants.KPITargetLevel.DEPARTMENT.getCode())){
            	parentTarget.setPlanType(TypeConstants.KPIPlanType.TARGET.getCode());
            	parentTarget.setTrgtLvlNo(3L);
            	parentTarget.setRevision(deptRev);
            	parentTarget.setEffFrmDt(getDeptEffDt());
    	    }
    	    else{
            	parentTarget.setPlanType(TypeConstants.KPIPlanType.SALES.getCode());
    	    }


    	    switch(currentlvl){
    	    case "lob" :
    	        parentTarget.setLobCode(null);
    	        break;
    	    case "product":
    	    	parentTarget.setProdCode(null);
    	    	break;
    	    case "channel" :
    	        parentTarget.setChannel(null);
    	        break;
    	    case "employee" :
    	    	parentTarget.setEmpCode(null);
    	    	break;
    	    }


    	    parentTarget = dao.loadTargetById(getParentTarget()).get(0);

 	    }


    	}catch (Exception e){

    		e.printStackTrace();
    	}

     	return "edit_trgt_frm";
    }

    public String openTargetAmendForm() {

     	setOperation("amend");
    	dao = new KpiDAO(getCompany());
    	setTrgtCurrency(dao.getBaseCurrencyForCompany(getCompany()));
        targetBean = dao.loadTargetById(targetBean).get(0);

		return "add_trgt_frm";
    }

    public String openTargetDeleteForm() {

    	dao = new KpiDAO(getCompany());
    	setOperation("delete");
		targetBean = dao.loadTargetById(targetBean).get(0);
    	return "del_trgt_frm";
    }



 public String saveTargetForm() {

	    int recCount = 0;
 	    dao = new KpiDAO(getCompany());
	    setMessageType(ApplicationConstants.MESSAGE_TYPE_ERROR);

	    if("add".equals(getOperation())){
	    	    setOperation("add");
		        targetBean.setCrUid(getUserInfo().getUserId());
	        	targetBean.setCrmId(targetBean.getCrmId());
	        	recCount = dao.saveTargetDetail(targetBean, getOperation());
	            if (recCount == 1) {
	                 setMessageType(ApplicationConstants.MESSAGE_TYPE_SUCCESS);
	            }

	      } else if("edit".equals(getOperation())){
    	         setOperation("edit");
	    		 targetBean.setUpdUid(getUserInfo().getUserId());
	       	     targetBean.setCrmId(targetBean.getCrmId());
	       	 	 recCount = dao.saveTargetDetail(targetBean, getOperation());
	        	 if (recCount == 1) {
	                 setMessageType(ApplicationConstants.MESSAGE_TYPE_EDIT_SUCCESS);
	             }

           } else if("delete".equals(getOperation())){
	    	      setOperation("delete");
	    		  recCount = dao.saveTargetDetail(targetBean, getOperation());
	    		  if (recCount >= 1) {
	                  setMessageType(ApplicationConstants.MESSAGE_TYPE_DELETE_SUCCESS);
	               }
	    	}




	   return "data";
 }

 public String saveTargetApproval() {
	    int recCount = 0;
		dao = new KpiDAO(getCompany());
		targetBean.setApprUid(getUserInfo().getUserId());

        recCount = dao.saveTargetApproval(targetBean);
 	    if (recCount >= 1) {
 	    	setMessageType(ApplicationConstants.MESSAGE_TYPE_APPROVE_SUCCESS);

        }
	 return "data";
 }

 public String saveTargetAmend() {


	     int recCount = 0;
		 dao = new KpiDAO(getCompany());
	     targetBean.setCrUid(getUserInfo().getUserId());
	     targetBean.setCrmId(targetBean.getCrmId());
         recCount = dao.saveTargetAmend(targetBean);
	     if (recCount >= 1) {
            setMessageType(ApplicationConstants.MESSAGE_TYPE_AMEND_SUCCESS);
            targetBean.setRevision((targetBean.getRevision()+1));
        }

	 return "data";
}

    public String loadAllTargets(){

    	setAaData(new ArrayList<>());
    	dao = new KpiDAO(getCompany());

    	if(BooleanUtils.isNotTrue(isInitialLoad()))
    	        setAaData(dao.loadTargetData(targetBean));

    	return "data";
    }

    public String loadTargetAmendHistory(){

    	setAaData(new ArrayList<>());
    	dao = new KpiDAO(getCompany());

    	if(targetBean.getTrgtLvlNo() == 1)
             setAaData(dao.loadCompanyTargetHistoryData(targetBean));
    	else if(targetBean.getTrgtLvlNo() == 4)
            setAaData(dao.loadSalesPlanTargetHistoryData(targetBean));



    	return "data";
    }

    public String loadTargetIsApproveDisabled(){

    	dao = new KpiDAO(getCompany());
     	setApproveDisabled(dao.loadIsApproveDisabled(targetBean));
    	return "data";

    }


    public String  loadApprovedTargets(){

    	List<KeyValue> userDivnDept = null;
    	dao = new KpiDAO(getCompany());
    	userDivnDept = dao.getDeptForUser(getUserInfo().getUserId(), getUserInfo().getCompanyCode());
        setAaData(dao.loadApprovedTargets(targetBean.getTargetType(), userDivnDept));
    	return "data";
    }



    private List<String>  loadTargetYears(){

    	Calendar cal = Calendar.getInstance();
    	List<String> list = new ArrayList<>();
        list.add(String.valueOf((cal.get(Calendar.YEAR))));
        list.add(String.valueOf((cal.get(Calendar.YEAR) + 1)));

        return list;
    }


    public String loadTargetTypes(){

    	dao = new KpiDAO(getCompany());
    	setAaData(dao.getCrmTargetTypeList());
    	return "data";



    }

    public String loadTargetLevels(){

    	dao = new KpiDAO(getCompany());
    	setAaData(dao.getCrmTargetTypeList());
    	return "data";



    }

   public String loadTargetSalesPlanBusUnits(){

    	dao = new KpiDAO(getCompany());

     	if(StringUtils.equals(TypeConstants.KPITargetLevel.LOB.getCode(), targetBean.getLevel())){
            salesPlanBusUnitList = dao.getLobList(targetBean.getCompCode(), targetBean.getDivCode(), targetBean.getDeptCode());
    	} else if(StringUtils.equals(TypeConstants.KPITargetLevel.PRODUCT.getCode(), targetBean.getLevel())){
         	salesPlanBusUnitList = dao.getProductList(StringUtils.defaultIfEmpty(targetBean.getLobCode(), null));
    	} else if(StringUtils.equals(TypeConstants.KPITargetLevel.CHANNEL.getCode(), targetBean.getLevel())){
    		salesPlanBusUnitList = dao.getChannels(targetBean);
    	} else if(StringUtils.equals(TypeConstants.KPITargetLevel.EMPLOYEE.getCode(), targetBean.getLevel())){
    		salesPlanBusUnitList = dao.getEmployeeListForDept(targetBean.getCompCode(), targetBean.getDivCode(), targetBean.getDeptCode());
    	}


    	setAaData(getSalesPlanBusUnitList());


    	return "data";



    }

   public String loadAllTargetLvlsForYrCompRev(){

	   dao = new KpiDAO(getCompany());
	   setAaData(dao.loadAllTargetLvlsForYrCompRev(targetBean));
	   return "data";
   }

   public String loadNextTargetLevelForSalesPlan(){

	   dao = new KpiDAO(getCompany());
	   List<KeyValue> lvlsRemaining = dao.loadTargetLvlsRemainingForSalesPlan(targetBean);

	   if(lvlsRemaining.size() > 0)
	             targetBean.setLevel(lvlsRemaining.get(0).getValue().toLowerCase());

	   setAaData(lvlsRemaining);


	   return "data";
   }

   public String loadCurrentTargetLevelForSalesPlan(){
	   dao = new KpiDAO(getCompany());
	   List<KeyValue> currentLvls = dao.loadCurrentTargetLvlsForSalesPlan(targetBean);
	   if(currentLvls.size() > 0)
	            targetBean.setLevel(currentLvls.get(0).getValue());
	   setAaData(currentLvls);


	   return "data";
   }



    private String getCompanyShortName(String code){

    	String shortName = null;
    	List<KeyValue> compList = new ArrayList<>();
    	compList = dao.getCompanyList();
		for (KeyValue company: compList){
			if(code.equals(company.getKey())){
				shortName = company.getValue();
				break;
			}
		}
    	return shortName;
    }

    private String getDivisionShortName(String compCode,String divCode){

    	String shortName = null;
    	List<KeyValue> divList = new ArrayList<>();
    	divList = dao.getDivisionList(compCode);
		for (KeyValue division: divList){
			if(divCode.equals(division.getKey())){
				shortName = division.getValue();
				break;
			}
		}
    	return shortName;
    }


    private String getDepartmentShortName(String compCode,String divCode,String deptCode){

    	String shortName = null;
    	List<KeyValue> deptList = new ArrayList<>();
    	deptList = dao.getDepartmentList(compCode, divCode);
		for (KeyValue department: deptList){
			if(deptCode.equals(department.getKey())){
				shortName = department.getValue();
				break;
			}
		}
    	return shortName;
    }



	@Override
	public void setSession(Map<String, Object> session) {
        this.session = session;
        setCompany((String) session.get(ApplicationConstants.SESSION_NAME_COMPANY_CODE));
        this.userInfo = (UserInfo) session.get(ApplicationConstants.SESSION_NAME_USER_INFO);
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



	public boolean isInitialLoad() {
		return initialLoad;
	}

	public void setInitialLoad(boolean initialLoad) {
		this.initialLoad = initialLoad;
	}

	public boolean isApproveDisabled() {
		return approveDisabled;
	}

	public void setApproveDisabled(boolean approveDisabled) {
		this.approveDisabled = approveDisabled;
	}


	public Long getMaxRev() {
		return maxRev;
	}

	public void setMaxRev(Long maxRev) {
		this.maxRev = maxRev;
	}




	public Long getDeptRev() {
		return deptRev;
	}

	public void setDeptRev(Long deptRev) {
		this.deptRev = deptRev;
	}

	public String getDeptEffDt() {
		return deptEffDt;
	}

	public void setDeptEffDt(String deptEffDt) {
		this.deptEffDt = deptEffDt;
	}

	public List<String> getTargetYears() {
		return targetYears;
	}

	public void setTargetYears(List<String> targetYears) {
		this.targetYears = targetYears;
	}



	public List<KeyValue> getTrgtTypList() {
		return trgtTypList;
	}

	public void setTrgtTypList(List<KeyValue> trgtTypList) {
		this.trgtTypList = trgtTypList;
	}

	public List<KeyValue> getTrgtLvlList() {
		return trgtLvlList;
	}

	public void setTrgtLvlList(List<KeyValue> trgtLvlList) {
		this.trgtLvlList = trgtLvlList;
	}

	public List<KeyValue> getCompanyList() {
		return companyList;
	}

	public void setCompanyList(List<KeyValue> companyList) {
		this.companyList = companyList;
	}

	public List<KeyValue> getDivsionList() {
		return divsionList;
	}

	public void setDivsionList(List<KeyValue> divsionList) {
		this.divsionList = divsionList;
	}

	public List<KeyValue> getDeptList() {
		return deptList;
	}

	public void setDeptList(List<KeyValue> deptList) {
		this.deptList = deptList;
	}




	public List<KeyValue> getSalesPlanBusUnitList() {
		return salesPlanBusUnitList;
	}

	public void setSalesPlanBusUnitList(List<KeyValue> salesPlanBusUnitList) {
		this.salesPlanBusUnitList = salesPlanBusUnitList;
	}

	public List<? extends Object> getAaData() {
		return aaData;
	}

	public void setAaData(List<? extends Object> aaData) {
		this.aaData = aaData;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getTrgtCurrency() {
		return trgtCurrency;
	}

	public void setTrgtCurrency(String trgtCurrency) {
		this.trgtCurrency = trgtCurrency;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}



	public KPITarget getTargetBean() {
		return targetBean;
	}

	public void setTargetBean(KPITarget targetBean) {
		this.targetBean = targetBean;
	}

	public KPITarget getParentTarget() {
		return parentTarget;
	}

	public void setParentTarget(KPITarget parentTarget) {
		this.parentTarget = parentTarget;
	}







}
