/**
 * 
 */
package cn.eshore.netty.coder;

import cn.eshore.netty.pojo.BaseRequest;

/**
 * @author wujingyou
 *
 */
public class MsgPacketHelper {

	public BaseRequest parseMsgPacket(byte[ ] servicePacket){
		if(servicePacket == null || servicePacket.length == 0){
			return null;
		}
		
		return null;		
	}
}
