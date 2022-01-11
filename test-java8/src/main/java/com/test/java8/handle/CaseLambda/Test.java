package com.test.java8.handle.CaseLambda;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by shunli on 2017/7/16.
 */
public class Test {

    public static void main(String args[]){
        List<String> list = Arrays.asList("a","b","c");
        List<String> list1 = list.stream().filter(e -> !e.equals("b")).collect(Collectors.toList());
        list1.forEach(System.out::println);

        System.out.println(list1.stream().findFirst().get());

        Map testMap = new HashMap();
        testMap.put("testa","aaa");
        testMap.put("testb",null);
        testMap.put("testc",true);
        testMap.keySet().stream().forEach(key -> {
            if (!(testMap.get(key) instanceof String)) {
                System.out.println("Invalid value ("+key+":"+testMap.get(key)+")");
            }
        });

        testMap.forEach((k,v) -> {
            System.out.println(v);
        });
        testMap.forEach((k,v)-> System.out.println(v));
    }

}
