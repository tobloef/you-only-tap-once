package com.tobloef.yoto.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tobloef.yoto.YouOnlyTapOnce;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "You Only Tap Once";
		if (true) {
			config.width = 757;
			config.height = 1345;
		} else {
			config.width = 1345;
			config.height = 757;
		}
		config.fullscreen = false;
		new LwjglApplication(new YouOnlyTapOnce(), config);
	}
}
