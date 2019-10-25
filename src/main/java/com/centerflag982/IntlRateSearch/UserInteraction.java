package com.centerflag982.IntlRateSearch;

import com.centerflag982.IntlRateSearch.dao.JDBCDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Component("uiInstance")
public class UserInteraction {

    @Autowired
    private AppOutput outputInstance;
    //For test class:
    //private AppOutput outputInstance = new ConsoleOutput();

    private Scanner scanner = new Scanner(System.in);

    @Autowired
    private JDBCDao dao;
    //private JDBCDao dao = new JDBCDao();

    public void startUp() {
        outputInstance.displayOutput("Welcome to my cheap 7L knockoff, try not to break it please");
        List<String> expiredList = dao.checkForExpiredRates();
        if (!expiredList.isEmpty()) {
            System.out.println("WARNING: " + expiredList.size() + " airlines' rates are expired!");
        }
        String selectString = "";
        while (!selectString.matches("Q")){
            outputInstance.displayOutput("[1] Search for rates   [2] Update an airline   [3] Update expired airlines   [4] Backup DB   [5] Load backup CSV   [Q] Exit");
            selectString = scanner.nextLine().toUpperCase();
            switch (selectString) {
                case "1":
                    searchRates();
                    break;
                case "2":
                    updateRates();
                    break;
                case "3":
                    quickUpdate(expiredList);
                    break;
                case "4":
                    databaseBackup();
                    break;
                case "5":
                    loadBackup();
                    break;
                case "Q":
                    System.out.println("Bye");
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
        String iataInput = "";
        while (!validInput){
            iataInput = scanner.nextLine().toUpperCase();
            iataInput = iataInput.replaceAll("[^a-zA-Z0-9]", "");
            if (iataInput.length() == 2){
                validInput = true;
            } else {
                System.out.println("IATA codes must be 2 alphanumeric characters.");
            }
        }
        dao.exportRates(iataInput);
        System.out.println("CSV file exported. LEAVE THIS WINDOW OPEN, make all necessary changes to the file, then press Enter to import.");
        scanner.nextLine();
        dao.importRates(iataInput);
        System.out.println("Updated successfully.");
    }

    private void quickUpdate(List<String> expiredList){
        String iataListString = "";
        for (String iata : expiredList){
            dao.exportRates(iata);
            iataListString = iataListString + " " + iata;
        }
        System.out.println("CSV files exported for the following airlines:" + iataListString);
        System.out.println("LEAVE THIS WINDOW OPEN, make all necessary changes to the files, then press Enter to import.");
        scanner.nextLine();
        for (String iata : expiredList){
            dao.importRates(iata);
        }
        System.out.println("All airlines updated successfully.");
    }

    private void databaseBackup(){
        dao.exportRates("BACKUP");
        System.out.println("All DB info backed up to BACKUP_data.csv");
        System.out.println("BE SURE TO COPY THE FILE ELSEWHERE.");
    }

    private void loadBackup(){
        System.out.println("MAKE SURE \"BACKUP_data.csv\" IS PRESENT, then press enter to continue.");
        dao.importRates("BACKUP");
        System.out.println("DB overwritten from backup.");
    }
}
