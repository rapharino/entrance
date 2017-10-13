
package org.rapharino.entrance.core.remote.impl;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.rapharino.entrance.core.annotation.API;
import org.rapharino.entrance.core.registry.Registry;
import org.rapharino.entrance.core.registry.impl.RemoteRegistry;
import org.rapharino.entrance.core.remote.Prodiver;

/**
 * Created By Rapharino on 12/10/2017 10:17 AM
 * 提供 Registry
 */
public abstract class AbstractProvider implements Prodiver {

    // name, serviceImpl
    protected final List<Object> declareService = new ArrayList<>();

    protected Registry registry;

    private static Registry DEFAULT_REGISTRY = new RemoteRegistry() {
        @Override
        public void remoteRegister(InetSocketAddress address, API api) {

        }
    };

    @Override
    public Prodiver register(Object serviceImpl) {
        // todo @API 确定
        declareService.add(serviceImpl);
        return this;
    }

    public AbstractProvider() {
        this.registry = DEFAULT_REGISTRY;
    }

    @Override
    public void accept(String host, int port) {
        acceptAndRegister(host, port);
    }

    // accept AND register
    protected abstract void acceptAndRegister(String host, int port);

    public Registry getRegistry() {
        return registry;
    }

    public void setRegistry(Registry registry) {
        this.registry = registry;
    }

    public List<Object> getDeclareService() {
        return declareService;
    }
}
