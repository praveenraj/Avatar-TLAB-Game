package com.avatar.character;

import java.io.Serializable;

public class Aang extends GameCharacter implements Serializable {

	private static final long serialVersionUID = 6475707637649790184L;

	public Aang() {
		super("Avatar Aang", "air");
	}

	public Aang(String name) {
		super(name, "air");
	}

	public Aang(String name, String powerSrc) {
		super(name, powerSrc);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return this.getName() + ", The last airbender and The " + this.getPowerSrc() + " guy.";
	}
}
