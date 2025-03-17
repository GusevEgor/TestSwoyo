package org.example.testswoyo.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.example.testswoyo.server.handler.ServerInHandler;

@Slf4j
@Component
@RequiredArgsConstructor
public class Server {
    private final ServerInHandler serverInHandler;

    public void start(Integer port) {
        new Thread(() -> {
            EventLoopGroup bossGroup = new NioEventLoopGroup(1);
            EventLoopGroup workerGroup = new NioEventLoopGroup();

            try {
                ServerBootstrap b = new ServerBootstrap();
                b.group(bossGroup, workerGroup)
                        .channel(NioServerSocketChannel.class)
                        .childHandler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            public void initChannel(SocketChannel ch) throws Exception {

                                ch.pipeline().addLast(new StringDecoder(), new StringEncoder(), serverInHandler);
                            }
                        });

                ChannelFuture f;
                try {
                    f = b.bind(port).sync();
                } catch (Exception e) {
                    System.out.println("Server error: " + e.getMessage());
                    log.error("Server error: " + e.getMessage());
                    return;
                }

                System.out.println("Server started on port " + port);
                try {
                    f.channel().closeFuture().sync();
                } catch (InterruptedException e) {
                    log.error("Server error: " + e.getMessage());
                }

            } finally {
                bossGroup.shutdownGracefully();
                workerGroup.shutdownGracefully();
            }
        }).start();
    }
}


