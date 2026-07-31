package org.example.dao;

import org.example.db.DatabaseConnection;
import org.example.model.PopulationReport;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class Population_rep
{
    private final DatabaseConnection dbConnection;
    public Population_rep(DatabaseConnection dbConnection)
    {
        this.dbConnection = dbConnection;
    }
    //for the population living in the cities and the rural areas.
    public List<PopulationReport> getPopulationBreakdownByCountry()
    {
        String sql = "SELECT co.Name AS CountryName, co.Population AS TotalPopulation, " + "       COALESCE(SUM(ci.Population), 0) AS CityPopulation " + "FROM country co " + "LEFT JOIN city ci ON ci.CountryCode = co.Code " + "GROUP BY co.Code, co.Name, co.Population " + "ORDER BY co.Name";
        List<PopulationReport> reports = new ArrayList<>();
        Connection conn = dbConnection.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery())
        {
            while (rs.next())
            {
                reports.add(new PopulationReport(rs.getString("CountryName"), rs.getLong("TotalPopulation"), rs.getLong("CityPopulation")));
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Failed to fetch population breakdown by country", e);
        }
        return reports;
    }
    public long getWorldPopulation()
    {
        String sql = "SELECT SUM(Population) AS Total FROM country";
        return querySinglePopulation(sql, null);
    }
    public long getContinentPopulation(String continent)
    {
        String sql = "SELECT SUM(Population) AS Total FROM country WHERE Continent = ?";
        return querySinglePopulation(sql, continent);
    }
    public long getRegionPopulation(String region)
    {
        String sql = "SELECT SUM(Population) AS Total FROM country WHERE Region = ?";
        return querySinglePopulation(sql, region);
    }
    public long getCountryPopulation(String countryName)
    {
        String sql = "SELECT Population AS Total FROM country WHERE Name = ?";
        return querySinglePopulation(sql, countryName);
    }
    public long getDistrictPopulation(String district)
    {
        String sql = "SELECT SUM(Population) AS Total FROM city WHERE District = ?";
        return querySinglePopulation(sql, district);
    }
    public long getCityPopulation(String cityName)
    {
        String sql = "SELECT Population AS Total FROM city WHERE Name = ?";
        return querySinglePopulation(sql, cityName);
    }
    private long querySinglePopulation(String sql, String param)
    {
        Connection conn = dbConnection.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql))
        {
            if (param != null)
            {
                stmt.setString(1, param);
            }
            try (ResultSet rs = stmt.executeQuery())
            {
                if (rs.next())
                {
                    return rs.getLong("Total");
                }
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Failed to fetch population", e);
        }
        return 0;
    }
}