package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import gamehelpers.AssetLoader;
import screens.GameScreen;

/**
 * libGdx hides the platform dependent code from us
 * Game - abstract class that implements Application Listener
 * This game is the interface between our code and the underlying platform. Point of interaction
 * between the two components, doesn't expose underlying implementation details
 */
public class MyGdxGame extends Game{
    @Override
    public void create() {
        AssetLoader.load();


        setScreen(new GameScreen());
    }
    @Override
    public void dispose() {
        super.dispose();
        AssetLoader.dispose();
    }
}
