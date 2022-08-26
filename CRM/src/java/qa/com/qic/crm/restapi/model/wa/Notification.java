/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.crm.restapi.model.wa;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author ravindar.singh
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Notification {

    @JsonProperty(value = "results", access = JsonProperty.Access.AUTO)
    private List<NotificationContent> contents;
    private Integer messageCount;
    private Integer pendingMessageCount;

    public List<NotificationContent> getContents() {
        return contents;
    }

    public void setContents(List<NotificationContent> contents) {
        this.contents = contents;
    }

    public Integer getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(Integer messageCount) {
        this.messageCount = messageCount;
    }

    public Integer getPendingMessageCount() {
        return pendingMessageCount;
    }

    public void setPendingMessageCount(Integer pendingMessageCount) {
        this.pendingMessageCount = pendingMessageCount;
    }

}
