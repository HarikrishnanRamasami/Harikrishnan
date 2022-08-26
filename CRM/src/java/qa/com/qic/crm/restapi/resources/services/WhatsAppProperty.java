/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.crm.restapi.resources.services;

import qa.com.qic.common.util.ApplicationConstants;

/**
 *
 * @author ravindar.singh
 */
public abstract class WhatsAppProperty {

    public static final String BASE_PATH = "/omni/1";
    public static final String RESOURCE_ADVANCED = BASE_PATH + "/advanced";

    /*
     * Local URL http://172.20.101.56:8081
     */
    public static final String NOTIFY_BASE_URL = "https://www.uat.qatarinsurance.com";
    public static final String NOTIFY_RESOURCE_MESSAGE = "/messages";
    public static final String NOTIFY_AUTH_USERNAME = "ravindar";
    public static final String NOTIFY_AUTH_PASSWORD = "Ravi@2019";

    // Supported image types are JPG, JPEG, PNG.
    public static final String SUPPORTED_IMAGE_TYPES = "(?i)(jpe?g|png)$";
    // Supported audio types.
    public static final String SUPPORTED_AUDIO_TYPES = "(?i)(aac|mp4|amr|mp3|opus)$";
    // Supported document types.
    public static final String SUPPORTED_DOC_TYPES = "(?i)(pdf|doc|ppt|xls)$";

    public static final String LANGUAGE_EN = "en";
    public static final String LANGUAGE_AR = "ar";

    public static final int COUNTRY_CODE_QATAR = 974;
    public static final int COUNTRY_CODE_DUBAI = 971;

    public static final String ENGATI_RETRIEVE_CHAT_HISTORY_URL = "https://api.engati.com/bot-api/v1.0/customer/{customerId}/bot/{botKey}/channel-user/{channeluserId}/conversations";
    public static final String ENGATI_TEMPLATE_URL = "https://api.engati.com/whatsapp-api/v1.0/customer/{customerId}/bot/{botKey}/template";

    public static enum Vendor {

        INFOBIP, ENGATI
    }

    public abstract String getBaseUrl();

    public String getBasePath() {
        return BASE_PATH;
    }

    public String getResourceAdvanced() {
        return RESOURCE_ADVANCED;
    }

    public String getNotifyBaseUrl() {
        return NOTIFY_BASE_URL;
    }

    public String getNotifyResourceMessage() {
        return NOTIFY_RESOURCE_MESSAGE;
    }

    public String getNotifyAuthUsername() {
        return NOTIFY_AUTH_USERNAME;
    }

    public String getNotifyAuthPassword() {
        return NOTIFY_AUTH_PASSWORD;
    }

    public abstract String getAuthUsername();

    public abstract String getAuthPassword();

    public abstract String getScenariosChannelWhatsapp();

    public String getSupportedImageTypes() {
        return SUPPORTED_IMAGE_TYPES;
    }

    public String getSupportedAudioTypes() {
        return SUPPORTED_AUDIO_TYPES;
    }

    public String getSupportedDocTypes() {
        return SUPPORTED_DOC_TYPES;
    }

    public String getLanguageEn() {
        return LANGUAGE_EN;
    }

    public String getLanguageAr() {
        return LANGUAGE_AR;
    }

    public abstract EngatiProerties getEngatiProerties();

    public abstract String getDocsBaseUrl();

    public abstract String getBaseFileStorePath();

    public abstract int getCountryCode();

    public Vendor getWhatsAppVendor(final String company) {
        Vendor vendor = Vendor.INFOBIP;
        if (null != company) {
            switch (company) {
                case ApplicationConstants.COMPANY_CODE_BEEMA:
                case ApplicationConstants.COMPANY_CODE_DOHA:
                    vendor = Vendor.ENGATI;
                    break;
                case ApplicationConstants.COMPANY_CODE_DUBAI:
                case ApplicationConstants.COMPANY_CODE_OMAN:
                    vendor = Vendor.INFOBIP;
                    break;
                case ApplicationConstants.COMPANY_CODE_MED_DOHA:
                    vendor = Vendor.INFOBIP;
                    break;
                default:
                    vendor = Vendor.INFOBIP;
            }
        }
        return vendor;
    }
}
