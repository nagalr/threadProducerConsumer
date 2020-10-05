package com.example;

import java.util.List;

public class Producer implements Runnable {

    List<Integer> questionList = null;
    final int LIMIT = 5;
    private int questionNo;

    public Producer(List<Integer> questionList) {
        this.questionList = questionList;
    }

    public void readQuestion(int questionNo) throws InterruptedException {
        synchronized (questionList) {
            while (questionList.size() == LIMIT) {
                System.out.println("Questions has benn piled put.. please wait for answers");
                questionList.wait();
            }
        }
        synchronized (questionList){
            System.out.println("New Question: " + questionNo);
            questionList.add(questionNo);
        }
    }

    @Override
    public void run() {

    }
}
