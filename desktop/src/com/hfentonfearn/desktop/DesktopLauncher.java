package com.hfentonfearn.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.hfentonfearn.CannonShot;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Cannon Shot";
		config.resizable = false;
		config.width = 1200;
		config.height = 800;

		new LwjglApplication(new CannonShot(), config);
	}
}
