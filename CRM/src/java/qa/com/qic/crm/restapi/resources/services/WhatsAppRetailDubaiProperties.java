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
public class WhatsAppRetailDubaiProperties extends WhatsAppProperty {

    public static final String BASE_URL = "https://vj3l4v.api.infobip.com";
    public static final String BASE_URL_FROM_ENGATI = "https://app.engati.com/livechat/webhook/ac248ca0271a478f";

    public static final String AUTH_USERNAME = "UAE_Ravindar";
    public static final String AUTH_PASSWORD = "RaviQIC12!@";
    public static final String SCENARIOS_CHANNEL_WHATSAPP = "61515522394AABD9BA83B68FCE7D670D";

    public static final String DOCS_BASE_URL = "https://www.uat.qatarinsurance.com/crm/api/wa/R_002/docs/";
    public static final String BASE_FILE_STORE_PATH = "/anoud_app/WhatsApp_Docs/R_002/";

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
        return COUNTRY_CODE_DUBAI;
    }

    @Override
    public EngatiProerties getEngatiProerties() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
