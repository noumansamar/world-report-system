package org.example;

import org.example.dao.Country_rep;
import org.example.db.DatabaseConnection;
import org.example.model.Country;
import org.example.service.ReportService;
import org.example.dao.City_rep;
import org.example.model.City;
import java.util.List;
public class Main
{
    public static void main(String[] args)
    {
        DatabaseConnection db = new DatabaseConnection();
        try
        {
            //this is the requirement one.
            Country_rep countryRepo = new Country_rep(db);
            ReportService reportService = new ReportService();
            List<Country> countries = countryRepo.getAllCountriesByPopulationDesc();
            reportService.printCountryReport(countries);

            //this is the requirement 2.
            City_rep cityRepo = new City_rep(db);
            List<City> cities = cityRepo.getAllCitiesByPopulationDesc();
            reportService.printCityReport(cities);
        }
        finally
        {
            db.disconnect();
        }
    }
}