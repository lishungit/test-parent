package com.test.java8.handle.CaseCompare;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by shunli on 2017/7/16.
 */
public class Main {

    public static void main(String args[]){
        List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");

        // User case 1
        Collections.sort(names,(String a, String b)->a.compareTo(b));
        System.out.println(names.toString());

        // User case 2
        Collections.sort(names,(String a, String b)->{
            return a.compareTo(b);
        });
        System.out.println(names.toString());

        // User case 3
        Collections.sort(names,(a, b)->a.compareTo(b));
        System.out.println(names.toString());
    }
}
