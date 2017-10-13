
package org.rapharino.entrance.core.blance.strategy;

import org.rapharino.entrance.core.blance.Loadbalance;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created By Rapharino on 12/10/2017 11:55 AM
 * 随机策略
 */
public class RandomStrategy<T> implements Loadbalance<T> {

    @Override
    public T get(List<T> set) {
        int size = set.size();
        return set.get(ThreadLocalRandom.current().nextInt(size));
    }
}
