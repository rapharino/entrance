
package org.rapharino.entrance.core.registry.impl;

import java.net.InetSocketAddress;
import java.util.List;

import org.rapharino.entrance.core.common.API;

/**
 * Created By Rapharino on 12/10/2017 11:45 AM
 */
public abstract class RemoteDiscovery extends AbstractDiscovery {

    //todo
    public abstract List<InetSocketAddress> remoteDiscovery(API api);

    @Override
    public InetSocketAddress discover(API api) {
        List<InetSocketAddress> list = remoteDiscovery(api);
        return doBlance(list);
    }

    private InetSocketAddress doBlance(List<InetSocketAddress> addresses) {
        return loadbalance.get(addresses);
    }
}
