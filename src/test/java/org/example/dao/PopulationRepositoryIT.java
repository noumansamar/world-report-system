package org.example.dao;

import org.example.db.DatabaseConnection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PopulationRepositoryIT
{
    private static DatabaseConnection db;
    private static Population_rep populationRepo;
    @BeforeAll
    static void setUp()
    {
        db = new DatabaseConnection();
        populationRepo = new Population_rep(db);
    }
    @AfterAll
    static void tearDown()
    {
        db.disconnect();
    }
    @Test
    void worldPopulationIsPositive()
    {
        long worldPop = populationRepo.getWorldPopulation();
        assertTrue(worldPop > 0);
    }
    @Test
    void continentPopulationIsLessThanWorld()
    {
        long asiaPop = populationRepo.getContinentPopulation("Asia");
        long worldPop = populationRepo.getWorldPopulation();
        assertTrue(asiaPop > 0);
        assertTrue(asiaPop < worldPop);
    }
    @Test
    void unknownCountryReturnsZero()
    {
        long pop = populationRepo.getCountryPopulation("NotARealCountry");
        assertEquals(0, pop);
    }
}