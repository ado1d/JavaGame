package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import java.util.Random;

public class Obstacle {
    private Texture texture;
    private float x, y;
    private float speed;
    private static final float SCALE = 0.2f; 
    private Rectangle boundingBox;
    private float leftReduction; 
    private float rightReduction; 
    private float topReduction;   

    public Obstacle(String textureFile) {
        texture = new Texture(Gdx.files.internal(textureFile));
        Random random = new Random();
        x = Gdx.graphics.getWidth();
        y = 50; // ground lvel

        speed = 350 + random.nextInt(100); 
        
        //reduced by 30%
        leftReduction = texture.getWidth() * SCALE * 0.30f;  
        rightReduction = texture.getWidth() * SCALE * 0.30f; 
        topReduction = texture.getHeight() * SCALE * 0.30f;  

        boundingBox = new Rectangle(
            x + leftReduction, 
            y,               
            texture.getWidth() * SCALE - leftReduction - rightReduction, 
            texture.getHeight() * SCALE - topReduction 
        );
    }

    public void update(float delta) {
        x -= speed * delta;
        boundingBox.setPosition(x + leftReduction, y);
    }

    public Rectangle getHitBox() {
        return boundingBox;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, texture.getWidth() * SCALE, texture.getHeight() * SCALE);
    }

    public void dispose() {
        texture.dispose();
    }
}
