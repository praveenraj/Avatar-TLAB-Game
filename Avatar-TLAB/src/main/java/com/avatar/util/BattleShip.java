package com.avatar.util;

import static com.avatar.constant.GameConstants.*;
import static com.avatar.constant.GameCombatConstants.*;
import static com.avatar.constant.GameMessageConstants.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BattleShip {

	private static final Logger LOG = LoggerFactory.getLogger(BattleShip.class);
	private static final boolean inputLoop = false;

	public static int initBattle(BufferedReader buf) throws NumberFormatException, IOException {
		int[][] board = new int[5][5];
		int[][] ships = new int[3][2];
		int[] shoot = new int[2];
		int attempts = 0, shotHit = 0;

		// init battle area and ship positions
		initBoard(board);
		initShips(ships);

		// game mode
		do {
			showBoard(board);
			shoot(shoot, buf);
			attempts++;

			if (hit(shoot, ships)) {
				shotHit++;
				if (shotHit != THREE)
					hint(shoot, ships, attempts);
				LOG.info(GameUtil.getFormattedMsg(ANSI_BLUE, MSG_BUNDLE, BATTLE_SHIP_HIT, shoot[0] + 1, shoot[1] + 1));
			} else
				hint(shoot, ships, attempts);

			changeboard(shoot, ships, board); // change board after shot

		} while (shotHit != THREE);

		showBoard(board); // after 3 ships down
		LOG.info(GameUtil.getFormattedMsg(ANSI_GREEN, MSG_BUNDLE, BATTLE_SHIP_END, attempts));
		return attempts - ONE;
	}

	public static void initBoard(int[][] board) {
		for (int row = 0; row < 5; row++)
			for (int column = 0; column < 5; column++)
				board[row][column] = -1;
	}

	public static void showBoard(int[][] board) {
		System.out.println("\t1 \t2 \t3 \t4 \t5");
		System.out.println();
		for (int row = 0; row < 5; row++) {
			System.out.print((row + 1) + "");
			for (int column = 0; column < 5; column++) {
				if (board[row][column] == NEGATIVE_ONE) {
					System.out.print(TAB + TILDE);
				} else if (board[row][column] == ZERO) {
					System.out.print(TAB + SHOT);
				} else if (board[row][column] == ONE) {
					System.out.print(TAB + PERFECT_HIT);
				}
			}
			System.out.println();
		}

	}

	public static void initShips(int[][] ships) {
		Random random = new Random();
		for (int ship = 0; ship < 3; ship++) {
			ships[ship][0] = random.nextInt(5);
			ships[ship][1] = random.nextInt(5);
			// let's check if that shot was already tried
			// if it was, just finish the do...while when a new pair was randomly selected
			for (int last = 0; last < ship; last++) {
				if ((ships[ship][0] == ships[last][0]) && (ships[ship][1] == ships[last][1]))
					do {
						ships[ship][0] = random.nextInt(5);
						ships[ship][1] = random.nextInt(5);
					} while ((ships[ship][0] == ships[last][0]) && (ships[ship][1] == ships[last][1]));
			}
		}
	}

	public static void shoot(int[] shoot, BufferedReader buf) throws NumberFormatException, IOException {
		while (!inputLoop) {
			System.out.print(GameUtil.getFormattedMsg(ANSI_RED, MSG_BUNDLE, BATTLE_SHIP_ROW));
			String rowInput = buf.readLine().trim();
			if (rowInput != null && !rowInput.isEmpty()) {
				shoot[0] = Integer.parseInt(rowInput);
				if (shoot[0] >= ONE && shoot[0] <= FIVE) {
					shoot[0]--;
					break;
				}
			}
			System.out.println(GameUtil.getFormattedMsg(MSG_BUNDLE, BATTLE_SHIP_INPUT_EXCEPTION));
		}

		while (!inputLoop) {
			System.out.print(GameUtil.getFormattedMsg(ANSI_RED, MSG_BUNDLE, BATTLE_SHIP_COLUMN));
			String colInput = buf.readLine().trim();
			if (colInput != null && !colInput.isEmpty()) {
				shoot[1] = Integer.parseInt(colInput);
				if (shoot[1] >= ONE && shoot[1] <= FIVE) {
					shoot[1]--;
					break;
				}
			}
			System.out.println(GameUtil.getFormattedMsg(MSG_BUNDLE, BATTLE_SHIP_INPUT_EXCEPTION));
		}

	}

	public static boolean hit(int[] shoot, int[][] ships) {
		for (int ship = 0; ship < ships.length; ship++) {
			if (shoot[0] == ships[ship][0] && shoot[1] == ships[ship][1]) {
				return true;
			}
		}
		return false;
	}

	public static void hint(int[] shoot, int[][] ships, int attempt) {
		int row = 0, column = 0;

		for (int line = 0; line < ships.length; line++) {
			if (ships[line][0] == shoot[0])
				row++;
			if (ships[line][1] == shoot[1])
				column++;
		}
		LOG.info(GameUtil.getFormattedMsg(ANSI_BLUE, MSG_BUNDLE, BATTLE_SHIP_HINT, attempt, shoot[0] + 1, row,
				shoot[1] + 1, column));
	}

	public static void changeboard(int[] shoot, int[][] ships, int[][] board) {
		if (hit(shoot, ships))
			board[shoot[0]][shoot[1]] = 1;
		else
			board[shoot[0]][shoot[1]] = 0;
	}

}
