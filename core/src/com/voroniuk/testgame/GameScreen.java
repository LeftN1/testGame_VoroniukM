package com.voroniuk.testgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.voroniuk.testgame.models.Desk;
import com.voroniuk.testgame.models.Fruit;
import com.voroniuk.testgame.models.Harvest;
import com.voroniuk.testgame.models.Vegetable;

import java.util.ArrayList;
import java.util.Random;

public class GameScreen implements Screen {
	//Координаты нижней левой ячейки
	private final float STX = 400;
	private final float STY = 360;
	//Шаг ячеек по X, Y
	private final float DTX = 114;
	private final float DTY = 100;


	private DragAndDrop dnd = new DragAndDrop();
	private Skin skin;
	private Desk desk; //Управляет пустыми ячейками, выдает случайную пустую ячейку

	private FGame game;
	private Stage stage;
	private Sprite backGround;

	TextureAtlas textureAtlas;
	Random random = new Random();

	private long lastCreateTime;  //Время создания последнего сундука, первый создается сразу, т.к. = 0
	private long timeStep = 5000; //Шаг времени с которым создается сундук



	public GameScreen(FGame gam) {
		this.game = gam;
		this.desk = new Desk(STX, STY, DTX, DTY, 5);
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		stage.getViewport().update(FGame.WIDTH, FGame.HEIGHT,true);
		textureAtlas = new TextureAtlas("sprites.txt");
		backGround = textureAtlas.createSprite("background");

		skin = new Skin(textureAtlas);
		skin.add("default", new LabelStyle(new BitmapFont(), Color.WHITE));
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


		if(TimeUtils.millis() - lastCreateTime > timeStep){
			lastCreateTime = TimeUtils.millis();
			addBox();
		}
		stage.draw();
		stage.act(Gdx.graphics.getDeltaTime());


	}

	@Override
	public void resize(int width, int height) {
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


	//Создание случайного плода, добавление Drag And Drop
	public void addHarvestItem(Vector2 cell){
		final MyActor item;
		if(random.nextBoolean()){
			item = new MyActor(textureAtlas, Fruit.getFirst(), cell);
		}
		else {
			item = new MyActor(textureAtlas, Vegetable.getFirst(), cell);
		}

		stage.addActor(item);

		dnd.addSource(new Source(item) {
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
				payload.setInvalidDragActor(new Label("No", skin));
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

		dnd.addTarget(new Target(item) {
			@Override
			public boolean drag(Source source, Payload payload, float x, float y, int pointer) {
				MyActor payloadActor = (MyActor) payload.getObject();
				MyActor thisActor = (MyActor) getActor();

				//Если типы плодов одинаковые  - разрешить совмещение
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

				if(thisActor.evolve()){
					desk.clearCell(payloadActor.getCell());
					payloadActor.remove();
				}else {
					//Тут можно дописать логику совмещения двух элементов верхнего уровня эволюции,
					// например, совместное исчезновение и увеличение счета игры
				}
			}
		});
	}

	public void addRandomItem(){
		addHarvestItem(desk.getRandomCell());
	}

	//Создание сундука с анимацие "падения"
	public void addBox(){
		if(!desk.isOverLoaded()) {
			final Vector2 cell = desk.getRandomCell();
			desk.fillCell(cell);

			final Sprite box = textureAtlas.createSprite("box");
			box.setPosition(cell.x, FGame.HEIGHT - 200);
			final Actor actor = new Actor() {
				@Override
				public void draw(Batch batch, float parentAlpha) {
					box.draw(batch);
				}

				@Override
				protected void positionChanged() {
					box.setPosition(getX(), getY());
					super.positionChanged();
				}

				@Override
				public void act(float delta) {
					super.act(delta);
				}
			};
			actor.setBounds(box.getX(), box.getY(), box.getWidth(), box.getHeight());
			MoveToAction moveToAction = new MoveToAction();
			moveToAction.setPosition(cell.x, cell.y);
			moveToAction.setDuration(1);

			actor.addAction(moveToAction);

			actor.addListener(new ClickListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					addHarvestItem(cell);
					actor.remove();
					return true;
				}
			});

			stage.addActor(actor);
		}else{
			//Тут можно написать GAME OVER)
		}

	}

}
