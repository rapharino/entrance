
package org.rapharino.entrance.core.remote.impl;

import java.net.InetSocketAddress;

import org.rapharino.entrance.core.blance.Loadbalance;
import org.rapharino.entrance.core.registry.impl.AbstractDiscovery;
import org.rapharino.entrance.core.remote.Consumer;

/**
 * Created By Rapharino on 12/10/2017 2:14 PM
 */
public abstract class AbstractConsumer implements Consumer {

    protected AbstractDiscovery discovery;

    protected Loadbalance<InetSocketAddress> loadbalance;

    public AbstractDiscovery getDiscovery() {
        return discovery;
    }

    public void setDiscovery(AbstractDiscovery discovery) {
        this.discovery = discovery;
    }

    @Override
    public void setLoadbalance(Loadbalance loadbalance) {
        this.loadbalance = loadbalance;
    }

    public Loadbalance<InetSocketAddress> getLoadbalance() {
        return loadbalance;
    }
}
