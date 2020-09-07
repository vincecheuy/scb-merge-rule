package com.vince.rule;

import com.vince.refdata.LMEInstrument;
import com.vince.refdata.PRIMEInstrument;
import static com.vince.common.Market.LME_PB;
import static com.vince.common.Market.PB;

/**
 * Centralized factory for easy-managing merge rules of various instruments
 */
public class MergeRuleFactory {

    //[As Requirement] - LME takes priority. Directly update all LME fields to published internal instrument
    public static final MergeRule<LMEInstrument> LME_RULE = (lmeInstrument, internalInstrument, ifExists) -> {
        internalInstrument.setLastTradingDate(lmeInstrument.getLastTradingDate());
        internalInstrument.setDeliveryDate(lmeInstrument.getDeliveryDate());
        internalInstrument.setMarket(lmeInstrument.getMarket());
        internalInstrument.setLabel(lmeInstrument.getLabel());
        internalInstrument.setTradable(true);   //[As requirement] - From sample, tradable is published as True for LME
    };

    //[As Requirement] - PRIME dates takes lower priority. Only updates market and tradable
    public static final MergeRule<PRIMEInstrument> PRIME_RULE = (primeInstrument, internalInstrument, ifExists) -> {
        if(ifExists){
            //[As Requirement] - if key exists and that is LME published
            if(internalInstrument != null && internalInstrument.getMarket() == LME_PB){
                //[As Requirement] - no change to lastTradingDate and deliveryDate
                internalInstrument.setMarket(PB);
                internalInstrument.setTradable(false);
            }
        }else{
            internalInstrument.setLastTradingDate(primeInstrument.getLastTradingDate());
            internalInstrument.setDeliveryDate(primeInstrument.getDeliveryDate());
            internalInstrument.setMarket(primeInstrument.getMarket());
            internalInstrument.setLabel(primeInstrument.getLabel());
            internalInstrument.setTradable(primeInstrument.isTradable());
        }
    };

}
