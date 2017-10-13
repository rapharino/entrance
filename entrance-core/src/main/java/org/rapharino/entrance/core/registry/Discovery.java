
package org.rapharino.entrance.core.registry;

import java.net.InetSocketAddress;

import org.rapharino.entrance.core.blance.Loadbalance;
import org.rapharino.entrance.core.common.API;

/**
 * Created By Rapharino on 12/10/2017 11:13 AM
 *
 * 发现者
 */
public interface Discovery {

    // 发现
    InetSocketAddress discover(API api);

    // 软负载均衡策略
    void setLoadbalance(Loadbalance<?> loadbalance);
}
