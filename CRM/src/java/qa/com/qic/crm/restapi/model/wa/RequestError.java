/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.crm.restapi.model.wa;

/**
 *
 * @author ravindar.singh
 */
public class RequestError {

    private OutgoingResponse serviceException;

    public OutgoingResponse getServiceException() {
        return serviceException;
    }

    public void setServiceException(OutgoingResponse serviceException) {
        this.serviceException = serviceException;
    }

}
