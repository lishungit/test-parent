package com.test;

public class Test implements Runnable {

    private Object object1;
    private Object object2;

    public Test(Object object1, Object object2){
        this.object1 = object1;
        this.object2 = object2;
    }

    public void run() {
        synchronized (object1){
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("[object1] This is Thread test running.");
        }

        synchronized (object2){
            System.out.println("[object2] test2");
        }

        System.out.println(Thread.currentThread().getName() + " done now");
    }

    public static void main(String args[]){
        Object object1 = new Object();
        Object object2 = new Object();

        Test test = new Test(object1,object2);
        try {
            Thread t = new Thread(test);
            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + " done now.");
    }
}
