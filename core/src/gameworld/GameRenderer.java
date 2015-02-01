package gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import gamehelpers.AssetLoader;
import gameobjects.Bird;
import gameobjects.Grass;
import gameobjects.Pipe;
import gameobjects.ScrollHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
//inverted flappy bird
public class GameRenderer {

    private GameWorld myWorld;
    private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;
    //draw images for us, using indices
    private SpriteBatch batcher;

    private int midPointY;
    private int gameHeight;

    // Game Objects
    private Bird bird;
    private ScrollHandler scroller;
    private Grass frontGrass, backGrass;
    private Pipe pipe1, pipe2, pipe3;

    // Game Assets
    private TextureRegion bg, grass;
    private Animation birdAnimation;
    private TextureRegion birdMid, birdDown, birdUp;
    private TextureRegion skullUp, skullDown, bar;

    public GameRenderer(GameWorld world, int gameHeight, int midPointY) {
        myWorld = world;
        this.gameHeight = gameHeight;
        this.midPointY = midPointY;
        cam = new OrthographicCamera();
        //true - want y pointing down
        //specify game units - 136 game units of width
        cam.setToOrtho(true, 136, gameHeight);
        batcher = new SpriteBatch();
        batcher.setProjectionMatrix(cam.combined);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);
        // Call helper methods to initialize instance variables
        initGameObjects();
        initAssets();
    }

    private void initGameObjects() {
        bird = myWorld.getBird();
        scroller = myWorld.getScroller();
        frontGrass = scroller.getFrontGrass();
        backGrass = scroller.getBackGrass();
        //need the scroller to access these
        pipe1 = scroller.getPipe1();
        pipe2 = scroller.getPipe2();
        pipe3 = scroller.getPipe3();
    }

    private void initAssets() {
        bg = AssetLoader.bg;
        grass = AssetLoader.grass;
        birdAnimation = AssetLoader.birdAnimation;
        birdMid = AssetLoader.bird;
        birdDown = AssetLoader.birdDown;
        birdUp = AssetLoader.birdUp;
        skullUp = AssetLoader.skullUp;
        skullDown = AssetLoader.skullDown;
        bar = AssetLoader.bar;
    }

    private void drawGrass() {
        // Draw the grass
        batcher.draw(grass, frontGrass.getX(), frontGrass.getY(),
                frontGrass.getWidth(), frontGrass.getHeight());
        batcher.draw(grass, backGrass.getX(), backGrass.getY(),
            backGrass.getWidth(), backGrass.getHeight());
    }

    private void drawSkulls() {
        // Temporary code! Sorry about the mess :)
        // We will fix this when we finish the Pipe class.

        batcher.draw(skullUp, pipe1.getX() - 1,
                pipe1.getY() + pipe1.getHeight() - 14, 24, 14);
        batcher.draw(skullDown, pipe1.getX() - 1,
                pipe1.getY() + pipe1.getHeight() + 45, 24, 14);

        batcher.draw(skullUp, pipe2.getX() - 1,
                pipe2.getY() + pipe2.getHeight() - 14, 24, 14);
        batcher.draw(skullDown, pipe2.getX() - 1,
                pipe2.getY() + pipe2.getHeight() + 45, 24, 14);

        batcher.draw(skullUp, pipe3.getX() - 1,
                pipe3.getY() + pipe3.getHeight() - 14, 24, 14);
        batcher.draw(skullDown, pipe3.getX() - 1,
                pipe3.getY() + pipe3.getHeight() + 45, 24, 14);
    }

    private void drawPipes() {
        // Temporary code! Sorry about the mess :)
        // We will fix this when we finish the Pipe class.
        batcher.draw(bar, pipe1.getX(), pipe1.getY(), pipe1.getWidth(),
                pipe1.getHeight());
        batcher.draw(bar, pipe1.getX(), pipe1.getY() + pipe1.getHeight() + 45,
                                       //rest of the area between the hole and grass starting
                pipe1.getWidth(), midPointY + 66 - (pipe1.getHeight() + 45));

        batcher.draw(bar, pipe2.getX(), pipe2.getY(), pipe2.getWidth(),
                pipe2.getHeight());
        batcher.draw(bar, pipe2.getX(), pipe2.getY() + pipe2.getHeight() + 45,
                pipe2.getWidth(), midPointY + 66 - (pipe2.getHeight() + 45));

        batcher.draw(bar, pipe3.getX(), pipe3.getY(), pipe3.getWidth(),
                pipe3.getHeight());
        batcher.draw(bar, pipe3.getX(), pipe3.getY() + pipe3.getHeight() + 45,
                pipe3.getWidth(), midPointY + 66 - (pipe3.getHeight() + 45));
    }

    public void render(float runTime) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shapeRenderer.begin(ShapeType.Filled);
        // Draw Background color
        shapeRenderer.setColor(55 / 255.0f, 80 / 255.0f, 100 / 255.0f, 1);
        shapeRenderer.rect(0, 0, 136, midPointY + 66);
        // Draw Dirt
        shapeRenderer.setColor(147 / 255.0f, 80 / 255.0f, 27 / 255.0f, 1);
        shapeRenderer.rect(0, midPointY + 77, 136, 52);
        shapeRenderer.end();
        batcher.begin();
        // 1. Draw Grass
        drawGrass();

        // 2. Draw Pipes
        drawPipes();
        drawSkulls();
        if (bird.shouldntFlap()) {
          batcher.draw(birdMid, bird.getX(), bird.getY(),
             //where to rotate, position about x and y
              bird.getWidth() / 2.0f, bird.getHeight() / 2.0f, bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());
        } else {
            batcher.draw(birdAnimation.getKeyFrame(runTime), bird.getX(), bird.getY(), bird.getWidth() / 2.0f ,
           bird.getHeight() / 2.0f, bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());
        }
        if (myWorld.isReady()) {
            // Draw shadow first
            AssetLoader.shadow.draw(batcher, "Touch me", (136 / 2)  - (42), 76);
            // Draw text
            AssetLoader.font.draw(batcher, "Touch me", (136 / 2)- (42 - 1), 75);
        } else {
            if (myWorld.isGameOver() || myWorld.isHighScore()) {
                if (myWorld.isGameOver()) {
                    AssetLoader.shadow.draw(batcher, "Game Over", 25, 56);
                    AssetLoader.font.draw(batcher, "Game Over", 24, 55);
                    AssetLoader.shadow.draw(batcher, "High Score:", 23, 106);
                    AssetLoader.font.draw(batcher, "High Score:", 22, 105);
                    String highScore = AssetLoader.getHighScore() + "";
                    // Draw shadow first
                    AssetLoader.shadow.draw(batcher, highScore, (136 / 2)     - (3 * highScore.length()), 128);
                    // Draw text
                    AssetLoader.font.draw(batcher, highScore, (136 / 2) - (3 * highScore.length() - 1), 127);
                } else {
                    AssetLoader.shadow.draw(batcher, "High Score!", 19, 56);
                    AssetLoader.font.draw(batcher, "High Score!", 18, 55);
                }
                AssetLoader.shadow.draw(batcher, "Try again?", 23, 76);
                AssetLoader.font.draw(batcher, "Try again?", 24, 75);
            }
            // Convert integer into String
            String score = myWorld.getScore() + "";
            // Draw shadow first
            AssetLoader.shadow.draw(batcher, score, (136 / 2) - (3 * score.length()), 12);
            // Draw text
            AssetLoader.font.draw(batcher, score,  (136 / 2) - (3 * score.length() - 1), 11);
        }
        batcher.end();

//        shapeRenderer.begin(ShapeType.Filled);
//        shapeRenderer.setColor(Color.RED);
//        shapeRenderer.circle(bird.getBoundingCircle().x, bird.getBoundingCircle().y, bird.getBoundingCircle().radius);

        /*
         * Excuse the mess below. Temporary code for testing bounding
         * rectangles.
         */
            // Bar up for pipes 1 2 and 3
//        shapeRenderer.rect(pipe1.getBarUp().x, pipe1.getBarUp().y,
//                pipe1.getBarUp().width, pipe1.getBarUp().height);
//        shapeRenderer.rect(pipe2.getBarUp().x, pipe2.getBarUp().y,
//                pipe2.getBarUp().width, pipe2.getBarUp().height);
//        shapeRenderer.rect(pipe3.getBarUp().x, pipe3.getBarUp().y,
//                pipe3.getBarUp().width, pipe3.getBarUp().height);

            // Bar down for pipes 1 2 and 3
//        shapeRenderer.rect(pipe1.getBarDown().x, pipe1.getBarDown().y,
//                pipe1.getBarDown().width, pipe1.getBarDown().height);
//        shapeRenderer.rect(pipe2.getBarDown().x, pipe2.getBarDown().y,
//                pipe2.getBarDown().width, pipe2.getBarDown().height);
//        shapeRenderer.rect(pipe3.getBarDown().x, pipe3.getBarDown().y,
//                pipe3.getBarDown().width, pipe3.getBarDown().height);

            // Skull up for Pipes 1 2 and 3
//        shapeRenderer.rect(pipe1.getSkullUp().x, pipe1.getSkullUp().y,
//                pipe1.getSkullUp().width, pipe1.getSkullUp().height);
//        shapeRenderer.rect(pipe2.getSkullUp().x, pipe2.getSkullUp().y,
//                pipe2.getSkullUp().width, pipe2.getSkullUp().height);
//        shapeRenderer.rect(pipe3.getSkullUp().x, pipe3.getSkullUp().y,
//                pipe3.getSkullUp().width, pipe3.getSkullUp().height);

            // Skull down for Pipes 1 2 and 3
//        shapeRenderer.rect(pipe1.getSkullDown().x, pipe1.getSkullDown().y,
//                pipe1.getSkullDown().width, pipe1.getSkullDown().height);
//        shapeRenderer.rect(pipe2.getSkullDown().x, pipe2.getSkullDown().y,
//                pipe2.getSkullDown().width, pipe2.getSkullDown().height);
//        shapeRenderer.rect(pipe3.getSkullDown().x, pipe3.getSkullDown().y,
//                pipe3.getSkullDown().width, pipe3.getSkullDown().height);
//        shapeRenderer.end();


        }

}
