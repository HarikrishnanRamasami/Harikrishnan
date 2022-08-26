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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutgoingResponseWrapper {

    private List<OutgoingResponse> messages;
    private RequestError requestError;

    public List<OutgoingResponse> getMessages() {
        return messages;
    }

    public void setMessages(List<OutgoingResponse> messages) {
        this.messages = messages;
    }

    public RequestError getRequestError() {
        return requestError;
    }

    public void setRequestError(RequestError requestError) {
        this.requestError = requestError;
    }
}
