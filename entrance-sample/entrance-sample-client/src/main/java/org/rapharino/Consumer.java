
package org.rapharino;

import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.List;

import org.rapharino.api.HelloWorldService;
import org.rapharino.entrance.core.blance.strategy.RandomStrategy;
import org.rapharino.entrance.core.blance.strategy.RoundRobinStrategy;
import org.rapharino.entrance.core.common.API;
import org.rapharino.entrance.core.registry.impl.RemoteDiscovery;
import org.rapharino.entrance.core.remote.impl.AbstractConsumer;
import org.rapharino.entrance.core.remote.netty.NettyConsumer;

/**
 * Hello world!
 */
public class Consumer {

    public static void main(String[] args) {
        AbstractConsumer consumer = new NettyConsumer();
        consumer.setLoadbalance(new RandomStrategy());
        consumer.setDiscovery(new RemoteDiscovery(){
            @Override
            public List<InetSocketAddress> remoteDiscovery(API api) {
                return Arrays.asList(new InetSocketAddress("127.0.0.1",8080),
                        new InetSocketAddress("127.0.0.1",8082));
            }
        });

        HelloWorldService service = consumer.proxy(new API(HelloWorldService.class));

        for (int i = 0; i < 2; i++) {
            int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 5; j++) {
                        System.out.println(service.hello("R_T"+ finalI+"_C" +j));
                    }
                }
            }).start();

        }
    }
}
