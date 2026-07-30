package org.example.dao;

import org.example.db.DatabaseConnection;
import org.example.model.Country;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class Country_rep
{
    private final DatabaseConnection dbConnection;
    public Country_rep(DatabaseConnection dbConnection)
    {
        this.dbConnection = dbConnection;
    }
    //the function that return all the countries from largest to smallest.
    public List<Country> getAllCountriesByPopulationDesc() {
        String sql = "SELECT co.Code, co.Name, co.Continent, co.Region, co.Population, ci.Name AS CapitalName " + "FROM country co " + "LEFT JOIN city ci ON co.Capital = ci.ID " + "ORDER BY co.Population DESC";
        List<Country> countries = new ArrayList<>();
        Connection conn = dbConnection.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery())
        {
            while (rs.next())
            {
                countries.add(new Country(rs.getString("Code"), rs.getString("Name"), rs.getString("Continent"), rs.getString("Region"),rs.getLong("Population"), rs.getString("CapitalName")));
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Failed to fetch the countries by population", e);
        }
        return countries;
    }
}