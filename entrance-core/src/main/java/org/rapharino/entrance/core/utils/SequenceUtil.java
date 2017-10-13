
package org.rapharino.entrance.core.utils;

import java.util.concurrent.atomic.LongAdder;

/**
 * Created By Rapharino on 12/10/2017 2:19 PM
 */
public abstract class SequenceUtil {

    private static LongAdder sequence = new LongAdder();

    static {
        sequence.add(1000L);
    }

    public static long next() {
        sequence.increment();
        return sequence.longValue();
    }
}
