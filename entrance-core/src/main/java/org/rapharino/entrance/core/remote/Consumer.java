package org.rapharino.entrance.core.remote;

import org.rapharino.entrance.core.blance.Loadbalance;
import org.rapharino.entrance.core.common.API;

/**
 * Created By Rapharino on 12/10/2017 11:40 AM
 */
public interface Consumer {
    // 创建代理对象
    <T> T proxy(API api);

    void setLoadbalance(Loadbalance loadbalance);
}
