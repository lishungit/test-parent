/**
 * 
 */
package cn.eshore.netty.coder;

import cn.eshore.netty.pojo.BaseResponse;
import cn.eshore.netty.pojo.MsgPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author wujingyou
 *
 */
public class ResponseEncoder extends MessageToByteEncoder<BaseResponse> {

	@Override
	protected void encode(ChannelHandlerContext ctx, BaseResponse msg, ByteBuf out) throws Exception {
		//
		MsgPacket mp = new MsgPacket(msg.toBytes());	
		out.writeBytes(mp.toBytes());
		
	}

}
