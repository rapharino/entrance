
package org.rapharino.entrance.core.remote.netty;

import java.net.InetSocketAddress;

import org.rapharino.entrance.core.common.Request;
import org.rapharino.entrance.core.common.Response;
import org.rapharino.entrance.core.remote.impl.AbstractProvider;
import org.rapharino.entrance.core.remote.netty.code.Decoder;
import org.rapharino.entrance.core.remote.netty.code.Encoder;
import org.rapharino.entrance.core.remote.netty.handler.ServiceHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created By Rapharino on 12/10/2017 10:12 AM
 */
public class NettyProdiver extends AbstractProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyProdiver.class);

    @Override
    protected void acceptAndRegister(String host, int port) {

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {

                @Override
                public void initChannel(SocketChannel channel) throws Exception {
                    ChannelPipeline pipeline = channel.pipeline();
                    pipeline.addLast(new Decoder(Request.class)); // 解码请求
                    pipeline.addLast(new Encoder(Response.class)); // 编码响应
                    pipeline.addLast(new ServiceHandler(NettyProdiver.this)); // 处理 RPC 请求
                }
            });
            bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
            bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture future = bootstrap.bind(host, port).sync();

            // 注册 RPC 服务地址
            if (!declareService.isEmpty()) {
                for (Object o : declareService) {
                    this.getRegistry().register(new InetSocketAddress(host, port), o);
                }
            }

            LOGGER.debug("server started on port {}", port);
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

}
