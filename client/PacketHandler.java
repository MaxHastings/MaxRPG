import javax.swing.JOptionPane;

import com.mdwapps.utils.network.Client;


public class PacketHandler
{

	private Game game;
	
	public PacketHandler(Game game)
	{
		this.game = game;
	}
	
	public void handlePacket(Client client, int packetType)
	{
		System.out.println("packet type: " + packetType);
		
		switch(packetType)
		{
			case Packet.CHAT:
				int chatType = client.getInput().readUnsignedByte();
				String chatMsg = client.getInput().readString();
				
				game.getWindow().getChatHud().addMessage(chatMsg);
				
				break;
				
				
				
			case Packet.SET_NAME:
				int success = client.getInput().readUnsignedByte();
				
				if(success == 1)
					game.initWindow();
				else
				{
					JOptionPane.showMessageDialog(null, "That is an invalid username");
					game.promptForName();
				}
				
				break;
				
				
				
			case Packet.PLAYER_COUNT:
				int count = client.getInput().readWord();
				game.getWindow().setNumberOfPlayers(count);
				
				break;
		}
	}
	
}
