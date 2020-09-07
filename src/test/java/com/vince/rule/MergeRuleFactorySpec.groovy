package com.vince.rule

import com.vince.common.InternalInstrument
import com.vince.common.Market
import com.vince.refdata.BaseInstrument
import com.vince.refdata.LMEInstrument
import org.spockframework.util.Assert
import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Unroll
import java.time.LocalDate

import static com.vince.common.Market.PB
import static com.vince.common.Market.LME_PB
import static com.vince.rule.MergeRuleFactory.LME_RULE
import static java.time.LocalDate.of
import static com.vince.common.InternalInstrument.Builder

/**
 * Spock groovy test to easily test combinations of rules
 * Unfinished
 */
@Deprecated
class MergeRuleFactorySpec extends Specification{

    /**
     * Input as combination of existing InternalInstrument and LMEInstrument
     * Output as merged InternalInstrument
     */
    @Unroll
    @Ignore
    "test LME merge rule"(LocalDate intLastTradingDate, LocalDate intDeliveryDate, Market intMkt, String intLabel, boolean intTradable,
                          LocalDate lmeLastTradingDate, LocalDate lmeDeliveryDate, Market lmeMkt, String lmeLabel){
//    "test LME merge rule"(InternalInstrument intInstrument,
//                          LMEInstrument lmeInstrument){

        given:
        InternalInstrument existingInstrument = new Builder()
                .lastTradingDate(intLastTradingDate).deliveryDate(intDeliveryDate).market(intMkt).label(intLabel).tradable(intTradable).build()

        BaseInstrument lmeInstrument = new LMEInstrument(of(2018, 3, 15), of(2018, 3, 17), PB, "Lead 13 March 2018", lmeCode)


        when:
        InternalInstrument mergedInstrument = LME_RULE.merge(lmeInstrument, existingInstrument)

        then:
        Assert.equals("2019-03-12", mergedInstrument.getDeliveryDate().toString())

        where:
        intLastTradingDate | intDeliveryDate | intMkt | intLabel        | intTradable | lmeLastTradingDate | lmeDeliveryDate | lmeMkt | lmeLabel
        of(2018, 3, 15)    | of(2018, 3, 17) | PB     | "Lead 19.03.13" | true        | of(2018, 3, 15)    | of(2018, 3, 17) | PB     | "Lead 13 March 2018"
//        intInstrument                                           | lmeInstrument
//        new Builder().lastTradingDate(of(2018, 3, 15)).deliveryDate(of(2018, 3, 17)).market(PB).label("Lead 19.03.13").tradable(true).build()|



    }

    @Unroll
    "test LME merge rule with no existing instrument"(InternalInstrument existingInstrument,
                          LocalDate lmeLastTradingDate, LocalDate lmeDeliveryDate, Market lmeMkt, String lmeLabel){
        given:
        BaseInstrument lmeInstrument = new LMEInstrument(of(2018, 3, 15), of(2018, 3, 17), PB, "Lead 13 March 2018", lmeCode)

        when:
        InternalInstrument mergedInstrument = LME_RULE.merge(lmeInstrument, existingInstrument)

        then:
        Assert.equals(lmeLastTradingDate, mergedInstrument.getLastTradingDate().toString())
        Assert.equals(lmeDeliveryDate, mergedInstrument.getDeliveryDate().toString())
        Assert.equals(lmeMkt, mergedInstrument.getMarket())
        Assert.equals(lmeLabel, mergedInstrument.getLabel())
        Assert.equals(true, mergedInstrument.isTradable())

        where:
        existingInstrument | lmeLastTradingDate | lmeDeliveryDate | lmeMkt | lmeLabel
        null               | of(2018, 3, 15)    | of(2018, 3, 17) | PB     | "Lead 13 March 2018"

    }

}
