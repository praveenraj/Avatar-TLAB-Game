package com.avatar.nation;

import static com.avatar.constant.GameConstants.EMPTY;
import com.avatar.constant.GameNationConstants;

public enum Nations {

	WATER_NATION(GameNationConstants.WATER_NATION, new WaterNation()), 
	EARTH_NATION(GameNationConstants.EARTH_NATION, new EarthNation()), 
	FIRE_NATION(GameNationConstants.FIRE_NATION, new FireNation());

	private final String name;

	private final Nation nation;

	Nations(String name, Nation nation) {
		this.name = name;
		this.nation = nation;
	}

	public static Nation getNation(String nation) {
		Nations ai = instanceOf(nation);
		return ai.nation;
	}

	public static Nations instanceOf(String name) {
		name = (name == null || name.isEmpty()) ? EMPTY : name;
		try {
			return valueOf(name.toUpperCase());
		} catch (IllegalArgumentException iae) {
			throw new IllegalArgumentException(GameNationConstants.INVALID_NATION);
		}
	}

	public String getName() {
		return name;
	}
}
