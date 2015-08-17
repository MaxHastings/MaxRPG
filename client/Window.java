import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;


public class Window extends JFrame
{	

	private static final long serialVersionUID = -6680402251688228752L;
	
	private int width, height;
	
	private Game game;
	
	public Window(Game game, String title, int width, int height)
	{
		super(title);
		
		setSize(width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//addOnKeyListener(listnerClass);
		
		this.game = game;
		this.width = width;
		this.height = height;
	}
	
	public void paint(Graphics graphics)
	{
		graphics.setColor(Color.BLACK);
		graphics.fillRect(0, 0, width, height);
		
		graphics.setColor(Color.WHITE);
		graphics.drawString("Hello from simba and max", width / 2, height / 2);
	}
}
