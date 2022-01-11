package com.shun.test.service;

import com.shun.test.service.server.Services;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by shunli on 2017/6/20.
 */
public class ServiceMain {

    public static void main(String args[]){
        /*ApplicationContext context = new ClassPathXmlApplicationContext("ts-context.xml");
        Services services = (Services) context.getBean("Services");
        services.init();*/

        new Thread(()->{
            System.out.println("test");
        });

    }
}
