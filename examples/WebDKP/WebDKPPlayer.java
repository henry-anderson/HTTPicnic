package org.henrya.httpicnic.examples.webdkp;

public class WebDKPPlayer {
	private String name;
	private int id;
	private double points;
	private double lifetimePoints;
	
	public WebDKPPlayer(String name, int id, double points, double lifetimePoints) {
		this.name = name;
		this.points = points;
		this.id = id;
		this.lifetimePoints = lifetimePoints;
	}
	
	public String getName() {
		return this.name;
	}
	
	public double getPoints() {
		return this.points;
	}
	
	public int getID() {
		return this.id;
	}
	
	public double getLifetimePoints() {
		return this.lifetimePoints;
	}
}
