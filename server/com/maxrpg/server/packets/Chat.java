package com.maxrpg.server.packets;

import com.maxrpg.server.Server;
import com.maxrpg.server.player.Player;
import com.mdwapps.apputils.UString;
import com.mdwapps.utils.network.Client;
import com.mdwapps.utils.network.Debug;

public class Chat implements Packet
{	
	public void handlePacket(Server server, Player player) 
	{
		String msg = player.getInput().readString();
		Debug.log(player.getName() + ": " + msg);
		
		if(msg.startsWith("/whisper"))
		{
			String whisperMessage = UString.getWords(msg, 2, UString.getWordCount(msg) - 1);
			String recipientName = UString.getWord(msg, 1);
			Player recipient = server.findPlayerByName(recipientName);
			
			if(recipient != null)
			{
				if(recipient == player)
				{
					player.getChatManager().sendBroadcast("You really shouldn't talk to yourself.");
					return;
				}
				
				player.getChatManager().sendWhisper(recipient, whisperMessage);
			}
			else
				player.getChatManager().sendBroadcast("Unable to find player with the name '" + recipientName + "'.");
		}
		else if(msg.startsWith("/r"))
		{
			String whisperMessage = UString.getWords(msg, 1, UString.getWordCount(msg) - 1);
			
			if(player.getChatManager().lastWhisperer != null)
				player.getChatManager().sendWhisper(player.getChatManager().lastWhisperer, whisperMessage);
			else
				player.getChatManager().sendBroadcast("You haven't whispered anybody yet");
		}
		else
		{
			for(Client client : server.clientGroup)
				((Player) client).getChatManager().sendChatMessage(player, msg);
		}
	}
}
