package com.example.practice14;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    private RadioGroup themeRadioGroup;
    private Button saveSettingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences("gamePrefs", MODE_PRIVATE);
        String currentTheme = preferences.getString("theme", "light");

        if ("dark".equals(currentTheme)) {
            setTheme(R.style.Theme_App_Dark);
        } else {
            setTheme(R.style.Theme_App_Light);
        }

        setContentView(R.layout.activity_settings);

        themeRadioGroup = findViewById(R.id.themeRadioGroup);
        saveSettingsButton = findViewById(R.id.saveSettingsButton);

        if ("dark".equals(currentTheme)) {
            ((RadioButton) findViewById(R.id.themeDark)).setChecked(true);
        } else {
            ((RadioButton) findViewById(R.id.themeLight)).setChecked(true);
        }

        saveSettingsButton.setOnClickListener(v -> {
            int selectedId = themeRadioGroup.getCheckedRadioButtonId();
            String theme = "light";

            if (selectedId == R.id.themeDark) {
                theme = "dark";
            }

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("theme", theme);
            editor.apply();

            finish();
            startActivity(getIntent());
        });
    }
}
