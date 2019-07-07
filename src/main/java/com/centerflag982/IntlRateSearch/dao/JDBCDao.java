package com.centerflag982.IntlRateSearch.dao;

import com.centerflag982.IntlRateSearch.FileIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Component("dao")
public class JDBCDao implements AirRateDAO{
    private static final String JDBC_SQLITE_AIR_RATE_DB = "jdbc:sqlite:airRateDB.db";
    private static final String ORG_SQLITE_JDBC = "org.sqlite.JDBC";

    private Connection connection;

    @Autowired
    private FileIO ioInstance;

    public JDBCDao() {
        try {
            Class.forName(ORG_SQLITE_JDBC);
        } catch (ClassNotFoundException e){
            throw new RuntimeException("Cannot find database loader class", e);
        }
        //return DriverManager.getConnection(JDBC_SQLITE_AIR_RATE_DB);
        try {
            this.connection = DriverManager.getConnection(JDBC_SQLITE_AIR_RATE_DB);
        }
        catch (SQLException e){
            throw new RuntimeException("Error getting database connection", e);
        }
    }

    public List<String> checkForExpiredRates(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");
        String currDate = dateFormatter.format(calendar.getTime());
        int currDateInt = Integer.parseInt(currDate);
        List<String> expiredIATAs = new ArrayList<>();
        try {
            PreparedStatement expiredQuery = connection.prepareStatement("SELECT DISTINCT iata FROM rate_info WHERE expiration < ?");
            expiredQuery.setInt(1,currDateInt);
            ResultSet expiredResults = expiredQuery.executeQuery();
            while (expiredResults.next()) {
                expiredIATAs.add(expiredResults.getString(1));
            }
        }
        catch (SQLException e){
            throw new RuntimeException("Error checking for expired rates", e);
        }
        return expiredIATAs;
    }

    public List<String> getAirports(String origOrDest){
        List<String> airportResults = new ArrayList<>();
        try {
            System.out.println("Fetching airport list");
            PreparedStatement airportQuery = connection.prepareStatement("SELECT DISTINCT " + origOrDest + " FROM rate_info");
            ResultSet airportResultSet = airportQuery.executeQuery();
            while (airportResultSet.next()) {
                airportResults.add(airportResultSet.getString(1));
            }
        }
        catch (SQLException e){
            throw new RuntimeException("Error checking " + origOrDest + " airports", e);
        }
        return airportResults;
    }

    public List<String> searchRates(String org, String dest){
        List<String> rateSearchResults = new ArrayList<>();
        try {
            PreparedStatement rateQuery = connection.prepareStatement("SELECT * FROM rate_info WHERE origin = ? AND destination = ?");
            rateQuery.setString(1,org);
            rateQuery.setString(2,dest);
            ResultSet rateResults = rateQuery.executeQuery();
            while (rateResults.next()) {
                String outputString = String.format("%-6s %-24s %-7s %-7s %8.2f %8.2f %8.2f %7.2f %7.2f %7.2f %7.2f %7s %7s %12d",
                        rateResults.getString(1), rateResults.getString(2), rateResults.getString(3), rateResults.getString(4),
                        rateResults.getFloat(5), rateResults.getFloat(6),rateResults.getFloat(7),rateResults.getFloat(8),rateResults.getFloat(9),
                        rateResults.getFloat(10),rateResults.getFloat(11), rateResults.getString(12),rateResults.getString(13), rateResults.getInt(14));
                rateSearchResults.add(outputString);
            }
        }
        catch (SQLException e){
            throw new RuntimeException("Error checking destination airports", e);
        }
        return rateSearchResults;
    }

    public void exportRates(String iata){
        List<String> exportList = new ArrayList<>();
        try {
            PreparedStatement exportQuery = connection.prepareStatement("SELECT * FROM rate_info WHERE iata = ?");
            exportQuery.setString(1, iata);
            ResultSet exportResultSet = exportQuery.executeQuery();
            String commaSepLine = "";
            while (exportResultSet.next()) {
                for (int i = 1; i < 15; i++){
                    commaSepLine = commaSepLine + exportResultSet.getString(i) + ",";
                }
                //System.out.println(commaSepLine);
                exportList.add(commaSepLine);
                commaSepLine = "";
            }
            ioInstance.exportCSV(iata, exportList);
        }
        catch (Exception e){ //SQLException, IOException
            throw new RuntimeException("Error exporting rate data.", e);
        }
    }

    public void importRates(String iata){ //considered using UPDATE, but when more than half the columns are being adjusted, the nuclear option actually seems more elegant
        List<String> importList = ioInstance.importCSV(iata);
        try {
            PreparedStatement nukeQuery = connection.prepareStatement("DELETE FROM rate_info WHERE iata = ?");
            nukeQuery.setString(1, iata);
            nukeQuery.executeUpdate();
            for (String importString : importList){
                String[] importArray = importString.split(",");
                String rebuiltImportString = "";
                for (int i = 0; i <= 3; i++)
                {
                    rebuiltImportString = rebuiltImportString + "\"" + importArray[i] + "\", ";
                }
                for (int i = 4; i <= 10; i++)
                {
                    rebuiltImportString = rebuiltImportString + importArray[i] + ", ";
                }
                for (int i = 11; i <= 12; i++)
                {
                    rebuiltImportString = rebuiltImportString + "\"" + importArray[i] + "\", ";
                }
                rebuiltImportString = rebuiltImportString + importArray[13];
                PreparedStatement rebuildQuery = connection.prepareStatement("INSERT INTO rate_info VALUES (" + rebuiltImportString + ")");
                rebuildQuery.executeUpdate();
            }
        }
        catch (Exception e){ //SQLException, IOException
            throw new RuntimeException("Error importing rate data.", e);
        }
    }

}
