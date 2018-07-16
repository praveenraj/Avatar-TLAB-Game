package com.avatar.nation;

import static com.avatar.constant.GameConstants.*;
import static com.avatar.constant.GameMessageConstants.*;

import java.io.BufferedReader;
import java.io.IOException;

import org.springframework.context.ApplicationContext;

import com.avatar.exception.GameOverException;
import com.avatar.levels.WaterNationLevel;
import com.avatar.model.GameLoad;
import com.avatar.util.GameUtil;

public class WaterNation implements Nation {

	@Override
	public GameLoad explore(int level, GameLoad gameLoad, BufferedReader buf, ApplicationContext context)
			throws IOException, GameOverException {

		WaterNationLevel waterLevel = (WaterNationLevel) context.getBean(WATER_LEVEL_BEAN);

		switch (level) {
		case 1:
			return waterLevel.level1(gameLoad, buf);
		case 2:
			return waterLevel.level2(gameLoad, buf);
		case 3:
			return waterLevel.level3(gameLoad, buf, context);
		default:
			throw new GameOverException(GameUtil.getFormattedMsg(MSG_BUNDLE, LEVEL_EXCEPTION, level, WATER));
		}
	}

}
