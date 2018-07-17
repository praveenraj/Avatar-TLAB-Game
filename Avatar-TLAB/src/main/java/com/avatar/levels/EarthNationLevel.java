package com.avatar.levels;

import static com.avatar.constant.GameConstants.*;
import static com.avatar.constant.GameMessageConstants.*;
import static com.avatar.constant.GameNationConstants.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.avatar.battles.BattleZuko;
import com.avatar.exception.GameOverException;
import com.avatar.model.GameLoad;
import com.avatar.util.GameUtil;

public class EarthNationLevel {

	private static final Logger LOG = LoggerFactory.getLogger(EarthNationLevel.class);

	/*
	 * Auto wire by constructor
	 */
	private BattleZuko battleZuko;

	public EarthNationLevel(BattleZuko battleZuko) {
		super();
		this.battleZuko = battleZuko;
	}

	public GameLoad needXpToPlay(GameLoad gameLoad, BufferedReader buf, ApplicationContext context)
			throws IOException, GameOverException {
		LOG.info(GameUtil.getFormattedMsg(ANSI_RED, MSG_BUNDLE, NEED_XP, HUNDRED));
		while (true) {
			LOG.info(GameUtil.getFormattedMsg(MSG_BUNDLE, PREVIOUS_LEVEL));
			String toPrev = buf.readLine().trim().toLowerCase();
			if (toPrev.equalsIgnoreCase(YES) || toPrev.equalsIgnoreCase(Y)) {
				return GameUtil.previousLevel(gameLoad, buf, context);
			} else if (toPrev.equalsIgnoreCase(NO) || toPrev.equalsIgnoreCase(N)) {
				GameUtil.exitGame();
			}
		}
	}

	public GameLoad battleWithZuko(GameLoad gameLoad, BufferedReader buf, ApplicationContext context)
			throws IOException, GameOverException {

		// init battle with zuko
		String winner = battleZuko.initBattle(buf);
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
					Properties prop = GameUtil.loadConfigFile();
					return GameUtil.resumeGame(buf, prop, null, context); // resume game
				} else if (toResume.equalsIgnoreCase(NO) || toResume.equalsIgnoreCase(N)) {
					GameUtil.exitGame();
				}
			}
		}
	}
}
