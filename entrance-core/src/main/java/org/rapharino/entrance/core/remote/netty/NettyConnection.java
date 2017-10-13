
package org.rapharino.entrance.core.remote.netty;

import java.net.InetSocketAddress;
import java.nio.channels.ClosedChannelException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.SynchronousQueue;

import org.rapharino.entrance.core.common.Request;
import org.rapharino.entrance.core.common.Response;
import org.rapharino.entrance.core.exception.EntranceException;
import org.rapharino.entrance.core.remote.Connection;
import org.rapharino.entrance.core.remote.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created By Rapharino on 12/10/2017 9:51 AM
 */
public class NettyConnection extends SimpleChannelInboundHandler<Response> implements Connection {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyConnection.class);

    // 当前的 channel
    private volatile Channel channel;

    // requestId
    private static volatile Sync<String, Response> SYNC = new Sync<>();

    private ConnectionFactory<NettyConnection> connectionFactory;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Response msg) throws Exception {
        SYNC.put(msg.getRequestId(),msg);
    }

    @Override
    public ConnectionFactory<NettyConnection> getConnectionFactory() {
        return connectionFactory;
    }

    @Override
    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public Response send(Request request) throws EntranceException {
        Response resp = new Response(request.getRequestId());
        if (!isConnected()) {
            resp.setError(new ClosedChannelException());
            return resp;
        }
        ChannelFuture wirteFuture = channel.writeAndFlush(request).awaitUninterruptibly();
        if (wirteFuture.isSuccess()) {
            resp = SYNC.get(request.getRequestId());
        } else {
            resp.setError(new EntranceException("channel write error caused by net"));
            try {
                close();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        return resp;
    }

    @Override
    public void connect(InetSocketAddress address) throws EntranceException {
        if (isConnected()) {
            return;
        }
        try {
            ChannelFuture connectFuture = ((NettyConsumer) connectionFactory.getConsumer()).getBootstrap()
                    .connect(address).sync();
            channel = connectFuture.channel();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws EntranceException {
        if (channel != null) {
            try {
                channel.close().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean isConnected() {
        return !((null == channel) || !channel.isActive() || !channel.isOpen() || !channel.isWritable());
    }

    /**
     * 同步器
     * @param <K> key
     * @param <T> object
     */
    private static class Sync<K, T> {

        private boolean fail;

        public Sync(boolean fail) {
            this.fail = fail;
        }

        public Sync() {
            this(false);
        }

        private final Map<K, SynchronousQueue<T>> sync = new ConcurrentHashMap<>();

        public void put(K k, T t) {
            try {
                sync.get(k).put(t);
                sync.remove(k);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public T get(K k) {
            try {
                SynchronousQueue<T> queue =new SynchronousQueue<>(fail);
                sync.putIfAbsent(k, queue);
                T t =queue.take();
                return t;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
