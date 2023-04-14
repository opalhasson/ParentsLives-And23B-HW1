package com.example.hw1;

public class Object {

    private int startObjectLocationX;
    private int startObjectLocationY;
    private int locationX;
    private int locationY;
    private String direction;


    public Object() {
    }

    public void initializationObject(int x, int y) {
        startObjectLocationX = locationX = x;
        startObjectLocationY = locationY = y;
        direction="";
    }

    public String getDirection() {
        return direction;
    }

    public Object setDirection(String direction) {
        this.direction = direction;
        return this;
    }

    public int getLocationX() {
        return locationX;
    }

    public Object setLocationX(int locationX) {
        this.locationX = locationX;
        return this;
    }

    public int getLocationY() {
        return locationY;
    }

    public Object setLocationY(int locationY) {
        this.locationY = locationY;
        return this;
    }

    public int getStartObjectLocationX() {
        return startObjectLocationX;
    }

    public int getStartObjectLocationY() {
        return startObjectLocationY;
    }

}
