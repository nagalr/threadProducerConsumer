package com.example;

import com.sun.xml.internal.ws.api.config.management.policy.ManagedServiceAssertion;

import java.util.List;

public class Consumer implements Runnable {

    List<Integer> questionList = null;

    public Consumer(List<Integer> questionList) {
        this.questionList = questionList;
    }

    public void answerQuestion() throws InterruptedException {

        /*
         while the List is empty, wait for the other thread to fill it with items,
         it will wake you up
         */
        synchronized (questionList) {
            while (questionList.isEmpty()) {
                System.out.println("No Questions to Answer...waiting for Producer to get questions");
                questionList.wait();
            }
        }
        /*
         wake up by the other thread, remove an item from the List and prints it to the console
         wake up the other thread via 'notify()'
         */
        synchronized (questionList) {
            Thread.sleep(5000);
            System.out.println("Answered Question: " + questionList.remove(0));
            questionList.notify();
        }
    }

    @Override
    public void run() {

        while (true) {
            try {
                answerQuestion();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
