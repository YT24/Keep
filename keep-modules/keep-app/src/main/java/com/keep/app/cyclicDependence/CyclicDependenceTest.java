package com.keep.app.cyclicDependence;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CyclicDependenceTest {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("/springbean.xml");
        context.getBean("classA");
        context.getBean("classB");
        //System.out.println(context.getBean("classC"));

    }
}
