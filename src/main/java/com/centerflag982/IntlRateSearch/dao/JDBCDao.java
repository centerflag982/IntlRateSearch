package com.centerflag982.IntlRateSearch.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCDao { //implements AirRateDAO
    private static final String JDBC_SQLITE_AIR_RATE_DB = "jdbc:sqlite:airRateDB.db";
    private static final String ORG_SQLITE_JDBC = "org.sqlite.JDBC";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName(ORG_SQLITE_JDBC);
        } catch (ClassNotFoundException e){
            throw new RuntimeException("Cannot find database loader class", e);
        }
        return DriverManager.getConnection(JDBC_SQLITE_AIR_RATE_DB);
    }

}
