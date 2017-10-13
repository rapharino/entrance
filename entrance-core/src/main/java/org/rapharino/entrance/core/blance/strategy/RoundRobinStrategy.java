
package org.rapharino.entrance.core.blance.strategy;

import org.rapharino.entrance.core.blance.Loadbalance;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created By Rapharino on 13/10/2017 3:28 PM
 * 轮询策略
 */
public class RoundRobinStrategy<T> implements Loadbalance<T> {

    private final AtomicInteger index = new AtomicInteger(0);

    @Override
    public T get(List<T> set) {
        int thisIndex = Math.abs(index.getAndIncrement());
        return set.get(thisIndex % set.size());
    }
}
