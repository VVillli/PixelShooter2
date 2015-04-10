package game;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class GameWindow extends JFrame{
	public GameWindow(){
		super();
		
		//Initial Values
		
		this.setTitle("Pixel Shooter 2");
		this.setBackground(Color.BLACK);
		this.setContentPane(new GamePanel(this));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
		this.getContentPane().setCursor(blankCursor);
		
		this.pack();
		
		//Setting Size and Centering

		this.setSize(new Dimension(806,828));
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
	    
	    this.setLocation(x, y);
	    
		//Finishing Up
		
	    this.setResizable(false);
		this.setVisible(true);
	}
}
