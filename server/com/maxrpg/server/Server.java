package com.maxrpg.server;

import java.net.Socket;
import java.util.Arrays;

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
			
			// update player count
			c.getOutput().createPacket(Packet.PLAYER_COUNT);
			c.getOutput().putWord(clientGroup.size() - 1);
			c.getOutput().endPacket();
			c.flushStream();
			
			// remove player from client games
			c.getOutput().createPacket(Packet.REMOVE_PLAYER);
			c.getOutput().putWord(client.getID());
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
		else if(command.equalsIgnoreCase("broadcast"))
		{
			if(argc == 0)
				return;
			
			StringBuilder builder = new StringBuilder();
			
			for(String s : args) 
			    builder.append(s).append(" ");
			
			String broadcastMsg = builder.toString().trim();
			sendBroadcast(broadcastMsg);
		}
	}

	public void onPacketReceived(Client client, int packetType) 
	{
		//Debug.log("Packet received.");
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
	
	public Player findPlayerById(int id)
	{
		for(Client c : clientGroup)
		{
			if(c.getID() == id)
				return (Player) c;
		}
		
		return null;
	}
	
	public Player findPlayerByName(String name)
	{
		for(Client c : clientGroup)
		{
			Player p = (Player) c;
			
			if(p.getName().equalsIgnoreCase(name))
				return p;
		}
		
		return null;
	}
	
	public void sendNewPlayer(Player player)
	{
		for(Client c : clientGroup)
		{
			Player p = (Player) c;
			if(p == player)
				continue;
			
			// update players of new player
			p.getOutput().createPacket(Packet.NEW_PLAYER);
			p.getOutput().putString(player.getName());
			p.getOutput().putByte(player.getID());
			p.getOutput().putWord(player.getPosition().x);
			p.getOutput().putWord(player.getPosition().y);
			p.getOutput().endPacket();
			p.flushStream();
			
			// update new player of other players
			player.getOutput().createPacket(Packet.NEW_PLAYER);
			player.getOutput().putString(p.getName());
			player.getOutput().putByte(p.getID());
			player.getOutput().putWord(p.getPosition().x);
			player.getOutput().putWord(p.getPosition().y);
			player.getOutput().endPacket();
			player.flushStream();
		}
	}
}
