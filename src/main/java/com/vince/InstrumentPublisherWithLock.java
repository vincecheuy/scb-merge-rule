package com.vince;

import com.google.common.util.concurrent.Striped;
import com.vince.common.InternalInstrument;
import com.vince.refdata.BaseInstrument;
import com.vince.rule.MergeRule;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;

/**
 * Add lock myself, but the performance is far lower than ConcurrentHashMap
 */
@Deprecated
public class InstrumentPublisherWithLock {

    private ConcurrentHashMap<String, InternalInstrument> publishedInstrumentMap;
    private final Striped<Lock> rwLockStripes = Striped.lock(24);
    private Lock keyLock;

    public InstrumentPublisherWithLock(ConcurrentHashMap<String, InternalInstrument> publishedInstrumentMap){
        this.publishedInstrumentMap = publishedInstrumentMap;
    }

    public void receiveAndPublish(BaseInstrument instrument, MergeRule mergeRule){
        String key = instrument.getKey();
        keyLock = rwLockStripes.get(key);
        try{
            keyLock.lock();
            InternalInstrument oldInstrument = publishedInstrumentMap.get(instrument.getKey()); //Read
            InternalInstrument newInstrument = mergeRule.merge(instrument, oldInstrument);      //Processing Data
            publishedInstrumentMap.put(instrument.getKey(), newInstrument);                     //Write
        }finally{
            keyLock.unlock();
        }
    }

}
