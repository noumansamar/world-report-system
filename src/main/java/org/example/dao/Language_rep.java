package org.example.dao;

import org.example.db.DatabaseConnection;
import org.example.model.Language_report;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class Language_rep
{
    private final DatabaseConnection dbConnection;
    public Language_rep(DatabaseConnection dbConnection)
    {
        this.dbConnection = dbConnection;
    }
    public List<Language_report> getLanguageStats(String[] languages)
    {
        String sql = "SELECT cl.Language, " + "       SUM(co.Population * cl.Percentage / 100) AS Speakers, " + "       (SELECT SUM(Population) FROM country) AS WorldPopulation " + "FROM countrylanguage cl " + "JOIN country co ON cl.CountryCode = co.Code " + "WHERE cl.Language = ? " + "GROUP BY cl.Language";
        List<Language_report> results = new ArrayList<>();
        Connection conn = dbConnection.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql))
        {
            for (String language : languages)
            {
                stmt.setString(1, language);
                try (ResultSet rs = stmt.executeQuery())
                {
                    if (rs.next())
                    {
                        long speakers = rs.getLong("Speakers");
                        long worldPopulation = rs.getLong("WorldPopulation");
                        double percentage = worldPopulation == 0 ? 0.0 : (speakers * 100.0) / worldPopulation;
                        results.add(new Language_report(language, speakers, percentage));
                    }
                    else
                    {
                        results.add(new Language_report(language, 0, 0.0));
                    }
                }
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Failed to fetch language statistics", e);
        }
        // the function that Sort greatest number of speakers to smallest
        results.sort((a, b) -> Long.compare(b.getSpeakers(), a.getSpeakers()));
        return results;
    }
}