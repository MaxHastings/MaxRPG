package com.maxrpg.server.packets;

import com.maxrpg.server.player.Player;

public interface Packet 
{
	public static final int CHAT = 1;
	
	public void handlePacket(Player player);
}
