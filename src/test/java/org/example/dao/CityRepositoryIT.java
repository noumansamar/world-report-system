package org.example.dao;

import org.example.db.DatabaseConnection;
import org.example.model.City;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
class CityRepositoryIT
{
    private static DatabaseConnection db;
    private static City_rep cityRepo;
    @BeforeAll
    static void setUp()
    {
        db = new DatabaseConnection();
        cityRepo = new City_rep(db);
    }
    @AfterAll
    static void tearDown()
    {
        db.disconnect();
    }
    @Test
    void returnsNonEmptyCityList()
    {
        List<City> cities = cityRepo.getAllCitiesByPopulationDesc();
        assertFalse(cities.isEmpty());
    }
    @Test
    void topNReturnsExactlyNResults()
    {
        List<City> top5 = cityRepo.getTopNCitiesByPopulation(5);
        assertEquals(5, top5.size());
    }
    @Test
    void topNResultsAreSortedDescending()
    {
        List<City> top10 = cityRepo.getTopNCitiesByPopulation(10);

        for (int i = 0; i < top10.size() - 1; i++) {
            assertTrue(top10.get(i).getPopulation() >= top10.get(i + 1).getPopulation());
        }
    }
}