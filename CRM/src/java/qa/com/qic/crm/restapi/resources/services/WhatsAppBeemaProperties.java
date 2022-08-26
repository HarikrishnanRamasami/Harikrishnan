/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.crm.restapi.resources.services;

/**
 *
 * @author ravindar.singh
 */
public class WhatsAppBeemaProperties extends WhatsAppProperty {

    public static final String BASE_URL = "https://yrp5eg.api.infobip.com";
    public static final String BASE_URL_FROM_ENGATI = "https://app.engati.com/livechat/webhook/ac248ca0271a478f";

    public static final String AUTH_USERNAME = "Beema_Ravindar";
    public static final String AUTH_PASSWORD = "RaviQIC12!@";

    public static final String SCENARIOS_CHANNEL_WHATSAPP = "F6F7E29F501A71D066B9E661D67898F6";

    public static final String DOCS_BASE_URL = "https://www.uat.qatarinsurance.com/mcrm/api/wa/docs/";
    public static final String BASE_FILE_STORE_PATH = "/anoud_app/WhatsApp_Docs/R_100/";

    @Override
    public String getBaseUrl() {
        return BASE_URL;
    }

    @Override
    public String getAuthUsername() {
        return AUTH_USERNAME;
    }

    @Override
    public String getAuthPassword() {
        return AUTH_PASSWORD;
    }

    @Override
    public String getScenariosChannelWhatsapp() {
        return SCENARIOS_CHANNEL_WHATSAPP;
    }

    @Override
    public String getDocsBaseUrl() {
        return DOCS_BASE_URL;
    }

    @Override
    public String getBaseFileStorePath() {
        return BASE_FILE_STORE_PATH;
    }

    @Override
    public int getCountryCode() {
        return COUNTRY_CODE_QATAR;
    }

    @Override
    public EngatiProerties getEngatiProerties() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
