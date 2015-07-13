package com.tobloef.yoto.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tobloef.yoto.YouOnlyTapOnce;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "You Only Tap Once";
		config.width = 1920;
		config.height = 1080;
		new LwjglApplication(new YouOnlyTapOnce(), config);
	}
}
