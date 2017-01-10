package com.genjitheninja.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;

import helpers.PlayServices;
import screens.LoadingScreen;
import screens.StartScreen;

public class GenjiTheNinja extends Game {
	private final AssetManager assetManager = new AssetManager();
	public static PlayServices playServices;

	public GenjiTheNinja(PlayServices playServices)
	{
		this.playServices = playServices;
	}

	@Override
	public void create () {
		setScreen(new LoadingScreen(this, playServices));
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}
}
