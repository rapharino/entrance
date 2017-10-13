package org.rapharino.entrance.core.registry.impl;

import java.net.InetSocketAddress;

import org.rapharino.entrance.core.blance.Loadbalance;
import org.rapharino.entrance.core.blance.strategy.RandomStrategy;
import org.rapharino.entrance.core.registry.Discovery;

/**
 * Created By Rapharino on 13/10/2017 3:45 PM
 */
public abstract class AbstractDiscovery implements Discovery {

    protected Loadbalance<InetSocketAddress> loadbalance;

    protected static Loadbalance<InetSocketAddress> DEFAULT_BLANCE = new RandomStrategy<>();

    @Override
    public void setLoadbalance(Loadbalance loadbalance) {
        this.loadbalance = loadbalance;
    }

    public Loadbalance<InetSocketAddress> getLoadbalance() {
        return loadbalance;
    }
}
