
package org.rapharino;

import org.rapharino.entrance.core.remote.impl.AbstractProvider;
import org.rapharino.entrance.core.remote.netty.NettyProdiver;

/**
 * Hello world!
 */
public class Provider {

    public static void main(String[] args) {

        int port = Integer.parseInt(System.getProperty("port"));

        AbstractProvider prodiver = new NettyProdiver();
        prodiver.register(new HelloServiceImpl());
        prodiver.accept("127.0.0.1", port);
    }
}
