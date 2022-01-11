package cn.eshore.netty.server.https;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class HttpServer {

    private static Log log = LogFactory.getLog(HttpServer.class);
    public static boolean isSSL = true;
    static final int PORT = 8844;
    
    public static void start(int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new HttpChannelInitializer());
            
            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();               
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        log.info("Http Server listening on "+PORT+"...");
        start(PORT);
    }

}
