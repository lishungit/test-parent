package com.test;

import org.omg.CORBA.Object;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadTest {

    public static void main(String args[]){
//        ThreadTest threadTest = new ThreadTest();
//        threadTest.dealListWithMutiThread();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Test");
            }
        }).start();
    }

    public void dealListWithMutiThread(){
        List<Object> list = new ArrayList<Object>(10000);
        int index = 0;
        ExecutorService ex = Executors.newFixedThreadPool(5);
        int dealSize = 2000;
        List<Future<List<Object>>> futures = new ArrayList<Future<List<Object>>>(5);
        //分配
        for(int i=0;i<= 5;i++,index+=dealSize){
            int start = index;
            if(start>=list.size()) break;
            int end = start + dealSize;
            end = end>list.size() ? list.size() : end;
            futures.add(ex.submit(new Task(list,start,end)));
        }
        try {
            //处理
            List<Object>  result = new ArrayList<Object>();
            for(Future<List<Object>> future : futures){
                //合并操作
                result.addAll(future.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class Task implements Callable<List<Object>> {

        private List<Object> list;
        private int start;
        private int end;

        public Task(List<Object> list,int start,int end){
            this.list = list;
            this.start = start;
            this.end = end;
        }

        @Override
        public List<Object> call() throws Exception {
            Object obj = null;
            List<Object> retList = new ArrayList<Object>();
            for(int i=start;i<end;i++){
                obj = list.get(i);
                //你的处理逻辑
            }
            //返回处理结果
            return retList;
        }
    }
}
