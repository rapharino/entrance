
package org.rapharino.entrance.core.registry;

import java.net.InetSocketAddress;

/**
 * Created By Rapharino on 11/10/2017 4:55 PM
 * 注册者
 */
public interface Registry {
    // 注册
    void register(InetSocketAddress address,Object serviceBean);
}
