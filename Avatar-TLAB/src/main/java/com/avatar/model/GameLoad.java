package com.avatar.model;

import java.util.ArrayList;
import java.util.List;

import com.avatar.character.GameCharacter;

public class GameLoad {

	private GameStats gameStats;

	private List<GameCharacter> characters;

	private GameCharacter currentCharacter;

	private String currentNation;

	public GameLoad() {
		super();
	}

	public GameLoad(GameStats gameStats, List<GameCharacter> characters) {
		super();
		this.gameStats = gameStats;
		this.characters = characters;
	}

	public GameLoad(GameStats gameStats, List<GameCharacter> characters, String currentNation) {
		super();
		this.gameStats = gameStats;
		this.characters = characters;
		this.currentNation = currentNation;
	}

	public GameLoad(GameStats gameStats, List<GameCharacter> characters, GameCharacter currentCharacter,
			String currentNation) {
		super();
		this.gameStats = gameStats;
		this.characters = characters;
		this.currentCharacter = currentCharacter;
		this.currentNation = currentNation;
	}

	public GameStats getGameStats() {
		return gameStats;
	}

	public void setGameStats(GameStats gameStats) {
		this.gameStats = gameStats;
	}

	public List<GameCharacter> getCharacters() {
		return characters != null ? characters : new ArrayList<GameCharacter>();
	}

	public void setCharacters(List<GameCharacter> characters) {
		this.characters = characters;
	}

	public GameCharacter getCurrentCharacter() {
		return currentCharacter;
	}

	public void setCurrentCharacter(GameCharacter currentCharacter) {
		this.currentCharacter = currentCharacter;
	}

	public String getCurrentNation() {
		return currentNation;
	}

	public void setCurrentNation(String currentNation) {
		this.currentNation = currentNation;
	}

	public GameCharacter getCharacterByName(String name) {
		for (GameCharacter gc : getCharacters()) {
			if (gc.getName().equalsIgnoreCase(name)) {
				return gc;
			}
		}
		return null;
	}

	public int getCharacterIndex(String name) {
		for (GameCharacter gc : getCharacters()) {
			if (gc.getName().equalsIgnoreCase(name)) {
				return characters.indexOf(gc);
			}
		}
		return 0;
	}

	public int getCharactersSize() {
		return characters.size();
	}

	@Override
	public String toString() {
		// return "\"" + gameStats + " and " + characters.size() + " characters\"";
		// return "" + gameStats;
		return "You are in " + currentNation.toLowerCase() + " " + gameStats;
	}

}
