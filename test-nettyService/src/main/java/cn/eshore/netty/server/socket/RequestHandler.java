/**
 * 
 */
package cn.eshore.netty.server.socket;

import java.io.UnsupportedEncodingException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import cn.eshore.netty.constant.codeDefine.ResponseCode;
import cn.eshore.netty.constant.codeDefine.ReqCode;
import cn.eshore.netty.pojo.BaseRequest;
import cn.eshore.netty.pojo.BaseResponse;
import cn.eshore.netty.pojo.Test1Request;
import cn.eshore.netty.pojo.Test1Response;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author wujingyou
 *
 */
public class RequestHandler extends SimpleChannelInboundHandler<BaseRequest> {

   // static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private static Log log = LogFactory.getLog(RequestHandler.class);
    
	@Override
	public void channelActive(final ChannelHandlerContext ctx) throws Exception {
		//super.channelActive(ctx);
		SslHandler sslHandler = ctx.pipeline().get(SslHandler.class);
		if(sslHandler == null){
			//channels.add(ctx.channel());
            String logMsg =
            		"RemoteIP: " + ctx.channel().remoteAddress().toString()+
                            ".\n";
            log.info(logMsg);
            return;
		}
	   ctx.pipeline().get(SslHandler.class).handshakeFuture().addListener(
                new GenericFutureListener<Future<Channel>>() {

                    public void operationComplete(Future<Channel> future) throws Exception { 
                       // channels.add(ctx.channel());
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
		//channels.remove(ctx.channel());
		String logMsg = "RemoteIP: " + ctx.channel().remoteAddress().toString() + " inactive.\n";
		log.info(logMsg);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		//super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        ctx.close();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, BaseRequest msg) throws Exception {
		log.info("Received message : " + msg.toString());
	    BaseResponse br = null;
	    int iReqCode = ReqCode.INT( msg.getReqCode());
		switch(iReqCode){
		case ReqCode.QUERY_TEST1_V: {
			 br = this.handleTestReq((Test1Request)msg);
			 break;
		}			
		
		}
		
		if(br == null){
	    	ctx.close();
	    	return;
		}
		
		Channel ch = ctx.channel();
		//ChannelFuture lastWriteFuture = 
		ch.writeAndFlush(br);
	}

	private Test1Response handleTestReq(Test1Request msg) throws UnsupportedEncodingException {
		byte[ ] reqCode = msg.getReqCode();

		String respMsg = "Only 5 Mao";

		Test1Response uar = new Test1Response();
		uar.setRespMsg(respMsg.getBytes("utf-8"));
		uar.setReqCode(reqCode);
		uar.setResponseCode(ResponseCode.NORMAL_RETURN); //TODO: 需要根据实际情况设置响应码

		return uar;
	}
	    
}
