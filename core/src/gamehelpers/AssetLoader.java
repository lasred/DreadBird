package gamehelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by chris on 1/23/2015.
 */
public class AssetLoader {
    public static Sound dead, flap, coin;

    public static Texture texture;
    public static TextureRegion bg, grass;
    //computerized format to store an image
    public static BitmapFont font, shadow;

    public static Animation birdAnimation;
    public static TextureRegion bird, birdDown, birdUp;

    public static TextureRegion skullUp, skullDown, bar;

    public static void load() {
        //texture - image file
        texture = new Texture(Gdx.files.internal("data/texture.png"));
        //Texture.TextureFilter.Nearest - when small pixel art is stretched to a larger size, each pixel
        //will retain its shape, not become blurry
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        /*
        TextureRegion - rectangular region of the texture
        texture, x, y, width, and height
         */
        bg = new TextureRegion(texture, 0, 0, 136, 43);
        /*
        Must be flipped because libGdx assumes y up coordinate by default
         */
        bg.flip(false, true);

        grass = new TextureRegion(texture, 0, 43, 143, 11);
        grass.flip(false, true);

        birdDown = new TextureRegion(texture, 136, 0, 17, 12);
        birdDown.flip(false, true);

        bird = new TextureRegion(texture, 153, 0, 17, 12);
        bird.flip(false, true);

        birdUp = new TextureRegion(texture, 170, 0, 17, 12);
        birdUp.flip(false, true);
        //Create an array of TextureRegions
        TextureRegion[] birds = { birdDown, bird, birdUp };
        /*
        Animation object specify how to animate flaps, take multiple texture regions
        for animation, each frame - 0.06f seconds, using the above array
         */
        birdAnimation = new Animation(0.06f, birds);
        birdAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        skullUp = new TextureRegion(texture, 192, 0, 24, 14);
        // Create by flipping existing skullUp
        skullDown = new TextureRegion(skullUp);
        skullDown.flip(false, true);

        bar = new TextureRegion(texture, 136, 16, 22, 3);
        bar.flip(false, true);
        //sound objects are stored in memory, can be loaded once
        dead = Gdx.audio.newSound(Gdx.files.internal("data/dead.wav"));
        flap = Gdx.audio.newSound(Gdx.files.internal("data/flap.wav"));
        coin = Gdx.audio.newSound(Gdx.files.internal("data/coin.wav"));

        /*
        Hiero - converts a text file into a .png Texture image similar to the one for the game
        Hiero also generates a .fnt configuration file which libGDX can read and figure
        out where each letter exists
         */
        font = new BitmapFont(Gdx.files.internal("data/text.fnt"));
        font.setScale(.25f, -.25f);

        /*
        BitmapFont allows us to draw Strings to the SpriteBatch in our GameRenderer without
        creating a new String object each time. Will use .fnt file to determine what
        each letter and number is
         */
        shadow = new BitmapFont(Gdx.files.internal("data/shadow.fnt"));
        shadow.setScale(.25f, -.25f);
    }

    public static void dispose() {
        // We must dispose of the texture when we are finished.
        texture.dispose();
        //done with sound objects, dispose them
        dead.dispose();
        flap.dispose();
        coin.dispose();
        font.dispose();
        shadow.dispose();
    }

}
