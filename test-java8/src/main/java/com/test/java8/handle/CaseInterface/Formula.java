package com.test.java8.handle.CaseInterface;

/**
 * Created by shunli on 2017/7/16.
 */
public interface Formula {

    double calculate(int a);

    default double sqrt(int a) {
        return Math.sqrt(a);
    }
}
