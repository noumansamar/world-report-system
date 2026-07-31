package org.example.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class CityTest
{
    @Test
    void gettersReturnCorrectValues()
    {
        City city = new City("Karachi", "Pakistan", "Sindh", 9269265L);
        assertEquals("Karachi", city.getName());
        assertEquals("Pakistan", city.getCountry());
        assertEquals("Sindh", city.getDistrict());
        assertEquals(9269265L, city.getPopulation());
    }
}