package com.avatar.nation;

import java.io.BufferedReader;
import java.io.IOException;

import com.avatar.exception.GameOverException;
import com.avatar.model.GameLoad;

public interface Nation {

	public GameLoad explore(int level, GameLoad gameload, BufferedReader buf) throws IOException, GameOverException;
}
