/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.utility.helpers;

import java.net.URI;
import java.util.List;
import java.util.concurrent.Callable;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriBuilderException;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.filter.LoggingFilter;

import qa.com.qic.common.vo.TWhatsappLog;
import qa.com.qic.crm.restapi.resources.services.WhatsAppProperties;

/**
 *
 * @author ravindar.singh
 */
public class NotifyWhatsAppMessage implements Callable<Boolean> {

    private static final org.apache.logging.log4j.Logger logger = LogUtil.getLogger(NotifyWhatsAppMessage.class);

    private List<TWhatsappLog> logs;
    private TWhatsappLog log;

    public NotifyWhatsAppMessage(TWhatsappLog log) {
        super();
        this.log = log;
    }

    public NotifyWhatsAppMessage(List<TWhatsappLog> logs) {
        super();
        this.logs = logs;
    }

    private void send(TWhatsappLog log) {
        try {
            WhatsAppProperties whatsAppProperties = new WhatsAppProperties();
            final URI uri = UriBuilder.fromPath(whatsAppProperties.getProp().getNotifyBaseUrl() + whatsAppProperties.getProp().getNotifyResourceMessage()).build();
            HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(whatsAppProperties.getProp().getNotifyAuthUsername(), whatsAppProperties.getProp().getNotifyAuthPassword());
            Client client = ClientBuilder.newClient(new ClientConfig(feature)).register(LoggingFilter.class);
            Invocation.Builder ib = client.target(uri).request(MediaType.APPLICATION_JSON);
            ib.property(ClientProperties.FOLLOW_REDIRECTS, Boolean.TRUE);
            Response response = ib.post(Entity.entity(log, MediaType.APPLICATION_JSON));
            String t = response.readEntity(String.class);
            logger.debug("Response: {}", t);
        } catch (IllegalArgumentException | UriBuilderException ex) {
            logger.error("Exception while notifying the message: ", ex);
        }
    }

    @Override
    public Boolean call() throws Exception {
        if (log == null && (logs == null || logs.isEmpty())) {
            return false;
        }
        if (log == null) {
            logs.stream().forEach((_log) -> {
                send(_log);
            });
        } else {
            send(log);
        }
        return true;
    }
}
