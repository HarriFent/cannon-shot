package com.hfentonfearn;

import com.badlogic.gdx.Game;
import com.hfentonfearn.helpers.AssetLoader;
import com.hfentonfearn.screens.GameScreen;
import com.hfentonfearn.screens.MainMenuScreen;
import com.hfentonfearn.screens.SplashScreen;

import static com.hfentonfearn.helpers.Constants.DEBUGMODE;

public class CannonShot extends Game {
	
	@Override
	public void create () {
		AssetLoader.load();
		setScreen(DEBUGMODE ? new GameScreen(this) : new SplashScreen(this));
	}

	@Override
	public void dispose () {
		super.dispose();
		AssetLoader.dispose();
	}
}
