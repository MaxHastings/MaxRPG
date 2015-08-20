import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;


public class Player 
{
	private String name;
	
	private Point position;
	
	private int id;
	
	boolean localPlayer;
	
	public Player(String name, int id)
	{
		this.name = name;
		this.id = id;
		
		position = new Point();
		localPlayer = false;
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
	
	public void setLocalPlayer(boolean b)
	{
		localPlayer = b;
	}
	
	public void draw(Graphics graphics)
	{
		graphics.setColor(localPlayer ? Color.GREEN : Color.RED);
		graphics.fillRect(position.x, position.y, 20, 20);
		
		graphics.setColor(Color.BLACK);
		graphics.drawString(name, position.x, position.y - 5);
	}
	
	public int getID()
	{
		return id;
	}
}
