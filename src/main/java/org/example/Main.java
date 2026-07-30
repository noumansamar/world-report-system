package org.example;

import org.example.dao.Country_rep;
import org.example.db.DatabaseConnection;
import org.example.model.Country;
import org.example.service.ReportService;
import java.util.List;
public class Main
{
    public static void main(String[] args)
    {
        DatabaseConnection db = new DatabaseConnection();
        try
        {
            Country_rep countryRepo = new Country_rep(db);
            ReportService reportService = new ReportService();
            List<Country> countries = countryRepo.getAllCountriesByPopulationDesc();
            reportService.printCountryReport(countries);
        }
        finally
        {
            db.disconnect();
        }
    }
}