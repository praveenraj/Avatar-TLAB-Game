package com.avatar.util;

import static com.avatar.constant.GameCombatConstants.*;
import static com.avatar.constant.GameConstants.*;
import static com.avatar.constant.GameMessageConstants.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BattleZuko {

	private static final Logger LOG = LoggerFactory.getLogger(BattleZuko.class);
	private static boolean playing = false;
	private static int enemyHealth = 100;
	private static int playerHealth = 100;
	private static String winner;

	public static String initBattle(BufferedReader buf) throws IOException {
		playing = true;
		while (playing) {
			LOG.info(GameUtil.getFormattedMsg(ANSI_BLUE, MSG_BUNDLE, BATTLE_ZUKO_ENEMY_HEALTH, enemyHealth));
			LOG.info(GameUtil.getFormattedMsg(ANSI_BLUE, MSG_BUNDLE, BATTLE_ZUKO_PLAYER_HEALTH, playerHealth));
			LOG.info(GameUtil.getFormattedMsg(ANSI_RED, MSG_BUNDLE, BATTLE_ZUKO_INPUT));

			switch (buf.readLine().trim().toLowerCase()) {
			case ACTION_A:
				Action.actionForCode(ACTION_A).perform();
				break;
			case ACTION_B:
				Action.actionForCode(ACTION_B).perform();
				break;
			case ACTION_C:
				Action.actionForCode(ACTION_C).perform();
				break;
			case ACTION_D:
				Action.actionForCode(ACTION_D).perform();
				break;
			case ACTION_E:
				Action.actionForCode(ACTION_E).perform();
				break;
			default:
				break;
			}
		}
		return winner;
	}

	private static void checkHealth() {
		if (enemyHealth <= 0) {
			winner = TLA;
			playing = false;
			resetHealth();
		} else if (playerHealth <= 0) {
			winner = ZUKO;
			playing = false;
			resetHealth();
		}
	}

	private static void resetHealth() {
		enemyHealth = 100;
		playerHealth = 100;
	}

	private enum Action {
		WATER_BULLER(ACTION_A, ONE, TEN, WATER_BULLETS, FIRE_MISSILES, TAKE_ADV, LIT_INTO), AIR_PUNCH(ACTION_B, ONE,
				TWELVE, AIR_KICK, FIRE_LASHES, PUNCHED,
				WHOMPED), TSUNAMI(ACTION_C, ONE, FIFTEEN, TSUNAMII, FIRE_STREAMS, MUFFLED, BRK_THROUGH), AIR_BLADES(
						ACTION_D, ZERO, TWELVE, AIR_BLADE, FIRE_BLADES, CHOPPES_DOWN,
						CHARGED), MAELSTROM(ACTION_E, ZERO, FOURTEEN, MAELSTROMS, BLUE_FIRE, RAIDED, EXCORIATED);

		private final String code;
		private final int enemyBlockAction;
		private final int healthLife;
		private final String[] messages;
		private Random random = new Random();

		Action(String code, int enemyBlockAction, int healthLife, String... messages) {
			this.code = code;
			this.enemyBlockAction = enemyBlockAction;
			this.healthLife = healthLife;
			this.messages = messages;
		}

		private static Action actionForCode(String code) {
			for (Action action : values()) {
				if (action.code.equals(code))
					return action;
			}
			throw new IllegalArgumentException();
		}

		private void perform() {
			if (random.nextInt() * 2 != enemyBlockAction) {
				enemyHealth -= healthLife;
				LOG.info("{} {} {}", messages[0], messages[2], messages[1]);
				checkHealth();
			} else {
				playerHealth -= healthLife;
				LOG.info("{} {} {}", messages[1], messages[3], messages[0]);
				checkHealth();
			}
		}
	}

}
