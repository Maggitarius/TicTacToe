package com.example.practice14;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView statisticsText;
    private Button startGameButton, settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences("gamePrefs", MODE_PRIVATE);
        String theme = preferences.getString("theme", "light");

        if ("dark".equals(theme)) {
            setTheme(R.style.Theme_App_Dark);
        } else {
            setTheme(R.style.Theme_App_Light);
        }

        setContentView(R.layout.activity_main);
        statisticsText = findViewById(R.id.statisticsText);
        startGameButton = findViewById(R.id.startGameButton);
        settingsButton = findViewById(R.id.settingsButton);

        updateStatistics();

        settingsButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
        });

        startGameButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, GameActivity.class));
        });
    }

    private void updateStatistics() {
        SharedPreferences prefs = getSharedPreferences("gamePrefs", MODE_PRIVATE);
        int winsX = prefs.getInt("winsX", 0);
        int winsO = prefs.getInt("winsO", 0);
        int draws = prefs.getInt("draws", 0);

        String statistics = "Победы X: " + winsX + "\nПобеды O: " + winsO + "\nНичьи: " + draws;
        statisticsText.setText(statistics);
    }
}
