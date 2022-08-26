/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.common.util;

import java.util.EnumMap;
import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.ConfigurationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.PartialResultException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ravindar.singh
 */
public class ActiveDirectoryAuthentication implements ActiveDirectory {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActiveDirectoryAuthentication.class);
    private String language = "en";
    private String authentication = "simple";
    private String providerUrl;
    private String searchBase;
    private String filter;
    private String domain;
    private EnumMap<AD_ATTR, String> data = null;

    public ActiveDirectoryAuthentication() {
        super();
        init("");
    }

    public ActiveDirectoryAuthentication(String companyCode) {
        super();
        init(companyCode);
    }

    private void init(String companyCode) {
        if (null != companyCode) switch (companyCode) {
            case "007":
                /*this.providerUrl = "ldap://172.20.157.10:389";
                this.searchBase = "DC=QREGROUP,DC=NET";
                this.domain = "QREGROUP";*/
                break;
            case "XXX":
                this.providerUrl = "ldap://10.1.1.1:389";
                this.searchBase = "DC=WURTT,DC=COM";
                this.domain = "UK_DOMAIN";
                break;
            default:
                this.providerUrl = "ldap://172.20.100.152:389";
                this.searchBase = "DC=QICHOAPPL,DC=COM";
                this.domain = "QICHOAPPL";
                break;
        }
    }

    public void setProperty(AD_PARAMS type, String value) throws ConfigurationException {
        switch (type) {
            case DOMAIN:
                setDomain(value);
                break;
            case PROVIDER_URL:
                setProviderUrl(value);
                break;
            case SEARCHBASE:
                setSearchBase(value);
                break;
            case FILTER:
                setFilter(value);
                break;
            case LANGUAGE:
                setLanguage(value);
                break;
            case AUTHENTICATION:
                setAuthentication(value);
                break;
            default:
                throw new ConfigurationException("Property Missmatch");
        }
    }

    public Object getProperty(AD_PARAMS type) throws ConfigurationException {
        switch (type) {
            case DOMAIN:
                return getDomain();
            case PROVIDER_URL:
                return getProviderUrl();
            case SEARCHBASE:
                return getSearchBase();
            case FILTER:
                return getFilter();
            case LANGUAGE:
                return getLanguage();
            case AUTHENTICATION:
                return getAuthentication();
            case DATA:
                return getData();
            default:
                throw new ConfigurationException("Property Missmatch");
        }
    }

    public boolean authenticate(String userId, String userPass) throws Exception {
        boolean isValidADUser = false;
        try {
            if (getDomain() == null || getProviderUrl() == null || "".equals(getDomain().trim()) || "".equals(getProviderUrl().trim())) {
                throw new IllegalArgumentException("Required parameter is missing");
            }
            if (userId.matches("[\\*/,+()]+")) {
                throw new IllegalArgumentException("user Id have malformed");
            }
            Hashtable<String, String> params = new Hashtable<>();
            params.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
            params.put(Context.LANGUAGE, getLanguage());
            params.put(Context.PROVIDER_URL, getProviderUrl());
            params.put(Context.SECURITY_AUTHENTICATION, getAuthentication());
            params.put(Context.SECURITY_PRINCIPAL, getDomain() + "\\" + userId);
            params.put(Context.SECURITY_CREDENTIALS, userPass);
            DirContext ctx = new InitialDirContext(params);

            SearchControls constraints = new SearchControls();
            constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
            String lFilter = getFilter();
            if (getFilter() == null || "".equals(getFilter().trim())) {
                lFilter = "sAMAccountName=" + userId;
            }
            NamingEnumeration results = ctx.search(getSearchBase(), lFilter, constraints);

            /*ModificationItem[] mods = new ModificationItem[1];
             Attribute mod0 = new BasicAttribute("userpassword", "a");
             mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, mod0);
             ctx.modifyAttributes("uid=username,ou=Administrator,o=SID", mods);*/
            if (results != null && results.hasMore()) {
                data = new EnumMap<>(AD_ATTR.class);
                SearchResult sr = (SearchResult) results.next();
                LOGGER.info("User {} found in active directroy. User name is {}", new Object[]{userId, sr.getName()});
                Attributes attrs = sr.getAttributes();
                for (AD_ATTR attr : AD_ATTR.values()) {
                    data.put(attr, attrs.get(attr.getCode()) == null ? "" : (String) attrs.get(attr.getCode()).get());
                }
                /*for (Map.Entry<AD_ATTR, String> obj : data.entrySet()) {
                 logger.info(obj.getKey().getCode() + " = " + obj.getValue());
                 }*/
                isValidADUser = true;
            } else {
                LOGGER.info("User {} not found in active directroy.", new Object[]{userId});
                throw new Exception("User not exists");
            }
        } catch (AuthenticationException e) {
            throw new Exception("UserID/Password is invalid");
        } catch (PartialResultException e) {
            throw new Exception("Not exists in LDAP");
        } catch (NamingException e) {
            throw new Exception("Unknown exception occured");
        }
        return isValidADUser;
    }

    private void setLanguage(String language) {
        this.language = language;
    }

    private String getLanguage() {
        return language;
    }

    private void setAuthentication(String authentication) {
        this.authentication = authentication;
    }

    private String getAuthentication() {
        return authentication;
    }

    private void setProviderUrl(String providerUrl) {
        this.providerUrl = providerUrl;
    }

    private String getProviderUrl() {
        return providerUrl;
    }

    private void setSearchBase(String searchBase) {
        this.searchBase = searchBase;
    }

    private String getSearchBase() {
        return searchBase;
    }

    /**
     * @param filter
     *
     * "(objectclass=person)"
     */
    private void setFilter(String filter) {
        this.filter = filter;
    }

    private String getFilter() {
        return filter;
    }

    private String getDomain() {
        return domain;
    }

    private void setDomain(String domain) {
        this.domain = domain;
    }

    private EnumMap<AD_ATTR, String> getData() {
        return data;
    }
}
