package io.shparki.dg;

import io.shparki.dg.gen.Miner;
import io.shparki.dg.graphics.Window;
import io.shparki.dg.io.Input;
import io.shparki.dg.util.Map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class Game {
	
	public static final int CELL_HEIGHT = 32;
	public static final int CELL_WIDTH = 64;
	public static final int PADDING = 4;
	public static final int FULL_WIDTH = (CELL_WIDTH + 2 * PADDING);
	public static final int FULL_HEIGHT = (CELL_HEIGHT + 2 * PADDING);
	
	private Map map;
	private Miner miner;
	
	public Game(){
		map = new Map(Window.getWidth() / (CELL_WIDTH + 2 * PADDING), Window.getHeight() / (CELL_HEIGHT + 2 * PADDING));
		miner = new Miner(map);
	}
	
	public void update(){
		if(Input.isKeyPressed(KeyEvent.VK_SHIFT)) {
			map.clear();
			miner = new Miner(map);
			
			for (int i = 0; i < 125; i++) miner.step();
		}
		if(Input.isKeyPressed(KeyEvent.VK_ENTER)) {
			miner.step();
		}
		miner.update();
		
		if (Input.isKeyPressed(KeyEvent.VK_C)){
			map.clear();
			miner = new Miner(map);
		}
	}
	
	public void render(Graphics2D g2d){
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, Window.getWidth(), Window.getHeight());
		
		g2d.setColor(Color.BLUE);
		
		for (int y = 0; y < map.getHeight(); y++){
			for (int x = 0; x < map.getWidth(); x++){
				if (map.get(x, y) != Miner.ROOM_EMPTY){
					g2d.fillRect(x * FULL_WIDTH + PADDING, y * FULL_HEIGHT + PADDING, CELL_WIDTH, CELL_HEIGHT);
				
					int roomType = map.get(x, y) / 10;
					int direction = map.get(x, y) % 10;
					
					switch (roomType){
					case Miner.ROOM_END:
						switch(direction){
						case Miner.NORTH:
							g2d.fillRect(x * FULL_WIDTH + PADDING, y * FULL_HEIGHT + 36, CELL_WIDTH, 8);
							break;
						case Miner.EAST:
							g2d.fillRect(x * FULL_WIDTH - 4, y * FULL_HEIGHT + PADDING, 8, CELL_HEIGHT);
							break;
						case Miner.SOUTH:
							g2d.fillRect(x * FULL_WIDTH + PADDING, y * FULL_HEIGHT  - 4, CELL_WIDTH, 8);
							break;
						case Miner.WEST:
							g2d.fillRect(x * FULL_WIDTH + 68, y * FULL_HEIGHT + PADDING, 8, CELL_HEIGHT);
							break;
						}
						break;
					case Miner.ROOM_HALL:
						switch(direction){
						case Miner.NORTH:
							break;
						case Miner.EAST:
							break;
						case Miner.SOUTH:
							break;
						case Miner.WEST:
							break;
						}
						break;
					case Miner.ROOM_CORNER:
						switch(direction){
						case Miner.NORTH:
							break;
						case Miner.EAST:
							break;
						case Miner.SOUTH:
							break;
						case Miner.WEST:
							break;
						}
						break;
					case Miner.ROOM_T:
						switch(direction){
						case Miner.NORTH:
							break;
						case Miner.EAST:
							break;
						case Miner.SOUTH:
							break;
						case Miner.WEST:
							break;
						}
						break;
					case Miner.ROOM_CROSS:
						switch(direction){
						case Miner.NORTH:
							break;
						case Miner.EAST:
							break;
						case Miner.SOUTH:
							break;
						case Miner.WEST:
							break;
						}
						break;
					}
				}
			}
		}
		
		g2d.setColor(Color.MAGENTA);
		g2d.drawRect(miner.getX() * FULL_WIDTH, miner.getY() * FULL_HEIGHT, FULL_WIDTH, FULL_HEIGHT);
	}
	
	
}
