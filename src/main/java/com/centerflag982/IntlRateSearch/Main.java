package com.centerflag982.IntlRateSearch;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main (String [] args){
        ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");

        Main mainInst = (Main) context.getBean("mainInstance");
        mainInst.run();
    }

    private void run(){

    }
}
