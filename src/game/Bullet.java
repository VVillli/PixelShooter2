package game;

import java.awt.Color;
import java.awt.Graphics2D;

public class Bullet {
	//Fields
	
	private double x;
	private double y;
	
	private int r;
	
	private double dx;
	private double dy;
	
	private double speed;
	
	private double rad;
	private Color color;
	
	//Constructor
	
	public Bullet(double angle, double x, double y){
		rad = Math.toRadians(angle);
		
		this.x = x;
		this.y = y;
		
		r = 3;
		
		speed = 15;
		
		dx = Math.cos(rad)*speed;
		dy = Math.sin(rad)*speed;
		
		color = Color.WHITE;
	}
	
	//Functions
	
	public double getX(){return x;}
	public double getY(){return y;}
	public int getR(){return r;}
	public double getDx(){return dx;}
	public double getDy(){return dy;}
	public double getSpeed(){return speed;}
	
	public boolean update(){
		x += dx;
		y += dy;
		
		if(x < -r || x > GamePanel.width + r || y < -r || y > GamePanel.height + r){
			return true;
		}
		
		return false;
	}
	
	public void draw(Graphics2D g){
		g.setColor(color);
		g.fillOval((int)(x-r), (int)(y-r), (int)(2*r), (int)(2*r));
	}
}
