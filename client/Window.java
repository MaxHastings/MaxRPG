import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Window extends JFrame
{	

	private static final long serialVersionUID = -6680402251688228752L;
	
	private int width, height;
	
	private Game game;
	
	private ChatHud chatHud;
	
	private CustomPanel panel;
	
	public Window(Game game, String title, int width, int height)
	{
		super(title);
		
		setSize(width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//setLayout(new FlowLayout());
		
		add(new CustomPanel(game));
		//pack();
		
		//addOnKeyListener(listnerClass);
		
		this.game = game;
		this.width = width;
		this.height = height;
		
		//chatHud = new ChatHud(this, 10, (int) (height - height * 0.20));
		//chatHud.setMessageLimit(10);
	}
	
	//public void paint(Graphics g)
	//{	
		//super.paint(g);
	//}
	
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
}
