package org.example.service;

import org.example.model.Country;
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
}