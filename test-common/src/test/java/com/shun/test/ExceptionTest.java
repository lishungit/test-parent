package com.shun.test;

public class ExceptionTest {

    public static class A{
        public void testa(String index){
            int[] list = new int[]{1,2,3,4};
            System.out.println(index);

            throw new NullPointerException("Test exception");
        }
    }

    public static void main(String args[]) throws NullPointerException{
        System.out.println("Test start.");

        String index = null;
           try {
               A a = new A();
               a.testa(index);
           } catch (RuntimeException e){
               System.out.println(e.getMessage());
           }



        System.out.println("Test done.");
    }

}
