package com.avatar.character;

import java.io.Serializable;

public class GameCharacter implements Serializable {

	private static final long serialVersionUID = 434273251189646899L;

	private String name;

	private String powerSrc;

	public GameCharacter() {
		super();
	}

	public GameCharacter(String name, String powerSrc) {
		super();
		this.name = name;
		this.powerSrc = powerSrc;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPowerSrc() {
		return powerSrc;
	}

	public void setPowerSrc(String powerSrc) {
		this.powerSrc = powerSrc;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
