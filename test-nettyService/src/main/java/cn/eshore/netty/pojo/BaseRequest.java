/**
 * 
 */
package cn.eshore.netty.pojo;

/**
 * @author wujingyou
 *
 */
public abstract class BaseRequest {
	//
	private byte[ ] reqCode;  //4个byte
	
	public byte[] getReqCode() {
		return reqCode;
	}
	public void setReqCode(byte[] tradeCode) {
		this.reqCode = tradeCode;
	}
	
	public abstract byte[ ] toBytes();
	
}
