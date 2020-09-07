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

    private InternalInstrument existingInstrument;

    @Test
    public void testLMERule_NoExisting(){
        existingInstrument = null;
        LMEInstrument lmeInstrument = new LMEInstrument(of(2018, 3, 15), of(2018, 3, 17), PB, "Lead 13 March 2018", "PB_03_2018");
        InternalInstrument mergedInstrument = LME_RULE.merge(lmeInstrument, existingInstrument);
        assert mergedInstrument.getLastTradingDate().toString().equals("2018-03-15");
        assert mergedInstrument.getDeliveryDate().toString().equals("2018-03-17");
        assert mergedInstrument.getMarket() == PB;
        assert mergedInstrument.getLabel().equals("Lead 13 March 2018");
        assert mergedInstrument.isTradable();
    }

    @Test
    public void testLMERule_WithExisting(){
        existingInstrument = new InternalInstrument.Builder()
                .lastTradingDate(LocalDate.of(2018, 3, 15)).deliveryDate(LocalDate.of(2018, 3, 17))
                .market(Market.PB).label("Lead 13 March 2018").tradable(true).build();
        LMEInstrument lmeInstrument = new LMEInstrument(of(2018, 3, 18), of(2018, 3, 20), PB, "Lead 18 March 2018", "PB_03_2018");
        InternalInstrument mergedInstrument = LME_RULE.merge(lmeInstrument, existingInstrument);
        assert mergedInstrument.getLastTradingDate().toString().equals("2018-03-18");
        assert mergedInstrument.getDeliveryDate().toString().equals("2018-03-20");
        assert mergedInstrument.getMarket() == PB;
        assert mergedInstrument.getLabel().equals("Lead 18 March 2018");
        assert mergedInstrument.isTradable();
    }

    @Test
    public void testPRIMERule_NoExisting(){
        existingInstrument = null;
        PRIMEInstrument primeInstrument = new PRIMEInstrument(of(2018, 3, 14), of(2018, 3, 18), LME_PB, "Lead 13 March 2018", "PB_03_2018", false);
        InternalInstrument mergedInstrument = PRIME_RULE.merge(primeInstrument, existingInstrument);
        assert mergedInstrument.getLastTradingDate().toString().equals("2018-03-14");
        assert mergedInstrument.getDeliveryDate().toString().equals("2018-03-18");
        assert mergedInstrument.getMarket() == LME_PB;
        assert mergedInstrument.getLabel().equals("Lead 13 March 2018");
        assert !mergedInstrument.isTradable();
    }

    @Test
    public void testPRIMERule_WithExistingLME(){
        existingInstrument = new InternalInstrument.Builder()
                .lastTradingDate(LocalDate.of(2018, 3, 15)).deliveryDate(LocalDate.of(2018, 3, 17))
                .market(Market.LME_PB).label("Lead 13 March 2018").tradable(true).build();
        PRIMEInstrument primeInstrument = new PRIMEInstrument(of(2018, 3, 14), of(2018, 3, 18), LME_PB, "Lead 13 March 2018", "PB_03_2018", false);
        InternalInstrument mergedInstrument = PRIME_RULE.merge(primeInstrument, existingInstrument);
        assert mergedInstrument.getLastTradingDate().toString().equals("2018-03-15");
        assert mergedInstrument.getDeliveryDate().toString().equals("2018-03-17");
        assert mergedInstrument.getMarket() == PB;
        assert mergedInstrument.getLabel().equals("Lead 13 March 2018");
        assert !mergedInstrument.isTradable();
    }

}
