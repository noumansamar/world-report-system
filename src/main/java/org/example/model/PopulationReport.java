package org.example.model;

public class PopulationReport
{
    private final String name;
    private final long totalPopulation;
    private final long cityPopulation;
    public PopulationReport(String name, long totalPopulation, long cityPopulation)
    {
        this.name = name;
        this.totalPopulation = totalPopulation;
        this.cityPopulation = cityPopulation;
    }
    public String getName()
    {
        return name;
    }
    public long getTotalPopulation()
    {
        return totalPopulation;
    }
    public long getCityPopulation()
    {
        return cityPopulation;
    }
    public long getNonCityPopulation()
    {
        long nonCity = totalPopulation - cityPopulation;
        return Math.max(nonCity, 0);
    }
    public double getCityPercentage()
    {
        if (totalPopulation == 0)
        {
            return 0.0;
        }
        return (cityPopulation * 100.0) / totalPopulation;
    }
    public double getNonCityPercentage()
    {
        if (totalPopulation == 0)
        {
            return 0.0;
        }
        return (getNonCityPopulation() * 100.0) / totalPopulation;
    }
}