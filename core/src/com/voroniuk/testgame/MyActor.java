package com.voroniuk.testgame;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;

public class MyActor extends Actor {
    Sprite sprite;

    public MyActor(Sprite sp, int x, int y) {
        this.sprite = sp;
        setBounds(x, y, sprite.getWidth(), sprite.getHeight());
        setTouchable(Touchable.enabled);

        addListener(new  DragListener(){
            @Override
            public void drag(InputEvent event, float x, float y, int pointer) {
                MyActor.this.moveBy(x - getWidth() / 2, y - getHeight() / 2);
            }

            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer) {

            }
        });
    }

    @Override
    protected void positionChanged() {
        sprite.setPosition(getX(), getY());
        super.positionChanged();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
