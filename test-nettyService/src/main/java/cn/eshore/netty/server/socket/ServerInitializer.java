/**
 * 
 */
package cn.eshore.netty.server.socket;

import java.io.FileInputStream;
import java.security.KeyStore;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import cn.eshore.netty.coder.RequestDecoder;
import cn.eshore.netty.coder.ResponseEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslHandler;

/**
 * @author wujingyou
 *
 */
public class ServerInitializer extends ChannelInitializer<SocketChannel> {

	private final String JKS_FILE = "server_DSA.jks";

	@Override
	public void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		if (Server.isSSL) {
			KeyStore ks = KeyStore.getInstance("JKS");

			String pass = "servers";
			ClassLoader classLoader = getClass().getClassLoader();
			ks.load(new FileInputStream(classLoader.getResource(JKS_FILE).getFile()), pass.toCharArray());

			// Set up key manager factory to use our key store
			KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
			kmf.init(ks, pass.toCharArray());
			KeyManager[] km = kmf.getKeyManagers();

			// 双向认证时需要设置
			TrustManagerFactory tmFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			tmFactory.init(ks);
			TrustManager[] tm = tmFactory.getTrustManagers();

			SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(km, tm, null);
			SSLEngine sslEngine = sslContext.createSSLEngine();
			sslEngine.setUseClientMode(false);
			// 双向认证时需要设置
			sslEngine.setNeedClientAuth(true);

			sslEngine.setEnabledProtocols(sslEngine.getSupportedProtocols());
			sslEngine.setEnabledCipherSuites(sslEngine.getSupportedCipherSuites());
			sslEngine.setEnableSessionCreation(true);
			pipeline.addLast(new SslHandler(sslEngine));
		}
		
		//编码
		pipeline.addLast(new ResponseEncoder());
		//解码
		pipeline.addLast(new RequestDecoder());
		
		// 自定义业务逻辑处理
		pipeline.addLast(new RequestHandler());

	}

}
