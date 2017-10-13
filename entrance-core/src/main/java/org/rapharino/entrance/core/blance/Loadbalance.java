
package org.rapharino.entrance.core.blance;

import java.util.List;

/**
 * Created By Rapharino on 11/10/2017 5:10 PM
 * 负载均衡器
 */
public interface Loadbalance<T> {

    T get(List<T> set);
}
