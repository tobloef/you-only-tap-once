package com.tobloef.yoto.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tobloef.yoto.YouOnlyTapOnce;

import java.awt.*;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "You Only Tap Once";
		config.width = Toolkit.getDefaultToolkit().getScreenSize().width;
		config.height = Toolkit.getDefaultToolkit().getScreenSize().height;
<<<<<<< HEAD
		if (true) {
			config.width = 700;
			config.height = 1240;
		} else {
			config.width = 1240;
			config.height = 700;
		}
		config.width = 1920;
		config.height = 1080;
		config.vSyncEnabled = false;
=======
		config.width = 700;
		config.height = 1240;
>>>>>>> parent of 925ea41... Added game over screen.
		config.fullscreen = false;
		new LwjglApplication(new YouOnlyTapOnce(), config);
	}
}
