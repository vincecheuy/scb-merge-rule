package com.vince.rule;

import com.vince.common.InternalInstrument;
import com.vince.refdata.BaseInstrument;

/**
 * Interface to allow flexibility on adding new merge rule
 */
@FunctionalInterface
public interface MergeRule<T extends BaseInstrument> {

    /**
     * Merge the received LME/Prime instrument, into the published instrument
     * Return the merged instrument
     */
    InternalInstrument merge(T sourceInstrument, InternalInstrument targetInstrument);

}