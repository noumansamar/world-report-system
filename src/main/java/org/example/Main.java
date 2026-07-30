package org.example;

import org.example.dao.Country_rep;
import org.example.db.DatabaseConnection;
import org.example.model.Country;
import org.example.service.ReportService;
import org.example.dao.City_rep;
import org.example.model.City;
import org.example.dao.CapitalCity_rep;
import org.example.model.CapitalCity;
import java.util.List;
import java.util.Scanner;
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

            //this is the requirement 3
            CapitalCity_rep capitalRepo = new CapitalCity_rep(db);
            List<CapitalCity> capitals = capitalRepo.getAllCapitalCitiesByPopulationDesc();
            reportService.printCapitalCityReport(capitals);

            //this is the requirement 4
            Scanner scanner = new Scanner(System.in);
            int n = 0;
            boolean validInput = false;
            while (!validInput)
            {
                System.out.print("\nEnter the top N populated cities to display : ");
                String input = scanner.nextLine().trim();
                try {
                    n = Integer.parseInt(input);
                    if (n > 0)
                    {
                        validInput = true;
                    }
                    else
                    {
                        System.out.println("Please enter a positive number.");
                    }
                }
                catch (NumberFormatException e)
                {
                    System.out.println("Invalid input — please enter a whole number.");
                }
            }
            List<City> topCities = cityRepo.getTopNCitiesByPopulation(n);
            reportService.printTopNCitiesReport(topCities, n);
        }
        finally
        {
            db.disconnect();
        }
    }
}