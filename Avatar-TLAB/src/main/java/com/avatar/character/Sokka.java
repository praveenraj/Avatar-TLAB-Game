package com.avatar.character;

import java.io.Serializable;

public class Sokka extends GameCharacter implements Serializable {

	private static final long serialVersionUID = -6852695158099170753L;

	public Sokka() {
		super("Sokka", "boomarang");
	}

	public Sokka(String name) {
		super(name, "boomarang");
	}

	public Sokka(String name, String powerSrc) {
		super(name, powerSrc);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return this.getName() + ", The " + this.getPowerSrc() + " guy.";
	}
}
