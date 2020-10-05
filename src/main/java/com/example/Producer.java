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

        /*
         while the questionList is full, wait until it will be filled with something
         at that time, the other thread will wake you up
        */
        synchronized (questionList) {
            while (questionList.size() == LIMIT) {
                System.out.println("Questions has been piled put.. please wait for answers");
                questionList.wait();
            }
        }
        /*
         wake up, now it's time to insert an item to the List (questionNo)
         after the insertion, wait a little and wake up the other thread (the consumer)
         */
        synchronized (questionList) {
            System.out.println("New Question: " + questionNo);
            questionList.add(questionNo);
            Thread.sleep(100);
            questionList.notify();
        }
    }

    @Override
    public void run() {

        while (true) {
            try {
                readQuestion(questionNo++);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
