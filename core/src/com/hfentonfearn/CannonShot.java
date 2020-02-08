package com.hfentonfearn;

import com.badlogic.gdx.Game;
import com.hfentonfearn.helpers.AssetLoader;
import com.hfentonfearn.screens.GameScreen;
import com.hfentonfearn.screens.SplashScreen;

public class CannonShot extends Game {
	
	@Override
	public void create () {
		AssetLoader.load();
		setScreen(new GameScreen(this));
	}

	@Override
	public void dispose () {
		super.dispose();
		AssetLoader.dispose();
	}
}
