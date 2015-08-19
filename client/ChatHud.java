import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.mdwapps.utils.network.Client;


public class ChatHud extends JPanel implements ActionListener 
{

	private static final long serialVersionUID = 1L;
	
	private int x, y, width, height;

	private List<String> messages;
	
	private List<Color> messageColors;
	
	private JTextField inputText;
	
	private int msgLimit;
	
	private CustomPanel parent;
	
	public ChatHud(CustomPanel parent)
	{
		setBorder(BorderFactory.createLineBorder(Color.black));
		setLayout(null);
		
		this.parent = parent;
		messages = new ArrayList<String>();
		messageColors = new ArrayList<Color>();
		
		int parentWidth = (int) parent.getPreferredSize().getWidth();
		int parentHeight = (int) parent.getPreferredSize().getHeight();
		
		width = (int) (parentWidth * 0.4);
		height = (int) (parentHeight * 0.5);
		
		msgLimit = 11;
		
		//inputText = new JTextField();
		//inputText.setBounds(2, height - 20, width, 20);
		//add(inputText);
	}
	
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		
		System.out.println("ran2");
		
		g.setColor(Color.BLACK);
		
		int startY = inputText.getY() - 5;
		
		for(int i = 0; i < messages.size(); i++)
		{
			Color color = messageColors.get(messages.size() - 1 - i);
			
			//if(color.getRed() == 0 && color.getGreen() == 100 && color.getBlue() == 0)
			//	g.setFont(new Font("TimesRoman", Font.BOLD, 12)); 
				
			g.setColor(color);
			g.drawString(messages.get(messages.size() - 1 - i), 2, startY - (i * 15));
		}
	}
	
	public void setBounds(int x, int y, int width, int height)
	{
		super.setBounds(x, y, width, height);
		
		System.out.println("setbounds");
		
		inputText = new JTextField();
		inputText.setBounds(2, (height - 25), width - 5, 20);
		inputText.addActionListener(this);
		add(inputText);
	}
	
	public Dimension getPreferredSize() 
	{
		return new Dimension(width, height);
	}

	public void actionPerformed(ActionEvent e) 
	{
		if(inputText.getText().trim().length() > 0)
		{
			Client client = parent.getGame().getClient();
			client.getOutput().createPacket(Packet.CHAT);
			client.getOutput().putString(inputText.getText());
			client.getOutput().endPacket();
			client.flushStream();
		}
		
		inputText.setText("");
	}
	
	public void addMessage(String msg, int chatType)
	{
		messages.add(msg);
		messageColors.add(getTextColor(chatType));
		
		if(messages.size() > msgLimit)
		{
			messages.remove(0);
			messageColors.remove(0);
		}
		
		repaint();
	}
	
	public Color getTextColor(int msgType)
	{
		switch(msgType)
		{
			case 0:
				return new Color(0, 150, 0);
				//return Color.GREEN;
				
			case 1:
				return Color.BLACK;
				
			case 2:
				return Color.BLUE;
				
			default:
				return Color.BLACK;
		}
	}
	
}
