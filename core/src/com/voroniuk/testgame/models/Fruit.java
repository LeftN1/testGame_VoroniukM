package com.voroniuk.testgame.models;

public enum  Fruit implements Harvest {
    APPLE("apple"),
    PEAR("green-pear"),
    BANANA("banana"),
    ORANGE("orange"),
    KIWI("kiwi");

    String spriteName;
    Fruit (String sName){
        this.spriteName = sName;
    }

    public static Harvest getFirst() {
        Fruit[] values = Fruit.values();
        return values[0];
    }

    @Override
    public String getSpriteName() {
        return this.spriteName;
    }

    @Override
    public Harvest getNext() {
        Fruit[] values = Fruit.values();
        if(this.ordinal() < values.length - 1){
            return values[this.ordinal() + 1];
        }
        else {
            return this;
        }
    }
}
