/**
 * 
 */
package cn.eshore.netty.client.socket;

import cn.eshore.netty.pojo.BaseRequest;
import cn.eshore.netty.pojo.MsgPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author wujingyou
 *
 */
public class RequestEncoder extends MessageToByteEncoder<BaseRequest> {

	@Override
	protected void encode(ChannelHandlerContext ctx, BaseRequest msg, ByteBuf out) throws Exception {
		MsgPacket mp = new MsgPacket(msg.toBytes());
		out.writeBytes(mp.toBytes());
		System.out.println("原始字节："+Bytes2HexString(mp.toBytes()));
	}

	public static String Bytes2HexString(byte[] b) {
		String ret = "";
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			ret += hex.toUpperCase();
		}
		return ret;
	}
}
