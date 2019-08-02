package com.voroniuk.testgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.voroniuk.testgame.models.Fruit;
import com.voroniuk.testgame.models.Harvest;

public class FGame extends ApplicationAdapter {
	private final float SCALE = 0.5f;

	SpriteBatch batch;
	TextureAtlas textureAtlas;
	OrthographicCamera camera;
	ExtendViewport viewport;
	Sprite backGround;


	Harvest h;
	Sprite item;

	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1143, 1386);

		textureAtlas = new TextureAtlas("sprites.txt");
		batch = new SpriteBatch();

		backGround = textureAtlas.createSprite("background");
		scaleSprite(backGround, SCALE);

		h = Fruit.getFirst();

		item = textureAtlas.createSprite(h.getSpriteName());
		scaleSprite(item, SCALE);


	}

	@Override
	public void render () {
//		Gdx.gl.glClearColor(0.57f, 0.77f, 0.85f, 1);
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		backGround.draw(batch);
		item.draw(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		textureAtlas.dispose();
		batch.dispose();

	}

	@Override
	public void resize(int width, int height) {

		camera.setToOrtho(false, width, height);
//		camera.update();
		batch.setProjectionMatrix(camera.combined);
	}

	public void scaleSprite(Sprite sprite, float scale){
		float width = sprite.getWidth() * scale;
		float height = sprite.getHeight() * scale;
		sprite.setSize(width, height);
	}
}
