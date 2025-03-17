package org.example.testswoyo.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.example.testswoyo.server.utils.ServerGlobalRequestHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@ChannelHandler.Sharable
public class ServerInHandler extends SimpleChannelInboundHandler<String> {

    private final ServerGlobalRequestHandler serverGlobalRequestHandler;

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        log.info("Client {} connected", ctx.channel().localAddress());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("Client channel registered successfully {}", ctx.channel().localAddress());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        log.info("Client request: {}", msg);
        String response = serverGlobalRequestHandler.analyze(msg);
        log.info("Server response: {}", response);
        ctx.writeAndFlush(response);
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("Client {} disconnected", ctx.channel().localAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("Error: {}", cause.getMessage());
        ctx.close();
    }

}