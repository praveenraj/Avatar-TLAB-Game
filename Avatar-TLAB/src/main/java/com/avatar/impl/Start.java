package com.avatar.impl;

import static com.avatar.constant.GameConstants.*;
import static com.avatar.constant.GameMessageConstants.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avatar.exception.GameOverException;
import com.avatar.model.GameLoad;
import com.avatar.nation.Nations;
import com.avatar.util.GameUtil;

public class Start {

	private static final Logger LOG = LoggerFactory.getLogger(Start.class);

	private static boolean inputLoop = false;
	private static GameLoad gameLoad;

	public static void main(String[] args) {

		BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));

		try {
			gameLoad = initPlay(buf);
			continueGame(buf, gameLoad);
			buf.close();
		} catch (Exception e) {
			StringBuilder sb = new StringBuilder();
			sb.append(GameUtil.getFormattedMsg(MSG_BUNDLE, GAME_CRASHED)).append(EMPTY).append(e.getMessage());
			LOG.error(sb.toString());
			System.exit(0);
		}
	}

	public static GameLoad initPlay(BufferedReader buf) throws IOException, GameOverException {
		int level;
		Properties prop;
		prop = GameUtil.loadConfigFile();
		if (prop.containsKey(LEVEL))
			level = Integer.parseInt(prop.getProperty(LEVEL));
		else
			level = ONE;

		if (level == ONE) {
			return GameUtil.newGame(buf); // start new game
		} else {
			while (!inputLoop) {
				LOG.info(GameUtil.getFormattedMsg(MSG_BUNDLE, START_OVER));
				String toStartOver = buf.readLine().trim().toLowerCase();
				if (toStartOver.equalsIgnoreCase(YES) || toStartOver.equalsIgnoreCase(Y)) {
					return GameUtil.newGame(buf); // start new game
				} else if (toStartOver.equalsIgnoreCase(NO) || toStartOver.equalsIgnoreCase(N)) {
					return GameUtil.resumeGame(buf, prop, null); // resume game
				}
			}
		}
		return gameLoad;
	}

	public static GameLoad continueGame(BufferedReader buf, GameLoad gameLoad) throws IOException, GameOverException {
		while (true) {
			LOG.info(GameUtil.getFormattedMsg(MSG_BUNDLE, CONTINUE_GAME));
			String toContinue = buf.readLine().trim().toLowerCase();
			if (toContinue.equalsIgnoreCase(YES) || toContinue.equalsIgnoreCase(Y)) {
				return Nations.getNation(gameLoad.getCurrentNation()).explore(gameLoad.getGameStats().getLevel(),
						gameLoad, buf);
			} else if (toContinue.equalsIgnoreCase(NO) || toContinue.equalsIgnoreCase(N)) {
				GameUtil.exitGame();
			}
		}
	}
}
