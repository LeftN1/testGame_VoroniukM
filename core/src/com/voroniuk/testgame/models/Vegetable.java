package com.voroniuk.testgame.models;

public enum  Vegetable implements Harvest {
    TOMATO("tomato"),
    CUCUMBER("cucumber"),
    EGGPLANT("eggplant"),
    POTATO("potato"),
    CARROT("carrot");

    String spriteName;
    Vegetable (String sName){
        this.spriteName = sName;
    }

    public static Harvest getFirst() {
        Vegetable[] values = Vegetable.values();
        return values[0];
    }

    @Override
    public String getSpriteName() {
        return this.spriteName;
    }

    @Override
    public Harvest getNext() {
        Vegetable[] values = Vegetable.values();
        if(this.ordinal() < values.length){
            return values[this.ordinal() + 1];
        }
        else {
            return this;
        }
    }
}