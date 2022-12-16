package com.spinoza.braintrainer;

import java.util.ArrayList;

public class Exercise {
    private static final int min = 5;
    private static final int max = 30;
    private final ArrayList<Integer> answers = new ArrayList<>();
    private final int rightAnswer;
    private final String question;


    public Exercise() {
        int firstNumber = getRandomNumber(min, max);
        int secondNumber = getRandomNumber(min, max);
        char markCharacter;
        int mark;
        if ((int) (Math.random() * 2) == 1) {
            mark = 1;
            markCharacter = '+';
        } else {
            mark = -1;
            markCharacter = '-';
        }
        rightAnswer = firstNumber + mark * secondNumber;
        int rightAnswerPosition = getRandomNumber(0, 3);
        for (int i = 0; i < 4; i++) {
            if (i == rightAnswerPosition) {
                answers.add(rightAnswer);
            } else {
                answers.add(getWrongAnswer());
            }
        }

        question = String.format("%s %s %s = ?", firstNumber, markCharacter, secondNumber);
    }

    public String getQuestion() {
        return question;
    }

    private int getRandomNumber(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }

    private int getWrongAnswer() {
        int wrongAnswer;
        do {
            wrongAnswer = getRandomNumber(0, max * 2) - (max - min);
        } while (wrongAnswer == rightAnswer);
        return wrongAnswer;
    }


    public String getAnswer1() {
        return answers.get(0).toString();
    }

    public String getAnswer2() {
        return answers.get(1).toString();
    }

    public String getAnswer3() {
        return answers.get(2).toString();
    }

    public String getAnswer4() {
        return answers.get(3).toString();
    }

    public boolean checkOpinion(int opinion) {
        return opinion == rightAnswer;
    }
}
