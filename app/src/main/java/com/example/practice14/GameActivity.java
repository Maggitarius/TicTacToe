package com.example.practice14;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;
import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {
    private GridLayout gameBoard;
    private Button finishGameButton;
    private String currentPlayer = "X";
    private String[][] board = new String[3][3];

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

        setContentView(R.layout.activity_game);

        gameBoard = findViewById(R.id.gameBoard);
        finishGameButton = findViewById(R.id.finishGameButton);

        gameBoard.setRowCount(3);
        gameBoard.setColumnCount(3);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button button = new Button(this);
                button.setLayoutParams(new GridLayout.LayoutParams());
                button.setTextSize(24);
                button.setTag(i + "," + j);
                int finalI = i;
                int finalJ = j;
                button.setOnClickListener(v -> onCellClicked(finalI, finalJ, button));
                gameBoard.addView(button);
                board[i][j] = "";
            }
        }

        finishGameButton.setOnClickListener(v -> finish());
    }

    private void onCellClicked(int i, int j, Button button) {
        if (!board[i][j].isEmpty()) return;

        board[i][j] = currentPlayer;
        button.setText(currentPlayer);

        if (checkWinner()) {
            updateStatistics("win");
            finish();
        } else if (isBoardFull()) {
            updateStatistics("draw");
            finish();
        }

        currentPlayer = currentPlayer.equals("X") ? "O" : "X";
    }

    private boolean checkWinner() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0].equals(board[i][1]) && board[i][1].equals(board[i][2]) && !board[i][0].isEmpty()) {
                return true;
            }
        }
        for (int j = 0; j < 3; j++) {
            if (board[0][j].equals(board[1][j]) && board[1][j].equals(board[2][j]) && !board[0][j].isEmpty()) {
                return true;
            }
        }
        if (board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2]) && !board[0][0].isEmpty()) {
            return true;
        }
        if (board[0][2].equals(board[1][1]) && board[1][1].equals(board[2][0]) && !board[0][2].isEmpty()) {
            return true;
        }
        return false;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void updateStatistics(String result) {
        SharedPreferences prefs = getSharedPreferences("gamePrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        int winsX = prefs.getInt("winsX", 0);
        int winsO = prefs.getInt("winsO", 0);
        int draws = prefs.getInt("draws", 0);

        if ("win".equals(result)) {
            if (currentPlayer.equals("X")) {
                editor.putInt("winsX", winsX + 1);
            } else {
                editor.putInt("winsO", winsO + 1);
            }
        } else if ("draw".equals(result)) {
            editor.putInt("draws", draws + 1);
        }

        editor.apply();
    }
}
