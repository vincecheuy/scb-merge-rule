package com.vince.refdata;

import com.vince.common.Market;
import java.time.LocalDate;

/**
 * Reference Data base model.
 * For extension only.
 */
public abstract class BaseInstrument {

    protected LocalDate lastTradingDate;
    protected LocalDate deliveryDate;
    protected Market market;
    protected String label;

    public BaseInstrument(LocalDate lastTradingDate, LocalDate deliveryDate, Market market, String label) {
        this.lastTradingDate = lastTradingDate;
        this.deliveryDate = deliveryDate;
        this.market = market;
        this.label = label;
    }

    //Flexibility to subclasses to define their own keys
    public abstract String getKey();

    public LocalDate getLastTradingDate() {
        return lastTradingDate;
    }

    public void setLastTradingDate(LocalDate lastTradingDate) {
        this.lastTradingDate = lastTradingDate;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Market getMarket() {
        return market;
    }

    public void setMarket(Market market) {
        this.market = market;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}
