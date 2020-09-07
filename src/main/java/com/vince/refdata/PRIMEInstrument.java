package com.vince.refdata;

import com.vince.common.Market;

import java.time.LocalDate;

public class PRIMEInstrument extends BaseInstrument{

    private String exchangeCode;
    private boolean tradable;

    public PRIMEInstrument(LocalDate lastTradingDate, LocalDate deliveryDate, Market market, String label, String exchangeCode, boolean tradable) {
        super(lastTradingDate, deliveryDate, market, label);
        this.exchangeCode = exchangeCode;
        this.tradable = tradable;
    }

    //[As Requirement] - exchange code as mapping key for PRIME
    @Override
    public String getKey(){
        return this.exchangeCode;
    }

    public String getExchangeCode() {
        return exchangeCode;
    }

    public void setExchangeCode(String exchangeCode) {
        this.exchangeCode = exchangeCode;
    }

    public boolean isTradable() {
        return tradable;
    }

    public void setTradable(boolean tradable) {
        this.tradable = tradable;
    }

    @Override
    public String toString() {
        return "PRIME   {" +
                "lastTradingDate=" + lastTradingDate +
                ", deliveryDate=" + deliveryDate +
                ", market=" + market +
                ", label='" + label + '\'' +
                ", exchangeCode='" + exchangeCode + '\'' +
                ", tradable=" + tradable +
                '}';
    }
}
