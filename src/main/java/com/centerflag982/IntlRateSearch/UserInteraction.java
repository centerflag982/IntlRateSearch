package com.centerflag982.IntlRateSearch;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Scanner;

public class UserInteraction {

    //@Autowired
    //private AppOutput outputInstance;
    //For test class:
    private AppOutput outputInstance = new ConsoleOutput();

    Scanner scanner = new Scanner(System.in);

    public void greet(String inputInt) {
        outputInstance.displayOutput("hi etc");
        //if (expiredStuff != null) {
        //expiredStuffPrompting
        //}
        outputInstance.displayOutput("[1] search for rates    [2] update an airline");
        int selection = Integer.parseInt(scanner.nextLine()); //NumberFormatException
    }

}
