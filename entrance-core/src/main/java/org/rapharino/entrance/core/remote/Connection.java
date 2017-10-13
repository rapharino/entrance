
package org.rapharino.entrance.core.remote;

import java.net.InetSocketAddress;

import org.rapharino.entrance.core.common.Request;
import org.rapharino.entrance.core.common.Response;
import org.rapharino.entrance.core.exception.EntranceException;

/**
 * Created By Rapharino on 11/10/2017 2:13 PM
 * 物理连接通道,用于网络通信.(S/C)
 */
public interface Connection {

    ConnectionFactory getConnectionFactory();

    void setConnectionFactory(ConnectionFactory connectionFactory);

    // send
    Response send(Request request) throws EntranceException, InterruptedException;

    // connect
    void connect(InetSocketAddress address) throws EntranceException;

    // close
    void close() throws EntranceException;

    // isConnected
    boolean isConnected();

}
