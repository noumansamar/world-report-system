package org.example.service;

import org.example.model.Country;
import org.example.dao.City_rep;
import org.example.dao.CapitalCity_rep;
import org.example.model.CapitalCity;
import org.example.model.City;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
public class ReportService
{
    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getInstance(Locale.US);
    public void printCountryReport(List<Country> countries) {
        System.out.println("Requirement 1: All the countries in the world organised by largest population to smallest.");
        System.out.println();
        String format = "%-5s %-32s %-16s %-24s %15s %-20s%n";
        System.out.printf(format, "Code", "Name", "Continent", "Region", "Population", "Capital");
        System.out.println("=".repeat(115));
        int rank = 1;
        for (Country c : countries)
        {
            System.out.printf("%-5s %-32s %-16s %-24s %15s %-20s%n", c.getCode(), truncate(c.getName(), 32), c.getContinent(), truncate(c.getRegion(), 24), NUMBER_FORMAT.format(c.getPopulation()), c.getCapital() != null ? c.getCapital() : "N/A");
            rank++;
        }
        System.out.println("=".repeat(115));
        System.out.println("Total countries: " + countries.size());
    }
    private String truncate(String value, int maxLength)
    {
        if (value == null)
        {
            return "";
        }
        return value.length() > maxLength ? value.substring(0, maxLength - 1) + "…" : value;
    }
    public void printCityReport(List<City> cities)
    {
        System.out.println();
        System.out.println("Requirement 2: All the cities in the world organised by largest population to smallest.");
        System.out.println();
        String format = "%-30s %-24s %-20s %15s%n";
        System.out.printf(format, "Name", "Country", "District", "Population");
        System.out.println("=".repeat(95));
        for (City c : cities)
        {
            System.out.printf(format, truncate(c.getName(), 30), truncate(c.getCountry(), 24), truncate(c.getDistrict(), 20),
                    NUMBER_FORMAT.format(c.getPopulation()));
        }
        System.out.println("=".repeat(95));
        System.out.println("Total cities: " + cities.size());
    }
    public void printCapitalCityReport(List<CapitalCity> capitals)
    {
        System.out.println();
        System.out.println("Requirement 3: All the capital cities in the world organised by largest population to smallest.");
        System.out.println();
        String format = "%-30s %-24s %15s%n";
        System.out.printf(format, "Name", "Country", "Population");
        System.out.println("=".repeat(75));
        for (CapitalCity c : capitals)
        {
            System.out.printf(format, truncate(c.getName(), 30), truncate(c.getCountry(), 24),
                    NUMBER_FORMAT.format(c.getPopulation()));
        }
        System.out.println("=".repeat(75));
        System.out.println("Total capital cities: " + capitals.size());
    }
}