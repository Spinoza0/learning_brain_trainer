package com.spinoza.braintrainer;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class GameViewModel extends AndroidViewModel {

    private static final String KEY_MAXIMUM_RESULT = "record";
    private static final int GAME_DURATION = 10000;

    private SharedPreferences preferences;

    private MutableLiveData<Score> score = new MutableLiveData<>();
    private MutableLiveData<Exercise> exercise = new MutableLiveData<>();
    private MutableLiveData<Integer> timer = new MutableLiveData<>();
    private MutableLiveData<Boolean> gameResult = new MutableLiveData<>();
    private MutableLiveData<Boolean> gameOver = new MutableLiveData<>();


    public LiveData<Score> getScore() {
        return score;
    }

    public LiveData<Exercise> getExercise() {
        return exercise;
    }

    public LiveData<Integer> getTimer() {
        return timer;
    }

    public LiveData<Boolean> getGameResult() {
        return gameResult;
    }

    public LiveData<Boolean> getGameOver() {
        return gameOver;
    }

    public GameViewModel(@NonNull Application application) {
        super(application);
        preferences = PreferenceManager.getDefaultSharedPreferences(
                getApplication().getApplicationContext()
        );
    }

    public int getCurrentScore() {
        return score.getValue().getCountOfRightAnswers();
    }

    public int getMaxScore() {
        return preferences.getInt(KEY_MAXIMUM_RESULT, 0);
    }

    public void saveMaxScore(int current, int max) {
        if (max < current) {
            max = current;
            preferences.edit().putInt(KEY_MAXIMUM_RESULT, max).apply();
        }
    }

    public void win() {
        score.getValue().increaseQuestions(true);
        score.setValue(score.getValue());
    }

    public void lose() {
        score.getValue().increaseQuestions(false);
        score.setValue(score.getValue());
    }

    public void startTimer() {
        score.setValue(new Score());
        CountDownTimer countDownTimer = new CountDownTimer(GAME_DURATION, 1000) {
            @Override
            public void onTick(long l) {
                int seconds = 1 + (int) (l / 1000);
                timer.setValue(seconds);
            }

            @Override
            public void onFinish() {
                gameOver.setValue(true);
            }
        };
        countDownTimer.start();
    }

    public void startGame() {
        exercise.setValue(new Exercise());
    }

    public void checkOpinion(String opinion) {
        try {
            int answer = Integer.parseInt(opinion);
            if (exercise.getValue().checkOpinion(answer)) {
                gameResult.setValue(true);
            } else
                gameResult.setValue(false);

        } catch (Exception e) {
            gameResult.setValue(false);
        }
    }

    public class Score {
        private int countOfRightAnswers;
        private int countOfQuestions;


        public Score() {
            this.countOfRightAnswers = 0;
            this.countOfQuestions = 0;
        }

        public int getCountOfRightAnswers() {
            return countOfRightAnswers;
        }

        public void increaseQuestions(boolean wasRightAnswer) {
            countOfQuestions++;
            if (wasRightAnswer) {
                countOfRightAnswers++;
            }
        }

        @NonNull
        @Override
        public String toString() {
            return String.format("%s / %s", countOfRightAnswers, countOfQuestions);
        }
    }
}
