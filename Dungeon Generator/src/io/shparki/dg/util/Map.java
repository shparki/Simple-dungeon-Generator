package io.shparki.dg.util;

public class Map {
	
	private int[][] mapArray;
	private int width, height;
	
	public Map(int width, int height){
		mapArray = new int[height][width];
		this.width = width;
		this.height = height;
	}
	
	
	
	public int get(int x, int y) { return mapArray[y][x]; }
	public void set(int x, int y, int i) { mapArray[y][x] = i; }
	public void clear(){
		for (int y = 0; y < height; y++){
			for (int x = 0; x < width; x++){
				mapArray[y][x] = 0;
			}
		}
	}
	
	
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	// public void setWidth(int width) { ... }
	// public void setHeight(int height) { ... }
	// public void incWidth(int width) { ... }
	// public void incHeight(int height) { ... }
}
