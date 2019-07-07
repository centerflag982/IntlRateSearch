package com.centerflag982.IntlRateSearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component("ioInstance")
public class FileIO {

    @Autowired
    private ImportValidator validator;

    public void exportCSV(String iata, List<String> exportList) throws IOException{

        String fileName = "./" + iata + "data.csv";
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName));
        PrintWriter printWriter = new PrintWriter(bufferedWriter);
        printWriter.println("#IATA,AIRLINE NAME,ORIG,DEST,MIN,-45,45,100,300,500,1000,FSC,SEC,EXPIRES");
        for (String outputLine : exportList){
            printWriter.println(outputLine);
        }
        printWriter.close();
    }

    public List<String> importCSV(String iata){
        List<String> importList = new ArrayList<>();
        String fileName = "./" + iata + "data.csv";
        File importFile = new File(fileName);
        try {
            Scanner scanner = new Scanner(importFile);
            scanner.nextLine(); // skip over header
            String importString;
            while (scanner.hasNextLine()){
                importString = scanner.nextLine();
                if (validator.validateString(importString)){
                    importList.add(importString);
                }
            //TODO for some reason I feel like there's supposed to be something here???????
            }
            scanner.close();
        }
        catch (FileNotFoundException e){
            throw new RuntimeException("CSV file missing, did you rename or delete it? (I'm blaming you either way)", e);
        }
        if(!importFile.delete())
        {
            System.out.println(iata + " CSV file failed to delete after import, please do so manually");
        }
        return importList;
    }


}
