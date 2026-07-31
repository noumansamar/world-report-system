package org.example;

import org.example.dao.Country_rep;
import org.example.db.DatabaseConnection;
import org.example.model.Country;
import org.example.service.ReportService;
import org.example.dao.City_rep;
import org.example.model.City;
import org.example.dao.CapitalCity_rep;
import org.example.model.CapitalCity;
import org.example.dao.Population_rep;
import org.example.model.PopulationReport;
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

            //this is the requirement 5
            Population_rep populationRepo = new Population_rep(db);
            List<PopulationReport> populationReports = populationRepo.getPopulationBreakdownByCountry();
            reportService.printPopulationBreakdownReport(populationReports);

            //this is the requirement 6
            System.out.println("\n    Requirement 6: Population Lookup    ");
            System.out.println("1. World");
            System.out.println("2. Continent");
            System.out.println("3. Region");
            System.out.println("4. Country");
            System.out.println("5. District");
            System.out.println("6. City");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice)
            {
                case "1" ->
                {
                    long pop = populationRepo.getWorldPopulation();
                    reportService.printSinglePopulation("World", null, pop);
                }
                case "2" ->
                {
                    System.out.print("Enter continent name: ");
                    String continent = scanner.nextLine().trim();
                    long pop = populationRepo.getContinentPopulation(continent);
                    reportService.printSinglePopulation("Continent", continent, pop);
                }
                case "3" ->
                {
                    System.out.print("Enter region name: ");
                    String region = scanner.nextLine().trim();
                    long pop = populationRepo.getRegionPopulation(region);
                    reportService.printSinglePopulation("Region", region, pop);
                }
                case "4" ->
                {
                    System.out.print("Enter country name: ");
                    String country = scanner.nextLine().trim();
                    long pop = populationRepo.getCountryPopulation(country);
                    reportService.printSinglePopulation("Country", country, pop);
                }
                case "5" ->
                {
                    System.out.print("Enter district name: ");
                    String district = scanner.nextLine().trim();
                    long pop = populationRepo.getDistrictPopulation(district);
                    reportService.printSinglePopulation("District", district, pop);
                }
                case "6" ->
                {
                    System.out.print("Enter city name: ");
                    String city = scanner.nextLine().trim();
                    long pop = populationRepo.getCityPopulation(city);
                    reportService.printSinglePopulation("City", city, pop);
                }
                default -> System.out.println("Invalid option.");
            }
        }
        finally
        {
            db.disconnect();
        }
    }
}