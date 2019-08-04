package com.voroniuk.testgame.models;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import com.voroniuk.testgame.Cell;
import com.voroniuk.testgame.MyActor;

import java.util.ArrayList;
import java.util.Random;

public class Desk {

    private ArrayList<Vector2> cells;

    Random random = new Random();

    public Desk(float stX, float stY, float dtX, float dtY, int size){
        cells = new ArrayList<>(size * size);
        float x = stX;
        float y = stY;
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                cells.add(new Vector2(x, y));
                x += dtX;
            }
            x = stX;
            y += dtY;
        }
    }

    public void fillCell(Vector2 cell){
        if(!cells.isEmpty()) {
            cells.remove(cell);
        }
    }

    public void clearCell(Vector2 cell){
        cells.add(cell);
    }

    public void print(){
        for(int i = 0; i < cells.size(); i++){
            if ((i % 5) == 0){
                System.out.println();
            }
            System.out.print(cells.get(i));
        }
    }

    public Vector2 getRandomCell(){
        if(!cells.isEmpty()) {
            int index = random.nextInt(cells.size());
            Vector2 res = cells.get(index);
            return res;
        }else {
            return new Vector2(0,0);
        }
    }

    public ArrayList<Vector2> getCells() {
        return cells;
    }

    public boolean isOverLoaded(){
        return cells.size() == 0 ? true : false;
    }
}
