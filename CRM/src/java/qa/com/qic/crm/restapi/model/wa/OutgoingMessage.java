/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.crm.restapi.model.wa;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 *
 * @author ravindar.singh
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OutgoingMessage {

    private String scenarioKey;
    private String from;
    private List<Destination> destinations;
    // Simple message
    private String text;
    // Advanced message
    private Template whatsApp;

    public String getScenarioKey() {
        return scenarioKey;
    }

    public void setScenarioKey(String scenarioKey) {
        this.scenarioKey = scenarioKey;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public List<Destination> getDestinations() {
        return destinations;
    }

    public void setDestinations(List<Destination> destinations) {
        this.destinations = destinations;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Template getWhatsApp() {
        return whatsApp;
    }

    public void setWhatsApp(Template whatsApp) {
        this.whatsApp = whatsApp;
    }

}
