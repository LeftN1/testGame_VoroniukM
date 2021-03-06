package com.voroniuk.testgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FillViewport;

public class MainMenuScreen implements Screen {

    final FGame game;

    OrthographicCamera camera;
    FillViewport viewport;

    public MainMenuScreen(FGame gam) {
        this.game = gam;
        camera = new OrthographicCamera();
        viewport = new FillViewport(800, 480, camera);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0.3f,0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.font.draw(game.batch, "Tap to start", 400, 240);
        game.batch.end();

        if(Gdx.input.isTouched()){
            game.setScreen(new GameScreen());
            dispose();
        }

    }

    @Override
    public void resize(int width, int height) {
        camera.update();
        viewport.update(width, height,true);
        game.batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
