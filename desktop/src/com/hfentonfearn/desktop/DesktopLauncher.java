package com.hfentonfearn.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.hfentonfearn.CannonShot;

import static com.hfentonfearn.utils.Constants.WINDOW_HEIGHT;
import static com.hfentonfearn.utils.Constants.WINDOW_WIDTH;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Cannon Shot";
		config.resizable = false;
		config.width = WINDOW_WIDTH;
		config.height = WINDOW_HEIGHT;

		new LwjglApplication(new CannonShot(), config);
	}
}
