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
public class WhatsAppRetailDohaProperties extends WhatsAppProperty {

    public static final String BASE_URL = "https://32pgj.api.infobip.com";

    public static final String AUTH_USERNAME = "QIC_Ravindar";
    public static final String AUTH_PASSWORD = "Ravi12!@";
    public static final String SCENARIOS_CHANNEL_WHATSAPP = "658F4AFFB01A7A0DF8403959A06BABB5";

    public static final String DOCS_BASE_URL = "https://www.uat.qatarinsurance.com/crm/api/wa/docs/";
    public static final String BASE_FILE_STORE_PATH = "c:/anoud_app/WhatsApp_Docs/R_001/";

    public static final String ENGATI_WEBHOOK_URL = "https://app.engati.com/livechat/webhook/ac248ca0271a478f";
    public static final String ENGATI_WEBHOOK_URL_API_KEY = "c63e03d7-7fb9-46a7-89e2-30c72203b657-Gl50bnD";
    public static final String ENGATI_PLATFORM = "dialog360";
    public static final String ENGATI_CUSTOMER_IDENTIFIER = "58588";
    public static final String ENGATI_BOT_KEY = "ac248ca0271a478f";
    public static final String ENGATI_BOT_IDENTIFIER = "eyJib3RSZWYiOjY4ODU1LCJ1c2Vyc0JvdFJlZiI6Njg4NTV9";

    private final EngatiProerties engatiProerties = new EngatiProerties();

    public WhatsAppRetailDohaProperties() {
        engatiProerties.setBotKey(ENGATI_BOT_KEY);
        engatiProerties.setBotIdentifier(ENGATI_BOT_IDENTIFIER);
        engatiProerties.setCustomerIdentifier(ENGATI_CUSTOMER_IDENTIFIER);
        engatiProerties.setPlatform(ENGATI_PLATFORM);
        engatiProerties.setWebhookUrl(ENGATI_WEBHOOK_URL);
        engatiProerties.setWebhookUrlApiKey(ENGATI_WEBHOOK_URL_API_KEY);
        engatiProerties.setChatHistoryUrl(ENGATI_RETRIEVE_CHAT_HISTORY_URL);
        engatiProerties.setChatTemplateUrl(ENGATI_TEMPLATE_URL);
    }

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
        return engatiProerties;
    }

}
