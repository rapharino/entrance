
package org.rapharino.entrance.core.remote.netty;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.rapharino.entrance.core.common.API;
import org.rapharino.entrance.core.common.Request;
import org.rapharino.entrance.core.common.Response;
import org.rapharino.entrance.core.remote.ConnectionFactory;
import org.rapharino.entrance.core.remote.impl.AbstractConsumer;
import org.rapharino.entrance.core.remote.netty.code.Decoder;
import org.rapharino.entrance.core.remote.netty.code.Encoder;
import org.rapharino.entrance.core.utils.SequenceUtil;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created By Rapharino on 12/10/2017 11:38 AM
 */
public class NettyConsumer extends AbstractConsumer {

    public static final String PREFIX_REQUEST = "ss_";

    private ConnectionFactory<NettyConnection> connectionFactory;

    @SuppressWarnings("unchecked")
    @Override
    public <T> T proxy(API api) {

        Class<T> interfaceClass = api.getValue();

        if (connectionFactory == null) {
            connectionFactory = new ConnectionFactory<>(this);
            connectionFactory.setChannelClazz(NettyConnection.class);
        }

        if (!interfaceClass.isInterface()) {
            throw new IllegalArgumentException(interfaceClass.getName() + " not  interface");
        }
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[] { interfaceClass },
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        api.setMethodName(method.getName())
                                .setParameterTypes(method.getParameterTypes());

                        // 获取连接
                        NettyConnection connection = connectionFactory.get(api);
                        Request request = new Request(api);
                        request.setRequestId(PREFIX_REQUEST + SequenceUtil.next());
                        request.setParameters(args);

                        Response response =connection.send(request);
                        if (response.isError()) {
                            throw response.getError(); // 原则上是抛出的。
                        } else {
                            return response.getResult();
                        }
                    }
                });
    }

    // netty client

    private final Bootstrap bootstrap;

    private static EventLoopGroup workerGroup = new NioEventLoopGroup();

    // 当前连接
    private volatile Channel channel;

    public NettyConsumer() {
        this.bootstrap = new Bootstrap();
        bootstrap.group(workerGroup).channel(NioSocketChannel.class).option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                .option(ChannelOption.TCP_NODELAY, true).option(ChannelOption.SO_REUSEADDR, true)
                .option(ChannelOption.SO_KEEPALIVE, true).option(ChannelOption.SO_SNDBUF, 65535)
                .option(ChannelOption.SO_RCVBUF, 65535).handler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    public void initChannel(SocketChannel channel) throws Exception {
                        channel.pipeline().addLast(new Encoder(Request.class)).addLast(new Decoder(Response.class))
                                .addLast(new NettyConnection());
                    }
                });
    }

    public Bootstrap getBootstrap() {
        return bootstrap;
    }
}
