package game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class FollowEnemy extends Enemy{
	private double x;
	private double y;
	
	private int r;
	
	private double dx;
	private double dy;
	
	private double speed;
	
	private double rad;
	
	private Color color;
	private Color spawningColor;
	
	private long spawnDelay;
	private long startTime;
	
	private boolean spawned;
	
	private boolean spawning;
	
	private Player p;
	
	public FollowEnemy(long spawnDelay, Player p){
		spawned = false;
		
		r = 5;
		
		x = GamePanel.width*Math.random() - r;
		y = GamePanel.height*Math.random() - r;
		
		speed = (int)(Math.random()*3) + 2;
		
		dx = 0;
		dy = 0;
		
		rad = 0;
		
		startTime = System.nanoTime();
		this.spawnDelay = spawnDelay;
		
		this.p = p;
		
		color = Color.blue;
		spawningColor = new Color(255,255,255,255);
	}
	
	public void update(){
		if(spawned){
			rad = Math.atan((p.getY()- y + Math.random()*5)/(p.getX() - x + Math.random()*5));
			
			if(p.getX() < x){
				rad += Math.PI;
			}
			
			dx = speed*Math.cos(rad);
			dy = speed*Math.sin(rad);
			
			x += dx;
			y += dy;
			
			dx = 0;
			dy = 0;
		}
		else{
			long elapsed = (System.nanoTime() - startTime)/1000000;
			if(elapsed >= spawnDelay){
				spawned = true;
			}
			/*else if((elapsed + 500) >= spawnDelay){
				spawning = true;
				int red = spawningColor.getRed() - (int)(255/30);
				int green = spawningColor.getGreen() - (int)(255/30);
				spawningColor = new Color(red, green, 255, 255);
			}*/
		}
	}
	
	public void draw(Graphics2D g){
		/*if(spawning){
			g.setColor(spawningColor.darker());
			g.fillOval((int)(x-r), (int)(y-r), (int)(2*r), (int)(2*r));
			
			g.setStroke(new BasicStroke(3));
			g.setColor(spawningColor);
			g.drawOval((int)(x-r), (int)(y-r), (int)(2*r), (int)(2*r));
			g.setStroke(new BasicStroke(1));
		}
		else if(spawned){
			g.setColor(color.darker());
			g.fillOval((int)(x-r), (int)(y-r), (int)(2*r), (int)(2*r));
			
			g.setStroke(new BasicStroke(3));
			g.setColor(color);
			g.drawOval((int)(x-r), (int)(y-r), (int)(2*r), (int)(2*r));
			g.setStroke(new BasicStroke(1));
		}*/
		
		if(spawned){
			g.setColor(color.darker());
			g.fillOval((int)(x-r), (int)(y-r), (int)(2*r), (int)(2*r));
			
			g.setStroke(new BasicStroke(3));
			g.setColor(color);
			g.drawOval((int)(x-r), (int)(y-r), (int)(2*r), (int)(2*r));
			g.setStroke(new BasicStroke(1));
		}
	}
}
