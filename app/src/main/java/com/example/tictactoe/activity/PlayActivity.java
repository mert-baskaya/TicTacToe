package com.example.tictactoe.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tictactoe.R;

public class PlayActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];
    private GridLayout playground;
    private TextView playerX;
    private TextView playerY;
    private Button restart;
    private Button quit;
    private boolean playerXTurn = true;
    private int roundCount = 0;
    private String playerXName;
    private String playerYName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        playground = findViewById(R.id.activity_play_container);
        playerX = findViewById(R.id.activity_play_player_x_tw);
        playerY = findViewById(R.id.activity_play_player_y_tw);
        restart = findViewById(R.id.activity_play_restart_button);
        quit = findViewById(R.id.activity_player_quit_button);

        Intent incomingIntent = getIntent();
        Bundle players = incomingIntent.getBundleExtra("play");
        if (players != null) {
            playerXName = players.getString("playerX");
            playerYName = players.getString("playerY");
            playerX.setText(playerXName);
            playerY.setText(playerYName);
        } else
            Log.e("player activity", "bundle is empty");

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "btn_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                //TODO styling
                //buttons[i][j].set
                buttons[i][j].setOnClickListener(this);
            }
        }

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPlayground();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (!((Button) view).getText().toString().equals("")) {
            return;
        }

        if (playerXTurn)
            ((Button) view).setText("X");
        else
            ((Button) view).setText("O");

        roundCount++;

        if (checkWin()) {
            if (playerXTurn)
                playerXWins();
            else
                playerYWins();
        } else if (roundCount == 9)
            draw();
        else
            playerXTurn = !playerXTurn;
    }

    private void playerXWins() {
        Toast.makeText(this, playerXName + " " + R.string.winner, Toast.LENGTH_SHORT).show();
        lockButtons();
    }

    private void playerYWins() {
        Toast.makeText(this, playerYName + " " + R.string.winner, Toast.LENGTH_SHORT).show();
        lockButtons();
    }

    private void draw() {
        Toast.makeText(this, R.string.draw, Toast.LENGTH_SHORT).show();
        lockButtons();
    }

    private void lockButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setClickable(false);
            }
        }
    }

    private void unlockButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setClickable(true);
            }
        }
    }

    private void resetButtonContents() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
    }

    private void resetPlayground() {
        resetButtonContents();
        unlockButtons();
    }

    private boolean checkWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }

        for (int j = 0; j < 3; j++) {
            if (field[0][j].equals(field[1][j])
                    && field[0][j].equals(field[2][j])
                    && !field[0][j].equals("")) {
                return true;
            }
        }

        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }

        return field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("");
    }
}
