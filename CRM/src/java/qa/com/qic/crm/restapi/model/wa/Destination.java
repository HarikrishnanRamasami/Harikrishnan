/*
 * Copyright (C) Qatar Insurance Group, PO Box 666, Doha, Qatar
 * All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */
package qa.com.qic.crm.restapi.model.wa;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 *
 * @author ravindar.singh
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Destination {

    private Contact to;

    public Contact getTo() {
        return to;
    }

    public void setTo(Contact to) {
        this.to = to;
    }

}
