package com.example.hw1;

public class GameManager {
    private final int MAX_LIVES = 3;
    private int lives = MAX_LIVES;
    private Object player;
    private Object Object[];
    private String DIRECTION[] = {
            "DOWN",
            ""
    };

    private Boolean isCrash = false;

    public GameManager() {
        player = new Object();
        Object = new Object[3];
        Object[0] = new Object();
        Object[1] = new Object();
        Object[2] = new Object();
        ObjectsLocations();
    }

    public Object getPlayer() {
        return player;
    }

    public Object[] getObject() {
        return Object;
    }

    public Boolean getCrash() {
        return isCrash;
    }

    public GameManager setCrash(Boolean crash) {
        isCrash = crash;
        return this;
    }

    public int getLives() {
        return lives;
    }

    public void reduceLives() {
        lives--;
    }

    public void ObjectsLocations() {
        player.initializationObject(6, 1);
        Object[0].initializationObject(0, 0);
        Object[1].initializationObject(0, 1);
        Object[2].initializationObject(0, 2);
    }

    public void randomObjectDirectionMove() {
        int r = (int) (Math.random() * 3);
        if (Object[r].getLocationX() == -1 || Object[r].getLocationX() == 0 || Object[r].getLocationX() == 6) {
            Object[r].setDirection(DIRECTION[(int) (Math.random() * 2)]);
            if (Object[r].getDirection() == "DOWN")
                Object[r].setLocationX(Object[r].getStartObjectLocationX());
        }

        for (int i = 0; i < 3; i++) {
            if (Object[i].getDirection() == "DOWN") {
                if (Object[i].getLocationX() < 6)
                    Object[i].setLocationX(Object[i].getLocationX() + 1);
                else Object[i].setDirection("");
            } else Object[i].setLocationX(-1);
        }
    }

    public void playerMove() {
        switch (player.getDirection()) {
            case "LEFT":
                if (player.getLocationY() > 0)
                    player.setLocationY(player.getLocationY() - 1);
                break;
            case "RIGHT":
                if (player.getLocationY() < 2)
                    player.setLocationY(player.getLocationY() + 1);
                break;
        }
    }

    public void checkCrash() {
        for (int i = 0; i < 3; i++) {
            if (player.getLocationX() == Object[i].getLocationX() && player.getLocationY() == Object[i].getLocationY()) {
                isCrash = true;
                Object[i].setLocationX(-1);
                if (lives > 0)
                    reduceLives();
            }
        }
    }

    public void setLives(int lives) {
        this.lives = lives;
    }
}




























