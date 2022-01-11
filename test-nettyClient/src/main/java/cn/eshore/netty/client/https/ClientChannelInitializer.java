/**
 *
 */
package cn.eshore.netty.client.https;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.security.KeyStore;

/**
 * @author wujingyou
 */
public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        if (HttpClient.isSSL) {
            TrustManagerFactory tmFactory = TrustManagerFactory.getInstance("SunX509");
            KeyStore tmpKS = KeyStore.getInstance("JKS");
            String filepath = "resources/client_DSA.jks";
            String pass = "clients";
            tmpKS.load(new FileInputStream(filepath),
                    pass.toCharArray());
            tmFactory.init(tmpKS);

            KeyManagerFactory kmFactory = KeyManagerFactory.getInstance("SunX509");
            // KeyStore ks = null;
            // kmFactory.init(ks, null);
            // 或者
            kmFactory.init(tmpKS, pass.toCharArray());

            KeyManager[] km = kmFactory.getKeyManagers();
            TrustManager[] tm = tmFactory.getTrustManagers();

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(km, tm, null);
            SSLEngine sslEngine = sslContext.createSSLEngine();
            sslEngine.setUseClientMode(true);
            sslEngine.setEnabledProtocols(sslEngine.getSupportedProtocols());
            sslEngine.setEnabledCipherSuites(sslEngine.getSupportedCipherSuites());
            sslEngine.setEnableSessionCreation(true);
            pipeline.addLast(new SslHandler(sslEngine));
        }

        //解码
        ch.pipeline().addLast(new HttpResponseDecoder());
        //编码
        ch.pipeline().addLast(new HttpRequestEncoder());

        ch.pipeline().addLast(new HttpClientInboundHandler());

    }

}
