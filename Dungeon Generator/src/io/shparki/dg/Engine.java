package io.shparki.dg;

import io.shparki.dg.graphics.Window;
import io.shparki.dg.io.Input;
import io.shparki.dg.util.Time;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

public class Engine extends Canvas implements Runnable{
	
	public static final int WIDTH = 1200;
	public static final int HEIGHT = WIDTH / 16 * 9;
	public static final String TITLE = "Dungeon Generator";
	
	public static final int TARGET_UPS = 30;
	public static final long PERIOD = Time.SECOND / TARGET_UPS;
	private int currentUPS = 0, currentFPS = 0;
	private int UPS = 0, FPS = 0;
	
	private boolean running = false;
	private Thread animator;
	
	private BufferStrategy bs;
	private Graphics2D g2d;
	
	private Game game;
	
	
	
	public Engine(){
		Window.createWindow(WIDTH, HEIGHT, TITLE, this);
		start();
	}
	
	public synchronized void start(){
		if (!running || animator == null){
			animator = new Thread(this, "Animator");
			animator.start();
		}
	}
	public synchronized void stop(){
		running = false;
	}
	
	public void run(){
		
		long beforeTime = 0;
		long currentTime = Time.getTime();
		long upsCounter = 0, secCounter = 0;
		
		init();
		
		running = true;
		while (running){
			beforeTime = currentTime;
			currentTime = Time.getTime();
			Time.setDelta(currentTime - beforeTime);
			
			upsCounter += Time.getDelta();
			if (upsCounter >= PERIOD){
				upsCounter -= PERIOD;
				update();
				currentUPS ++;
			}
			
			render();
			currentFPS ++;
			
			secCounter += Time.getDelta();
			if (secCounter >= Time.SECOND){
				secCounter -= Time.SECOND;
				UPS = currentUPS; FPS = currentFPS;
				currentUPS = 0; currentFPS = 0;
				
				System.out.println("UPS: " + UPS + " | FPS: " + FPS);
			}
		}
		System.exit(0);
	}
	
	public void init(){
		game = new Game();
	}
	public void update(){
		if (Input.isKeyPressed(KeyEvent.VK_ESCAPE)){
			stop();
			return;
		}
		
		game.update();
		Window.update();
	}
	public void render(){
		if (bs == null){
			bs = getBufferStrategy();
			if (bs == null){
				createBufferStrategy(3);
				return;
			}
		}
		
		g2d = (Graphics2D) bs.getDrawGraphics();
		
		game.render(g2d);
		
		bs.show();
		g2d.dispose();
	}
	
}
