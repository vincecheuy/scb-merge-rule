package com.vince.common;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

public class InternalInstrumentTest {

    @Test
    public void testConstructorAndBuilder(){
        InternalInstrument internalInstrument = new InternalInstrument.Builder()
                .lastTradingDate(LocalDate.of(2019, 3, 12))
                .deliveryDate(LocalDate.of(2019, 3, 13))
                .market(Market.LME_PB)
                .label("Lead 13 March 2019")
                .tradable(true).build();

        Assert.assertEquals("2019-03-12", internalInstrument.getLastTradingDate().toString());
        Assert.assertEquals("2019-03-13", internalInstrument.getDeliveryDate().toString());
        Assert.assertEquals(Market.LME_PB, internalInstrument.getMarket());
        Assert.assertEquals("Lead 13 March 2019", internalInstrument.getLabel());
        Assert.assertTrue(internalInstrument.isTradable());
    }

}
