package org.example.dao;

import org.example.db.DatabaseConnection;
import org.example.model.Country;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
class CountryRepositoryIT
{
    private static DatabaseConnection db;
    private static Country_rep countryRepo;
    @BeforeAll
    static void setUp()
    {
        db = new DatabaseConnection();
        countryRepo = new Country_rep(db);
    }
    @AfterAll
    static void tearDown()
    {
        db.disconnect();
    }
    @Test
    void returnsNonEmptyCountryList()
    {
        List<Country> countries = countryRepo.getAllCountriesByPopulationDesc();
        assertFalse(countries.isEmpty(), "Expected countries to be returned from the database");
    }
    @Test
    void resultsAreSortedByPopulationDescending()
    {
        List<Country> countries = countryRepo.getAllCountriesByPopulationDesc();
        for (int i = 0; i < countries.size() - 1; i++)
        {
            assertTrue(countries.get(i).getPopulation() >= countries.get(i + 1).getPopulation(), "List is not sorted descending at index " + i
            );
        }
    }
    @Test
    void chinaHasExpectedApproxPopulation()
    {
        List<Country> countries = countryRepo.getAllCountriesByPopulationDesc();
        Country china = countries.stream().filter(c -> c.getCode().equals("CHN")).findFirst().orElseThrow(() -> new AssertionError("China not found in results"));
        assertTrue(china.getPopulation() > 1_000_000_000L, "Expected China's population to exceed 1 billion");
    }
}