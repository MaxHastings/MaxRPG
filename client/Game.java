import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.mdwapps.utils.network.Client;
import com.mdwapps.utils.network.DataListener;
import com.mdwapps.utils.network.misc.Network;


public class Game implements DataListener 
{

	private CustomPanel window;
	
	private JFrame windowFrame;
	
	private Client client;
	
	private PacketHandler handler;
	
	public static void main(String [] args)
	{
    	Game g = new Game();
	}
	
	public Game()
	{
		
		System.out.println("Welcome to MaxRPG! Created by Maxwell & Simba.");
		handler = new PacketHandler(this);
		
		// Set up window
		
		windowFrame = new JFrame("RPG");
		windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		windowFrame.setResizable(false);
		//window.setLocationRelativeTo(null);
		window = new CustomPanel(this);
		windowFrame.add(window);
		windowFrame.pack();
		//window.setVisible(true);
		
		// Set up client
		try 
		{
			client = Network.connectTo("45.50.34.64", 45454);
			client.setDataListener(this);
		} 
		catch (UnknownHostException e) 
		{
			System.out.println("The server is down...");
			System.exit(1);
		}
		catch (IOException e)
		{
			System.out.println("Unable to connect to the server: " + e.getMessage());
			System.exit(1);
		}
		
		promptForName();
	}
	
	public void initWindow()
	{
		System.out.println("Showing window");
		windowFrame.setVisible(true);
	}
	
	public void promptForName()
	{
		String name = JOptionPane.showInputDialog("Choose a name");
		if(name == null)
			System.exit(1);
		
		System.out.println(name);
		
		client.getOutput().createPacket(Packet.SET_NAME);
		client.getOutput().putString(name);
		client.getOutput().endPacket();
		client.flushStream();
	}
	
	public CustomPanel getWindow()
	{
		return window;
	}
	
	public Client getClient()
	{
		return client;
	}

	public void onDataReceived(Client client) 
	{
		int packetType = client.getInput().readUnsignedByte();
		handler.handlePacket(client, packetType);
	}

	public void onDisconnect(Client client) 
	{
		System.out.println("Disconnected from server.");
	}
}
