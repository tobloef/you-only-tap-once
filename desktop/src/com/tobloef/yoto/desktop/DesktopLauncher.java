package com.tobloef.yoto.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tobloef.yoto.YouOnlyTapOnce;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "You Only Tap Once";
		//config.width = Toolkit.getDefaultToolkit().getScreenSize().width;
		//config.height = Toolkit.getDefaultToolkit().getScreenSize().height;
		config.width = 1280;
		config.height = 720;
		config.vSyncEnabled = true;
		config.fullscreen = false;
		new LwjglApplication(new YouOnlyTapOnce(), config);
	}
}
