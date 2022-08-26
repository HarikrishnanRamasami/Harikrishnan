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
public class Price {

    private String pricePerMessage;
    private String currency;

    public String getPricePerMessage() {
        return pricePerMessage;
    }

    public void setPricePerMessage(String pricePerMessage) {
        this.pricePerMessage = pricePerMessage;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

}
