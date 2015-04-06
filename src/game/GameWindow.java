package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class GameWindow extends JFrame{
	public GameWindow(){
		super();
		
		//Initial Values
		
		this.setTitle("Pixel Shooter 2");
		this.setBackground(Color.BLACK);
		this.setContentPane(new GamePanel(this));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.pack();
		
		//Setting Size and Centering
		
		this.setMinimumSize(new Dimension(800,800));
		this.setSize(new Dimension(800,800));
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
	    
	    this.setLocation(x, y);
	    
		//Finishing Up
		
	    this.setResizable(false);
		this.setVisible(true);
	}
}
