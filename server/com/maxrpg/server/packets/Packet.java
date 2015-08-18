package com.maxrpg.server.packets;

import com.maxrpg.server.Server;
import com.maxrpg.server.player.Player;

public interface Packet 
{
	public static final int CHAT = 1;
	
	public static final int SET_NAME = 2;
	
	public static final int PLAYER_COUNT = 3;
	
	public void handlePacket(Server server, Player player);
}
