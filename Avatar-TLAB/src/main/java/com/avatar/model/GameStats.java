package com.avatar.model;

public class GameStats {

	private int points;

	private int level;

	private int xp;

	public GameStats() {
		super();
		this.level = 1;
		this.points = 10;
		this.xp = 5;
	}

	public GameStats(int points, int level, int xp) {
		super();
		this.points = points;
		this.level = level;
		this.xp = xp;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getXp() {
		return xp;
	}

	public void setXp(int xp) {
		this.xp = xp;
	}

	@Override
	public String toString() {
		return "at level " + level + " with " + points + " points & " + xp + " xp";
	}

}
