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
public interface ActiveDirectory {
    public static final String INITIAL_CONTEXT_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";

    //Parameter names
    static enum AD_PARAMS {
        DOMAIN, PROVIDER_URL, SEARCHBASE, FILTER, LANGUAGE, AUTHENTICATION, DATA;
    }

    //Attribute names
    static enum AD_ATTR {
        NAME("displayName"), DISTINGUISHED_NAME("distinguishedName"), TITLE("title"), TELE_PHONE("telephoneNumber"), USER_EMAIL("mail"), ACCOUNT_NAME("sAMAccountName");
        private String code;
        AD_ATTR(String code) {
            this.code = code;
        }
        String getCode() {
            return this.code;
        }
    }
}
