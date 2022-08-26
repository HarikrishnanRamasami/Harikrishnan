/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qa.com.qic.crm.restapi.resources;

import org.apache.logging.log4j.Logger;
import org.jvnet.hk2.annotations.Service;

import qa.com.qic.anoud.dao.AnoudDAO;
import qa.com.qic.anoud.vo.Activity;
import qa.com.qic.anoud.vo.Customer;
import qa.com.qic.common.dao.CommonDAO;
import qa.com.qic.common.dao.LeadDAO;
import qa.com.qic.common.dao.TaskDAO;
import qa.com.qic.common.db.DatabaseDAO;
import qa.com.qic.common.vo.CrmCallLog;
import qa.com.qic.common.vo.CrmLeads;
import qa.com.qic.common.vo.CrmTasks;
import qa.com.qic.common.vo.CrmUser;
import qa.com.qic.common.vo.UserInfo;
import qa.com.qic.utility.helpers.LogUtil;

/**
 *
 * @author sutharsan.g
 */
@Service
public class CrmService extends DatabaseDAO {

    private static final Logger logger = LogUtil.getLogger(CrmService.class);

    private final String companyCode;
    private final CrmDAO crmDAO;
    private final TaskDAO taskDAO;
    private final LeadDAO leadDAO;
    private final AnoudDAO anoudDAO;

    public String getCompanyCode() {
        return companyCode;
    }

    public CrmService(String companyCode) {
        this.companyCode = companyCode;
        crmDAO = new CrmDAO(companyCode);
        taskDAO = new TaskDAO(companyCode);
        leadDAO = new LeadDAO(companyCode);
        anoudDAO = new AnoudDAO(companyCode);
    }

    public int saveTasks(CrmTasks crmTasks, UserInfo userInfo) {
       crmTasks.setCtCrUid(userInfo.getUserId());
        crmTasks.setCtStatus("P");
        return taskDAO.saveTask(crmTasks, "add");
    }

    public Long createTasks(CrmTasks crmTasks, String userId) {
        crmTasks.setCtCrUid(userId);
        crmTasks.setCtStatus("P");
        crmTasks.setCtPriority("N");
        return taskDAO.createTask(crmTasks);
    }

    public int saveLeads(CrmLeads crmLeads, UserInfo userInfo) throws Exception {
        crmLeads.setClCrUid(userInfo.getUserId());
        crmLeads.setClStatus("P");
        return leadDAO.saveLeads(crmLeads, "add");
    }

    public CrmLeads loadLeadData(Long leadId) {
        return leadDAO.loadLeadDetailById(leadId);
    }

    public int saveCustomer(Customer customer, UserInfo userInfo) {
        customer.setCrUid(userInfo.getUserId());
        return anoudDAO.saveCustomerDetail(customer, "add");
    }

    public String createActivity(Activity activity, UserInfo userInfo) throws Exception {
        activity.setUserId(userInfo.getUserId());
        return anoudDAO.callCRMPackage(activity, CommonDAO.ActivityTypes.ACTIVITY);
    }

    public UserInfo login(final String username, final String password) throws Exception {
        return crmDAO.login(username, password);
    }

    public CrmUser getUserDetailsByUserId(String userId) throws Exception {
        return anoudDAO.getUserDetailsByUserId(userId);
    }

    public int updateAmeyoFeedBack(CrmCallLog callLog) throws Exception {
        return crmDAO.updateAmeyoFeedBack(callLog);
    }
}
