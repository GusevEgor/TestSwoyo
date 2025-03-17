package org.example.testswoyo.client;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.testswoyo.client.handler.ClientInHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@Getter
public class Client {

    private Channel channel;
    private final ClientInHandler clientInHandler;

    public void start(String host, int port) {
        try {
            new Thread(() -> {
                EventLoopGroup group = new NioEventLoopGroup();

                try {

                    Bootstrap bootstrap = new Bootstrap();
                    bootstrap.group(group)
                            .channel(NioSocketChannel.class)
                            .handler(new ChannelInitializer<SocketChannel>() {
                                @Override
                                public void initChannel(SocketChannel socketChannel) throws Exception {

                                    socketChannel.pipeline().addLast(
                                            new StringDecoder(),
                                            new StringEncoder(),
                                            clientInHandler);
                                }
                            });

                    ChannelFuture f;
                    try {
                       f = bootstrap.connect(host, port).sync();
                    } catch (Exception e) {
                        log.error("Connection failed : {}", e.getMessage());
                        System.out.println("Connection failed... ");
                        return;
                    }

                    f.addListener((ChannelFutureListener) future -> {
                        if (future.isSuccess()) {
                            channel = future.channel();
                        } else {
                            System.out.println("Failed connection to the server");
                            log.error("Connection failed : {}", future.cause().getMessage());
                        }
                    });
                    f.channel().closeFuture().sync();
                    System.out.println("Connection closed");
                } catch (InterruptedException e) {
                    log.error("Client interrupted", e);
                } finally {
                    group.shutdownGracefully();
                }
            }).start();
        } catch (Exception e) {
            log.error("Client error: {}", e.getMessage());
            System.out.println("Client error... ");
        }
    }

    public ChannelFuture sendMessage(String msg){
        if (channel == null || !channel.isActive()){
            System.out.println("Client: channel is not active");
            return null;
        }

        return channel.writeAndFlush(msg);
    }

    public void closeConnection() {
        if (channel != null && channel.isActive()) {
            channel.close();
        }
    }
}

