package gameobjects;

/**
 * Created by chris on 1/24/2015.
 */
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class Pipe extends Scrollable {
    private boolean isScored;
    // We need a constant to represent the 45 pixel vertical gap between the upper and lower pipe, and some other constants:
    public static final int VERTICAL_GAP = 45;
    public static final int SKULL_WIDTH = 24;
    public static final int SKULL_HEIGHT = 11;
    private Random r;
    //We also need to know where the ground is
    private float groundY;

    private Rectangle skullUp, skullDown, barUp, barDown;

    // When Pipe's constructor is invoked, invoke the super (Scrollable)
    // constructor
    public Pipe(float x, float y, int width, int height, float scrollSpeed, float groundY) {
        super(x, y, width, height, scrollSpeed);
        isScored = false;
        // Initialize a Random object for Random number generation
        r = new Random();
        skullUp = new Rectangle();
        skullDown = new Rectangle();
        barUp = new Rectangle();
        barDown = new Rectangle();
        this.groundY = groundY;
    }

    @Override
    public void update(float delta) {
        //want update logic in super class
        super.update(delta);

        // The set() method allows you to set the top left corner's x, y
        // coordinates,
        // along with the width and height of the rectangle
        barUp.set(position.x, position.y, width, height);
                                       //height includes the skull
        barDown.set(position.x, position.y + height + VERTICAL_GAP, width,
                groundY - (position.y + height + VERTICAL_GAP));

        // Our skull width is 24. The bar is only 22 pixels wide. So the skull
        // must be shifted by 1 pixel to the left (so that the skull is centered
        // with respect to its bar).
        // This shift is equivalent to: (SKULL_WIDTH - width) / 2
                                    //24          22
        skullUp.set(position.x - (SKULL_WIDTH - width) / 2, position.y + height
                  //11          24
                - SKULL_HEIGHT, SKULL_WIDTH, SKULL_HEIGHT);
        skullDown.set(position.x - (SKULL_WIDTH - width) / 2, barDown.y,
                SKULL_WIDTH, SKULL_HEIGHT);

    }
    public boolean collides(Bird bird) {
        //cheap check (does not take a toll on performance
        //collision is not possible if it the bird's outer x edge has not reached the pipe
        if (position.x <= bird.getX() + bird.getWidth()) {
            //expensive operation, will take a toll on performance
            return (Intersector.overlaps(bird.getBoundingCircle(), barUp)
                    //if any four rectangles collides with the bird
                    || Intersector.overlaps(bird.getBoundingCircle(), barDown)
                    || Intersector.overlaps(bird.getBoundingCircle(), skullUp) || Intersector
                    .overlaps(bird.getBoundingCircle(), skullDown));
        }
        return false;
    }
    /*
         Use this class's reset method with pipe isntance
         */
    @Override
    public void reset(float newX) {
        // Call the reset method in the superclass (Scrollable)
        //subclass still has reference to superclass's reset method
        super.reset(newX);
        // Change the height to a random number
        height = r.nextInt(90) + 15;
        isScored = false;
    }
    public Rectangle getSkullUp() {
        return skullUp;
    }

    public boolean isScored() {
        return isScored;
    }

    public void setScored(boolean b) {
        isScored = b;
    }
    public Rectangle getSkullDown() {
        return skullDown;
    }

    public Rectangle getBarUp() {
        return barUp;
    }

    public Rectangle getBarDown() {
        return barDown;
    }

}