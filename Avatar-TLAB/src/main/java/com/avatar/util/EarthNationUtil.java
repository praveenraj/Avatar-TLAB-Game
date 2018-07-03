package com.avatar.util;

import static com.avatar.constant.GameConstants.*;
import static com.avatar.constant.GameMessageConstants.*;
import static com.avatar.constant.GameNationConstants.FIRE_NATION;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avatar.exception.GameOverException;
import com.avatar.model.GameLoad;

public class EarthNationUtil {

	private static final Logger LOG = LoggerFactory.getLogger(EarthNationUtil.class);

	private EarthNationUtil() {
		throw new IllegalArgumentException("IllegalArgumentException in EarthNationUtil");
	}

	public static GameLoad needXpToPlay(GameLoad gameLoad, BufferedReader buf) throws IOException, GameOverException {
		LOG.info(GameUtil.getFormattedMsg(ANSI_RED, MSG_BUNDLE, NEED_XP, HUNDRED));
		while (true) {
			LOG.info(GameUtil.getFormattedMsg(MSG_BUNDLE, PREVIOUS_LEVEL));
			String toPrev = buf.readLine().trim().toLowerCase();
			if (toPrev.equalsIgnoreCase(YES) || toPrev.equalsIgnoreCase(Y)) {
				return GameUtil.previousLevel(gameLoad, buf);
			} else if (toPrev.equalsIgnoreCase(NO) || toPrev.equalsIgnoreCase(N)) {
				GameUtil.exitGame();
			}
		}
	}

	public static GameLoad battleWithZuko(GameLoad gameLoad, BufferedReader buf) throws IOException, GameOverException {
		// init battle with zuko
		String winner = BattleZuko.initBattle(buf);
		GameUtil.msgLogInterval(GameUtil.getFormattedMsg(ANSI_GREEN, MSG_BUNDLE, BATTLE_ZUKO_WINNER, winner));

		if (winner.equalsIgnoreCase(TLA)) {
			// set 150 points & 100xp
			GameUtil.setGamePoints(gameLoad, ONE_FIFTY, HUNDRED);

			// level completed
			GameUtil.levelCompleted(gameLoad, FIFTY, buf);

			// earth nation completed
			gameLoad.setCurrentNation(FIRE_NATION);
			return gameLoad;

		} else {
			while (true) {
				LOG.info(GameUtil.getFormattedMsg(MSG_BUNDLE, RESUME_GAME));
				String toResume = buf.readLine().trim().toLowerCase();
				if (toResume.equalsIgnoreCase(YES) || toResume.equalsIgnoreCase(Y)) {
					InputStream input = new FileInputStream(RESOURCE_FOLDER + CONFIG_PROPS);
					// load a properties file
					Properties prop = new Properties();
					prop.load(input);
					return GameUtil.resumeGame(buf, prop, null); // resume game
				} else if (toResume.equalsIgnoreCase(NO) || toResume.equalsIgnoreCase(N)) {
					GameUtil.exitGame();
				}
			}
		}
	}
}
