/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.anoud.config;

import java.util.ArrayList;
import java.util.List;

import qa.com.qic.anoud.vo.KeyValue;

/**
 *
 * @author ravindar.singh
 */
public class FieldConstants {

    public static enum AppCodes {

        DESIGNATION("DESIGNATION"),
        COUNTRY("COUNTRY"),
        STATE("STATE"),
        OCCUPATION("OCCUPATION"),
        NATIONALITY("NATIONALITY"),
        CRM_LOG_TYPE("CRM_LOG_TYPE"),
        CUST_TYPE("PROMO_CATG"),
        FEEDBACK_TYPE("CRM_FEEDBACK"),
        CRM_HAND_SMS("CRM_HAND_SMS"),
        CRM_CATEGORY("CRM_CATG"),
        CRM_SUB_CATEGORY("CRM_SUB_CATG"),
        CRM_ACT_TYPE("CRM_ACT_TYPE"),
        CRM_OPP_TYPE("CRM_OPP_TYPE"),
        REN_FOLLOWUP("REN_FOLLOWUP"),
        LEADS_DS("LEADS_DS"),
        CRM_TEAM("CRM_TEAM"),
        CRM_LEVEL("CRM_LEVEL"),
        PROMO_CATG("PROMO_CATG"),
        CRM_CALL_TYPE("CRM_CALL_TYP"),
        CRM_CALL_DNIS("CRM_DNIS"),
        CRM_HOLD_TYP("CRM_HOLD_TYP"),
        MK_FRM_COLOR("MK_FRM_COLOR"),
        //added for corporate
        CRM_TARGET_LEVEL("CRM_TRGT_LVL"),
        CRM_TARGET_TYPE("CRM_TRGT_TYP"),
        CRM_CONTACT_TYPE("CRM_CONT_TYP"),
        CRM_DEAL_LOST_REASON("CRM_LOST_DL");


        String code;

        AppCodes(String code) {
            this.code = code;
        }

        public String getCode() {
            return this.code;
        }
    }

    public static enum AppParameter {

        GENDER("GENDER"),
        CUST_SOURCE("CUST_SOURCE"),
        CRM_MODULE("CRM_MODULE"),
        CRM_FILTER("CRM_FILTER"),
        CRM_OPERATOR("CRM_OPERATOR"),
        CRM_PRIORITY("CRM_PRIORITY"),
        CRM_CUST_SRC("APP_PORTAL"),
        DATA_SOURCE("MK_DATA_SRC"),
        CAMP_TYPE("MK_CAMP_TYPE"),
        SCHEDULE_MODE("MK_SCH_MODE"),
        CAMP_STATUS("MK_STATUS"),
        ACTION_TYPE("MK_ACTN_TYPE"),
      //added for corporate
        APP_TYPE("CRM_TYPE"),
    	CORP_QUOT_MODE("QUOT_MODE");
        String code;

        AppParameter(String code) {
            this.code = code;
        }

        public String getCode() {
            return this.code;
        }
    }

    public static enum AppConfig {

        BOT_KEY_FAQ("BOT_KEY_FAQ"),
        CRM_TASK_FWD("CRM_TASK_FWD");
        String code;

        AppConfig(String code) {
            this.code = code;
        }

        public String getCode() {
            return this.code;
        }
    }

    public static final List<KeyValue> AGENT_TYPE_LIST = new ArrayList<KeyValue>() {
        {
            add(new KeyValue("1", "Phone Function"));
            add(new KeyValue("2", "Phone Function - Whatsapp"));
            add(new KeyValue("3", "Phone Function - OFF"));
            add(new KeyValue("4", "Others"));
        }
    };

    public static final List<KeyValue> USER_TYPE_LIST = new ArrayList<KeyValue>() {
        {
            add(new KeyValue("U", "User"));
            add(new KeyValue("P", "User(%)"));
            add(new KeyValue("R", "Team"));
        }
    };




    public static final List<KeyValue> START_TIME_LIST = new ArrayList<KeyValue>() {
        {
            add(new KeyValue("0", "0"));
            add(new KeyValue("1", "1"));
            add(new KeyValue("2", "2"));
            add(new KeyValue("3", "3"));
            add(new KeyValue("4", "4"));
            add(new KeyValue("5", "5"));
            add(new KeyValue("6", "6"));
            add(new KeyValue("7", "7"));
            add(new KeyValue("8", "8"));
            add(new KeyValue("9", "9"));
            add(new KeyValue("10", "10"));
            add(new KeyValue("11", "11"));
            add(new KeyValue("12", "12"));
            add(new KeyValue("13", "13"));
            add(new KeyValue("14", "14"));
            add(new KeyValue("15", "15"));
            add(new KeyValue("16", "16"));
            add(new KeyValue("17", "17"));
            add(new KeyValue("18", "18"));
            add(new KeyValue("19", "19"));
            add(new KeyValue("20", "20"));
            add(new KeyValue("21", "21"));
            add(new KeyValue("22", "22"));
            add(new KeyValue("23", "23"));
            add(new KeyValue("24", "24"));

        }
    };

    public static final List<KeyValue> CAMPAIGN_ACTION_LIST = new ArrayList<KeyValue>() {
        {
            add(new KeyValue("MB", "Bounced"));
            add(new KeyValue("MO", "Opened"));
            add(new KeyValue("MR", "Replied"));
            add(new KeyValue("MC", "Clicked"));
            add(new KeyValue("MCD", "Converted"));
            add(new KeyValue("MS", "Sent"));
        }
    };

}
