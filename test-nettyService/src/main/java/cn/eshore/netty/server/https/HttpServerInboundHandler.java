package cn.eshore.netty.server.https;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpHeaders.Values;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.handler.codec.http.HttpRequest;

public class HttpServerInboundHandler extends ChannelInboundHandlerAdapter {

    private static Log log = LogFactory.getLog(HttpServerInboundHandler.class);

    private HttpRequest request;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        if (msg instanceof HttpRequest) {
            request = (HttpRequest) msg;

            String uri = request.getUri();
            System.out.println("Uri:" + uri);
        }
        if (msg instanceof HttpContent) {
            HttpContent content = (HttpContent) msg;
            ByteBuf buf = content.content();
            System.out.println("收到客户端消息："+buf.toString(io.netty.util.CharsetUtil.UTF_8));
            buf.release();

            String res = "I am OK@" + System.currentTimeMillis();
            FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,
                    OK, Unpooled.wrappedBuffer(res.getBytes("UTF-8")));
            response.headers().set(CONTENT_TYPE, "text/plain");
            response.headers().set(CONTENT_LENGTH,
                    response.content().readableBytes());
            if (HttpHeaders.isKeepAlive(request)) {
                response.headers().set(CONNECTION, Values.KEEP_ALIVE);
            }
            ctx.write(response);
            ctx.flush();
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error(cause.getMessage());
        ctx.close();
    }
    
    
   	@Override
   	public void channelActive(final ChannelHandlerContext ctx) throws Exception {
   		//super.channelActive(ctx);
   		SslHandler sslHandler = ctx.pipeline().get(SslHandler.class);
   		if(sslHandler == null){
               String logMsg =
               		"RemoteIP: " + ctx.channel().remoteAddress().toString()+
                               ".\n";
               log.info(logMsg);
               return;
   		}
   	   ctx.pipeline().get(SslHandler.class).handshakeFuture().addListener(
                   new GenericFutureListener<Future<Channel>>() {

                       public void operationComplete(Future<Channel> future) throws Exception { 
                       	//TODO: 建立黑名单，如果ip在里面则不给连接
                           String logMsg =
                           		"RemoteIP: " + ctx.channel().remoteAddress().toString()+
                                           ".\n" +
                           		"Session is protected by " +
                                           ctx.pipeline().get(SslHandler.class).engine().getSession().getCipherSuite() +
                                           " cipher suite.\n";
                           log.info(logMsg);
                       }
           });
   	}
    
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		String logMsg = "RemoteIP: " + ctx.channel().remoteAddress().toString() + " inactive.\n";
		log.info(logMsg);
	}
}
