/**
 * 
 */
package cn.eshore.netty.pojo;

import java.nio.ByteBuffer;
import cn.eshore.netty.constant.codeDefine.ReqCode;

/**
 * @author wujingyou
 *
 */
public class Test1Request extends BaseRequest {
	private byte[ ] reqMsg;// = new byte[30]; 

	public Test1Request(){
		this.setReqCode(ReqCode.QUERY_TEST1); 
	}
	
	public Test1Request(byte[] reqMsg) {
		this.setReqCode(ReqCode.QUERY_TEST1);
		this.setReqMsg(reqMsg);
		
	}
	
	public byte[] getReqMsg() {
		return reqMsg;
	}
	public void setReqMsg(byte[] reqMsg) {
		this.reqMsg = reqMsg;
	}

	@Override
	public byte[ ] toBytes() {
		//ByteBuf buffer = ByteBufUtil.threadLocalDirectBuffer();
		ByteBuffer bb = ByteBuffer.allocate(34);
		//bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.put(this.getReqCode());        
        bb.put(this.getReqMsg());
        
        return bb.array();
	}
	
	
}
