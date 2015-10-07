package com.tobloef.yoto.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tobloef.yoto.YouOnlyTapOnce;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "You Only Tap Once";
		if (true) {
			config.width = 700;
			config.height = 1240;
		} else {
			config.width = 1240;
			config.height = 700;
		}
		config.fullscreen = false;
		new LwjglApplication(new YouOnlyTapOnce(), config);
	}
}
