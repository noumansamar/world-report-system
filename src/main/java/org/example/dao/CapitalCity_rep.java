package org.example.dao;

import org.example.db.DatabaseConnection;
import org.example.model.CapitalCity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class CapitalCity_rep
{
    private final DatabaseConnection dbConnection;
    public CapitalCity_rep(DatabaseConnection dbConnection)
    {
        this.dbConnection = dbConnection;
    }
    //this ftn return all the capitalcities in order to population.
    public List<CapitalCity> getAllCapitalCitiesByPopulationDesc()
    {
        String sql = "SELECT ci.Name, co.Name AS CountryName, ci.Population " + "FROM country co " + "JOIN city ci ON co.Capital = ci.ID " + "ORDER BY ci.Population DESC";
        List<CapitalCity> capitals = new ArrayList<>();
        Connection conn = dbConnection.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next())
            {
                capitals.add(new CapitalCity(rs.getString("Name"), rs.getString("CountryName"), rs.getLong("Population")));
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Failed to fetch capital cities by population", e);
        }
        return capitals;
    }
}