package com.vince.refdata;

import com.vince.common.Market;

import java.time.LocalDate;

public class LMEInstrument extends BaseInstrument{

    private String code;

    public LMEInstrument(LocalDate lastTradingDate, LocalDate deliveryDate, Market market, String label, String code) {
        super(lastTradingDate, deliveryDate, market, label);
        this.code = code;
    }

    //[As Requirement] - LME code as mapping key
    @Override
    public String getKey(){
        return this.code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "LME     {" +
                "lastTradingDate=" + lastTradingDate +
                ", deliveryDate=" + deliveryDate +
                ", market=" + market +
                ", label='" + label + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
