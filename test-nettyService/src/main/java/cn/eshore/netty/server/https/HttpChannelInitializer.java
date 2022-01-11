package cn.eshore.netty.server.https;

import java.io.FileInputStream;
import java.security.KeyStore;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.ssl.SslHandler;

public class HttpChannelInitializer  extends ChannelInitializer<SocketChannel> {
    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        if (HttpServer.isSSL) {
        	System.out.println("use SSL");
    		KeyStore ks = KeyStore.getInstance("JKS");
    		String pass = "servers";
    		ks.load(new FileInputStream("/Users/wujingyou/Documents/workspace/NettyServer/server_DSA.jks"), pass.toCharArray());
    		
    		// Set up key manager factory to use our key store
    		KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
    		kmf.init(ks, pass.toCharArray());
    		KeyManager[] km = kmf.getKeyManagers();
    		
    		//双向认证时需要设置
    		TrustManagerFactory tmFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
    		tmFactory.init(ks);
    		TrustManager[] tm = tmFactory.getTrustManagers();

    		SSLContext sslContext = SSLContext.getInstance("TLS");
    		sslContext.init(km, tm, null);
    		SSLEngine sslEngine = sslContext.createSSLEngine();
    		sslEngine.setUseClientMode(false);
    		//双向认证时需要设置
    		sslEngine.setNeedClientAuth(true);

    		sslEngine.setEnabledProtocols(sslEngine.getSupportedProtocols());
    		sslEngine.setEnabledCipherSuites(sslEngine.getSupportedCipherSuites());
    		sslEngine.setEnableSessionCreation(true);
    		pipeline.addLast(new SslHandler(sslEngine));
        }
        
        //编码
        pipeline.addLast(new HttpResponseEncoder());
        //解码
        pipeline.addLast(new HttpRequestDecoder());
        
        //自定义业务逻辑处理
        pipeline.addLast(new HttpServerInboundHandler());
     		      
    }
}
