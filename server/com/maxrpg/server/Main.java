package com.maxrpg.server;

public class Main 
{
	public static void main(String[] args)
	{
		Server server = new Server(45454, false);
		server.start();
	}
}
