package com.maxrpg.server.player;

import java.net.Socket;

import com.mdwapps.utils.network.Client;

public class Player extends Client
{
	private ChatManager chatManager;
	
	private String name;
	
	public Player(Socket socket) 
	{
		super(socket);
		
		chatManager = new ChatManager(this);
		
		name = "null";
	}
	
	public ChatManager getChatManager()
	{
		return chatManager;
	}
	
	public void setName(String n)
	{
		name = n;
	}
	
	public String getName()
	{
		return name;
	}
}
