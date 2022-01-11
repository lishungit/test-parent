package cn.eshore.netty.client.https;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;


public class HttpClient {

	static final String HOST = System.getProperty("host", "192.168.2.3");
	static final int PORT = Integer.parseInt(System.getProperty("port", "8844"));
	public static boolean isSSL = true;
	
    public void connect(String host, int port) throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ClientChannelInitializer());

            // Start the client.
            Channel ch = b.connect(host, port).sync().channel();

            URI uri;
            if(HttpClient.isSSL){
            	uri = new URI("https://"+HOST+":+" + PORT);
            }else{
            	uri = new URI("http://"+HOST+":+" + PORT);
            }
            String msg = "Are you ok?@"+System.currentTimeMillis();
            
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			//BaseRequest request = null;
			boolean isExit = false;
			for (;;) {
				
				println("------------------------------------------------------");
				
				println("Please choose one : \n1. https test\n4. Quit");
				String line1 = in.readLine();
				int c = Integer.valueOf(line1);
				switch (c) {
				case 1: 
					   DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET,
			                    uri.toASCIIString(), Unpooled.wrappedBuffer(msg.getBytes("UTF-8")));

			            // 构建http请求
			            request.headers().set(HttpHeaders.Names.HOST, host);
			            request.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
			            request.headers().set(HttpHeaders.Names.CONTENT_LENGTH, request.content().readableBytes());
			            
			            // 发送http请求
			            ChannelFuture lastWriteFuture = ch.writeAndFlush(request);
			            if (lastWriteFuture != null) {
							lastWriteFuture.sync();
						}
			           // ch.closeFuture().sync();

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
            workerGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws Exception {
        HttpClient client = new HttpClient();
        client.connect(HOST,  PORT);
    }
    
	private static void println(String msg) {
		System.out.println(msg);
	}
}