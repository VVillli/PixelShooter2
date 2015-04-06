package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

public class GamePanel extends JPanel implements Runnable,KeyListener,MouseInputListener{
	//Fields
	
	public static int width = 800;
	public static int height = 800;
	
	private GameWindow w;
	
	private Thread thread;
	private boolean running;
	
	private BufferedImage image;
	private Graphics2D g;
	
	private int FPS = 60;
	private int averageFPS;
	
	//Constructor
	
	public GamePanel(GameWindow w){
		super();
		
		this.w = w;
		running = false;
		
		setPreferredSize(new Dimension(width, height));
		setFocusable(true);
		requestFocus();
		
	}
	
	//Functions
	
	public void addNotify(){
		super.addNotify();
		if(thread == null){
			thread = new Thread(this);
			thread.start();
		}
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	public void run(){
		running = true;

		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D)image.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		long startTime;
		long URDTimeMilli;
		long waitTime;
		long totalTime = 0;
		
		int frameCount = 0;
		int maxFrameCount = 60;
		
		long targetTime = 1000/FPS;
		
		//Game Loop
		
		while(running){
			startTime = System.nanoTime();
			
			gameUpdate();
			gameRender();
			gameDraw();
			
			URDTimeMilli = (System.nanoTime() - startTime)/1000000;
			
			waitTime = targetTime - URDTimeMilli;
			
			try{
				Thread.sleep(waitTime);
			}catch(Exception e){}
			
			totalTime += System.nanoTime() - startTime;
			frameCount++;
			
			if(frameCount == maxFrameCount){
				averageFPS = (int)(1000.0/((totalTime/frameCount)/1000000));
				frameCount = 0;
				totalTime = 0;
			}
		}
	}
	
	public void gameUpdate(){
		
	}
	
	public void gameRender(){		
		//Draw Background
		
		g.setColor(new Color(40,40,40,255));
		g.fillRect(0, 0, width, height);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		g.drawString("FPS: " + averageFPS, 10, 60);
	}
	
	public void gameDraw(){
		Graphics g2 = this.getGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
	}
	
	//Listeners
	
	public void keyTyped(KeyEvent key){}
	public void keyPressed(KeyEvent key){
		int keyCode = key.getKeyCode();
		if(keyCode == KeyEvent.VK_A){}
		if(keyCode == KeyEvent.VK_D){}
		if(keyCode == KeyEvent.VK_W){}
		if(keyCode == KeyEvent.VK_S){}
	}
	public void keyReleased(KeyEvent key){
		int keyCode = key.getKeyCode();
		if(keyCode == KeyEvent.VK_A){}
		if(keyCode == KeyEvent.VK_D){}
		if(keyCode == KeyEvent.VK_W){}
		if(keyCode == KeyEvent.VK_S){}
	}

	public void mouseClicked(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseDragged(MouseEvent e) {}
	public void mouseMoved(MouseEvent e) {}
}
