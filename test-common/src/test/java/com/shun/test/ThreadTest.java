package com.shun.test;


public class ThreadTest {

    public static void main(String args[]){
        try {
            new Thread(new Runnable() {
                public void run() {
                    System.out.println("test");
                }
            }).join();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
