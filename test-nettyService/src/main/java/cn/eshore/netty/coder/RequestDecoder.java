/**
 * 
 */
package cn.eshore.netty.coder;

import java.util.List;

import cn.eshore.netty.constant.codeDefine.ReqCode;
import cn.eshore.netty.pojo.BaseRequest;
import cn.eshore.netty.pojo.MsgPacket;
import cn.eshore.netty.pojo.Test1Request;
import cn.eshore.netty.utils.ByteBufToBytes;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * @author wujingyou
 *
 */
public class RequestDecoder extends ByteToMessageDecoder {
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		int readableLength = in.readableBytes();
		if (readableLength < MsgPacket.MIN_MSG_PACKET_LENGHT) { //包不完整
            return; 
        }
		
		byte headerFlag = in.readByte();
		if(headerFlag != MsgPacket.HEARDER_FLAG){
			return;
		}
		
		int dataLength = in.readInt(); //业务数据包长度
		if(dataLength >= readableLength){
			return;
		}
		ByteBuf dataService = in.readBytes(dataLength); //
		
		byte tailFlag = in.readByte();
		if(tailFlag != MsgPacket.TAIL_FLAG){
			return;
		}
//		byte verifyFlag = in.readByte();
//		//重新计算校验位是否相等
//    	byte[] servicePacket = ByteBufToBytes.getBytes(dataService);
//    	MsgPacket mp = new MsgPacket(servicePacket);
//    	if(verifyFlag != mp.getVerifyFlag()){
//    		return;
//    	}
    	//
    	ByteBufAllocator allocator = ctx.pipeline().channel().alloc();
    	ByteBuf bbReqCode = allocator.heapBuffer(ReqCode.CONSTANT_LENGHT);
    	dataService.getBytes(0, bbReqCode, ReqCode.CONSTANT_LENGHT);
    	int iReqCode = ReqCode.INT(bbReqCode.array());
    	
    	BaseRequest baseRequest = null;
		switch(iReqCode){
		case ReqCode.QUERY_TEST1_V: { //
			baseRequest = genTest1Request(dataService);	 
			 break;
		}			
		}
		
		if(baseRequest != null){
			out.add(baseRequest);
		}
	}

	private Test1Request genTest1Request(ByteBuf dataService) {
		ByteBuf reqMsg =  dataService.readBytes(30);
		Test1Request uar = new Test1Request(ByteBufToBytes.read(reqMsg));
		return uar;
	}
	
}
