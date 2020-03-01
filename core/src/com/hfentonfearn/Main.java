package com.hfentonfearn;

import com.badlogic.gdx.Game;
import com.hfentonfearn.screens.SplashScreen;
import com.hfentonfearn.utils.AssetLoader;

public class Main extends Game {
	
	@Override
	public void create () {
		GameManager.init(this);
		GameManager.setScreen(new SplashScreen());
		AssetLoader.load();
	}
}
