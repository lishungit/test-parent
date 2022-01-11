/**
 * 
 */
package cn.eshore.netty.utils;

import java.nio.ByteBuffer;

/**
 * @author http://my.oschina.net/u/169390/blog/97495
 *
 */
public class ByteHelper {
	/**
	 * int到byte[]
	 * 
	 * @param i
	 * @return
	 */
	public static byte[] intToByteArray(int i) {
		byte[] result = new byte[4];
		// 由高位到低位
		result[0] = (byte) ((i >> 24) & 0xFF);
		result[1] = (byte) ((i >> 16) & 0xFF);
		result[2] = (byte) ((i >> 8) & 0xFF);
		result[3] = (byte) (i & 0xFF);
		return result;
	}

	/**
	 * byte[]转int
	 * 
	 * @param bytes
	 * @return
	 */
	public static int byteArrayToInt(byte[] bytes) {
		int value = 0;
		// 由高位到低位
		for (int i = 0; i < 4; i++) {
			int shift = (4 - 1 - i) * 8;
			value += (bytes[i] & 0x000000FF) << shift;// 往高位游
		}
		return value;
	}

	// byte 数组与 long 的相互转换
	public static byte[] longToBytes(long x) {
		ByteBuffer buffer = ByteBuffer.allocate(10);
		buffer.putLong(0, x);
		return buffer.array();
	}

	public static long bytesToLong(byte[] bytes) {
//		ByteBuffer buffer = ByteBuffer.allocate(10);
//		buffer.put(bytes, 0, bytes.length);
//		buffer.flip();// need flip
//		return buffer.getLong();
		long value = 0;
		// 由高位到低位
		for (int i = 0; i < 8; i++) {
			int shift = (8 - 1 - i) * 8;
			value += (bytes[i] & 0x00000000000000FF) << shift;// 往高位游
		}
		return value;
	}
}
