
package org.rapharino.entrance.core.remote;

/**
 * Created By Rapharino on 12/10/2017 10:08 AM
 */
public interface Prodiver {

    /**
     * 服务注册
     * @param serviceImpl 实现类
     * @return
     */
    Prodiver register(Object serviceImpl);

    // accept
    void accept(String host, int port);
}
