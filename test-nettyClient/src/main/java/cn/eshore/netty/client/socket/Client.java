/**
 * 
 */
package cn.eshore.netty.client.socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import cn.eshore.netty.pojo.BaseRequest;
import cn.eshore.netty.pojo.Test1Request;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author wujingyou client
 */
public class Client {

	public static boolean isSSL = true;
	static final String hostIP = "192.168.0.102";//"172.20.10.3";
    static final String HOST = System.getProperty("host", hostIP);
	static final int PORT = Integer.parseInt(System.getProperty("port", "8992"));

	public static void main(String[] aSrgs) throws Exception {

		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class).handler(new ClientChannelInitializer());
			// Start the connection attempt.
			Channel ch = b.connect(HOST, PORT).sync().channel();

			// Read commands from the stdin.
			ChannelFuture lastWriteFuture = null;
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			BaseRequest request = null;
			boolean isExit = false;
			for (;;) {
				
				println("------------------------------------------------------");
				
				println("Please choose one : \n1. test1\n4. Quit");
				String line1 = in.readLine();
				int c = Integer.valueOf(line1);
				switch (c) {
				case 1: 
					String reqMsg = "How much money do you have?";
					request = new Test1Request(reqMsg.getBytes("utf-8"));
					if (request != null) {
						lastWriteFuture = ch.writeAndFlush(request);
						println("--------------------");
					}
					// Wait until all messages are flushed before closing the
					// channel.
					if (lastWriteFuture != null) {
						lastWriteFuture.sync();
					}

					break;
				
				case 4:
					ch.disconnect();//closeFuture();
					isExit = true;
					break;
				}

				if (isExit) {
					break;
				}
			}
		} finally {
			// The connection is closed automatically on shutdown.
			group.shutdownGracefully();
			println("Pragram Over.\n");
		}
	}

	private static void println(String msg) {
		System.out.println(msg);
	}
}
