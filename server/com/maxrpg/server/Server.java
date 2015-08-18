package com.maxrpg.server;

import java.net.Socket;

import com.maxrpg.server.packets.Packet;
import com.maxrpg.server.packets.PacketManager;
import com.maxrpg.server.player.Player;
import com.mdwapps.utils.network.Client;
import com.mdwapps.utils.network.Debug;


public class Server extends com.mdwapps.utils.network.Server
{

	public Server(int port, boolean localHost) 
	{
		super(port, localHost);
		
	}
	
	public void onStart()
	{
		Debug.log("Server started.");
	}
	
	public void onClientConnected(Client client) 
	{
		Debug.log("Client connected from: " + client.getIPAddress());
	}

	public void onClientDisconnected(Client client) 
	{
		Debug.log("Client disconnected from: " + client.getIPAddress());
		
		for(Client c : clientGroup)
		{
			if(c == client)
				continue;
			
			c.getOutput().createPacket(Packet.PLAYER_COUNT);
			c.getOutput().putWord(clientGroup.size() - 1);
			c.getOutput().endPacket();
			c.flushStream();
		}
	}

	public void onCommandEntered(String command, int argc, String... args) 
	{
		if(command.equalsIgnoreCase("shutdown"))
		{
			shutdown();
		}
		else if(command.equalsIgnoreCase("clients"))
		{
			System.out.println("Clients\n-------------------------");
			
			for(Client client : clientGroup)
			{
				Player player = (Player) client;
				System.out.println(player.getName() + " | " + client.getIPAddress());
			}
			
			System.out.println();
		}
	}

	public void onPacketReceived(Client client, int packetType) 
	{
		Debug.log("Packet received.");
		PacketManager.handlePacket(this, (Player) client, packetType);
	}
	
	public void onStop()
	{
		Debug.log("Server stopped.");
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
