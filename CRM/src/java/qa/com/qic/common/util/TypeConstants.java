/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.common.util;

/**
 *
 * @author ravindar.singh
 */
public class TypeConstants {

    public static enum Operation {

        SAVE,
        UPDATE,
        DELETE,
        LOAD,
        CREATE
    }

    public static enum Menu {

        DASHBOARD,
        CUSTOMER360,
        TASKS,
        ACTIVITIES,
        LEADS,
        OPPORTUNITIES,
        CONTACT,
        REPORTS,
        SEARCH,
        ADMIN,
        MONITOR,
        CRM,
        DIGITALMARKETING,
        KPI
    }

	 public static enum DateFilter {
		    CURRENT("current"),
		    FUTURE("future"),
		    PAST("past");

		  private String code;

		  DateFilter(String code) {
	            this.code = code;
	        }
	        public String getCode() {
	            return code;
	        }
	 }

	 public static enum InsuredStatus {
		    ACTIVE("Active"),
		    IN_ACTIVE("In-Active"),
		    UNKNOWN("N/A");

	        private String code;

	        InsuredStatus(String code) {
	            this.code = code;
	        }
	        public String getCode() {
	            return code;
	        }

	 }

	 public static enum CrmContactType {
		    LEAD("001"),
		    CUSTOMER("002"),
		    PRIORITY_CUSTOMER("003");

	        private String code;

	        CrmContactType(String code) {
	            this.code = code;
	        }
	        public String getCode() {
	            return code;
	        }
	 }

	 public static enum CrmDealStatus {
		    OPEN("OPEN"),
		    WON("WON"),
		    LOST("LOST");

	        private String code;

	        CrmDealStatus(String code) {
	            this.code = code;
	        }
	        public String getCode() {
	            return code;
	        }
	 }

    public static enum KPIPlanType {

        TARGET("001"),
        SALES("002");

        private String code;

        KPIPlanType(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

    }

  public static enum KPITargetApprovalStatus {

        OPEN("OPEN"),
        MATCHED("MATCHED"),
        APPROVED("APPROVED");

        private String code;

        KPITargetApprovalStatus(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

    }

public static enum KPITargetType {

    GROSS_PREMIUM("001"),
    NET_PREMIUM("002"),
    COMMISIONS("003"),
    EXPENSES("004");

    private String code;
	KPITargetType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}


public static enum KPITargetLevel {

    COMPANY("company"),
    DIVISION("division"),
    DEPARTMENT("department"),
    LOB("lob"),
    PRODUCT("product"),
    CHANNEL("channel"),
    EMPLOYEE("employee");

    private String code;

	KPITargetLevel(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}


    public static enum EmailFormType {

        CUSTOMER("customer"),
        CONTACT("contact");

        private String code;

    	EmailFormType(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    public static enum CrmLogType {

        NEW_POLICY_RENEWALS("000"),
        CLAIM_REG("001"),
        SMS_SENT("002"),
        EMAIL_SENT("003"),
        CALL_INBOUND("004"),
    	CALL_OUTBOUND("005"),
    	SMS_INCOMING("006"),
        CRM_TASK("007"),
        MISSED_CALL("008"),
        COMPLAINTS("009"),
        POLICY_ENDORSEMENT("010"),
        CALL_FORWARDED("011"),
    	APPOINTMENT("012"),
    	ENQUIRY_CREATED("013");

        private String code;

        CrmLogType(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }


    public static enum CallType {

        MISSED("008"),
        INBOUND("004"),
        OUTBOUND("005"),
        FORWARDED("011"),
        ABANDONED("AC");

        private String code;

        CallType(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    public static enum SystemDispositionType {

        CALL_NOT_PICKED("CALL_NOT_PICKED"),
        CALL_HANGUP("CALL_HANGUP"),
        FAILED("FAILED"),
        NO_ANSWER("NO_ANSWER"),
        CONNECTED("CONNECTED"),
        PROVIDER_TEMP_FAILURE("PROVIDER_TEMP_FAILURE"),
        ATTEMPT_FAILED("ATTEMPT_FAILED"),
        NUMBER_FAILURE("NUMBER_FAILURE");

        private String code;

        SystemDispositionType(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    public enum DateRange {

        TODAY("Today", "= TRUNC(SYSDATE) "),
        YESTERDAY("Yesterday", "= TRUNC(SYSDATE - 1) "),
        THIS_WEEK("This week", "BETWEEN TRUNC(SYSDATE, 'DAY') AND TRUNC(SYSDATE, 'DAY') + 6 "),
        LAST_WEEK("Last week", "BETWEEN TRUNC(SYSDATE - 7, 'DAY') AND TRUNC(SYSDATE - 7, 'DAY') + 6 "),
        THIS_MONTH("This month", "BETWEEN TRUNC(SYSDATE, 'MONTH') AND TRUNC(LAST_DAY(SYSDATE)) "),
        LAST_MONTH("Last month", "BETWEEN TRUNC(ADD_MONTHS(SYSDATE, -1), 'MONTH') AND TRUNC(LAST_DAY(ADD_MONTHS(SYSDATE, -1))) "),
        THIS_QUARTER("This quarter", "BETWEEN TRUNC(SYSDATE, 'Q') AND ADD_MONTHS(TRUNC(SYSDATE, 'Q'), 3) - 1 "),
        LAST_QUARTER("Last quarter", "BETWEEN ADD_MONTHS(TRUNC(SYSDATE, 'Q'), -3) AND ADD_MONTHS(ADD_MONTHS(TRUNC(SYSDATE, 'Q'), -3), 3) - 1 "),
        THIS_YEAR("This year", "BETWEEN TRUNC(SYSDATE, 'YEAR') AND ADD_MONTHS(TRUNC(SYSDATE, 'YEAR'), 12) - 1 "),
        LAST_YEAR("Last year", "BETWEEN TRUNC(ADD_MONTHS(SYSDATE, -12), 'YEAR') AND ADD_MONTHS(TRUNC(ADD_MONTHS(SYSDATE, -12), 'YEAR'), 12) - 1 "),
        CUSTOM("Custom", "BETWEEN TRUNC(TO_DATE('?', 'DD/MM/YYYY')) AND TRUNC(TO_DATE('?', 'DD/MM/YYYY'))");

        final String desc;
        String range;

        DateRange(String desc, String range) {
            this.desc = desc;
            this.range = range;
        }

        public String getDesc() {
            return this.desc;
        }

        public String getRange() {
            return this.range;
        }

        public void setRange(String range) {
            this.range = range;
        }
    }
}
