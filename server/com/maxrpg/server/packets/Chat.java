package com.maxrpg.server.packets;

import com.maxrpg.server.Server;
import com.maxrpg.server.player.Player;
import com.mdwapps.utils.network.Client;

public class Chat implements Packet
{	
	public void handlePacket(Server server, Player player) 
	{
		String msg = player.getInput().readString();
		
		for(Client client : server.clientGroup)
			((Player) client).getChatManager().sendChatMessage(player, msg);
	}
}
