package com.avatar.nation;

import static com.avatar.constant.GameConstants.*;
import static com.avatar.constant.GameMessageConstants.*;

import java.io.BufferedReader;
import java.io.IOException;

import com.avatar.exception.GameOverException;
import com.avatar.model.GameLoad;
import com.avatar.util.EarthNationUtil;
import com.avatar.util.GameUtil;

public class EarthNation implements Nation {

	@Override
	public GameLoad explore(int level, GameLoad gameLoad, BufferedReader buf) throws IOException, GameOverException {

		GameUtil.storyLogInterval(GameUtil.getFormattedMsg(ANSI_RED, MSG_BUNDLE, EARTH_NATION_WELCOME));
		GameUtil.storyLogInterval(GameUtil.getFormattedMsg(MSG_BUNDLE, EARTH_NATION_INIT));

		if (gameLoad.getGameStats().getXp() < HUNDRED) {
			return EarthNationUtil.needXpToPlay(gameLoad, buf);
		} else {
			return EarthNationUtil.battleWithZuko(gameLoad, buf);
		}
	}
}
