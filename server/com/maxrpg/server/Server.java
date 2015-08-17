package com.maxrpg.server;

import java.net.Socket;

import com.maxrpg.server.player.Player;
import com.mdwapps.utils.network.Client;


public class Server extends com.mdwapps.utils.network.Server
{

	public Server(int port, boolean localHost) 
	{
		super(port, localHost);
		
	}
	
	public void onStart()
	{
		
	}
	
	public void onClientConnected(Client client) 
	{
		
	}

	public void onClientDisconnected(Client client) 
	{
		
	}

	public void onCommandEntered(String command, int argc, String... args) 
	{
		
	}

	public void onPacketReceived(Client client, int packetType) 
	{
		
	}
	
	public void onStop()
	{
		
	}


	protected Client createClient(Socket socket) 
	{
		return new Player(socket);
	}
	
	
	public void sendBroadcast(String msg)
	{
		for(Client client : clientGroup)
			((Player) client).getChatManager().sendBroadcast(msg);
	}
	
}
