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
import java.util.ArrayList;

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
	
	private Player player;
	private CrossHair c;
	
	public static ArrayList<Bullet> bullets; 
	public static ArrayList<Enemy> enemies;
	
	private long waveStartTimer;
	private long waveStartTimerDiff;
	private int waveNumber;
	private boolean waveStart;
	private int waveDelay = 2000;
	
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
		
		player = new Player();
		c = new CrossHair((int)(player.getX()),(int)(player.getY()));
		bullets = new ArrayList<Bullet>();
		enemies = new ArrayList<Enemy>();
		
		waveStartTimer = 0;
		waveStartTimerDiff = 0;
		waveStart = true;
		waveNumber = 0;
		
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
		if(waveStartTimer == 0 && enemies.size() == 0){
			waveNumber++;
			waveStart = false;
			waveStartTimer = System.nanoTime();
		}
		else{
			waveStartTimerDiff = (System.nanoTime() - waveStartTimer)/1000000;
			if(waveStartTimerDiff > waveDelay){
				waveStart = true;
				waveStartTimer = 0;
				waveStartTimerDiff = 0;
			}
		}
		
		if(waveStart && enemies.size() == 0){
			createNewEnemies();
		}
		
		player.update();
		
		for(int i = 0; i < bullets.size(); i++){
			boolean remove = bullets.get(i).update();
			if(remove){
				bullets.remove(i);
				i--;
			}
		}
		
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).update();
		}
	}
	
	public void gameRender(){		
		//Draw Background
		
		g.setColor(new Color(40,40,40,255));
		g.fillRect(0, 0, width, height);
		
		//Draw Objects
		
		for(int i = 0; i < bullets.size(); i++){
			bullets.get(i).draw(g);
		}
		
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).draw(g);;
		}
		
		player.draw(g);
		c.draw(g);
		
		//Render HUD
		
		if(waveStartTimer != 0){
			g.setFont(new Font("Century Gothic", Font.BOLD, 24));
			String s = "- W A V E  " + waveNumber + "  -";
			int length = (int)g.getFontMetrics().getStringBounds(s, g).getWidth();
			int alpha = (int)(255*Math.sin(3.14*waveStartTimerDiff/waveDelay));
			if(alpha > 255){alpha = 255;}
			g.setColor(new Color(255,255,255, alpha));
			g.drawString(s, width/2 - length/2, height/2);
		}
		
	}
	
	public void gameDraw(){
		Graphics g2 = this.getGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
	}
	
	public void createNewEnemies(){
		if(waveNumber < 3){
			for(int i = 0; i < 4*waveNumber; i++){
				enemies.add(new FollowEnemy((long)(Math.random()*2000*waveNumber), player));
			}
		}
	}
	
	//Listeners
	
	public void keyTyped(KeyEvent key){}
	public void keyPressed(KeyEvent key){
		int keyCode = key.getKeyCode();
		if(keyCode == KeyEvent.VK_A){player.setLeft(true);}
		if(keyCode == KeyEvent.VK_D){player.setRight(true);}
		if(keyCode == KeyEvent.VK_W){player.setUp(true);}
		if(keyCode == KeyEvent.VK_S){player.setDown(true);}
	}
	public void keyReleased(KeyEvent key){
		int keyCode = key.getKeyCode();
		if(keyCode == KeyEvent.VK_A){player.setLeft(false);}
		if(keyCode == KeyEvent.VK_D){player.setRight(false);}
		if(keyCode == KeyEvent.VK_W){player.setUp(false);}
		if(keyCode == KeyEvent.VK_S){player.setDown(false);}
	}

	public void mouseClicked(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {player.setFiring(true, e.getX(), e.getY());}
	public void mouseReleased(MouseEvent e) {player.setFiring(false, e.getX(), e.getY());}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseDragged(MouseEvent e) {
		player.setFiring(true, e.getX(), e.getY());
		c.update(e.getX(), e.getY());
	}
	public void mouseMoved(MouseEvent e) {c.update(e.getX(), e.getY());}
}
