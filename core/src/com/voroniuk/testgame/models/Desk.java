package com.voroniuk.testgame.models;

import com.badlogic.gdx.math.Vector2;
import com.voroniuk.testgame.Cell;

import java.util.ArrayList;

public class Desk {

    private ArrayList<Cell> cells;

    Desk(float stX, float stY, float dtX, float dtY, int size){
        cells = new ArrayList<>(size * size);



    }

 }
