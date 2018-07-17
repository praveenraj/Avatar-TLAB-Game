package com.avatar.util;

import static com.avatar.constant.GameConstants.*;
import static com.avatar.constant.GameMessageConstants.*;
import static com.avatar.constant.GameNationConstants.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.avatar.character.Aang;
import com.avatar.character.GameCharacter;
import com.avatar.character.Katara;
import com.avatar.character.Sokka;
import com.avatar.constant.GameNationConstants;
import com.avatar.exception.GameOverException;
import com.avatar.model.GameLoad;
import com.avatar.model.GameStats;
import com.avatar.nation.Nations;

public class GameUtil {

	private static final Logger LOG = LoggerFactory.getLogger(GameUtil.class);

	/*
	 * Auto wire by annotation
	 */
	@Autowired
	private static Katara katara;
	@Autowired
	private static Sokka sokka;
	@Autowired
	private static Aang aang;

	private static GameStats gameStats;
	private static GameLoad gameLoad;
	private static int COUNTER = 0;
	private static Properties prop = new Properties();

	private GameUtil() {
		throw new IllegalArgumentException("IllegalArgumentException in GameUtil");
	}

	public static String getFormattedMsg(ResourceBundle bundle, String msg, Object... args) {
		return MessageFormat.format(bundle.getString(msg), args);
	}

	public static String getFormattedMsg(String color, ResourceBundle bundle, String msg, Object... args) {
		return color + MessageFormat.format(bundle.getString(msg), args) + ANSI_RESET;
	}

	public static void printLevelAndPoints(GameLoad gameLoad) {
		LOG.debug("{}{}{}", ANSI_BLUE, gameLoad, ANSI_RESET);
	}

	public static void setGamePoints(GameLoad gameLoad, int points, int xp) throws GameOverException {
		gameLoad.getGameStats().setPoints(gameLoad.getGameStats().getPoints() + points);
		gameLoad.getGameStats().setXp(gameLoad.getGameStats().getXp() + xp);
		msgLogInterval(getFormattedMsg(ANSI_BLUE, MSG_BUNDLE, AWARDED_POINTS, points));
		msgLogInterval(getFormattedMsg(ANSI_BLUE, MSG_BUNDLE, AWARDED_XP, xp, gameLoad.getGameStats().getXp()));
	}

	public static void addCharacterToGameLoad(GameLoad gameLoad, GameCharacter gameCharacter) {
		boolean contains = false;
		for (GameCharacter gc : gameLoad.getCharacters()) {
			if (gc.getName().equalsIgnoreCase(gameCharacter.getName()))
				contains = true;
		}
		if (!contains) {
			gameLoad.getCharacters().add(gameCharacter);
		}
	}

	public static void levelCompleted(GameLoad gameLoad, Integer points, BufferedReader buf)
			throws IOException, GameOverException {
		gameLoad.getGameStats().setPoints(gameLoad.getGameStats().getPoints() + points);
		msgLogInterval(getFormattedMsg(ANSI_BLUE, MSG_BUNDLE, AWARDED_POINTS_LEVEL, points));
		msgLogInterval(getFormattedMsg(ANSI_GREEN, MSG_BUNDLE, LEVEL_FINISHED, gameLoad.getGameStats().getLevel(),
				gameLoad.getGameStats().getPoints(), gameLoad.getGameStats().getXp()));
		gameLoad.getGameStats().setLevel(gameLoad.getGameStats().getLevel() + LEVEL_UP);

		// wants to save game
		boolean inputLoop = false;
		while (!inputLoop) {
			LOG.info(getFormattedMsg(MSG_BUNDLE, SAVE_GAME));
			String toSave = buf.readLine().trim().toLowerCase();
			if (toSave.equalsIgnoreCase(YES) || toSave.equalsIgnoreCase(Y)) {
				saveGame(gameLoad); // save the game
				inputLoop = true;
			} else if (toSave.equalsIgnoreCase(NO) || toSave.equalsIgnoreCase(N)) {
				inputLoop = true;
			}
		}
	}

	public static void incrementCounter() throws GameOverException {
		if (COUNTER == TWO)
			throw new GameOverException();
		COUNTER++;
	}

	public static void clearCounter() {
		COUNTER = ZERO;
	}

	public static GameCharacter getGameCharacter(String name) throws GameOverException {
		switch (name) {
		case KATARA:
			return katara;
		case AVATAR_AANG:
			return aang;
		case SOKKA:
			return sokka;
		default:
			throw new GameOverException(getFormattedMsg(MSG_BUNDLE, CHARACTER_EXCEPTION));
		}
	}

	public static List<GameCharacter> getListOfCharacter(String names) throws GameOverException {
		List<GameCharacter> characters = new ArrayList<>();
		String[] charcatersArray = names.split(COMMA);
		for (String characterName : charcatersArray) {
			switch (characterName) {
			case KATARA:
				characters.add(katara);
				break;
			case AVATAR_AANG:
				characters.add(aang);
				break;
			case SOKKA:
				characters.add(sokka);
				break;
			default:
				throw new GameOverException(getFormattedMsg(MSG_BUNDLE, CHARACTER_EXCEPTION));
			}
		}
		return characters;
	}

	public static GameLoad newGame(BufferedReader buf, ApplicationContext context)
			throws IOException, GameOverException {
		storyLogInterval(getFormattedMsg(MSG_BUNDLE, WELCOME_TO_THE_GAME));
		storyLogInterval(getFormattedMsg(MSG_BUNDLE, ORIGIN_STORY));
		storyLogInterval(getFormattedMsg(ANSI_GREEN, MSG_BUNDLE, GAME_BEGINS));

		gameStats = new GameStats();
		gameLoad = new GameLoad(gameStats, new ArrayList<GameCharacter>(), GameNationConstants.WATER_NATION);
		// explore the nation from new point
		return Nations.getNation(GameNationConstants.WATER_NATION).explore(1, gameLoad, buf, context);
	}

	public static void saveGame(GameLoad gameLoad) throws IOException {
		OutputStream output = new FileOutputStream(RESOURCE_FOLDER + CONFIG_PROPS);
		// set the properties value
		prop.setProperty(LEVEL, String.valueOf(gameLoad.getGameStats().getLevel()));
		prop.setProperty(POINTS, String.valueOf(gameLoad.getGameStats().getPoints()));
		prop.setProperty(XP, String.valueOf(gameLoad.getGameStats().getXp()));
		prop.setProperty(CURRENT_CHARACTER, gameLoad.getCurrentCharacter().getName());
		StringBuilder sb = new StringBuilder();
		int charcSize = gameLoad.getCharacters().size();
		int i = ZERO;
		while (charcSize-- > ONE) {
			sb.append(gameLoad.getCharacters().get(i++).getName());
			sb.append(COMMA);
		}
		sb.append(gameLoad.getCharacters().get(i).getName());
		prop.setProperty(CHARACTERS, sb.toString());
		// save properties to project's resource folder
		prop.store(output, RESOURCE_FOLDER);
		output.close();
	}

	public static GameLoad resumeGame(BufferedReader buf, Properties prop, Integer level, ApplicationContext context)
			throws GameOverException, IOException {
		if (level == null) {
			level = Integer.parseInt(prop.getProperty(LEVEL));
		}
		gameStats = new GameStats(Integer.parseInt(prop.getProperty(POINTS)), level,
				Integer.parseInt(prop.getProperty(XP)));
		List<GameCharacter> characters = getListOfCharacter(prop.getProperty(CHARACTERS));
		GameCharacter currentGameCharacter = getGameCharacter(prop.getProperty(CURRENT_CHARACTER));
		String currentNation = null;
		if (level >= ONE && level <= THREE) {
			currentNation = WATER_NATION;
		} else if (level == FOUR) {
			currentNation = EARTH_NATION;
		} else {
			currentNation = FIRE_NATION;
		}
		gameLoad = new GameLoad(gameStats, characters, currentGameCharacter, currentNation);
		// explore the nation from resume point
		return Nations.getNation(currentNation).explore(level, gameLoad, buf, context);
	}

	public static GameLoad previousLevel(GameLoad gameLoad, BufferedReader buf, ApplicationContext context)
			throws IOException, GameOverException {
		prop = loadConfigFile();
		return resumeGame(buf, prop, gameLoad.getGameStats().getLevel() - ONE, context);
	}

	public static Properties loadConfigFile() throws IOException {
		InputStream input = new FileInputStream(RESOURCE_FOLDER + CONFIG_PROPS);
		// load a properties file
		prop.load(input);
		input.close();
		return prop;
	}

	public static void exitGame() {
		LOG.info(getFormattedMsg(ANSI_GREEN, MSG_BUNDLE, SEE_YOU_LATER));
		System.exit(0);
	}

	public static GameLoad exploreNation(GameLoad gameLoad, BufferedReader buf, ApplicationContext context)
			throws IOException, GameOverException {
		return Nations.getNation(gameLoad.getCurrentNation()).explore(gameLoad.getGameStats().getLevel(), gameLoad, buf,
				context);
	}

	public static void storyLogInterval(String msg) {
		try {
			LOG.info(msg);
			Thread.sleep(STORY_TIMER);
		} catch (InterruptedException e) {
			// Restore interrupted state...
			Thread.currentThread().interrupt();
		}
	}

	public static void msgLogInterval(String msg) {
		try {
			LOG.info(msg);
			Thread.sleep(MSG_TIMER);
		} catch (InterruptedException e) {
			// Restore interrupted state...
			Thread.currentThread().interrupt();
		}
	}

}
