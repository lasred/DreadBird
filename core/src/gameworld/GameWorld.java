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
    public GameWorld(int midPointY) {
        //x, 5px above the middle of the screen
        bird = new Bird(33, midPointY - 5, 17, 12);
        // The grass should start 66 pixels below the midPointY
        scroller = new ScrollHandler(this, midPointY + 66);
        ground = new Rectangle(0, midPointY + 66, 136, 11);

    }

    public void update(float delta) {
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
            AssetLoader.dead.play();
            bird.die();
        }
        if (Intersector.overlaps(bird.getBoundingCircle(), ground)) {
            scroller.stop();
            bird.die();
            bird.declerate();
        }
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
