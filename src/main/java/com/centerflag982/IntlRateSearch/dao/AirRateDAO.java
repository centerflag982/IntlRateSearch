package com.centerflag982.IntlRateSearch.dao;

import java.util.List;

public interface AirRateDAO {
    List<String> checkForExpiredRates();
    List<String>  getAirports(String originOrDestination);
    List<String> searchRates(String origin, String destination);
    void exportRates(String iata);
    void importRates(String iata);
}

//TODO add Hibernate version?
//Not really needed, simple application... might as well show off though
