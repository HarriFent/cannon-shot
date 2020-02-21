package com.hfentonfearn.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.hfentonfearn.CannonShot;

import static com.hfentonfearn.helpers.Constants.WORLD_PIXEL_HEIGHT;
import static com.hfentonfearn.helpers.Constants.WORLD_PIXEL_WIDTH;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Cannon Shot";
		config.resizable = false;
		config.width = WORLD_PIXEL_WIDTH;
		config.height = WORLD_PIXEL_HEIGHT;

		new LwjglApplication(new CannonShot(), config);
	}
}
