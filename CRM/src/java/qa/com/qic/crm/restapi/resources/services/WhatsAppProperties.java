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
public class WhatsAppProperties {

    private final WhatsAppProperty prop;

    public WhatsAppProperties() {
        this.prop = new WhatsAppProperty() {
            @Override
            public String getBaseUrl() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public String getAuthUsername() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public String getAuthPassword() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public String getScenariosChannelWhatsapp() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public String getDocsBaseUrl() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public String getBaseFileStorePath() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public int getCountryCode() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public EngatiProerties getEngatiProerties() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }

    public WhatsAppProperties(String company) {
        if (ApplicationConstants.APP_TYPE_QLM.equals(ApplicationConstants.APP_TYPE)) {
            switch (company) {
                case "009":
                    this.prop = new WhatsAppQLMProperties();
                    break;
                default:
                    this.prop = new WhatsAppQLMProperties();
            }
        } else {
            switch (company) {
                case "001":
                    this.prop = new WhatsAppRetailDohaProperties();
                    break;
                case "002":
                    this.prop = new WhatsAppRetailDubaiProperties();
                    break;
                case "100":
                    this.prop = new WhatsAppBeemaProperties();
                    break;
                default:
                    this.prop = new WhatsAppRetailDohaProperties();
            }
        }
    }

    public WhatsAppProperty getProp() {
        return prop;
    }

    //added for corporate
    public static final String BASE_URL = "https://32pgj.api.infobip.com";
    public static final String BASE_PATH = "/omni/1";
    public static final String RESOURCE_ADVANCED = BASE_PATH + "/advanced";

    public static final String NOTIFY_BASE_URL = "https://www.uat.qatarinsurance.com";
    public static final String NOTIFY_RESOURCE_MESSAGE = "/messages";
    public static final String NOTIFY_AUTH_USERNAME = "ravindar";
    public static final String NOTIFY_AUTH_PASSWORD = "Ravi@2019";

    public static final String AUTH_USERNAME = "QIC_Ravindar";
    public static final String AUTH_PASSWORD = "Ravi12!@";
    public static final String SCENARIOS_CHANNEL_DOHA = "658F4AFFB01A7A0DF8403959A06BABB5";
    public static final String SCENARIOS_CHANNEL_MED_DOHA = "658F4AFFB01A7A0DF8403959A06BABB5";

    // Supported image types are JPG, JPEG, PNG.
    public static final String SUPPORTED_IMAGE_TYPES = "(?i)(jpe?g|png)$";
    // Supported audio types.
    public static final String SUPPORTED_AUDIO_TYPES = "(?i)(aac|mp4|amr|mp3|opus)$";
    // Supported document types.
    public static final String SUPPORTED_DOC_TYPES = "(?i)(pdf|doc|ppt|xls)$";

    public static final String LANGUAGE_EN = "en";
    public static final String LANGUAGE_AR = "ar";

    public static final String DOCS_BASE_URL = "https://www.uat.qatarinsurance.com/crm/api/wa/docs/";
    public static final String BASE_FILE_STORE_PATH = "/anoud_app/WhatsApp_Docs/";

    public static final String getScenariosChannel(final String crmId) {
        if (null != crmId) {
            switch (crmId) {
                case ApplicationConstants.COMPANY_CODE_DOHA:
                    return SCENARIOS_CHANNEL_DOHA;
                case ApplicationConstants.COMPANY_CODE_DOHA_MED_DOHA:
                    return SCENARIOS_CHANNEL_MED_DOHA;
            }
        }
        return null;
    }
}
