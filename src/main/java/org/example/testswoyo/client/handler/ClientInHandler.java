package org.example.testswoyo.client.handler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.testswoyo.client.utils.ClientGlobalResponseHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ConnectException;


@Slf4j
@Component
@RequiredArgsConstructor
public class ClientInHandler extends SimpleChannelInboundHandler<String> {

    private final ClientGlobalResponseHandler clientGlobalResponseHandler;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("Client channel connected successfully");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        log.info("Server response: {}", msg);
        String response = clientGlobalResponseHandler.analyze(msg);
        System.out.println(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof ConnectException) {
            log.error("Connection refused: Could not connect to server at {}: {}",
                    ctx.channel().localAddress(), cause.getMessage());
        } else if (cause instanceof IOException) {
            log.error("IOException occurred: {}", cause.getMessage());
        } else {
            log.error("Unexpected error: {}", cause.getMessage());
        }

        ctx.close();
    }

}