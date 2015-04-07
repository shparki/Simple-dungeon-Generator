package io.shparki.dg.gen;

import io.shparki.dg.io.Input;
import io.shparki.dg.util.Map;
import io.shparki.dg.util.Point;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class Miner {
	
	public static final int NORTH = 0;
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;
	
	public static final int ROOM_EMPTY = 0;
	public static final int ROOM_END = 1;
	public static final int ROOM_HALL = 2;
	public static final int ROOM_CORNER = 3;
	public static final int ROOM_T = 4;
	public static final int ROOM_CROSS = 5;
	
	private Random rand = new Random();
	private Map map;
	private int x = 0, y = 0;
	private boolean alive = false;
	
	private ArrayList<Point> placedRooms = new ArrayList<Point>();
	public void removeRoom(int x, int y){
		int index = 0;
		boolean success = false;
		
		for (int i = 0; i < placedRooms.size(); i++){
			if (placedRooms.get(i).getX() == x && placedRooms.get(i).getY() == y){
				index = i;
				success = true;
			}
		}
		
		if (success) { placedRooms.remove(index); }
	}
	
	public Miner(Map map){
		this.map = map;
		
		x = map.getWidth() / 2;
		y = map.getHeight() - 1;
		
		map.set(x, y, ROOM_END * 10);
		placedRooms.add(new Point(x, y));
		alive = true;
	}
	
	public void update(){
		if (Input.isKeyPressed(KeyEvent.VK_UP)){
			if (y - 1 >= 0) y--;
		}
		if (Input.isKeyPressed(KeyEvent.VK_DOWN)){
			if (y + 1 < map.getHeight()) y++;
		}
		
		if (Input.isKeyPressed(KeyEvent.VK_RIGHT)){
			if (x + 1 < map.getWidth()) x++;
		}
		if (Input.isKeyPressed(KeyEvent.VK_LEFT)){
			if (x - 1 >= 0) x--;
		}
		
		if (Input.isKeyPressed(KeyEvent.VK_EQUALS)){
			map.set(x, y, ROOM_END * 10);
			placedRooms.add(new Point(x, y));
		}
		if (Input.isKeyPressed(KeyEvent.VK_MINUS)){
			map.set(x, y, ROOM_EMPTY);
			removeRoom(x, y);
		}
		
		if (Input.isKeyPressed(KeyEvent.VK_PERIOD)){
			int value = map.get(x, y) / 10;
			int rotation = map.get(x, y) % 10;
			rotation ++;
			if (rotation >= 4) rotation -= 4;
			if (rotation < 0) rotation += 4;
			map.set(x, y, value * 10 + rotation);
		}
		if (Input.isKeyPressed(KeyEvent.VK_COMMA)){
			int value = map.get(x, y) / 10;
			int rotation = map.get(x, y) % 10;
			rotation --;
			if (rotation >= 4) rotation -= 4;
			if (rotation < 0) rotation += 4;
			map.set(x, y, value * 10 + rotation);
		}
	}
	
	public int getX() { return x; }
	public int getY() { return y; }
	
	public void step(){
		int breakCounter = 0;
	
		
		//Find a random placed room
		int index = rand.nextInt(placedRooms.size());
		
		//Make Sure this room has free spaces around it; if not; find a new room;
		while (getNumberOfFreeNeighbors(placedRooms.get(index).getX(), placedRooms.get(index).getY()) < 2){
			index = rand.nextInt(placedRooms.size());
			breakCounter ++;
			if (breakCounter >= 150) return;
		}
		
		
		x = placedRooms.get(index).getX();
		y = placedRooms.get(index).getY();
		
		int fromX = x, fromY = y;
		
		//Find a random direction
		int direction = rand.nextInt(4);
		
		//Make sure this direction is available; AKA not taken by a room
		breakCounter = 0;
		while (!isDirectionFree(x, y, direction)){
			direction = rand.nextInt(4);
			breakCounter ++;
			if (breakCounter >= 25) return;
		}
		
		switch (direction){
		case NORTH:
			y--;
			break;
		case EAST:
			x ++;
			break;
		case SOUTH:
			y ++;
			break;
		case WEST:
			x--;
			break;
		}
		
		map.set(x, y, ROOM_END * 10 + direction);
		placedRooms.add(new Point(x, y));
	}
	public boolean isAlive() { return alive; }
	
	public void rotate(){
		
	}
	public void move(){
		
	}

	
	public int getNumberOfFreeNeighbors(int x, int y){
		int counter = 0;
		//Check north
		if (y - 1 >= 0){
			if (map.get(x, y - 1) == ROOM_EMPTY) { counter++; }
		}
		//Check east
		if (x + 1 < map.getWidth()){
			if (map.get(x + 1, y) == ROOM_EMPTY) { counter ++; } 
		}
		//check south
		if (y + 1 < map.getHeight()){
			if (map.get(x, y + 1) == ROOM_EMPTY) { counter ++; }
		}
		//check west
		if (x - 1 >= 0){
			if (map.get(x - 1, y) == ROOM_EMPTY) { counter ++; }
		}
		return counter;
	}
	public boolean isDirectionFree(int x, int y, int direction){
		switch(direction){
		case NORTH:
			if (y - 1 >= 0){
				if (map.get(x, y - 1) == ROOM_EMPTY) return true;
			}
			break;
		case EAST:
			if (x + 1 < map.getWidth()){
				if (map.get(x + 1, y) == ROOM_EMPTY) return true;
			}
			break;
		case SOUTH:
			if (y + 1 < map.getHeight()){
				if (map.get(x, y + 1) == ROOM_EMPTY) return true;
			}
			break;
		case WEST:
			if (x - 1 >= 0){
				if (map.get(x - 1, y) == ROOM_EMPTY) return true;
			}
			break;
		}
		
		return false;
	}
}
