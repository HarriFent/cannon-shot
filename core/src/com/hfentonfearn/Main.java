package com.hfentonfearn;

import com.badlogic.gdx.Game;
import com.hfentonfearn.utils.AssetLoader;
import com.hfentonfearn.screens.SplashScreen;

public class Main extends Game {
	
	@Override
	public void create () {
		GameManager.init(this);
		GameManager.setScreen(new SplashScreen());
		AssetLoader.load();
	}
}
