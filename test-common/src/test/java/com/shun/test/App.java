package com.shun.test;

public class App {

    public static void main(String args[]){
        TestT<String> t = new TestT<String>();
        System.out.println(t.getTest("a"));
    }
}
