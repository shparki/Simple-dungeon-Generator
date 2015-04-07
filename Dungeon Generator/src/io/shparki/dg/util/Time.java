package io.shparki.dg.util;

public class Time {
	
	public static final long SECOND = 1_000_000_000;
	
	public static long getTime() { return System.nanoTime(); }
	
	
	private static double delta;
	public static double getDelta() { return delta; }
	public static void setDelta(double delta) { Time.delta = delta; }
	
	
	
}
