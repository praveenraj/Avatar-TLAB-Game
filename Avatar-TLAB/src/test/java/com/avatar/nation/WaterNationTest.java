package com.avatar.nation;

import java.io.BufferedReader;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.avatar.impl.Start;

@RunWith(PowerMockRunner.class)
public class WaterNationTest {

	private BufferedReader buf;

	@Before
	public void setUp() throws Exception {
		buf = PowerMockito.mock(BufferedReader.class);
	}

	@Test
	public void test() throws Exception {
		PowerMockito.when(buf.readLine()).thenReturn("1", "break", "y", "no");
		Start.initPlay(buf);
	}

}
