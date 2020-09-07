package com.vince;

import com.vince.common.InternalInstrument;
import com.vince.refdata.BaseInstrument;
import com.vince.refdata.LMEInstrument;
import com.vince.refdata.PRIMEInstrument;
import java.util.concurrent.ConcurrentHashMap;
import static com.vince.common.Market.LME_PB;
import static com.vince.common.Market.PB;
import static com.vince.rule.MergeRuleFactory.LME_RULE;
import static com.vince.rule.MergeRuleFactory.PRIME_RULE;
import static java.time.LocalDate.of;
import static java.lang.String.format;

/**
 * Main class for execution.
 * Execute story 1 & 2 with exact required input / output
 */
public class MergeRuleApplication {

    public static void main(String[] args){
        ConcurrentHashMap<String, InternalInstrument> instrumentMap = new ConcurrentHashMap<>(16, 0.75f, 24);
        InstrumentPublisher publisher = new InstrumentPublisher(instrumentMap);

        String instrumentKey = "PB_03_2018";

        //--------------------Story 1---------instrumentKey used as LMECode-----------//
        BaseInstrument lmeInstrument1 = new LMEInstrument(of(2018, 3, 15), of(2018, 3, 17), PB, "Lead 13 March 2018", instrumentKey);
        publisher.receiveAndPublish(lmeInstrument1, LME_RULE);
        System.out.println(format("<Story 1>\nRECEIVE - %s\nPUBLISH - %s", lmeInstrument1, instrumentMap.get(instrumentKey)));

        //--------------------Story 2---------instrumentKey used as LMECode-----------//
        BaseInstrument lmeInstrument2 = new LMEInstrument(of(2018, 3, 15), of(2018, 3, 17), LME_PB, "Lead 13 March 2018", instrumentKey);
        publisher.receiveAndPublish(lmeInstrument2, LME_RULE);
        System.out.println(format("<Story 2>\nRECEIVE - %s\nPUBLISH - %s", lmeInstrument2, instrumentMap.get(instrumentKey)));

        //-------------------------------------instrumentKey used as exchangeCode------//
        BaseInstrument primeInstrument2 = new PRIMEInstrument(of(2018, 3, 14), of(2018, 3, 18), LME_PB, "Lead 13 March 2018", instrumentKey, false);
        publisher.receiveAndPublish(primeInstrument2, PRIME_RULE);
        System.out.println(format("\nRECEIVE - %s\nPUBLISH - %s", primeInstrument2, instrumentMap.get(instrumentKey)));
    }

}
