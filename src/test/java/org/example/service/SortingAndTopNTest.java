package org.example.service;

import org.example.model.City;
import org.example.model.Country;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
class SortingAndTopNTest
{
    private List<Country> sampleCountries()
    {
        List<Country> countries = new ArrayList<>();
        countries.add(new Country("A", "Alpha", "Asia", "Region", 500L, null));
        countries.add(new Country("B", "Beta", "Asia", "Region", 1500L, null));
        countries.add(new Country("C", "Gamma", "Asia", "Region", 1000L, null));
        return countries;
    }
    @Test
    void sortsCountriesByPopulationDescending()
    {
        List<Country> countries = sampleCountries();
        countries.sort(Comparator.comparingLong(Country::getPopulation).reversed());
        assertEquals("Beta", countries.get(0).getName());
        assertEquals("Gamma", countries.get(1).getName());
        assertEquals("Alpha", countries.get(2).getName());
    }
    @Test
    void topNReturnsCorrectSubsetInOrder()
    {
        List<City> cities = new ArrayList<>();
        cities.add(new City("Small", "X", "D", 100L));
        cities.add(new City("Medium", "X", "D", 500L));
        cities.add(new City("Large", "X", "D", 1000L));
        cities.add(new City("Huge", "X", "D", 2000L));
        cities.sort(Comparator.comparingLong(City::getPopulation).reversed());
        List<City> top2 = cities.subList(0, 2);
        assertEquals(2, top2.size());
        assertEquals("Huge", top2.get(0).getName());
        assertEquals("Large", top2.get(1).getName());
    }
    @Test
    void topNHandlesNRequestLargerThanListSize()
    {
        List<City> cities = new ArrayList<>();
        cities.add(new City("Only", "X", "D", 100L));
        int n = Math.min(5, cities.size());
        List<City> top = cities.subList(0, n);
        assertEquals(1, top.size());
    }
}