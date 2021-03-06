package gamehelpers;

import com.badlogic.gdx.InputProcessor;

import gameobjects.Bird;
import gameworld.GameWorld;

/**
 * Created by chris on 1/23/2015.
 * To do  - implement the InputProcessor, interface between platform dependent code and our coe
 * When platform(IOS, Android), receives some kind of input, it will call a method inside InputProcessor
 */
public class InputHandler implements InputProcessor{
    private GameWorld world;
    private Bird myBird;
    /*
    Ask reference to the bird when the InputHandler is created
    Need a reference to GameWorld object so we can determine what the current game state
    and handle touches correctly
     */
    public InputHandler(GameWorld world){
        this.world = world;
        myBird = world.getBird();
    }
    /*
    Touchdown method should call bird's onclick method. Need a refernece to our
    brid to call bird's  methods
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        if (world.isReady()) {
            world.start();
        }

        myBird.onClick();

        if (world.isGameOver() ||world.isHighScore()) {
            // Reset all variables, go to GameState.READ
            world.restart();
        }        //return true to say we handled the touch event
        return true;
    }



    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
