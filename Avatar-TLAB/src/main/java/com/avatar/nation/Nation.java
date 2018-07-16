package com.avatar.nation;

import java.io.BufferedReader;
import java.io.IOException;

import org.springframework.context.ApplicationContext;

import com.avatar.exception.GameOverException;
import com.avatar.model.GameLoad;

public interface Nation {

	public GameLoad explore(int level, GameLoad gameload, BufferedReader buf, ApplicationContext context) throws IOException, GameOverException;
}
