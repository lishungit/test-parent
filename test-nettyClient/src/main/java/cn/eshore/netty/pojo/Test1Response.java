/**
 * 
 */
package cn.eshore.netty.pojo;

import java.nio.ByteBuffer;

/**
 * @author wujingyou
 */
public class Test1Response extends BaseResponse {
	//
	private byte[ ] respMsg;// = new byte[30];  

	public byte[] getRespMsg() {
		return respMsg;
	}
	public void setRespMsg(byte[] respMsg) {
		this.respMsg = respMsg;
	}
	
	@Override
	public byte[] toBytes() {
		//ByteBuf buffer = ByteBufUtil.threadLocalDirectBuffer();
		ByteBuffer bb = ByteBuffer.allocate(36);
		//bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.put(this.getReqCode());
        bb.put(this.getRespMsg());
        bb.put(this.getResponseCode());
        return bb.array();
	}

}
