/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.common.util.config;

/**
 *
 * @author ravindar.singh
 */
public class MvpAppConfigQA implements AppConfig {

    static final String BASE_URL_MVP = "https://www.demo.anoudtechnologies.com";//, "http://10.170.1.229:8080";
    public static final String BASE_URL_ANOUD_APP_DOHA = BASE_URL_MVP + "/AnoudPlus";
    public static final String BASE_URL_ANOUD_APP_DUBAI = BASE_URL_MVP + "/AnoudPlus";
    public static final String BASE_URL_ANOUD_APP_OMAN = BASE_URL_MVP + "/AnoudPlus";
    public static final String BASE_URL_ANOUD_APP_KUWAIT = BASE_URL_MVP + "/AnoudPlus";
    public static final String BASE_URL_ANOUD_APP_BEEMA = "";
    public static final String BASE_URL_ANOUD_APP_MED_DOHA = BASE_URL_MVP + "/Medical";
    public static final String BASE_URL_ANOUD_APP_MED_OMAN = BASE_URL_MVP + "/Medical";

    public static final String BASE_URL_CAMPAIGN_APP_DOHA = "https://www.uat.qatarinsurance.com/campaign";
    public static final String BASE_URL_CAMPAIGN_APP_DUBAI = "https://www.uat.qatarinsurance.com/campaign";
    public static final String BASE_URL_CAMPAIGN_APP_OMAN = "https://www.uat.qatarinsurance.com/campaign";
    public static final String BASE_URL_CAMPAIGN_APP_KUWAIT = "https://www.uat.qatarinsurance.com/campaign";
    public static final String BASE_URL_CAMPAIGN_APP_BEEMA = "https://www.uat.qatarinsurance.com/campaign";
    public static final String BASE_URL_CAMPAIGN_APP_MED_DOHA = "https://www.uat.qatarinsurance.com/campaign";
    public static final String BASE_URL_CAMPAIGN_APP_MED_OMAN = "https://www.uat.qatarinsurance.com/campaign";

    @Override
    public String getBaseUrlAnoudAppDoha() {
        return BASE_URL_ANOUD_APP_DOHA;
    }

    @Override
    public String getBaseUrlAnoudAppDubai() {
        return BASE_URL_ANOUD_APP_DUBAI;
    }

    @Override
    public String getBaseUrlAnoudAppOman() {
        return BASE_URL_ANOUD_APP_OMAN;
    }

    @Override
    public String getBaseUrlAnoudAppKuwait() {
        return BASE_URL_ANOUD_APP_KUWAIT;
    }

    @Override
    public String getBaseUrlAnoudAppBeema() {
        return BASE_URL_ANOUD_APP_BEEMA;
    }

    @Override
    public String getBaseUrlAnoudAppMedDoha() {
        return BASE_URL_ANOUD_APP_MED_DOHA;
    }

    @Override
    public String getBaseUrlAnoudAppMedOman() {
        return BASE_URL_ANOUD_APP_MED_OMAN;
    }

    @Override
    public String getBaseUrlCampaignAppDoha() {
        return BASE_URL_CAMPAIGN_APP_DOHA;
    }

    @Override
    public String getBaseUrlCampaignAppDubai() {
        return BASE_URL_CAMPAIGN_APP_DUBAI;
    }

    @Override
    public String getBaseUrlCampaignAppOman() {
        return BASE_URL_CAMPAIGN_APP_OMAN;
    }

    @Override
    public String getBaseUrlCampaignAppKuwait() {
        return BASE_URL_CAMPAIGN_APP_KUWAIT;
    }

    @Override
    public String getBaseUrlCampaignAppBeema() {
        return BASE_URL_CAMPAIGN_APP_BEEMA;
    }

    @Override
    public String getBaseUrlCampaignAppMedDoha() {
        return BASE_URL_CAMPAIGN_APP_MED_DOHA;
    }

    @Override
    public String getBaseUrlCampaignAppMedOman() {
        return BASE_URL_CAMPAIGN_APP_MED_OMAN;
    }
}
