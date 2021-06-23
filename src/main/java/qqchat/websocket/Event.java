
package com.github.zyxgad.qqchat.websocket;

import java.io.OutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.DataInputStream;
import java.io.IOException;

import org.bukkit.OfflinePlayer;

import com.github.zyxgad.qqchat.QQChat;
import com.github.zyxgad.qqchat.config.UserConfig;
import com.github.zyxgad.qqchat.util.Util;

public final class Event{
	public static final byte RECV_ID     = (byte)(0xff);
	public static final byte SEND_MSG_ID = (byte)(0x01);
	public static final byte SEND_CMD_ID = (byte)(0x02);

	private final int id;
	private final byte tid;
	private byte[] data;

	private Event(int id, byte tid, byte[] data){
		this.id = id;
		this.tid = tid;
		this.data = data;
	}

	public int getId(){
		return this.id;
	}

	public byte getTId(){
		return this.tid;
	}

	public byte[] getData(){
		return this.data;
	}

	public void writeTo(DataOutputStream outstream) throws IOException{
		outstream.writeInt(this.id);
		outstream.writeByte(this.tid);
		outstream.writeInt(this.data.length);
		outstream.write(this.data);
	}

	public void writeTo(OutputStream outstream) throws IOException{
		this.writeTo(new DataOutputStream(outstream));
	}

	static Event recvMessage(DataInputStream instream) throws IOException{
		Event recv = null;
		final int id = instream.readInt();
		final byte type = instream.readByte();
		final long qqid = instream.readLong();
		final int length = instream.readInt();
		final byte[] data = new byte[length];
		instream.read(data);
		switch(type){
			case SEND_MSG_ID:{
				String msg = Util.bytesToString(data);
				OfflinePlayer player = UserConfig.getInstance().getQQPlayer(qqid);
				if(player == null){
					recv = Event.newRecvMessage(id, "[消息发送失败]此QQ未绑定游戏账号");
					break;
				}
				QQChat.INSTANCE.sendMessage(player, msg);
				recv = Event.newRecvMessage(id, "");
				break;
			}
			case SEND_CMD_ID:{
				String msg = Util.bytesToString(data);
				recv = Event.newRecvMessage(id, "[指令执行失败]" + msg);
				break;
			}
		}
		return recv;
	}
	static Event recvMessage(InputStream instream) throws IOException{
		return Event.recvMessage(new DataInputStream(instream));
	}

	public static Event newRecvMessage(int id, String data){
		return new Event(id, Event.RECV_ID, Util.stringToBytes(data));
	}

	public static Event newPlayerChatMessage(String data){
		return new Event(0, Event.SEND_MSG_ID, Util.stringToBytes(data));
	}
}
