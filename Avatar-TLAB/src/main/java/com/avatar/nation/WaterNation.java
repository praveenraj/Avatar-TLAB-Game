package com.avatar.nation;

import static com.avatar.constant.GameConstants.*;
import static com.avatar.constant.GameMessageConstants.*;

import java.io.BufferedReader;
import java.io.IOException;

import com.avatar.exception.GameOverException;
import com.avatar.model.GameLoad;
import com.avatar.util.GameUtil;
import com.avatar.util.WaterNationUtil;

public class WaterNation implements Nation {

	@Override
	public GameLoad explore(int level, GameLoad gameLoad, BufferedReader buf) throws IOException, GameOverException {

		switch (level) {
		case 1:
			return WaterNationUtil.level1(gameLoad, buf);
		case 2:
			return WaterNationUtil.level2(gameLoad, buf);
		case 3:
			return WaterNationUtil.level3(gameLoad, buf);
		default:
			throw new GameOverException(GameUtil.getFormattedMsg(MSG_BUNDLE, LEVEL_EXCEPTION, level, WATER));
		}
	}

}
