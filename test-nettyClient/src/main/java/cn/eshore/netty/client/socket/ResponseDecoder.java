/**
 * 
 */
package cn.eshore.netty.client.socket;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import cn.eshore.netty.constant.codeDefine.ReqCode;
import cn.eshore.netty.pojo.BaseResponse;
import cn.eshore.netty.pojo.MsgPacket;
import cn.eshore.netty.pojo.Test1Response;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * @author wujingyou
 *
 */
public class ResponseDecoder extends ByteToMessageDecoder {

	private static Log log = LogFactory.getLog(ResponseDecoder.class);
	
	/* (non-Javadoc)
	 * @see io.netty.handler.codec.ByteToMessageDecoder#decode(io.netty.channel.ChannelHandlerContext, io.netty.buffer.ByteBuf, java.util.List)
	 */
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		// 
		log.info("ResponseDecoder...decode...\n");
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
		ByteBuf dataService = in.readBytes(dataLength); //业务数据包
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
    	
    	ByteBufAllocator allocator = ctx.pipeline().channel().alloc();
    	ByteBuf bbTradeCode = allocator.heapBuffer(ReqCode.CONSTANT_LENGHT);
    	dataService.getBytes(0, bbTradeCode, ReqCode.CONSTANT_LENGHT);
    	int iTradeCode = ReqCode.INT(bbTradeCode.array());
        
    	BaseResponse response = null;
    	switch(iTradeCode) {
        case ReqCode.QUERY_TEST1_V: //
        	response = this.genTest1RespResponse(dataService);
        	break;
        }
		if(response != null){
			out.add(response);
		}
	}

	private Test1Response genTest1RespResponse(ByteBuf dataService) {
		byte[] reqCode =  dataService.readBytes(4).array();
		byte[] respMsg =  dataService.readBytes(30).array();
		
		Test1Response uar = new Test1Response();
		uar.setReqCode(reqCode);					
		uar.setRespMsg(respMsg);
		byte[] responseCode =  dataService.readBytes(2).array();
		uar.setResponseCode(responseCode); 
        return uar;
	}
	
}
