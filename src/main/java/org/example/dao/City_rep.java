package org.example.dao;

import org.example.db.DatabaseConnection;
import org.example.model.City;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class City_rep
{
    private final DatabaseConnection dbConnection;
    public City_rep(DatabaseConnection dbConnection)
    {
        this.dbConnection = dbConnection;
    }
    //the function that return all the cities in order of the population.
    public List<City> getAllCitiesByPopulationDesc()
    {
        String sql = "SELECT ci.Name, co.Name AS CountryName, ci.District, ci.Population " + "FROM city ci " + "JOIN country co ON ci.CountryCode = co.Code " + "ORDER BY ci.Population DESC";
        List<City> cities = new ArrayList<>();
        Connection conn = dbConnection.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery())
        {
            while (rs.next())
            {
                cities.add(new City(rs.getString("Name"), rs.getString("CountryName"), rs.getString("District"), rs.getLong("Population")));
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Failed to fetch cities by population", e);
        }
        return cities;
    }
    public List<City> getTopNCitiesByPopulation(int n)
    {
        String sql = "SELECT ci.Name, co.Name AS CountryName, ci.District, ci.Population " + "FROM city ci " + "JOIN country co ON ci.CountryCode = co.Code " + "ORDER BY ci.Population DESC " + "LIMIT ?";
        List<City> cities = new ArrayList<>();
        Connection conn = dbConnection.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setInt(1, n);
            try (ResultSet rs = stmt.executeQuery())
            {
                while (rs.next())
                {
                    cities.add(new City(rs.getString("Name"), rs.getString("CountryName"), rs.getString("District"), rs.getLong("Population")));
                }
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Failed to fetch the top " + n + " cities", e);
        }
        return cities;
    }
}