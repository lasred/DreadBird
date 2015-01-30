package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

import gamehelpers.InputHandler;
import gameworld.GameRenderer;
import gameworld.GameWorld;

/**
 * Created by chris on 1/22/2015.
 */
public class GameScreen implements Screen {
    /*
    Good OO design pattern, want each class to do one thing
    So helper classes
    One for updating the game objects - GameWorld
    Another for rendering the game objects -  GameRenderer
     */
    private GameWorld world;
    private GameRenderer renderer;
    //how long the game ha been running?
    private float runTime;
    public GameScreen() {
        //width of the screen
        float screenWidth = Gdx.graphics.getWidth();
        //height of the screen
        float screenHeight = Gdx.graphics.getHeight();
        Gdx.app.log("Dimensions", "Screen Width: " + screenWidth + " Screen Height: " + screenHeight);
        //scale everything down to orthographic camera's width and height
        float gameWidth = 136;
        float scaleDownFactor = screenWidth/gameWidth;
        float gameHeight = screenHeight / scaleDownFactor;
        int midPointY = (int)(gameHeight / 2);
        world = new GameWorld(midPointY);
        renderer = new GameRenderer(world, (int)gameHeight, midPointY);
        Gdx.input.setInputProcessor(new InputHandler(world.getBird()));

    }

    /**
     * @param delta number of seconds that has passed since last time render was called
     * Animation will use the runTime to determine which TextureREgion to display
     * Game loop - update objects and render those objects
     * OO Design - Game screen should only do one thing
     *  (1/60s)
     */
    @Override
    public void render(float delta) {
        runTime += delta;
        world.update(delta);
        renderer.render(runTime);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        //Gdx.app.log("GameScreen", "show called");
    }

    @Override
    public void hide() {
        //Gdx.app.log("GameScreen", "hide called");
    }

    @Override
    public void pause() {
        //Gdx.app.log("GameScreen", "pause called");
    }

    @Override
    public void resume(){
        //Gdx.app.log("GameScreen", "resume called");
    }

    @Override
    public void dispose() {
        // Leave blank
    }
}
