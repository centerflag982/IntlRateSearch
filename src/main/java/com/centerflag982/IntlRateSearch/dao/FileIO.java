package com.centerflag982.IntlRateSearch.dao;

import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

@Component("ioInstance")
public class FileIO {

    void exportCSV(String iata, List<String> exportList) throws java.io.IOException{

        String fileName = "./" + iata + "data.csv";
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName));
        PrintWriter printWriter = new PrintWriter(bufferedWriter);
        printWriter.println("#IATA,AIRLINE NAME,ORIG,DEST,MIN,-45,45,100,300,500,1000,FSC,SEC,EXPIRES");
        for (String outputLine : exportList){
            printWriter.println(outputLine);
        }
        printWriter.close();
    }

    public void importCSV(){
        //AAAAAAAAAAAAAAAAAAAA
    }
}
