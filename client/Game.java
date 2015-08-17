
public class Game 
{

	public static void main(String [] args)
	{
		System.out.println("Welcome to MaxRPG! Created by Maxwell & Simba.");
		Window window = new Window(new Game(), "MaxRPG", 800, 600);
		window.setVisible(true);
	}
}
