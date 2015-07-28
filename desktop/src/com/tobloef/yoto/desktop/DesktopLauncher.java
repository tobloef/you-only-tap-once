package com.tobloef.yoto.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tobloef.yoto.YouOnlyTapOnce;

import java.awt.*;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "You Only Tap Once";
		config.width = 1280;
		config.height = 720;
		config.width = Toolkit.getDefaultToolkit().getScreenSize().width;
		config.height = Toolkit.getDefaultToolkit().getScreenSize().height;
		config.vSyncEnabled = true;
		config.fullscreen = true;
		new LwjglApplication(new YouOnlyTapOnce(), config);
	}
}
