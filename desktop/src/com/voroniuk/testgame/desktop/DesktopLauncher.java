package com.voroniuk.testgame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.voroniuk.testgame.FGame;
import com.voroniuk.testgame.GameScreen;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = FGame.WIDTH;
		config.height = FGame.HEIGHT;
		new LwjglApplication(new FGame(), config);
	}
}
