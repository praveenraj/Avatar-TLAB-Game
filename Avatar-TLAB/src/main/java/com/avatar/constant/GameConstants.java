package com.avatar.constant;

import java.util.ResourceBundle;

public final class GameConstants {

	public static final String MSG_BUNDLE_PROPERTIES = "message";

	public static final ResourceBundle MSG_BUNDLE = ResourceBundle.getBundle(MSG_BUNDLE_PROPERTIES);

	public static final String RESOURCE_FOLDER = "src/main/resources/";

	public static final String CONFIG_PROPS = "config.properties";

	public static final String BEANS_XML = "classpath:app-context.xml";

	public static final String AVATAR_AANG = "Avatar Aang";

	public static final String TLA = "The Last Airbender";

	public static final String KATARA = "Katara";

	public static final String SOKKA = "Sokka";

	public static final String ALPHA = "Appa";

	public static final String ZUKO = "Zuko";

	public static final String ANSI_RESET = "\033[0m";

	public static final String ANSI_RED = "\033[0;91m";

	public static final String ANSI_GREEN = "\033[1;32m";

	public static final String ANSI_BLUE = "\033[0;94m";

	public static final String BREAK = "break";

	public static final String SWING = "swing";

	public static final String WATER = "water";

	public static final String BOOMARANG = "boomarang";

	public static final String EARTH = "earth";

	public static final String FIRE = "fire";

	public static final Integer NEGATIVE_ONE = -1;

	public static final Integer ZERO = 0;

	public static final Integer ONE = 1;

	public static final Integer TWO = 2;

	public static final Integer THREE = 3;

	public static final Integer FOUR = 4;

	public static final Integer FIVE = 5;

	public static final Integer TEN = 10;

	public static final Integer TWELVE = 12;

	public static final Integer FOURTEEN = 14;

	public static final Integer FIFTEEN = 15;

	public static final Integer TWENTY = 20;

	public static final Integer TWENTY_FIVE = 25;

	public static final Integer FIFTY = 50;

	public static final Integer SEVENTY_FIVE = 75;

	public static final Integer HUNDRED = 100;

	public static final Integer ONE_FIFTY = 150;

	public static final Integer LEVEL_UP = 1;

	public static final String DIRECTION_LEFT = "A";

	public static final String DIRECTION_RIGHT = "D";

	public static final String DIRECTION_FORWARD = "W";

	public static final String DIRECTION_BACKWARD = "S";

	public static final String ACTION_A = "a";

	public static final String ACTION_B = "b";

	public static final String ACTION_C = "c";

	public static final String ACTION_D = "d";

	public static final String ACTION_E = "e";

	public static final String EMPTY = "";

	public static final String COMMA = ",";

	public static final String SPACE = " ";

	public static final String LEVEL = "level";

	public static final String POINTS = "points";

	public static final String CURRENT_CHARACTER = "currentCharacter";

	public static final String CHARACTERS = "characters";

	public static final String XP = "xp";

	public static final String YES = "yes";

	public static final String NO = "no";

	public static final String Y = "y";

	public static final String N = "n";

	public static final Integer STORY_TIMER = 0;

	public static final Integer MSG_TIMER = 0;

	public static final String AANG_BEAN = "aang";

	public static final String KATARA_BEAN = "katara";

	public static final String SOKKA_BEAN = "sokka";

	public static final String WATER_LEVEL_BEAN = "waterLevel";

	public static final String EARTH_LEVEL_BEAN = "earthLevel";

	public static final String BATTLE_SHIP_BEAN = "battleShip";

	public static final String BATTLE_ZUKO_BEAN = "battleZuko";

	private GameConstants() {
		throw new IllegalArgumentException("IllegalArgumentException in GameConstants");
	}
}
