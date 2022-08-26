/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.crm.restapi.model.wa;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 *
 * @author ravindar.singh
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationContent {

    private String from;
    private String to;
    private String integrationType;
    private Date receivedAt;
    private String messageId;
    private String pairedMessageId;
    private String callbackData;
    // Seen report
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "Asia/Qatar")
    private Date sentAt;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "Asia/Qatar")
    private Date seenAt;
    // Delivery report
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "Asia/Qatar")
    private Date doneAt;
    private Integer messageCount;
    private Status status;
    private Status error;
    private Message message;
    private Price price;

    /**
     * Sender ID that can be alphanumeric or numeric.
     * @return the from
     */
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * The message destination address.
     * @return  the to
     */
    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getIntegrationType() {
        return integrationType;
    }

    public void setIntegrationType(String integrationType) {
        this.integrationType = integrationType;
    }

    public Date getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(Date receivedAt) {
        this.receivedAt = receivedAt;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getPairedMessageId() {
        return pairedMessageId;
    }

    public void setPairedMessageId(String pairedMessageId) {
        this.pairedMessageId = pairedMessageId;
    }

    public String getCallbackData() {
        return callbackData;
    }

    public void setCallbackData(String callbackData) {
        this.callbackData = callbackData;
    }

    /**
     * Tells when the message was sent. It has the following format: yyyy-MM-dd'T'HH:mm:ss.SSSZ.
     * @return the sentAt
     */
    public Date getSentAt() {
        return sentAt;
    }

    public void setSentAt(Date sentAt) {
        this.sentAt = sentAt;
    }

    /**
     * Tells when the message was seen. It has the following format: yyyy-MM-dd'T'HH:mm:ss.SSSZ.
     * @return the seenAt
     */
    public Date getSeenAt() {
        return seenAt;
    }

    public void setSeenAt(Date seenAt) {
        this.seenAt = seenAt;
    }

    /**
     * Tells when the message was finished processing by Infobip (ie. delivered to destination, delivered to destination network, etc.)
     * @return the doneAt
     */
    public Date getDoneAt() {
        return doneAt;
    }

    public void setDoneAt(Date doneAt) {
        this.doneAt = doneAt;
    }

    /**
     * The number of sent message segments.
     * @return the messageCount
     */
    public Integer getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(Integer messageCount) {
        this.messageCount = messageCount;
    }

    /**
     * Indicates whether the message is successfully sent, not sent, delivered, not delivered, waiting for delivery or any other possible status.
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Indicates whether the error occurred during the query execution.
     * @return the error
     */
    public Status getError() {
        return error;
    }

    public void setError(Status error) {
        this.error = error;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

}
