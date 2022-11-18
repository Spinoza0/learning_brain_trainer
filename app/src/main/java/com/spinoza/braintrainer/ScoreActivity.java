package com.spinoza.braintrainer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ScoreActivity extends AppCompatActivity {

    private static final String EXTRA_RESULT = "result";
    private static final String EXTRA_MAXIMUM_RESULT = "record";

    private TextView textViewResult;
    private TextView textViewMaxResult;
    private Button buttonStartAgain;
    int result;
    int maximumResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        initViews();

        Intent intent = getIntent();
        if (intent != null) {
            result = intent.getIntExtra(EXTRA_RESULT, 0);
            maximumResult = intent.getIntExtra(EXTRA_MAXIMUM_RESULT, 0);
        } else {
            result = 0;
            maximumResult = 0;
        }
        setContent();
    }

    private void setContent() {
        textViewResult.setText(getString(R.string.your_result, Integer.toString(result)));
        textViewMaxResult.setText(
                getString(R.string.maximum_result, Integer.toString(maximumResult))
        );
    }

    public void onClickStartGame(View view) {
        startActivity(GameActivity.newIntent(ScoreActivity.this));
        finish();
    }

    public static Intent newIntent(Context context, int result, int maximumResult) {
        Intent intent = new Intent(context, ScoreActivity.class);
        intent.putExtra(EXTRA_RESULT, result);
        intent.putExtra(EXTRA_MAXIMUM_RESULT, maximumResult);
        return intent;
    }

    private void initViews() {
        textViewResult = findViewById(R.id.textViewResult);
        textViewMaxResult = findViewById(R.id.textViewMaxResult);
        buttonStartAgain = findViewById(R.id.buttonStartAgain);
    }
}