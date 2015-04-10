package game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class CrossHair {
	//Fields
	
	private int x;
	private int y;
	
	private Color color;
	
	//Constructor
	
	public CrossHair(int x, int y){
		this.x = x;
		this.y = y;
		
		color = Color.RED;
	}
	
	//Functions
	
	public double getX(){return x;}
	public double getY(){return y;}
	
	public void update(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void draw(Graphics2D g){
		g.setColor(color);
		g.setStroke(new BasicStroke(2));
		g.drawOval(x-6, y-6, 12, 12);
		g.setStroke(new BasicStroke(1));
		g.fillRect(x, y-9, 1, 19);
		g.fillRect(x-9, y, 19, 1);
	}
}
