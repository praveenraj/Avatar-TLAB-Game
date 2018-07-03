package com.avatar.nation;

import static com.avatar.constant.GameConstants.*;
import static com.avatar.constant.GameMessageConstants.*;

import java.io.BufferedReader;

import com.avatar.exception.GameOverException;
import com.avatar.model.GameLoad;
import com.avatar.util.GameUtil;

public class FireNation implements Nation {

	@Override
	public GameLoad explore(int level, GameLoad gameLoad, BufferedReader buf) throws GameOverException {

		GameUtil.storyLogInterval(GameUtil.getFormattedMsg(ANSI_RED, MSG_BUNDLE, FIRE_NATION_WELCOME));
		GameUtil.storyLogInterval(GameUtil.getFormattedMsg(MSG_BUNDLE, FIRE_NATION_INIT));
		GameUtil.storyLogInterval(GameUtil.getFormattedMsg(ANSI_GREEN, MSG_BUNDLE, GAME_UPDATE));

		System.exit(0);
		return gameLoad;
	}

}
