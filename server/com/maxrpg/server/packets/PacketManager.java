package com.maxrpg.server.packets;

import java.util.HashMap;

import com.maxrpg.server.Server;
import com.maxrpg.server.player.Player;

public class PacketManager 
{
	private static HashMap<Integer, Packet> packetMap = new HashMap<Integer, Packet>();
	
	static
	{
		packetMap.put(Packet.CHAT, new Chat());
		packetMap.put(Packet.SET_NAME, new SetName());
	}
	
	
	public static void handlePacket(Server server, Player player, int packetType)
	{
		Packet p = packetMap.get(packetType);
		
		if(p != null)
			p.handlePacket(server, player);
	}
}
