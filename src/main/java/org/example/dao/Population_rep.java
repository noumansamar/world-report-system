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
}