package com.hfentonfearn;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hfentonfearn.helpers.AssetLoader;
import com.hfentonfearn.screens.SplashScreen;

public class CannonShot extends Game {
	
	@Override
	public void create () {
		AssetLoader.load();
		setScreen(new SplashScreen(this));
	}

	@Override
	public void dispose () {
		super.dispose();
		AssetLoader.dispose();
	}
}
