package com.maxrpg.server.packets;

import com.maxrpg.server.Server;
import com.maxrpg.server.player.Player;
import com.mdwapps.utils.network.Client;
import com.mdwapps.utils.network.Debug;

public class SetName implements Packet
{
	public void handlePacket(Server server, Player player) 
	{
		String name = player.getInput().readString();
		Debug.log("Requested name: " + name);
		
		boolean taken = false;
		
		for(Client c : server.clientGroup)
		{
			Player p = (Player) c;
			if(p.getName().equalsIgnoreCase(name))
			{
				taken = true;
				break;
			}
		}
		
		int success = name.trim().length() == 0 || taken ? 0 : 1;
		
		player.getOutput().createPacket(Packet.SET_NAME);
		player.getOutput().putByte(success);
		player.getOutput().endPacket();
		player.flushStream();
		
		if(success == 1)
		{
			player.setName(name);
			player.getChatManager().sendBroadcast("Welcome to Max And Simba's RPG. Simba does all the work though :).");
			player.init();
			server.sendNewPlayer(player);
			
			int count = server.clientGroup.size();
			for(Client c : server.clientGroup)
			{
				Player p = (Player) c;
				p.getOutput().createPacket(Packet.PLAYER_COUNT);
				p.getOutput().putWord(count);
				p.getOutput().endPacket();
				p.flushStream();
				
			}
		}
	}

}
