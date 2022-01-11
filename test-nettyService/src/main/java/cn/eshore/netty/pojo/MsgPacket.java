package cn.eshore.netty.pojo;

import java.nio.ByteBuffer;

import cn.eshore.netty.utils.ByteHelper;


public class MsgPacket {

	public static final byte HEARDER_FLAG =  0x02;  //包头标识
	public static final byte TAIL_FLAG =  0x03;  //包尾标识
	public static final int MIN_MSG_PACKET_LENGHT = 12; //需要满足最小消息包的长度
	public static final int PacketFixedLength = 6; //(headerFlag、packetLength、tailFlag 三者长度和)

    private byte headerFlag = HEARDER_FLAG;  // 1bit
    private byte[ ] packetLength  = new byte[4];  // 4bit
    private byte[ ] servicePacket;  // n bit
    private byte tailFlag = TAIL_FLAG;    // 1bit
    //private byte verifyFlag;  // 1bit

    public MsgPacket(byte[ ] servicePacket) {
    	this.servicePacket = servicePacket;
    	int len = this.servicePacket.length;
    	this.packetLength = ByteHelper.intToByteArray(len);
    	//计算校验码
    	byte tmp = HEARDER_FLAG;
    	for(byte b : this.packetLength){
    		tmp = (byte) (tmp ^  b);
    	}

    	for(byte b : this.servicePacket){
    		tmp = (byte) (tmp ^  b);
    	}
    	//this.verifyFlag = (byte) ((tmp ^ TAIL_FLAG) & 0xFF);
    }

    public byte[ ] toBytes() {
		//ByteBuf buffer = ByteBufUtil.threadLocalDirectBuffer();
		ByteBuffer bb = ByteBuffer.allocate(MsgPacket.PacketFixedLength+this.servicePacket.length);
		//bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.put(this.getHeaderFlag());
        bb.put(this.getPacketLength());
        bb.put(this.getServicePacket());
        bb.put(this.getTailFlag());
       // bb.put(this.getVerifyFlag());

        return bb.array();
    }

	public byte getHeaderFlag() {
		return headerFlag;
	}
	public void setHeaderFlag(byte headerFlag) {
		this.headerFlag = headerFlag;
	}
	public byte[] getPacketLength() {
		return packetLength;
	}
	public void setPacketLength(byte[] packetLength) {
		this.packetLength = packetLength;
	}
	public byte[ ] getServicePacket() {
		return servicePacket;
	}
	public void setServicePacket(byte[ ] servicePacket) {
		this.servicePacket = servicePacket;
	}
	public byte getTailFlag() {
		return tailFlag;
	}
	public void setTailFlag(byte tailFlag) {
		this.tailFlag = tailFlag;
	}

}
