package com.avatar.nation;

import static com.avatar.constant.GameConstants.*;
import static com.avatar.constant.GameMessageConstants.*;
import static com.avatar.constant.GameNationConstants.FIRE_NATION;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avatar.exception.GameOverException;
import com.avatar.model.GameLoad;
import com.avatar.util.BattleZuko;
import com.avatar.util.GameUtil;

public class EarthNation implements Nation {

	private static final Logger LOG = LoggerFactory.getLogger(EarthNation.class);
	private static boolean inputLoop = false;
	private static InputStream input = null;

	@Override
	public GameLoad explore(int level, GameLoad gameLoad, BufferedReader buf) throws IOException, GameOverException {

		GameUtil.storyLogInterval(GameUtil.getFormattedMsg(ANSI_RED, MSG_BUNDLE, EARTH_NATION_WELCOME));
		GameUtil.storyLogInterval(GameUtil.getFormattedMsg(MSG_BUNDLE, EARTH_NATION_INIT));

		if (gameLoad.getGameStats().getXp() < HUNDRED) {
			LOG.info(GameUtil.getFormattedMsg(ANSI_RED, MSG_BUNDLE, NEED_XP, HUNDRED));
			while (!inputLoop) {
				LOG.info(GameUtil.getFormattedMsg(MSG_BUNDLE, PREVIOUS_LEVEL));
				String toPrev = buf.readLine().trim().toLowerCase();
				if (toPrev.equalsIgnoreCase(YES) || toPrev.equalsIgnoreCase(Y)) {
					return GameUtil.previousLevel(gameLoad, buf);
				} else if (toPrev.equalsIgnoreCase(NO) || toPrev.equalsIgnoreCase(N)) {
					GameUtil.exitGame();
				}
			}
		} else {
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
				while (!inputLoop) {
					LOG.info(GameUtil.getFormattedMsg(MSG_BUNDLE, RESUME_GAME));
					String toResume = buf.readLine().trim().toLowerCase();
					if (toResume.equalsIgnoreCase(YES) || toResume.equalsIgnoreCase(Y)) {
						input = new FileInputStream(RESOURCE_FOLDER + CONFIG_PROPS);
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
		return gameLoad;
	}

}
