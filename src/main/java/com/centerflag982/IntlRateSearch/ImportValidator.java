package com.centerflag982.IntlRateSearch;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component ("bridgekeeper")
public class ImportValidator {

    //check iata
    //check orig, dest
    //check rate fields
    //check expiration

    public boolean validateString(String importString){
        String[] importArray = importString.split(",");
        List<String> checkList = new ArrayList<>();
        checkList.add(Boolean.toString(checkLength(importArray)));
        checkList.add(Boolean.toString(checkIata(importArray[0])));
        checkList.add(Boolean.toString(checkAirport(importArray[2])));
        checkList.add(Boolean.toString(checkAirport(importArray[3])));
        for (int i = 4; i < 11; i++){
            checkList.add(Boolean.toString(checkRateFields(importArray[i])));
        }
        checkList.add(Boolean.toString(checkExpiration(importArray[13])));
        if (checkList.contains("false")){
            return false;
        }
        else{
            return true;
        }
    }

    private boolean checkLength(String[] input){
        if (input.length != 14){
            System.out.println("Incorrect length: expected 14 columns, found " + input.length);
            return false;
        }
        return true;
    }

    private boolean checkIata(String input){
        if (input.length() != 2){
            System.out.println("Invalid IATA code found: " + input + " - contains " + input.length() + " characters.");
            return false;
        }
        String inputTemp = input.replaceAll("[^a-zA-Z0-9]", "");
        if (inputTemp.length() != 2){
            System.out.println("Invalid IATA code found: " + input + " - contains special characters.");
            return false;
        }
        return true;
    }

    private boolean checkAirport(String input){
        if (input.length() != 3){
            System.out.println("Invalid airport code found: " + input + " - contains " + input.length() + " characters.");
            return false;
        }
        String inputTemp = input.replaceAll("[^a-zA-Z]", "");
        if (inputTemp.length() != 3){
            System.out.println("Invalid airport code found: " + input + " - contains numbers or special characters.");
            return false;
        }
        return true;
    }

    private boolean checkRateFields(String input){
        if (input.length() == 0){
            return true;
        }
        String inputTemp = input.replaceAll("[0-9]", "");
        if (!inputTemp.contains(".")){
            System.out.println("Invalid rate found: " + input + " - missing decimal point.");
            return false;
        }
        try{
            Float.parseFloat(input);
        }
        catch (NumberFormatException e){
            System.out.println("Invalid rate found: " + input + " - not a decimal number.");
            return false;
        }
        return true;
    }

    private boolean checkExpiration(String input){
        if (input.length() == 0 ){
            return true;
        }
        if (input.length() != 8){
            System.out.println("Invalid expiration date found: " + input + " - contains " + input.length() + " characters.");
            return false;
        }
        String inputTemp = input.replaceAll("[^0-9]", "");
        if (inputTemp.length() != 8){
            System.out.println("Invalid expiration date found: " + input + " - contains letters or special characters.");
            return false;
        }
        int tempInt = Integer.parseInt(input.substring(0,2));
        if (tempInt != 20 && tempInt != 99){
            System.out.println("Invalid expiration date found: " + input + " - this isn't even this century.");
            return false;
        }
        tempInt = Integer.parseInt(input.substring(4,6));
        if (tempInt > 12 && tempInt != 99){
            System.out.println("Invalid expiration date found: " + input + " - invalid month.");
            return false;
        }
        tempInt = Integer.parseInt(input.substring(6,8));
        if (tempInt > 31 && tempInt != 99){
            System.out.println("Invalid expiration date found: " + input + " - invalid day.");
            return false;
        }
        return true;
    }
}
