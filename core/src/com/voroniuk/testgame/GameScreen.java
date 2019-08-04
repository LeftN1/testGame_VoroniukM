package com.voroniuk.testgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Camera;
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
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.voroniuk.testgame.models.Desk;
import com.voroniuk.testgame.models.Fruit;
import com.voroniuk.testgame.models.Harvest;
import com.voroniuk.testgame.models.Vegetable;

import java.util.ArrayList;
import java.util.Random;

public class GameScreen implements Screen {
	private final float STX = 200 * 2;
	private final float STY = 180 * 2;
	private final float DTX = 57 * 2;
	private final float DTY = 50 * 2;

	private final Desk desk;

	private FGame game;
	private Stage stage;
	private Sprite backGround;

	TextureAtlas textureAtlas;
	ArrayList<MyActor> actorList;

	Random random = new Random();


	public GameScreen(FGame gam) {
		this.game = gam;
		this.desk = new Desk(STX, STY, DTX, DTY, 5);
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		stage.getViewport().update(FGame.WIDTH, FGame.HEIGHT,true);
		textureAtlas = new TextureAtlas("sprites.txt");
		backGround = textureAtlas.createSprite("background");

		final Skin skin = new Skin(textureAtlas);
		skin.add("default", new LabelStyle(new BitmapFont(), Color.WHITE));

		for (int i = 0; i < 21; i++) {
			addRandomItem();
		}


		final DragAndDrop dnd = new DragAndDrop();
		System.out.println(desk.getItems().size());

		for (MyActor m : desk.getItems()){

			stage.addActor(m);

			dnd.addSource(new Source(m) {
				float sX;
				float sY;
				MyActor myActor = (MyActor) getActor();
				@Override
				public Payload dragStart(InputEvent event, float x, float y, int pointer) {
					Payload payload = new Payload();
					payload.setObject(myActor);
					payload.setDragActor(new MyActor(myActor));
					myActor.setVisible(false);
					sX = myActor.getX();
					sY = myActor.getY();

					payload.setValidDragActor(new Label("Yes", skin));
					payload.setInvalidDragActor(new Label("Nooo", skin));
					return payload;
				}

				@Override
				public void drag(InputEvent event, float x, float y, int pointer) {
					super.drag(event, x, y, pointer);
				}

				@Override
				public void dragStop(InputEvent event, float x, float y, int pointer, Payload payload, Target target) {
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
					addRandomItem();
					System.out.println(desk.getItems().size());
					payloadActor.remove();
				}
			});
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
		stage.getViewport().update(FGame.WIDTH, FGame.HEIGHT, false);

		stage.getBatch().begin();
		stage.getBatch().draw(backGround,0,0, FGame.WIDTH, FGame.HEIGHT);
		stage.getBatch().end();

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();


	}

	@Override
	public void resize(int width, int height) {
//		stage.getViewport().update(width, height);
//		stage.getBatch().setProjectionMatrix(stage.getCamera().combined);
		stage.getCamera().update();
		stage.getViewport().update(width, height,false);
		game.batch.setProjectionMatrix(stage.getCamera().combined);
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


	public void addRandomItem(){
		MyActor item;
		if(random.nextBoolean()){
			item = new MyActor(textureAtlas, Fruit.getFirst(), desk.getRandomCell());
		}
		else {
			item = new MyActor(textureAtlas, Vegetable.getFirst(), desk.getRandomCell());
		}
		desk.addItem(item);
	}

}
