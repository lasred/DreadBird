package gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

import gamehelpers.AssetLoader;

/**
 * Created by chris on 1/23/2015.
 */
public class Bird {
    /*
    In flappy bird you either die via making contact with the ground or hit a pipe
    Use a circle for the bird to detect collision because it never needs to rotate, should
    cover the bird's head
     */
    private Circle boundingCircle;
    //magnitude and direction. hold x c
    private Vector2 position;
    //change in position
    private Vector2 velocity;
    //change in velocity
    private Vector2 acceleration;
    //if bird is alive or not
    private boolean isAlive;
    private float rotation; // For handling bird rotation
    private int width;
    private int height;

    public Bird(float x, float y, int width, int height) {
        boundingCircle = new Circle();
        this.width = width;
        this.height = height;
        //position - vector also x and y components
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        //where did 460 come from?
        acceleration = new Vector2(0, 460);
        isAlive = true;
    }
    //when bird should begin rotating downwards
    public boolean isFalling() {
        return velocity.y > 110;
    }

    //when bird should stop flapping
    public boolean shouldntFlap() {
        //if the bird is not flapping, it is either dead or it's downward velocity
        //is greater than 70
        return velocity.y > 70 || !isAlive;
    }

    //delta called every time render is called, typically around 1/60s
    public void update(float delta) {
        //add scaled acceleration vector to velocity vector. Downward speed
        //incr        eases by 9.8m/s^2 every second
        /*
        Scale because you don't want the acceleartion vector to actually change
        Each time you create a new object, you obtain memory from the RAM, specifically the heap
        After heap fills up, garbage collector will come and clean memory so you don't run out of space
         */
        velocity.add(acceleration.cpy().scl(delta));

        //bird's terminal velocity
        if (velocity.y > 200) {
            velocity.y = 200;
        }
        // CEILING CHECK
        if (position.y < -2) {
            position.y = -2;
            velocity.y = 0;
        }
        /*scale is delta * vector
           Why?
           Game slows down, delta will go up (processor takes longer to complete previous iteration of the loop)
           By scaling with delta, achieve frame rate independent movement - if update method took twice as long to execute,
           we move our character 2x the original velocity
        */
        //use sclaed velocity to update bird's position
        //Gdx.app.log("Game Running", "Render time " + delta);
        //Gdx.app.log("Game running", "position x : " + position.x + "  position y: " + position.y);
        //Gdx.app.log("Game running", "velocity x : " + velocity.x + " velocity y: " + velocity.y);
        //Gdx.app.log("Game running", "acceleration x : " + acceleration.x + " acceleration y: " + acceleration.y);
        position.add(velocity.cpy().scl(delta));
        // Set the circle's center to be (9, 6) with respect to the bird.
        // Set the circle's radius to be 6.5f;
        //when initialized, circle's width is 17 and height is 12
        boundingCircle.set(position.x + 9, position.y + 6, 6.5f);

        // Rotate counterclockwise, going up
        if (velocity.y < 0) {
            //scale rotation by delta
            rotation -= 600 * delta;
            //cap on rotation
            if (rotation < -20) {
                rotation = -20;
            }
        }
        // Rotate clockwise if dead as well
        if (isFalling() || !isAlive) {
            //scale rotation by delta
            rotation += 480 * delta;
            //max rotation
            if (rotation > 90) {
                rotation = 90;
            }
        }
    }
    public void die() {
        isAlive = false;
        //want bird to stop moving
        velocity.y = 0;
    }
    public void deaccelerate() {
        //want bird to stop accelerating downwards once it is dead
        acceleration.y = 0;
    }
    public void onRestart(int y) {
        rotation = 0;
        position.y = y;
        velocity.x = 0;
        velocity.y = 0;
        acceleration.x = 0;
        acceleration.y = 460;
        isAlive = true;

    }
    public Circle getBoundingCircle() {
        return boundingCircle;
    }
    public void onClick(){
        if(isAlive) {
            velocity.y = -140;
            AssetLoader.flap.play();
        }
    }
    public boolean isAlive() {
        return isAlive;
    }
    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getRotation() {
        return rotation;
    }
}
