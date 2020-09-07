package com.vince;

import com.vince.common.InternalInstrument;
import com.vince.refdata.BaseInstrument;
import com.vince.refdata.LMEInstrument;
import com.vince.refdata.PRIMEInstrument;
import org.junit.Test;


import java.util.concurrent.ConcurrentHashMap;

import static com.vince.common.Market.LME_PB;
import static com.vince.common.Market.PB;
import static com.vince.rule.MergeRuleFactory.LME_RULE;
import static com.vince.rule.MergeRuleFactory.PRIME_RULE;
import static java.time.LocalDate.of;

public class InstrumentPublisherTest {

    private ConcurrentHashMap<String, InternalInstrument> instrumentMap = new ConcurrentHashMap<>();
    private InstrumentPublisher unit = new InstrumentPublisher(instrumentMap);
    private String instrumentKey = "PB_03_2018";

    @Test
    public void testPublish(){
        BaseInstrument lmeInstrument = new LMEInstrument(of(2018, 3, 15), of(2018, 3, 17), LME_PB, "Lead 13 March 2018", instrumentKey);
        unit.receiveAndPublish(lmeInstrument, LME_RULE);

        BaseInstrument primeInstrument = new PRIMEInstrument(of(2018, 3, 14), of(2018, 3, 18), LME_PB, "Lead 13 March 2018", instrumentKey, false);
        unit.receiveAndPublish(primeInstrument, PRIME_RULE);

        assert instrumentMap.get(instrumentKey).getLastTradingDate().toString().equals("2018-03-15");
        assert instrumentMap.get(instrumentKey).getDeliveryDate().toString().equals("2018-03-17");
        assert instrumentMap.get(instrumentKey).getMarket() == PB;
        assert instrumentMap.get(instrumentKey).getLabel().equals("Lead 13 March 2018");
        assert !instrumentMap.get(instrumentKey).isTradable();
    }

}
