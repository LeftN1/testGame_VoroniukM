package com.voroniuk.testgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
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
	Stage stage;


	Harvest h;
	Sprite item;

	@Override
	public void create () {
		camera = new OrthographicCamera();

		viewport = new ExtendViewport(800, 600, camera);
		stage = new Stage();
		stage.setViewport(viewport);


		Gdx.input.setInputProcessor(stage);

		final Skin skin = new Skin();
		skin.add("default", new LabelStyle(new BitmapFont(), Color.WHITE));
		skin.add("background", new Texture("background.png"));
		skin.add("apple", new Texture("apple.png"));
		skin.add("carrot", new Texture("carrot.png"));


//		camera = new OrthographicCamera();
//		camera.setToOrtho(false, 1143, 1386);
//
		final Image sourceImage = new Image(skin, "apple");
		sourceImage.setBounds(0, 0, 112, 102);
		stage.addActor(sourceImage);

		Image validTargetImage = new Image(skin, "apple");
		validTargetImage.setBounds(300, 300, 112, 102);
		stage.addActor(validTargetImage);

		Image invalidTargetImage = new Image(skin, "carrot");
		invalidTargetImage.setBounds(500, 500, 112, 102);
		stage.addActor(invalidTargetImage);

		DragAndDrop dragAndDrop = new DragAndDrop();

		dragAndDrop.addSource(new Source(sourceImage) {
			public Payload dragStart (InputEvent event, float x, float y, int pointer) {
				Payload payload = new Payload();
				payload.setObject(sourceImage);

				payload.setDragActor(new Label("Some payload!", skin));

				Label validLabel = new Label("Some payload!", skin);
				validLabel.setColor(0, 1, 0, 1);
				payload.setValidDragActor(validLabel);

				Label invalidLabel = new Label("Some payload!", skin);
				invalidLabel.setColor(1, 0, 0, 1);
				payload.setInvalidDragActor(invalidLabel);

				return payload;
			}
		});

		dragAndDrop.addTarget(new DragAndDrop.Target(validTargetImage) {
			public boolean drag (Source source, Payload payload, float x, float y, int pointer) {
				getActor().setColor(Color.GREEN);
				return true;
			}

			public void reset (Source source, Payload payload) {
				getActor().setColor(Color.WHITE);
			}

			public void drop (Source source, Payload payload, float x, float y, int pointer) {
				System.out.println("Accepted: " + payload.getObject() + " " + x + ", " + y);
			}
		});
		dragAndDrop.addTarget(new DragAndDrop.Target(invalidTargetImage) {
			public boolean drag (Source source, Payload payload, float x, float y, int pointer) {
				getActor().setColor(Color.RED);
				return false;
			}

			public void reset (Source source, Payload payload) {
				getActor().setColor(Color.WHITE);
			}

			public void drop (Source source, Payload payload, float x, float y, int pointer) {
			}
		});

	}

	@Override
	public void render () {

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	@Override
	public void dispose () {
		stage.dispose();

	}

	@Override
	public void resize(int width, int height) {

		stage.getViewport().update(width, height, true);

	}

	public void scaleSprite(Sprite sprite, float scale){
		float width = sprite.getWidth() * scale;
		float height = sprite.getHeight() * scale;
		sprite.setSize(width, height);
	}
}
