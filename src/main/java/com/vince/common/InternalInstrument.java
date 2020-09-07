package com.vince.common;

import java.time.LocalDate;

/**
 * Internally published instrument
 */
public class InternalInstrument {

    private LocalDate lastTradingDate;
    private LocalDate deliveryDate;
    private Market market;
    private String label;
    private boolean tradable;

    public InternalInstrument(){}

    public InternalInstrument(Builder builder) {
        this.lastTradingDate = builder.getLastTradingDate();
        this.deliveryDate = builder.getDeliveryDate();
        this.market = builder.getMarket();
        this.label = builder.getLabel();
        this.tradable = builder.isTradable();
    }

    //Builder as public internal class
    public static class Builder{
        private LocalDate lastTradingDate;
        private LocalDate deliveryDate;
        private Market market;
        private String label;
        private boolean tradable;

        public InternalInstrument build() {return new InternalInstrument(this);}

        public LocalDate getLastTradingDate() {
            return lastTradingDate;
        }

        public Builder lastTradingDate(LocalDate lastTradingDate) {
            this.lastTradingDate = lastTradingDate;
            return this;
        }

        public LocalDate getDeliveryDate() {
            return deliveryDate;
        }

        public Builder deliveryDate(LocalDate deliveryDate) {
            this.deliveryDate = deliveryDate;
            return this;
        }

        public Market getMarket() {
            return market;
        }

        public Builder market(Market market) {
            this.market = market;
            return this;
        }

        public String getLabel() {
            return label;
        }

        public Builder label(String label) {
            this.label = label;
            return this;
        }

        public boolean isTradable() {
            return tradable;
        }

        public Builder tradable(boolean tradable) {
            this.tradable = tradable;
            return this;
        }
    }

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

    public boolean isTradable() {
        return tradable;
    }

    public void setTradable(boolean tradable) {
        this.tradable = tradable;
    }

    @Override
    public String toString() {
        return "Internal{" +
                "lastTradingDate=" + lastTradingDate +
                ", deliveryDate=" + deliveryDate +
                ", market=" + market +
                ", label='" + label + '\'' +
                ", tradable=" + tradable +
                '}';
    }
}
