package game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Player{
	
	//Fields
	
	private double x;
	private double y;
	
	private int r;
	
	private double dx;
	private double dy;
	
	private double speed;
	
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;
	
	private boolean firing;
	private long firingTimer;
	private long firingDelay;
	
	private int health;
	
	private Color normal;
	private Color hurt;
	
	private boolean recovering;
	private long recoveringTimer;
	
	private int score;
	
	private int rx;
	private int ry;
	
	//Constructor
	
	public Player(){		
		x = GamePanel.width/2;
		y = GamePanel.height/2;
		
		r = 7;
		
		dx = 0;
		dy = 0;
		
		speed = 10;
		
		firing = false;
		firingTimer = System.nanoTime();
		firingDelay = 200;
		
		health = 100;
		
		normal = Color.WHITE;
		hurt = Color.RED;
		
		recovering = false;
		recoveringTimer = 0;
		
		score = 0;
	}
	
	//Functions
	
	public double getX(){return x;}
	public double getY(){return y;}
	public int getR(){return r;}
	public double getDx(){return dx;}
	public double getDy(){return dy;}
	public double getSpeed(){return speed;}
	
	public void setLeft(boolean b){left = b;}
	public void setRight(boolean b){right = b;}
	public void setUp(boolean b){up = b;}
	public void setDown(boolean b){down = b;}
	
	public void setFiring(boolean b, int rx, int ry){
		this.rx = rx;
		this.ry = ry;
		firing = b;
	}
	
	public void hurt(int amount){
		health -= amount;
		recovering = true;
	}
	
	public void update(){
		if(left){dx = -speed;}
		if(right){dx = speed;}
		if(up){dy = -speed;}
		if(down){dy = speed;}
		
		x += dx;
		y += dy;
		
		if(x < r){x = r;}
		if(y < r){y = r;}
		if(x > GamePanel.width - r){x = GamePanel.width - r;}
		if(y > GamePanel.height - r){y = GamePanel.height - r;}
		
		dx = 0;
		dy = 0;

		if(firing){
			long elapsed = (System.nanoTime() - firingTimer)/1000000;
			if(elapsed > firingDelay){
				firingTimer = System.nanoTime();
				double distX = rx - x;
				double distY = ry - y;
				double angle;
				if(rx < x){
					angle = Math.toDegrees(Math.atan(distY/distX)) - 180;
				} 
				else{
					angle = Math.toDegrees(Math.atan(distY/distX));
				}
				GamePanel.bullets.add(new Bullet(angle, x, y));
			}
		}
			
		if(recovering){
			long elapsed = (System.nanoTime() - recoveringTimer)/1000000;
			
			if(elapsed > 1500){
				recovering = false;
				recoveringTimer = 0;
			}
		} 
	}
	
	public void draw(Graphics2D g){
		if(recovering){
			g.setColor(hurt.darker());
			g.fillOval((int)(x-r), (int)(y-r), (int)(2*r), (int)(2*r));
			
			g.setStroke(new BasicStroke(3));
			g.setColor(hurt);
			g.drawOval((int)(x-r), (int)(y-r), (int)(2*r), (int)(2*r));
			g.setStroke(new BasicStroke(1));
		}
		else{
			g.setColor(normal.darker());
			g.fillOval((int)(x-r), (int)(y-r), (int)(2*r), (int)(2*r));
			
			g.setStroke(new BasicStroke(3));
			g.setColor(normal);
			g.drawOval((int)(x-r), (int)(y-r), (int)(2*r), (int)(2*r));
			g.setStroke(new BasicStroke(1));
		}
	}
}
