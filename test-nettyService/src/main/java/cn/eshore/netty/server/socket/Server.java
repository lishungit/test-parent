/**
 * 
 */
package cn.eshore.netty.server.socket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author wujingyou
 *
 */
public class Server {
	private static Log log = LogFactory.getLog(Server.class);
	static final int PORT = Integer.parseInt(System.getProperty("port", "8992"));
	public static boolean isSSL = true;

	public static void start(int port) throws Exception {
		//启动两个线程池：一个负责接客，一个负责收钱
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
					.childHandler(new ServerInitializer());

			ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();      
		} finally {
			 bossGroup.shutdownGracefully();
			 workerGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws Exception {
		log.info("Socket server start.");
		start(PORT);
	}
	
}
