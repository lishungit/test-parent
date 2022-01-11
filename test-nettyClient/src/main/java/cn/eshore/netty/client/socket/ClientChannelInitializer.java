/**
 * 
 */
package cn.eshore.netty.client.socket;

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
import io.netty.handler.ssl.SslHandler;

/**
 * @author wujingyou
 *
 */
public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

	private final String JKS_FILE = "client_DSA.jks";

	@Override
	public void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		if (Client.isSSL) {
			TrustManagerFactory tmFactory = TrustManagerFactory.getInstance("SunX509");
			KeyStore tmpKS = KeyStore.getInstance("JKS");

			String pass = "clients";// "cNetty";

			ClassLoader classLoader = getClass().getClassLoader();
			tmpKS.load(new FileInputStream(classLoader.getResource(JKS_FILE).getFile()), pass.toCharArray());
			tmFactory.init(tmpKS);

			KeyManagerFactory kmFactory = KeyManagerFactory.getInstance("SunX509");
			// KeyStore ks = null;
			// kmFactory.init(ks, null);
			// 或者
			kmFactory.init(tmpKS, pass.toCharArray());

			KeyManager[] km = kmFactory.getKeyManagers();
			TrustManager[] tm = tmFactory.getTrustManagers();

			SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(km, tm, null);
			SSLEngine sslEngine = sslContext.createSSLEngine();
			sslEngine.setUseClientMode(true);
			sslEngine.setEnabledProtocols(sslEngine.getSupportedProtocols());
			sslEngine.setEnabledCipherSuites(sslEngine.getSupportedCipherSuites());
			sslEngine.setEnableSessionCreation(true);
			// pipeline.addLast("ssl", new SslHandler(sslEngine));
			pipeline.addLast(new SslHandler(sslEngine));
		}
		
		pipeline.addLast(new RequestEncoder());
		pipeline.addLast(new ResponseDecoder());		
		
		pipeline.addLast(new ResponseHandler());
		
	}

}
