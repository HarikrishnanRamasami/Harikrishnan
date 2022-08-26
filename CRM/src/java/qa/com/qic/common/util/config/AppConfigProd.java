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
public class AppConfigProd implements AppConfig {

    public static final String BASE_URL_ANOUD_APP_DOHA = "http://www.doha.anoud.com/QIC";
    public static final String BASE_URL_ANOUD_APP_DUBAI = "http://www.dubai.anoud.com/QIC";
    public static final String BASE_URL_ANOUD_APP_OMAN = "http://www.oman.anoud.com/QIC";
    public static final String BASE_URL_ANOUD_APP_KUWAIT = "http://www.kuwait.anoud.com/QIC";
    public static final String BASE_URL_ANOUD_APP_BEEMA = "http://www.beema.anoud.com/742";
    public static final String BASE_URL_ANOUD_APP_MED_DOHA = "http://www.medical.anoud.com/medical";
    public static final String BASE_URL_ANOUD_APP_MED_OMAN = "http://www.oman.medical.anoud.com/medical";

    public static final String BASE_URL_CAMPAIGN_APP_DOHA = "https://www.qic-insured.com/camp";
    public static final String BASE_URL_CAMPAIGN_APP_DUBAI = "https://www.i-insured.com/camp";
    public static final String BASE_URL_CAMPAIGN_APP_OMAN = "https://www.oqic-insured.com/camp";
    public static final String BASE_URL_CAMPAIGN_APP_KUWAIT = "https://www.kqic-insured.com/camp";
    public static final String BASE_URL_CAMPAIGN_APP_BEEMA = "https://www.beema-online.com/camp";
    public static final String BASE_URL_CAMPAIGN_APP_MED_DOHA = "https://www.qlm-online.com/camp";
    public static final String BASE_URL_CAMPAIGN_APP_MED_OMAN = "https://www.medical.oqic.com/camp";

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
