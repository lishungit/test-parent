/**
 * 
 */
package cn.eshore.netty.pojo;

/**
 * @author wujingyou
 *
 */
public abstract class BaseResponse {
	//
	private byte[ ] reqCode; // = {1,1,0,0};  
	//
	private byte[ ] responseCode;// = new byte[2];
	
	public byte[] getReqCode() {
		return reqCode;
	}
	public void setReqCode(byte[] reqCode) {
		this.reqCode = reqCode;
	}
	public byte[] getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(byte[] responseCode) {
		this.responseCode = responseCode;
	}
	
	public abstract byte[ ] toBytes();
}
