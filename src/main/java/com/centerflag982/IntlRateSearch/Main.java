package com.centerflag982.IntlRateSearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component("mainInstance")
public class Main {

    @Autowired
    private UserInteraction uiInstance;
    //private UserInteraction uiInstance = new UserInteraction();


    public static void main (String [] args){
        ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");

        Main mainInst = (Main) context.getBean("mainInstance");
        mainInst.run();
    }

    private void run(){
        uiInstance.startUp();
    }
}
