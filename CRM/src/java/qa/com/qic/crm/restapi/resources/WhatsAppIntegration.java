/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.crm.restapi.resources;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.server.container.Identifier;
import org.glassfish.jersey.server.exceptions.TransactionException;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import qa.com.qic.common.vo.TWhatsappLog;
import qa.com.qic.crm.restapi.model.wa.EngatiMessage;
import qa.com.qic.crm.restapi.model.wa.EngatiMessageHistory;
import qa.com.qic.crm.restapi.model.wa.Message;
import qa.com.qic.crm.restapi.model.wa.Notification;
import qa.com.qic.crm.restapi.model.wa.Status;
import qa.com.qic.crm.restapi.resources.services.WhatsAppProperties;
import qa.com.qic.crm.restapi.resources.services.WhatsAppService;
import qa.com.qic.utility.helpers.LogUtil;
import qa.com.qic.utility.helpers.NotifyWhatsAppMessage;
import qa.com.qic.utility.helpers.UrlStreamer;

/**
 * WL_FLEX_01 - Used for Auto message response ID
 * WL_FLEX_02 - Used for store reply message ID
 *
 * @author ravindar.singh
 */
@Path("wa")
@Identifier
public class WhatsAppIntegration {

    private static final Logger logger = LogUtil.getLogger(WhatsAppIntegration.class);

    private WhatsAppService service;
    @Context
    private HttpServletRequest request;

    @POST
    @Path("engati/notification")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response incomingMessageFromEngati(EngatiMessage engatiMessage) throws Exception, IOException {
        logger.info("API - WA => Incoming message from Engati - Enter");
        Response response = null;
        final String dataSource = (String) request.getAttribute("COMPANY");
        service = new WhatsAppService(dataSource);
        ObjectMapper obm = new ObjectMapper();
        if (engatiMessage != null && null != engatiMessage.getExternalPacketType()) {
            logger.debug("API - WA => Incoming {}", engatiMessage.toString());
            switch (engatiMessage.getExternalPacketType()) {
                case START_CHAT:
                    EngatiMessageHistory chatHistory = service.retrieveChatHistFromEngati(engatiMessage);
                    try {
                        TWhatsappLog txnLog = new TWhatsappLog();
                        txnLog.setWlMobileNo(engatiMessage.getUserId());
                        service.insertTxn(txnLog);

                        List<TWhatsappLog> logs = new ArrayList<>();
                        TWhatsappLog log;
                        for (EngatiMessageHistory.Conversations conversation : chatHistory.getConversations()) {
                            log = new TWhatsappLog();
                            log.setWlMsgId(String.valueOf(conversation.getTimestamp().getTime()));
                            log.setWlLogDate(conversation.getTimestamp());
                            log.setWlMobileNo(engatiMessage.getUserId());
                            log.setWlMsgMode("I");
                            String messageType = conversation.getMessageType();
                            if (null != messageType) {
                                switch (messageType) {
                                    case "TEXT":
                                        log.setWlMsgType(Message.Type.TEXT.getMode());
                                        log.setWlText(conversation.getResponse());
                                        break;
                                    case "IMAGE":
                                        log.setWlMsgType(Message.Type.IMAGE.getMode());
                                        break;
                                    case "VIDEO":
                                        log.setWlMsgType(Message.Type.VIDEO.getMode());
                                        break;
                                    case "AUDIO":
                                        log.setWlMsgType(Message.Type.AUDIO.getMode());
                                        break;
                                    case "DOCUMENT":
                                        log.setWlMsgType(Message.Type.DOCUMENT.getMode());
                                        break;
                                    case "LOCATION":
                                        log.setWlMsgType(Message.Type.LOCATION.getMode());
                                        break;
                                    case "AGENT_PARTICIPATION_STATUS":
                                        log.setWlMsgType(Message.Type.AGENT_PARTICIPATION_STATUS.getMode());
                                        String apsResp = conversation.getResponse();
                                        JSONObject apsJson = new JSONObject(apsResp);
                                        log.setWlText(apsJson.getString("PARTICIPATION_MESSAGE"));
                                        break;
                                }
                                if ("(IMAGE|AUDIO|VIDEO|DOCUMENT)".matches(messageType)) {
                                    log.setWlMsgUrl(engatiMessage.getBody().getMedia().getValue());
                                    if(null != engatiMessage.getBody().getText()) {
                                        log.setWlText(engatiMessage.getBody().getText().getValue());
                                    }
                                    String ext = FilenameUtils.getExtension(log.getWlMsgUrl()); //Extract file extension
                                    log.setWlFileExt(ext);
                                }
                            }
                            if (null != log.getWlText()) {
                                byte[] bytes = log.getWlText().getBytes(StandardCharsets.UTF_8);
                                log.setWlText(new String(bytes, StandardCharsets.UTF_8));
                            }
                            if (StringUtils.isNoneBlank(log.getWlMsgUrl())) {
                                UrlStreamer urlStream = new UrlStreamer(log.getWlMsgUrl());
                                try {
                                    WhatsAppProperties whatsAppProperties = new WhatsAppProperties(dataSource);
                                    urlStream.resolveAndStore(whatsAppProperties.getProp().getBaseFileStorePath(), UUID.randomUUID().toString(), log.getWlFileExt());
                                    log.setWlFilePath(urlStream.getFilename());
                                } catch (TransactionException ex) {
                                    log.setWlErrorId("6001");
                                    log.setWlErrorMsg(ex.getMessage());
                                }
                            }
                            logs.add(log);
                        }
                        service.processIncomingMessage(logs);
                        ExecutorService executor = null;
                        try {
                            NotifyWhatsAppMessage nwm = new NotifyWhatsAppMessage(logs);
                            // Timeout of 2 minutes.
                            executor = Executors.newSingleThreadExecutor();
                            executor.invokeAll(Arrays.asList(nwm), 2, TimeUnit.MINUTES);
                        } catch (Exception e) {
                            logger.error("API - WA => Send notify failed");
                        } finally {
                            if (executor != null) {
                                executor.shutdown();
                            }
                        }
                        logger.info("API - WA => Incoming message from Engati - Exit");
                        return Response.status(Response.Status.OK).build();
                    } catch (JSONException err) {
                        logger.error("API - WA => Send notify failed");
                        logger.info("API - WA => Incoming message from Engati - Exit");
                        return Response.status(Response.Status.BAD_REQUEST).build();
                    }
                case USER_MESSAGE:
                    logger.info("API - WA => Incoming message from Engati To Agent - Enter");
                    List<TWhatsappLog> logs = new ArrayList<>();
                    //String wtContactNum = service.fetchWTContactNumByUserKey(engatiMessage.getUserId());
                    TWhatsappLog log = new TWhatsappLog();
                    if(null == engatiMessage.getBody().getTimestamp()) {
                        log.setWlLogDate(new Date());
                    } else {
                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
                        String receivedTimeStamps = df.format(engatiMessage.getBody().getTimestamp());
                        log.setWlLogDate(df.parse(receivedTimeStamps));
                    }
                    //log.setWlMobileNo(String.valueOf(wtContactNum));
                    log.setWlMobileNo(engatiMessage.getUserId());
                    log.setWlMsgMode("I");
                    log.setWlMsgType(engatiMessage.getBody().getPacketType().getMode());
                    log.setWlMsgId(String.valueOf(log.getWlLogDate().getTime()));
                    if (engatiMessage.getBody().getPacketType() == Message.Type.DOCUMENT
                            || engatiMessage.getBody().getPacketType() == Message.Type.IMAGE
                            || engatiMessage.getBody().getPacketType() == Message.Type.AUDIO
                            || engatiMessage.getBody().getPacketType() == Message.Type.VIDEO) {
                        log.setWlMsgUrl(engatiMessage.getBody().getMedia().getValue());
                        if(null != engatiMessage.getBody().getText()) {
                            log.setWlText(engatiMessage.getBody().getText().getValue());
                        }
                        String ext = FilenameUtils.getExtension(log.getWlMsgUrl()); //Extract file extension
                        log.setWlFileExt(ext);
                    } else if (engatiMessage.getBody().getPacketType() == Message.Type.TEXT) {
                        log.setWlText(engatiMessage.getBody().getText().getValue());
                    }
                    if (null != log.getWlText()) {
                        byte[] bytes = log.getWlText().getBytes(StandardCharsets.UTF_8);
                        log.setWlText(new String(bytes, StandardCharsets.UTF_8));
                    }
                    if (StringUtils.isNoneBlank(log.getWlMsgUrl())) {
                        UrlStreamer urlStream = new UrlStreamer(log.getWlMsgUrl());
                        try {
                            WhatsAppProperties whatsAppProperties = new WhatsAppProperties(dataSource);
                            urlStream.resolveAndStore(whatsAppProperties.getProp().getBaseFileStorePath(), UUID.randomUUID().toString(), log.getWlFileExt());
                            log.setWlFilePath(urlStream.getFilename());
                        } catch (TransactionException ex) {
                            log.setWlErrorId("6001");
                            log.setWlErrorMsg(ex.getMessage());
                        }
                    }
                    logs.add(log);
                    service.processIncomingMessage(logs);

                    ExecutorService executor = null;
                    try {
                        NotifyWhatsAppMessage nwm = new NotifyWhatsAppMessage(logs);
                        // Timeout of 2 minutes.
                        executor = Executors.newSingleThreadExecutor();
                        executor.invokeAll(Arrays.asList(nwm), 2, TimeUnit.MINUTES);
                    } catch (Exception e) {
                        logger.error("API - WA => Send notify failed");
                    } finally {
                        if (executor != null) {
                            executor.shutdown();
                        }
                    }
                    logger.info("API - WA => Incoming message from Engati To Agent - Exit");
                    return Response.status(Response.Status.OK).build();
                case STATUS_PACKET:
                    /**
                     * 1001 : EXTERNAL_LIVE_CHAT_DISABLED
                     * 1002 : USER_IS_OUTSIDE_CONVERSATION_WINDOW
                     * 1003 : USER_HAS_CLOSED_THE_CHAT, USER_IS_OUTSIDE_24_HOUR_WINDOW
                     * 1004 : USER_NOT_IN_LIVE_CHAT
                     */
                    if(StringUtils.isNotBlank(engatiMessage.getUserId())) {
                        log = new TWhatsappLog();
                        log.setWlMobileNo(engatiMessage.getUserId());
                        log.setWlErrorId(engatiMessage.getBody().getCode());
                        log.setWlErrorMsg(engatiMessage.getBody().getDescription());
                        service.updateOutgoingMessageStatusByUserId(log);
                        return Response.status(Response.Status.OK).build();
                    }
                    break;
                default:
                    break;
            }
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @POST
    @Path("notification")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response incomingMessage(Notification model) throws Exception, IOException {
        logger.info("API - WA => Incoming message - Enter");
        Response response = null;
        final String dataSource = (String) request.getAttribute("COMPANY");
        final String appType = (String) request.getAttribute("APP_TYPE");
        service = new WhatsAppService(dataSource);
        ObjectMapper ob = new ObjectMapper();
        String res = ob.writeValueAsString(model);
        logger.debug(res);
        //DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //logger.debug("Date: " + dateFormat.format(model.getContents().get(0).getReceivedAt()));
        //for(NotificationContent o : model.getContents()) {
        List<TWhatsappLog> logs = model.getContents().stream().map(o -> {
            TWhatsappLog log = new TWhatsappLog();
            log.setWlCrmId(appType + "_" + dataSource);
            log.setWlMsgId(o.getMessageId());
            log.setWlMobileNo(o.getFrom());
            log.setWlLogDate(o.getReceivedAt());
            log.setWlMsgMode("I");
            if (o.getMessage() != null) {
                log.setWlMsgType(o.getMessage().getType().getMode());
                if (o.getMessage().getType() == Message.Type.DOCUMENT || o.getMessage().getType() == Message.Type.IMAGE || o.getMessage().getType() == Message.Type.VIDEO) {
                    log.setWlText(o.getMessage().getCaption());
                } else {
                    log.setWlText(o.getMessage().getText());
                }
                log.setWlMsgUrl(o.getMessage().getUrl());
                if (null != log.getWlText()) {
                    byte[] bytes = log.getWlText().getBytes(StandardCharsets.UTF_8);
                    log.setWlText(new String(bytes, StandardCharsets.UTF_8));
                }
                if (o.getMessage().getContext() != null) {
                    log.setWlFlex02(o.getMessage().getContext().getId());
                }

                if (StringUtils.isNoneBlank(log.getWlMsgUrl())) {
                    UrlStreamer urlStream = new UrlStreamer(log.getWlMsgUrl());
                    try {
                        WhatsAppProperties whatsAppProperties = new WhatsAppProperties(dataSource);
                        urlStream.setCredentials(urlStream.new Credential(whatsAppProperties.getProp().getAuthUsername(), whatsAppProperties.getProp().getAuthPassword()));
                        urlStream.resolveAndStore(whatsAppProperties.getProp().getBaseFileStorePath(), UUID.randomUUID().toString());
                        log.setWlFilePath(urlStream.getFilename());
                    } catch (TransactionException ex) {
                        log.setWlErrorId("6001");
                        log.setWlErrorMsg(ex.getMessage());
                    }
                }
            } else {
                Status error = o.getError();
                if (error != null && error.getId() != 0) {
                    log.setWlMobileNo(o.getTo());
                    log.setWlErrorId(String.valueOf(error.getId()));
                    log.setWlErrorMsg(error.getDescription());
                }
            }
            if (o.getSeenAt() != null) {
                log.setWlMobileNo(o.getTo());
                log.setWlReadDt(o.getSeenAt());
                log.setWlReadYn("Y");
            }
            if (o.getDoneAt() != null) {
                log.setWlMobileNo(o.getTo());
                log.setWlDeliverdDt(o.getDoneAt());
                log.setWlDeliverdYn("Y");
            }
            return log;
        }).collect(Collectors.toList());
        service.processIncomingMessage(logs);

        ExecutorService executor = null;
        try {
            NotifyWhatsAppMessage nwm = new NotifyWhatsAppMessage(logs);
            // Timeout of 2 minutes.
            executor = Executors.newSingleThreadExecutor();
            executor.invokeAll(Arrays.asList(nwm), 2, TimeUnit.MINUTES);
        } catch (Exception e) {
            logger.error("API - WA => Send notify failed");
        } finally {
            if (executor != null) {
                executor.shutdown();
            }
        }

        logger.info("API - WA => Incoming message - Exit");
        return Response.status(Response.Status.OK).build();
    }

    @Deprecated
    @POST
    @Path("send")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response sendMessage(TWhatsappLog model) {
        final String dataSource = (String) request.getAttribute("COMPANY");
        service = new WhatsAppService(dataSource);
        service.sendMessage(model);
        return Response.status(Response.Status.OK).build();
    }

    @Deprecated
    @POST
    @Path("push-notification")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response pushMessage(List<TWhatsappLog> logs) {
        ExecutorService executor = null;
        try {
            NotifyWhatsAppMessage nwm = new NotifyWhatsAppMessage(logs);
            // Timeout of 2 minutes.
            executor = Executors.newSingleThreadExecutor();
            executor.invokeAll(Arrays.asList(nwm), 2, TimeUnit.MINUTES);
        } catch (Exception e) {
            logger.error("API - WA => Send notify failed");
        } finally {
            if (executor != null) {
                executor.shutdown();
            }
        }
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("docs/{company}/{path: [a-zA-Z0-9\\-_\\./]+}")
    public Response viewDocs(@PathParam("company") final String company, @PathParam("path") final String path) {
        logger.debug("View document. Company: {}, Path: {}", new Object[]{company, path});
        Response response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        try {
            if (StringUtils.isBlank(path)) {
                response = Response.status(Response.Status.BAD_REQUEST).build();
            } else {
                String dataSource = company.split("_")[1];
                WhatsAppProperties whatsAppProperties = new WhatsAppProperties(dataSource);
                File file = new File(whatsAppProperties.getProp().getBaseFileStorePath() + path);
                logger.debug("View doc path: {}", file.toPath());
                if (file.exists() && file.isFile()) {
                    if (file.canRead()) {
                        StreamingOutput stream = (java.io.OutputStream output) -> {
                            try {
                                Files.copy(file.toPath(), output);
                                output.flush();
                            } catch (Exception e) {
                                output.close();
                                throw new WebApplicationException("Oops! - Unable to retrieve the document");
                            }
                        };
                        String ext = FilenameUtils.getExtension(file.getName());
                        String mediaType = findMediaType(ext);
                        return Response.ok(stream, mediaType)
                                .header("Content-Length", file.length())
                                .header("Content-Disposition", "inline; filename=" + file.getName()).build();
                    } else {
                        response = Response.status(Response.Status.NO_CONTENT).build();
                    }
                } else {
                    response = Response.status(Response.Status.NOT_FOUND).build();
                }
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return response;
    }

    @GET
    @Path("docs/{path: [a-zA-Z0-9\\-_\\./]+}")
    public Response viewDocs(@PathParam("path") final String path) {
        logger.debug("View document. Path: {}", new Object[]{path});
        Response response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        try {
            if (StringUtils.isBlank(path)) {
                response = Response.status(Response.Status.BAD_REQUEST).build();
            } else {
                final String dataSource = (String) request.getAttribute("COMPANY");
                WhatsAppProperties whatsAppProperties = new WhatsAppProperties(dataSource);
                File file = new File(whatsAppProperties.getProp().getBaseFileStorePath() + path);
                if (file.exists() && file.isFile()) {
                    if (file.canRead()) {
                        StreamingOutput stream = (java.io.OutputStream output) -> {
                            try {
                                Files.copy(file.toPath(), output);
                                output.flush();
                            } catch (Exception e) {
                                output.close();
                                throw new WebApplicationException("Oops! - Unable to retrieve the document");
                            }
                        };
                        String ext = FilenameUtils.getExtension(file.getName());
                        String mediaType = findMediaType(ext);
                        return Response.ok(stream, mediaType)
                                .header("Content-Length", file.length())
                                .header("Content-Disposition", "inline; filename=" + file.getName()).build();
                    } else {
                        response = Response.status(Response.Status.NO_CONTENT).build();
                    }
                } else {
                    response = Response.status(Response.Status.NOT_FOUND).build();
                }
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return response;
    }

    private String findMediaType(String ext) {
        String mediaType = MediaType.APPLICATION_OCTET_STREAM;
        if (null != ext) {
            ext = ext.toLowerCase();
            switch (ext) {
                case "jpeg":
                case "jpg":
                    mediaType = "image/jpeg";
                    break;
                case "png":
                    mediaType = "image/png";
                    break;
                case "pdf":
                    mediaType = "application/pdf";
                    break;
                case "xls":
                    mediaType = "application/vnd.ms-excel";
                    break;
                case "xlsx":
                    mediaType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
                    break;
                case "doc":
                    mediaType = "application/msword";
                    break;
                case "docx":
                    mediaType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
                    break;
                case "ppt":
                    mediaType = "application/vnd.ms-powerpoint";
                    break;
                case "pptx":
                    mediaType = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
                    break;
                case "mp4":
                    mediaType = "video/mp4";
                    break;
                case "aac":
                    mediaType = "audio/aac";
                    break;
                case "mp3":
                    mediaType = "audio/mp3";
                    break;
                case "amr":
                    mediaType = "audio/amr";
                    break;
                case "ogg":
                    mediaType = "audio/ogg";
                    break;
            }
        }
        return mediaType;
    }
}
