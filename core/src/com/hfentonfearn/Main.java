package com.hfentonfearn;

import com.badlogic.gdx.Game;
import com.hfentonfearn.helpers.AssetLoader;
import com.hfentonfearn.screens.GameScreen;
import com.hfentonfearn.screens.SplashScreen;

import static com.hfentonfearn.helpers.Constants.DEBUGMODE;

public class Main extends Game {
	
	@Override
	public void create () {
		GameManager.init(this);
		GameManager.setScreen(new SplashScreen());
		AssetLoader.load();
	}
}
