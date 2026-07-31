package org.example.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class CountryTest
{
    @Test
    void gettersReturnCorrectValues()
    {
        Country country = new Country("USA", "United States", "North America", "North America", 278357000L, "Washington");
        assertEquals("USA", country.getCode());
        assertEquals("United States", country.getName());
        assertEquals("North America", country.getContinent());
        assertEquals("North America", country.getRegion());
        assertEquals(278357000L, country.getPopulation());
        assertEquals("Washington", country.getCapital());
    }
    @Test
    void toStringHandlesNullCapital()
    {
        Country country = new Country("XXX", "Nowhere", "Antarctica", "Antarctica", 0L, null);
        assertDoesNotThrow(country::toString);
        assertTrue(country.toString().contains("N/A"));
    }
}