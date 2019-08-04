package com.voroniuk.testgame;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.voroniuk.testgame.models.Harvest;

public class MyActor extends Actor {

    private final float SCALE = 1f;
    private TextureAtlas textureAtlas;
    private Sprite sprite;
    private Harvest type;
    private Vector2 cell;


    public MyActor(TextureAtlas tx, Harvest harvest, Vector2 adress) {
        textureAtlas = tx;
        this.cell = adress;
        this.type = harvest;
        setSprite();
        setPosition(cell.x, cell.y);
    }

    public MyActor(MyActor m){
        this.textureAtlas = m.getTextureAtlas();
        this.type = m.getType();
        this.sprite = m.getSprite();
        this.cell = m.getCell();
        setSprite();
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

    public Vector2 getCell() {
        return cell;
    }

    public Harvest getType(){
        return type;
    }

    public Sprite getSprite(){
        return sprite;
    }

    public void setSprite(){
        float x, y;

        x = cell.x;
        y = cell.y;

        this.sprite = textureAtlas.createSprite(type.getSpriteName());
        this.sprite.setPosition(x, y);
        this.sprite.setSize(sprite.getWidth() * SCALE, sprite.getHeight() * SCALE);
        setBounds(getX(), getY(), sprite.getWidth(), sprite.getHeight());
        setTouchable(Touchable.enabled);
    }


    public boolean evolve(){
        if(this.type == this.type.getNext()){
            return false;
        }else {
            this.type = type.getNext();
            setSprite();
            return true;
        }
    }

    public TextureAtlas getTextureAtlas() {
        return textureAtlas;
    }
}
