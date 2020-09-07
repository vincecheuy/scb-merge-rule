package com.vince.rule;

import com.vince.common.InternalInstrument;
import com.vince.common.Market;
import com.vince.refdata.LMEInstrument;
import com.vince.refdata.PRIMEInstrument;
import org.junit.Test;

import java.time.LocalDate;

import static com.vince.common.Market.LME_PB;
import static com.vince.common.Market.PB;
import static com.vince.rule.MergeRuleFactory.LME_RULE;
import static com.vince.rule.MergeRuleFactory.PRIME_RULE;
import static java.time.LocalDate.of;

public class MergeRuleFactoryTest {

    @Test
    public void testLMERule_KeyNotExists(){
        LMEInstrument lmeInstrument = new LMEInstrument(of(2018, 3, 15), of(2018, 3, 17), PB, "Lead 13 March 2018", "PB_03_2018");
        InternalInstrument internalInstrument = new InternalInstrument();
        LME_RULE.merge(lmeInstrument, internalInstrument, false);
        assert internalInstrument.getLastTradingDate().toString().equals("2018-03-15");
        assert internalInstrument.getDeliveryDate().toString().equals("2018-03-17");
        assert internalInstrument.getMarket() == PB;
        assert internalInstrument.getLabel().equals("Lead 13 March 2018");
        assert internalInstrument.isTradable() == true;
    }

    @Test
    public void testLMERule_KeyExists(){
        InternalInstrument internalInstrument = new InternalInstrument.Builder()
                .lastTradingDate(LocalDate.of(2018, 3, 15)).deliveryDate(LocalDate.of(2018, 3, 17))
                .market(Market.PB).label("Lead 13 March 2018").tradable(true).build();
        LMEInstrument lmeInstrument = new LMEInstrument(of(2018, 3, 18), of(2018, 3, 20), PB, "Lead 18 March 2018", "PB_03_2018");
        LME_RULE.merge(lmeInstrument, internalInstrument, true);
        assert internalInstrument.getLastTradingDate().toString().equals("2018-03-18");
        assert internalInstrument.getDeliveryDate().toString().equals("2018-03-20");
        assert internalInstrument.getMarket() == PB;
        assert internalInstrument.getLabel().equals("Lead 18 March 2018");
        assert internalInstrument.isTradable() == true;
    }

    @Test
    public void testPRIMERule_KeyNotExists(){
        PRIMEInstrument primeInstrument = new PRIMEInstrument(of(2018, 3, 14), of(2018, 3, 18), LME_PB, "Lead 13 March 2018", "PB_03_2018", false);
        InternalInstrument internalInstrument = new InternalInstrument();
        PRIME_RULE.merge(primeInstrument, internalInstrument, false);
        assert internalInstrument.getLastTradingDate().toString().equals("2018-03-14");
        assert internalInstrument.getDeliveryDate().toString().equals("2018-03-18");
        assert internalInstrument.getMarket() == LME_PB;
        assert internalInstrument.getLabel().equals("Lead 13 March 2018");
        assert internalInstrument.isTradable() == false;
    }

    @Test
    public void testPRIMERule_WithExistingLME(){
        InternalInstrument internalInstrument = new InternalInstrument.Builder()
                .lastTradingDate(LocalDate.of(2018, 3, 15)).deliveryDate(LocalDate.of(2018, 3, 17))
                .market(Market.LME_PB).label("Lead 13 March 2018").tradable(true).build();
        PRIMEInstrument primeInstrument = new PRIMEInstrument(of(2018, 3, 14), of(2018, 3, 18), LME_PB, "Lead 13 March 2018", "PB_03_2018", false);
        PRIME_RULE.merge(primeInstrument, internalInstrument, true);
        assert internalInstrument.getLastTradingDate().toString().equals("2018-03-15");
        assert internalInstrument.getDeliveryDate().toString().equals("2018-03-17");
        assert internalInstrument.getMarket() == PB;
        assert internalInstrument.getLabel().equals("Lead 13 March 2018");
        assert internalInstrument.isTradable() == false;
    }

}
