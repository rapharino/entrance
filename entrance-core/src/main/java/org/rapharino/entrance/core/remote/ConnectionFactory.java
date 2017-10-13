
package org.rapharino.entrance.core.remote;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.rapharino.entrance.core.common.API;
import org.rapharino.entrance.core.exception.EntranceException;
import org.rapharino.entrance.core.registry.Discovery;
import org.rapharino.entrance.core.remote.impl.AbstractConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 连接工厂.
 * 1. 保存连接引用.
 * 2. 服务发现和负载均衡
 * 
 * @param <T>
 */
public  class ConnectionFactory<T extends Connection> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionFactory.class);

    // address connection  连接对象的保存
    private volatile Map<InetSocketAddress, T> connectionMap = new ConcurrentHashMap<>();

    protected Discovery discovery;

    protected AbstractConsumer consumer;

    protected Class<T> channelClazz;

    public ConnectionFactory(AbstractConsumer consumer) {
        this.consumer = consumer;
        init();
    }

    private void init() {
        this.discovery = consumer.getDiscovery();
        this.discovery.setLoadbalance(consumer.getLoadbalance());
    }


    // 完成连接
    public T get(API api) {

        InetSocketAddress address = discovery.discover(api);

        T connection = null;
        if ((connection = connectionMap.get(address)) != null) {
            if (!connection.isConnected()) {
                connection.connect(address);
            }
        } else {
            connection = newInstance(this.channelClazz);
            connection.setConnectionFactory(this);
            connection.connect(address);
            connectionMap.put(address,connection);
        }
        return connection;
    }

    public void recycle(Connection connection) throws EntranceException {
        if (connection != null) {
            connection.close();
        }
    }

    private T newInstance(Class<T> calss) {
        T t = null;
        try {
            t = calss.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    public AbstractConsumer getConsumer() {
        return consumer;
    }

    public ConnectionFactory setConsumer(AbstractConsumer consumer) {
        this.consumer = consumer;
        return this;
    }

    public Discovery getDiscovery() {
        return discovery;
    }

    public void setDiscovery(Discovery discovery) {
        this.discovery = discovery;
    }

    public Class<T> getChannelClazz() {
        return channelClazz;
    }

    public void setChannelClazz(Class<T> channelClazz) {
        this.channelClazz = channelClazz;
    }
}
