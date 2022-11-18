package com.spinoza.braintrainer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class GameActivity extends AppCompatActivity {

    private TextView textViewScore;
    private TextView textViewTimer;
    private TextView textViewQuestion;
    private TextView textViewVersion1;
    private TextView textViewVersion2;
    private TextView textViewVersion3;
    private TextView textViewVersion4;

    private GameViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initViews();

        viewModel = new ViewModelProvider(this).get(GameViewModel.class);

        setObservers();

        viewModel.startTimer();
        viewModel.startGame();
    }

    public void onClickAnswer(View view) {
        viewModel.checkOpinion(((TextView) view).getText().toString());
        viewModel.startGame();
    }


    private void setObservers() {
        viewModel.getTimer().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                textViewTimer.setText(integer.toString());
                int colorResId;
                if (integer > 5) {
                    colorResId = android.R.color.holo_green_dark;
                } else {
                    colorResId = android.R.color.holo_red_dark;
                }
                textViewTimer.setTextColor(getResources().getColor(colorResId));
            }
        });
        viewModel.getExercise().observe(this, new Observer<Exercise>() {
            @Override
            public void onChanged(Exercise exercise) {
                textViewVersion1.setText(exercise.getAnswer1());
                textViewVersion2.setText(exercise.getAnswer2());
                textViewVersion3.setText(exercise.getAnswer3());
                textViewVersion4.setText(exercise.getAnswer4());
                textViewQuestion.setText(exercise.getQuestion());
            }
        });
        viewModel.getScore().observe(this, new Observer<GameViewModel.Score>() {
            @Override
            public void onChanged(GameViewModel.Score score) {
                textViewScore.setText(score.toString());
            }
        });
        viewModel.getGameResult().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean gameResult) {
                if (gameResult) {
                    Toast.makeText(
                            GameActivity.this,
                            R.string.win,
                            Toast.LENGTH_SHORT).show();
                    viewModel.win();
                } else {
                    Toast.makeText(
                            GameActivity.this,
                            R.string.lose,
                            Toast.LENGTH_SHORT).show();
                    viewModel.lose();
                }
            }
        });
        viewModel.getGameOver().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean gameOver) {
                if (gameOver) {
                    int max = viewModel.getMaxScore();
                    int current = viewModel.getCurrentScore();
                    viewModel.saveMaxScore(current, max);
                    startActivity(ScoreActivity.newIntent(GameActivity.this, current, max));
                    finish();
                }
            }
        });
    }

    private void initViews() {
        textViewScore = findViewById(R.id.textViewScore);
        textViewTimer = findViewById(R.id.textViewTimer);
        textViewQuestion = findViewById(R.id.textViewQuestion);
        textViewVersion1 = findViewById(R.id.textViewOpinion1);
        textViewVersion2 = findViewById(R.id.textViewOpinion2);
        textViewVersion3 = findViewById(R.id.textViewOpinion3);
        textViewVersion4 = findViewById(R.id.textViewOpinion4);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, GameActivity.class);
    }

}