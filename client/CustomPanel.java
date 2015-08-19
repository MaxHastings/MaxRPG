import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class CustomPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private JTextField text;
	
	private ChatHud chatHud;
	
	private Game game;
	
	private int numberOfPlayers;
	
	private ArrayList<Player> players;

	public CustomPanel(Game game)
	{
		this.game = game;
		
		players = new ArrayList<Player>();
		
		setBorder(BorderFactory.createLineBorder(Color.black));
		setLayout(null);
		chatHud = new ChatHud(this);
		chatHud.setBounds(5, 395, 400, 200);
		add(chatHud);
		
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		System.out.println("ran");
		
		for(Player p : players)
			p.draw(g);
		
		g.setColor(Color.BLACK);
		g.drawString(numberOfPlayers + " online", 100, 250);
	}
	
	
	
	public Dimension getPreferredSize() 
	{
        return new Dimension(800, 600);
    }
	
	public ChatHud getChatHud()
	{
		return chatHud;
	}
	
	public Game getGame()
	{
		return game;
	}
	
	public void setNumberOfPlayers(int n)
	{
		numberOfPlayers = n;
		repaint();
	}
	
	public void createPlayer(String name, int id, int x, int y, boolean local)
	{
		Player p = new Player(name, id);
		p.setPosition(x, y);
		p.setLocalPlayer(local);
		players.add(p);
		
		repaint();
	}
	
	public void removePlayer(int id)
	{
		for(int i = 0; i < players.size(); i++)
		{
			if(players.get(i).getID() == id)
			{
				players.remove(i);
				break;
			}
		}
		
		repaint();
	}
}
