package gameobjects;

/**
 * Created by chris on 1/24/2015.
 * Define share properties of pipe and grass objects

 */
import com.badlogic.gdx.math.Vector2;

public abstract class Scrollable {

    // Protected is similar to private, but allows inheritance by subclasses.
    //subclasses can access these fields
    protected Vector2 position;
    protected Vector2 velocity;
    protected int width;
    protected int height;
    //is scrollable object still visible
    protected boolean isScrolledLeft;

    public Scrollable(float x, float y, int width, int height, float scrollSpeed) {
        position = new Vector2(x, y);
        velocity = new Vector2(scrollSpeed, 0);
        this.width = width;
        this.height = height;
        isScrolledLeft = false;
    }
    public void stop() {
        velocity.x = 0;
    }
    public void update(float delta) {
        position.add(velocity.cpy().scl(delta));
        // If the Scrollable object is no longer visible:
        if (position.x + width < 0) {
            isScrolledLeft = true;
        }
    }

    // Reset: Should Override in subclass for more specific behavior.
    public void reset(float newX) {
        //change the position
        position.x = newX;
        isScrolledLeft = false;
    }

    // Getters for instance variables
    public boolean isScrolledLeft() {
        return isScrolledLeft;
    }

    public float getTailX() {
        //the right side of the scrollable object
        return position.x + width;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
