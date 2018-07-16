package com.avatar.nation;

import static com.avatar.constant.GameConstants.*;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.avatar.impl.Start;
import com.avatar.model.GameLoad;

public class WaterNationTest {

	private BufferedReader buf;
	private ApplicationContext context;

	@Before
	public void setUp() throws Exception {
		buf = PowerMockito.mock(BufferedReader.class);
		context = new ClassPathXmlApplicationContext(BEANS_XML);
	}

	private void resetConfig() throws IOException {
		OutputStream output = new FileOutputStream(RESOURCE_FOLDER + CONFIG_PROPS);
		Properties prop = new Properties();
		prop.setProperty(LEVEL, "1");
		prop.setProperty(POINTS, "10");
		prop.setProperty(XP, "5");
		prop.setProperty(CURRENT_CHARACTER, "Avatar Aang");
		prop.store(output, RESOURCE_FOLDER);
		output.close();
	}

	@Test
	public void testLevel1WithKatara() throws Exception {
		resetConfig();
		PowerMockito.when(buf.readLine()).thenReturn("1", "break", "n");
		GameLoad gameLoad = Start.initPlay(buf, context);
		Assert.assertNotNull(gameLoad);
		Assert.assertEquals(2, gameLoad.getGameStats().getLevel());
		Assert.assertEquals(135, gameLoad.getGameStats().getPoints());
		Assert.assertEquals(40, gameLoad.getGameStats().getXp());
		Assert.assertEquals("Avatar Aang, The last airbender and The air guy.",
				gameLoad.getCurrentCharacter().toString());
	}

	@Test
	public void testLevel1WithSokka() throws Exception {
		PowerMockito.when(buf.readLine()).thenReturn("2", "swing", "y");
		GameLoad gameLoad = Start.initPlay(buf, context);
		Assert.assertNotNull(gameLoad);
		Assert.assertEquals(2, gameLoad.getGameStats().getLevel());
		Assert.assertEquals(135, gameLoad.getGameStats().getPoints());
		Assert.assertEquals(40, gameLoad.getGameStats().getXp());
		Assert.assertEquals("Avatar Aang, The last airbender and The air guy.",
				gameLoad.getCurrentCharacter().toString());
	}

}
