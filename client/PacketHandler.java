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
				
				game.getWindow().getChatHud().addMessage(chatMsg, chatType);
				
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
				
			
				
			case Packet.INIT:
				String pName = client.getInput().readString();
				int pID = client.getInput().readUnsignedByte();
				int pX = client.getInput().readWord();
				int pY = client.getInput().readWord();
				
				game.getWindow().createPlayer(pName, pID, pX, pY, true);
				
				break;
				
				
			
			case Packet.NEW_PLAYER:
				String playerName = client.getInput().readString();
				int playerID = client.getInput().readUnsignedByte();
				int playerX = client.getInput().readWord();
				int playerY = client.getInput().readWord();
				
				game.getWindow().createPlayer(playerName, playerID, playerX, playerY, false);
				break;
				
				
			
			case Packet.REMOVE_PLAYER:
				int removeID = client.getInput().readWord();
				game.getWindow().removePlayer(removeID);
				break;
				
		}
	}
	
}
