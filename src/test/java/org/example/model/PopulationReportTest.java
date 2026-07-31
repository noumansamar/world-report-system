package org.example.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PopulationReportTest
{
    @Test
    void calculatesNonCityPopulationCorrectly()
    {
        PopulationReport report = new PopulationReport("Pakistan", 156483000L, 50000000L);
        assertEquals(106483000L, report.getNonCityPopulation());
    }
    @Test
    void calculatesPercentagesCorrectly()
    {
        PopulationReport report = new PopulationReport("TestLand", 1000000L, 250000L);
        assertEquals(25.0, report.getCityPercentage(), 0.001);
        assertEquals(75.0, report.getNonCityPercentage(), 0.001);
    }
    @Test
    void handlesZeroTotalPopulationWithoutDivideByZero()
    {
        PopulationReport report = new PopulationReport("Empty", 0L, 0L);
        assertEquals(0.0, report.getCityPercentage());
        assertEquals(0.0, report.getNonCityPercentage());
    }
    @Test
    void guardsAgainstNegativeNonCityPopulation()
    {
        // Simulates the inconsistent source data where city population > total population
        PopulationReport report = new PopulationReport("Bad Data", 1000L, 1500L);
        assertEquals(0L, report.getNonCityPopulation());
    }
}