
package org.rapharino.entrance.core.registry.impl;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.rapharino.entrance.core.annotation.API;
import org.rapharino.entrance.core.registry.Registry;
import org.rapharino.entrance.core.utils.APIUtil;

/**
 * Created By Rapharino on 12/10/2017 11:30 AM
 */
public abstract class RemoteRegistry implements Registry {

    protected volatile Map<String, Object> serviceMap = new ConcurrentHashMap<>();

    @Override
    public void register(InetSocketAddress address, Object serviceBean) {
        API api = serviceBean.getClass().getAnnotation(API.class);
        serviceMap.put(APIUtil.getPrimaryKey(api), serviceBean);
        remoteRegister(address, api);
    }

    //todo 提供远程注册 <value ,address >
    abstract public void remoteRegister(InetSocketAddress address, API api);
}
