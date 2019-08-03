package com.voroniuk.testgame;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.voroniuk.testgame.models.Harvest;

public class MyActor extends Actor {
    TextureAtlas textureAtlas;
    Sprite sprite;
    Harvest type;
    int i;

    public MyActor(TextureAtlas tx,Harvest harvest) {
        textureAtlas = tx;
        this.type = harvest;
        setSprite();
        i=0;

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

    public Harvest getType(){
        return type;
    }

    public void setSprite(){
        float x, y;
        if(sprite != null) {
            x = sprite.getX();
            y = sprite.getY();
        }else{
            x = 0;
            y = 0;
        }
        this.sprite = textureAtlas.createSprite(type.getSpriteName());
        this.sprite.setPosition(x, y);
        setBounds(getX(), getY(), sprite.getWidth(), sprite.getHeight());
        setTouchable(Touchable.enabled);
    }

    public void evolve(){
        this.type = type.getNext();
        setSprite();
    }
}
