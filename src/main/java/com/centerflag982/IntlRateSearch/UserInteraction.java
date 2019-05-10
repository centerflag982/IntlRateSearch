package com.centerflag982.IntlRateSearch;

import com.centerflag982.IntlRateSearch.dao.JDBCDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

@Component("uiInstance")
public class UserInteraction {

    @Autowired
    private AppOutput outputInstance;
    //For test class:
    //private AppOutput outputInstance = new ConsoleOutput();

    private Scanner scanner = new Scanner(System.in);
    private JDBCDao dao = new JDBCDao();

    public void startUp() {
        outputInstance.displayOutput("hi etc");
        List<String> expiredList = dao.checkForExpiredRates();
        if (!expiredList.isEmpty()) {
            System.out.println("WARNING: " + expiredList.size() + " airlines' rates are expired!");
        }
        boolean validInput = false;
        while (!validInput) {
            outputInstance.displayOutput("[1] search for rates    [2] Update an airline  [3] Update expired airlines");
            int selection = Integer.parseInt(scanner.nextLine()); //NumberFormatException
            switch (selection) {
                case 1:
                    validInput = true;
                    searchRates();
                    break;
                case 2:
                    validInput = true;
                    updateRates();
                    break;
                case 3:
                    validInput = true;
                    quickUpdate();
                    break;
                default:
                    System.out.println("Please enter a valid option.");
            }
        }
    }

    private void searchRates(){
        List<String> validOrigins = dao.getAirports("origin");
        System.out.println("Enter origin:");
        String orgInput = scanner.nextLine().toUpperCase();
        while (!validOrigins.contains(orgInput)){
            System.out.println("Airport not found, please try again.");
            orgInput = scanner.nextLine().toUpperCase();
        }

        List<String> validDestinations = dao.getAirports("destination");
        System.out.println("Enter destination:");
        String destInput = scanner.nextLine().toUpperCase();
        while (!validDestinations.contains(destInput)){
            System.out.println("Airport not found, please try again.");
            destInput = scanner.nextLine().toUpperCase();
        }

        List<String> rateResults = dao.searchRates(orgInput, destInput);
        System.out.println(String.format("%-6s %-24s %-7s %-7s %8s %8s %8s %7s %7s %7s %7s %7s %7s %12s",
                "IATA", "AIRLINE NAME", "ORIG", "DEST", "MIN", "-45", "45", "100", "300", "500", "1000", "FSC", "SEC", "EXPIRES"));
        for (String resultLine : rateResults){
            System.out.println(resultLine);
        }
    }

    private void updateRates(){
        System.out.println("Enter IATA of airline to update.");
        boolean validInput = false;
        while (!validInput){
            String iataInput = scanner.nextLine().toUpperCase();
            iataInput = iataInput.replaceAll("[^a-zA-Z0-9]", "");
            if (iataInput.length() != 2){
                System.out.println("IATA codes must be 2 alphanumeric characters.");
            }
        }
    }

    private void quickUpdate(){
        //do stuff
    }


}
