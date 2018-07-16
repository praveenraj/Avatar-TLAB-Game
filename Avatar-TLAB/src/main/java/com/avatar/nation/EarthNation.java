package com.avatar.nation;

import static com.avatar.constant.GameConstants.*;
import static com.avatar.constant.GameMessageConstants.*;

import java.io.BufferedReader;
import java.io.IOException;

import org.springframework.context.ApplicationContext;

import com.avatar.exception.GameOverException;
import com.avatar.levels.EarthNationLevel;
import com.avatar.model.GameLoad;
import com.avatar.util.GameUtil;

public class EarthNation implements Nation {

	@Override
	public GameLoad explore(int level, GameLoad gameLoad, BufferedReader buf, ApplicationContext context)
			throws IOException, GameOverException {

		EarthNationLevel earthLevel = context.getBean(EarthNationLevel.class, EARTH_LEVEL_BEAN);

		GameUtil.storyLogInterval(GameUtil.getFormattedMsg(ANSI_RED, MSG_BUNDLE, EARTH_NATION_WELCOME));
		GameUtil.storyLogInterval(GameUtil.getFormattedMsg(MSG_BUNDLE, EARTH_NATION_INIT));

		if (gameLoad.getGameStats().getXp() < HUNDRED) {
			return earthLevel.needXpToPlay(gameLoad, buf, context);
		} else {
			return earthLevel.battleWithZuko(gameLoad, buf, context);
		}
	}
}
