package com.vince;

import com.vince.common.InternalInstrument;
import com.vince.refdata.BaseInstrument;
import com.vince.refdata.LMEInstrument;
import com.vince.refdata.PRIMEInstrument;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static com.vince.common.Market.PB;
import static com.vince.rule.MergeRuleFactory.LME_RULE;
import static com.vince.rule.MergeRuleFactory.PRIME_RULE;
import static java.time.LocalDate.of;

/**
 * Stress Test on publisher
 * To detect possible deadlock failure if any
 * Publish 12000 LME and 12000 PRIME instruments within 3 instrument keys Only (Adjustable)
 * As the test is CPU-bound with 4-12 core, thread pool size set as [4-12] * 2 = [8-24]. Set 24 threads in total then.
 */
public class ConcurrentPublisherTest {

    private ConcurrentHashMap<String, InternalInstrument> instrumentMap =  new ConcurrentHashMap<>(16, 0.75f, 24);
    private InstrumentPublisher publisher = new InstrumentPublisher(instrumentMap);
    private String[] testInstrumentCodes = {"PB_03_2018", "PB_04_2018", "PB_05_2018"};
    private AtomicInteger lmeCount = new AtomicInteger(0);
    private AtomicInteger primeCount = new AtomicInteger(0);

    @Test
    public void stressTestPublisher() throws InterruptedException{
        ExecutorService executorService = Executors.newFixedThreadPool(24);

        //Submit 12 LME threads. Each thread publishing 100 LMEInstruments
        for(int i=0; i<12; i++){
            executorService.submit(() -> {
                for(int j=0; j<1000; j++){
                    BaseInstrument lmeInstrument = new LMEInstrument(
                            of(2018, RandomUtils.nextInt(1, 13), RandomUtils.nextInt(1, 29)),
                            of(2018, RandomUtils.nextInt(1, 13), RandomUtils.nextInt(1, 29)),
                            PB, "Lead 13 March 2018", testInstrumentCodes[RandomUtils.nextInt(0, 3)]);
                    lmeCount.incrementAndGet();
                    publisher.receiveAndPublish(lmeInstrument, LME_RULE);
                    System.out.println(String.format("Thread %s published %s", Thread.currentThread().getId(), lmeInstrument));
                }
            });
        }

        //Submit 12 PRIME threads. Each thread publishing 100 PRIMEInstruments
        for(int i=0; i<12; i++){
            executorService.submit(() -> {
                for(int j=0; j<1000; j++){
                    BaseInstrument primeInstrument = new PRIMEInstrument(
                            of(2018, RandomUtils.nextInt(1, 13), RandomUtils.nextInt(1, 29)),
                            of(2018, RandomUtils.nextInt(1, 13), RandomUtils.nextInt(1, 29)),
                            PB, "Lead 13 March 2018", testInstrumentCodes[RandomUtils.nextInt(0, 3)], false);
                    publisher.receiveAndPublish(primeInstrument, PRIME_RULE);
                    primeCount.incrementAndGet();
                    System.out.println(String.format("Thread %s published %s", Thread.currentThread().getId(), primeInstrument));
                }
            });
        }

        //Wait till all threads finish
        TimeUnit.SECONDS.sleep(8);

        System.out.println(String.format("%s LME instruments published in total", lmeCount.get()));
        System.out.println(String.format("%s PRIME instruments published in total", primeCount.get()));
        System.out.println(String.format("Total instruments in map is %s - %s", instrumentMap.size(), instrumentMap.keySet()));
        assert lmeCount.get() <= 12000;
        assert primeCount.get() <= 12000;
        assert instrumentMap.size() <= 3;

    }

}
