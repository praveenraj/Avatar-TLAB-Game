package com.avatar.exception;

public class GameOverException extends Exception {

	private static final long serialVersionUID = 6781378729773498144L;

	public GameOverException() {
		super("Game Over..! Max tries exceeded for player input");
	}

	public GameOverException(String message) {
		super(message);
	}
}
