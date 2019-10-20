package com.example.tictactoe.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tictactoe.R;
public class PlayerNameActivity extends AppCompatActivity {

    private EditText playerX;
    private EditText playerO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_name);

        playerX = findViewById(R.id.activity_player_name_player_x_et);
        playerO = findViewById(R.id.activity_player_name_player_y_et);
        Button start = findViewById(R.id.activity_player_name_start_button);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playerX.getText().length() == 0 && playerO.getText().length() == 0) {
                    Toast.makeText(PlayerNameActivity.this, R.string.no_name_error, Toast.LENGTH_SHORT).show();
                } else {
                    Intent toPlay = new Intent(PlayerNameActivity.this, PlayActivity.class);
                    Bundle players = new Bundle();
                    players.putString("playerX", playerX.getText().toString());
                    players.putString("playerO", playerO.getText().toString());
                    toPlay.putExtra("play", players);
                    startActivityForResult(toPlay, 1);
                }
            }
        });
    }
}
