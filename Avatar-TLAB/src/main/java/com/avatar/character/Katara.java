package com.avatar.character;

import java.io.Serializable;

public class Katara extends GameCharacter implements Serializable {

	private static final long serialVersionUID = -7979561454970114094L;

	public Katara(String name, String powerSrc) {
		super(name, powerSrc);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return this.getName() + ", The " + this.getPowerSrc() + " girl.";
	}
}
