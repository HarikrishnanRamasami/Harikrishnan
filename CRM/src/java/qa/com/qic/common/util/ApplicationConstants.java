/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.common.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedList;

import qa.com.qic.common.util.config.AppConfig;
import qa.com.qic.common.util.config.AppConfigDev;
import qa.com.qic.common.util.config.AppConfigProd;
import qa.com.qic.common.util.config.AppConfigQA;
import qa.com.qic.common.util.config.MvpAppConfigDev;
import qa.com.qic.common.util.config.MvpAppConfigQA;

/**
 *
 * @author ravindar.singh
 */
public class ApplicationConstants {

    public static final String COMPANY_CODE_DOHA_MED_DOHA = "001009";
    public static final String COMPANY_CODE_DOHA = "001";
    public static final String COMPANY_CODE_CORP_DOHA = "001";
    public static final String COMPANY_CODE_DUBAI = "002";
    public static final String COMPANY_CODE_KUWAIT = "005";
    public static final String COMPANY_CODE_OMAN = "006";
    public static final String COMPANY_CODE_BEEMA = "100";
    public static final String COMPANY_CODE_MED_DOHA = "009";
    public static final String COMPANY_CODE_MED_OMAN = "006";

    public static final String MESSAGE_TYPE_SUCCESS = "S";
    public static final String MESSAGE_TYPE_ERROR = "E";

    public static final String EMAIL_VALIDTOR_REGEX = "^[A-Za-z0-9_\\+-]+(\\.[A-Za-z0-9_\\+-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*\\.([A-Za-z]{2,4})$";

    public static final String SESSION_NAME_COMPANY_CODE = "COMPANY_CODE";
    public static final String SESSION_NAME_USER_ID = "USER_ID";
    public static final String SESSION_NAME_USER_INFO = "USER_INFO";
    public static final String SESSION_NAME_OLD_PASSWORD = "OLD_PASSWORD";

    public static enum Environment {
        PROD, QA, DEV, LOCAL, MVP_QA, MVP_DEV
    }

    public final static Environment ENVIRONMENT = Environment.LOCAL;

    //public static final String BASE_URL_ANOUD_APP_DOHA = "https://demo.qatarinsurance.com/QIC";
    public static final String BASE_URL_ANOUD_APP_DUBAI = "http://www.dubai.anoud.com/QIC";
    public static final String BASE_URL_ANOUD_APP_OMAN = "http://www.oman.anoud.com/QIC";
    public static final String BASE_URL_ANOUD_APP_KUWAIT = "http://www.kuwait.anoud.com/QIC";
    public static final String BASE_URL_ANOUD_APP_DOHA = "http://www.doha.anoud.com/QIC";
    public static final String BASE_URL_ANOUD_APP_BEEMA = "http://www.beema.anoud.com/742";
    public static final String BASE_URL_ANOUD_APP_MED_DOHA = "http://www.medical.anoud.com/medical";
    public static final String BASE_URL_ANOUD_APP_MED_OMAN = "http://www.oman.medical.anoud.com/medical";

    public static final AppConfig APP_URLS;
    public static final String FILE_PATH_PREFIX;

    static {
        switch (ENVIRONMENT) {
            case PROD:
                APP_URLS = new AppConfigProd();
                FILE_PATH_PREFIX = "/qlmapps/DMSUploads/";
                break;
            case QA:
                APP_URLS = new AppConfigQA();
                FILE_PATH_PREFIX = "/anoudapps/DMSUploads/";
                break;
            case DEV:
                APP_URLS = new AppConfigDev();
                FILE_PATH_PREFIX = "/dms_app/DMSUploads/";
                break;
            case MVP_QA:
                APP_URLS = new MvpAppConfigQA();
                FILE_PATH_PREFIX = "/DMSUploads/";
                break;
            case MVP_DEV:
                APP_URLS = new MvpAppConfigDev();
                FILE_PATH_PREFIX = "/DMSUploads/";
                break;
            default:
                APP_URLS = new AppConfigDev();
                FILE_PATH_PREFIX = "C:/DMSUploads/";
                break;
        }
    }

    public static final String RETAIL_CTI_AMEYO_URL = "https://ameyo.anoudapps.com:8443/";
    public static final String CHAT_ENGATI_URL = "https://app.engati.com/portal/login?next=#/customerSupportRequests";

    public static String PUSH_NOTIFICATION_TYPE = "009";
    public static String PUSH_NOTIFICATION_STATUS = "P";

    public static boolean PUSH_NOTIFICATION_TASK = false;
    private final static String TASK_NOTIFY_RETAIL_CATG = "'14003'";
    private final static String TASK_NOTIFY_QLM_CATG = "'001001','001002'";

    private final static String DOHA_MOBILE_REGEX = "^974[3,5,6,7]\\d{7}$";
    private final static String DUBAI_MOBILE_REGEX = "^971[5]\\d{8}$|^([0][5])\\d{8}$";
    private final static String OMAN_MOBILE_REGEX = "^968[7,9]\\d{7}$";
    private final static String KUWAIT_MOBILE_REGEX = "^965[5,6,9]\\d{7}$";

    public static String TASK_NOTIFY_CATG(String companyCode) throws Exception {
        if (null != companyCode) {
            if (APP_TYPE_RETAIL.equals(APP_TYPE)) {
                return TASK_NOTIFY_RETAIL_CATG;
            } else if (APP_TYPE_QLM.equals(APP_TYPE)) {
                return TASK_NOTIFY_QLM_CATG;
            }
        }
        return null;
    }

    public static final String DATE_TIME_FORMAT = "MM-dd-yyyy HH:mm";
    public static final String DATE_FORMAT = "dd/MM/yyyy";

    public static final String M_APP_CODE_RELATION = "RELATION";
    private static final String CAMPAIGN_BASE_PATH = "campaign/";
    public static final String CAMPAIGN_IMAGE_PATH = CAMPAIGN_BASE_PATH + "images/";
    public static final String CAMPAIGN_DS_PATH = CAMPAIGN_BASE_PATH + "ds/";
    public static final String CAMPAIGN_PROPERTY_DELIMIT = "@";
    public static final String MULTIPLE_CIVIL_ID_EXIST = "-1";
    public static final String TASK_FILE_PATH = "TASKS/";
    public static final String TEMP_FILE_PATH = "TEMP/";

    public static final String APP_TYPE_RETAIL = "R";
    public static final String APP_TYPE_QLM = "Q";
    public static final String APP_TYPE_GI = "GI";
    public static final String APP_TYPE = APP_TYPE_RETAIL; //APP_TYPE_QLM;//
    public static final String DEFAULT_COMPANY_CODE;
    public static final LinkedList<Company> DATASOURCE_LIST;

    static {
        switch (APP_TYPE) {
            case APP_TYPE_RETAIL:
                DEFAULT_COMPANY_CODE = COMPANY_CODE_DOHA;
                //DEFAULT_COMPANY_CODE = COMPANY_CODE_BEEMA;
                break;
            case APP_TYPE_QLM:
                DEFAULT_COMPANY_CODE = COMPANY_CODE_MED_DOHA;
                break;
            default:
                DEFAULT_COMPANY_CODE = COMPANY_CODE_DOHA;
                break;
        }
        DATASOURCE_LIST = new LinkedList<>();
        if(Environment.MVP_QA == ENVIRONMENT || Environment.MVP_DEV == ENVIRONMENT) {
            if(APP_TYPE_RETAIL.equals(APP_TYPE)) {
                DATASOURCE_LIST.add(new Company(COMPANY_CODE_DOHA, "&nbsp;&nbsp;Qatar", "/plugins/flag/flags/1x1/qa.svg"));
            } else if(APP_TYPE_QLM.equals(APP_TYPE)) {
                DATASOURCE_LIST.add(new Company(COMPANY_CODE_MED_DOHA, "&nbsp;&nbsp;Qatar", "/plugins/flag/flags/1x1/qa.svg"));
            }
        } else {
            if(APP_TYPE_RETAIL.equals(APP_TYPE)) {
                DATASOURCE_LIST.add(new Company(COMPANY_CODE_DOHA, "&nbsp;&nbsp;Qatar", "/plugins/flag/flags/1x1/qa.svg"));
                DATASOURCE_LIST.add(new Company(COMPANY_CODE_DUBAI, "&nbsp;&nbsp;UAE", "/plugins/flag/flags/1x1/ae.svg"));
                DATASOURCE_LIST.add(new Company(COMPANY_CODE_OMAN, "&nbsp;&nbsp;Oman", "/plugins/flag/flags/1x1/om.svg"));
                DATASOURCE_LIST.add(new Company(COMPANY_CODE_KUWAIT, "&nbsp;&nbsp;Kuwait", "/plugins/flag/flags/1x1/kw.svg"));
            } else if(APP_TYPE_QLM.equals(APP_TYPE)) {
                DATASOURCE_LIST.add(new Company(COMPANY_CODE_MED_DOHA, "&nbsp;&nbsp;QLM", "/images/QLM.jpg"));
                DATASOURCE_LIST.add(new Company(COMPANY_CODE_MED_OMAN, "&nbsp;&nbsp;OQIC", "/images/logo_med_oman.png"));
            }
        }
    }

    public static final String ABANDONED_CALL_USER_ID = "abandoned";
    public static final String WHATSAPP_QLM_DOC_URL = "https://www.qlm-online.com/crm/wa/docs/";
   //Deals - Begin
    public static final Long DEALS_DEFAULT_PIPELINE = 1L;
   //Deals - End

   //Corporate CRM - Begins

    public static final String DB_TIMEZONE = "Asia/Qatar";
    public static final String CUSTOMER360_GI_SEARCH_INSURED = "INS";
    public static final String CUSTOMER360_GI_SEARCH_CUSTOMER = "CUST";
    public static final String CUSTOMER360_GI_SEARCH_BROKER = "BRK";
    public static final String VALUE_ZERO = "0";

    public static final String MESSAGE_TYPE_DELETE_SUCCESS = "D";
    public static final String MESSAGE_TYPE_APPROVE_SUCCESS = "A";
    public static final String MESSAGE_TYPE_AMEND_SUCCESS = "AM";
    public static final String MESSAGE_TYPE_EDIT_SUCCESS = "M";
    public static final String MESSAGE_TYPE_RECORD_EXISTS = "R";

   // public static final String BASE_URL_ANOUD_APP_CORP_DOHA = "http://127.0.0.1:8081/GIMaven";
    public static final String BASE_URL_ANOUD_APP_CORP_DOHA = "http://172.20.132.83:7007/Corp";

    //Corporate CRM - Ends

    public static String FILE_STORE_LOC(String appType, String companyCode) throws Exception {
        StringBuilder sb = new StringBuilder(FILE_PATH_PREFIX);
        if (null != companyCode) {
            if (APP_TYPE_RETAIL.equals(appType)) {
                switch (companyCode) {
                    case ApplicationConstants.COMPANY_CODE_BEEMA:
                    case ApplicationConstants.COMPANY_CODE_DUBAI:
                    case ApplicationConstants.COMPANY_CODE_OMAN:
                    case ApplicationConstants.COMPANY_CODE_KUWAIT:
                    case ApplicationConstants.COMPANY_CODE_DOHA:
                        sb.append(appType).append("/").append(companyCode).append("/");
                        break;
                }
            } else if (APP_TYPE_QLM.equals(appType)) {
                switch (companyCode) {
                    case ApplicationConstants.COMPANY_CODE_MED_DOHA:
                    case ApplicationConstants.COMPANY_CODE_MED_OMAN:
                        sb.append(appType).append("/").append(companyCode).append("/");
                        break;
                }
            }
        }
        return sb.toString();
    }

    public static String BASE_URL_ANOUD_APP(String appType, String companyCode) throws Exception {
        if (null != companyCode) {
            if (APP_TYPE_RETAIL.equals(appType)) {
                switch (companyCode) {
                    case COMPANY_CODE_BEEMA:
                        return APP_URLS.getBaseUrlAnoudAppBeema();
                    case COMPANY_CODE_DUBAI:
                        return APP_URLS.getBaseUrlAnoudAppDubai();
                    case COMPANY_CODE_OMAN:
                        return APP_URLS.getBaseUrlAnoudAppOman();
                    case COMPANY_CODE_KUWAIT:
                        return APP_URLS.getBaseUrlAnoudAppKuwait();
                    case COMPANY_CODE_DOHA:
                        return APP_URLS.getBaseUrlAnoudAppDoha();
                }
            } else if (APP_TYPE_QLM.equals(appType)) {
                switch (companyCode) {
                    case COMPANY_CODE_MED_DOHA:
                        return APP_URLS.getBaseUrlAnoudAppMedDoha();
                    case COMPANY_CODE_MED_OMAN:
                        return APP_URLS.getBaseUrlAnoudAppMedOman();
                }
            } else if (APP_TYPE_GI.equals(appType)) {
                switch (companyCode) {
                case COMPANY_CODE_DOHA:
                    return BASE_URL_ANOUD_APP_CORP_DOHA;
            }
        }
        }
        return null;
    }

    private static String getLocalIP() {
        String localip = "";
        InetAddress localhost;
        try {
            localhost = InetAddress.getLocalHost();
            localip = localhost.getHostAddress().trim();
        } catch (UnknownHostException ex) {

        }
        return localip;
    }

    public static String getCompanyByDomain(String domain, String contextPath, String company) {
        if (null == domain || null != company) {
            return company;
        }
        /*if (domain.contains("qlm-online.com")) {
            company = ApplicationConstants.COMPANY_CODE_MED_DOHA;
        } else if (domain.contains("beema.crm.anoudapps.com") || domain.contains("beema-online.com")) {
            company = ApplicationConstants.COMPANY_CODE_BEEMA;
        } else if (domain.contains("i-insured.com")) {
            company = ApplicationConstants.COMPANY_CODE_DUBAI;
        } else if (domain.contains("oqic-insured.com")) {
            company = ApplicationConstants.COMPANY_CODE_OMAN;
        } else if (domain.contains("kqic-insured.com")) {
            company = ApplicationConstants.COMPANY_CODE_KUWAIT;
        } else if (domain.contains("qic-insured.com")) {
            company = ApplicationConstants.COMPANY_CODE_DOHA;
        } else {
            company = ApplicationConstants.DEFAULT_COMPANY_CODE;
        }*/

        if (contextPath.contains("/mcrm") || domain.contains(getLocalIP())) {
            company = company == null ? ApplicationConstants.COMPANY_CODE_MED_DOHA : company;
        } else if (contextPath.contains("/bcrm") || domain.contains("127.0.0.1")) {
            company = ApplicationConstants.COMPANY_CODE_BEEMA;
        } else if (contextPath.contains("/crm") || domain.contains("localhost")) {
            company = company == null ? ApplicationConstants.COMPANY_CODE_DOHA : company;
        } else {
            company = ApplicationConstants.DEFAULT_COMPANY_CODE;
        }

        return company;
        //commented for corporate
        //return ApplicationConstants.COMPANY_CODE_DOHA_MED_DOHA;
    }

    /*DMS project code START */
    private static final long DMS_PROJECT_ID_009 = 300;
    private static final long DMS_PROJECT_ID_006 = 301;
    public static final String DMS_MEMBERS_DOC_TYPE = "MEMBERS";
    public static final String DMS_PROJECT_MED = "DMS_MEDICAL";

    /*DMS project code END */
    public static long DMS_PROJECT_ID(String companyCode, String project) throws Exception {
        if (DMS_PROJECT_MED.equalsIgnoreCase(project)) {
            if (COMPANY_CODE_MED_DOHA.equalsIgnoreCase(companyCode)) {
                return DMS_PROJECT_ID_009;
            } else if (COMPANY_CODE_MED_OMAN.equalsIgnoreCase(companyCode)) {
                return DMS_PROJECT_ID_006;
            } else {
                throw new Exception("Company not mapped to DMS");
            }
        }
        throw new Exception("Company not mapped to DMS");
    }

    public static String getCampaignBaseDomain(String companyCode) {
        if (null != companyCode) {
            if (APP_TYPE_RETAIL.equals(APP_TYPE)) {
                switch (companyCode) {
                    case COMPANY_CODE_BEEMA:
                        return APP_URLS.getBaseUrlCampaignAppBeema();
                    case COMPANY_CODE_DUBAI:
                        return APP_URLS.getBaseUrlCampaignAppDubai();
                    case COMPANY_CODE_OMAN:
                        return APP_URLS.getBaseUrlCampaignAppOman();
                    case COMPANY_CODE_KUWAIT:
                        return APP_URLS.getBaseUrlCampaignAppKuwait();
                    case COMPANY_CODE_DOHA:
                        return APP_URLS.getBaseUrlCampaignAppDoha();
                }
            } else if (APP_TYPE_QLM.equals(APP_TYPE)) {
                switch (companyCode) {
                    case COMPANY_CODE_MED_DOHA:
                        return APP_URLS.getBaseUrlCampaignAppMedDoha();
                    case COMPANY_CODE_MED_OMAN:
                        return APP_URLS.getBaseUrlCampaignAppMedOman();
                }
            }
        }
        return null;
    }

    public static String getRegex(String companyCode) {
        if (null != companyCode) {
            switch (companyCode) {
                case COMPANY_CODE_BEEMA:
                case COMPANY_CODE_DOHA:
                    return DOHA_MOBILE_REGEX;
                case COMPANY_CODE_DUBAI:
                    return DUBAI_MOBILE_REGEX;
                case COMPANY_CODE_OMAN:
                    return OMAN_MOBILE_REGEX;
                case COMPANY_CODE_KUWAIT:
                    return KUWAIT_MOBILE_REGEX;
            }
        }
        return DOHA_MOBILE_REGEX;
    }


    /**
     * This class holds Company Code, Name and Flag.
     *
     * @author Ravindar Singh T
     * (<a href="mailto:ravindarsingh_t@yahoo.com">ravindarsingh_t@yahoo.com</a>)
     */
    static class Company {

        private String compCode;
        private String compName;
        private String compFlag;

        public Company(String compCode, String compName, String compFlag) {
            this.compCode = compCode;
            this.compName = compName;
            this.compFlag = compFlag;
        }

        public String getCompCode() {
            return compCode;
        }

        public void setCompCode(String compCode) {
            this.compCode = compCode;
        }

        public String getCompName() {
            return compName;
        }

        public void setCompName(String compName) {
            this.compName = compName;
        }

        public String getCompFlag() {
            return compFlag;
        }

        public void setCompFlag(String compFlag) {
            this.compFlag = compFlag;
        }

        ////added for corporate

    public static final String CAMPAIGN_ID_DOHA = "5";
    public static final String CAMPAIGN_ID_DUBAI = "4";
    public static final String CAMPAIGN_ID_OMAN = "8";
    public static final String CAMPAIGN_ID_BEEMA = "10";
    public static final String CAMPAIGN_ID_MED_DOHA = "7";
    public static final String CAMPAIGN_ID_MED_OMAN = "9";

    public static final String CRM_ID_DOHA = "R_001";
    public static final String CRM_ID_DUBAI = "R_002";
    public static final String CRM_ID_KUWAIT = "R_005";
    public static final String CRM_ID_OMAN = "R_006";
    public static final String CRM_ID_BEEMA = "R_100";
    public static final String CRM_ID_MED_DOHA = "Q_009";
    public static final String CRM_ID_MED_OMAN = "Q_006";

    /**
     * Get CRM ID based on campaign ID
     *
     * @param campaignId
     * @return
     */
    public static String getCrmId(final String campaignId) {
        if (null != campaignId) {
            switch (campaignId) {
                case CAMPAIGN_ID_BEEMA:
                    return CRM_ID_BEEMA;
                case CAMPAIGN_ID_DOHA:
                    return CRM_ID_DOHA;
                case CAMPAIGN_ID_DUBAI:
                    return CRM_ID_DUBAI;
                case CAMPAIGN_ID_OMAN:
                    return CRM_ID_OMAN;
                case CAMPAIGN_ID_MED_DOHA:
                    return CRM_ID_MED_DOHA;
                case CAMPAIGN_ID_MED_OMAN:
                    return CRM_ID_MED_OMAN;
            }
        }
        return null;
    }

    public static final String MOBILE_COUNTRY_CODE_DOHA = "974";
    public static final String MOBILE_COUNTRY_CODE_DUBAI = "971";
    public static final String MOBILE_COUNTRY_CODE_OMAN = "968";
    public static final String MOBILE_COUNTRY_CODE_KUWAIT = "965";

    /**
     * Get mobile country code based on CRM ID
     *
     * @param crmId
     * @return
     */
    public static String getMobileCountryCode(final String crmId) {
        if (null != crmId) {
            switch (crmId) {
                case CRM_ID_BEEMA:
                case CRM_ID_DOHA:
                case CRM_ID_MED_DOHA:
                    return MOBILE_COUNTRY_CODE_DOHA;
                case CRM_ID_DUBAI:
                    return MOBILE_COUNTRY_CODE_DUBAI;
                case CRM_ID_KUWAIT:
                    return MOBILE_COUNTRY_CODE_KUWAIT;
                case CRM_ID_OMAN:
                case CRM_ID_MED_OMAN:
                    return MOBILE_COUNTRY_CODE_OMAN;
            }
        }
        return null;
    }
}

}
