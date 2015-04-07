package io.shparki.dg.io;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Input implements KeyListener{
	
	private static ArrayList<Integer> keysDown = new ArrayList<Integer>();
	private static ArrayList<Integer> keysPressed = new ArrayList<Integer>();
	private static ArrayList<Integer> keysReleased = new ArrayList<Integer>();
	
	public static boolean isKeyDown(int keyCode) { return keysDown.contains(Integer.valueOf(keyCode)); }
	public static boolean isKeyPressed(int keyCode) { return keysPressed.contains(Integer.valueOf(keyCode)); }
	public static boolean isKeyReleased(int keyCode) { return keysReleased.contains(Integer.valueOf(keyCode)); }
	
	public void keyPressed(KeyEvent e){
		if (!isKeyDown(e.getKeyCode())){
			if (!isKeyPressed(e.getKeyCode())){
				keysPressed.add(Integer.valueOf(e.getKeyCode()));
			}
			keysDown.add(Integer.valueOf(e.getKeyCode()));
		}
	}
	public void keyReleased(KeyEvent e){
		if (isKeyDown(e.getKeyCode())){
			if (!isKeyReleased(e.getKeyCode())){
				keysReleased.add(Integer.valueOf(e.getKeyCode()));
			}
			keysDown.remove(Integer.valueOf(e.getKeyCode()));
		}
	}
	public void keyTyped(KeyEvent e) {  }
	
	
	public static void update(){
		keysPressed.clear();
		keysReleased.clear();
	}
	
}
