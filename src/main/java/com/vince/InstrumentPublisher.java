package com.vince;

import com.vince.common.InternalInstrument;
import com.vince.refdata.BaseInstrument;
import com.vince.rule.MergeRule;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Use ConcurrentHashMap to ensure thread-safe when updating map elements
 * */
public class InstrumentPublisher {

    private ConcurrentHashMap<String, InternalInstrument> publishedInstrumentMap;

    public InstrumentPublisher(ConcurrentHashMap<String, InternalInstrument> publishedInstrumentMap){
        this.publishedInstrumentMap = publishedInstrumentMap;
    }

    /**
     * if - this instrument key exists, merge into this instrument directly
     * else - Create new InternalInstrument and put into map for merge
     * Thread Safe - ConcurrentHashMap locks all updates to the value object InternalInstrument
     */
    public void receiveAndPublish(BaseInstrument instrument, MergeRule mergeRule){
        InternalInstrument oldInstrument = publishedInstrumentMap.get(instrument.getKey());
        //based on actual instrument type, mergeRule will merge accordingly
        InternalInstrument newInstrument = mergeRule.merge(instrument, oldInstrument);
        if(oldInstrument == null)
            publishedInstrumentMap.putIfAbsent(instrument.getKey(), newInstrument);
        else
            publishedInstrumentMap.replace(instrument.getKey(), newInstrument);
    }

}
