package gameworld;

import com.badlogic.gdx.math.Intersector;


import gamehelpers.AssetLoader;
import gameobjects.Bird;
import gameobjects.ScrollHandler;
import com.badlogic.gdx.math.Rectangle;



/**
 * Created by chris on 1/23/2015.
 */
public class GameWorld {
    private Rectangle ground;
    private Bird bird;
    private ScrollHandler scroller;
    private int score;
    private GameState currentState;
    private int midPointY;
    /**
     * Special data type that enables a variable to be a set of predefined variables. The variable
     * must be equal to one of the values thave been predefined for it.
     */
    public enum GameState {
        READY, RUNNING, GAMEOVER, HIGHSCORE;
    }
    public GameWorld(int midPointY) {
        this.midPointY = midPointY;
        //x, 5px above the middle of the screen
        bird = new Bird(33, midPointY - 5, 17, 12);
        // The grass should start 66 pixels below the midPointY
        scroller = new ScrollHandler(this, midPointY + 66);
        ground = new Rectangle(0, midPointY + 66, 136, 11);
        currentState = GameState.READY;
    }

    /**
     * Cascade down(pass down restart to other methods), like dominos
     * Should reset all the instance variables
     */
    public void restart() {
        //Game should be ready to play after restart
        currentState = GameState.READY;
        score = 0;
        //original y position
        bird.onRestart(midPointY - 5);
        /*pass down the resetting
        Every instance variable that has changed throughout the flow of the game will be
        reset */
        scroller.onRestart();
        currentState = GameState.READY;
    }
    private void updateReady(float delta) {

    }
    public void update(float delta) {
        /*
        Check the current state of the game before determining which specific update method
         */
       switch(currentState) {
           case READY:
               updateReady(delta);
               break;
           case RUNNING:
               updateRunning(delta);
               break;
       }
   }

   private void updateRunning(float delta) {
        // Add a delta cap so that if our game takes too long
        // to update, we will not break our collision detection.
        if (delta > .15f) {
            delta = .15f;
        }
        bird.update(delta);
        scroller.update(delta);

        //after update
        if ( scroller.collides(bird) && bird.isAlive()) {
            scroller.stop();
            //play the sound once
            AssetLoader.dead.play();
            bird.die();
            //still want updates to move the bird down
        }
        if (Intersector.overlaps(bird.getBoundingCircle(), ground)) {
            //stop the scrolling of grass and pipe
            scroller.stop();
            //die if it already isn't die(collide iwth ground), velocity is zero
            bird.die();
            AssetLoader.dead.play();
            //stop, should not go beyond the ground, so acceleration has no effect
            bird.deaccelerate();
            //no more updates
            currentState = GameState.GAMEOVER;
            //going to hit the ground
            if (score > AssetLoader.getHighScore()) {
                AssetLoader.setHighScore(score);
                //reached a game state of a high score
                currentState = GameState.HIGHSCORE;
            }
        }
    }
    public boolean isHighScore() {
        return currentState == GameState.HIGHSCORE;
    }
    public boolean isReady() {
        return currentState == GameState.READY;
    }
    public boolean isGameOver() {
        return currentState == GameState.GAMEOVER;
    }
    public void start() {
        currentState = GameState.RUNNING;
    }
    public int getScore() {
        return score;
    }
    public void addScore(int increment) {
        score += increment;
    }
    public Bird getBird() {
        return bird;
    }

    public ScrollHandler getScroller() {
        return scroller;
    }
}
