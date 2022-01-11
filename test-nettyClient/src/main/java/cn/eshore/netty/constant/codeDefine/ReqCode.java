/**
 * 
 */
package cn.eshore.netty.constant.codeDefine;

import cn.eshore.netty.utils.ByteHelper;

/**
 * @author wujingyou
 */
public class ReqCode {
	public static final int CONSTANT_LENGHT = 4; 
	
    //
	public static final byte[ ] QUERY_TEST1 =  {1,1,0,0};
	public static final int QUERY_TEST1_V =  16842752;//ReqCode.INT(QUERY_THREE_ITEM_BILL);
 
    public static int INT(byte[] bTradeCode){
    	return ByteHelper.byteArrayToInt(bTradeCode);
    }
    
    public static boolean isEqual(byte[] bTradeCode1, byte[] bTradeCode2){
    	int code1 =  ByteHelper.byteArrayToInt(bTradeCode1);
    	int code2 =  ByteHelper.byteArrayToInt(bTradeCode2);
    	return (code1 == code2);
    }
}
