package com.maxrpg.server.player;

import com.maxrpg.server.packets.Packet;

public class ChatManager 
{	
	/* Chat IDs:
	 * 0 - Server Broadcast
	 * 1 - Regular chat message
	 * 2 - Whisper message
	 */
	
	private Player player;
	
	public Player lastWhisperer;
	
	public ChatManager(Player player)
	{
		this.player = player;
		lastWhisperer = null;
	}
	
	public void sendBroadcast(String msg)
	{
		sendMessage(msg, 0);
	}
	
	public void sendChatMessage(Player from, String msg)
	{
		String chat = from.getName() + ": " + msg;
		sendMessage(chat, 1);
	}	
	
	public void sendWhisper(Player recipient, String msg)
	{
		String whisperFrom = "From " + player.getName() + ": " + msg;
		recipient.getChatManager().sendMessage(whisperFrom, 2);
		recipient.getChatManager().lastWhisperer = player;
		
		String whisperTo = "To " + recipient.getName() + ": " + msg;
		sendMessage(whisperTo, 2);
		lastWhisperer = recipient;
	}
	
	private void sendMessage(String msg, int type)
	{
		player.getOutput().createPacket(Packet.CHAT);
		player.getOutput().putByte(type);
		player.getOutput().putString(msg);
		player.getOutput().endPacket();
		player.flushStream();
	}
}
