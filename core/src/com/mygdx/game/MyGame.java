package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MyGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture[] playerFrames;
    private Texture backgroundTexture;
    private Animation<TextureRegion> playerAnimation;
    private float stateTime;
    private float playerX, playerY;
    private float backgroundX;
    private float scale;
    private float playerScale;
    private boolean isJumping;
    private float jumpSpeed;
    private float gravity;

    @Override
    public void create() {
        batch = new SpriteBatch();
        
        playerFrames = new Texture[9]; 

        for (int i = 0; i < 9; i++) {
            playerFrames[i] = new Texture(Gdx.files.internal("Run__00" + i + ".png"));
        }

        backgroundTexture = new Texture(Gdx.files.internal("back2.png")); 
        playerX = 50;
        playerY = 50; 
        backgroundX = 0;

        scale = (float) Gdx.graphics.getWidth() / backgroundTexture.getWidth();
        playerScale = 0.4f;

        TextureRegion[] regions = new TextureRegion[playerFrames.length];
        for (int i = 0; i < playerFrames.length; i++) {
            regions[i] = new TextureRegion(playerFrames[i]);
        }
        playerAnimation = new Animation<>(0.1f, regions); 
        stateTime = 0f;

        isJumping = false;
        jumpSpeed = 0;
        gravity = -0.6f; 
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = playerAnimation.getKeyFrame(stateTime, true);

        batch.begin();
        batch.draw(backgroundTexture, backgroundX, 0, backgroundTexture.getWidth() * scale, Gdx.graphics.getHeight());
        batch.draw(backgroundTexture, backgroundX + backgroundTexture.getWidth() * scale, 0, backgroundTexture.getWidth() * scale, Gdx.graphics.getHeight());
        batch.draw(currentFrame, playerX, playerY, currentFrame.getRegionWidth() * playerScale, currentFrame.getRegionHeight() * playerScale);

        batch.end();

        updatePlayer();
        updateBackground();
    }

    private void updatePlayer() {
        if (Gdx.input.isTouched() && !isJumping) {
            jumpSpeed = 12; 
            isJumping = true;
        }

        if (isJumping) {
            playerY += jumpSpeed;
            jumpSpeed += gravity; 
            if (playerY < 50) { 
                playerY = 50;
                isJumping = false;
                jumpSpeed = 0; 
            }
        }
    }

    private void updateBackground() {
        backgroundX -= 200 * Gdx.graphics.getDeltaTime();
        if (backgroundX <= -backgroundTexture.getWidth() * scale) {
            backgroundX = 0;
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        for (Texture frame : playerFrames) {
            frame.dispose();
        }
        backgroundTexture.dispose();
    }
}
