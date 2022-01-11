package com.shun.test;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App {

        private final static int BYTE_SIZE = 1 * 1024;

        public static void main(String []args) {
            List <Object> List = new ArrayList<Object>();
            for(int i = 0 ; i < 1000 ; i ++) {
                List.add(new byte[BYTE_SIZE]);
                System.out.println("Add new object "+i);
                if(i == 6) {
                    List.clear();
                    System.out.println("Remove all data, current number: "+i);
                }

                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
}
