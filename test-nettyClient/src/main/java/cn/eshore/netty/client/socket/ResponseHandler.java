/**
 * 
 */
package cn.eshore.netty.client.socket;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import cn.eshore.netty.constant.codeDefine.ReqCode;
import cn.eshore.netty.pojo.BaseResponse;
import cn.eshore.netty.pojo.Test1Response;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.net.InetAddress;

/**
 * @author wujingyou
 *
 */
public class ResponseHandler extends SimpleChannelInboundHandler<BaseResponse> {
	private static Log log = LogFactory.getLog(ResponseHandler.class);

	@Override
	public void channelActive(final ChannelHandlerContext ctx) {
		// Once session is secured, send a greeting and register the channel to
		// the global channel
		// list so the channel received the messages from others.
		if(Client.isSSL){
			
		    ctx.pipeline().get(SslHandler.class).handshakeFuture()
				.addListener(new GenericFutureListener<Future<Channel>>() {
					public void operationComplete(Future<Channel> future) throws Exception {
						ctx.writeAndFlush("Come from Client:  " + InetAddress.getLocalHost().getHostName() + "\n");
					}
				});
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, BaseResponse msg) throws Exception {
		// TODO Auto-generated method stub
		log.info("ResponseHandler...channelRead0...\n");
	    byte[ ] reqCode = msg.getReqCode(); 
	    int iReqCode = ReqCode.INT(reqCode);
		switch(iReqCode){
			case ReqCode.QUERY_TEST1_V: {
				Test1Response uar = (Test1Response)msg;
				byte[] bRespMsg = uar.getRespMsg();
				String respMsg = new String(bRespMsg, "utf-8");
				log.info("收到服务端响应的消息："+respMsg);
			}
			break;
		}
	}

}
