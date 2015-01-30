package gameobjects;

import gamehelpers.AssetLoader;
import gameworld.GameWorld;

/**
 * Created by chris on 1/24/2015.
 * Take care of creating scrollable objects, updating them
 * and handling reset
 * Because it updates scrollable objects and its bounding rectangles, ScrollHandler should also perform collision
 * checking
 * Handle logic for incrementing score
 */
public class ScrollHandler {
    private GameWorld theWorld;
    private Grass frontGrass, backGrass;
    private Pipe pipe1, pipe2, pipe3;
    public static final int SCROLL_SPEED = -59;
    //distance between the gaps
    public static final int PIPE_GAP = 49;
    /*
    Constructor receives a floating point value(Where to create the ground)
    that tells us where we need to create our Grass and
    Pipe  objects
     */
    public ScrollHandler(GameWorld theW, float yPos) {
        theWorld = theW;
        //x y width height scrollspeed
        frontGrass = new Grass(0, yPos, 143, 11, SCROLL_SPEED);
        backGrass = new Grass(frontGrass.getTailX(), yPos, 143, 11,
                SCROLL_SPEED);
        //x y width height scrollspeed
        pipe1 = new Pipe(210, 0, 22, 60, SCROLL_SPEED, yPos);
        pipe2 = new Pipe(pipe1.getTailX() + PIPE_GAP, 0, 22, 70, SCROLL_SPEED, yPos);
        pipe3 = new Pipe(pipe2.getTailX() + PIPE_GAP, 0, 22, 60, SCROLL_SPEED, yPos);
    }
    public void stop() {
        frontGrass.stop();
        backGrass.stop();
        pipe1.stop();
        pipe2.stop();
        pipe3.stop();
    }
    // Return true if ANY pipe hits the bird.
    public boolean collides(Bird bird) {
        if (!pipe1.isScored()
                && pipe1.getX() + (pipe1.getWidth() / 2) < bird.getX()
                + bird.getWidth()) {
            //add one to the score if bird's beak is past pipe's midpoint
            theWorld.addScore(1);
            //do not want this to repeat until pipe is reset
            pipe1.setScored(true);
            AssetLoader.coin.play();
        } else if (!pipe2.isScored()
                && pipe2.getX() + (pipe2.getWidth() / 2) < bird.getX()
                + bird.getWidth()) {
            theWorld.addScore(1);
            pipe2.setScored(true);
            AssetLoader.coin.play();

        } else if (!pipe3.isScored()
                && pipe3.getX() + (pipe3.getWidth() / 2) < bird.getX()
                + bird.getWidth()) {
            theWorld.addScore(1);
            pipe3.setScored(true);
            AssetLoader.coin.play();

        }
        return (pipe1.collides(bird) || pipe2.collides(bird) || pipe3.collides(bird));
    }

    public void update(float delta) {
        // Update our objects
        frontGrass.update(delta);
        backGrass.update(delta);
        pipe1.update(delta);
        pipe2.update(delta);
        pipe3.update(delta);

        // Check if any of the pipes are scrolled left,
        // and reset accordingly
        if (pipe1.isScrolledLeft()) {
            //when resetting pipe 1, should go behind pipe 3
            pipe1.reset(pipe3.getTailX() + PIPE_GAP);
        } else if (pipe2.isScrolledLeft()) {
            //when resetting pipe 2, should go behind pipe 1
            pipe2.reset(pipe1.getTailX() + PIPE_GAP);
        } else if (pipe3.isScrolledLeft()) {
            //when resetting pip3, should go behind pipe 2
            pipe3.reset(pipe2.getTailX() + PIPE_GAP);
        }

        // Same with grass
        if (frontGrass.isScrolledLeft()) {
            frontGrass.reset(backGrass.getTailX());
        } else if (backGrass.isScrolledLeft()) {
            backGrass.reset(frontGrass.getTailX());
        }
    }

    public Grass getFrontGrass() {
        return frontGrass;
    }

    public Grass getBackGrass() {
        return backGrass;
    }

    public Pipe getPipe1() {
        return pipe1;
    }

    public Pipe getPipe2() {
        return pipe2;
    }

    public Pipe getPipe3() {
        return pipe3;
    }
}
