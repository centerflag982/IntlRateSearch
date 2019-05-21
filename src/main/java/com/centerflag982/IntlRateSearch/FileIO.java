package com.centerflag982.IntlRateSearch;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component("ioInstance")
public class FileIO {

    public void exportCSV(String iata, List<String> exportList) throws java.io.IOException{

        String fileName = "./" + iata + "data.csv";
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName));
        PrintWriter printWriter = new PrintWriter(bufferedWriter);
        printWriter.println("#IATA,AIRLINE NAME,ORIG,DEST,MIN,-45,45,100,300,500,1000,FSC,SEC,EXPIRES");
        for (String outputLine : exportList){
            printWriter.println(outputLine);
        }
        printWriter.close();
    }

    public void importCSV(String iata){
        //AAAAAAAAAAAAAAAAAAAA
        List<String> importList = new ArrayList<>();
        String fileName = "./" + iata + "data.csv";
        File importFile = new File(fileName);
        try {
            Scanner scanner = new Scanner(importFile);
            String importString = "";
            while (scanner.hasNextLine()){
                importString = scanner.nextLine();
            }
        }
        catch (FileNotFoundException e){
            throw new RuntimeException("CSV file missing, did you rename or delete it?", e);
        }
    }

    public boolean validateInputString(String inputString){
        String[] validationArray = inputString.split(",");
        if (validationArray.length != 14){
            System.out.println("Incorrect length: expected 14, found " + validationArray.length);
            return false;
        }

        System.out.println(validationArray.length);
        return true;
    }
}
