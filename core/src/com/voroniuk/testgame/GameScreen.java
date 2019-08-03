package com.voroniuk.testgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.voroniuk.testgame.models.Fruit;
import com.voroniuk.testgame.models.Vegetable;

import java.util.ArrayList;

public class GameScreen implements Screen {
	private final float SCALE = 0.5f;

	private Stage stage;
	TextureAtlas textureAtlas;
	ArrayList<MyActor> actorList;


	public GameScreen() {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		textureAtlas = new TextureAtlas("sprites.txt");
		final Skin skin = new Skin(textureAtlas);
		skin.add("default", new LabelStyle(new BitmapFont(), Color.WHITE));

		actorList = new ArrayList<>();
		actorList.add(getRandomActor());
		actorList.add(getRandomActor());
		actorList.add(getRandomActor());
		actorList.add(getRandomActor());



		int x = 0;
		int y = 0;


		DragAndDrop dnd = new DragAndDrop();

		for (final MyActor m : actorList){

			m.setPosition(x, y);
			stage.addActor(m);

			dnd.addSource(new Source(m) {
				float sX;
				float sY;
				MyActor myActor = (MyActor) getActor();
				@Override
				public Payload dragStart(InputEvent event, float x, float y, int pointer) {
					Payload payload = new Payload();
					payload.setObject(myActor);
					payload.setDragActor(myActor.getMiniActor());
					myActor.setVisible(false);
					sX = myActor.getX();
					sY = myActor.getY();

					payload.setValidDragActor(new Label("Yes", skin));
					payload.setInvalidDragActor(new Label("Nooo", skin));

					return payload;
				}

				@Override
				public void dragStop(InputEvent event, float x, float y, int pointer, Payload payload, Target target) {
//					MyActor payloadActor = (MyActor) payload.getObject();
//					MyActor targetActor = (MyActor) target.getActor();
//					if(payloadActor.getType() == targetActor.getType()){
//						System.out.println("YYYYYYYYRRAA!");
//					}
					myActor.setVisible(true);
				}

			});


			dnd.addTarget(new Target(m) {
				@Override
				public boolean drag(Source source, Payload payload, float x, float y, int pointer) {
					MyActor payloadActor = (MyActor) payload.getObject();
					MyActor thisActor = (MyActor) getActor();

					if(payloadActor.getType() == thisActor.getType()){
						return true;
					}else {
						return false;
					}
				}

				@Override
				public void drop(Source source, Payload payload, float x, float y, int pointer) {
					MyActor payloadActor = (MyActor) payload.getObject();
					MyActor thisActor = (MyActor) getActor();
					thisActor.evolve();
					payloadActor.remove();
				}
			});

			x += 100;
			y += 100;
		}





 	}

	@Override
	public void dispose () {
		stage.dispose();
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();


	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
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

	public MyActor getRandomActor(){
		if(Math.random() < 0.5){
			return new MyActor(textureAtlas, Fruit.getFirst());
		}else {
			return new MyActor(textureAtlas, Vegetable.getFirst());
		}
	}

	public void scaleSprite(Sprite sprite, float scale){
		float width = sprite.getWidth() * scale;
		float height = sprite.getHeight() * scale;
		sprite.setSize(width, height);
	}

}
