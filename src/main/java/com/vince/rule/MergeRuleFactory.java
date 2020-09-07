package com.vince.rule;

import com.vince.common.InternalInstrument;
import com.vince.refdata.LMEInstrument;
import com.vince.refdata.PRIMEInstrument;
import static com.vince.common.Market.LME_PB;
import static com.vince.common.Market.PB;

/**
 * Centralized factory for easy-managing merge rules of various instruments
 */
public class MergeRuleFactory {


    //[As Requirement] - LME takes priority. Assume LME fields always overrides and are published
    public static final MergeRule<LMEInstrument> LME_RULE = (lmeInstrument, existingInstrument) ->
            new InternalInstrument.Builder().lastTradingDate(lmeInstrument.getLastTradingDate())
                .deliveryDate(lmeInstrument.getDeliveryDate())
                .market(lmeInstrument.getMarket())
                .label(lmeInstrument.getLabel())
                //[As requirement] - From sample, tradable is published as True for LME
                .tradable(true)
                .build();

    public static final MergeRule<PRIMEInstrument> PRIME_RULE = (primeInstrument, existingInstrument) -> {
        InternalInstrument.Builder builder = new InternalInstrument.Builder();
        //[As Requirement] - if existing is LME, take existing lastTradingDate and deliveryDate
        if(existingInstrument != null && existingInstrument.getMarket() == LME_PB){
            builder.lastTradingDate(existingInstrument.getLastTradingDate())
                    .deliveryDate(existingInstrument.getDeliveryDate())
                    .market(PB)
                    .label(primeInstrument.getLabel())
                    .tradable(false);
        }else{
            builder.lastTradingDate(primeInstrument.getLastTradingDate())
                    .deliveryDate(primeInstrument.getDeliveryDate())
                    .market(primeInstrument.getMarket())
                    .label(primeInstrument.getLabel())
                    .tradable(primeInstrument.isTradable());
        }
        return builder.build();
    };
}