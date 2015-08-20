package com.maxrpg.server.player;

import java.awt.Point;
import java.net.Socket;
import java.util.Random;

import com.maxrpg.server.packets.Packet;
import com.mdwapps.utils.network.Client;

public class Player extends Client
{
	private ChatManager chatManager;
	
	private String name;
	
	private Point position;
	
	public Player(Socket socket) 
	{
		super(socket);
		
		chatManager = new ChatManager(this);
		position = new Point();
		
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
	
	public void init()
	{	
		Random r = new Random();
		int x = r.nextInt(600) + 100;
		int y = r.nextInt(400) + 100;
		
		setPosition(x, y);
		
		getOutput().createPacket(Packet.INIT);
		getOutput().putString(name);
		getOutput().putByte(getID());
		getOutput().putWord(x);
		getOutput().putWord(y);
		getOutput().endPacket();
		flushStream();
	}
	
	public void setPosition(int x, int y)
	{
		position.x = x;
		position.y = y;
	}
	
	public Point getPosition()
	{
		return position;
	}
	
	public void doLogic()
	{
		
	}
}
