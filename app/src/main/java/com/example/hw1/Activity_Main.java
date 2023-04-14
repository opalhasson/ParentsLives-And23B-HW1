package com.example.hw1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.Timer;
import java.util.TimerTask;

public class Activity_Main extends AppCompatActivity {
    private ImageView[] main_IMG_Lives;
    private ImageView[][] main_IMG_route;
    private ImageButton main_BTN_left;
    private ImageButton main_BTN_right;
    private GameManager gameManager;

    private Vibrator v;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameManager = new GameManager();
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        setContentView(R.layout.activity_main);
        findViews();
        initPlayerButtons();
    }

    private void findViews() {
        main_IMG_Lives = new ImageView[]{
                findViewById(R.id.main_IMG_live1),
                findViewById(R.id.main_IMG_live2),
                findViewById(R.id.main_IMG_live3)
        };

        main_IMG_route = new ImageView[][]{
                {findViewById(R.id.main_IMG_00), findViewById(R.id.main_IMG_01), findViewById(R.id.main_IMG_02)},
                {findViewById(R.id.main_IMG_10), findViewById(R.id.main_IMG_11), findViewById(R.id.main_IMG_12)},
                {findViewById(R.id.main_IMG_20), findViewById(R.id.main_IMG_21), findViewById(R.id.main_IMG_22)},
                {findViewById(R.id.main_IMG_30), findViewById(R.id.main_IMG_31), findViewById(R.id.main_IMG_32)},
                {findViewById(R.id.main_IMG_40), findViewById(R.id.main_IMG_41), findViewById(R.id.main_IMG_42)},
                {findViewById(R.id.main_IMG_50), findViewById(R.id.main_IMG_51), findViewById(R.id.main_IMG_52)},
                {findViewById(R.id.main_IMG_60), findViewById(R.id.main_IMG_61), findViewById(R.id.main_IMG_62)}
        };

        main_BTN_left = findViewById(R.id.main_BTN_left);
        main_BTN_right = findViewById(R.id.main_BTN_right);
    }

    private void initPlayerButtons() {
        main_BTN_left.setOnClickListener(view -> {
            gameManager.getPlayer().setDirection("LEFT");
            gameManager.playerMove();
        });

        main_BTN_right.setOnClickListener(view -> {
            gameManager.getPlayer().setDirection("RIGHT");
            gameManager.playerMove();
        });
    }


    //-------timer--------
    private Timer timer = new Timer();
    private final int DELAY = 1000; // 1000 milliseconds == 1 second
    private int counter = 0;

    private enum TIMER_STATUS {
        OFF,
        RUNNING,
        PAUSE
    }

    private TIMER_STATUS timerStatus = TIMER_STATUS.OFF;

    private void tick() {
        ++counter;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (timerStatus == TIMER_STATUS.RUNNING) {
            stopTimer();
            timerStatus = TIMER_STATUS.PAUSE;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (timerStatus == TIMER_STATUS.OFF) {
            startTimer();
        } else if (timerStatus == TIMER_STATUS.RUNNING) {
            stopTimer();
        } else startTimer();
    }

    private void startTimer() {
        timerStatus = TIMER_STATUS.RUNNING;
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> startGame());
            }
        }, 0, DELAY);
    }

    private void stopTimer() {
        timerStatus = TIMER_STATUS.OFF;
        timer.cancel();
    }

    private void startGame() {
        tick();
        runLogic();
        updateUI();
    }

    public void runLogic() {
        gameManager.randomObjectDirectionMove();
        gameManager.checkCrash();
    }

    private void updateUI() {
        for (int i = 0; i < main_IMG_route.length; i++) {
            for (int j = 0; j < main_IMG_route[0].length; j++)
                main_IMG_route[i][j].setImageResource(R.drawable.ic_road);
        }

        main_IMG_route[gameManager.getPlayer().getLocationX()][gameManager.getPlayer().getLocationY()].setImageResource(R.drawable.ic_parents);

        for (int i = 0; i < 3; i++) {
            if (gameManager.getObject()[i].getLocationX() != -1)
                main_IMG_route[gameManager.getObject()[i].getLocationX()][gameManager.getObject()[i].getLocationY()].setImageResource(R.drawable.ic_baby);
        }

        if (gameManager.getCrash()) {
            if (gameManager.getLives() > 0) {
                Toast.makeText(this, "BOOM", Toast.LENGTH_LONG).show();
                v.vibrate(500);
                main_IMG_Lives[gameManager.getLives()].setVisibility(View.INVISIBLE);
                updateUIAfterCrash();
            } else {
                gameManager.setCrash(false);
                gameManager.setLives(3);
                gameManager.getPlayer().setLocationY(gameManager.getPlayer().getStartObjectLocationY());
                main_IMG_Lives[0].setVisibility(View.VISIBLE);
                main_IMG_Lives[1].setVisibility(View.VISIBLE);
                main_IMG_Lives[2].setVisibility(View.VISIBLE);
            }
        }
    }

    private void updateUIAfterCrash() {
        main_IMG_route[gameManager.getPlayer().getLocationX()][gameManager.getPlayer().getLocationY()].setImageResource(R.drawable.ic_road);
        gameManager.getPlayer().setLocationY(gameManager.getPlayer().getStartObjectLocationY());
        main_IMG_route[gameManager.getPlayer().getLocationX()][gameManager.getPlayer().getLocationY()].setImageResource(R.drawable.ic_parents);
        gameManager.setCrash(false);
    }


}


