package com.keep.app.io.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.nio.ByteBuffer;


public class NettyServer {

    int port  = 8888;

    public NettyServer(int port){
        this.port = port;
    }

    public void serverStart(){
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new Handler());
                    }
                });
    }
}

class Handler extends ChannelInboundHandlerAdapter{

    @Override
    public void channelRead(ChannelHandlerContext context,Object msg){
        ByteBuffer buf = (ByteBuffer) msg;
        context.writeAndFlush(msg);
        context.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext context,Throwable cause){
        cause.printStackTrace();
        context.close();
    }
}