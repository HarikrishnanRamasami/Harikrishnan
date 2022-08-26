/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.crm.restapi.resources.services;

import java.io.IOException;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.filter.LoggingFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import qa.com.qic.anoud.config.FieldConstants;
import qa.com.qic.anoud.dao.AnoudDAO;
import qa.com.qic.anoud.vo.KeyValue;
import qa.com.qic.common.vo.TWhatsappLog;
import qa.com.qic.common.vo.UserInfo;
import qa.com.qic.crm.restapi.model.wa.Contact;
import qa.com.qic.crm.restapi.model.wa.Destination;
import qa.com.qic.crm.restapi.model.wa.EngatiBody;
import qa.com.qic.crm.restapi.model.wa.EngatiMessage;
import qa.com.qic.crm.restapi.model.wa.EngatiMessageBody;
import qa.com.qic.crm.restapi.model.wa.EngatiMessageHistory;
import qa.com.qic.crm.restapi.model.wa.EngatiTemplate;
import qa.com.qic.crm.restapi.model.wa.EngatiTemplateResponse;
import qa.com.qic.crm.restapi.model.wa.Message;
import qa.com.qic.crm.restapi.model.wa.OutgoingMessage;
import qa.com.qic.crm.restapi.model.wa.OutgoingResponse;
import qa.com.qic.crm.restapi.model.wa.OutgoingResponseWrapper;
import qa.com.qic.crm.restapi.model.wa.Template;
import qa.com.qic.crm.restapi.resources.dao.WhatsAppDAO;
import qa.com.qic.crm.restapi.resources.services.WhatsAppProperty.Vendor;
import qa.com.qic.utility.helpers.LogUtil;

/**
 *
 * @author ravindar.singh
 */
public class WhatsAppService {

    private static final Logger logger = LogUtil.getLogger(WhatsAppService.class);

    private final String dataSource;
    private final WhatsAppDAO dao;
    private final WhatsAppProperties whatsAppProperties;
    private transient UserInfo userInfo;

    public WhatsAppService(String dataSource) {
        this.dataSource = dataSource;
        this.dao = new WhatsAppDAO(dataSource);
        this.whatsAppProperties = new WhatsAppProperties(this.dataSource);
    }

    public String getDataSource() {
        return dataSource;
    }

    public void processIncomingMessage(List<TWhatsappLog> logs) {
        dao.processIncomingMessage(logs);
    }

    public Long sendInviteMessage(TWhatsappLog log) {
        Long logId = sendMessage(log);
        if (null != logId) {
            dao.insertTxn(log);
        }
        return logId;
    }

    public Long sendMessage(TWhatsappLog log) {
        Long logId = null;
        try {
            dao.init();
            logId = dao.processOutgoingMessage(log);
            if (null == logId) {
                return null;
            } else if (null == log.getWlFlex01()) {
                dao.resetUnReadMessageFlag(log);
            }
            log.setWlLogId(logId);

            Vendor vendor = whatsAppProperties.getProp().getWhatsAppVendor(getDataSource());
            if (vendor == Vendor.ENGATI) {
                EngatiMessage em = new EngatiMessage();
                EngatiMessageBody emb = new EngatiMessageBody();
                EngatiBody eb = null;
                boolean isTemplate = Boolean.FALSE;
                log.setWlMsgId(String.valueOf(new Date().getTime()));
                if (StringUtils.isNotBlank(log.getWlTemplateId())) {
                    AnoudDAO anoudDao = new AnoudDAO(getDataSource());
                    KeyValue kv = anoudDao.getMAppCodes(FieldConstants.AppCodes.CRM_HAND_SMS, log.getWlTemplateId());
                    EngatiTemplate template = new EngatiTemplate();
                    em.setPhoneNumber(log.getWlMobileNo());
                    EngatiTemplate.Language language = new EngatiTemplate.Language();
                    language.setPolicy("deterministic");
                    List<EngatiTemplate.Component> components = new ArrayList<>();
                    EngatiTemplate.Component component;
                    List<EngatiTemplate.Parameter> parameters;
                    EngatiTemplate.Parameter parameter;
                    boolean isMediaAndInteractive = false;

                    if(isMediaAndInteractive) {
                        component = new EngatiTemplate.Component();
                        parameters = new ArrayList<>();
                        parameter = new EngatiTemplate.Parameter();
                        if(true) {
                            parameter.setType("image");
                            Map<String, String> image = new HashMap<>();
                            image.put("link", "");
                            parameter.setImage(image);
                        } else {
                            parameter.setType("text");
                            parameter.setText("");
                        }
                        parameters.add(parameter);
                        component.setParameters(parameters);
                        component.setType("header");
                        components.add(component);
                    }

                    component = new EngatiTemplate.Component();
                    parameters = new ArrayList<>();
                    if (kv != null) {
                        if (kv.getText() == null) {
                            return null;
                        }
                        isTemplate = Boolean.TRUE;
                        if (kv.getText().contains("@@")) {
                            String tmpIds[] = kv.getText().split("@@");
                            template.setName(tmpIds[0]);
                            language.setCode(tmpIds[1]);
                        } else {
                            template.setNamespace(kv.getText());
                            language.setCode(whatsAppProperties.getProp().getLanguageEn());
                        }
                        template.setNamespace(kv.getInfo3());
                        template.setLanguage(language);
                        if (log.getTemplateData() != null) {
                            log.getTemplateData().forEach((data) -> {
                                EngatiTemplate.Parameter param = new EngatiTemplate.Parameter();
                                param.setType("text");
                                param.setText(data.getValue());
                                parameters.add(param);
                            });
                        }
                        component.setParameters(parameters);
                        component.setType("body");
                        components.add(component);
                    }

                    if(isMediaAndInteractive) {
                        component = new EngatiTemplate.Component();
                        parameters = new ArrayList<>();
                        parameter = new EngatiTemplate.Parameter();
                        parameter.setType("text");
                        parameter.setText("");
                        parameters.add(parameter);
                        component.setParameters(parameters);
                        component.setType("footer");
                        components.add(component);

                        component = new EngatiTemplate.Component();
                        parameters = new ArrayList<>();
                        parameter = new EngatiTemplate.Parameter();
                        parameter.setType("payload");
                        parameter.setPayload("");
                        parameters.add(parameter);
                        component.setParameters(parameters);
                        component.setType("button");
                        component.setIndex(0);
                        component.setSubType("quick_reply");
                        components.add(component);
                    }

                    template.setComponents(components);
                    em.setPayload(template);
                } else {
                    eb = new EngatiBody();
                    eb.setValue(log.getWlText());
                    emb.setText(eb);
                    Message.Type type = Message.Type.fromMode(log.getWlMsgType());
                    if(Message.Type.VOICE == type) {
                        type = Message.Type.AUDIO;
                    }
                    emb.setPacketType(type);
                    if (emb.getPacketType().getCode().matches("(IMAGE|AUDIO|VIDEO|DOCUMENT)")) {
                        String msgUrl = (whatsAppProperties.getProp().getDocsBaseUrl() + log.getWlMsgUrl()).replace("\\", "/");
                        eb = new EngatiBody();
                        eb.setValue(msgUrl);
                        eb.setMimeType(FilenameUtils.getExtension(log.getWlMsgUrl()));
                        emb.setMedia(eb);
                    }
                    emb.setTimestamp(new Date());
                    em.setExternalPacketType(EngatiMessage.EngatiPacketType.AGENT_MESSAGE);
                    em.setBody(emb);
                    em.setPlatform(whatsAppProperties.getProp().getEngatiProerties().getPlatform());
                    //em.setUserId(fetchUserKeyByWTContact(log.getWlMobileNo()));
                    em.setUserId(log.getWlMobileNo());
                    em.setBotKey(whatsAppProperties.getProp().getEngatiProerties().getBotKey());
                    em.setBotIdentifier(whatsAppProperties.getProp().getEngatiProerties().getBotIdentifier());
                }
                logger.debug("API - WA => Outgoing {}", em.toString());

                Response response;
                URI uri;
                Client client;
                Invocation.Builder ib;
                if (isTemplate) {
                    client = prepareClient(null);
                    WebTarget webTarget = client.target(whatsAppProperties.getProp().getEngatiProerties().getChatTemplateUrl())
                            .resolveTemplate("customerId", whatsAppProperties.getProp().getEngatiProerties().getCustomerIdentifier())
                            .resolveTemplate("botKey", whatsAppProperties.getProp().getEngatiProerties().getBotKey());
                    ib = webTarget.request(MediaType.APPLICATION_JSON);
                    ib.header("Authorization", "Basic " + whatsAppProperties.getProp().getEngatiProerties().getWebhookUrlApiKey());
                } else {
                    uri = UriBuilder.fromPath(whatsAppProperties.getProp().getEngatiProerties().getWebhookUrl()).build();
                    HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(whatsAppProperties.getProp().getAuthUsername(), whatsAppProperties.getProp().getAuthPassword());
                    client = prepareClient(feature);
                    ib = client.target(uri).request(MediaType.APPLICATION_JSON);
                }
                ib.property(ClientProperties.FOLLOW_REDIRECTS, Boolean.TRUE);
                response = ib.post(Entity.entity(em, MediaType.APPLICATION_JSON));
                EngatiTemplateResponse t = response.readEntity(EngatiTemplateResponse.class);
                logger.debug("WA - Error response: {}", t);
                if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                    if("1000".equals(t.getStatus().getCode())) {

                    } else {
                        log.setWlErrorId(t.getStatus().getCode());
                        String e;
                        if(null != t.getResponseObject() && null != t.getResponseObject().getErrors() && !t.getResponseObject().getErrors().isEmpty()) {
                            e = t.getResponseObject().getErrors().stream().map(o -> o.getCode() + " - " + o.getDetails()).collect(Collectors.joining(", "));
                        } else {
                            e = t.getStatus().getDesc();
                        }
                        log.setWlErrorMsg(e);
                    }
                } else {
                    log.setWlErrorId(String.valueOf(response.getStatus()));
                    log.setWlErrorMsg("API call failed");
                }
            } else if (vendor == Vendor.INFOBIP) {
                OutgoingMessage om = new OutgoingMessage();
                Destination destination = new Destination();
                Contact contact = new Contact();
                contact.setPhoneNumber(log.getWlMobileNo());
                destination.setTo(contact);
                List<Destination> destinations = new ArrayList<>();
                destinations.add(destination);
                om.setDestinations(destinations);
                Template template = new Template();
                if (StringUtils.isNotBlank(log.getWlTemplateId())) {
                    AnoudDAO anoudDao = new AnoudDAO(getDataSource());
                    KeyValue kv = anoudDao.getMAppCodes(FieldConstants.AppCodes.CRM_HAND_SMS, log.getWlTemplateId());
                    if (kv != null) {
                        if (kv.getText() == null) {
                            return null;
                        }
                        if (kv.getText().contains("@@")) {
                            String tmpIds[] = kv.getText().split("@@");
                            template.setTemplateName(tmpIds[0]);
                            template.setLanguage(tmpIds[1]);
                        } else {
                            template.setTemplateName(kv.getText());
                            template.setLanguage(whatsAppProperties.getProp().getLanguageEn());
                        }
                        LinkedList<String> params = new LinkedList<>();
                        if (log.getTemplateData() != null) {
                            log.getTemplateData().forEach((data) -> {
                                params.add(data.getValue());
                            });
                        }
                        template.setTemplateData(params);
                    }
                } else {
                    template.setText(log.getWlText());
                    String msgUrl = (whatsAppProperties.getProp().getDocsBaseUrl() + log.getWlMsgUrl()).replace("\\", "/");
                    if (Message.Type.IMAGE.getMode().equals(log.getWlMsgType())) {
                        template.setImageUrl(msgUrl);
                    } else if (Message.Type.VOICE.getMode().equals(log.getWlMsgType())) {
                        template.setAudioUrl(msgUrl);
                    } else if (Message.Type.DOCUMENT.getMode().equals(log.getWlMsgType()) || Message.Type.VIDEO.getMode().equals(log.getWlMsgType())) {
                        template.setFileUrl(msgUrl);
                    }
                }
                om.setScenarioKey(whatsAppProperties.getProp().getScenariosChannelWhatsapp());
                om.setWhatsApp(template);
                try {
                    ObjectMapper ob = new ObjectMapper();
                    String res = ob.writeValueAsString(om);
                    logger.debug(res);
                } catch (JsonProcessingException ex) {
                    logger.error("", ex);
                }

                Response response;
                final URI uri = UriBuilder.fromPath(whatsAppProperties.getProp().getBaseUrl() + whatsAppProperties.getProp().getResourceAdvanced()).build();
                HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(whatsAppProperties.getProp().getAuthUsername(), whatsAppProperties.getProp().getAuthPassword());
                Client client = prepareClient(feature);
                Invocation.Builder ib = client.target(uri).request(MediaType.APPLICATION_JSON);
                ib.property(ClientProperties.FOLLOW_REDIRECTS, Boolean.TRUE);
                response = ib.post(Entity.entity(om, MediaType.APPLICATION_JSON));
                if (response.getStatus() == Response.Status.OK.getStatusCode() || response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode()) {
                    /*String t = response.readEntity(String.class);
                    logger.debug("Response: " + t);
                    OutgoingResponseWrapper or = null;*/
                    OutgoingResponseWrapper orw = response.readEntity(OutgoingResponseWrapper.class);
                    try {
                        ObjectMapper ob = new ObjectMapper();
                        //or = ob.readValue(t, OutgoingResponseWrapper.class);
                        String t = ob.writeValueAsString(orw);
                        logger.debug("Response: {}", t);
                    } catch (IOException ex) {
                        logger.error("", ex);
                    }
                    if (null != orw && null != orw.getMessages() && !orw.getMessages().isEmpty()) {
                        OutgoingResponse or = orw.getMessages().get(0);
                        logger.debug("Response message id is {}", new Object[]{or.getMessageId()});
                        log.setWlMsgId(or.getMessageId());
                        if (or.getStatus() != null && "REJECTED".equals(or.getStatus().getGroupName())) {
                            log.setWlErrorId("400");
                            log.setWlErrorMsg(or.getStatus().getDescription());
                        }
                    } else {
                        log.setWlErrorId("6002");
                        log.setWlErrorMsg("Unable to find message id");
                    }
                    if (null != orw && null != orw.getRequestError() && null != orw.getRequestError().getServiceException()) {
                        log.setWlErrorId(String.valueOf(response.getStatus()));
                        log.setWlErrorMsg(orw.getRequestError().getServiceException().getText());
                    }
                } else {
                    log.setWlErrorId(String.valueOf(response.getStatus()));
                    log.setWlErrorMsg("API call failed");
                    String t = response.readEntity(String.class);
                    logger.debug("WA - Error response: {}", t);
                }
            }
            dao.updateOutgoingMessageStatus(log);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            dao.destroy();
        }
        return logId;
    }

    public void updateOutgoingMessageStatusByUserId(TWhatsappLog log) {
        try {
            dao.init();
            dao.updateOutgoingMessageStatusByUserId(log);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            dao.destroy();
        }
    }

    public void insertTxn(TWhatsappLog log) {
        dao.insertTxn(log);
    }

    public EngatiMessageHistory retrieveChatHistFromEngati(EngatiMessage engatiMessage) {
        EngatiMessageHistory response = null;
        try {
            Timestamp wlLogLastDate = dao.fetchLastWLDate(engatiMessage.getUserId());
            Client client = ClientBuilder.newClient().register(LoggingFilter.class);
            WebTarget webTarget = client.target(whatsAppProperties.getProp().getEngatiProerties().getChatHistoryUrl())
                    .resolveTemplate("customerId", whatsAppProperties.getProp().getEngatiProerties().getCustomerIdentifier())
                    .resolveTemplate("botKey", whatsAppProperties.getProp().getEngatiProerties().getBotKey())
                    .resolveTemplate("channeluserId", engatiMessage.getUserId())
                    .queryParam("start_date", String.valueOf(wlLogLastDate.getTime()));
            Invocation.Builder ib = webTarget.request(MediaType.APPLICATION_JSON);
            ib.header("Authorization", "Basic " + whatsAppProperties.getProp().getEngatiProerties().getWebhookUrlApiKey());
            response = ib.get(EngatiMessageHistory.class);
            logger.debug("Response message is {}", new Object[]{response});
        } catch (Exception e) {
            logger.error("", e);
        }
        return response;
    }

    public boolean chatResolveFromEngati(String userId) {
        boolean chatResolve = false;
        EngatiMessage em = new EngatiMessage();
        em.setExternalPacketType(EngatiMessage.EngatiPacketType.RESOLVE_CHAT);
        em.setPlatform(whatsAppProperties.getProp().getEngatiProerties().getPlatform());
        em.setUserId(userId);
        em.setBotKey(whatsAppProperties.getProp().getEngatiProerties().getBotKey());
        em.setBotIdentifier(whatsAppProperties.getProp().getEngatiProerties().getBotIdentifier());
        final URI uri = UriBuilder.fromPath(whatsAppProperties.getProp().getEngatiProerties().getWebhookUrl()).build();
        Client client = ClientBuilder.newClient().register(LoggingFilter.class);
        Invocation.Builder ib = client.target(uri).request(MediaType.APPLICATION_JSON);
        ib.header("Authorization", "Basic " + whatsAppProperties.getProp().getEngatiProerties().getWebhookUrlApiKey());
        ib.property(ClientProperties.FOLLOW_REDIRECTS, Boolean.TRUE);
        Response response = ib.post(Entity.entity(em, MediaType.APPLICATION_JSON));
        String t = response.readEntity(String.class);
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            chatResolve = true;
            logger.debug("WA - Response: {}", new Object[]{t});
        } else {
            logger.debug("WA - Resolve chat failure. Code: {}, Response: {}", new Object[]{response.getStatus(), t});
        }
        return chatResolve;
    }

    /*public String fetchWTContactNumByUserKey(String userId) {
        return dao.fetchWTContactByUserKey(userId);
    }

    public String fetchUserKeyByWTContact(String wtContact) {
        return dao.fetchUserKeyByWTContact(wtContact);
    }*/

    public Vendor validateVendor() {
        return whatsAppProperties.getProp().getWhatsAppVendor(getDataSource());
    }

    private Client prepareClient(HttpAuthenticationFeature feature) {
        Client client;
        if (false) {
            client = ClientBuilder.newClient();
        } else {
            SSLContext sslcontext = null;
            TrustManager[] trustAllCerts = new X509TrustManager[]{new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};
            try {
                sslcontext = SSLContext.getInstance("SSL");
                sslcontext.init(null, trustAllCerts, null);
            } catch (NoSuchAlgorithmException | KeyManagementException e) {
            }
            client = ClientBuilder.newBuilder()
                    .sslContext(sslcontext)
                    .hostnameVerifier((s1, s2) -> true)
                    .build();
        }
        if (null != feature) {
            client.register(feature);
        }
        client.register(LoggingFilter.class);
        return client;
    }
}
